package com.zerozae.blog.repository.role;

import com.zerozae.blog.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
