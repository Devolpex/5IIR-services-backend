package org._iir.backend.modules.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping("/api/services")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> create(@RequestBody ServiceDTO req) {
        return ResponseEntity.ok(serviceService.create(req));
    }

    @PutMapping("/api/services/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> update(@RequestBody ServiceDTO req, @PathVariable Long id) {
        return ResponseEntity.ok(serviceService.update(req, id));
    }

    @DeleteMapping("/api/services/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/services/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.findById(id));
    }

    @GetMapping("/api/services/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<ServiceDTO>> findList() {
        return ResponseEntity.ok(serviceService.findList());
    }

    @GetMapping("/api/services")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ServiceDTO>> findPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return ResponseEntity.ok(serviceService.findPage(pageable));
    }

}
