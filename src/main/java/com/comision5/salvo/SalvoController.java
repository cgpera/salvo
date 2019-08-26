package com.comision5.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Id;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository repo;
//    private GamePlayer gamePlayers

    private PlayerRepository playerRepository;
    private Player player;

/*

// solo muestra los game id

    @RequestMapping("/games")
    public Set<Object> getAll() {
        return
                repo.findAll()
                .stream().map(Game::getId)
                .collect(Collectors.toSet());
    }*/

@RequestMapping("/games")
public List<Map<String, Object>> getGames() {
    { return repo
            .findAll()
            .stream()
            .map(game -> makeGameDTO(game))
            .collect(Collectors.toList());
    }
}

    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate().getTime());
        dto.put("gamePlayers", getGamePlayerListDTO((Set<GamePlayer>) game.getGamePlayers()));
        return dto;
    }


    // Fijate si lo que te devuelve game.getGamePlayers es un set o un list ??
    private List<Map<String, Object>> getGamePlayerListDTO(Set<GamePlayer> gamePlayers) {
        return gamePlayers
                .stream()
                .map(gamePlayerList ->  makeGamePlayerDTO(gamePlayerList))
                .collect(Collectors.toList());
    }
    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("joinDate", gamePlayer.getJoinDate().getTime());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("userName", player.getUserName());
        return dto;
    }

}