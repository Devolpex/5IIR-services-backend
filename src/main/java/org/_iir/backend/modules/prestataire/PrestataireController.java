package org._iir.backend.modules.prestataire;

import java.util.List;

import org._iir.backend.modules.offre.dto.OffreDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PrestataireController {

    private final PrestataireService prestataireService;


    // Endpoint to retrieve offers associated with a prestataire (service provider)
    @GetMapping("/api/prestataire/offres")
    public List<OffreDTO> getOffersByPrestataire() {
        return prestataireService.getOffersByPrestataire();
    }
}
