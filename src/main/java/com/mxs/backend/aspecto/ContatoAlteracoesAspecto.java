package com.mxs.backend.aspecto;

import com.mxs.backend.excecao.NaoMapeadaExcecao;
import com.mxs.backend.requisicao.*;
import com.mxs.backend.resposta.*;
import com.mxs.backend.tipo.OperacaoTipo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.mxs.backend.padrao.MensagemPadrao.EXCECAO_NAO_MAPEADA;

/**
 * Classe responsável por monitorar as alterações nos registros de Contato e salvar-las em cache.
 */
@Aspect
@Component
public class ContatoAlteracoesAspecto {

    private static final String ENTRADA = "entrada";
    private static final String QUANDO = "quando";

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("execution(* com.mxs.backend.controlador.ContatoControlador.criarContato(..)) || " +
            "execution(* com.mxs.backend.controlador.ContatoControlador.atualizarContato(..)) || " +
            "execution(* com.mxs.backend.controlador.ContatoControlador.excluirContato(..))")
    public void monitorarAlteracoesContato() {
    }

    /**
     * Método responsável por analisar o tipo de resposta de cada solicitação, encapsular suas informações e salvá-las em cache.
     *
     * @param joinPoint ponto de corte do aspecto que monitora os serviços de alteração de Contato
     * @param responseEntity o retorno de cada serviço que altera algum registro de Contato
     */
    @AfterReturning(pointcut = "monitorarAlteracoesContato()", returning = "responseEntity")
    public void monitorarPorRetorno(JoinPoint joinPoint, Object responseEntity) {

        var agora = Instant.now().toString();
        Object[] args = joinPoint.getArgs();
        var retorno = (ResponseEntity) responseEntity;

        switch (retorno.getBody().getClass().getName()) {

            case "com.mxs.backend.resposta.ContatoControladorCriarResposta":

                var contatoControladorCriarRequisicao = (ContatoControladorCriarRequisicao) args[0];
                var contatoControladorCriarResposta = (ContatoControladorCriarResposta) retorno.getBody();

                Map<String, String> valoresCriarContato = new HashMap<>();
                valoresCriarContato.put(ENTRADA, contatoControladorCriarRequisicao.toString());
                valoresCriarContato.put(QUANDO, agora);

                salvarOperacaoEmCache(OperacaoTipo.CRIAR_CONTATO, contatoControladorCriarResposta.codigo(), valoresCriarContato);
                break;

            case "com.mxs.backend.resposta.ContatoControladorAtualizarResposta":

                var contatoControladorAtualizarRequisicao = (ContatoControladorAtualizarRequisicao) args[1];
                var contatoControladorAtualizarResposta = (ContatoControladorAtualizarResposta) retorno.getBody();

                Map<String, String> valoresAtualizarContato = new HashMap<>();
                valoresAtualizarContato.put(ENTRADA, contatoControladorAtualizarRequisicao.toString());
                valoresAtualizarContato.put(QUANDO, agora);

                salvarOperacaoEmCache(OperacaoTipo.ATUALIZAR_CONTATO, contatoControladorAtualizarResposta.codigo(), valoresAtualizarContato);
                break;

            case "com.mxs.backend.resposta.ContatoControladorExcluirResposta":

                var codigoContatoExcluir = (String) args[0];
                var contatoControladorExcluirResposta = (ContatoControladorExcluirResposta) retorno.getBody();

                Map<String, String> valoresExcluirContato = new HashMap<>();
                valoresExcluirContato.put(ENTRADA, codigoContatoExcluir);
                valoresExcluirContato.put(QUANDO, agora);

                salvarOperacaoEmCache(OperacaoTipo.EXCLUIR_CONTATO, contatoControladorExcluirResposta.codigo(), valoresExcluirContato);
                break;

            default:
                throw new NaoMapeadaExcecao(EXCECAO_NAO_MAPEADA);
        }
    }

    /**
     * Método responsável por salvar as operações de alteração de registro de Contato no Redis.
     *
     * @param operacaoTipo CRIAR_CONTATO ou ATUALIZAR_CONTATO ou EXCLUIR_CONTATO
     * @param codigo       código identificador do Contato
     * @param valores      valores que foram alterados no banco de dados
     */
    private void salvarOperacaoEmCache(OperacaoTipo operacaoTipo, String codigo, Map<String, String> valores) {
        CompletableFuture.runAsync(() -> {
            try {
                redisTemplate.opsForHash().put(operacaoTipo, codigo, valores);
            } catch (Exception e) {
            }
        });
    }
}
