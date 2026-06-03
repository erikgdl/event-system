package br.com.erikdev.sistema_evento.controller;

import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import br.com.erikdev.sistema_evento.dto.ParticipanteDto;
import br.com.erikdev.sistema_evento.exception.BadRequestException;
import br.com.erikdev.sistema_evento.exception.NotFoundException;
import br.com.erikdev.sistema_evento.service.ParticipanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/participantes")
@RequiredArgsConstructor
@Validated
public class ParticipanteController {

    private final ParticipanteService participanteService;

    @PostMapping
    public ResponseEntity<ParticipanteEntity> criarParticipante(@RequestBody @Valid ParticipanteDto dto) throws BadRequestException {
        ParticipanteEntity participante = participanteService.criarParticipante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(participante);
    }

    @GetMapping
    public ResponseEntity<List<ParticipanteEntity>> listarParticipantes() {
        return ResponseEntity.ok(participanteService.listarParticipantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteEntity> buscarParticipante(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(participanteService.buscarParticipantePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteEntity> atualizarParticipante(
            @PathVariable UUID id,
            @RequestBody @Valid ParticipanteDto dto
    ) throws NotFoundException, BadRequestException {
        return ResponseEntity.ok(participanteService.atualizarParticipante(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarParticipante(@PathVariable UUID id) throws NotFoundException {
        participanteService.deletarParticipante(id);
        return ResponseEntity.noContent().build();
    }
}
