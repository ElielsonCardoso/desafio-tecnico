package br.com.elotech.desafio.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.elotech.desafio.entity.Pessoa;
import br.com.elotech.desafio.entity.Endereco;
import br.com.elotech.desafio.entity.EnderecoDTO;
import br.com.elotech.desafio.repository.PessoaRepository;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Optional<Pessoa> findById(Long id) {
        return pessoaRepository.findById(id);
    }

    public Page<Pessoa> findByNomeAndCpf(String nome, String cpf, Pageable pageable) {
        return pessoaRepository.findByNomeAndCpf(nome, cpf, pageable);
    }

    public Long getQuantidadePessoas() {
        return pessoaRepository.count();
    }

    private void validaPessoa(Pessoa pessoa) {
        if (pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new IllegalArgumentException("CPF informado já está cadastrado!");
        }

        if (pessoa.getTelefone().length() != 11) {
            throw new IllegalArgumentException("O telefone deve possuir 11 números!");
        }

        if (pessoa.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de nascimento não pode ser maior que a data atual!");
        }
    }

    public Pessoa savePessoa(Pessoa pessoa) {
        this.validaPessoa(pessoa);
        return pessoaRepository.save(pessoa);
    }

    public Optional<Pessoa> updatePessoa(Long id, Pessoa pessoa) {
        Optional<Pessoa> findPessoa = pessoaRepository.findById(id);

        if (findPessoa.isPresent()) {
            Pessoa pessoaAtualizada = findPessoa.get();

            pessoaAtualizada.setNome(pessoa.getNome());
            pessoaAtualizada.setCpf(pessoa.getCpf());
            pessoaAtualizada.setDataNascimento(pessoa.getDataNascimento());
            pessoaAtualizada.setTelefone(pessoa.getTelefone());
            pessoaAtualizada.setIdade(pessoa.getIdade());
            pessoaAtualizada.setEnderecos(pessoa.getEnderecos());

            pessoaRepository.save(pessoaAtualizada);

            return Optional.of(pessoaAtualizada);
        } else {
            throw new IllegalArgumentException("Não existe pessoa com ID " + id + "!");
        }
    }

    public void deletePessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

    public Pessoa adicionarEndereco(Long pessoaId, EnderecoDTO enderecoDTO) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);

        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();

            Endereco novoEndereco = new Endereco();
            novoEndereco.setCep(enderecoDTO.getCep());
            novoEndereco.setLogradouro(enderecoDTO.getLogradouro());
            novoEndereco.setNumero(enderecoDTO.getNumero());
            novoEndereco.setCidade(enderecoDTO.getCidade());
            novoEndereco.setUf(enderecoDTO.getUf());

            pessoa.adicionarEndereco(novoEndereco);

            return pessoaRepository.save(pessoa);
        }else {
            throw new IllegalArgumentException("Não existe pessoa com ID " + pessoaId + "!");
        }
    }

}