package com.example.servlet.web.frontcontroller.v3.controller;

import com.example.servlet.domain.member.Member;
import com.example.servlet.domain.member.MemberRepository;
import com.example.servlet.web.frontcontroller.ModelView;
import com.example.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        final String username = paramMap.get("username");
        final int age = Integer.parseInt(paramMap.get("age"));

        final Member member = new Member(username, age);
        this.memberRepository.save(member);

        // Model에 데이터를 보관
        final ModelView modelView = new ModelView("save-result");
        modelView.getModel().put("member", member);
        return modelView;
    }
}
