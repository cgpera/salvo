package com.comision5.salvo;

import com.comision5.salvo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class SalvoApplication {
	public static void main(String[] args) {
		SpringApplication.run(com.comision5.salvo.SalvoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository,	GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
		return (args) -> {
			// save a couple of customers
			Player player1 = new Player("j.bauer@ctu.gov", passwordEncoder().encode("24"));
			playerRepository.save(player1);
			Player player2 = new Player( "c.obrian@ctu.gov", passwordEncoder().encode("42"));
			playerRepository.save(player2);
			Player player3 = new Player("kim_bauer@gmail.com", passwordEncoder().encode("kb"));
			playerRepository.save(player3);
			Player player4 = new Player("t.almeida@ctu.gov", passwordEncoder().encode("mole"));
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


			GamePlayer gamePlayer1 = new GamePlayer(date, player1, game1);
			gamePlayerRepository.save(gamePlayer1);
			GamePlayer gamePlayer2 = new GamePlayer(new Date(), player2, game1);
			gamePlayerRepository.save(gamePlayer2);
			GamePlayer gamePlayer3 = new GamePlayer(new Date(), player1, game2);
			gamePlayerRepository.save(gamePlayer3);
			GamePlayer gamePlayer4 = new GamePlayer(new Date(), player2, game2);
			gamePlayerRepository.save(gamePlayer4);
			GamePlayer gamePlayer5 = new GamePlayer(new Date(), player2, game3);
			gamePlayerRepository.save(gamePlayer5);
			GamePlayer gamePlayer6 = new GamePlayer(new Date(), player4, game3);
			gamePlayerRepository.save(gamePlayer6);
			GamePlayer gamePlayer7 = new GamePlayer(date, player2, game4);
			gamePlayerRepository.save(gamePlayer7);
			GamePlayer gamePlayer8 = new GamePlayer(new Date(), player1, game4);
			gamePlayerRepository.save(gamePlayer8);
			GamePlayer gamePlayer9 = new GamePlayer(new Date(), player4, game5);
			gamePlayerRepository.save(gamePlayer9);
			GamePlayer gamePlayer10 = new GamePlayer(new Date(), player1, game5);
			gamePlayerRepository.save(gamePlayer10);
			GamePlayer gamePlayer11 = new GamePlayer(new Date(), player3, game6);
			gamePlayerRepository.save(gamePlayer11);
			GamePlayer gamePlayer12 = new GamePlayer(new Date(), player4, game7);
			gamePlayerRepository.save(gamePlayer12);
			GamePlayer gamePlayer13 = new GamePlayer(new Date(), player3, game8);
			gamePlayerRepository.save(gamePlayer13);
			GamePlayer gamePlayer14 = new GamePlayer(new Date(), player4, game8);
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

			Salvo s1 = new Salvo(1, Arrays.asList("B5", "C5", "F1"), gamePlayer1);
			Salvo s2 = new Salvo(1, Arrays.asList("E1", "H3", "A2"), gamePlayer2);
			Salvo s3 = new Salvo(2, Arrays.asList("F2", "D5"), gamePlayer1);
			Salvo s4 = new Salvo(2, Arrays.asList("E1", "H3", "A2"), gamePlayer2);
			Salvo s5 = new Salvo(1, Arrays.asList("A2", "A4", "G6"), gamePlayer3);
			Salvo s6 = new Salvo(1, Arrays.asList("A2", "A4", "G6"), gamePlayer4);
			Salvo s7 = new Salvo(2, Arrays.asList("A3", "H6"), gamePlayer3);
			Salvo s8 = new Salvo(2, Arrays.asList("C5", "C6"), gamePlayer4);
			Salvo s9 = new Salvo(1, Arrays.asList("G6", "H6", "A4"), gamePlayer5);
			Salvo s10 = new Salvo(1, Arrays.asList("H1", "H2", "H3"), gamePlayer6);
			Salvo s11 = new Salvo(2, Arrays.asList("A2", "A3", "D8"), gamePlayer5);
			Salvo s12 = new Salvo(2, Arrays.asList("E1", "F2", "G3"), gamePlayer6);
			Salvo s13 = new Salvo(1, Arrays.asList("A3", "A4", "F7"), gamePlayer7);
			Salvo s14 = new Salvo(1, Arrays.asList("B5", "H6", "C1"), gamePlayer8);
			Salvo s15 = new Salvo(2, Arrays.asList("A2", "G6", "H6"), gamePlayer7);
			Salvo s16 = new Salvo(2, Arrays.asList("C5", "D7", "D5"), gamePlayer8);
			Salvo s17 = new Salvo(1, Arrays.asList("A1", "A2", "A3"), gamePlayer9);
			Salvo s18 = new Salvo(1, Arrays.asList("B5", "B6", "C7"), gamePlayer10);
			Salvo s19 = new Salvo(2, Arrays.asList("G6", "G7", "G8"), gamePlayer9);
			Salvo s20 = new Salvo(2, Arrays.asList("C6", "D6", "E6"), gamePlayer10);
			Salvo s21 = new Salvo(3, Arrays.asList("", "", ""), gamePlayer9);
			Salvo s22 = new Salvo(3, Arrays.asList("C5", "D7"), gamePlayer10);

			salvoRepository.save(s1);
			salvoRepository.save(s2);
			salvoRepository.save(s3);
			salvoRepository.save(s4);
			salvoRepository.save(s5);
			salvoRepository.save(s6);
			salvoRepository.save(s7);
			salvoRepository.save(s8);
			salvoRepository.save(s9);
			salvoRepository.save(s10);
			salvoRepository.save(s11);
			salvoRepository.save(s12);
			salvoRepository.save(s13);
			salvoRepository.save(s14);
			salvoRepository.save(s15);
			salvoRepository.save(s16);
			salvoRepository.save(s17);
			salvoRepository.save(s18);
			salvoRepository.save(s19);
			salvoRepository.save(s20);
			salvoRepository.save(s21);
			salvoRepository.save(s22);


			Score sc1 = new Score(1.0D, new Date(), game1, player1);
			Score sc2 = new Score(0.0D, new Date(), game1, player2);
			Score sc3 = new Score(0.5D, new Date(), game2, player1);
			Score sc4 = new Score(0.5D, new Date(), game2, player2);
			Score sc5 = new Score(1.0D, new Date(), game3, player2);
			Score sc6 = new Score(0.0D, new Date(), game3, player4);
			Score sc7 = new Score(0.5D, new Date(), game4, player2);
			Score sc8 = new Score(0.5D, new Date(), game4, player1);

			scoreRepository.save(sc1);
			scoreRepository.save(sc2);
			scoreRepository.save(sc3);
			scoreRepository.save(sc4);
			scoreRepository.save(sc5);
			scoreRepository.save(sc6);
			scoreRepository.save(sc7);
			scoreRepository.save(sc8);

			System.out.println();
		};
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByUserName(inputName);
			if (player != null) {
				return new User(player.getUserName(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/api/games").permitAll()
				.antMatchers("/api/*").permitAll()
				.antMatchers("/web/**").permitAll()
				.antMatchers("/rest/**").permitAll()
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers("/**").hasAuthority("USER")
				.anyRequest().permitAll();
				http.formLogin()
				.usernameParameter("name")
				.passwordParameter("pwd")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");
		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}
