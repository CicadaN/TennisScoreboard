package io.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Matches")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Transient
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "PLAYER1", nullable = false)
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "PLAYER2", nullable = false)
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "WINNER", nullable = false)
    private Player winner;
}
