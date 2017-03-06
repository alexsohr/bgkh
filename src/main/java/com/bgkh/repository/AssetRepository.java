package com.bgkh.repository;

import com.bgkh.domain.Asset;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

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

    @Query("select distinct asset from Asset asset left join fetch asset.maps left join fetch asset.otherFiles order by asset.id")
    List<Asset> findAllWithEagerRelationships();

    @Query("select asset from Asset asset left join fetch asset.maps left join fetch asset.otherFiles where asset.id =:id order by asset.id")
    Asset findOneWithEagerRelationships(@Param("id") Long id);

}
