package org._iir.backend.modules.prestataire;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.modules.offre.dto.OffreDTO;
import org._iir.backend.modules.offre.dto.ServiceDTO;
import org._iir.backend.modules.order.dto.DemandeDTO;
import org._iir.backend.modules.order.dto.DemandeOrderDTO;
import org._iir.backend.modules.order.dto.OffreOrderDTO;
import org._iir.backend.modules.order.dto.UserDTO;
import org._iir.backend.modules.prestataire_services.PrestataireServiceID;
import org._iir.backend.modules.prestataire_services.PrestataireServices;
import org._iir.backend.modules.prestataire_services.PrestataireServicesRepository;
import org._iir.backend.modules.service.Service;
import org._iir.backend.modules.service.ServiceRepository;
import org._iir.backend.modules.user.UserService;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class PrestataireService {

        private final PrestataireDao prestataireDao;
        private final UserService userService;
        private final PrestataireServicesRepository psRepository;
        private final ServiceRepository serviceRepository;

        /**
         * Service to retrieve offers associated with a prestataire (service provider)
         * using the security context.
         * This method retrieves the authenticated user (assumed to be a Prestataire)
         * and then collects all offers
         * associated with the services the prestataire provides. The result is returned
         * as a flat list of {@link OffreDTO}.
         * 
         * @return a list of {@link OffreDTO} containing the details of all offers
         *         associated with the authenticated prestataire.
         *         Each offer includes its ID, description, price (tarif), and the
         *         service it belongs to (represented by a {@link ServiceDTO}).
         * 
         * @throws AuthenticationException if the user is not authenticated or not a
         *                                 prestataire.
         */
        public List<OffreDTO> getOffersByPrestataire() {
                // Retrieve the authenticated user (Prestataire)
                Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();

                // Flatten the stream of offers from each PrestataireService and map to OffreDTO
                return prestataire.getPrestataireServices().stream()
                                .flatMap(ps -> ps.getOffres().stream() // Flatten the nested stream
                                                .map(offre -> OffreDTO.builder()
                                                                .id(offre.getId())
                                                                .description(offre.getDescription())
                                                                .tarif(offre.getTarif())
                                                                .service(ServiceDTO.builder()
                                                                                .id(ps.getService().getId())
                                                                                .title(ps.getService().getTitle()) // Corrected
                                                                                                                   // service
                                                                                                                   // property
                                                                                                                   // from
                                                                                                                   // description
                                                                                                                   // to
                                                                                                                   // title
                                                                                .build())
                                                                .build()))
                                .collect(Collectors.toList()); // Collect the flat result into a list
        }

        /**
         * Service to retrieve all orders relatied to her offres associated with a
         * prestataire (service provider)
         * using the security context.
         */
        public List<OffreOrderDTO> getOffersOrdersByPrestataire() {
                // Retrieve the authenticated user (Prestataire)
                Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();

                // Flatten the stream of orders from each PrestataireService and map to
                // OffreOrderDTO
                return prestataire.getPrestataireServices().stream()
                                .flatMap(ps -> ps.getOffres().stream() // Flatten the nested stream
                                                .flatMap(offre -> offre.getOrders().stream()
                                                                .map(order -> OffreOrderDTO.builder()
                                                                                .id(order.getId())
                                                                                .orderDate(order.getOrderDate())
                                                                                .status(order.getStatus())
                                                                                // OffreDTO of order package
                                                                                .offre(org._iir.backend.modules.order.dto.OffreDTO
                                                                                                .builder()
                                                                                                .id(offre.getId())
                                                                                                .description(offre
                                                                                                                .getDescription())
                                                                                                .tarif(offre.getTarif())
                                                                                                // ServiceDTO of order
                                                                                                // package
                                                                                                .service(org._iir.backend.modules.order.dto.ServiceDTO
                                                                                                                .builder()
                                                                                                                .id(ps.getService()
                                                                                                                                .getId())
                                                                                                                .title(ps.getService()
                                                                                                                                .getTitle())
                                                                                                                .build())
                                                                                                .build())
                                                                                .demandeur(UserDTO.builder()
                                                                                                .id(order.getDemandeur()
                                                                                                                .getId())
                                                                                                .email(order.getDemandeur()
                                                                                                                .getEmail())
                                                                                                .nom(order.getDemandeur()
                                                                                                                .getNom())
                                                                                                .build())
                                                                                .build())))
                                .collect(Collectors.toList());
        }

        public List<DemandeOrderDTO> getDemandesOrdersByPrestataire() {
                // Retrieve the authenticated user (Prestataire)
                Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();

                return prestataire.getPropositions().stream()
                                .map(proposition -> {
                                        // Check if demandeOrder is null and return null if true
                                        if (proposition.getDemandeOrder() == null) {
                                                return null; // Return null if no demandeOrder present
                                        }

                                        // Create DemandeOrderDTO if demandeOrder is not null
                                        return DemandeOrderDTO.builder()
                                                        .id(proposition.getDemandeOrder().getId())
                                                        .orderDate(proposition.getDemandeOrder().getOrderDate())
                                                        .status(proposition.getDemandeOrder().getStatus())
                                                        .demande(DemandeDTO.builder()
                                                                        .id(proposition.getDemande().getId())
                                                                        .description(proposition.getDemande()
                                                                                        .getDescription())
                                                                        .service(proposition.getDemande().getService())
                                                                        .dateDisponible(proposition.getDemande()
                                                                                        .getDateDisponible())
                                                                        .lieu(proposition.getDemande().getLieu())
                                                                        .build())
                                                        .demandeur(UserDTO.builder()
                                                                        .id(proposition.getDemande().getDemandeur()
                                                                                        .getId())
                                                                        .email(proposition.getDemande().getDemandeur()
                                                                                        .getEmail())
                                                                        .nom(proposition.getDemande().getDemandeur()
                                                                                        .getNom())
                                                                        .build())
                                                        .build();
                                })
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
        }

        public boolean addServiceToPrestataire(Long serviceId) {
                Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();
                return serviceRepository.findById(serviceId)
                                .map(s -> {
                                        PrestataireServiceID id = PrestataireServiceID.builder()
                                                        .prestataireId(prestataire.getId())
                                                        .serviceId(s.getId())
                                                        .build();
                                        PrestataireServices ps = PrestataireServices.builder()
                                                        .id(id)
                                                        .prestataire(prestataire)
                                                        .service(s)
                                                        .build();
                                        psRepository.save(ps);
                                        return true;
                                })
                                .orElseThrow(() -> new OwnNotFoundException("Service not found"));
        }

        public boolean removeServiceFromPrestataire(Long serviceId) {
                Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();
                return serviceRepository.findById(serviceId)
                                .map(s -> {
                                        PrestataireServiceID id = PrestataireServiceID.builder()
                                                        .prestataireId(prestataire.getId())
                                                        .serviceId(s.getId())
                                                        .build();
                                        psRepository.deleteById(id);
                                        return true;
                                })
                                .orElseThrow(() -> new OwnNotFoundException("Service not found"));
        }

}
