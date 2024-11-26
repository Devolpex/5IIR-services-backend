package org._iir.backend.modules.offre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OffreDTO {
    private Long id;
    private String description;
    private Double tarif;
    private String disponibilite;
    private Long prestataireId;
}
