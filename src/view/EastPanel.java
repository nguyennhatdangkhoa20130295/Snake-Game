package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EastPanel extends JPanel {
	private JLabel liveLabel;

	public EastPanel() {
		setOpaque(false);
		setPreferredSize(new Dimension(230, 720));
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
	}

	public void setLiveLabel(int live) {
		removeAll();

		// Lặp qua số lượng live hiện tại trong model và tạo các JLabel tương ứng
		for (int i = 0; i < live; i++) {
			ImageIcon icon = new ImageIcon("./src/images/heart.png");
			Image image = icon.getImage();
			Image newImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newImage);
			liveLabel = new JLabel(newIcon);

			add(liveLabel);
			add(Box.createHorizontalStrut(5));
		}

		revalidate();
		repaint();
	}
}
