package org.ngo.core.repository;

import jdk.nashorn.internal.runtime.options.Option;
import org.ngo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Modifying
    @Transactional
    @Query(value="INSERT INTO ngo_user_role(FK_USER_ID, FK_ROLE_ID) VALUES (:userId, :roleId)", nativeQuery=true)
    int addRolesToUser(@Param("userId") Long userId, @Param("roleId") Long roleId);

    Role findByRolename(String rolename);

    Optional<Collection<Role>> findByRolecode(List<String> rolecode);
}
