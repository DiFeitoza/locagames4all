package ufc.pds.locagames4all.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufc.pds.locagames4all.model.Cliente;

@Repository
public interface ClienteRepositoryJPA extends JpaRepository<Cliente, Long> {
    public Cliente findByCpf(String cpf);
}
