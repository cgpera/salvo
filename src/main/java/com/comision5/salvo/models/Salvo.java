package com.comision5.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private int turn;

    @ElementCollection
    @Column(name="shipLocations")
    private List<String> salvoLocations;
//    private Set<String> salvoLocations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayerID")
    private GamePlayer gamePlayer;

    public Salvo(){}

    public Salvo(int turn, List<String> salvoLocations, GamePlayer gamePlayer) {
        this.turn = turn;
        this.salvoLocations = salvoLocations;
        this.gamePlayer = gamePlayer;
    }

    public long getId() {
        return id;
    }

    public int getTurn() {
        return turn;
    }

    public List<String> getSalvoLocations() {
        return salvoLocations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
