package com.sampaio.hiroshi.nibbles.core;

import java.util.EnumMap;
import java.util.EnumSet;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Orchestrator {

  private final GameContext gameContext;

  public void setInitialGameState() {
    for (final Snake snake : gameContext.getSnakes()) {
      for (final Point point : snake.snakePoints()) {
        gameContext.getArena().setBlockAt(point.getX(), point.getY(), snake.getSnakeBlock());
      }
    }
  }

  public void handleEvents(
      final EnumSet<Event> events, final EnumMap<Block, SnakeMovement> snakeMovements) {
    for (final Event event : events) {
      switch (event) {
        case SNAKE_ONE_SLITHERS -> {
          final SnakeMovement snakeMovement = snakeMovements.get(Block.SNAKE_ONE); // todo
          final Snake snake = gameContext.getSnakeByBlock(Block.SNAKE_ONE);
          snake.doUpdate(snakeMovement);
          updateArena(snakeMovement);
        }
        case SNAKE_TWO_SLITHERS -> {
          final SnakeMovement snakeMovement = snakeMovements.get(Block.SNAKE_TWO);
          final Snake snake = gameContext.getSnakeByBlock(Block.SNAKE_TWO);
          snake.doUpdate(snakeMovement);
          updateArena(snakeMovement);
        }
        case SNAKE_ONE_EATS -> gameContext.getSnakeByBlock(Block.SNAKE_ONE).grow(3); // todo
        case SNAKE_TWO_EATS -> gameContext.getSnakeByBlock(Block.SNAKE_TWO).grow(3); // todo
        case SNAKE_ONE_RUNS_INTO_WALL,
            SNAKE_ONE_TRIPS_ON_ITSELF,
            SNAKE_ONE_BUMPS_INTO_SNAKE_TWO -> killSnake(
            gameContext.getSnakeByBlock(Block.SNAKE_ONE));
        case SNAKE_TWO_RUNS_INTO_WALL,
            SNAKE_TWO_TRIPS_ON_ITSELF,
            SNAKE_TWO_BUMPS_INTO_SNAKE_ONE -> killSnake(
            gameContext.getSnakeByBlock(Block.SNAKE_TWO));
        case HEADS_BUTT -> {}
      }
    }
  }

  private void updateArena(final SnakeMovement snakeMovement) {
    final Point pointToSetAsSnake = snakeMovement.getPointToSetAsSnake();
    gameContext.getArena().setSnakeOneAt(pointToSetAsSnake.getX(), pointToSetAsSnake.getY());
    final Point pointToSetAsEmpty = snakeMovement.getPointToSetAsEmpty();
    if (pointToSetAsEmpty != null) {
      gameContext.getArena().setEmptyBlockAt(pointToSetAsEmpty.getX(), pointToSetAsEmpty.getY());
    }
  }

  private void killSnake(final Snake snake) {
    snake.setAlive(false);
  }
}
