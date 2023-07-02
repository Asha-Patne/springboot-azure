package com.java.springboot.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERFLAG")
public class UserFlag {

    @Id
    @Column(name="ID")
    private Long id;

    @Column(name="TYPE")
    private String type;

    @Column(name="VALUE")
    private String value;

}
