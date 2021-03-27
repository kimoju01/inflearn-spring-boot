package kr.ac.ks.hellospring.service;

import kr.ac.ks.hellospring.domain.Member;
import kr.ac.ks.hellospring.repository.MemberRepository;
import kr.ac.ks.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//스프링까지 사용하는 통합 테스트
@SpringBootTest //스프링 컨테이너와 테스트를 함께 시작한다.
@Transactional
//@Transactional : 테스트 코드에서 사용하면 Test 시작 전에 트랜잭션을 시작하고, 테스트가 끝나면 항상 롤백 해줘서
//DB에서 테스트 한 기록(데이터)을 깔끔하게 지워주므로 다음 테스트에 영향을 주지 않는다.
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService; //Test는 굳이 생성자주입으로 안하고 필드주입으로 해도 상관 없다. (편하게)
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {   // Test는 메서드 명을 한글로 해도 상관 X
        //given-when-then 문법: 무언가가 주어졌을 때(given), 이걸 실행했을 때(when), 결과가 이게 나와야해!(then)
        //given
        Member member = new Member();
        member.setName("hello1");

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
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");

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

}