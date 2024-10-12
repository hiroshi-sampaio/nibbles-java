package com.sampaio.hiroshi.nibbles.core.game;

import com.sampaio.hiroshi.nibbles.core.event.Event;
import com.sampaio.hiroshi.nibbles.core.event.Foreseer;
import com.sampaio.hiroshi.nibbles.core.event.Fulfiller;
import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.snake.Snake;
import com.sampaio.hiroshi.nibbles.core.snake.SnakeMove;
import java.time.Duration;
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
public class Game implements InputListenerDrivingPort {

  public static final long NANOS_PER_MILLI = 1000_000L;

  private final int fps;
  @Getter
  @NonNull
  private final GameContext gameContext;
  @NonNull
  private final Foreseer foreseer;
  @NonNull
  private final Fulfiller fulfiller;

  @SneakyThrows
  public void gameLoop() {
    final long nanosPerFrame = Duration.ofSeconds(1).toNanos() / fps;

    fulfiller.setInitialGameState();

    while (anySnakeAlive()) {
      final long nanoTimeStart = System.nanoTime();

      gameContext.getGiver().placeItems(gameContext);

      final EnumMap<Block, SnakeMove> snakesMoves =
          foreseer.foreseeSnakeMoves(gameContext.getSnakes());

      final EnumSet<Event> events = foreseer.foreseeEvents(snakesMoves, gameContext.getField());

      fulfiller.fulfillEvents(events, snakesMoves);
      gameContext.incrementFrameCount();

      final long elapsedNanos = System.nanoTime() - nanoTimeStart;
      if (nanosPerFrame > elapsedNanos) {
        final long remainingNanos = nanosPerFrame - elapsedNanos;
        final long remainingMillis = remainingNanos / NANOS_PER_MILLI;
        final int nanosPartOfRemainingTime = (int) (remainingNanos % NANOS_PER_MILLI);
        // noinspection BusyWait
        Thread.sleep(remainingMillis, nanosPartOfRemainingTime);
      }
    }
  }

  private boolean anySnakeAlive() {
    for (Snake snake : gameContext.getSnakes()) {
      if (snake.isAlive()) {return true;}
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
