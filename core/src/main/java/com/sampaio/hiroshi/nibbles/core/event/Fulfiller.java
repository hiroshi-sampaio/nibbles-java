package com.sampaio.hiroshi.nibbles.core.event;

import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.field.Point;
import com.sampaio.hiroshi.nibbles.core.game.GameContext;
import com.sampaio.hiroshi.nibbles.core.snake.Snake;
import com.sampaio.hiroshi.nibbles.core.snake.SnakeMove;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Fulfiller {

  private final GameContext gameContext;
  private final EventListenerDrivenPort eventListener;

  public void setInitialGameState() {
    for (final Snake snake : gameContext.getSnakes()) {
      for (final Point point : snake.snakePoints()) {
        gameContext.getField().setAt(point, snake.getSnakeBlock());
      }
    }
  }

  public void fulfillEvents(
      final EnumSet<Event> events, final EnumMap<Block, SnakeMove> snakesMoves) {
    for (final Event event : events) {
      switch (event) {
        case SNAKE_ONE_SLITHERS -> snakesMoves.get(Block.SNAKE_ONE).execute();
        case SNAKE_TWO_SLITHERS -> snakesMoves.get(Block.SNAKE_TWO).execute();
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
    fulfillMoves(snakesMoves.values());
  }

  private void fulfillMoves(final Collection<SnakeMove> snakeMove) {
    for (final SnakeMove move : snakeMove) {
      if (move.getSnake().isAlive()) {
        Optional.ofNullable(move.getTailTipToRemove())
            .ifPresent(gameContext.getField()::setEmptyAt);
      }
    }
    for (final SnakeMove move : snakeMove) {
      if (move.getSnake().isAlive()) {
        gameContext.getField().setAt(move.getNextHead(), move.getSnakeBlock());
      }
    }
  }

  private void killSnake(final Block snakeBlock) {
    final Snake snake = gameContext.getSnakeByBlock(snakeBlock).orElseThrow();
    snake.setAlive(false);
    snake.snakePoints().forEach(gameContext.getField()::setEmptyAt);
  }
}
