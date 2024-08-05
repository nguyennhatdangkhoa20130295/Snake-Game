package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DialogView {
	private JDialog dialog;
	private JButton continueButton;
	private JButton newGameButton;
	private JButton retryButton;
	private JButton homeButton;
	private JLabel titleLabel;
	private JLabel levelLabel;
	private JLabel scoreLabel;
	private JLabel scoreText;
	private JPanel titlePanel;
	private JPanel buttonPanel;

	public DialogView() {
		dialog = new JDialog();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// Tạo các thành phần trong hộp thoại
		titlePanel = new JPanel();

		titleLabel = new JLabel();
		titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setForeground(Color.YELLOW);

		EmptyBorder borderTitlePanel = new EmptyBorder(20, 10, 20, 10);
		titlePanel.setBorder(borderTitlePanel);
		titlePanel.add(titleLabel);

		JPanel contentPanel = new JPanel();
		EmptyBorder bordercontentPanel = new EmptyBorder(0, 20, 0, 20);
		contentPanel.setBackground(new Color(188, 108, 37));
		contentPanel.setBorder(bordercontentPanel);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		levelLabel = new JLabel();
		levelLabel.setFont(new Font("Montserrat", Font.PLAIN, 20));
		levelLabel.setForeground(Color.WHITE);
		levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		scoreLabel = new JLabel("ĐIỂM SỐ CỦA BẠN");
		scoreLabel.setFont(new Font("Montserrat", Font.PLAIN, 20));
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		scoreText = new JLabel();
		scoreText.setFont(new Font("Montserrat", Font.BOLD, 25));
		scoreText.setForeground(Color.WHITE);
		scoreText.setAlignmentX(Component.CENTER_ALIGNMENT);

		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);

		newGameButton = new JButton("Mới");
		newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGameButton.setFocusPainted(false);
		newGameButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
		newGameButton.setBackground(new Color(28, 116, 48));
		newGameButton.setForeground(Color.WHITE);

		continueButton = new JButton("Tiếp tục");
		continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueButton.setFocusPainted(false);
		continueButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
		continueButton.setBackground(new Color(28, 116, 48));
		continueButton.setForeground(Color.WHITE);

		retryButton = new JButton("Chơi lại");
		retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		retryButton.setFocusPainted(false);
		retryButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
		retryButton.setBackground(new Color(28, 116, 48));
		retryButton.setForeground(Color.WHITE);

		homeButton = new JButton("Màn hình chính");
		homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		homeButton.setFocusPainted(false);
		homeButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
		homeButton.setBackground(new Color(28, 116, 48));
		homeButton.setForeground(Color.WHITE);

		buttonPanel.add(newGameButton);
		buttonPanel.add(continueButton);
		buttonPanel.add(retryButton);
		buttonPanel.add(homeButton);

		contentPanel.add(Box.createVerticalStrut(10));
		contentPanel.add(levelLabel);
		contentPanel.add(Box.createVerticalStrut(10));
		contentPanel.add(scoreLabel);
		contentPanel.add(Box.createVerticalStrut(10));
		contentPanel.add(scoreText);
		contentPanel.add(Box.createVerticalStrut(20));
		contentPanel.add(buttonPanel);
		contentPanel.add(Box.createVerticalStrut(10));

		panel.add(titlePanel, BorderLayout.NORTH);
		panel.add(contentPanel, BorderLayout.CENTER);

		dialog.setContentPane(panel);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setAlwaysOnTop(true);
	}

	// Phương thức hiển thị hộp thoại hoàn thành màn chơi
	public void showPassLevelDialog(int currentLevel, int currentScore, boolean levelMode) {
		titlePanel.setBackground(new Color(138, 201, 38));
		titleLabel.setText("HOÀN THÀNH");
		levelLabel.setText("CẤP ĐỘ: " + currentLevel);
		scoreText.setText("" + currentScore);
		continueButton.setVisible(true);
		retryButton.setVisible(false);
		homeButton.setVisible(false);
		if (levelMode) {
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			buttonPanel.setPreferredSize(new Dimension(200, 80));
			continueButton.setVisible(false);
			newGameButton.setVisible(false);
			retryButton.setVisible(true);
			homeButton.setVisible(true);
		}
		if (currentLevel == 5) {
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			buttonPanel.setPreferredSize(new Dimension(200, 80));
			continueButton.setVisible(false);
			homeButton.setVisible(true);
		}
		dialog.pack();
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}

	// Phương thức hiển thị hộp thoại trò chơi kết thúc
	public void showGameOverDialog(int currentLevel, int currentScore, boolean levelMode) {
		titlePanel.setBackground(new Color(255, 0, 0));
		titleLabel.setText("THẤT BẠI!");
		levelLabel.setText("CẤP ĐỘ: " + currentLevel);
		scoreText.setText("" + currentScore);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setPreferredSize(new Dimension(200, 80));
		continueButton.setVisible(false);
		homeButton.setVisible(true);
		retryButton.setVisible(false);
		if (levelMode) {
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			buttonPanel.setPreferredSize(new Dimension(200, 80));
			newGameButton.setVisible(false);
			retryButton.setVisible(true);
		}
		dialog.pack();
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	public void addCountinueButtonListener(ActionListener continueAction) {
		continueButton.addActionListener(continueAction);
	}

	public void addNewGameButtonListener(ActionListener newGameAction) {
		newGameButton.addActionListener(newGameAction);
	}

	public void addRetryButtonListener(ActionListener retryAction) {
		retryButton.addActionListener(retryAction);
	}

	public void addHomeButtonListener(ActionListener homeAction) {
		homeButton.addActionListener(homeAction);
	}

	public void hideDialog() {
		dialog.setVisible(false);
	}

}
