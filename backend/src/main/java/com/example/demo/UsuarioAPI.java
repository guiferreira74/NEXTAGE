package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioAPI {

    @Autowired
    private UsuarioDAO usuarioDAO;

    // CADASTRO
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {

        Optional<Usuario> usuarioExistente =
                usuarioDAO.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("E-mail já cadastrado");
        }

        if (usuario.getTipo() == null || usuario.getTipo().isBlank()) {
            usuario.setTipo("cliente");
        }

        Usuario usuarioSalvo = usuarioDAO.save(usuario);

        return ResponseEntity.ok(usuarioSalvo);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {

        Optional<Usuario> usuarioEncontrado =
                usuarioDAO.findByEmailAndSenha(
                        usuario.getEmail(),
                        usuario.getSenha()
                );

        if (usuarioEncontrado.isPresent()) {
            return ResponseEntity.ok(usuarioEncontrado.get());
        }

        return ResponseEntity
                .status(401)
                .body("E-mail ou senha inválidos");
    }
}