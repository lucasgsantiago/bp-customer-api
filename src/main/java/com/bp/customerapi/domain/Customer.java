package com.bp.customerapi.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

    private String id;
    private String name;
    private String cpf;
    private String address;
    private Date createDate;
    private Date updateDate;

    public Customer(String id, String name, String cpf, String address) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.address = address;
    }

    public void update(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
