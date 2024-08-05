package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import model.ISnakeModel;
import observer.Observer;

public class SouthPanel extends JPanel implements Observer {
	private JLabel scoreLabel;
	private JPanel progressBarContainer;
	private JProgressBar progressBarNormal;
	private JProgressBar progressBarBonus;
	private Dimension progressBarSize;
	private Timer bonusTimer;
	private ISnakeModel model;

	public SouthPanel(ISnakeModel model) {
		this.model = model;
		this.model.registerObserver(this);
		bonusTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Xử lý sự kiện khi timer được kích hoạt
				if (model.isPlaying()) {
					decreaseProgressBarValue();
				}
			}
		});
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.LEFT));

		scoreLabel = new JLabel();
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("Segoe Print", Font.PLAIN, 55));
		setScoreLabel(0);
		scoreLabel.setPreferredSize(new Dimension(200, 90));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setVerticalAlignment(SwingConstants.CENTER);
		add(scoreLabel);

		progressBarContainer = new JPanel();
		progressBarContainer.setLayout(new BoxLayout(progressBarContainer, BoxLayout.Y_AXIS));
		progressBarContainer.setOpaque(false);

		progressBarContainer.add(Box.createVerticalStrut(10));
		progressBarSize = new Dimension(580, 30);
		progressBarNormal = new JProgressBar();
		progressBarNormal.setMaximumSize(progressBarSize);
		progressBarNormal.setMinimumSize(progressBarSize);
		progressBarNormal.setPreferredSize(progressBarSize);
		progressBarNormal.setBorder(new EmptyBorder(-1, -1, -1, -1));
		progressBarNormal.setBorderPainted(false);
		progressBarContainer.add(progressBarNormal);

		progressBarContainer.add(Box.createVerticalStrut(10));

		progressBarBonus = new JProgressBar();
		progressBarBonus.setMaximumSize(progressBarSize);
		progressBarBonus.setMinimumSize(progressBarSize);
		progressBarBonus.setPreferredSize(progressBarSize);
		progressBarBonus.setBorderPainted(false);
		progressBarBonus.setBorder(new EmptyBorder(-1, -1, -1, -1));
		progressBarBonus.setValue(100);
		progressBarBonus.setForeground(new Color(204, 9, 9));
		progressBarBonus.setVisible(false);
		progressBarContainer.add(progressBarBonus);

		add(progressBarContainer);

	}

	public void setScoreLabel(int score) {
		if (score < 10) {
			scoreLabel.setText("000" + score);
		} else if (score < 100) {
			scoreLabel.setText("00" + score);
		} else if (score < 1000) {
			scoreLabel.setText("0" + score);
		} else {
			scoreLabel.setText("" + score);
		}

	}

	public void showBonusProgressBar(boolean show) {
		if (show) {
			progressBarBonus.setVisible(true);
			// Bắt đầu timer khi progressBarBonus được hiển thị
			bonusTimer.start();
		} else {
			// Nếu không hiển thị, dừng timer và ẩn progressBarBonus
			progressBarBonus.setVisible(false);
			bonusTimer.stop();
			progressBarBonus.setValue(100);
			model.setShowBonusProgressBar(show);
			model.resetBonusFood();
		}
	}

	public void updateProgressBar(int foodScore, int foodCount) {
		int newValue = foodScore * foodCount;
		// Đảm bảo giá trị không vượt quá 100%
		if (newValue > 100) {
			newValue = 100;
		}
		model.setProgressBarValue(newValue);
		progressBarNormal.setValue(model.getProgressBarValue()); // Cập nhật giá trị mới cho JProgressBar
		// Đặt màu xanh lá cho JProgressBar
		progressBarNormal.setForeground(new Color(98, 206, 27));
	}

	private void decreaseProgressBarValue() {
		int initialValue = model.getInitialBonusTime();
		int currentValue = model.decreaseBonusTime();
		if (currentValue > 0) {
			model.decreaseBonusScore(6);
			double remainingPercentage = (double) currentValue / initialValue * 100;
			int roundedPercentage = (int) Math.round(remainingPercentage);
			progressBarBonus.setValue(roundedPercentage);
		} else {
			// Khi giá trị giảm xuống 0, dừng timer và ẩn progressBarBonus
			// Cập nhật trạng thái trong SnakeModel
			showBonusProgressBar(false);
		}
	}

	@Override
	public void update() {
		setScoreLabel(model.getCurrentScore());
		updateProgressBar(model.getFoodScore(), model.getFoodCount());
		showBonusProgressBar(model.isShowBonusProgressBar());
	}

}
