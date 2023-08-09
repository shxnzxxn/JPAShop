package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // AllArgsConstructor 어노테이션은 필드에 있는 모든 데이터를 필요로 하지만, 이 어노테이션은 final이 붙은 필드만 생성자로 만들어준다.
public class MemberService {

    // 생성자 주입을 통해서 진행한다. 의존관계 세팅은 보통 최초 1회만 실행되는 경우가 많으므로 final로 지정해준다.
    // 또한 생성자 주입을 통해서, 목 객체를 던져주는 등의 테스트를 간편하게 해줄 수 있으므로, 이 방법을 권장한다.
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 같은 아이디는 등록할 수 없음.
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하 회원입니다.");
        }
    }

    // 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
