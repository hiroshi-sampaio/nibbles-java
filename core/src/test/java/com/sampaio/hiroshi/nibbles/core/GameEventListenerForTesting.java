package com.sampaio.hiroshi.nibbles.core;

import com.sampaio.hiroshi.nibbles.core.driven.GameEventListener;
import java.util.EnumMap;
import java.util.EnumSet;

public class GameEventListenerForTesting implements GameEventListener {

  final BlockCharMapper blockCharMapper = new BlockCharMapper();

  @Override
  public void arenaUpdated(Arena arena) {
    for (int y = 0; y < arena.getFieldMeasures().getHeight(); y++) {
      final StringBuilder stringBuilder = new StringBuilder();
      for (int x = 0; x < arena.getFieldMeasures().getWidth(); x++) {
        stringBuilder.append(blockCharMapper.map(arena.getAt(x, y)));
      }
      System.out.println(stringBuilder);
    }
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
