package view;

import static constant.Constants.CELL_SIZE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Cell;
import model.Direction;
import model.ISnakeModel;
import observer.Observer;;

public class SnakeView extends JPanel implements KeyListener, Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ISnakeModel model;

	public SnakeView(ISnakeModel model) {
		this.model = model;
		this.model.registerObserver(this);
		setPreferredSize(new Dimension(model.getCols() * CELL_SIZE, model.getRows() * CELL_SIZE));
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		List<Cell> cells = model.getCells();
		int currentLevel = model.getCurrentLevel();

		for (Cell cell : cells) {
			int x = cell.getX();
			int y = cell.getY();
			if (cell.isSnake()) {
				ImageIcon bodyIcon = new ImageIcon("./src/images/snake_body.png");
				Image bodyImage = bodyIcon.getImage();
				g.drawImage(bodyImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
			} else if (cell.isFood()) {
				ImageIcon foodIcon = new ImageIcon("./src/images/red_apple.png");
				Image foodImage = foodIcon.getImage();
				g.drawImage(foodImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
			} else if (cell.isObstacle()) {
				if (currentLevel == 2) {
					ImageIcon obstacleIcon = new ImageIcon("./src/images/brick.png");
					Image obstacleImage = obstacleIcon.getImage();
					g.drawImage(obstacleImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
				} else if (currentLevel == 3) {
					ImageIcon obstacleIcon = new ImageIcon("./src/images/brick41.png");
					if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
						obstacleIcon = new ImageIcon("./src/images/brick51.png");
					} else {
						obstacleIcon = new ImageIcon("./src/images/brick52.png");
					}
					Image obstacleImage = obstacleIcon.getImage();
					g.drawImage(obstacleImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
				} else if (currentLevel == 4) {
					ImageIcon obstacleIcon = new ImageIcon("./src/images/brick21.png");
					if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
						obstacleIcon = new ImageIcon("./src/images/brick41.png");
					} else {
						obstacleIcon = new ImageIcon("./src/images/brick22.png");
					}
					Image obstacleImage = obstacleIcon.getImage();
					g.drawImage(obstacleImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
				} else if (currentLevel == 5) {
					ImageIcon obstacleIcon = new ImageIcon("./src/images/brick22.png");
					Image obstacleImage = obstacleIcon.getImage();
					g.drawImage(obstacleImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
				}
			} else if (cell.isBonus()) {
				ImageIcon bonusIcon = new ImageIcon("./src/images/red_apple.png");
				Image bonusImage = bonusIcon.getImage();
				g.drawImage(bonusImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE * 2, CELL_SIZE * 2, null);
			} else if (cell.isNextLevel()) {
				ImageIcon nextLevelIcon = new ImageIcon("./src/images/next_level.png");
				Image nextLevelImage = nextLevelIcon.getImage();
				g.drawImage(nextLevelImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE * 2, CELL_SIZE * 2, null);
			} else if (cell.isBox()) {
				ImageIcon boxIcon = new ImageIcon("./src/images/box2.png");
				Image boxImage = boxIcon.getImage();
				g.drawImage(boxImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
			} else if (cell.isBoxGoal()) {
				ImageIcon boxGoalIcon = new ImageIcon("./src/images/box1.png");
				Image boxGoalImage = boxGoalIcon.getImage();
				g.drawImage(boxGoalImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
			} else {
				g.setColor(new Color(255, 255, 255, 0));
			}
			if (!cell.isFood() && !cell.isObstacle() && !cell.isBonus() && !cell.isNextLevel() && !cell.isBox()
					&& !cell.isBoxGoal() && !cell.isSnake()) {
				g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			model.getSnake().setCurrentDirection(Direction.UP);
			break;
		case KeyEvent.VK_DOWN:
			model.getSnake().setCurrentDirection(Direction.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			model.getSnake().setCurrentDirection(Direction.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			model.getSnake().setCurrentDirection(Direction.RIGHT);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void update() {
		repaint();
	}

}
