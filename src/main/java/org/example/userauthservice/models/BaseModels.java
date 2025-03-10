package org.example.userauthservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@MappedSuperclass
public abstract class BaseModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private Status status;

}
