package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // 여기를 EnumType.ORDINAL이 default인데, 이렇게 하면 나중에 상태를 갱신하고자 할 때, db에 있는 값이 다른 상태를 나타낼 수 있음.
    private DeliveryStatus status; // [READY, COMP]
}
