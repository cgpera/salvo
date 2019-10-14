package com.comision5.salvo.controllers;

import com.comision5.salvo.repositories.PlayerRepository;
import com.comision5.salvo.models.Player;
import com.comision5.salvo.repositories.GamePlayerRepository;
import com.comision5.salvo.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> register(@RequestParam String name, @RequestParam String pwd) {
        if (name.isEmpty() || pwd.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(name).orElse(null) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(name, passwordEncoder.encode(pwd)));
        return new ResponseEntity<>("user created", HttpStatus.CREATED);
    }

/*    @RequestMapping("/players")
    public List<Object> getPlayersAll() {
        return playerRepository
                .findAll()
                .stream()
                .map(player -> player.makePlayerDTO())
                .collect(Collectors.toList());
    }

 */
}