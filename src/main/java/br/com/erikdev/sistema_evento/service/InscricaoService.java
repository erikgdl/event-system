package br.com.erikdev.sistema_evento.service;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import br.com.erikdev.sistema_evento.database.model.InscricaoEntity;
import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import br.com.erikdev.sistema_evento.database.repository.IEventoRepository;
import br.com.erikdev.sistema_evento.database.repository.IInscricaoRepository;
import br.com.erikdev.sistema_evento.database.repository.IParticipanteRepository;
import br.com.erikdev.sistema_evento.dto.InscricaoDto;
import br.com.erikdev.sistema_evento.enums.InscricaoEnum;
import br.com.erikdev.sistema_evento.exception.BadRequestException;
import br.com.erikdev.sistema_evento.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final IInscricaoRepository inscricaoRepository;
    private final IParticipanteRepository participanteRepository;
    private final IEventoRepository eventoRepository;

    @Transactional
    public InscricaoEntity realizarInscricao(InscricaoDto dto) throws NotFoundException, BadRequestException {

        ParticipanteEntity participante = participanteRepository.findById(dto.participanteId())
                .orElseThrow(() -> new NotFoundException("Participante não encontrado"));

        EventoEntity evento = eventoRepository.findById(dto.eventoId())
                .orElseThrow(() -> new NotFoundException("Evento não encontrado"));

        if (inscricaoRepository.existsByParticipanteIdAndEventoId(participante.getId(), evento.getId())) {
            throw new BadRequestException("Usuário já está inscrito!");
        }

        InscricaoEntity novaInscricao = new InscricaoEntity();
        novaInscricao.setParticipante(participante);
        novaInscricao.setEvento(evento);
        novaInscricao.setDataInscricao(LocalDateTime.now());

        if (evento.getVagasDisponiveis() > 0) {
            novaInscricao.setStatus(InscricaoEnum.CONFIRMADA);
            evento.setVagasDisponiveis(evento.getVagasDisponiveis() - 1);
            eventoRepository.save(evento);
        } else {
            novaInscricao.setStatus(InscricaoEnum.LISTA_DE_ESPERA);
        }

        return inscricaoRepository.save(novaInscricao);
    }

    public List<InscricaoEntity> listarInscricoes() {
        return inscricaoRepository.findAll();
    }

    public InscricaoEntity buscarInscricaoPorId(UUID id) throws NotFoundException {
        return inscricaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inscrição não encontrada"));
    }

    @Transactional
    public void cancelarInscricao(UUID inscricaoId) throws NotFoundException, BadRequestException {
        InscricaoEntity inscricaoCancelada = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new NotFoundException("Inscrição não encontrada"));

        if (inscricaoCancelada.getStatus() == InscricaoEnum.CANCELADA) {
            throw new BadRequestException("Esta inscrição já foi cancelada!");
        }

        EventoEntity evento = inscricaoCancelada.getEvento();

        if (inscricaoCancelada.getStatus() == InscricaoEnum.CONFIRMADA) {

            Optional<InscricaoEntity> proximoDaFila = inscricaoRepository
                    .findFirstByEventoAndStatusOrderByDataInscricaoAsc(evento, InscricaoEnum.LISTA_DE_ESPERA);

            if (proximoDaFila.isPresent()) {
                InscricaoEntity sortudo = proximoDaFila.get();
                sortudo.setStatus(InscricaoEnum.CONFIRMADA);
                inscricaoRepository.save(sortudo);
            } else {
                evento.setVagasDisponiveis(evento.getVagasDisponiveis() + 1);
                eventoRepository.save(evento);
            }
        }
        inscricaoCancelada.setStatus(InscricaoEnum.CANCELADA);
        inscricaoRepository.save(inscricaoCancelada);
    }

    public List<ParticipanteEntity> listarParticipantesConfirmados(UUID eventoId) throws NotFoundException {
        EventoEntity evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento não encontrado"));

        return inscricaoRepository.findByEventoAndStatusOrderByDataInscricaoAsc(evento, InscricaoEnum.CONFIRMADA)
                .stream()
                .map(InscricaoEntity::getParticipante)
                .toList();
    }
}
