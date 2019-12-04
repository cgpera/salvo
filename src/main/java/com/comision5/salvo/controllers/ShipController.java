package com.comision5.salvo.controllers;

import com.comision5.salvo.models.GamePlayer;
import com.comision5.salvo.models.Player;
import com.comision5.salvo.models.Ship;
import com.comision5.salvo.repositories.GamePlayerRepository;
import com.comision5.salvo.repositories.GameRepository;
import com.comision5.salvo.repositories.PlayerRepository;
import com.comision5.salvo.repositories.ShipRepository;
import com.comision5.salvo.utils.Util;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.comision5.salvo.utils.Util.isGuest;

@RestController
@RequestMapping("/api")
public class ShipController {


    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    GameRepository gameRepository;


    @Autowired
    ShipRepository shipRepository;

    @RequestMapping(path = "/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
    public ResponseEntity putShipsAll(Authentication authentication, @PathVariable long gamePlayerId, @RequestBody List<Ship> ships) {
        Map<String, Object> dto = new LinkedHashMap<>();

        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeGameMap("User not logged", -1L), HttpStatus.UNAUTHORIZED);
        }

        GamePlayer gamePlayer = gamePlayerRepository.getOne(gamePlayerId);
        if (gamePlayer == null) {
            return new ResponseEntity<>(Util.makeGameMap("User not in Game", -1L), HttpStatus.UNAUTHORIZED);
        }

        if (!gamePlayer.getShips().isEmpty()) {
            return new ResponseEntity<>(Util.makeGameMap("Ships already in Game", -1L), HttpStatus.FORBIDDEN);
        } else {

            ships.forEach(ship -> {
                ship.setGamePlayer(gamePlayer);
                shipRepository.save(ship);
            });
        }
        return new ResponseEntity<>(Util.makeGameMap("Ships in Game", -1L), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/games/players/{gamePlayerId}/ships", method = RequestMethod.GET)
    public Map<String,Object> getGameAll(Authentication authentication, @PathVariable long gamePlayerId, @RequestBody List<Ship> ships){
        Map<String,  Object>  dto = new LinkedHashMap<>();

        if(Util.isGuest(authentication)){
            dto.put("player", "Guest");
        }else{
            Player player  = playerRepository.findByUserName(authentication.getName()).get();
            dto.put("player", player.makePlayerDTO());
        }

        dto.put("games", gameRepository.findAll()
                .stream()
                .map(game -> game.makeGameDTO())
                .collect(Collectors.toList()));
        return dto;


/*
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeGameMap("User not logged", -1L), HttpStatus.UNAUTHORIZED);
        }

        GamePlayer gamePlayer = gamePlayerRepository.getOne(gamePlayerId);
        if (gamePlayer == null) {
            return new ResponseEntity<>(Util.makeGameMap("User not in Game", -1L), HttpStatus.UNAUTHORIZED);
        }

        if (!gamePlayer.getShips().isEmpty()) {
            return new ResponseEntity<>(Util.makeGameMap("Ships already in Game", -1L), HttpStatus.FORBIDDEN);
        } else {

            ships.forEach(ship -> {
                ship.setGamePlayer(gamePlayer);
                shipRepository.save(ship);
            });
        }
        return new ResponseEntity<>(Util.makeGameMap("Ships in Game", -1L), HttpStatus.CREATED);
*/
    }


}