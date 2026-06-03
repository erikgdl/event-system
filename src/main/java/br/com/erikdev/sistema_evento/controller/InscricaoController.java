package br.com.erikdev.sistema_evento.controller;

import br.com.erikdev.sistema_evento.database.model.InscricaoEntity;
import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import br.com.erikdev.sistema_evento.dto.InscricaoDto;
import br.com.erikdev.sistema_evento.exception.BadRequestException;
import br.com.erikdev.sistema_evento.exception.NotFoundException;
import br.com.erikdev.sistema_evento.service.InscricaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/inscricoes")
@RequiredArgsConstructor
@Validated
public class InscricaoController {

    private final InscricaoService inscricaoService;

    @PostMapping
    public ResponseEntity<InscricaoEntity> fazerInscricao(@RequestBody @Valid InscricaoDto dto)
            throws NotFoundException, BadRequestException {
        InscricaoEntity inscricao = inscricaoService.realizarInscricao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricao);
    }

    @GetMapping
    public ResponseEntity<List<InscricaoEntity>> listarInscricoes() {
        return ResponseEntity.ok(inscricaoService.listarInscricoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoEntity> buscarInscricao(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(inscricaoService.buscarInscricaoPorId(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarInscricao(@PathVariable UUID id) throws NotFoundException, BadRequestException {
        inscricaoService.cancelarInscricao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/eventos/{id}/participantes")
    public ResponseEntity<List<ParticipanteEntity>> listarParticipantesConfirmados(@PathVariable UUID id)
            throws NotFoundException {
        return ResponseEntity.ok(inscricaoService.listarParticipantesConfirmados(id));
    }
}
