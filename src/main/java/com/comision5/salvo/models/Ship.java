package com.comision5.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Ship {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  private String type;

  @ElementCollection
  @Column(name="shipLocations")
  private List<String> shipLocations;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "gamePlayerID")
  private GamePlayer gamePlayer;


  public Ship(){
    this.shipLocations  = new ArrayList<>();
  };

  public Ship(GamePlayer gamePlayer, String type, List<String> shipLocations) {
    this.type = type;
    this.shipLocations = shipLocations;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<String> getShipLocations() {
    return shipLocations;
  }

  public void setShipLocations(List<String> shipLocations) {
    this.shipLocations = shipLocations;
  }

  public Map<String, Object> makeShipDTO(){
    Map<String, Object> dto = new LinkedHashMap<>();
    dto.put("type", this.type);
    dto.put("locations", this.getShipLocations());
    return dto;
  }
};
