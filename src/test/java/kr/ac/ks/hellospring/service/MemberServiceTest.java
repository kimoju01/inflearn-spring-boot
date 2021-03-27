package kr.ac.ks.hellospring.service;

import kr.ac.ks.hellospring.domain.Member;
import kr.ac.ks.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//순수 자바코드 Test는 단위테스트 라고한다. 스프링까지 올리는 통합테스트에 비해 속도도 빠르고, 훨씬 좋은 테스트일 확률이 높다!
class MemberServiceTest {
    /*
    MemberService memberService = new MemberService();
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    이런식으로 new를 쓰면 다른 객체기 때문에 다른 인스턴스기 때문에 내용물이 달라질 수도 있다.
    같은 인스턴스를 사용하는 것이 좋다!
    => MemberService 클래스에
    private final MemberRepository memberRepository = new MemoryMemberRepository(); 를
    private final MemberRepository memberRepository; 로 바꾸고 생성자를 만든다. (외부에서 주입하도록 = DI)
    */

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach //각 테스트 실행되기 전에 실행된다
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();    //같은 MemoryMemberRepository가 사용된다.
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {   // Test는 메서드 명을 한글로 해도 상관 X
        //given-when-then 문법: 무언가가 주어졌을 때(given), 이걸 실행했을 때(when), 결과가 이게 나와야해!(then)
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

        //then
        /* [방법1] */
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // () -> memberService.join(member2) 로직을 실행할때 IllegalStateException이 발생하는지 아닌지

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /* [방법2]
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
         */
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}