package no.ntnu;

import javax.swing.*;
import java.awt.*;

public class RemoteView {
    private JFrame frame;
    JButton powerOnButton;
    JButton powerOffButton;
    JButton channelUpButton;
    JButton channelDownButton;
    JButton setChannelButton;
    private JTextField channelField;
    private JTextArea responseArea;

    public RemoteView() {
        frame = new JFrame("Remote Control");
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2));

        powerOnButton = new JButton("Turn On");
        powerOffButton = new JButton("Turn Off");
        channelUpButton = new JButton("Channel Up");
        channelDownButton = new JButton("Channel Down");
        setChannelButton = new JButton("Set Channel");
        channelField = new JTextField();

        mainPanel.add(powerOnButton);
        mainPanel.add(powerOffButton);
        mainPanel.add(channelUpButton);
        mainPanel.add(channelDownButton);
        mainPanel.add(setChannelButton);
        mainPanel.add(channelField);

        responseArea = new JTextArea();
        responseArea.setEditable(false);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(responseArea, BorderLayout.SOUTH);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
