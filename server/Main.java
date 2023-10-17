package server;

import server.client.gui.ClientGUI;
import server.server.domain.Server;
import server.server.gui.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        new ClientGUI(serverWindow);
        new ClientGUI(serverWindow);
    }
}