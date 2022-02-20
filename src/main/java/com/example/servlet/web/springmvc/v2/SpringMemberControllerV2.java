package com.example.servlet.web.springmvc.v2;

import com.example.servlet.domain.member.Member;
import com.example.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        System.out.println("SpringMemberFormControllerV1.process");
        return new ModelAndView("new-form");
    }

    @RequestMapping
    public ModelAndView members() {
        final List<Member> members = memberRepository.findAll();
        return new ModelAndView("members")
                .addObject("members", members);
    }

    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String username = request.getParameter("username");
        final int age = Integer.parseInt(request.getParameter("age"));

        final Member member = new Member(username, age);
        this.memberRepository.save(member);

        // Model에 데이터를 보관
        final ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member", member);
        return mv;
    }
}
