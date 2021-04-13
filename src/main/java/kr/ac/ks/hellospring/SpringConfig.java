package kr.ac.ks.hellospring;

import kr.ac.ks.hellospring.aop.TimeTraceAop;
import kr.ac.ks.hellospring.repository.*;
import kr.ac.ks.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    /*
    스프링 빈 등록 방식 중 자바코드로 직접 스프링 빈을 등록하는 방식.
    스프링이 뜰 때 memberService와 memberRepository를 스프링 빈에 등록하고,
    스프링 빈에 등록되어있는 memberRepository를 memberService에 넣어준다.
    memberService -> memberRepository로 연결해준다.
    Controlller는 따로 설정할 수가 없어서 컴포넌트 스캔 방식(@Controller)으로 사용한다.
     */

    private final MemberRepository memberRepository;

    //@Autowired  //생성자 1개일 땐 생략 가능이라 생략 한다.
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    /*
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
     */

    @Bean   //스프링이 실행될 때 @configuration 읽고 이 로직을 bean을 스프링에 등록해준다.
    public MemberService memberService() {
//        return new MemberService(memberRepository());
        return new MemberService(memberRepository);
        //ctrl+P 누르면 무슨 인스턴스가 () 안에 들어가야하는지 알려줌
    }

    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }

//    @Bean
//    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);    //Jdbc를 쓸거기 때문에 memory는 주석처리
        //return new JdbcTemplateMemberRepository(dataSource);
        //return new JpaMemberRepository(em);
        //SpringDataJapMemberRepository는 스프링 빈에 자동으로 등록된다.
//    }
}
