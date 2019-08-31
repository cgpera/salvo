package com.comision5.salvo;

import com.comision5.salvo.models.Game;
import com.comision5.salvo.models.GamePlayer;
import com.comision5.salvo.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository repo;

    @Autowired
    private  GamePlayerRepository gamePlayerRepository;

    private PlayerRepository playerRepository;
    private Player player;

    @RequestMapping("/games")
    public List<Map<String, Object>> getGames() {
         return repo
                .findAll()
                .stream()
                .map(game -> game.makeGameDTO())
                .collect(Collectors.toList());
    }



    private List<Map<String, Object>> getGamePlayerListDTO(Set<GamePlayer> gamePlayers) {
        return gamePlayers
                .stream()
                .map(gamePlayerList ->  gamePlayerList.makeGamePlayerDTO())
                .collect(Collectors.toList());
    }



    @RequestMapping("/game_view/{nn}")
    public Map<String, Object> GetGameByGamePlayerID(@PathVariable Long nn){
        GamePlayer gamePlayer = gamePlayerRepository.findById(nn).get();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getCreationDate());
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers()
                                                    .stream()
                                                    .map(gamePlayer1 -> gamePlayer1.makeGamePlayerDTO()));
                                                    return dto;
    }
}