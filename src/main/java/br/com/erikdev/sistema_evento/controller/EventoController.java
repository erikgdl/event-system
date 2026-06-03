package br.com.erikdev.sistema_evento.controller;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import br.com.erikdev.sistema_evento.dto.EventoDto;
import br.com.erikdev.sistema_evento.exception.NotFoundException;
import br.com.erikdev.sistema_evento.service.EventoService;
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
@RequestMapping("v1/eventos")
@RequiredArgsConstructor
@Validated
public class  EventoController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoEntity> criarEvento(@RequestBody @Valid EventoDto eventoDto) {
        EventoEntity evento = eventoService.criarEvento(eventoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @GetMapping
    public ResponseEntity<List<EventoEntity>> listarEventos() {
        return ResponseEntity.ok(eventoService.listarEventos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoEntity> buscarEvento(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(eventoService.buscarEventoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoEntity> atualizarEvento(@PathVariable UUID id, @RequestBody @Valid EventoDto eventoDto)
            throws NotFoundException {
        return ResponseEntity.ok(eventoService.atualizarEvento(id, eventoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEvento(@PathVariable UUID id) throws NotFoundException {
        eventoService.deletarEvento(id);
        return ResponseEntity.noContent().build();
    }
}
