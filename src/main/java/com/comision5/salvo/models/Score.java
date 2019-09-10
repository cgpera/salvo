package com.comision5.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private int score;

    private Date finishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameID")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerID")
    private Player player;

    public Score(){};

    public Score(int score, Date finishDate, Game game, Player player) {
        this.score = score;
        this.finishDate = finishDate;
        this.game = game;
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public int getScore() {
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
}
