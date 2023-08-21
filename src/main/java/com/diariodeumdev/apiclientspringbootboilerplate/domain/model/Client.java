package com.diariodeumdev.apiclientspringbootboilerplate.domain.model;

import javax.persistence.*;

@Table(name = "clients")
@Entity(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
