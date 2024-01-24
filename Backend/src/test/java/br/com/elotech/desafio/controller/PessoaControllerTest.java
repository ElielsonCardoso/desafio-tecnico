package br.com.elotech.desafio.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.elotech.desafio.DTO.EnderecoDTO;
import br.com.elotech.desafio.entity.Endereco;
import br.com.elotech.desafio.service.PessoaService;
import br.com.elotech.desafio.entity.Pessoa;

import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.springframework.http.ResponseEntity;

public class PessoaControllerTest {
    @InjectMocks
    private PessoaController pessoaController;

    @Mock
    private PessoaService pessoaService;

    Pessoa pessoa;

    Endereco enderecoResidencial;

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

        enderecoResidencial = new Endereco();
        enderecoResidencial.setId(1L);
        enderecoResidencial.setUf("PR");
        enderecoResidencial.setCep("87070240");
        enderecoResidencial.setNumero("500");
        enderecoResidencial.setPessoa(pessoa);
        enderecoResidencial.setCidade("Maringá");
        enderecoResidencial.setLogradouro("Rua Olímpico, Jardim Aurora");

        pessoa.adicionarEndereco(enderecoResidencial);
    }

    @Test
    void testFindById() {
        when(pessoaService.findById(1L)).thenReturn(Optional.of(pessoa));

        ResponseEntity<Pessoa> responseEntity = pessoaController.findById(1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void testFindByNomeAndCpf() {
        when(pessoaService.findByNomeAndCpf("Manuela Sara dos Santos", "22781450960", PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(pessoa)));

        ResponseEntity<Page<Pessoa>> responseEntity = pessoaController.
                findByNomeAndCpf("Manuela Sara dos Santos", "22781450960", 0);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getContent().size()).isEqualTo(1);
    }

    @Test
    void testGetQuantidadePessoas() {
        when(pessoaService.getQuantidadePessoas()).thenReturn(5L);

        ResponseEntity<Long> responseEntity = pessoaController.getQuantidadePessoas();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(5L);
    }

    @Test
    void testSavePessoa() {
        when(pessoaService.savePessoa(pessoa)).thenReturn(pessoa);

        ResponseEntity<Pessoa> responseEntity = pessoaController.savePessoa(pessoa);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void testUpdatePessoa() {
        when(pessoaService.updatePessoa(1L, pessoa)).thenReturn(Optional.of(pessoa));

        ResponseEntity<Pessoa> responseEntity = pessoaController.updatePessoa(1L, pessoa);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void testDeletePessoa() {
        ResponseEntity<Void> responseEntity = pessoaController.deletePessoa(1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void testAdicionarEndereco() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        when(pessoaService.adicionarEndereco(1L, enderecoDTO)).thenReturn(pessoa);

        ResponseEntity<Pessoa> responseEntity = pessoaController.adicionarEndereco(1L, enderecoDTO);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
    }

}
