package br.com.erikdev.sistema_evento.controller;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import br.com.erikdev.sistema_evento.dto.EventoDto;
import br.com.erikdev.sistema_evento.service.EventoService;
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
@RequestMapping("v1/evento")
@RequiredArgsConstructor
@Validated
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoEntity> criarEvento(@RequestBody @Valid EventoDto eventoDto) {
        EventoEntity evento = eventoService.criarEvento(eventoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }
}
