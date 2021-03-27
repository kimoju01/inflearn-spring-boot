package kr.ac.ks.hellospring.repository;

import kr.ac.ks.hellospring.domain.Member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    /* Test를 한 클래스 전체적으로 run 할 수 있고, 한 메서드 마다 할 수 있는데
    클래스 전체를 run할 때 실행되는 순서는 보장할 수 X
    => 그렇기 때문에 서로서로 순서에 의존하지 않도록 설계해야한다.
    => 메서드가 하나 끝날 때 마다 공용 데이터를 clear 해야 한다.
    그 역할을 하는 게 @AfterEach
     */

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach  //메서드가 끝날 때 마다 호출된다. save가 끝나고, .. findById가 끝나고, ..
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();  //optional에서 꺼낼땐 get으로 꺼낸다.
        //Assertions.assertThat(member).isEqualTo(result); 을 Assertions(assertj)에서 alt+enter로 static import 해주면 Aseertions을 생략가능하다
        assertThat(member).isEqualTo(result);   //검증. 같으면 실행 시 초록불, 다르면 빨간불
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        
        Member member2 = new Member();  //ctrl+shift+alt+T에서 rename하거나 shift + f6 누르면 같은 변수 한번에 rename 된다.
        member2.setName("spring2");
        repository.save(member2);

        //ctrl+shift+enter 하면 그 자리에서 바로 다음 줄로 넘어감
        Member result = repository.findByName("spring1").get();//ctrl+alt+V 누르면 자동으로 타입 불러온다.

        assertThat(result).isEqualTo(member1);  //spring1 == member1 이면 실행 시 초록불
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        //repository.findAll();만 입력하고 ctrl+alt+V 누르면 자동으로
        // List<Member> result = 부분 만들어준다.

        assertThat(result.size()).isEqualTo(2);
    }
}
