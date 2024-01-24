package br.com.elotech.desafio.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.elotech.desafio.entity.Endereco;
import br.com.elotech.desafio.entity.Pessoa;
import br.com.elotech.desafio.service.EnderecoService;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {

    EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService){
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable Long id) {
        Optional<Endereco> pessoa = enderecoService.findById(id);
        return pessoa.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listagem")
    public ResponseEntity<Page<Endereco>> findByPessoa(@RequestParam Pessoa pessoa, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Endereco> enderecoPage = enderecoService.findByPessoa(pessoa, pageable);
        return ResponseEntity.ok(enderecoPage);
    }

    @PostMapping("/novo")
    public ResponseEntity<Endereco> saveEndereco(@RequestBody Endereco endereco) {
        Endereco novoEndereco = enderecoService.saveEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Endereco> updateEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        Optional<Endereco> updateEndereco = enderecoService.updateEndereco(id, endereco);

        return updateEndereco
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        enderecoService.deleteEndereco(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletar-todos")
    public ResponseEntity<Void> deleteTodosEnderecos() {
        enderecoService.deleteTodosEnderecos();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/integracao-cep")
    public ResponseEntity<Endereco> getEnderecoByCEP(@PathVariable String cep) {
        Endereco novoEndereco = enderecoService.createEnderecoByCEP(cep);
        return ResponseEntity.ok(novoEndereco);
    }

}
