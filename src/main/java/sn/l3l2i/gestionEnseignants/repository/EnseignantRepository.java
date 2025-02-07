package sn.l3l2i.gestionEnseignants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.l3l2i.gestionEnseignants.models.Enseignant;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
}