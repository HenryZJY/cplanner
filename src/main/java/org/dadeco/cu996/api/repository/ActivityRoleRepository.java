package org.dadeco.cu996.api.repository;

import org.dadeco.cu996.api.model.ActivityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRoleRepository extends JpaRepository<ActivityRole, Integer> {
    ActivityRole findByName(String name);

    ActivityRole findByDescription(String desc);
}
