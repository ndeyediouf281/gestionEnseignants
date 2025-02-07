package sn.l3l2i.gestionEnseignants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.l3l2i.gestionEnseignants.models.Enseignant;
import sn.l3l2i.gestionEnseignants.repository.EnseignantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EnseignantService {

	@Autowired
	private EnseignantRepository enseignantRepository;

	public Enseignant ajouterEnseignant(Enseignant enseignant) {
		if (enseignant == null) {
			throw new IllegalArgumentException("L'enseignant ne peut pas être null");
		}
		return enseignantRepository.save(enseignant);
	}

	public Enseignant trouverEnseignantParId(Long id) {
		Optional<Enseignant> enseignant = enseignantRepository.findById(id);
		return enseignant.orElseThrow(() -> new RuntimeException("Enseignant non trouvé avec l'ID : " + id));
	}

	public List<Enseignant> trouverTousLesEnseignants() {
		return enseignantRepository.findAll();
	}

	public void supprimerEnseignant(Long id) {
		enseignantRepository.deleteById(id);
	}
}