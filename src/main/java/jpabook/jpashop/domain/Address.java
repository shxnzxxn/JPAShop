package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 내장 타입이라는 것을 알려주는 어노테이션. 사실 Member의 Address에 @Embedded를 넣으면 안써도 되지만, 둘 다 쓰자!! 가독성을 위해
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
