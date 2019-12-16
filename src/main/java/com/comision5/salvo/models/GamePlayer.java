package com.comision5.salvo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerID")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameID")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> ships;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvoes;


    public GamePlayer(){
        this.joinDate = new Date();
        this.ships  = new HashSet<>();
        this.salvoes  = new HashSet<>();
    }

    public GamePlayer(Player player, Game game) {
        this.joinDate = new Date();
        this.player = player;
        this.game = game;
        this.ships  = new HashSet<>();
        this.salvoes  = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

//    @JsonIgnore
    public Player getPlayer() {
        return player;
    }

    public Set<Ship> getShips() {
        return ships;
    }

//    @JsonIgnore
    public Game getGame() {
        return game;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public void setSalvoes(Set<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public Optional<Score>  getScore(){
        return this.getPlayer().getScore(this.getGame());}

    public Map<String, Object> makeGamePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
//        dto.put("joinDate", this.getJoinDate());
        dto.put("player", this.getPlayer().makePlayerDTO());
        return dto;
    }

    public GamePlayer getOpponent(){
        return  this.getGame().getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getId()  !=  this.getId())
                .findFirst()
                .orElse(new GamePlayer());
    }
}
