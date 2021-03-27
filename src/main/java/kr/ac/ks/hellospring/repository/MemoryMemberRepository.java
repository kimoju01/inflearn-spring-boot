package kr.ac.ks.hellospring.repository;

import kr.ac.ks.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository 컴포넌트 스캔 방식
public class MemoryMemberRepository implements MemberRepository {
    //인터페이스 implements 함

    private static Map<Long, Member> store = new HashMap<>();
    /*key, value에서 value를 String이 아닌 Memer타입으로 설정한 이유?
    => id(Long타입)을 제외하곤 name만 있지만 그 외에도 다른 정보가 들어갈 수 있어서
    Member타입으로 지정해준 것 */
    private static long sequence = 0L;  //0, 1, 2 .. key값(id)을 생성해 주는 것

    @Override
    public Member save(Member member) {
        member.setId(++sequence);   //id를 정하고
        store.put(member.getId(), member);  //store에 저장한다(map에 저장)
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));   //결과가 null일 때를 대비해 Optional으로 감싼다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() //loop를 돌린다
                .filter(member -> member.getName().equals(name))    //파라미터로 넘어온 name과 getName이 같은지 필터링
                .findAny();     //뭐 하나라도 찾아서 return 한다. 없으면 null인데 Optional이 포함되서 반환된다.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store에 있는 values가 member니까 그대로 반환
    }

    public void clearStore() {
        store.clear();
    }
}
//동작하는지 어떻게 확인하지? => Test Case 작성
