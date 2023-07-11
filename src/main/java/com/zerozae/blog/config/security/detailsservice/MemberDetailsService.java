package com.zerozae.blog.config.security.detailsservice;

import com.zerozae.blog.config.security.details.MemberDetails;
import com.zerozae.blog.domain.member.Member;
import com.zerozae.blog.domain.member.MemberRole;
import com.zerozae.blog.exception.member.MemberNotFoundException;
import com.zerozae.blog.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findOneWithRolesByUsername(username).orElseThrow(MemberNotFoundException::new);

        return createMember(member);
    }

    private UserDetails createMember(Member member) {
        List<MemberRole> roles = member.getRoles();

        List<SimpleGrantedAuthority> authorities = roles.stream().map(mr -> new SimpleGrantedAuthority(mr.getRole().getRoleType().toString())).collect(Collectors.toList());

        return new MemberDetails(String.valueOf(member.getId()),member.getUsername(), member.getPassword(), authorities);
    }
}
