package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // 이 클래스(Book)은 부모의 @DiscriminatorColumn의 이름(dtype)을 B로 지정한다.
@Getter @Setter
public class Book extends Item{
    private String author;
    private String isbn;
}
