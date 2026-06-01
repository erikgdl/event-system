package br.com.erikdev.sistema_evento.database.repository;

import br.com.erikdev.sistema_evento.database.model.ParticipanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IParticipanteRepository extends JpaRepository<ParticipanteEntity, UUID> {

    Optional<ParticipanteEntity> findByEmail(String email);

    Optional<ParticipanteEntity> findByCpf(String cpf);

}
