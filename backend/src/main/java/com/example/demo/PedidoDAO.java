package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoDAO extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByUsuarioId(Integer usuarioId);

}