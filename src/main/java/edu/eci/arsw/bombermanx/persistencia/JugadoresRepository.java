/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.bombermanx.persistencia;

import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author kvn CF
 */
public interface JugadoresRepository extends MongoRepository<Jugador, Integer>{

    public Jugador findById(Integer id);
}
