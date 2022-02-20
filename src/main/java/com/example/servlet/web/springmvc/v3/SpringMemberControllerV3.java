package com.example.servlet.web.springmvc.v3;

import com.example.servlet.domain.member.Member;
import com.example.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping(value = "/new-form")
    public String newForm() {
        System.out.println("SpringMemberFormControllerV1.process");
        return "new-form";
    }

    @GetMapping
    public String members(Model model) {
        final List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    @PostMapping(value = "/save")
    public String save(@RequestParam("username") String username, @RequestParam("age") int age, Model model) {
        final Member member = new Member(username, age);
        this.memberRepository.save(member);

        // Model에 데이터를 보관
        model.addAttribute("member", member);
        return "save-result";
    }
}
