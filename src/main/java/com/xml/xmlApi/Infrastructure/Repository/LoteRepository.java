package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.Lote.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {
    @Query("SELECT l FROM lote l WHERE l.nLote = :nLote AND l.dFab = :dFab AND l.dVal = :dVal")
    List<Lote> findLoteByNLoteDFabDVal(@Param("nLote") String nLote, @Param("dFab") String dFab, @Param("dVal") String dVal);
}
