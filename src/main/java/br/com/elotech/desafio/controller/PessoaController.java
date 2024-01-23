package br.com.elotech.desafio.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import br.com.elotech.desafio.entity.EnderecoDTO;
import br.com.elotech.desafio.entity.Pessoa;
import br.com.elotech.desafio.service.PessoaService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService){
        this.pessoaService = pessoaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaService.findById(id);
        return pessoa.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pessoas")
    public ResponseEntity<Page<Pessoa>> findByNomeAndCpf(@RequestParam String nome,
            @RequestParam String cpf, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Pessoa> pessoas = pessoaService.findByNomeAndCpf(nome, cpf, pageable);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/quantidade")
    public ResponseEntity<Long> getQuantidadePessoas() {
        long quantidadePessoas = pessoaService.getQuantidadePessoas();
        return ResponseEntity.ok(quantidadePessoas);
    }

    @PostMapping("/novo")
    public ResponseEntity<Pessoa> savePessoa(@RequestBody Pessoa pessoa) {
        Pessoa novaPessoa = pessoaService.savePessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Optional<Pessoa> updatePessoa = pessoaService.updatePessoa(id, pessoa);

        return updatePessoa
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/adicionarEndereco")
    public ResponseEntity<Pessoa> adicionarEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        Pessoa pessoa = pessoaService.adicionarEndereco(id, enderecoDTO);
        return ResponseEntity.ok(pessoa);
    }
}
