package Run;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class WorldClockApp extends JFrame {
    private JLabel clockLabel;
    private JTextField timezoneTextField; // Thay thế JComboBox bằng JTextField
    private JPanel mainPanel;
    private JPanel clockGrid;

    public WorldClockApp() {
        setTitle("World Clock");
        setSize(609, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        mainPanel.add(clockLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        timezoneTextField = new JTextField(20); // JTextField để nhập múi giờ
        inputPanel.add(timezoneTextField);

        JButton addButton = new JButton("Add Clock");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addClock();
            }
        });
        inputPanel.add(addButton);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        clockGrid = new JPanel();
        clockGrid.setLayout(new BoxLayout(clockGrid, BoxLayout.Y_AXIS));
        mainPanel.add(clockGrid, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        setVisible(true);

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }
        });
        timer.start();
    }

    private void updateClock() {
        TimeZone defaultTimeZone = TimeZone.getDefault();
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(defaultTimeZone);
        clockLabel.setText(sdf.format(currentTime));
    }

    private void addClock() {
        String selectedTimezone = timezoneTextField.getText(); // Lấy giá trị từ JTextField
        if (selectedTimezone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a timezone.");
            return;
        }

        JLabel newClockLabel = new JLabel();
        newClockLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        newClockLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JPanel clockPanel = new JPanel();
        clockPanel.setLayout(new FlowLayout());
        clockPanel.add(newClockLabel);

        clockGrid.add(clockPanel);

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TimeZone customTimeZone = TimeZone.getTimeZone(selectedTimezone);
                Date currentTime = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                sdf.setTimeZone(customTimeZone);
                newClockLabel.setText(selectedTimezone + ": " + sdf.format(currentTime));
            }
        });
        timer.start();

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WorldClockApp();
            }
        });
    }
}
