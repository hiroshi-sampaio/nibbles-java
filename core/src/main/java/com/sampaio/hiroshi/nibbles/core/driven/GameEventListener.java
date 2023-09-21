package com.sampaio.hiroshi.nibbles.core.driven;

import com.sampaio.hiroshi.nibbles.core.field.Field;
import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.event.Event;
import com.sampaio.hiroshi.nibbles.core.game.GameContext;
import com.sampaio.hiroshi.nibbles.core.snake.SnakeMove;
import java.util.EnumMap;
import java.util.EnumSet;

public interface GameEventListener {
  void initialGameContextSet(GameContext gameContext);

  void fieldUpdated(Field field);

  void snakeMovesForeseen(EnumMap<Block, SnakeMove> snakeMoves);

  void eventsForeseen(EnumSet<Event> events);
}
