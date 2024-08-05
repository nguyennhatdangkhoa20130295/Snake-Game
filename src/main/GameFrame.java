package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.GameController;
import model.ISnakeModel;
import model.SnakeModel;
import view.DialogView;
import view.EastPanel;
import view.SnakeView;
import view.SouthPanel;
import view.WestPanel;

public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameFrame() {
		setTitle("Snake Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		showMainScreen();

		setVisible(true);
	}

	public void showMainScreen() {
		MainScreen mainScreen = new MainScreen(this);
		setContentPane(mainScreen);
		revalidate();
		repaint();
	}

	private void showGuideScreen() {
		GuideScreen guideScreen = new GuideScreen(this);
		setContentPane(guideScreen);
		revalidate();
		repaint();
	}

	private void showLevelSelectScreen() {
		LevelSelectScreen levelSelectScreen = new LevelSelectScreen(this);
		setContentPane(levelSelectScreen);
		revalidate();
		repaint();
	}

	private void showGameScreen(int level, boolean levelMode) {
		ISnakeModel model = new SnakeModel(level, levelMode);
		SnakeView view = new SnakeView(model);
		SouthPanel southPanel = new SouthPanel(model);
		EastPanel eastPanel = new EastPanel();
		WestPanel westPanel = new WestPanel();
		DialogView dialogView = new DialogView();
		GameController controller = new GameController(model, view, southPanel, eastPanel, westPanel, dialogView, this);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		ImageIcon backgroundImage = new ImageIcon("./src/images/background0.jpg");
		Image image = backgroundImage.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT);
		backgroundImage = new ImageIcon(image);

		JLabel backgroundLabel = new JLabel(backgroundImage);
		backgroundLabel.setLayout(new BoxLayout(backgroundLabel, BoxLayout.X_AXIS));
		setContentPane(backgroundLabel);

		JPanel contentPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(820, 720));
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		contentPanel.setOpaque(false);
		contentPanel.add(view);
		contentPanel.add(southPanel);

		add(westPanel);
		add(contentPanel);
		add(eastPanel);

		revalidate();
		repaint();

		view.requestFocusInWindow();

		controller.startGame();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameFrame();
			}
		});
	}

	public static class MainScreen extends JPanel {

		private JButton playButton;
		private JButton guideButton;
		private JButton levelButton;

		public MainScreen(GameFrame frame) {
			setLayout(new BorderLayout());
			setOpaque(false);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(3, 1));
			buttonPanel.setOpaque(false);
			buttonPanel.setPreferredSize(new Dimension(200, 250));

			playButton = createButton("CHƠI");
			guideButton = createButton("HƯỚNG DẪN");
			levelButton = createButton("CHỌN CẤP ĐỘ");

			JLabel label = new JLabel("SNAKE");
			label.setForeground(Color.YELLOW);
			label.setFont(new Font("Montserrat", Font.BOLD, 110));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = screenSize.width;
			int screenHeight = screenSize.height;

			ImageIcon backgroundImage = new ImageIcon("./src/images/bg_option.jpg");
			Image image = backgroundImage.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
			backgroundImage = new ImageIcon(image);

			JLabel backgroundLabel = new JLabel(backgroundImage);
			backgroundLabel.setLayout(new BorderLayout());

			playButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.showGameScreen(1, false);
				}
			});

			guideButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.showGuideScreen();
				}
			});

			levelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.showLevelSelectScreen();
				}
			});

			buttonPanel.add(playButton);
			buttonPanel.add(guideButton);
			buttonPanel.add(levelButton);

			JPanel containerPanel = new JPanel(new BorderLayout());
			containerPanel.setOpaque(false);
			containerPanel.add(buttonPanel, BorderLayout.CENTER);
			containerPanel.setBorder(new EmptyBorder(0, 0, 60, 0));

			backgroundLabel.add(label, BorderLayout.CENTER);
			backgroundLabel.add(containerPanel, BorderLayout.SOUTH);
			add(backgroundLabel);
		}

		private JButton createButton(String text) {
			JButton button = new JButton(text);
			button.setFocusPainted(false);
			button.setFont(new Font("Montserrat", Font.BOLD, 30));
			button.setForeground(Color.YELLOW);
			button.setContentAreaFilled(false);
			button.setBorder(new EmptyBorder(-1, -1, -1, -1));
			return button;
		}
	}

	public static class GuideScreen extends JPanel {
		private JButton backButton;

		public GuideScreen(GameFrame frame) {
			setLayout(new BorderLayout());
			String guideText = "<html><body style='text-align: left; color: white'>"
					+ "<h1 style='font-size: 20px'>Hướng dẫn chơi game:</h1>"
					+ "<p style='font-size: 14px'>- Sử dụng 4 phím mũi tên để di chuyển rắn.</p>"
					+ "<p style='font-size: 14px'>- Nhấn phím P để tạm dừng trò chơi.</p>"
					+ "<p style='font-size: 14px'>- Nhấn phím P hoặc 4 phím mũi tên để tiếp tục trò chơi.</p>"
					+ "<p style='font-size: 14px'>- Trong quá trình chơi sẽ xuất hiện các mồi bonus, hãy di chuyển rắn ăn nó để được cộng thêm điểm.</p>"
					+ "<p style='font-size: 14px'>- Bắt đầu game người chơi có tối đa 3 mạng. Khi hết số mạng, trò chơi sẽ kết thúc ngay.</p>"
					+ "<p style='font-size: 14px'>- Ở cấp độ 5, khi người chơi di chuyển rắn đẩy hộp đến vị trí hộp mục tiêu, nếu hộp va chạm vào chướng ngại vật thì sẽ được khởi tạo lại.</p>"
					+ "<p style='font-size: 14px'>- Chọn CHƠI ở màn hình chính, bắt đầu với cấp độ đầu tiên và người chơi cần phải hoàn thành cấp độ chơi hiện tại để sang cấp độ tiếp theo.</p>"
					+ "<p style='font-size: 14px'>- Người chơi cũng có thể chọn cấp độ chơi trong mục CHỌN CẤP ĐỘ ở màn hình chính.</p>"
					+ "</body></html>";
			JLabel guideLabel = new JLabel(guideText);
			guideLabel.setHorizontalAlignment(SwingConstants.CENTER);
			guideLabel.setVerticalAlignment(SwingConstants.CENTER);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = screenSize.width;
			int screenHeight = screenSize.height;

			ImageIcon backgroundImage = new ImageIcon("./src/images/bg_option.jpg");
			Image image = backgroundImage.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
			backgroundImage = new ImageIcon(image);

			JLabel backgroundLabel = new JLabel(backgroundImage);
			backgroundLabel.setLayout(new BorderLayout());

			backButton = new JButton("TRỞ LẠI");
			backButton.setFocusPainted(false);
			backButton.setFont(new Font("Montserrat", Font.BOLD, 30));
			backButton.setForeground(Color.YELLOW);
			backButton.setContentAreaFilled(false);
			backButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			backButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.showMainScreen();
				}
			});

			backgroundLabel.add(guideLabel, BorderLayout.CENTER);
			backgroundLabel.add(backButton, BorderLayout.SOUTH);
			add(backgroundLabel);
		}
	}

	public static class LevelSelectScreen extends JPanel {
		private JButton backButton;
		private JButton startButton;
		private JComboBox<Integer> levelComboBox;

		public LevelSelectScreen(GameFrame frame) {
			setLayout(new BorderLayout());

			Integer[] levels = { 1, 2, 3, 4, 5 };
			JLabel levelLabel = new JLabel("Cấp độ: ");
			levelLabel.setFont(new Font("Montserrat", Font.BOLD, 30));
			levelComboBox = new JComboBox<>(levels);
			levelComboBox.setPreferredSize(new Dimension(100, 40));
			levelComboBox.setFont(new Font("Montserrat", Font.BOLD, 30));
			levelComboBox.setForeground(Color.BLACK);
			levelComboBox.setBackground(Color.WHITE);

			JPanel levelPanel = new JPanel();
			levelPanel.setOpaque(false);
			levelPanel.add(levelLabel);
			levelPanel.add(levelComboBox);

			backButton = createButton("TRỞ LẠI");
			startButton = createButton("BẮT ĐẦU");

			backButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.showMainScreen();
				}
			});

			startButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedLevel = (int) levelComboBox.getSelectedItem();
					frame.showGameScreen(selectedLevel, true);
				}
			});

			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 5));
			buttonPanel.setOpaque(false);
			buttonPanel.add(backButton);
			buttonPanel.add(startButton);

			JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
			centerPanel.setOpaque(false);
			centerPanel.add(Box.createVerticalGlue());
			centerPanel.add(levelPanel);
			centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
			centerPanel.add(buttonPanel);
			centerPanel.add(Box.createVerticalGlue());

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = screenSize.width;
			int screenHeight = screenSize.height;

			ImageIcon backgroundImage = new ImageIcon("./src/images/bg_option.jpg");
			Image image = backgroundImage.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
			backgroundImage = new ImageIcon(image);

			JLabel backgroundLabel = new JLabel(backgroundImage);
			backgroundLabel.setLayout(new BorderLayout());
			backgroundLabel.add(centerPanel, BorderLayout.CENTER);

			add(backgroundLabel);
		}

		private JButton createButton(String text) {
			JButton button = new JButton(text);
			button.setFocusPainted(false);
			button.setFont(new Font("Montserrat", Font.BOLD, 30));
			button.setForeground(Color.YELLOW);
			button.setContentAreaFilled(false);
			button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			return button;
		}
	}

}
