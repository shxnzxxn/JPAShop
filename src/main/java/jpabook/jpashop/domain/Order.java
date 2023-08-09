package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") // 이 어노테이션을 쓰지 않으면 db의 table의 이름이 order로 된다. 이는 쿼리에서 order by의 예약어와 겹치므로 orders로 관례상 사용한다.
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 인스턴스를 생성(그러니까 주문 생성)을 할 때는 무조건 create를 강제하게 하자!! 기본 생성자를 protected로!
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Order 입장에서 Member는 다대일관계이다. (하나의 Member가 여러 주문을 할 수 있으므로!)
    @JoinColumn(name="member_id") // Member의 member_id와 연결!! 즉, fk를 설정한 것.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // cascade : 연관된 것들을 한번에 persist함. 기존에는 List의 값을 하나하나 persist하고
                                                                // 그 후에, order를 persist했지만, cascade를 ALL로 적용하면 Order만 persist하면 됨
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    //=== 연관관계 메서드 ===//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //=== 생성 메서드 ===//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //=== 비즈니스 로직===//

    // 주문 취소
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //=== 조회 로직===//

    // 전체 주문 가격 조회
    public int getTotalPrice(){
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }


}
