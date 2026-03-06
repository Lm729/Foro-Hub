package com.proyecto.Foro_Hub.controller;

import com.proyecto.Foro_Hub.domain.topico.*;
import com.proyecto.Foro_Hub.dto.DatosActualizarTopico;
import com.proyecto.Foro_Hub.dto.DatosRegistroTopico;
import com.proyecto.Foro_Hub.infra.security.ValidacionException;
import com.proyecto.Foro_Hub.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository repository;

    public TopicoController(TopicoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos) {

        if (repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Tópico duplicado");
        }

        Topico topico = new Topico(
                null,
                datos.titulo(),
                datos.mensaje(),
                LocalDateTime.now(),
                "ACTIVO",
                datos.autor(),
                datos.curso()
        );

        repository.save(topico);

        return ResponseEntity.status(201).body(topico);
    }

    @GetMapping
    public ResponseEntity<List<Topico>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity detalle(@PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizar(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos) {

        Optional<Topico> optionalTopico = repository.findById(id);

        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = optionalTopico.get();

        String nuevoTitulo = datos.titulo() != null ? datos.titulo() : topico.getTitulo();
        String nuevoMensaje = datos.mensaje() != null ? datos.mensaje() : topico.getMensaje();

        if (repository.existsByTituloAndMensajeAndIdNot(nuevoTitulo, nuevoMensaje, id)) {
            throw new ValidacionException("Tópico duplicado");
        }

        if (datos.titulo() != null && datos.titulo().equals(topico.getTitulo())) {
            throw new ValidacionException("El título ya es igual al actual");
        }

        if (datos.mensaje() != null && datos.mensaje().equals(topico.getMensaje())) {
            throw new ValidacionException("El mensaje ya es igual al actual");
        }

        if (datos.titulo() != null) {
            topico.setTitulo(datos.titulo());
        }

        if (datos.mensaje() != null) {
            topico.setMensaje(datos.mensaje());
        }

        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}