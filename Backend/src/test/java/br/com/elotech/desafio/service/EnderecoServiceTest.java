package br.com.elotech.desafio.service;

import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.elotech.desafio.entity.Endereco;
import br.com.elotech.desafio.entity.Pessoa;
import br.com.elotech.desafio.repository.EnderecoRepository;

public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    Endereco endereco;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        endereco = new Endereco();
        endereco.setId(1L);
        endereco.setUf("PR");
        endereco.setCep("87070240");
        endereco.setNumero("500");
        endereco.setCidade("Maringá");
        endereco.setLogradouro("Rua Olímpico, Jardim Aurora");
    }

    @Test
    public void testFindById(){
        when(enderecoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(endereco));

        Optional<Endereco> findEndereco = enderecoService.findById(1L);

        assertThat(findEndereco.isPresent()).isTrue();

        Endereco enderecoEncontrado = findEndereco.get();

        assertThat(enderecoEncontrado).isNotNull();
        assertThat(enderecoEncontrado.getId()).isEqualTo(1L);
        assertThat(enderecoEncontrado.getUf()).isEqualTo("PR");
        assertThat(enderecoEncontrado.getCep()).isEqualTo("87070240");
        assertThat(enderecoEncontrado.getNumero()).isEqualTo("500");
        assertThat(enderecoEncontrado.getCidade()).isEqualTo("Maringá");
        assertThat(enderecoEncontrado.getLogradouro()).isEqualTo("Rua Olímpico, Jardim Aurora");
    }

    @Test
    public void testFindByPessoa(){
        PageRequest pageRequest = PageRequest.of(0, 20);

        Pessoa pessoa = mock(Pessoa.class);

        when(enderecoRepository.findByPessoa(ArgumentMatchers.any(), ArgumentMatchers.any())).
                thenReturn(new PageImpl<>(Lists.newArrayList(endereco)));

        Page<Endereco> enderecoPage = enderecoService.findByPessoa(pessoa, pageRequest);

        assertThat(enderecoPage.getContent().contains(endereco)).isTrue();

        verify(enderecoRepository, times(1)).findByPessoa(pessoa, pageRequest);
    }

    @Test
    public void testSaveEndereco(){
        when(enderecoRepository.save(ArgumentMatchers.any())).thenReturn(endereco);

        Endereco saveEndereco = enderecoService.saveEndereco(endereco);

        assertThat(saveEndereco).isNotNull();
        assertThat(saveEndereco.getId()).isEqualTo(1L);
        assertThat(saveEndereco.getUf()).isEqualTo("PR");
        assertThat(saveEndereco.getCep()).isEqualTo("87070240");
        assertThat(saveEndereco.getNumero()).isEqualTo("500");
        assertThat(saveEndereco.getCidade()).isEqualTo("Maringá");
        assertThat(saveEndereco.getLogradouro()).isEqualTo("Rua Olímpico, Jardim Aurora");
    }

    @Test
    public void testUpdateEndereco(){
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        Optional<Endereco> updateEndereco = enderecoService.updateEndereco(1L, endereco);
        Endereco novoEndereco = updateEndereco.get();

        assertThat(novoEndereco).isNotNull();
        assertThat(novoEndereco.getId()).isEqualTo(1L);
        assertThat(novoEndereco.getUf()).isEqualTo("PR");
        assertThat(novoEndereco.getCep()).isEqualTo("87070240");
        assertThat(novoEndereco.getNumero()).isEqualTo("500");
        assertThat(novoEndereco.getCidade()).isEqualTo("Maringá");
        assertThat(novoEndereco.getLogradouro()).isEqualTo("Rua Olímpico, Jardim Aurora");

        verify(enderecoRepository, times(1)).save(endereco);

        updateEndereco = enderecoService.updateEndereco(2L, endereco);
        assertThat(updateEndereco).isEqualTo(Optional.empty());
    }

    @Test
    void testDeleteEndereco() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        enderecoService.deleteEndereco(1L);

        verify(enderecoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTodosEndereco() {
        Endereco enderecoComercial = new Endereco();
        enderecoComercial.setId(2L);
        enderecoComercial.setUf("PR");
        enderecoComercial.setCep("87025540");
        enderecoComercial.setNumero("7984");
        enderecoComercial.setCidade("Maringá");
        enderecoComercial.setLogradouro("Rua Poeta Carlos Drumond de Andrade, Parque Avenida");

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.findById(2L)).thenReturn(Optional.of(enderecoComercial));

        enderecoService.deleteTodosEnderecos();

        verify(enderecoRepository, times(1)).deleteAll();
    }

    @Test
    void testCreateEnderecoByCEP(){
        Endereco novoEndereco = enderecoService.createEnderecoByCEP("92420460");
        assertThat(novoEndereco).isNotNull();
        assertThat(novoEndereco.getUf()).isEqualTo("RS");
        assertThat(novoEndereco.getCep()).isEqualTo("92420460");
        assertThat(novoEndereco.getCidade()).isEqualTo("Canoas");
        assertThat(novoEndereco.getLogradouro()).isEqualTo("Rua E");

        assertThrows(IllegalArgumentException.class, () -> {
            enderecoService.createEnderecoByCEP("123");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            enderecoService.createEnderecoByCEP("99999999");
        });
    }
}
