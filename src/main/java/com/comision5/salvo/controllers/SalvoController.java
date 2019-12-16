package com.comision5.salvo.controllers;

import com.comision5.salvo.models.Salvo;
import com.comision5.salvo.repositories.*;
import com.comision5.salvo.models.Game;
import com.comision5.salvo.models.GamePlayer;
import com.comision5.salvo.models.Player;
import com.comision5.salvo.utils.GameState;
import com.comision5.salvo.utils.Util;
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
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @Autowired
    private ShipRepository shipRepository;

//    private Player player;

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

    @RequestMapping(path="/games", method = RequestMethod.GET)
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

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {
        if(isGuest(authentication)){
            return new ResponseEntity<>(makeGameMap("not logged", -1L), HttpStatus.FORBIDDEN);
        }else{
            Player player  = playerRepository.findByUserName(authentication.getName()).get();
            Date creationDate = new Date();
            Game game = new Game(creationDate);
            GamePlayer gamePlayer = new GamePlayer(player, game);
            gameRepository.save(game);
            gamePlayerRepository.save(gamePlayer);
            return new ResponseEntity<>(makeGameMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
        }
    }

    @RequestMapping(path = "/games/{id}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long id, Authentication authentication ) {
        System.out.println(id);
        if(isGuest(authentication)){
            return new ResponseEntity<>(makeGameMap("not logged", -1L), HttpStatus.FORBIDDEN);
        }
        if(!gameRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(makeGameMap("Game not exist", -1L), HttpStatus.FORBIDDEN);
        }
        Game game = gameRepository.findById(id).get();
        if(game.getGamePlayers().size() == 2) {
            return new ResponseEntity<>(makeGameMap("Game full", -1L), HttpStatus.FORBIDDEN);
        }
        Player player  = playerRepository.findByUserName(authentication.getName()).get();
        boolean isMember = game.getGamePlayers().stream().anyMatch(gamePlayer -> gamePlayer.getPlayer().getId().equals(player.getId()));

        if(isMember) {
            return new ResponseEntity<>(makeGameMap("Player can't join himself", -1L), HttpStatus.FORBIDDEN);
        }
        Date creationDate = new Date();
        GamePlayer gamePlayer = new GamePlayer(player, game);
        gamePlayerRepository.save(gamePlayer);
        return new ResponseEntity<>(makeGameMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
    }


    @RequestMapping(path = "/game_view/{nn}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> GetGameByGamePlayerID(@PathVariable Long nn, Authentication authentication){

        if(Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error", "Not logged"), HttpStatus.UNAUTHORIZED);
        }

        Player playerLogged = playerRepository.findByUserName(authentication.getName()).orElse(null);
        GamePlayer gamePlayer = gamePlayerRepository.findById(nn).orElse(null);

        if(playerLogged == null) {
            return new ResponseEntity<>(Util.makeMap("error", "User not found"), HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer == null) {
            return new ResponseEntity<>(Util.makeMap("error", "User not found"), HttpStatus.UNAUTHORIZED);
        }

        if(!gamePlayer.getPlayer().getId().equals(playerLogged.getId())) {
            return new ResponseEntity<>(Util.makeMap("error", "Users not corresponding"), HttpStatus.CONFLICT);
        }

        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> hits = new LinkedHashMap<>();

        hits.put("self", getHits(gamePlayer, gamePlayer.getOpponent()));
        hits.put("opponent", getHits(gamePlayer.getOpponent(), gamePlayer));

        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getCreationDate());

        dto.put("gameState", getGameState(gamePlayer));
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers()
                                                    .stream()
                                                    .map(GamePlayer::makeGamePlayerDTO)
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
 //       hits.put("self", new getHits());
 //       hits.put("opponent", new ArrayList<>());
        dto.put("hits", hits);
//        dto.put("gameState", "PLACESHIPS");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping("/leaderBoard")
    public  List<Map<String, Object>> leaderBoard() {
        return playerRepository.findAll()
                .stream()
                .map(Player::makePlayerScoreDTO)
                .collect(Collectors.toList());
    }


    @RequestMapping(path = "/games/players/{gpid}/salvoes", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addSalvo(@PathVariable Long gpid, @RequestBody Salvo salvo, Authentication authentication ) {
        if (isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("Error", "not logged"), HttpStatus.UNAUTHORIZED);
        }

        Player player = playerRepository.findByUserName(authentication.getName()).orElse(null);
        if (player == null) {
            return new ResponseEntity<>(Util.makeMap("Error", "User not authorized"), HttpStatus.UNAUTHORIZED);
        }

        GamePlayer self = gamePlayerRepository.getOne(gpid);
        if (self == null) {
            return new ResponseEntity<>(Util.makeMap("Error", "User not authorized"), HttpStatus.UNAUTHORIZED);
        }

        if (!player.getId().equals(self.getPlayer().getId())) {
            return new ResponseEntity<>(Util.makeMap("Error", "User not that init session"), HttpStatus.FORBIDDEN);
        }

        GamePlayer opponent = getOpponent(self);
        if(self.getSalvoes().size() <= opponent.getSalvoes().size()) {
            salvo.setTurn(self.getSalvoes().size()+1);
            salvo.setGamePlayer(self);
            salvoRepository.save(salvo);
        }
        return new ResponseEntity<>(Util.makeMap("Salvo Created", "Salvo OK"), HttpStatus.CREATED);
    }



    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private Map<String, Object> makeGameMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private GamePlayer getOpponent(GamePlayer self) {
        return self.getGame().getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getId() != self.getId()).findFirst().orElse(new GamePlayer());
    }

    private List<Map> getHits(GamePlayer self, GamePlayer opponent) {
        List<Map> hits = new ArrayList<>();
        Integer carrierDamage = 0;
        Integer battleshipDamage = 0;
        Integer submarineDamage = 0;
        Integer destroyerDamage = 0;
        Integer patrolboatDamage = 0;

        List <String> carrierLocation = getLocationsByType("carrier",self);
        List <String> battleshipLocation = getLocationsByType("battleship",self);
        List <String> submarineLocation = getLocationsByType("submarine",self);
        List <String> destroyerLocation = getLocationsByType("destroyer",self);
        List <String> patrolboatLocation = getLocationsByType("patrolboat",self);

        for (Salvo  salvo : opponent.getSalvoes()){

            long carrierHitsInTurn = 0;
            long battleshipHitsInTurn = 0;
            long submarineHitsInTurn = 0;
            long destroyerHitsInTurn = 0;
            long patrolboatHitsInTurn = 0;
            long missedShots = salvo.getSalvoLocations().size();

            Map<String, Object> hitsMapPerTurn = new LinkedHashMap<>();
            Map<String, Object> damagesPerTurn = new LinkedHashMap<>();

            List<String> salvoLocationsList = new ArrayList<>();
            List<String> hitCellsList = new ArrayList<>();

            for (String salvoShot : salvo.getSalvoLocations()) {
                if (carrierLocation.contains(salvoShot)) {
                    carrierDamage++;
                    carrierHitsInTurn++;
                    hitCellsList.add(salvoShot);
                    missedShots--;
                }
                if (battleshipLocation.contains(salvoShot)) {
                    battleshipDamage++;
                    battleshipHitsInTurn++;
                    hitCellsList.add(salvoShot);
                    missedShots--;
                }
                if (submarineLocation.contains(salvoShot)) {
                    submarineDamage++;
                    submarineHitsInTurn++;
                    hitCellsList.add(salvoShot);
                    missedShots--;
                }
                if (destroyerLocation.contains(salvoShot)) {
                    destroyerDamage++;
                    destroyerHitsInTurn++;
                    hitCellsList.add(salvoShot);
                    missedShots--;
                }
                if (patrolboatLocation.contains(salvoShot)) {
                    patrolboatDamage++;
                    patrolboatHitsInTurn++;
                    hitCellsList.add(salvoShot);
                    missedShots--;
                }
            }

            damagesPerTurn.put("carrierHits", carrierHitsInTurn);
            damagesPerTurn.put("battleshipHits", battleshipHitsInTurn);
            damagesPerTurn.put("submarineHits", submarineHitsInTurn);
            damagesPerTurn.put("destroyerHits", destroyerHitsInTurn);
            damagesPerTurn.put("patrolboatHits", patrolboatHitsInTurn);
            damagesPerTurn.put("carrier", carrierDamage);
            damagesPerTurn.put("battleship", battleshipDamage);
            damagesPerTurn.put("submarine", submarineDamage);
            damagesPerTurn.put("destroyer", destroyerDamage);
            damagesPerTurn.put("patrolboat", patrolboatDamage);

            hitsMapPerTurn.put("turn", salvo.getTurn());
            hitsMapPerTurn.put("hitLocations", hitCellsList);
            hitsMapPerTurn.put("damages", damagesPerTurn);
            hitsMapPerTurn.put("missed", missedShots);
            hits.add(hitsMapPerTurn);

        };

        return hits;
    }

    private GameState getGameState (GamePlayer gamePlayer) {

        if (gamePlayer.getShips().size() == 0) {
            return GameState.PLACESHIPS;
        }
        if (gamePlayer.getGame().getGamePlayers().size() == 1){
            return GameState.WAITINGFOROPP;
        }
        if (gamePlayer.getGame().getGamePlayers().size() == 2) {

            GamePlayer opponentGp = gamePlayer.getOpponent();

            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (getIfAllSunk(opponentGp, gamePlayer)) && (!getIfAllSunk(gamePlayer, opponentGp))) {
                return GameState.WON;
            }
            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (getIfAllSunk(opponentGp, gamePlayer)) && (getIfAllSunk(gamePlayer, opponentGp))) {
                return GameState.TIE;
            }
            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (!getIfAllSunk(opponentGp, gamePlayer)) && (getIfAllSunk(gamePlayer, opponentGp))) {
                return GameState.LOST;
            }

            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (gamePlayer.getId() < opponentGp.getId())) {
                return GameState.PLAY;
            }
            if (gamePlayer.getSalvoes().size() < opponentGp.getSalvoes().size()){
                return GameState.PLAY;
            }
            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (gamePlayer.getId() > opponentGp.getId())) {
                return GameState.WAIT;
            }
            if (gamePlayer.getSalvoes().size() > opponentGp.getSalvoes().size()){
                return GameState.WAIT;
            }

        }
        return GameState.UNDEFINED;
    }

    private List<String>  getLocationsByType(String type, GamePlayer self){
        return  self.getShips().size()  ==  0 ? new ArrayList<>() : self.getShips().stream().filter(ship -> ship.getType().equals(type)).findFirst().get().getShipLocations();
    }

    private Boolean getIfAllSunk (GamePlayer self, GamePlayer opponent) {

        if(!opponent.getShips().isEmpty() && !self.getSalvoes().isEmpty()){
            return opponent.getSalvoes().stream().flatMap(salvo -> salvo.getSalvoLocations().stream()).collect(Collectors.toList()).containsAll(self.getShips().stream()
                    .flatMap(ship -> ship.getShipLocations().stream()).collect(Collectors.toList()));
        }
        return false;
    }
}