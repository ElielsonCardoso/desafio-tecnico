package br.com.elotech.desafio.entity;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pessoa {
    @Id
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private LocalDate dataNascimento;
    @NotNull
    private String cpf;
    @NotNull
    @Size(max = 11)
    private String telefone;
    @NotNull
    private Integer idade;
    @OneToMany(mappedBy = "pessoa")
    private List<Endereco> enderecos = new ArrayList<>();
    public void adicionarEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
    }

}
