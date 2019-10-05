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
public class SalvoController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private  GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private Player player;

/*
    @RequestMapping("/games")
    public List<Map<String, Object>> getGames() {
         return gameRepository
                .findAll()
                .stream()
                .map(game -> game.makeGameDTO())
                .collect(Collectors.toList());
    }
*/

    private List<Map<String, Object>> getGamePlayerListDTO(Set<GamePlayer> gamePlayers) {
        return gamePlayers
                .stream()
                .map(gamePlayerList ->  gamePlayerList.makeGamePlayerDTO())
                .collect(Collectors.toList());
    }

    @RequestMapping("/games")
    public Map<String,Object> getGameAll(Authentication authentication){
        Map<String,  Object>  dto = new LinkedHashMap<>();

        if(isGuest(authentication)){
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
    }


    @RequestMapping("/game_view/{nn}")
    public Map<String, Object> GetGameByGamePlayerID(@PathVariable Long nn){
        GamePlayer gamePlayer = gamePlayerRepository.findById(nn).get();
        System.out.println(nn);
        System.out.println(gamePlayer.getId());
        System.out.println(gamePlayer.getGame().getId());
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getCreationDate());
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers()
                                                    .stream()
                                                    .map(gamePlayer1 -> gamePlayer1.makeGamePlayerDTO())
                                                    .collect(Collectors.toList())
        );
        dto.put("ships", gamePlayer.getShips()
                                    .stream()
                                    .map(ship -> ship.makeShipDTO())
                                    .collect(Collectors.toList())
        );

        dto.put("salvoes", gamePlayer.getGame().getGamePlayers()
                                                .stream()
                                                .flatMap(gamePlayer1 -> gamePlayer1.getSalvoes()
                                                        .stream()
                                                        .map(salvo -> salvo.makeSalvoDTO())
                                                )
                                                .collect(Collectors.toList())
        );
        return dto;
    }

    @RequestMapping("/leaderBoard")
    public  List<Map<String, Object>> leaderBoard() {
        return playerRepository.findAll()
                .stream()
                .map(Player::makePlayerScoreDTO)
                .collect(Collectors.toList());
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
}