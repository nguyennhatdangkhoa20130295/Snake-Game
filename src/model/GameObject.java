package model;

import java.awt.Point;

public abstract class GameObject {
	protected Point position;

	public GameObject(int x, int y) {
		this.position = new Point(x, y);
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [position=" + position + "]";
	}

}
