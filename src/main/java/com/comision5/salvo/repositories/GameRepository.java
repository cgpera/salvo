package com.comision5.salvo.repositories;

import com.comision5.salvo.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GameRepository extends JpaRepository<Game, Long> {
//    List<Game> findByCreationDate(Date creationDate);
}
