package com.mxs.backend.aspecto;

import com.mxs.backend.excecao.NaoMapeadaExcecao;
import com.mxs.backend.requisicao.ProfissionalControladorAtualizarRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorCriarRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorAtualizarResposta;
import com.mxs.backend.resposta.ProfissionalControladorCriarResposta;
import com.mxs.backend.resposta.ProfissionalControladorExcluirResposta;
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
 * Classe responsável por monitorar as alterações nos registros de Profissional e salvar-las em cache.
 */
@Aspect
@Component
public class ProfissionalAlteracoesAspecto {

    private static final String ENTRADA = "entrada";
    private static final String QUANDO = "quando";

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("execution(* com.mxs.backend.controlador.ProfissionalControlador.criarProfissional(..)) || " +
            "execution(* com.mxs.backend.controlador.ProfissionalControlador.atualizarProfissional(..)) || " +
            "execution(* com.mxs.backend.controlador.ProfissionalControlador.excluirProfissional(..))")
    public void monitorarAlteracoesProfissional() {
    }

    /**
     * Método responsável por analisar o tipo de resposta de cada solicitação, encapsular suas informações e salvá-las em cache.
     *
     * @param joinPoint      ponto de corte do aspecto que monitora os serviços de alteração de Profissional
     * @param responseEntity o retorno de cada serviço que altera algum registro de Profissional
     */
    @AfterReturning(pointcut = "monitorarAlteracoesProfissional()", returning = "responseEntity")
    public void monitorarPorRetorno(JoinPoint joinPoint, Object responseEntity) {

        var agora = Instant.now().toString();
        Object[] args = joinPoint.getArgs();
        var retorno = (ResponseEntity) responseEntity;

        switch (retorno.getBody().getClass().getName()) {

            case "com.mxs.backend.resposta.ProfissionalControladorCriarResposta":

                var profissionalControladorCriarRequisicao = (ProfissionalControladorCriarRequisicao) args[0];
                var profissionalControladorCriarResposta = (ProfissionalControladorCriarResposta) retorno.getBody();

                Map<String, String> valoresCriarProfissional = new HashMap<>();
                valoresCriarProfissional.put(ENTRADA, profissionalControladorCriarRequisicao.toString());
                valoresCriarProfissional.put(QUANDO, agora);

                salvarOperacaoEmCache(OperacaoTipo.CRIAR_PROFISSIONAL, profissionalControladorCriarResposta.codigo(), valoresCriarProfissional);
                break;

            case "com.mxs.backend.resposta.ProfissionalControladorAtualizarResposta":

                var profissionalControladorAtualizarRequisicao = (ProfissionalControladorAtualizarRequisicao) args[1];
                var profissionalControladorAtualizarResposta = (ProfissionalControladorAtualizarResposta) retorno.getBody();

                Map<String, String> valoresAtualizarProfissional = new HashMap<>();
                valoresAtualizarProfissional.put(ENTRADA, profissionalControladorAtualizarRequisicao.toString());
                valoresAtualizarProfissional.put(QUANDO, agora);

                salvarOperacaoEmCache(OperacaoTipo.ATUALIZAR_PROFISSIONAL, profissionalControladorAtualizarResposta.codigo(), valoresAtualizarProfissional);
                break;

            case "com.mxs.backend.resposta.ProfissionalControladorExcluirResposta":

                var codigoProfissionalExcluir = (String) args[0];
                var profissionalControladorExcluirResposta = (ProfissionalControladorExcluirResposta) retorno.getBody();

                Map<String, String> valoresExcluirProfissional = new HashMap<>();
                valoresExcluirProfissional.put(ENTRADA, codigoProfissionalExcluir);
                valoresExcluirProfissional.put(QUANDO, agora);

                salvarOperacaoEmCache(OperacaoTipo.EXCLUIR_PROFISSIONAL, profissionalControladorExcluirResposta.codigo(), valoresExcluirProfissional);
                break;

            default:
                throw new NaoMapeadaExcecao(EXCECAO_NAO_MAPEADA);
        }
    }

    /**
     * Método responsável por salvar as operações de alteração de registro de Profissional no Redis.
     *
     * @param operacaoTipo CRIAR_PROFISSIONAL ou ATUALIZAR_PROFISSIONAL ou EXCLUIR_PROFISSIONAL
     * @param codigo       código identificador do Profissional
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
