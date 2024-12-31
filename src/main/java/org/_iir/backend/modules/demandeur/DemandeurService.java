package org._iir.backend.modules.demandeur;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org._iir.backend.modules.demande.dto.DemandeDTO;
import org._iir.backend.modules.order.dto.DemandeOrderDTO;
import org._iir.backend.modules.order.dto.OffreDTO;
import org._iir.backend.modules.order.dto.OffreOrderDTO;
import org._iir.backend.modules.order.dto.ServiceDTO;
import org._iir.backend.modules.order.dto.UserDTO;
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

    /**
     * Service to fetch offres orders of a demandeur
     */
    public List<OffreOrderDTO> getOffreOrdersOfDemandeur() {
        Demandeur demandeur = (Demandeur) userService.getAuthenticatedUser();

        return demandeur.getOffreOrders().stream()
                .map(order -> OffreOrderDTO.builder()
                        .id(order.getId())
                        .orderDate(order.getOrderDate())
                        .status(order.getStatus())
                        .offre(OffreDTO.builder()
                                .id(order.getOffre().getId())
                                .description(order.getOffre().getDescription())
                                .tarif(order.getOffre().getTarif())
                                .service(ServiceDTO.builder()
                                        .id(order.getOffre().getPrestataireService().getService().getId())
                                        .title(order.getOffre().getPrestataireService().getService().getTitle())
                                        .build())
                                .build())
                        .prestataire(UserDTO.builder()
                                .id(order.getOffre().getPrestataireService().getPrestataire().getId())
                                .email(order.getOffre().getPrestataireService().getPrestataire().getEmail())
                                .nom(order.getOffre().getPrestataireService().getPrestataire().getNom())
                                .build())
                        .build())
                .toList();
    }

    /**
     * Service to fetch demandes order by demandeur
     */
    public List<DemandeOrderDTO> getDemandesOrderByDemandeur() {
        // Retrieve the authenticated user (Demandeur)
        Demandeur demandeur = (Demandeur) userService.getAuthenticatedUser();

        // Fetch the demandes and map them to DemandeOrderDTOs
        return demandeur.getDemandes().stream()
                .flatMap(demande -> demande.getPropositions().stream()
                        .map(proposition -> proposition.getDemandeOrder()) // Get DemandeOrder
                        .filter(Objects::nonNull) // Filter out null DemandeOrders
                        .map(order -> DemandeOrderDTO.builder()
                                .id(order.getId())
                                .orderDate(order.getOrderDate())
                                .status(order.getStatus())
                                .demande(org._iir.backend.modules.order.dto.DemandeDTO.builder()
                                        .id(order.getProposition().getDemande().getId())
                                        .service(order.getProposition().getDemande().getService())
                                        .description(order.getProposition().getDemande().getDescription())
                                        .lieu(order.getProposition().getDemande().getLieu())
                                        .dateDisponible(order.getProposition().getDemande().getDateDisponible())
                                        .build())
                                .demandeur(UserDTO.builder()
                                        .id(order.getProposition().getDemande().getDemandeur().getId())
                                        .email(order.getProposition().getDemande().getDemandeur().getEmail())
                                        .nom(order.getProposition().getDemande().getDemandeur().getNom())
                                        .build())
                                .build()))
                .collect(Collectors.toList());
    }

}
