package com.comision5.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    private String password;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers = new HashSet<>();

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score> scores;

    public Player() { }

    public Player(String userName, String password) {

        this.userName = userName;
        this.password  = password;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = userName;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> makePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("userName", this.getUserName());
        dto.put("password", this.getPassword());
        return dto;
    }

    public Map<String,Object> makePlayerScoreDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> score = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("email", this.getUserName());
        dto.put("scores", score);
        score.put("total", this.getTotalScore());
        score.put("won", this.getWinScore());
        score.put("lost", this.getLostScore());
        score.put("tied", this.getTiedScore());
        return  dto;
    }
    public Double getTotalScore(){
        return this.getWinScore() * 1.0D + this.getTiedScore() * 0.5D;
    }

    public long  getWinScore(){
        return this.getScores().stream()
                .filter(score -> score.getScore() == 1.0D)
                .count();
    }

    public long  getLostScore(){
        return this.getScores().stream()
                .filter(score -> score.getScore() == 0.0D)
                .count();
    }

    public long  getTiedScore(){
        return this.getScores().stream()
                .filter(score -> score.getScore() == 0.5D)
                .count();
    }

}