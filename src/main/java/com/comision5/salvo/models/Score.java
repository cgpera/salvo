package com.comision5.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private double score;

    private Date finishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameID")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerID")
    private Player player;

    public Score(){
        this.finishDate = new Date();
    }

    public Score(Double score, Date finDate, Game game, Player player) {
        this.score = score;
        this.finishDate = finDate;
        this.game = game;
        this.player = player;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public long getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<String,  Object> makeScoreDTO(){
        Map<String,  Object>    dto=    new LinkedHashMap<>();
        dto.put("player",   this.getPlayer().getId());
        dto.put("score",   this.getScore());
        dto.put("finishDate", this.getFinishDate().getTime());
        return  dto;
    }


}
