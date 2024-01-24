package br.com.elotech.desafio.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties
public class CEPResponse {
    private String logradouro;
    private String numero;
    private String localidade;
    private String uf;
    private Boolean erro;
}
