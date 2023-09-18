package com.sampaio.hiroshi.nibbles.core;

import com.sampaio.hiroshi.nibbles.core.driven.GameEventListener;
import com.sampaio.hiroshi.nibbles.core.driving.GameInputListener;
import java.util.EnumMap;
import java.util.EnumSet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Game implements GameInputListener {

  public static final long NANOS_PER_MILLI = 1000_000L;
  public static final long NANOS_PER_SECOND = 1000_000_000L;

  private final int fps;
  @Getter @NonNull private final GameContext gameContext;
  @NonNull private final EventsForeseer eventsForeseer;
  @NonNull private final GameEventListener eventListener;
  @NonNull private final Orchestrator orchestrator;

  @SneakyThrows
  public void gameLoop() {
    final long nanosPerFrame = NANOS_PER_SECOND / fps;

    orchestrator.setInitialGameState();
    eventListener.initialGameContextSet(gameContext);
    eventListener.arenaUpdated(gameContext.getArena());

    while (anySnakeAlive()) {
      final long nanoTimeStart = System.nanoTime();

      final EnumMap<Block, SnakeMovement> snakeMovements =
          eventsForeseer.foreseeSnakeMovements(gameContext.getSnakes());

      eventListener.snakeMovementsForeseen(snakeMovements);

      final EnumSet<Event> events =
          eventsForeseer.foreseeEventsOnArena(snakeMovements, gameContext.getArena());

      eventListener.eventsForeseen(events);

      orchestrator.handleEvents(events, snakeMovements);

      if (!events.isEmpty()) eventListener.arenaUpdated(gameContext.getArena());

      final long elapsedNanos = System.nanoTime() - nanoTimeStart;
      if (nanosPerFrame > elapsedNanos) {
        final long remainingNanos = nanosPerFrame - elapsedNanos;
        final long millis = remainingNanos / NANOS_PER_MILLI;
        final int nanosPart = (int) (remainingNanos % NANOS_PER_MILLI);
        Thread.sleep(millis, nanosPart);
      }
    }
  }

  private boolean anySnakeAlive() {
    for (Snake snake : gameContext.getSnakes()) {
      if (snake.isAlive()) return true;
    }
    return false;
  }

  @Override
  public void upSnakeOne() {
    gameContext.getSnakeByBlock(Block.SNAKE_ONE).ifPresent(Snake::turnUp);
  }

  @Override
  public void downSnakeOne() {
    gameContext.getSnakeByBlock(Block.SNAKE_ONE).ifPresent(Snake::turnDown);
  }

  @Override
  public void leftSnakeOne() {
    gameContext.getSnakeByBlock(Block.SNAKE_ONE).ifPresent(Snake::turnLeft);
  }

  @Override
  public void rightSnakeOne() {
    gameContext.getSnakeByBlock(Block.SNAKE_ONE).ifPresent(Snake::turnRight);
  }

  @Override
  public void upSnakeTwo() {
    gameContext.getSnakeByBlock(Block.SNAKE_TWO).ifPresent(Snake::turnUp);
  }

  @Override
  public void downSnakeTwo() {
    gameContext.getSnakeByBlock(Block.SNAKE_TWO).ifPresent(Snake::turnDown);
  }

  @Override
  public void leftSnakeTwo() {
    gameContext.getSnakeByBlock(Block.SNAKE_TWO).ifPresent(Snake::turnLeft);
  }

  @Override
  public void rightSnakeTwo() {
    gameContext.getSnakeByBlock(Block.SNAKE_TWO).ifPresent(Snake::turnRight);
  }
}
