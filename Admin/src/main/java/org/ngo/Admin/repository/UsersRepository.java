package org.ngo.admin.repository;

import org.ngo.admin.entity.Admin;
import org.ngo.admin.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}
