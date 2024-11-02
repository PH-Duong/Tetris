package gamemenu;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Leaderboard extends JFrame {

    private JPanel panel;

    public Leaderboard() {
        super("TOP 20");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(0x24384d));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setSize(400, 600);
    }

    public void loadAndDisplay() {
        setLocationRelativeTo(null); // Căn giữa màn hình mà không cần parentWindow
        setSize(640, 720);
        setResizable(false);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 60, 5, 60);

        panel.setBackground(new Color(0x24384d));

        try (BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"))) {
            String line;
            int rank = 0;
            while ((line = reader.readLine()) != null && rank < 20) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    try {
                        int score = Integer.parseInt(parts[1].trim());

                        JLabel rankLabel = createLabel(String.valueOf(rank + 1), SwingConstants.LEFT, new Color(0xc8cdd0), new Font("Arial", Font.PLAIN, 14));
                        JLabel nameLabel = createLabel(name, SwingConstants.LEFT, new Color(0xd9dadc), new Font("Arial", Font.BOLD, 14));

                        JPanel scorePanel = new JPanel();
                        scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
                        scorePanel.setBackground(new Color(0x24384d));
                        JLabel trophyLabel = new JLabel(new ImageIcon(new ImageIcon("Icon\\OIP.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
                        trophyLabel.setHorizontalAlignment(SwingConstants.LEFT);
                        scorePanel.add(trophyLabel);

                        JLabel scoreLabel = createLabel(String.valueOf(score), SwingConstants.LEFT, new Color(0xc8cdd0), new Font("Arial", Font.PLAIN, 14));
                        scorePanel.add(scoreLabel);

                        gbc.gridx = 0;
                        gbc.gridy = rank;
                        panel.add(rankLabel, gbc);

                        gbc.gridx = 1;
                        panel.add(nameLabel, gbc);

                        gbc.gridx = 2;
                        panel.add(scorePanel, gbc);

                        rank++;
                    } catch (NumberFormatException ex) {
                        System.err.println("Invalid score format: " + line);
                    }
                } else {
                    System.err.println("Invalid entry format: " + line);
                }
            }
        } catch (IOException e) {
            JLabel errorLabel = createLabel("Error loading leaderboard: " + e.getMessage(), SwingConstants.CENTER, Color.RED, new Font("Arial", Font.BOLD, 14));
            panel.add(errorLabel);
            System.err.println("Error loading leaderboard: " + e.getMessage());
        }
    }

    public void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    private JLabel createLabel(String text, int alignment, Color color, Font font) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(alignment);
        label.setForeground(color);
        label.setFont(font);
        return label;
    }
}
