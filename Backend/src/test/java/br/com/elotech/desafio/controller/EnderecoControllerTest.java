package br.com.elotech.desafio.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import br.com.elotech.desafio.entity.Endereco;
import br.com.elotech.desafio.entity.Pessoa;
import br.com.elotech.desafio.service.EnderecoService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EnderecoControllerTest {

    @InjectMocks
    private EnderecoController enderecoController;

    @Mock
    private EnderecoService enderecoService;

    Pessoa pessoa;

    Endereco endereco;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
        endereco.setPessoa(pessoa);
        endereco.setCidade("Maringá");
        endereco.setLogradouro("Rua Olímpico, Jardim Aurora");

        pessoa.adicionarEndereco(endereco);
    }

    @Test
    void testFindById() {
        when(enderecoService.findById(1L)).thenReturn(Optional.of(endereco));

        ResponseEntity<Endereco> responseEntity = enderecoController.findById(1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void testFindByPessoa() {
        when(enderecoService.findByPessoa(pessoa, PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(endereco)));

        ResponseEntity<Page<Endereco>> responseEntity = enderecoController.
                findByPessoa(pessoa, 0);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getContent().size()).isEqualTo(1);
    }

    @Test
    void testSaveEndereco() {
        when(enderecoService.saveEndereco(endereco)).thenReturn(endereco);

        ResponseEntity<Endereco> responseEntity = enderecoController.saveEndereco(endereco);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void testUpdateEndereco() {
        when(enderecoService.updateEndereco(1L, endereco)).thenReturn(Optional.of(endereco));

        ResponseEntity<Endereco> responseEntity = enderecoController.updateEndereco(1L, endereco);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void testDeleteEndereco() {
        ResponseEntity<Void> responseEntity = enderecoController.deleteEndereco(1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void testDeleteTodosEndereco() {
        ResponseEntity<Void> responseEntity = enderecoController.deleteTodosEnderecos();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void testGetEnderecoByCEP(){
        when(enderecoService.createEnderecoByCEP("72507411")).thenReturn(endereco);
        ResponseEntity<Endereco> responseEntity = enderecoController.getEnderecoByCEP("72507411");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
    }

}
