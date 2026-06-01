package br.com.erikdev.sistema_evento.service;

import br.com.erikdev.sistema_evento.database.repository.IEventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final IEventoRepository eventoRepository;



}
