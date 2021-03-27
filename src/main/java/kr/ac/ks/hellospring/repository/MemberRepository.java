package kr.ac.ks.hellospring.repository;

import kr.ac.ks.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

//SQL이 정해지면 그 때 그대로 사용하기 위해 인터페이스로 만듦
public interface MemberRepository { //4가지 기능이 있는 것
    Member save(Member member); //회원을 저장하면 저장된 회원 반환
    Optional<Member> findById(Long id); //id가 null이면 Optional으로 감싸서 반환한다
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
