package org._iir.backend.dao;

import org._iir.backend.bean.DemandeService;
import org._iir.backend.bean.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeServiceDao extends JpaRepository<DemandeService, Long> {

}