package org._iir.backend.modules.demandeur;

import java.util.List;

import org._iir.backend.modules.demande.dto.DemandeDTO;
import org._iir.backend.modules.user.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemandeurService {

    private final UserService userService;

    /**
     * Service to fetch demandes of a demandeur
     */
    public List<DemandeDTO> getDemandesOfDemandeur() {
        Demandeur demandeur = (Demandeur) userService.getAuthenticatedUser();

        return demandeur.getDemandes().stream()
                .map(demande -> DemandeDTO.builder()
                        .id(demande.getId())
                        .service(demande.getService())
                        .description(demande.getDescription())
                        .lieu(demande.getLieu())
                        .dateDisponible(demande.getDateDisponible())
                        .createdAt(demande.getCreatedAt())
                        .updatedAt(demande.getUpdatedAt())
                        .build())
                .toList();
    }

}
