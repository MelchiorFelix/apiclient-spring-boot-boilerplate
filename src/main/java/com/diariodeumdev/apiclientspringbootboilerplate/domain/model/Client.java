package com.diariodeumdev.apiclientspringbootboilerplate.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "clients")
@Entity(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Client(String name) {
        this.name = name;
    }
}
