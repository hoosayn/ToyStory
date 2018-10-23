package org.ngo.registration.core.repository;

import org.ngo.registration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(value="select rolename from ngo_role  where PK_ROLE_ID in (select FK_ROLE_ID from NGO_USER_ROLE where FK_USER_ID in (select pk_user_id from NGO_USER where username = :username))", nativeQuery = true)
    Set<String> getRoles(@Param("username") String username);


}
