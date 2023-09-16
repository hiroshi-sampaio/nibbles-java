package com.sampaio.hiroshi.nibbles.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GameTest {

  private final LevelHelper levelHelper = new LevelHelper();

  @Test
  void snake3pointsLongGoingToTheRightAndBumpingIntoWall() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
                        #########
                        #       #
                        #11A    #
                        #       #
                        #########
                        """,
            Direction.RIGHT,
            Direction.LEFT);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(new GameEventListenerForTesting())
            .eventsForeseer(new EventsForeseer(new SnakeToEventMapper()))
            .orchestrator(new Orchestrator(gameContext))
            .build();

    game.gameLoop();

    final Arena expectedFinalArenaState =
        levelHelper.createArena(
            """
                        #########
                        #       #
                        #    11A#
                        #       #
                        #########
                        """);

    assertEquals(expectedFinalArenaState, game.getGameContext().getArena());
  }
}
