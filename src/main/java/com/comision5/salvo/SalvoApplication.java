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
			Player player1 = new Player("j.bauer@ctu.gov");
			playerRepository.save(player1);
			Player player2 = new Player( "c.obrian@ctu.gov");
			playerRepository.save(player2);
			Player player3 = new Player("kim_bauer@gmail.com");
			playerRepository.save(player3);
			Player player4 = new Player("t.almeida@ctu.gov");
			playerRepository.save(player4);

			Game game1 = new Game( new Date());
			gameRepository.save(game1);
			Game game2 = new Game( new Date());
			gameRepository.save(game2);
			Game game3 = new Game( new Date());
			gameRepository.save(game3);
			Game game4 = new Game( new Date());
			gameRepository.save(game4);
			Game game5 = new Game( new Date());
			gameRepository.save(game5);
			Game game6 = new Game( new Date());
			gameRepository.save(game6);
			Game game7 = new Game( new Date());
			gameRepository.save(game7);
			Game game8 = new Game( new Date());
			gameRepository.save(game8);

/*			GamePlayer gamePlayer1 = new GamePlayer(new Date(), player3, game2);
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
*/
			GamePlayer gamePlayer11 = new GamePlayer(new Date(), player1, game1);
			gamePlayerRepository.save(gamePlayer11);
			GamePlayer gamePlayer12 = new GamePlayer(new Date(), player2, game1);
			gamePlayerRepository.save(gamePlayer12);
			GamePlayer gamePlayer21 = new GamePlayer(new Date(), player1, game2);
			gamePlayerRepository.save(gamePlayer21);
			GamePlayer gamePlayer22 = new GamePlayer(new Date(), player2, game2);
			gamePlayerRepository.save(gamePlayer22);
			GamePlayer gamePlayer32 = new GamePlayer(new Date(), player2, game3);
			gamePlayerRepository.save(gamePlayer32);
			GamePlayer gamePlayer34 = new GamePlayer(new Date(), player4, game3);
			gamePlayerRepository.save(gamePlayer34);
			GamePlayer gamePlayer41 = new GamePlayer(new Date(), player1, game4);
			gamePlayerRepository.save(gamePlayer41);
			GamePlayer gamePlayer42 = new GamePlayer(new Date(), player2, game4);
			gamePlayerRepository.save(gamePlayer42);
			GamePlayer gamePlayer54 = new GamePlayer(new Date(), player4, game5);
			gamePlayerRepository.save(gamePlayer54);
			GamePlayer gamePlayer51 = new GamePlayer(new Date(), player1, game5);
			gamePlayerRepository.save(gamePlayer51);
			GamePlayer gamePlayer63 = new GamePlayer(new Date(), player3, game6);
			gamePlayerRepository.save(gamePlayer63);
			GamePlayer gamePlayer74 = new GamePlayer(new Date(), player4, game7);
			gamePlayerRepository.save(gamePlayer74);
			GamePlayer gamePlayer83 = new GamePlayer(new Date(), player3, game8);
			gamePlayerRepository.save(gamePlayer83);
			GamePlayer gamePlayer84 = new GamePlayer(new Date(), player4, game8);
			gamePlayerRepository.save(gamePlayer84);

			System.out.println();
		};
	}
}
