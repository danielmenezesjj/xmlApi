package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.domain.Emitente.Emit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmitRepository extends JpaRepository<Emit, Integer> {
}
