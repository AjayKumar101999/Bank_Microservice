package com.eazybank.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass   // used to add this columns in subclass entities.
@Getter@Setter
@EntityListeners(AuditingEntityListener.class)  //to tell spring this field have to fill data
public class BaseEntity {

    @Column(updatable = false) // this column will not be updated after first time insertion.
    private LocalDate createdAt;

    @Column(updatable = false) // this column will not be updated after first time insertion.
    private String createdBy;

    @Column(insertable = false) // this column will not be inserted at first time.
    private LocalDate updatedAt;

    @Column(insertable = false) // this column will not be inserted at first time.
    private String updatedBy;
}
