package kr.ac.ks.hellospring.controller;

import kr.ac.ks.hellospring.domain.Member;
import kr.ac.ks.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    /*
    private final MemberService memberService = new MemberService();
    이런 식으로 객체를 new를 할 필요가 없다. 왜? MemberService에 보면 별 기능이 없으니까 여러개 만들 필요가 없다.
    그래서 하나만 생성해서 같이 공용으로 쓰면 된다.
    그렇기 때문에 스프링 컨테이너로부터 받아서 쓰도록 바꿔야한다.
    연결을 어떻게 해주느냐? 생성자를 만들고 @Autowired 한다.
     */

    private final MemberService memberService;
    //멤버컨트롤러가 멤버서비스를 의존한다.

    @Autowired //Contoroller가 컴포넌트 스캔 방식이기 때문에 @Autowired 해준다.
    //@Autowired는 MemberController가 생성될 때,
    //스프링 Bean(컨테이너)에 등록되어 있는 memberService 객체를 가져와서 넣어준다. => DI
    //컨트롤러와 서비스를 연결시켜줘야한다. memberController -> memberService -> memberRepository
    public MemberController(MemberService memberService) {
        this.memberService = memberService; //생성자 주입(DI)
    }

    @GetMapping("/members/new")     //주소창 치면 나오는 건 보통 get 매핑
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")    //createMemberForm.html에서 form 채우면 post 매핑으로 넘어간다.
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        //System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/";    //home 화면으로 redirect
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
