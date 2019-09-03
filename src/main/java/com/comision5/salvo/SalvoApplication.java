package com.comision5.salvo;



import com.comision5.salvo.models.Game;
import com.comision5.salvo.models.GamePlayer;
import com.comision5.salvo.models.Player;
import com.comision5.salvo.models.Ship;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class SalvoApplication {
	public static void main(String[] args) {
		SpringApplication.run(com.comision5.salvo.SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository,	GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository) {
		return (args) -> {
			// save a couple of customers
			Player player1 = new Player("j.bauer@ctu.gov");
			playerRepository.save(player1);
			Player player2 = new Player( "c.obrian@ctu.gov");
			playerRepository.save(player2);
			Player player3 = new Player("kim_bauer@gmail.com");
			playerRepository.save(player3);
			Player player4 = new Player("t.almeida@ctu.gov");
			playerRepository.save(player4);

			Date date = new Date();
			Game game1 = new Game(date);
			gameRepository.save(game1);
			Game game2 = new Game(Date.from(date.toInstant().plusSeconds(3600)));
			gameRepository.save(game2);
			Game game3 = new Game(Date.from(date.toInstant().plusSeconds(7200)));
			gameRepository.save(game3);
			Game game4 = new Game(Date.from(date.toInstant().plusSeconds(3600*3)));
			gameRepository.save(game4);
			Game game5 = new Game(Date.from(date.toInstant().plusSeconds(3600*4)));
			gameRepository.save(game5);
			Game game6 = new Game(Date.from(date.toInstant().plusSeconds(3600*5)));
			gameRepository.save(game6);
			Game game7 = new Game(Date.from(date.toInstant().plusSeconds(3600*6)));
			gameRepository.save(game7);
			Game game8 = new Game(Date.from(date.toInstant().plusSeconds(3600*7)));
			gameRepository.save(game8);


			GamePlayer gamePlayer1 = new GamePlayer(date, player3, game2);
			gamePlayerRepository.save(gamePlayer1);
			GamePlayer gamePlayer2 = new GamePlayer(new Date(), player4, game2);
			gamePlayerRepository.save(gamePlayer2);
			GamePlayer gamePlayer3 = new GamePlayer(new Date(), player1, game3);
			gamePlayerRepository.save(gamePlayer3);
			GamePlayer gamePlayer4 = new GamePlayer(new Date(), player4, game3);
			gamePlayerRepository.save(gamePlayer4);
			GamePlayer gamePlayer5 = new GamePlayer(new Date(), player2, game1);
			gamePlayerRepository.save(gamePlayer5);
			GamePlayer gamePlayer6 = new GamePlayer(new Date(), player4, game1);
			gamePlayerRepository.save(gamePlayer6);

			GamePlayer gamePlayer7 = new GamePlayer(date, player2, game4);
			gamePlayerRepository.save(gamePlayer7);
			GamePlayer gamePlayer8 = new GamePlayer(new Date(), player1, game4);
			gamePlayerRepository.save(gamePlayer8);
			GamePlayer gamePlayer9 = new GamePlayer(new Date(), player3, game5);
			gamePlayerRepository.save(gamePlayer9);
			GamePlayer gamePlayer10 = new GamePlayer(new Date(), player1, game5);
			gamePlayerRepository.save(gamePlayer10);
			GamePlayer gamePlayer11 = new GamePlayer(new Date(), player4, game6);
			gamePlayerRepository.save(gamePlayer11);
			GamePlayer gamePlayer12 = new GamePlayer(new Date(), player3, game7);
			gamePlayerRepository.save(gamePlayer12);
			GamePlayer gamePlayer13 = new GamePlayer(new Date(), player4, game8);
			gamePlayerRepository.save(gamePlayer13);
			GamePlayer gamePlayer14 = new GamePlayer(new Date(), player3, game8);
			gamePlayerRepository.save(gamePlayer14);


			String carrier = "Carrier";
			String battleship = "Battleship";
			String submarine = "Submarine";
			String destroyer = "Destroyer";
			String patrolBoat = "Patrol Boat";
			Ship ship1 = new Ship(gamePlayer1, destroyer, Arrays.asList("H2", "H3", "H4"));
			Ship ship2 = new Ship(gamePlayer1, submarine, Arrays.asList("E1", "F1", "G1"));
			Ship ship3 = new Ship(gamePlayer1, patrolBoat, Arrays.asList("B4", "B5"));
			Ship ship4 = new Ship(gamePlayer2, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship5 = new Ship(gamePlayer2, patrolBoat, Arrays.asList("F1", "F2"));
			Ship ship6 = new Ship(gamePlayer3, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship7 = new Ship(gamePlayer3, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship8 = new Ship(gamePlayer4, submarine, Arrays.asList("A2", "A3", "A4"));
			Ship ship9 = new Ship(gamePlayer4, patrolBoat, Arrays.asList("G6", "H6"));
			Ship ship10 = new Ship(gamePlayer6, destroyer, Arrays.asList("B3", "C3", "D3"));
			Ship ship11 = new Ship(gamePlayer6, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship12 = new Ship(gamePlayer5, submarine, Arrays.asList("A2", "A3", "A4"));
			Ship ship13 = new Ship(gamePlayer5, patrolBoat, Arrays.asList("G6", "H6"));
			Ship ship14 = new Ship(gamePlayer7, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship15 = new Ship(gamePlayer7, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship16 = new Ship(gamePlayer8, submarine, Arrays.asList("A2","A3", "A4"));
			Ship ship17 = new Ship(gamePlayer8, patrolBoat, Arrays.asList("G6", "H6"));
			Ship ship18 = new Ship(gamePlayer9, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship19 = new Ship(gamePlayer9, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship20 = new Ship(gamePlayer10, submarine, Arrays.asList("A2","A3", "A4"));
			Ship ship21 = new Ship(gamePlayer10, patrolBoat, Arrays.asList("G6", "H6"));
			Ship ship22 = new Ship(gamePlayer11, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship23 = new Ship(gamePlayer11, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship24 = new Ship(gamePlayer13, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship25 = new Ship(gamePlayer13, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship26 = new Ship(gamePlayer14, submarine, Arrays.asList("A2", "A3", "A4"));
			Ship ship27 = new Ship(gamePlayer14, patrolBoat, Arrays.asList("G6", "H6"));

			shipRepository.save(ship1);
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);
			shipRepository.save(ship6);
			shipRepository.save(ship7);
			shipRepository.save(ship8);
			shipRepository.save(ship9);
			shipRepository.save(ship10);
			shipRepository.save(ship11);
			shipRepository.save(ship12);
			shipRepository.save(ship13);
			shipRepository.save(ship14);
			shipRepository.save(ship15);
			shipRepository.save(ship16);
			shipRepository.save(ship17);
			shipRepository.save(ship18);
			shipRepository.save(ship19);
			shipRepository.save(ship20);
			shipRepository.save(ship21);
			shipRepository.save(ship22);
			shipRepository.save(ship23);
			shipRepository.save(ship24);
			shipRepository.save(ship25);
			shipRepository.save(ship26);
			shipRepository.save(ship27);


			System.out.println();
		};
	}
}
