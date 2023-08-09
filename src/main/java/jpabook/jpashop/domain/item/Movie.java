package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M") // 이 클래스(Movie)은 부모의 @DiscriminatorColumn의 이름(dtype)을 M으로 지정한다.
@Getter @Setter
public class Movie extends Item{
    private String director;
    private String actor;
}
