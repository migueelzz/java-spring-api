package br.fatec.lins.apiRest.controller;

import br.fatec.lins.apiRest.modelo.Produto;
import br.fatec.lins.apiRest.record.ProdutoDTO;
import br.fatec.lins.apiRest.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProdutoController {
    @Autowired
    ProdutoRepository repositoryProduto;

    @PostMapping("/produtos")
    public ResponseEntity<Produto> salvarProduto(@RequestBody ProdutoDTO dto) {
        var produtoModelo = new Produto();
        BeanUtils.copyProperties(dto, produtoModelo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(repositoryProduto.save(produtoModelo));
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> getAllProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body((repositoryProduto.findAll()));
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<Object> getProdutoByID(@PathVariable(value="id") UUID id) {
        Optional<Produto> produto = repositoryProduto.findById(id);
        if(produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produto.get());
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<Object> putProduto(@PathVariable(value="id") UUID id,
                                             @RequestBody ProdutoDTO dto) {
        Optional<Produto> produto = repositoryProduto.findById(id);

        if(produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        BeanUtils.copyProperties(dto, produto.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(repositoryProduto.save(produto.get()));
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Object> deleteProduto(@PathVariable(value="id") UUID id) {
        Optional<Produto> produto = repositoryProduto.findById(id);

        if(produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        repositoryProduto.delete(produto.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso!");
    }

}
