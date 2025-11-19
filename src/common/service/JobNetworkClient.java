package common.service;

import java.io.*;
import java.net.Socket;

public class JobNetworkClient {
    private final String host;
    private final int port;
    private StatusCallback callback;

    public interface StatusCallback {
        void onStatusReceived(String status);
    }

    public JobNetworkClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setStatusCallback(StatusCallback callback) {
        this.callback = callback;
    }

    public void sendJobLine(String line) {
        try {
            Socket socket = new Socket(host, port);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.write(line);
            out.newLine();
            out.flush();
            
            Thread listenerThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        callback.onStatusReceived(response);
                    }
                } catch (IOException e) {
                }
            });
            listenerThread.setDaemon(true);
            listenerThread.start();
        } catch (IOException e) {
        }
    }
}
