package br.com.erikdev.sistema_evento.database.repository;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import br.com.erikdev.sistema_evento.database.model.InscricaoEntity;
import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IInscricaoRepository extends JpaRepository<InscricaoEntity, UUID> {

    Boolean existsByParticipanteAndEvento(UUID participante, UUID evento);
}
