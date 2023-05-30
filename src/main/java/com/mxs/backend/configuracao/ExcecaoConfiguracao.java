package com.mxs.backend.configuracao;

import com.mxs.backend.dto.ExcecaoDto;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.tipo.ExcecaoTipo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExcecaoConfiguracao {

    @ExceptionHandler(value = {NaoEncontradoExcecao.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExcecaoDto> lancarExcecaoDeNegocio(NaoEncontradoExcecao naoEncontradoExcecao, WebRequest webRequest) {

        ExcecaoDto excecaoDto = new ExcecaoDto();
        excecaoDto.setMensagem(naoEncontradoExcecao.getMessage());
        excecaoDto.setTipo(ExcecaoTipo.NEGOCIO);
        return new ResponseEntity<>(excecaoDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExcecaoDto> lancarExcecaoDeDadosDeEntradaIncorretos(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {

        ExcecaoDto excecaoDto = new ExcecaoDto();
        excecaoDto.setMensagem(methodArgumentNotValidException.getMessage());
        excecaoDto.setTipo(ExcecaoTipo.DADOS_DE_ENTRADA_INCORRETOS);
        return new ResponseEntity<>(excecaoDto, HttpStatus.BAD_REQUEST);
    }
}
