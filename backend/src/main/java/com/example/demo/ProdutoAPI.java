package com.example.demo;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// Imports do Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
public class ProdutoAPI {

    @Autowired
    private ProdutoDAO dao;

    // LISTAR TODOS OS PRODUTOS
    @GetMapping
    public List<Produto> obterTodos() {
        return dao.findAll();
    }

    // BUSCAR PRODUTO POR ID
    @GetMapping("/{id}")
    public Produto obterPorId(@PathVariable Integer id) {
        return dao.findById(id).orElseThrow();
    }

    // CADASTRAR PRODUTO
    @Operation(
        summary = "Criar novo produto",
        description = "Cadastra um novo produto na loja."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Produto criado com sucesso"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados da requisição inválidos"
        )
    })
    @PostMapping
    public Produto inserir(
            @RequestParam String nome,
            @RequestParam int quantidade,
            @RequestParam String descricao,
            @RequestParam double preco,
            @RequestParam String categoria,
            @RequestParam MultipartFile imagem
    ) throws IOException {

        Produto p = new Produto();

        p.setNome(nome);
        p.setQuantidade(quantidade);
        p.setDescricao(descricao);
        p.setPreco(preco);
        p.setCategoria(categoria);

        if (UploadUtil.fazerUploadImagem(imagem)) {
            p.setImagem(imagem.getOriginalFilename());
        }

        return dao.save(p);
    }

    // ATUALIZAR PRODUTO
    @PutMapping("/{id}")
    public Produto atualizar(
            @PathVariable Integer id,
            @RequestParam String nome,
            @RequestParam int quantidade,
            @RequestParam String descricao,
            @RequestParam double preco,
            @RequestParam String categoria,
            @RequestParam(required = false) MultipartFile imagem
    ) throws IOException {

        Produto existente = dao.findById(id).orElseThrow();

        existente.setNome(nome);
        existente.setQuantidade(quantidade);
        existente.setDescricao(descricao);
        existente.setPreco(preco);
        existente.setCategoria(categoria);

        if (imagem != null && !imagem.isEmpty()) {

            if (UploadUtil.fazerUploadImagem(imagem)) {
                existente.setImagem(imagem.getOriginalFilename());
            }
        }

        return dao.save(existente);
    }

    // EXCLUIR PRODUTO
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        dao.deleteById(id);
    }
}


// Controller para servir a página inicial
@Controller
class HomeController {

    @GetMapping("/")
    public String index() {

        // O Spring procura esse arquivo em:
        // src/main/resources/static
        return "pagina_produto.html";
    }
}