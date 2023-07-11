package com.zerozae.blog.repository.role;

import com.zerozae.blog.domain.role.Role;
import com.zerozae.blog.domain.role.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByRoleType(RoleType roleType);
}
