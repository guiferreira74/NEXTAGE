package com.example.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoAPI {

    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private ItemPedidoDAO itemPedidoDAO;

    @Autowired
    private ProdutoDAO produtoDAO;


    // CADASTRAR UM NOVO PEDIDO
    @PostMapping
    @Transactional
    public Pedido criarPedido(@RequestBody Map<String, Object> dados) {

        Integer usuarioId = Integer.valueOf(
            dados.get("usuarioId").toString()
        );

        String formaPagamento =
            dados.get("formaPagamento").toString();

        double total = Double.parseDouble(
            dados.get("total").toString()
        );


        // RECEBE OS PRODUTOS DO CARRINHO
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> produtos =
            (List<Map<String, Object>>) dados.get("produtos");


        // PRIMEIRO VERIFICA SE EXISTE ESTOQUE SUFICIENTE
        for (Map<String, Object> produtoCarrinho : produtos) {

            Integer produtoId = Integer.valueOf(
                produtoCarrinho.get("id").toString()
            );

            int quantidadeComprada = Integer.parseInt(
                produtoCarrinho.get("quantidade").toString()
            );

            Produto produtoBanco = produtoDAO
                .findById(produtoId)
                .orElseThrow(
                    () -> new RuntimeException(
                        "Produto não encontrado: " + produtoId
                    )
                );

            if (quantidadeComprada <= 0) {
                throw new RuntimeException(
                    "Quantidade inválida para o produto: "
                    + produtoBanco.getNome()
                );
            }

            if (produtoBanco.getQuantidade() < quantidadeComprada) {
                throw new RuntimeException(
                    "Estoque insuficiente para o produto: "
                    + produtoBanco.getNome()
                );
            }
        }


        // CRIA O PEDIDO
        Pedido pedido = new Pedido();

        pedido.setUsuarioId(usuarioId);
        pedido.setData(LocalDateTime.now());
        pedido.setTotal(total);
        pedido.setFormaPagamento(formaPagamento);

        // SALVA O PEDIDO PARA GERAR O ID
        Pedido pedidoSalvo = pedidoDAO.save(pedido);


        // SALVA OS ITENS E DIMINUI O ESTOQUE
        for (Map<String, Object> produtoCarrinho : produtos) {

            Integer produtoId = Integer.valueOf(
                produtoCarrinho.get("id").toString()
            );

            int quantidadeComprada = Integer.parseInt(
                produtoCarrinho.get("quantidade").toString()
            );

            Produto produtoBanco = produtoDAO
                .findById(produtoId)
                .orElseThrow();


            // CRIA O ITEM DO PEDIDO
            ItemPedido item = new ItemPedido();

            item.setPedidoId(pedidoSalvo.getId());
            item.setProdutoId(produtoBanco.getId());
            item.setNomeProduto(produtoBanco.getNome());
            item.setQuantidade(quantidadeComprada);
            item.setPreco(produtoBanco.getPreco());

            itemPedidoDAO.save(item);


            // DIMINUI O ESTOQUE
            int novoEstoque =
                produtoBanco.getQuantidade() - quantidadeComprada;

            produtoBanco.setQuantidade(novoEstoque);

            produtoDAO.save(produtoBanco);
        }


        return pedidoSalvo;
    }


    // BUSCAR TODOS OS PEDIDOS DE UM USUÁRIO
    @GetMapping("/usuario/{usuarioId}")
    public List<Map<String, Object>> buscarPedidosUsuario(
            @PathVariable Integer usuarioId) {

        List<Pedido> pedidos =
            pedidoDAO.findByUsuarioId(usuarioId);

        List<Map<String, Object>> resultado =
            new ArrayList<>();


        for (Pedido pedido : pedidos) {

            Map<String, Object> pedidoCompleto =
                new HashMap<>();

            pedidoCompleto.put(
                "id",
                pedido.getId()
            );

            pedidoCompleto.put(
                "data",
                pedido.getData()
            );

            pedidoCompleto.put(
                "total",
                pedido.getTotal()
            );

            pedidoCompleto.put(
                "formaPagamento",
                pedido.getFormaPagamento()
            );


            List<ItemPedido> itens =
                itemPedidoDAO.findByPedidoId(
                    pedido.getId()
                );


            List<Map<String, Object>> produtos =
                new ArrayList<>();


            for (ItemPedido item : itens) {

                Map<String, Object> produto =
                    new HashMap<>();

                produto.put(
                    "id",
                    item.getProdutoId()
                );

                produto.put(
                    "nome",
                    item.getNomeProduto()
                );

                produto.put(
                    "quantidade",
                    item.getQuantidade()
                );

                produto.put(
                    "preco",
                    item.getPreco()
                );

                produtos.add(produto);
            }


            pedidoCompleto.put(
                "produtos",
                produtos
            );

            resultado.add(pedidoCompleto);
        }

        return resultado;
    }
}