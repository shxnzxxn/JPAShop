package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") // 이 클래스(Album)은 부모의 @DiscriminatorColumn의 이름(dtype)을 A로 지정한다.
@Getter @Setter
public class Album extends Item{
    private String artist;
    private String etc;
}
