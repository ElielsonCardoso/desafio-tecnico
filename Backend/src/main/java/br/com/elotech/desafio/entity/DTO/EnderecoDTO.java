package br.com.elotech.desafio.entity.DTO;

import br.com.elotech.desafio.entity.Pessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
    private String cep;
    private String logradouro;
    private String numero;
    private String cidade;
    private String uf;
    private Pessoa pessoa;
}
