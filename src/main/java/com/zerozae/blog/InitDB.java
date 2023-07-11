package com.zerozae.blog;

import com.zerozae.blog.domain.member.Member;
import com.zerozae.blog.domain.role.Role;
import com.zerozae.blog.domain.role.RoleType;
import com.zerozae.blog.exception.role.RoleNotFoundException;
import com.zerozae.blog.repository.member.MemberRepository;
import com.zerozae.blog.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {
        initRoles();
        initAdmin();
        initManager();
        initMember();
    }

    private void initRoles() {
        RoleType[] values = RoleType.values();
        List<Role> roles = Arrays.stream(values).map(roleType -> new Role(roleType)).collect(Collectors.toList());

        roleRepository.saveAll(roles);
    }

    @Transactional
    public void initAdmin() {
        memberRepository.save(new Member(
                "admin",
                passwordEncoder.encode("qwer1234!"),
                "None",
                List.of(roleRepository.findByRoleType(RoleType.ADMIN).orElseThrow(RoleNotFoundException::new),
                        roleRepository.findByRoleType(RoleType.MANAGER).orElseThrow(RoleNotFoundException::new),
                        roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new)
                ),
                List.of()
        ));
    }


    @Transactional
    public void initManager() {
        memberRepository.save(new Member(
                "manager",
                passwordEncoder.encode("qwer1234!"),
                "None",
                List.of(roleRepository.findByRoleType(RoleType.MANAGER).orElseThrow(RoleNotFoundException::new),
                        roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new)
                ),
                List.of()
        ));
    }

    @Transactional
    public void initMember() {
        memberRepository.save(new Member(
                "user",
                passwordEncoder.encode("qwer1234!"),
                "None",
                List.of(roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new)),
                List.of()
        ));
    }

}
