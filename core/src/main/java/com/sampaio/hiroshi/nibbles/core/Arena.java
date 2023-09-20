package com.sampaio.hiroshi.nibbles.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Arena {

  @Getter private final FieldMeasures fieldMeasures;
  private final Block[][] field;

  private Arena(final FieldMeasures fieldMeasures) {
    this.fieldMeasures = fieldMeasures;
    this.field = new Block[fieldMeasures.getWidth()][fieldMeasures.getHeight()];
    fillArenaWithEmptyBlocks();
  }

  protected static Arena of(final FieldMeasures fieldMeasures) {
    return new Arena(fieldMeasures);
  }

  public void fillArenaWithEmptyBlocks() {
    for (int x = 0; x < fieldMeasures.getWidth(); x++)
      for (int y = 0; y < fieldMeasures.getHeight(); y++) field[x][y] = Block.EMPTY;
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

  public void setEmptyAt(final int x, final int y) {
    setAt(x, y, Block.EMPTY);
  }

  public void setEmptyAt(final Point point) {
    setAt(point, Block.EMPTY);
  }

  public void setWallAt(final int x, final int y) {
    setAt(x, y, Block.WALL);
  }

  public void setWallAt(final Point point) {
    setAt(point, Block.WALL);
  }

  public void setFoodAt(final int x, final int y) {
    setAt(x, y, Block.FOOD);
  }

  public void setFoodAt(final Point point) {
    setAt(point, Block.FOOD);
  }

  public void setSnakeOneAt(final int x, final int y) {
    setAt(x, y, Block.SNAKE_ONE);
  }

  public void setSnakeOneAt(final Point point) {
    setAt(point, Block.SNAKE_ONE);
  }

  public void setSnakeTwoAt(final int x, final int y) {
    setAt(x, y, Block.SNAKE_TWO);
  }

  public void setSnakeTwoAt(final Point point) {
    setAt(point, Block.SNAKE_TWO);
  }
}
