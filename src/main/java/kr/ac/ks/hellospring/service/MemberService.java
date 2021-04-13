package kr.ac.ks.hellospring.service;

import kr.ac.ks.hellospring.domain.Member;
import kr.ac.ks.hellospring.repository.MemberRepository;
import kr.ac.ks.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
//@Service => 스프링 빈을 등록하는 2가지 방법 중 컴포넌트 스캔 방식.
//다른 하나는 자바코드로 직접 스프링 빈을 등록하는 방식이 있다. (SpringConfig 파일) 이게 더 중요!
//@Service를 넣어주면 스프링이 올라올 때, 스프링 컨테이너에 MemberService를 등록해준다.
@Transactional  //jpa를 사용하려면 트랜잭션이 꼭 있어야한다!
public class MemberService {
    //ctrl+shift+T 누르면 자동으로 Test 껍데기 만들어준다
    private final MemberRepository memberRepository;

    //@Autowired 컴포넌트 스캔 방식
    //스프링이 뜰 때 Autowired가 있으면 MemberRepository를 넣어준다(주입). 서로 연결하게 해준다.
    //memberController -> memberService -> memberRepository 라서 DI가 필요하다.
    public MemberService(MemberRepository memberRepository) {
        /* MemberService 입장에선 memberRepository를 외부에서 넣어주는 것이다
           => 이런 것을 DI(Dependency Injection) 라고 한다! (생성자 주입) */
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {

        validateDuplicateMember(member);    //로직이 생기면 따로 분리한다 => ctrl+alt+shift+T 하고 extract method 하면 자동으로 분리해줌
        memberRepository.save(member);
        return member.getId();

//*****   시간 측정 로직(공통 관심 사항)이랑 비즈니스 로직(핵심 관심 사항)이 섞여서 유지보수 불편함.
//        => 핵심 상황을 분리해야함. AOP가 필요한 상황이다!
//        long start = System.currentTimeMillis();
//
//        try {
//            // 같은 이름이 있는 중복 회원은 안 되게!
//            validateDuplicateMember(member);    //로직이 생기면 따로 분리한다 => ctrl+alt+shift+T 하고 extract method 하면 자동으로 분리해줌
//            memberRepository.save(member);
//            return member.getId();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join = " + timeMs + "ms");
//        }


    }

    private void validateDuplicateMember(Member member) {   //중복 회원 검증
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
         /* memberRepository.findByName(member.getName())은
           Optinal로 감싸져있기 때문에 if(m==null)을 안쓰고 ifPresent 통해 사용한다.
           만약 member에 값이 있으면 이미 존재하는 회원입니다를 출력한다. */
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);

    }
}
