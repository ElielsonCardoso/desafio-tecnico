package br.com.elotech.desafio.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.elotech.desafio.entity.Endereco;
import br.com.elotech.desafio.repository.EnderecoRepository;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository){
        this.enderecoRepository = enderecoRepository;
    }

    public Optional<Endereco> findById(Long id){
        return enderecoRepository.findById(id);
    }

    public Endereco saveEndereco(Endereco endereco){
        return enderecoRepository.save(endereco);
    }

    public Optional<Endereco> updateEndereco(Long id, Endereco endereco){
        Optional<Endereco> findEndereco = enderecoRepository.findById(id);

        if (findEndereco.isPresent()){
            Endereco enderecoAtualizado = findEndereco.get();

            enderecoAtualizado.setCep(endereco.getCep());
            enderecoAtualizado.setLogradouro(endereco.getLogradouro());
            enderecoAtualizado.setNumero(endereco.getNumero());
            enderecoAtualizado.setCidade(endereco.getCidade());
            enderecoAtualizado.setUf(endereco.getUf());

            enderecoRepository.save(enderecoAtualizado);

            return Optional.of(enderecoAtualizado);
        } else {
            return Optional.empty();
        }
    }

    public void deleteEndereco(Long id){
        enderecoRepository.deleteById(id);
    }

    public void deleteTodosEnderecos(){ enderecoRepository.deleteAll(); }

}
