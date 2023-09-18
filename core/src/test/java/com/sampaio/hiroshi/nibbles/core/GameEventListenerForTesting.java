package com.sampaio.hiroshi.nibbles.core;

import com.sampaio.hiroshi.nibbles.core.driven.GameEventListener;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameEventListenerForTesting implements GameEventListener {

  final BlockCharMapper blockCharMapper;
  GameContext gameContext;
  @Getter final List<String> frameHistory = new ArrayList<>();

  @Override
  public void initialGameContextSet(final GameContext gameContext) {
    frameHistory.clear();
    this.gameContext = gameContext;
  }

  @Override
  public void arenaUpdated(final Arena arena) {
    final StringBuilder frameSb = new StringBuilder();
    for (int y = 0; y < arena.getFieldMeasures().getHeight(); y++) {
      for (int x = 0; x < arena.getFieldMeasures().getWidth(); x++) {
        final Block arenaAt = arena.getAt(x, y);
        final Character mapped = blockCharMapper.map(arenaAt);
        if (gameContext
            .getSnakeByBlock(arenaAt)
            .map(Snake::getHead)
            .filter(gameContext.getArena().getFieldMeasures().pointOf(x, y)::equals)
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
  public void eventsForeseen(final EnumSet<Event> events) {
    System.out.println("eventsForeseen = " + events);
  }

  @Override
  public void snakeMovementsForeseen(final EnumMap<Block, SnakeMovement> snakeMovements) {
    System.out.println("snakeMovementsForeseen = " + snakeMovements);
  }
}
