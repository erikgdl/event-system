package br.com.erikdev.sistema_evento.service;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import br.com.erikdev.sistema_evento.database.repository.IInscricaoRepository;
import br.com.erikdev.sistema_evento.database.repository.IEventoRepository;
import br.com.erikdev.sistema_evento.dto.EventoDto;
import br.com.erikdev.sistema_evento.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final IEventoRepository eventoRepository;
    private final IInscricaoRepository inscricaoRepository;

    public EventoEntity criarEvento(EventoDto dto) {
        Integer vagasDisponiveis = dto.vagasDisponiveis();
        if (vagasDisponiveis == null) {
            vagasDisponiveis = dto.capacidadeMaxima();
        }
        return eventoRepository.save(EventoEntity.builder()
                .nome(dto.nome())
                .dataHorario(dto.dataHorario())
                .localizacao(dto.localizacao())
                .capacidadeMaxima(dto.capacidadeMaxima())
                .vagasDisponiveis(vagasDisponiveis)
                .build());
    }

    public List<EventoEntity> listarEventos() {
        return eventoRepository.findAll();
    }

    public EventoEntity buscarEventoPorId(UUID id) throws NotFoundException {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento não encontrado"));
    }

    public EventoEntity atualizarEvento(UUID id, EventoDto dto) throws NotFoundException {
        EventoEntity evento = buscarEventoPorId(id);
        evento.setNome(dto.nome());
        evento.setDataHorario(dto.dataHorario());
        evento.setLocalizacao(dto.localizacao());
        evento.setCapacidadeMaxima(dto.capacidadeMaxima());
        if (dto.vagasDisponiveis() != null) {
            evento.setVagasDisponiveis(dto.vagasDisponiveis());
        }
        return eventoRepository.save(evento);
    }

    @Transactional
    public void deletarEvento(UUID id) throws NotFoundException {
        EventoEntity evento = buscarEventoPorId(id);
        inscricaoRepository.deleteByEvento(evento);
        eventoRepository.delete(evento);
    }

}
