package br.com.elotech.desafio.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import javax.validation.constraints.NotNull;

import br.com.elotech.desafio.entity.DTO.EnderecoDTO;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Endereco {
    @Id
    private Long id;
    @NotNull
    private String cep;
    @NotNull
    private String logradouro;
    @NotNull
    private String numero;
    @NotNull
    private String cidade;
    @NotNull
    private String uf;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}
