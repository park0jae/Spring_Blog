package com.zerozae.blog;

import com.zerozae.blog.domain.role.Role;
import com.zerozae.blog.domain.role.RoleType;
import com.zerozae.blog.repository.member.MemberRepository;
import com.zerozae.blog.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {
        initRoles();
    }

    private void initRoles() {
        RoleType[] values = RoleType.values();
        List<Role> roles = Arrays.stream(values).map(roleType -> new Role(roleType)).collect(Collectors.toList());

        roleRepository.saveAll(roles);
    }
}
