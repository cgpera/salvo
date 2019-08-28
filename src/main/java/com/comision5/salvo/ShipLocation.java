package com.comision5.salvo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ShipLocation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;
  private String cell;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ship_id")
  private Long shipID;

  public ShipLocation() {};

}