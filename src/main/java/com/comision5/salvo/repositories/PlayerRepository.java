package com.comision5.salvo.repositories;

import com.comision5.salvo.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
    public interface PlayerRepository extends JpaRepository<Player, Long> {

        Optional <Player> findByUserName(@Param("userName") String name);

    }
