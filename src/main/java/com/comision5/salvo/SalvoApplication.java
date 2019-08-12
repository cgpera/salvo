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
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository) {
		return (args) -> {
			// save a couple of customers
			Player player1 = new Player("david@gmail.com");
			playerRepository.save(player1);
			playerRepository.save(new Player("Chloe@Obrian.com"));
			playerRepository.save(new Player("Kim@Bauer.com"));
			playerRepository.save(new Player("David@Palmer.com"));
			playerRepository.save(new Player("Michelle@Dessler.com"));

			gameRepository.save(new Game(new Date()));
			System.out.println();
		};
	}
}
