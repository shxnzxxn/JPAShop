package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id") // 그냥 하게되면 id라는 column의 이름으로 pk가 생성됨. 우리는 member_id라는 pk를 원하므로 column 이름을 변경해줌
    private Long Id;

    private String name;

    @Embedded // Address에서도 말했듯이, @Embeddable만 써도 되지만, 가독성을 위해 여기에도 어노테이션을 더해주자
    private Address address;

     // Member 입장에서 여러 주문을 할 수 있으므로, 일대다 관계이다. 또한 fk와 가장 가까운 것을 연관관계의 주인으로 설정하므로 주인은 Order가 되고,
     // 주인이 아닌 클래스는 mappedBy를 통해서 읽기 전용으로 바꿔준다.
     // 즉!! Member의 orders는 변경할 수 없고, 읽을 수만 있으며, 수정 자체는 Order에서 일어나야한다.
     @OneToMany(mappedBy = "member") // 나는 Order의 member 필드의 거울이야.
    private List<Order> orders = new ArrayList<>();
}
