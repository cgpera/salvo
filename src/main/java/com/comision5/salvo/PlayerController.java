package com.comision5.salvo;

import com.comision5.salvo.models.Game;
import com.comision5.salvo.models.GamePlayer;
import com.comision5.salvo.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private GameRepository repo;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private Player player;

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String username, @RequestParam String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(username) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.CONFLICT);
        }

        playerRepository.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>("user created", HttpStatus.CREATED);
    }


    // falta
/*
    @RequestMapping("/player")
    public Player getAll(Authentication authentication) {
        return repo. (authentication.getName());
    }
*/


    /*    @RequestMapping(value = "/players", method = RequestMethod.GET)
        public Player getLoggedPlayer(Authentication authentication) {
            return playerRepository.findByUserName(authentication.getName());
        }

        private boolean isGuest(Authentication authentication) {
            return authentication == null || authentication instanceof AnonymousAuthenticationToken;
        }*/
    @RequestMapping("/players")
    public List<Object> getPlayersAll() {
        return playerRepository
                .findAll()
                .stream()
                .map(player1 -> player.makePlayerDTO())
                .collect(Collectors.toList());
    }
}