package br.com.elotech.desafio.DTO;

import org.junit.jupiter.api.Test;

import br.com.elotech.desafio.entity.DTO.EnderecoDTO;

import static org.assertj.core.api.Assertions.assertThat;

public class EnderecoDTOTest {

    @Test
    public void testEmpenhoDTO(){
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setUf("PR");
        endereco.setCep("83606262");
        endereco.setNumero("984");
        endereco.setCidade("Campo Largo");
        endereco.setLogradouro("Rua Travessa Piotto, Ouro Verde");

        assertThat(endereco.getUf()).isEqualTo("PR");
        assertThat(endereco.getCep()).isEqualTo("83606262");
        assertThat(endereco.getNumero()).isEqualTo("984");
        assertThat(endereco.getCidade()).isEqualTo("Campo Largo");
        assertThat(endereco.getLogradouro()).isEqualTo("Rua Travessa Piotto, Ouro Verde");
    }
}
