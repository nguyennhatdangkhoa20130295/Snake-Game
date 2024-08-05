package model;

import java.awt.Point;

public class Box extends GameObject {

	public Box(int x, int y) {
		super(x, y);
	}

	// Kiểm tra xem hộp(box) có va chạm với hộp mục tiêu(box goal) hay không
	public boolean collidesWithBoxGoal(BoxGoal boxGoal) {
		Point boxPosition = this.position;
		return boxPosition.equals(boxGoal.getPosition());
	}

	// Kiểm tra xem hộp(box) có va chạm với chướng ngại vật hay không
	public boolean collidesWithObstacles(Obstacle obstacle) {
		Point boxPosition = this.position;
		return boxPosition.equals(obstacle.getPosition());
	}
}
