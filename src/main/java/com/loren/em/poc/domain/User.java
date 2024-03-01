package com.loren.em.poc.domain;

import com.loren.em.poc.enumeration.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

import static com.loren.em.poc.constant.Constants.PUBLIC_SCHEMA;

@Data
@Entity
@Table(schema = PUBLIC_SCHEMA, name = "EM_USER")
public class User {

    @Id
    @Column(name = "USER_ID", nullable = false, length = 20)
    private String userId;

    @Column(name = "USER_NAME", nullable = false, length = 100)
    private String userName;

    @Column(name = "GENDER", nullable = false, length = 15)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "AGE")
    private Integer age;

    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}
