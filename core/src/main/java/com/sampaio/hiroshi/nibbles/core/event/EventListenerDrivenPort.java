package com.sampaio.hiroshi.nibbles.core.event;

import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.field.Field;
import com.sampaio.hiroshi.nibbles.core.game.GameContext;
import com.sampaio.hiroshi.nibbles.core.snake.SnakeMove;
import java.util.EnumMap;
import java.util.EnumSet;

public interface EventListenerDrivenPort {
  void initialGameContextSet(GameContext gameContext);

  void eventsFulfilled(Field field);

  void snakeMovesForeseen(EnumMap<Block, SnakeMove> snakeMoves);

  void eventsForeseen(EnumSet<Event> events);
}
