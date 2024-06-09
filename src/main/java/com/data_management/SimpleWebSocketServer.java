package com.data_management;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

/**
 * Simple WebSocket server for handling connections and messages.
 */
public class SimpleWebSocketServer extends WebSocketServer {

    /**
     * Constructs a SimpleWebSocketServer with the specified port.
     *
     * @param port The port on which the server will listen.
     */
    public SimpleWebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    /**
     * Called when a new WebSocket connection is opened.
     *
     * @param conn       The WebSocket connection.
     * @param handshake  The handshake data of the connection.
     */
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection opened: " + conn.getRemoteSocketAddress());
        // Send a test message to the client
        conn.send("12345,98.6,HeartRate," + System.currentTimeMillis());
    }

    /**
     * Called when a WebSocket connection is closed.
     *
     * @param conn   The WebSocket connection.
     * @param code   The closing code.
     * @param reason The reason for the closure.
     * @param remote Whether the closure was initiated by the remote peer.
     */
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + conn.getRemoteSocketAddress());
    }

    /**
     * Called when a message is received from a WebSocket connection.
     *
     * @param conn    The WebSocket connection.
     * @param message The message received from the client.
     */
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message received from client: " + message);
    }

    /**
     * Called when an error occurs on a WebSocket connection.
     *
     * @param conn The WebSocket connection.
     * @param ex   The exception that occurred.
     */
    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("Error: " + ex.getMessage());
    }

    /**
     * Called when the WebSocket server starts.
     */
    @Override
    public void onStart() {
        System.out.println("Server started successfully!");
    }

    /**
     * The main method to start the WebSocket server.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        int port = 8080;
        SimpleWebSocketServer server = new SimpleWebSocketServer(port);
        server.start();
        System.out.println("WebSocket server started on port: " + port);
    }
}
