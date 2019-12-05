package com.comision5.salvo.controllers;

import com.comision5.salvo.models.GamePlayer;
import com.comision5.salvo.models.Ship;
import com.comision5.salvo.repositories.GamePlayerRepository;
import com.comision5.salvo.repositories.GameRepository;
import com.comision5.salvo.repositories.ShipRepository;
import com.comision5.salvo.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShipController {


    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    GameRepository gameRepository;


    @Autowired
    ShipRepository shipRepository;

    @RequestMapping(path = "/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Object> putShipsAll(Authentication authentication, @PathVariable long gamePlayerId, @RequestBody List<Ship> ships) {
        Map<String, Object> dto = new LinkedHashMap<>();


        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>("User not logged", HttpStatus.UNAUTHORIZED);
        }

        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).orElse(null);
        if (gamePlayer == null) {
            return new ResponseEntity<Object>("User not exist", HttpStatus.UNAUTHORIZED);
        }

        if (!gamePlayer.getShips().isEmpty()) {
            return new ResponseEntity<Object>("Ships already in Game", HttpStatus.FORBIDDEN);
        }
        else {
            ships.forEach(ship -> {
                ship.setGamePlayer(gamePlayer);
                shipRepository.save(ship);
            });
        }
        return new ResponseEntity<Object>("Ships in Game", HttpStatus.CREATED);
    }
}