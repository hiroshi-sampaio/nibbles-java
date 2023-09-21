package com.sampaio.hiroshi.nibbles.core.testing.support;

import com.sampaio.hiroshi.nibbles.core.*;
import com.sampaio.hiroshi.nibbles.core.driven.GameEventListener;
import java.util.*;

import com.sampaio.hiroshi.nibbles.core.testing.support.BlockCharMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameEventListenerForTesting implements GameEventListener {

  final BlockCharMapper blockCharMapper;
  GameContext gameContext;
  @Getter final Deque<String> frameHistory = new ArrayDeque<>();
  @Getter final Deque<EnumSet<Event>> foreseenEventsHistory = new ArrayDeque<>();
  @Getter final Deque<EnumMap<Block, SnakeMovement>> foreseenMovementsHistory = new ArrayDeque<>();

  @Override
  public void initialGameContextSet(final GameContext gameContext) {
    frameHistory.clear();
    foreseenEventsHistory.clear();
    foreseenMovementsHistory.clear();
    this.gameContext = gameContext;
  }

  @Override
  public void arenaUpdated(final Arena arena) {
    final StringBuilder frameSb = new StringBuilder();
    for (int y = 0; y < arena.getFieldMeasures().getHeight(); y++) {
      for (int x = 0; x < arena.getFieldMeasures().getWidth(); x++) {
        final Block arenaAt = arena.getAt(x, y);
        final Character mapped = blockCharMapper.map(arenaAt);
        final Point point = gameContext.getArena().getFieldMeasures().pointOf(x, y);
        if (gameContext
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
  public void snakeMovementsForeseen(final EnumMap<Block, SnakeMovement> snakeMovements) {
    foreseenMovementsHistory.add(snakeMovements);
    System.out.println("snakeMovementsForeseen = " + snakeMovements);
  }

  @Override
  public void eventsForeseen(final EnumSet<Event> events) {
    foreseenEventsHistory.add(events);
    System.out.println("eventsForeseen = " + events);
  }
}
