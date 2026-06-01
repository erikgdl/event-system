package br.com.erikdev.sistema_evento.service;

import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import br.com.erikdev.sistema_evento.database.repository.IEventoRepository;
import br.com.erikdev.sistema_evento.database.repository.IParticipanteRepository;
import br.com.erikdev.sistema_evento.dto.ParticipanteDto;
import br.com.erikdev.sistema_evento.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

    private final IParticipanteRepository participanteRepository;

    public ParticipanteEntity criarParticipante(ParticipanteDto dto) throws BadRequestException {
        ParticipanteEntity participanteEmail = participanteRepository.findByEmail(dto.email())
                .orElse(null);

        ParticipanteEntity participanteCpf = participanteRepository.findByCpf(dto.cpf())
                .orElse(null);

        if (participanteEmail != null && participanteCpf != null) {
            throw new BadRequestException("Já tem um participante cadastrado com esse email ou cpf");
        }

        return participanteRepository.save(ParticipanteEntity.builder()
                .nome(dto.nome())
                .email(dto.email())
                .cpf(dto.cpf())
                .build());
    }

}
