package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;
    private Set<WebSocket> connections = new CopyOnWriteArraySet<>();

    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        server.start();
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
        broadcast(message);
    }

    private void broadcast(String message) {
        for (WebSocket conn : connections) {
            try {
                conn.send(message);
            } catch (Exception e) {
                System.err.println("Error sending message to " + conn.getRemoteSocketAddress() + ": " + e.getMessage());
            }
        }
    }

    private class SimpleWebSocketServer extends WebSocketServer {

        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            connections.add(conn);
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            connections.remove(conn);
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            System.err.println("An error occurred on connection " + conn.getRemoteSocketAddress() + ": " + ex.getMessage());
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}
