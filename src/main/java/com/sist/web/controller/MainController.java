package com.sist.web.controller;

import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.web.service.MemberService;
import com.sist.web.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final MemberService mService;
	
	@GetMapping("/")
	public String main(Authentication auth,Model model) {
		boolean isLogin=auth!=null && auth.isAuthenticated() && auth.getPrincipal().toString().equals("annonymousUser")==false;
		model.addAttribute("isLogin",isLogin);
		if(isLogin) {
			String username=auth.getName();
			MemberVO vo=mService.findByUsername(username);
			String name=vo.getName();
			String role=auth.getAuthorities().iterator().next().getAuthority();
			model.addAttribute("username",name);
			model.addAttribute("role", role);
		}
		return "main/main";
	}
	@GetMapping("/member/login")
	public String member_login(Model model) {
		return "member/login";
	}
}
