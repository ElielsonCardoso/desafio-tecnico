package br.com.elotech.desafio.entity;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnderecoTest {

    @Test
    public void testEndereco() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setIdade(25);
        pessoa.setNome("Igor Nelson da Cunha");
        pessoa.setCpf("15723979930");
        pessoa.setTelefone("44999988777");
        pessoa.setDataNascimento(LocalDate.of(1997, 10, 16));

        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setUf("PR");
        endereco.setCep("87070240");
        endereco.setNumero("500");
        endereco.setCidade("Maringá");
        endereco.setLogradouro("Rua Olímpico, Jardim Aurora");
        endereco.setPessoa(pessoa);

        pessoa.adicionarEndereco(endereco);

        assertThat(endereco.getId()).isEqualTo(1L);
        assertThat(endereco.getUf()).isEqualTo("PR");
        assertThat(endereco.getCep()).isEqualTo("87070240");
        assertThat(endereco.getNumero()).isEqualTo("500");
        assertThat(endereco.getCidade()).isEqualTo("Maringá");
        assertThat(endereco.getLogradouro()).isEqualTo("Rua Olímpico, Jardim Aurora");
        assertThat(endereco.getPessoa()).isEqualTo(pessoa);
    }
}
