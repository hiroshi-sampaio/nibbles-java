package com.sampaio.hiroshi.nibbles.core.driven;

import com.sampaio.hiroshi.nibbles.core.Arena;
import lombok.NonNull;

public interface GameEventListener {
  void arenaUpdated(@NonNull Arena arena);
}
