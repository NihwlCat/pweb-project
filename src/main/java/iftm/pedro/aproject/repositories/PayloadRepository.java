package iftm.pedro.aproject.repositories;

import iftm.pedro.aproject.entities.Payload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayloadRepository extends JpaRepository<Payload, String> {

    List<Payload> findByUsername(String username);
}
