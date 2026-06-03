package br.com.erikdev.sistema_evento.service;

import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import br.com.erikdev.sistema_evento.database.repository.IInscricaoRepository;
import br.com.erikdev.sistema_evento.database.repository.IParticipanteRepository;
import br.com.erikdev.sistema_evento.dto.ParticipanteDto;
import br.com.erikdev.sistema_evento.exception.BadRequestException;
import br.com.erikdev.sistema_evento.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

    private final IParticipanteRepository participanteRepository;
    private final IInscricaoRepository inscricaoRepository;

    public ParticipanteEntity criarParticipante(ParticipanteDto dto) throws BadRequestException {
        ParticipanteEntity participanteEmail = participanteRepository.findByEmail(dto.email())
                .orElse(null);

        ParticipanteEntity participanteCpf = participanteRepository.findByCpf(dto.cpf())
                .orElse(null);

        if (participanteCpf != null) {
            throw new BadRequestException("Já tem um participante cadastrado com esse cpf");
        }

        if (participanteEmail != null ) {
            throw new BadRequestException("Já tem um participante cadastrado com esse email");
        }

        return participanteRepository.save(ParticipanteEntity.builder()
                .nome(dto.nome())
                .email(dto.email())
                .cpf(dto.cpf())
                .build());
    }

    public List<ParticipanteEntity> listarParticipantes() {
        return participanteRepository.findAll();
    }

    public ParticipanteEntity buscarParticipantePorId(UUID id) throws NotFoundException {
        return participanteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participante não encontrado"));
    }

    public ParticipanteEntity atualizarParticipante(UUID id, ParticipanteDto dto) throws NotFoundException, BadRequestException {
        ParticipanteEntity participante = buscarParticipantePorId(id);

        ParticipanteEntity participanteEmail = participanteRepository.findByEmail(dto.email())
                .orElse(null);

        ParticipanteEntity participanteCpf = participanteRepository.findByCpf(dto.cpf())
                .orElse(null);

        if (participanteCpf != null && !participanteCpf.getId().equals(id)) {
            throw new BadRequestException("Já tem um participante cadastrado com esse cpf");
        }

        if (participanteEmail != null && !participanteEmail.getId().equals(id)) {
            throw new BadRequestException("Já tem um participante cadastrado com esse email");
        }

        participante.setNome(dto.nome());
        participante.setEmail(dto.email());
        participante.setCpf(dto.cpf());

        return participanteRepository.save(participante);
    }

    @Transactional
    public void deletarParticipante(UUID id) throws NotFoundException {
        ParticipanteEntity participante = buscarParticipantePorId(id);
        inscricaoRepository.deleteByParticipante(participante);
        participanteRepository.delete(participante);
    }


}
