package org._iir.backend.prestataire;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org._iir.backend.modules.offre.Offre;
import org._iir.backend.modules.offre.dto.OffreDTO;
import org._iir.backend.modules.offre.dto.ServiceDTO;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.prestataire.PrestataireDao;
import org._iir.backend.modules.prestataire.PrestataireService;
import org._iir.backend.modules.prestataire_services.PrestataireServiceID;
import org._iir.backend.modules.prestataire_services.PrestataireServices;
import org._iir.backend.modules.service.Service;
import org._iir.backend.modules.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
class PrestataireServiceTest {

    @InjectMocks
    private PrestataireService prestataireService;

    @Mock
    private PrestataireDao prestataireDao;

    @Mock
    private UserService userService;

    @Mock
    private Prestataire prestataire;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Create a mock Prestataire
        prestataire = Prestataire.builder()
                .id(1L)
                .email("prestataire@gmail.com")
                .nom("Prestataire")
                .build();

        // Mock the userService to return the mock prestataire when getAuthenticatedUser is called
        when(userService.getAuthenticatedUser()).thenReturn(prestataire);

        // Set the prestataire's services
        Set<PrestataireServices> prestataireServices = generatePrestataireData();
        prestataire.setPrestataireServices(prestataireServices);
    }

    private Set<PrestataireServices> generatePrestataireData() {
        // Create a Set of PrestataireServices
        return IntStream.range(1, 4)
                .mapToObj(i -> PrestataireServices.builder()
                        .id(PrestataireServiceID.builder()
                                .prestataireId(prestataire.getId())
                                .serviceId((long) i)
                                .build())
                        .prestataire(prestataire)
                        .service(Service.builder()
                                .id((long) i)
                                .title("Service " + i)
                                .description("Service description " + i)
                                .build())
                        .offres(Set.of(Offre.builder()
                                .id((long) i)
                                .description("Offre " + i)
                                .tarif(100.0 * i)
                                .build()))
                        .build())
                .collect(Collectors.toSet());
    }

    @Test
    void testGetOffersByPrestataire() {
        // Call the service method
        List<OffreDTO> offres = prestataireService.getOffersByPrestataire();
    
        // Verify that the method returns the correct number of offers (3 offers)
        assertEquals(3, offres.size(), "The number of offers should be 3.");
    
        // Verify that the offers are correct
        for (int i = 0; i < offres.size(); i++) {
            OffreDTO offreDTO = offres.get(i);
            log.info("Offre ID: " + offreDTO.getId());
            assertEquals((long) (i + 1), offreDTO.getId(), "Offer ID should be " + (i + 1));
            assertEquals("Offre " + (i + 1), offreDTO.getDescription(), "Offer description should be 'Offre " + (i + 1) + "'");
            assertEquals(100.0 * (i + 1), offreDTO.getTarif(), "Offer tarif should be " + (100.0 * (i + 1)));
            ServiceDTO serviceDTO = offreDTO.getService();
            assertEquals((long) (i + 1), serviceDTO.id(), "Service ID should be " + (i + 1));
            assertEquals("Service " + (i + 1), serviceDTO.title(), "Service title should be 'Service " + (i + 1) + "'");
        }
    
        // Verify that the mocked method getAuthenticatedUser is actually called
        verify(userService, times(1)).getAuthenticatedUser();
    }
}
