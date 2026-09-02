package com.sist.web.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

import com.sist.web.vo.AuthorityVO;
import com.sist.web.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberService mService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO member=mService.findByUsername(username);
		if(member==null) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다 :"+username);
		}
		if(member.getEnabled()!=1) {
			throw new UsernameNotFoundException("휴면 계정입니다");
		}
		List<AuthorityVO> authList=mService.getAuthrityData(member.getMember_id());
		
		// 권한 => SpringSecurity로 변환
		List<SimpleGrantedAuthority> authorities= authList.stream()
														.map(a->new SimpleGrantedAuthority(a.getAuthority()))
														.toList();
		// UserDetails에 저장
		return User.builder()
					.username(member.getUsername())
					.password(member.getPassword())
					.authorities(authorities)
					.build();
	}
	
}
