package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.AuthorityMapper;
import com.sist.web.mapper.MemberMapper;
import com.sist.web.vo.AuthorityVO;
import com.sist.web.vo.MemberVO;

import lombok.RequiredArgsConstructor;
/*
 *           |Security
 *    User <----> Controller <----> Service <----> Repository ---- DB
 *    => 의존성이 낮은 프로그램
 *       ---- 결합성 => 다른 클래스에 영향 X
 *       ---- 유지보수용 주로 사용
 */

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberMapper mMapper;
	private final AuthorityMapper aMapper;
	
	@Override
	public MemberVO findByUsername(String username) {
		return mMapper.findByUsername(username);
	}

	@Override
	public List<AuthorityVO> getAuthrityData(int member_id) {
		return aMapper.getAuthrityData(member_id);
	}
	
	
}
