package br.com.erikdev.sistema_evento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventoDto(
        @NotBlank
        String nome,
        @NotNull
        LocalDateTime dataHorario,
        @NotBlank
        String localizacao,
        @NotNull
        Integer capacidadeMaxima,
        Integer vagasDisponiveis) {
}
