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

  public void setBlockAt(final int x, final int y, final Block block) {
    field[x][y] = block;
  }

  public void setBlockAt(final Point point, final Block block) {
    setBlockAt(point.getX(), point.getY(), block);
  }

  public void setEmptyBlockAt(final int x, final int y) {
    setBlockAt(x, y, Block.EMPTY);
  }

  public void setEmptyBlockAt(final Point point) {
    setBlockAt(point, Block.EMPTY);
  }

  public void setWallBlockAt(final int x, final int y) {
    setBlockAt(x, y, Block.WALL);
  }

  public void setWallBlockAt(final Point point) {
    setBlockAt(point, Block.WALL);
  }

  public void setFoodBlockAt(final int x, final int y) {
    setBlockAt(x, y, Block.FOOD);
  }

  public void setFoodBlockAt(final Point point) {
    setBlockAt(point, Block.FOOD);
  }

  public void setSnakeOneAt(final int x, final int y) {
    setBlockAt(x, y, Block.SNAKE_ONE);
  }

  public void setSnakeOneAt(final Point point) {
    setBlockAt(point, Block.SNAKE_ONE);
  }

  public void setSnakeTwoAt(final int x, final int y) {
    setBlockAt(x, y, Block.SNAKE_TWO);
  }

  public void setSnakeTwoAt(final Point point) {
    setBlockAt(point, Block.SNAKE_TWO);
  }
}
