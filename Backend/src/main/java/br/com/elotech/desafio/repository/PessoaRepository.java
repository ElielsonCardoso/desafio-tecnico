package br.com.elotech.desafio.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.elotech.desafio.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Page<Pessoa> findByNomeAndCpf(String nome, String cpf, Pageable pageable);

    boolean existsByCpf(String cpf);

}
