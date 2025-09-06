package com.example.los.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agents")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Agent manager;

    private boolean available = true;
}
