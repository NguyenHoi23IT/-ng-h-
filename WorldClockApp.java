package Run;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WorldClockApp extends JFrame {
    private JLabel clockLabel;

    public WorldClockApp() {
        setTitle("Clock App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(481, 359);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        clockLabel = new JLabel();
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(clockLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JTextField timeZoneField = new JTextField(10);
        JButton createClockButton = new JButton("Tạo đồng hồ");
        createClockButton.setFont(new Font("Arial", Font.BOLD, 14));
        createClockButton.setBackground(new Color(59, 89, 182));
        createClockButton.setForeground(Color.WHITE);
        createClockButton.setFocusPainted(false);
        createClockButton.addActionListener(e -> {
            String timeZoneOffset = timeZoneField.getText();
            createClock(timeZoneOffset);
        });

        controlPanel.add(new JLabel("Múi giờ:"));
        controlPanel.add(timeZoneField);
        controlPanel.add(createClockButton);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        Thread mainClockThread = new Thread(() -> {
            while (true) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String currentTime = dateFormat.format(new Date());
                clockLabel.setText(currentTime);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainClockThread.start();
    }

    private void createClock(String timeZoneOffset) {
        int offset;
        try {
            offset = Integer.parseInt(timeZoneOffset);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Múi giờ không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TimeZone timeZone = TimeZone.getTimeZone("GMT" + (offset >= 0 ? "+" : "") + offset);
        JFrame clockFrame = new JFrame("Đồng hồ - GMT" + (offset >= 0 ? "+" : "") + offset);
        clockFrame.setSize(200, 150);
        clockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clockFrame.setLocationRelativeTo(null);

        JLabel timeZoneLabel = new JLabel();
        timeZoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeZoneLabel.setFont(new Font("Arial", Font.BOLD, 18));
        clockFrame.getContentPane().add(timeZoneLabel);

        Thread clockThread = new Thread(() -> {
            while (true) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                dateFormat.setTimeZone(timeZone);
                String currentTime = dateFormat.format(new Date());
                timeZoneLabel.setText(currentTime);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        clockThread.start();

        clockFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WorldClockApp().setVisible(true);
        });
    }
}
