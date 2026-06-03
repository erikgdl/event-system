package br.com.erikdev.sistema_evento.database.repository;

import br.com.erikdev.sistema_evento.database.model.EventoEntity;
import br.com.erikdev.sistema_evento.database.model.InscricaoEntity;
import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import br.com.erikdev.sistema_evento.enums.InscricaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IInscricaoRepository extends JpaRepository<InscricaoEntity, UUID> {

    Boolean existsByParticipanteIdAndEventoId(UUID participante, UUID evento);

    Optional<InscricaoEntity> findFirstByEventoAndStatusOrderByDataInscricaoAsc(EventoEntity evento, InscricaoEnum status);

    List<InscricaoEntity> findByEventoAndStatusOrderByDataInscricaoAsc(EventoEntity evento, InscricaoEnum status);

    void deleteByEvento(EventoEntity evento);

    void deleteByParticipante(ParticipanteEntity participante);
}
