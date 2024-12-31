package org._iir.backend.modules.demandeur;

import java.util.List;

import org._iir.backend.modules.demande.dto.DemandeDTO;
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
}
