package ufc.pds.locagames4all.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ufc.pds.locagames4all.enums.StatusJogo;
import ufc.pds.locagames4all.enums.TipoJogo;
import ufc.pds.locagames4all.model.Jogo;

import java.util.List;
import java.util.Optional;


@Repository
public interface JogoRepositoryJPA extends JpaRepository<Jogo, Long> {
    List<Jogo> findByTipo(TipoJogo tipo);

    List<Jogo> findByStatus(StatusJogo status);
}
