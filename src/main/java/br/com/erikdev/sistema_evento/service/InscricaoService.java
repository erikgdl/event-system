package br.com.erikdev.sistema_evento.service;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import br.com.erikdev.sistema_evento.database.model.InscricaoEntity;
import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import br.com.erikdev.sistema_evento.database.repository.IEventoRepository;
import br.com.erikdev.sistema_evento.database.repository.IInscricaoRepository;
import br.com.erikdev.sistema_evento.database.repository.IParticipanteRepository;
import br.com.erikdev.sistema_evento.enums.InscricaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final IInscricaoRepository inscricaoRepository;
    private final IParticipanteRepository participanteRepository;
    private final IEventoRepository eventoRepository;

    @Transactional
    public InscricaoEntity realizarInscricao(UUID participanteId, UUID eventoId) {

        ParticipanteEntity participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));

        EventoEntity evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (inscricaoRepository.existsByParticipanteAndEvento(participante.getId(), evento.getId())) {
            throw new RuntimeException("Usuário já está inscrito!");
        }

        if (evento.getVagasDisponiveis() <= 0) {
            throw new RuntimeException("Evento esgotado!");
        }

        InscricaoEntity novaInscricao = new InscricaoEntity();
        novaInscricao.setParticipante(participante);
        novaInscricao.setEvento(evento);
        novaInscricao.setDataInscricao(LocalDateTime.now());
        novaInscricao.setStatus(InscricaoEnum.CONFIRMADA);

        evento.setVagasDisponiveis(evento.getVagasDisponiveis() - 1);
        eventoRepository.save(evento);

        return inscricaoRepository.save(novaInscricao);
    }



}
