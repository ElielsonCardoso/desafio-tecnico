package br.com.elotech.desafio.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.elotech.desafio.entity.Endereco;
import br.com.elotech.desafio.entity.Pessoa;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Page<Endereco> findByPessoa(Pessoa pessoa, Pageable pageable);

}
