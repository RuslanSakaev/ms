package server.client.gui;

import server.client.domain.Client;
import server.server.gui.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ClientView {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    JTextArea log;
    JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    JPasswordField password;
    JButton btnLogin, btnSend;
    JPanel headerPanel;

    private Client client;

    public ClientGUI(ServerWindow server) {
        setting(server);
        createPanel();

        setVisible(true);
    }

    private void setting(ServerWindow server) {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setLocation(server.getX() - 500, server.getY());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        client = new Client(this, server.getConnection());
    }

    public void showMessage(String msg) {
        log.append(msg);
    }

    public void disconnectFromServer(){
        hideHeaderPanel(true);
        client.disconnectFromServer();
    }

    public void hideHeaderPanel(boolean visible){
        headerPanel.setVisible(visible);
    }

    public void login(){
        if (client.connectToServer(tfLogin.getText())){
            headerPanel.setVisible(false);
        }
    }

    private void message(){
        client.message(tfMessage.getText());
        tfMessage.setText("");
    }

    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    private Component createHeaderPanel() {
        headerPanel = new JPanel(new GridLayout(2,4));
        JLabel ipAddressLabel = new JLabel("IP Address:");
        tfIPAddress = new JTextField("127.0.0.1");
        JLabel portLabel = new JLabel("Port:");
        tfPort = new JTextField("8189");
        JLabel stub = new JLabel();
        JLabel usernameLabel = new JLabel("Username:");
        tfLogin = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        password = new JPasswordField();
        btnLogin = new JButton("login");
        btnLogin.addActionListener(e -> login());
        headerPanel.add(ipAddressLabel);
        headerPanel.add(tfIPAddress);
        headerPanel.add(portLabel);
        headerPanel.add(tfPort);
        headerPanel.add(stub);
        headerPanel.add(usernameLabel);
        headerPanel.add(tfLogin);
        headerPanel.add(passwordLabel);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }

    private Component createLog() {
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    message();
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(e -> message());
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            disconnectFromServer();
        }
    }
}