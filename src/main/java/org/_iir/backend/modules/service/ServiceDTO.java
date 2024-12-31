package org._iir.backend.modules.service;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
    @Nullable
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;
    private String description;

}
