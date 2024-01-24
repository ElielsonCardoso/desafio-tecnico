package br.com.elotech.desafio.entity;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PessoaTest {
    @Test
    public void testPessoa(){
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setIdade(25);
        pessoa.setNome("Igor Nelson da Cunha");
        pessoa.setCpf("15723979930");
        pessoa.setTelefone("44999988777");
        pessoa.setDataNascimento(LocalDate.of(1997, 10, 16));

        Endereco enderecoResidencial = new Endereco();
        enderecoResidencial.setId(1L);
        enderecoResidencial.setUf("PR");
        enderecoResidencial.setCep("87070240");
        enderecoResidencial.setNumero("500");
        enderecoResidencial.setPessoa(pessoa);
        enderecoResidencial.setCidade("Maringá");
        enderecoResidencial.setLogradouro("Rua Olímpico, Jardim Aurora");

        Endereco enderecoComercial = new Endereco();
        enderecoComercial.setId(2L);
        enderecoComercial.setUf("PR");
        enderecoComercial.setCep("87025540");
        enderecoComercial.setNumero("7984");
        enderecoComercial.setPessoa(pessoa);
        enderecoComercial.setCidade("Maringá");
        enderecoComercial.setLogradouro("Rua Poeta Carlos Drumond de Andrade, Parque Avenida");

        pessoa.adicionarEndereco(enderecoResidencial);
        pessoa.adicionarEndereco(enderecoComercial);

        assertThat(pessoa.getId()).isEqualTo(1L);
        assertThat(pessoa.getIdade()).isEqualTo(25);
        assertThat(pessoa.getNome()).isEqualTo("Igor Nelson da Cunha");
        assertThat(pessoa.getCpf()).isEqualTo("15723979930");
        assertThat(pessoa.getTelefone()).isEqualTo("44999988777");
        assertThat(pessoa.getEnderecos().size()).isEqualTo(2);

        pessoa.removerEndereco(enderecoResidencial);
        assertThat(pessoa.getEnderecos().size()).isEqualTo(1);
    }
}
