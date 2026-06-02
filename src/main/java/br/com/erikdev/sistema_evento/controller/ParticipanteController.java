package br.com.erikdev.sistema_evento.controller;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import br.com.erikdev.sistema_evento.dto.EventoDto;
import br.com.erikdev.sistema_evento.dto.ParticipanteDto;
import br.com.erikdev.sistema_evento.exception.BadRequestException;
import br.com.erikdev.sistema_evento.service.EventoService;
import br.com.erikdev.sistema_evento.service.ParticipanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/participante")
@RequiredArgsConstructor
@Validated
public class ParticipanteController {

    private final ParticipanteService participanteService;

    @PostMapping
    public ResponseEntity<ParticipanteEntity> criarParticipante(@RequestBody @Valid ParticipanteDto dto) throws BadRequestException {
        ParticipanteEntity participante = participanteService.criarParticipante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(participante);
    }
}
