package com.proyecto.Foro_Hub.domain.topico;

import com.proyecto.Foro_Hub.dto.DatosActualizarTopico;
import com.proyecto.Foro_Hub.infra.security.ValidacionException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String titulo;
        private String mensaje;
        private LocalDateTime fechaCreacion;
        private String status;
        private String autor;
        private String curso;
}
