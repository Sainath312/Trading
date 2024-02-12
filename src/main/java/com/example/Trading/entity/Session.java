package com.example.Trading.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String token;

    @Column(unique = true)
    Long userId;
}
