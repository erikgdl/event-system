package br.com.erikdev.sistema_evento.database.model;

import br.com.erikdev.sistema_evento.enums.InscricaoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "inscricao")
@Entity
public class InscricaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "participante_id", nullable = false)
    private ParticipanteEntity participante;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private EventoEntity evento;

    private LocalDateTime dataInscricao;

    @Enumerated(EnumType.STRING)
    private InscricaoEnum status;

}