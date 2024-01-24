package br.com.elotech.desafio.entity;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.elotech.desafio.entity.DTO.EnderecoDTO;

import static org.assertj.core.api.Assertions.assertThat;

public class EnderecoTest {

    Pessoa pessoa;

    Endereco endereco;

    EnderecoDTO enderecoDTO;

    @BeforeEach
    void setup() {
        pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setIdade(25);
        pessoa.setNome("Igor Nelson da Cunha");
        pessoa.setCpf("15723979930");
        pessoa.setTelefone("44999988777");
        pessoa.setDataNascimento(LocalDate.of(1997, 10, 16));

        endereco = new Endereco();
        endereco.setId(1L);
        endereco.setUf("PR");
        endereco.setCep("87070240");
        endereco.setNumero("500");
        endereco.setCidade("Maringá");
        endereco.setLogradouro("Rua Olímpico, Jardim Aurora");
        endereco.setPessoa(pessoa);

        enderecoDTO = new EnderecoDTO();
        enderecoDTO.setUf("PR");
        enderecoDTO.setCep("87070240");
        enderecoDTO.setNumero("500");
        enderecoDTO.setCidade("Maringá");
        enderecoDTO.setLogradouro("Rua Olímpico, Jardim Aurora");
        enderecoDTO.setPessoa(pessoa);

        pessoa.adicionarEndereco(endereco);
    }

    @Test
    public void testEndereco() {
        assertThat(endereco.getId()).isEqualTo(1L);
        assertThat(endereco.getUf()).isEqualTo("PR");
        assertThat(endereco.getCep()).isEqualTo("87070240");
        assertThat(endereco.getNumero()).isEqualTo("500");
        assertThat(endereco.getCidade()).isEqualTo("Maringá");
        assertThat(endereco.getLogradouro()).isEqualTo("Rua Olímpico, Jardim Aurora");
        assertThat(endereco.getPessoa()).isEqualTo(pessoa);
    }

    @Test
    public void testEnderecoDTO(){
        assertThat(enderecoDTO.getUf()).isEqualTo("PR");
        assertThat(enderecoDTO.getCep()).isEqualTo("87070240");
        assertThat(enderecoDTO.getNumero()).isEqualTo("500");
        assertThat(enderecoDTO.getCidade()).isEqualTo("Maringá");
        assertThat(enderecoDTO.getLogradouro()).isEqualTo("Rua Olímpico, Jardim Aurora");
        assertThat(enderecoDTO.getPessoa()).isEqualTo(pessoa);
    }

}
