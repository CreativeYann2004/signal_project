package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
/**
 * Implements the {@link OutputStrategy} for sending output data via TCP.
 * This strategy sets up a TCP server on the specified port and waits for a client connection to send data.
 * to send data. As soon as a client is connected, it sends formatted patient data via this connection.
 *
 * <p>This class handles the creation of a server socket and manages client connections in a
 * separate thread so as not to block the main application flow. It is suitable for real-time data streaming
 * Scenarios where data needs to be sent immediately and continuously</p>.
 *
 * <p>Example application:
 * <pre>
 * TcpOutputStrategy tcpOutput = new TcpOutputStrategy(5000);
 * tcpOutput.output(123, System.currentTimeMillis(), "HeartRate", "120 bpm");
 * </pre>
 * </p>
 */
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
