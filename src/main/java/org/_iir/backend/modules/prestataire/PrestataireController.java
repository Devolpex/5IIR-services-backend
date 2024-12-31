package org._iir.backend.modules.prestataire;

import java.util.List;

import org._iir.backend.modules.offre.dto.OffreDTO;
import org._iir.backend.modules.order.dto.DemandeOrderDTO;
import org._iir.backend.modules.order.dto.OffreOrderDTO;
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

    // Endpoint to retrieve all orders related to her offres associated with a
    // prestataire (service provider)
    @GetMapping("/api/prestataire/orders/offres")
    public List<OffreOrderDTO> getOrdersByPrestataire() {
        return prestataireService.getOffersOrdersByPrestataire();
    }

    // Endpoint to retrieve all orders related to her demandes associated with a
    // prestataire (service provider)
    @GetMapping("/api/prestataire/orders/demandes")
    public List<DemandeOrderDTO> getDemandesOrdersByPrestataire() {
        return prestataireService.getDemandesOrdersByPrestataire();
    }
}
