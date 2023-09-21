package com.sampaio.hiroshi.nibbles.core.field;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Field {

  @Getter private final Measures measures;
  private final Block[][] field;

  private Field(final Measures measures) {
    this.measures = measures;
    this.field = new Block[measures.getWidth()][measures.getHeight()];
    fillWithEmptyBlocks();
  }

  protected static Field of(final Measures measures) {
    return new Field(measures);
  }

  public void fillWithEmptyBlocks() {
    for (int x = 0; x < measures.getWidth(); x++)
      for (int y = 0; y < measures.getHeight(); y++) field[x][y] = Block.EMPTY;
  }

  public Block getAt(final int x, final int y) {
    return field[x][y];
  }

  public Block getAt(final Point point) {
    return getAt(point.getX(), point.getY());
  }

  public void setAt(final int x, final int y, final Block block) {
    field[x][y] = block;
  }

  public void setAt(final Point point, final Block block) {
    setAt(point.getX(), point.getY(), block);
  }

  public void setEmptyAt(final Point point) {
    setAt(point, Block.EMPTY);
  }

  public void setWallAt(final Point point) {
    setAt(point, Block.WALL);
  }

  public void setFoodAt(final Point point) {
    setAt(point, Block.FOOD);
  }

  public void setSnakeOneAt(final Point point) {
    setAt(point, Block.SNAKE_ONE);
  }

  public void setSnakeTwoAt(final Point point) {
    setAt(point, Block.SNAKE_TWO);
  }
}
