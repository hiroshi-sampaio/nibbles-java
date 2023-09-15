package com.sampaio.hiroshi.nibbles.core;

import lombok.Getter;

public class Arena {

  @Getter private final FieldMeasures fieldMeasures;
  private final Block[][] field;

  protected static Arena of(final FieldMeasures fieldMeasures) {
    return new Arena(fieldMeasures);
  }

  private Arena(final FieldMeasures fieldMeasures) {
    this.fieldMeasures = fieldMeasures;
    this.field = new Block[fieldMeasures.getWidth()][fieldMeasures.getHeight()];
    fillArenaWithEmptyBlocks();
  }

  public void fillArenaWithEmptyBlocks() {
    for (int x = 0; x < fieldMeasures.getWidth(); x++)
      for (int y = 0; y < fieldMeasures.getHeight(); y++) field[x][y] = Block.EMPTY;
  }

  public void setEmptyBlockAt(final int x, final int y) {
    field[x][y] = Block.EMPTY;
  }

  public void setWallBlockAt(final int x, final int y) {
    field[x][y] = Block.WALL;
  }

  public void setFoodBlockAt(final int x, final int y) {
    field[x][y] = Block.FOOD;
  }

  public void setSnakeOneAt(final int x, final int y) {
    field[x][y] = Block.SNAKE_ONE;
  }

  public void setSnakeTwoAt(final int x, final int y) {
    field[x][y] = Block.SNAKE_TWO;
  }

  public void setBlockAt(final int x, final int y, final Block block) {
    field[x][y] = block;
  }

  public Block getAt(final int x, final int y) {
    return field[x][y];
  }
}
