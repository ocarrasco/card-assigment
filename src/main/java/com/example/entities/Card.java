package com.example.entities;

import com.example.entities.enums.CardStatus;
import com.example.entities.enums.converters.CardStatusConverter;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    private String name;

    private String color;

    private String description;

    @Convert(converter = CardStatusConverter.class)
    private CardStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private RegisteredUser owner;

}
