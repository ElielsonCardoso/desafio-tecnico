package br.com.elotech.desafio.service;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.util.Lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.elotech.desafio.DTO.EnderecoDTO;
import br.com.elotech.desafio.entity.Endereco;
import br.com.elotech.desafio.entity.Pessoa;
import br.com.elotech.desafio.repository.PessoaRepository;

public class PessoaServiceTest {
    @InjectMocks
    PessoaService pessoaService;

    @Mock
    PessoaRepository pessoaRepository;

    Pessoa pessoa;

    Endereco enderecoResidencial;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
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
    public void testFindById(){
        when(pessoaRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> findPessoa = pessoaService.findById(1L);

        assertThat(findPessoa.isPresent()).isTrue();

        Pessoa pessoaEncontrada = findPessoa.get();

        assertThat(pessoaEncontrada.getId()).isEqualTo(1L);
        assertThat(pessoaEncontrada.getIdade()).isEqualTo(25);
        assertThat(pessoaEncontrada.getNome()).isEqualTo("Igor Nelson da Cunha");
        assertThat(pessoaEncontrada.getCpf()).isEqualTo("15723979930");
        assertThat(pessoaEncontrada.getTelefone()).isEqualTo("44999988777");
        assertThat(pessoaEncontrada.getEnderecos().stream().count()).isEqualTo(1);
    }

    @Test
    public void testFindByNomeAndCpf(){
        PageRequest pageRequest = PageRequest.of(0, 20);

        when(pessoaRepository.findByNomeAndCpf(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).
                thenReturn(new PageImpl<>(Lists.newArrayList(pessoa)));

        Page<Pessoa> pessoaPage = pessoaService.findByNomeAndCpf("Teste", "88654940000", pageRequest);

        assertThat(pessoaPage.getContent().contains(pessoa)).isTrue();

        verify(pessoaRepository, times(1)).findByNomeAndCpf("Teste", "88654940000", pageRequest);
    }

    @Test
    public void testGetQuantidadePessoas(){
        when(pessoaRepository.count()).thenReturn(5L);
        Long quantidadePessoas = pessoaService.getQuantidadePessoas();
        assertThat(quantidadePessoas).isEqualTo(5L);
    }

    @Test
    public void testSavePessoa(){
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa novaPessoa = pessoaService.savePessoa(pessoa);
        assertThat(novaPessoa).isNotNull();
        assertThat(novaPessoa.getId()).isEqualTo(1L);
        assertThat(novaPessoa.getIdade()).isEqualTo(25);
        assertThat(novaPessoa.getNome()).isEqualTo("Igor Nelson da Cunha");
        assertThat(novaPessoa.getCpf()).isEqualTo("15723979930");
        assertThat(novaPessoa.getTelefone()).isEqualTo("44999988777");
        assertThat(novaPessoa.getEnderecos().stream().count()).isEqualTo(1);

        novaPessoa.setTelefone("123");

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.savePessoa(novaPessoa);
        });

        novaPessoa.setTelefone("44999988777");
        novaPessoa.removerEndereco(enderecoResidencial);

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.savePessoa(novaPessoa);
        });

        novaPessoa.adicionarEndereco(enderecoResidencial);

        LocalDate dataAtual = LocalDate.now();
        novaPessoa.setDataNascimento(dataAtual.plusDays(1));

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.savePessoa(novaPessoa);
        });

        pessoa.setDataNascimento(LocalDate.of(1997, 10, 16));

        when(pessoaRepository.existsByCpf(pessoa.getCpf())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.savePessoa(novaPessoa);
        });
    }

    @Test
    public void testUpdatePessoa(){
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> updatePessoa = pessoaService.updatePessoa(1L, pessoa);

        assertThat(updatePessoa.isPresent()).isTrue();

        Pessoa novaPessoa = updatePessoa.get();

        assertThat(novaPessoa.getId()).isEqualTo(1L);
        assertThat(novaPessoa.getIdade()).isEqualTo(25);
        assertThat(novaPessoa.getNome()).isEqualTo("Igor Nelson da Cunha");
        assertThat(novaPessoa.getCpf()).isEqualTo("15723979930");
        assertThat(novaPessoa.getTelefone()).isEqualTo("44999988777");
        assertThat(novaPessoa.getEnderecos().stream().count()).isEqualTo(1);

        verify(pessoaRepository, times(1)).save(pessoa);

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.updatePessoa(2L, novaPessoa);
        });
    }

    @Test
    void testDeletePessoa() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        pessoaService.deletePessoa(1L);

        verify(pessoaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAdicionarEndereco() {
        EnderecoDTO novoEndereco = new EnderecoDTO();
        novoEndereco.setUf("PR");
        novoEndereco.setCep("87025540");
        novoEndereco.setNumero("7984");
        novoEndereco.setCidade("Maringá");
        novoEndereco.setLogradouro("Rua Poeta Carlos Drumond de Andrade, Parque Avenida");

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa pessoaNovoEndereco = pessoaService.adicionarEndereco(1L, novoEndereco);
        assertThat(pessoaNovoEndereco.getEnderecos().stream().count()).isEqualTo(2);

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.adicionarEndereco(2L, novoEndereco);
        });
    }

}
