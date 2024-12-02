package org._iir.backend.modules.order.dto;

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
    private ServiceDTO service;
}