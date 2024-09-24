package com.example.springprojectexample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "province")
public class Province {
    @Id
    private String id;

    private String provinceName;
}
