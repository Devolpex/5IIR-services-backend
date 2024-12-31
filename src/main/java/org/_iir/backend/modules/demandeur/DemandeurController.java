package org._iir.backend.modules.demandeur;

import java.util.List;

import org._iir.backend.modules.demande.dto.DemandeDTO;
import org._iir.backend.modules.order.dto.OffreOrderDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DemandeurController {

    private final DemandeurService demandeurService;

    @GetMapping("/api/demandeur/demandes")
    public List<DemandeDTO> getDemandesOfDemandeur() {
        return demandeurService.getDemandesOfDemandeur();
    }

    // Endpoint to fetch offres orders of a demandeur
    @GetMapping("/api/demandeur/orders/offres")
    public List<OffreOrderDTO> getOffreOrdersOfDemandeur() {
        return demandeurService.getOffreOrdersOfDemandeur();
    }

    // Endpoint to fetch demandeur's demandes orders
    @GetMapping("/api/demandeur/orders/demandes")
    public List<OffreOrderDTO> getDemandeOrdersOfDemandeur() {
        return demandeurService.getOffreOrdersOfDemandeur();
    }
}
