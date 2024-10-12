package com.sampaio.hiroshi.nibbles.core.testing.support;

import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.field.Field;
import com.sampaio.hiroshi.nibbles.core.field.Point;
import com.sampaio.hiroshi.nibbles.core.game.GameContext;
import com.sampaio.hiroshi.nibbles.core.item.Giver;
import com.sampaio.hiroshi.nibbles.core.item.Item;
import java.util.List;

public class GiverTestImpl implements Giver {

  @Override
  public void placeItems(final GameContext gameContext) {
    if (gameContext.getFrameCount() > 0) {return;}

    final int width = gameContext.getField().getMeasures().getWidth();
    final int height = gameContext.getField().getMeasures().getHeight();

    final Item item = Item.builder()
        .points(List.of(gameContext.getField().getMeasures().pointOf(width / 2, height / 2)))
        .build();

    place(gameContext.getField(), item);
  }

  private void place(final Field field, final Item item) {
    for (final Point point : item.getPoints()) {
      field.setAt(point, Block.ITEM);
    }
  }

}
