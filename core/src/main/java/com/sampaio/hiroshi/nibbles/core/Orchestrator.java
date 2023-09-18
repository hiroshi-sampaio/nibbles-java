package com.sampaio.hiroshi.nibbles.core;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Optional;
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
        case SNAKE_ONE_SLITHERS -> snakeMovements.get(Block.SNAKE_ONE).execute();
        case SNAKE_TWO_SLITHERS -> snakeMovements.get(Block.SNAKE_TWO).execute();
        case SNAKE_ONE_EATS -> gameContext
            .getSnakeByBlock(Block.SNAKE_ONE)
            .orElseThrow()
            .grow(3); // todo
        case SNAKE_TWO_EATS -> gameContext
            .getSnakeByBlock(Block.SNAKE_TWO)
            .orElseThrow()
            .grow(3); // todo
        case SNAKE_ONE_RUNS_INTO_WALL,
            SNAKE_ONE_TRIPS_ON_ITSELF,
            SNAKE_ONE_BUMPS_INTO_SNAKE_TWO -> killSnake(Block.SNAKE_ONE);
        case SNAKE_TWO_RUNS_INTO_WALL,
            SNAKE_TWO_TRIPS_ON_ITSELF,
            SNAKE_TWO_BUMPS_INTO_SNAKE_ONE -> killSnake(Block.SNAKE_TWO);
      }
    }
    updateArena(snakeMovements.values());
  }

  private void updateArena(final Collection<SnakeMovement> snakeMovement) {
    for (final SnakeMovement movement : snakeMovement) {
      if (movement.getSnake().isAlive()) {
        Optional.ofNullable(movement.getCurrentTailTip())
            .ifPresent(gameContext.getArena()::setEmptyBlockAt);
      }
    }
    for (final SnakeMovement movement : snakeMovement) {
      if (movement.getSnake().isAlive()) {
        gameContext.getArena().setBlockAt(movement.getNextHead(), movement.getSnakeBlock());
      }
    }
  }

  private void killSnake(final Block snakeBlock) {
    final Snake snake = gameContext.getSnakeByBlock(snakeBlock).orElseThrow();
    snake.setAlive(false);
    snake.snakePoints().forEach(gameContext.getArena()::setEmptyBlockAt);
  }
}
