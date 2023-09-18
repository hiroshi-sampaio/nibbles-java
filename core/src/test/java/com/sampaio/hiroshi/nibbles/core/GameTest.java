package com.sampaio.hiroshi.nibbles.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class GameTest {

  private final BlockCharMapper blockCharMapper = new BlockCharMapper();
  private final LevelHelper levelHelper = new LevelHelper(blockCharMapper);
  private final GameEventListenerForTesting gameEventListenerForTesting =
      new GameEventListenerForTesting(blockCharMapper);

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
                """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .eventsForeseer(new EventsForeseer(new SnakeToEventMapper()))
            .orchestrator(new Orchestrator(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            #########
            #       #
            #    11A#
            #       #
            #########
            """);
  }

  @Test
  void snakesWithSameSpeedInLine() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            ######################
            #                    #
            #1111A2222B          #
            #                    #
            ######################
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .eventsForeseer(new EventsForeseer(new SnakeToEventMapper()))
            .orchestrator(new Orchestrator(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            ######################
            #                    #
            #          1111A2222B#
            #                    #
            ######################
            """,
            """
            ######################
            #                    #
            #               1111A#
            #                    #
            ######################
            """);
  }
  @Test
  void snakesRunToTailOfAnotherWhichIsDying() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            #######################
            #                     #
            #         1           #
            #         1           #
            #    2222B1           #
            #         1           #
            #         1           #
            #         A           #
            #######################
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .eventsForeseer(new EventsForeseer(new SnakeToEventMapper()))
            .orchestrator(new Orchestrator(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            ######################
            #                    #
            #          1111A2222B#
            #                    #
            ######################
            """,
            """
            ######################
            #                    #
            #               1111A#
            #                    #
            ######################
            """);
  }
}
