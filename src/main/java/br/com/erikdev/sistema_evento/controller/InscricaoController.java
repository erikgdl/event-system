package br.com.erikdev.sistema_evento.controller;

import br.com.erikdev.sistema_evento.database.model.InscricaoEntity;
import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import br.com.erikdev.sistema_evento.dto.InscricaoDto;
import br.com.erikdev.sistema_evento.dto.ParticipanteDto;
import br.com.erikdev.sistema_evento.exception.BadRequestException;
import br.com.erikdev.sistema_evento.service.InscricaoService;
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
@RequestMapping("v1/inscricao")
@RequiredArgsConstructor
@Validated
public class InscricaoController {

    private final InscricaoService inscricaoService;

    @PostMapping
    public ResponseEntity<InscricaoEntity> fazerInscricao(@RequestBody @Valid InscricaoDto dto) throws Exception {
        InscricaoEntity inscricao = inscricaoService.realizarInscricao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricao);
    }
}
