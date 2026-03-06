package com.proyecto.Foro_Hub.repository;


import com.proyecto.Foro_Hub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);
}
