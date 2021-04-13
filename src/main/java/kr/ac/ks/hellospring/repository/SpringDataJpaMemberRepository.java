package kr.ac.ks.hellospring.repository;

import kr.ac.ks.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//interface가 implements를 받을 땐 extends / interface는 다중 상속이 된다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //스프링 데이터 JPA가 인터페이스에 대한 구현체를 만들어내고 스프링 빈에 알아서 등록해둔다.

    @Override
    Optional<Member> findByName(String name);
    //fincByName, findByEmail 처럼 메서드 이름만 맞춰주면 알아서
    //select m from Member m where m.name = ? 라는 쿼리로 만들어준다.
    //복잡한 동적 쿼리 => Querydsl 라이브러리 사용하면 된다.
}
