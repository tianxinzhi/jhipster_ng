package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AttrValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttrValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttrValueRepository extends JpaRepository<AttrValue, Long> {
}
