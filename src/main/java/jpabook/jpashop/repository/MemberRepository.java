package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext 스프링 JPA에서는 이 어노테이션 대신 @Autowired를 사용할 수 있게 해준다!!
    // 따라서 우리는 @Autowired를 사용하고, 생성자가 한개만 되므로, @RequiredArgsConstructor 어노테이션을 사용하면 된다.
    // 그리고 EntityManager에 final을 넣어주면 더 간단해진다!!
    private final EntityManager em;

    public void save(Member member){em.persist(member);}

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
