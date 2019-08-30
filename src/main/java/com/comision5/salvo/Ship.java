package com.comision5.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ship {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;
  private String shipType;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "gamePlayerID")
  private GamePlayer gamePlayer;

  @ElementCollection
  @Column(name="cell")
  private List<String> cells = new ArrayList<>();

  public Ship(){};

  public Ship(GamePlayer gamePlayer) {
    this.gamePlayer = gamePlayer;
  }

  public Long getId() {
    return id;
}

  public GamePlayer getGamePlayer() {
    return gamePlayer;
  }

  public void setGamePlayer(GamePlayer gamePlayer) {
    this.gamePlayer = gamePlayer;
  }

  public String getShipType() {
    return shipType;
  }

  public void setShipType(String shipType) {
    this.shipType = shipType;
  }

  public List<String> getCells() {
    return cells;
  }

  public void setCells(List<String> cells) {
    this.cells = cells;
  }
};
