package com.comision5.salvo;



import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SalvoApplication {
	public static void main(String[] args) {
		SpringApplication.run(com.comision5.salvo.SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository,	GamePlayerRepository gamePlayerRepository) {
		return (args) -> {
			// save a couple of customers
			Player player1 = new Player("david@gmail.com");
			playerRepository.save(player1);
			Player player2 = new Player( "Chloe@Obrian.com");
			playerRepository.save(player2);
			Player player3 = new Player("Kim@Bauer.com");
			playerRepository.save(player3);
			Player player4 = new Player("David@Palmer.com");
			playerRepository.save(player4);
			Player player5 = new Player("Michelle@Dessler.com");
			playerRepository.save(player5);

			Game game1 = new Game( new Date());
			gameRepository.save(game1);
			Game game2 = new Game( new Date());
			gameRepository.save(game2);
			Game game3 = new Game( new Date());
			gameRepository.save(game3);

			GamePlayer gamePlayer1 = new GamePlayer(new Date(), player3, game2);
			gamePlayerRepository.save(gamePlayer1);
			GamePlayer gamePlayer2 = new GamePlayer(new Date(), player4, game2);
			gamePlayerRepository.save(gamePlayer2);
			GamePlayer gamePlayer3 = new GamePlayer(new Date(), player1, game3);
			gamePlayerRepository.save(gamePlayer3);
			GamePlayer gamePlayer4 = new GamePlayer(new Date(), player5, game3);
			gamePlayerRepository.save(gamePlayer4);
			GamePlayer gamePlayer5 = new GamePlayer(new Date(), player2, game1);
			gamePlayerRepository.save(gamePlayer5);
			GamePlayer gamePlayer6 = new GamePlayer(new Date(), player4, game1);
			gamePlayerRepository.save(gamePlayer6);

			System.out.println();
		};
	}
}
