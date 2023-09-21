package com.sampaio.hiroshi.nibbles.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumSet;

import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.event.Event;
import com.sampaio.hiroshi.nibbles.core.event.Foreseer;
import com.sampaio.hiroshi.nibbles.core.event.SnakeToEventMapper;
import com.sampaio.hiroshi.nibbles.core.game.Game;
import com.sampaio.hiroshi.nibbles.core.game.GameContext;
import com.sampaio.hiroshi.nibbles.core.event.Fulfiller;
import com.sampaio.hiroshi.nibbles.core.testing.support.BlockCharMapper;
import com.sampaio.hiroshi.nibbles.core.testing.support.GameEventListenerForTesting;
import com.sampaio.hiroshi.nibbles.core.testing.support.LevelHelper;
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
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
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

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL));
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
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
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

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(
            EnumSet.of(Event.SNAKE_TWO_RUNS_INTO_WALL, Event.SNAKE_ONE_SLITHERS),
            EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL));
  }

  @Test
  void snakesRunToTailOfAnotherWhichIsGoingToRunIntoWall() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            #######################
            #          1          #
            #          1          #
            #    2222B 1          #
            #          1          #
            #          1          #
            #          A          #
            #                     #
            #######################
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL, Event.SNAKE_TWO_BUMPS_INTO_SNAKE_ONE));
  }

  @Test
  void crossingBothGoingIntoWall() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            #######################
            #                     #
            #                     #
            #          1          #
            #    2222B 1          #
            #          1          #
            #          A          #
            #                     #
            #######################
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            #######################
            #                     #
            #                     #
            #                     #
            #                2222B#
            #                     #
            #                     #
            #                     #
            #######################
            """);

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(
            EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL, Event.SNAKE_TWO_SLITHERS),
            EnumSet.of(Event.SNAKE_TWO_RUNS_INTO_WALL));
  }

  @Test
  void headButtCrossing() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            #######################
            #          1          #
            #          1          #
            #          1          #
            #          A          #
            #                     #
            #                     #
            #   2222B             #
            #######################
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
                #######################
                #                     #
                #                     #
                #          1          #
                #          1          #
                #          1          #
                #          A          #
                #     2222B           #
                #######################
                """);

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(
            EnumSet.of(Event.SNAKE_TWO_BUMPS_INTO_SNAKE_ONE, Event.SNAKE_ONE_BUMPS_INTO_SNAKE_TWO));
  }

  @Test
  void frontalHeadButtEvenEmptySpacesInBetween() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            #######################
            #                     #
            #  2222B      A11111  #
            #                     #
            #######################
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
          #######################
          #                     #
          #     2222BA11111     #
          #                     #
          #######################
          """);

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(
            EnumSet.of(Event.SNAKE_TWO_BUMPS_INTO_SNAKE_ONE, Event.SNAKE_ONE_BUMPS_INTO_SNAKE_TWO));
  }

  @Test
  void frontalHeadButtOddEmptySpacesInBetween() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            ######################
            #                    #
            #  2222B     A11111  #
            #                    #
            ######################
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            ######################
            #                    #
            #    2222B A11111    #
            #                    #
            ######################
            """);

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(
            EnumSet.of(Event.SNAKE_TWO_BUMPS_INTO_SNAKE_ONE, Event.SNAKE_ONE_BUMPS_INTO_SNAKE_TWO));
  }

  @Test
  void headButtCrossingWhileFoodEating() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
                  #######################
                  #          1          #
                  #          1          #
                  #          1          #
                  #          A          #
                  #                     #
                  #                     #
                  #   2222B  F          #
                  #######################
                  """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            #######################
            #                     #
            #                     #
            #          1          #
            #          1          #
            #          1          #
            #          A          #
            #     2222BF          #
            #######################
            """);

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(
            EnumSet.of(
                Event.SNAKE_TWO_BUMPS_INTO_SNAKE_ONE,
                Event.SNAKE_ONE_BUMPS_INTO_SNAKE_TWO,
                Event.SNAKE_ONE_EATS,
                Event.SNAKE_TWO_EATS));
  }

  @Test
  void quickerSnakeTripsOnSlowerOne() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
                  ##############################
                  #2222B         11A           #
                  ##############################
                  """);

    gameContext.getSnakeByBlock(Block.SNAKE_ONE).orElseThrow().setSpeed(2);
    gameContext.getSnakeByBlock(Block.SNAKE_TWO).orElseThrow().setSpeed(1);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(
            EnumSet.of(Event.SNAKE_TWO_BUMPS_INTO_SNAKE_ONE),
            EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL));
  }

  @Test
  void swapHorizontallyGoingToRight() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
                  #############
                       # 111A
                  #############
                  """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL));

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            #############
             111A#      \s
            #############
            """);
  }

  @Test
  void swapHorizontallyGoingToLeft() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
                  #############
                   A111#
                  #############
                  """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL));

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            #############
                 #A111  \s
            #############
            """);
  }

  @Test
  void swapVerticallyGoingToUp() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
                  #     #
                  #  A  #
                  #  1  #
                  #  1  #
                  #######
                  #     #
                  #     #
                  #     #
                  #     #
                  """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL));

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            #     #
            #     #
            #     #
            #     #
            #######
            #  A  #
            #  1  #
            #  1  #
            #     #
            """);
  }

  @Test
  void swapVerticallyGoingDown() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
                  #     #
                  #     #
                  #     #
                  #     #
                  #######
                  #  1  #
                  #  1  #
                  #  A  #
                  #     #
                  """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL));

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            #     #
            #  1  #
            #  1  #
            #  A  #
            #######
            #     #
            #     #
            #     #
            #     #
            """);
  }

  @Test
  void snakeHeadsToWhereItsTailTipWasCrossing() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            #########
            # 1     #
            # 1 A11 #
            # 1   1 #
            # 11111 #
            #       #
            #########
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            #########
            #       #
            # A1111 #
            # 1   1 #
            # 11111 #
            #       #
            #########
            """);

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL));
  }

  @Test
  void snakeHeadsToWhereItsTailTipWasInline() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            #########
            #       #
            # 111A1 #
            # 1   1 #
            # 11111 #
            #       #
            #########
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(EnumSet.of(Event.SNAKE_ONE_RUNS_INTO_WALL));
  }

  @Test
  void headbutt_twoSnakeGoingToWhereATailTipWas() {

    final GameContext gameContext =
        levelHelper.createGameContext(
            """
            ##############
            #     1      #
            # 11A 1 B222 #
            # 1   1      #
            # 11111      #
            #            #
            ##############
            """);

    final Game game =
        Game.builder()
            .fps(Integer.MAX_VALUE)
            .gameContext(gameContext)
            .eventListener(gameEventListenerForTesting)
            .foreseer(new Foreseer(new SnakeToEventMapper()))
            .fulfiller(new Fulfiller(gameContext))
            .build();

    game.gameLoop();

    assertThat(gameEventListenerForTesting.getFrameHistory())
        .contains(
            """
            ##############
            #            #
            # 111A1B222  #
            # 1   1      #
            # 11111      #
            #            #
            ##############
            """);

    assertThat(gameEventListenerForTesting.getForeseenEventsHistory())
        .contains(
            EnumSet.of(Event.SNAKE_TWO_BUMPS_INTO_SNAKE_ONE, Event.SNAKE_ONE_BUMPS_INTO_SNAKE_TWO));
  }
}
