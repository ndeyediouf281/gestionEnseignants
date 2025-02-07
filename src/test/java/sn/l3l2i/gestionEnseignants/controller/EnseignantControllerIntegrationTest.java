package sn.l3l2i.gestionEnseignants.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import sn.l3l2i.gestionEnseignants.models.Enseignant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnseignantControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAjouterEnseignant() {
        // Arrange
        Enseignant enseignant = new Enseignant(null, "Doe", "John", "john.doe@example.com");

        // Act
        ResponseEntity<Enseignant> response = restTemplate.postForEntity(
                "/enseignants",
                enseignant,
                Enseignant.class
        );

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Doe", response.getBody().getNom());
    }

    @Test
    public void testTrouverEnseignantParId() {
        // Arrange
        Enseignant enseignant = new Enseignant(null, "Doe", "John", "john.doe@example.com");
        ResponseEntity<Enseignant> createResponse = restTemplate.postForEntity(
                "/enseignants",
                enseignant,
                Enseignant.class
        );
        Long enseignantId = createResponse.getBody().getId();

        // Act
        ResponseEntity<Enseignant> response = restTemplate.getForEntity(
                "/enseignants/" + enseignantId,
                Enseignant.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Doe", response.getBody().getNom());
        assertEquals(enseignantId, response.getBody().getId());
    }
    @Test
    public void testGetAllEnseignants() {
        // Arrange
        // Ajouter des enseignants dans la base de données
        Enseignant enseignant1 = new Enseignant(null, "Doe", "John", "john.doe@example.com");
        Enseignant enseignant2 = new Enseignant(null, "Smith", "Jane", "jane.smith@example.com");

        restTemplate.postForEntity("/enseignants", enseignant1, Enseignant.class);
        restTemplate.postForEntity("/enseignants", enseignant2, Enseignant.class);

        // Act
        ResponseEntity<List<Enseignant>> response = restTemplate.exchange(
                "/enseignants",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Enseignant>>() {}
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Vérifier le statut HTTP
        assertNotNull(response.getBody()); // Vérifier que la réponse n'est pas nulle
        assertEquals(2, response.getBody().size()); // Vérifier le nombre d'enseignants retournés

        // Vérifier les données des enseignants
        Enseignant firstEnseignant = response.getBody().get(0);
        Enseignant secondEnseignant = response.getBody().get(1);

        assertEquals("Doe", firstEnseignant.getNom());
        assertEquals("Smith", secondEnseignant.getNom());
    }

    @Test
    public void testTrouverEnseignantParId_NotFound() {
        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/enseignants/999",
                String.class
        );

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Enseignant non trouvé"));
    }

    @Test
    public void testAjouterEnseignant_Null() {
        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/enseignants",
                null,
                String.class
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}