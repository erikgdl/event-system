package br.com.erikdev.sistema_evento.database.repository;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IEventoRepository extends JpaRepository<EventoEntity, UUID> {
}
