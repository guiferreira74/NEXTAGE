package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoDAO extends JpaRepository<ItemPedido, Integer> {

    List<ItemPedido> findByPedidoId(Integer pedidoId);

}