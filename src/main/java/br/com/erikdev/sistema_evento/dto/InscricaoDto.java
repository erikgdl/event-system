package br.com.erikdev.sistema_evento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record InscricaoDto(
        @NotNull
        UUID participanteId,
        @NotNull
        UUID eventoId) {
}
