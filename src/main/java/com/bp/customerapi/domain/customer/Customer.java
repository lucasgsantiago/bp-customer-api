package com.bp.customerapi.domain.customer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Document("customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Customer implements Serializable {

    @Id
    @Column(length = 36)
    private String id;

    @NotNull(message = "name is required")
    @Column(length = 60)
    private String name;

    @NotNull(message = "Cpf is required")
    @Column(length = 11, unique = true)
    private String cpf;

    @NotNull(message = "Address is required")
    @Column(length = 254)
    private String address;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;

    public Customer(String id, String name, String cpf, String address) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.creationDate = this.updateDate = LocalDateTime.now();
    }

    public void update(String name, String address) {
        this.name = name;
        this.address = address;
        this.updateDate = LocalDateTime.now();
    }
}
