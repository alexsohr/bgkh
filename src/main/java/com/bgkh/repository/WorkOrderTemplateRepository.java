package com.bgkh.repository;

import com.bgkh.domain.WorkOrderTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkOrderTemplate entity.
 */
@SuppressWarnings("unused")
public interface WorkOrderTemplateRepository extends JpaRepository<WorkOrderTemplate,Long> {

}
