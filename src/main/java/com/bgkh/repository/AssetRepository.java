package com.bgkh.repository;

import com.bgkh.domain.Asset;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Asset entity.
 */
@SuppressWarnings("unused")
public interface AssetRepository extends JpaRepository<Asset,Long> {

    @Query("select asset from Asset asset where asset.supervisor.login = ?#{principal.username}")
    List<Asset> findBySupervisorIsCurrentUser();

    @Query("select asset from Asset asset where asset.technician.login = ?#{principal.username}")
    List<Asset> findByTechnicianIsCurrentUser();

    @Query("select asset.manufacture from Asset asset where asset.manufacture <> '' AND asset.manufacture is not null group by asset.manufacture")
    List<String> findAllManufactures();
}
