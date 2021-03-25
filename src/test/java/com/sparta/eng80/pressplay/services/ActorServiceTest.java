package com.sparta.eng80.pressplay.services;

import com.sparta.eng80.pressplay.entities.ActorEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ActorServiceTest {

    @Autowired
    private ActorService actorService;

    @Test
    @Order(Integer.MIN_VALUE)
    void contextLoads() {
        Assertions.assertNotNull(actorService);
    }

    @Test
    @DisplayName("Check that find all returns data")
    void findAllActorsNotNull() {
        Assertions.assertNotNull(actorService.findAll());
    }

    @ParameterizedTest
    @DisplayName("Check that find all includes correct actors")
    @CsvSource({"1, PENELOPE", "10, CHRISTIAN", "100, SPENCER"})
    void findAllIncludesActors(int actorId, String actorName) {
        Iterable<ActorEntity> allActors = actorService.findAll();
        for (ActorEntity actor:allActors) {
            if(actor.getActorId() == actorId) {
                Assertions.assertEquals(actorName, actor.getFirstName());
            }
        }
    }

    @ParameterizedTest
    @DisplayName("Check that findAll does not return fake actors")
    @CsvSource({"1000", "201", "333"})
    void findAllActorsNotFound(int actorId) {
        Iterable<ActorEntity> allActors = actorService.findAll();
        for (ActorEntity actor:allActors) {
            Assertions.assertNotEquals(actorId, actor.getActorId());
        }
    }
}
