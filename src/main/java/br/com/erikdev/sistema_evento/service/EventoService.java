package br.com.erikdev.sistema_evento.service;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import br.com.erikdev.sistema_evento.database.repository.IEventoRepository;
import br.com.erikdev.sistema_evento.dto.EventoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final IEventoRepository eventoRepository;

    public EventoEntity criarEvento(EventoDto dto) {
        return eventoRepository.save(EventoEntity.builder()
                .nome(dto.nome())
                .dataHorario(dto.dataHorario())
                .localizacao(dto.localizacao())
                .capacidadeMaxima(dto.capacidadeMaxima())
                .vagasDisponiveis(dto.vagasDisponiveis())
                .build());
    }

}
