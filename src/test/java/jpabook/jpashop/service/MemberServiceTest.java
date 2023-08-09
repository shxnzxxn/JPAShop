package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit을 실행할 때, 스프링이랑 같이 엮어서 실행할래!
@SpringBootTest
@Transactional // 테스트에서 이 어노테이션은, 기본적으로 롤백을 해준다.
public class MemberServiceTest {
    // 필드주입을 권장하지는 않지만 테스트코드에서는 ㄱㅊ
    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("shin");

        // when
        Long saveId = memberService.join(member);

        // then
        assertEquals(member, memberService.findOne(saveId));

    }
    
    @Test(expected = IllegalStateException.class) // 이 메서드에서 해당 클래스의 exception이 터지면 성공!
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("shin");
        Member member2 = new Member();
        member2.setName("shin");

        // when
        memberService.join(member1);
        memberService.join(member2); // 오류가 발생해야한다.

        // then
        fail("예외가 발생해야 합니다."); // 여기까지 도달하면 실패!
    
    }
        
        
}