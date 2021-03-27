package kr.ac.ks.hellospring.repository;

import kr.ac.ks.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;
    //jpa는 entitymanager로 동작한다. 스프링부트가 생성해준다. 그 만들어진걸 인젝션 받으면 된다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
        //이렇게만 해도 jpa가 insert 쿼리 다 만들어서 member에 있는 setId까지 다해준다.
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();   //jpql 이라고 부름. 스프링 데이터 jpa를 사용하면 jpql 안짜도 된다.

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
        //밑에 코드 result에서 ctrl + alt + N 하면 밑에 두줄이 위에 코드처럼 inline으로 합쳐진다.
        //List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        //return result;
        //객체(엔티티, Member)를 대상으로 쿼리를 날린다. select 대상이 Member 엔티티 자체다.

    }
}
