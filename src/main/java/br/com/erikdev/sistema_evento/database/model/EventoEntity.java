package br.com.erikdev.sistema_evento.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "evento")
@Entity
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @Column(name = "data_horario")
    private LocalDateTime dataHorario;

    private String localizacao;

    @Column(name = "capacidade_maxima")
    private Integer capacidadeMaxima;

    @Column(name = "vagas_disponiveis")
    private Integer vagasDisponiveis;
}
