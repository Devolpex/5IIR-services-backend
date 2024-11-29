package org._iir.backend.modules.proposition;

import org._iir.backend.modules.proposition.dto.PropositionDto;
import org._iir.backend.modules.proposition.dto.PropositionReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PropositionController {
    
        private final PropositionService service;
    
        @PostMapping("/api/proposition")
        public ResponseEntity<PropositionDto> create(@RequestBody @Valid PropositionReq request) {
            PropositionDto dto = service.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }

        @DeleteMapping("/api/proposition/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
}
