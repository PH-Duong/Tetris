package gamemenu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class UserPanel extends JPanel {

    private JTextPane user;

    private int borderThickness;
    private Color color;
    private boolean bellOn;

    public UserPanel(Dimension d, String userName, int userScore) {
        this.setPreferredSize(d);
        this.setLayout(null);

        borderThickness = (int) (d.getHeight() * 0.01);
        color = new Color(31, 33, 38);
        this.setBorder(new MatteBorder(borderThickness, 0, 0, 0, color.brighter()));
        this.setBackground(color);

        user = new JTextPane();
        user.setEditable(true);
        user.setFocusable(false);
        user.setCursor(Cursor.getDefaultCursor());

        user.setBounds((int) (d.getWidth() * 0.8), 0, (int) (d.getWidth() * 0.18), (int) (d.getHeight() * 0.9));
        user.setBorder(new MatteBorder(3, 3, 3, 3, color.darker()));
        user.setBackground(color.darker());

        user.setContentType("text/html");
        user.setText("<html><p style='margin-top: 3mm; margin-right: 0mm; margin-bottom: 0mm; font-size: 11pt; font-family: \"Calibri\", sans-serif; line-height: 0.8;'><span style=\"font-size: 15px; font-family: Helvetica; color: rgb(239, 239, 239);\">&nbsp;" + userName + "</span></p>\n"
                + "<p style='margin: 0mm; font-size: 11pt; font-family: \"Calibri\", sans-serif; line-height: 0.4;'><span style=\"font-size: 12px; font-family: Helvetica; color: rgb(239, 239, 239);\">&nbsp; Best Score: " + userScore + "</span></p></html>");
        this.add(user);

        JLabel mainMenuText = new JLabel("<html><p style='margin-top: mm; margin-right: 0mm; margin-bottom: 0mm; font-size: 11pt; font-family: \"Calibri\", sans-serif; line-height: 0.3;'><span style=\"font-size: 32px; font-family: Helvetica; color: rgb(116, 125, 153);\">MAIN MENU<br></span></p></html>");
        this.add(mainMenuText);
        mainMenuText.setBounds(8, 12, 300, 50);

        bellOn = true;
        JLabel bell = new JLabel();
        ImageIcon bellOnIcon = new ImageIcon("bellon.png");
        bell.setIcon(new ImageIcon(bellOnIcon.getImage().getScaledInstance(bellOnIcon.getIconWidth(), bellOnIcon.getIconHeight(), Image.SCALE_DEFAULT)));
        JPanel testp = new JPanel();
        testp.setBounds((int) (user.getX() - user.getHeight() * 1.08), user.getY(), user.getHeight(), user.getHeight());
        testp.add(bell);
        testp.setLayout(null);
        bell.setBounds(15, 4, testp.getWidth(), testp.getHeight());
        testp.setBackground(color.darker());
        this.add(testp);

        testp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (bellOn) {
                    bellOn = false;
                    ImageIcon bellOnIcon = new ImageIcon("belloff.png");
                    bell.setIcon(new ImageIcon(bellOnIcon.getImage().getScaledInstance(bellOnIcon.getIconWidth(), bellOnIcon.getIconHeight(), Image.SCALE_DEFAULT)));
                } else {
                    bellOn = true;
                    ImageIcon bellOnIcon = new ImageIcon("bellon.png");
                    bell.setIcon(new ImageIcon(bellOnIcon.getImage().getScaledInstance(bellOnIcon.getIconWidth(), bellOnIcon.getIconHeight(), Image.SCALE_DEFAULT)));
                }
            }
        });

    }

}
