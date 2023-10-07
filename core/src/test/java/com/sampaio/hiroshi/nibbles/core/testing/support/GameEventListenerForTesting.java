package com.sampaio.hiroshi.nibbles.core.testing.support;

import com.sampaio.hiroshi.nibbles.core.event.Event;
import com.sampaio.hiroshi.nibbles.core.event.EventListenerDrivenPort;
import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.field.Point;
import com.sampaio.hiroshi.nibbles.core.game.GameContext;
import com.sampaio.hiroshi.nibbles.core.snake.Snake;
import com.sampaio.hiroshi.nibbles.core.snake.SnakeMove;
import java.util.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameEventListenerForTesting implements EventListenerDrivenPort {

  final BlockCharMapper blockCharMapper;
  GameContext gameContext;
  @Getter final Deque<String> frameHistory = new ArrayDeque<>();
  @Getter final Deque<EnumSet<Event>> foreseenEventsHistory = new ArrayDeque<>();
  @Getter final Deque<EnumMap<Block, SnakeMove>> foreseenMovesHistory = new ArrayDeque<>();

  @Override
  public void initialGameContextSet(final GameContext gameContext) {
    frameHistory.clear();
    foreseenEventsHistory.clear();
    foreseenMovesHistory.clear();
    this.gameContext = gameContext;
  }

  @Override
  public void gameContextChanged(final GameContext gameContext) {
    final StringBuilder frameSb = new StringBuilder();
    for (int y = 0; y < gameContext.getField().getMeasures().getHeight(); y++) {
      for (int x = 0; x < gameContext.getField().getMeasures().getWidth(); x++) {
        final Block arenaAt = gameContext.getField().getAt(x, y);
        final Character mapped = blockCharMapper.map(arenaAt);
        final Point point = this.gameContext.getField().getMeasures().pointOf(x, y);
        if (this.gameContext
            .getSnakeByBlock(arenaAt)
            .map(Snake::getHead)
            .filter(point::equals)
            .isPresent()) {
          frameSb.append(blockCharMapper.mapToHead(arenaAt));
        } else {
          frameSb.append(mapped);
        }
      }
      frameSb.append(System.lineSeparator());
    }
    final String frame = frameSb.toString();
    frameHistory.add(frame);
    System.out.print(frame);
  }

  @Override
  public void snakeDied(final Snake snake) {}

  @Override
  public void snakeMovesForeseen(final EnumMap<Block, SnakeMove> snakeMoves) {
    foreseenMovesHistory.add(snakeMoves);
    System.out.println("snakeMovesForeseen = " + snakeMoves);
  }

  @Override
  public void eventsForeseen(final EnumSet<Event> events) {
    foreseenEventsHistory.add(events);
    System.out.println("eventsForeseen = " + events);
  }
}
