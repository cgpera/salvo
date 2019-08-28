package com.comision5.salvo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Ship {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "gamePlayer_id")
  private GamePlayer gamePlayerID;

  @OneToMany(mappedBy = "shipLocation", fetch = FetchType.EAGER)
  private Set<ShipLocation> shipLocations = new HashSet<>();

  public Ship(){};

  public Ship(Long gamePlayerID) {
    this.gamePlayerID = gamePlayerID;
  }

  public Long getId() {
    return id;
}



};
