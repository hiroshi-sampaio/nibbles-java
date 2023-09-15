package com.sampaio.hiroshi.nibbles.core;

import com.sampaio.hiroshi.nibbles.core.driven.GameEventListener;
import com.sampaio.hiroshi.nibbles.core.driving.GameInputListener;
import java.util.EnumMap;
import java.util.EnumSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class Game implements GameInputListener {

  public static final long NANOS_PER_MILLI = 1000_000L;
  public static final long NANOS_PER_SECOND = 1000_000_000L;

  private final int fps;
  @NonNull private final GameContext gameContext;
  @NonNull private final EventsForeseer eventsForeseer;
  @NonNull private final GameEventListener eventListener;
  @NonNull private final Orchestrator orchestrator;

  @SneakyThrows
  public void gameLoop() {
    final long nanosPerFrame = NANOS_PER_SECOND / fps;

    orchestrator.setInitialGameState();
    eventListener.arenaUpdated(gameContext.getArena());

    while (anySnakeAlive()) {
      final long nanoTimeStart = System.nanoTime();

      final EnumMap<Block, SnakeMovement> snakeMovements =
          eventsForeseer.foreseeSnakeMovements(gameContext.getSnakes());
      final EnumSet<Event> events =
          eventsForeseer.foreseeEventsOnArena(snakeMovements, gameContext.getArena());
      orchestrator.handleEvents(events, snakeMovements);

      System.out.println("events = " + events);

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
    if (!gameContext.getSnakes().isEmpty()) {
      final Snake snake = gameContext.getSnakes().get(0);
      snake.turnUp();
    }
  }

  @Override
  public void downSnakeOne() {
    if (!gameContext.getSnakes().isEmpty()) {
      final Snake snake = gameContext.getSnakes().get(0);
      snake.turnDown();
    }
  }

  @Override
  public void leftSnakeOne() {
    if (!gameContext.getSnakes().isEmpty()) {
      final Snake snake = gameContext.getSnakes().get(0);
      snake.turnLeft();
    }
  }

  @Override
  public void rightSnakeOne() {
    if (!gameContext.getSnakes().isEmpty()) {
      final Snake snake = gameContext.getSnakes().get(0);
      snake.turnRight();
    }
  }

  @Override
  public void upSnakeTwo() {
    if (gameContext.getSnakes().size() >= 2) {
      final Snake snake = gameContext.getSnakes().get(1);
      snake.turnUp();
    }
  }

  @Override
  public void downSnakeTwo() {
    if (gameContext.getSnakes().size() >= 2) {
      final Snake snake = gameContext.getSnakes().get(1);
      snake.turnDown();
    }
  }

  @Override
  public void leftSnakeTwo() {
    if (gameContext.getSnakes().size() >= 2) {
      final Snake snake = gameContext.getSnakes().get(1);
      snake.turnLeft();
    }
  }

  @Override
  public void rightSnakeTwo() {
    if (gameContext.getSnakes().size() >= 2) {
      final Snake snake = gameContext.getSnakes().get(1);
      snake.turnRight();
    }
  }
}
