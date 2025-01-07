package org._iir.backend.modules.service;

import java.util.List;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.interfaces.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService implements IService<Service, ServiceDTO, ServiceDTO, ServiceDTO, Long> {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceDTO create(ServiceDTO req) {
        Service service = Service.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .build();
        service = serviceRepository.save(service);
        return ServiceDTO.builder()
                .id(service.getId())
                .title(service.getTitle())
                .description(service.getDescription())
                .build();
    }

    @Override
    public ServiceDTO update(ServiceDTO req, Long id) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setTitle(req.getTitle());
                    service.setDescription(req.getDescription());
                    service = serviceRepository.save(service);
                    return ServiceDTO.builder()
                            .id(service.getId())
                            .title(service.getTitle())
                            .description(service.getDescription())
                            .build();
                })
                .orElseThrow(() -> new OwnNotFoundException("Service not found"));
    }

    @Override
    public void delete(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new OwnNotFoundException("Service not found"));
        serviceRepository.delete(service);

    }

    @Override
    public ServiceDTO findById(Long id) {
        return serviceRepository.findById(id)
                .map(service -> ServiceDTO.builder()
                        .id(service.getId())
                        .title(service.getTitle())
                        .description(service.getDescription())
                        .build())
                .orElseThrow(() -> new OwnNotFoundException("Service not found"));
    }

    @Override
    public List<ServiceDTO> findList() {
        return serviceRepository.findAll().stream()
                .map(service -> ServiceDTO.builder()
                        .id(service.getId())
                        .title(service.getTitle())
                        .description(service.getDescription())
                        .build())
                .toList();
    }

    @Override
    public Page<ServiceDTO> findPage(Pageable pageable) {
        return serviceRepository.findAll(pageable)
                .map(service -> ServiceDTO.builder()
                        .id(service.getId())
                        .title(service.getTitle())
                        .description(service.getDescription())
                        .build());
    }
}
