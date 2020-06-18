package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AttrAndValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttrAndValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttrAndValueRepository extends JpaRepository<AttrAndValue, Long> {
}
