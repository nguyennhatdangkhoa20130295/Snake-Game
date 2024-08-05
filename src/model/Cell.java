package model;

public class Cell {
	private int x;
	private int y;
	private CellType type;

	public Cell(int x, int y, CellType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the type
	 */
	public CellType getType() {
		return type;
	}

	public boolean isSnake() {
		return this.type.equals(CellType.SNAKE);
	}

	public boolean isFood() {
		return this.type.equals(CellType.FOOD);
	}

	public boolean isEmpty() {
		return this.type.equals(CellType.EMPTY);
	}

	public boolean isObstacle() {
		return this.type.equals(CellType.OBSTACLE);
	}

	public boolean isBonus() {
		return this.type.equals(CellType.BONUS);
	}

	public boolean isNextLevel() {
		return this.type.equals(CellType.NEXT_LEVEL);
	}

	public boolean isBox() {
		return this.type.equals(CellType.BOX);
	}

	public boolean isBoxGoal() {
		return this.type.equals(CellType.BOX_GOAL);
	}

	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + ", type=" + type + "]";
	}

}
