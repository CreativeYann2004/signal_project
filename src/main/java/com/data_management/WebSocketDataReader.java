package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Class for reading patient data from a WebSocket server and storing it in DataStorage.
 */
public class WebSocketDataReader implements DataReader {

    private String serverUri;
    private WebSocketClient client;
    private DataStorage dataStorage;
    private Consumer<String> onDataReceivedListener;

    /**
     * Constructs a WebSocketDataReader with the specified server URI.
     *
     * @param serverUri The URI of the WebSocket server.
     * @throws URISyntaxException If the server URI is invalid.
     */
    public WebSocketDataReader(String serverUri) throws URISyntaxException {
        this.serverUri = serverUri;
        this.client = new WebSocketClient(new URI(serverUri)) {
            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println("Connected to WebSocket server");
            }

            @Override
            public void onMessage(String message) {
                try {
                    System.out.println("Message received: " + message);
                    if (onDataReceivedListener != null) {
                        onDataReceivedListener.accept(message);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing message: " + e.getMessage());
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Connection closed: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket error: " + ex.getMessage());
            }
        };
    }

    /**
     * Unsupported operation for WebSocketDataReader. Throws UnsupportedOperationException.
     *
     * @param dataStorage The data storage where the data would be stored.
     * @throws IOException                  If an I/O error occurs.
     * @throws UnsupportedOperationException Always thrown for this implementation.
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        throw new UnsupportedOperationException("WebSocketDataReader does not support readData");
    }

    /**
     * Connects to the WebSocket server and reads data into the specified data storage.
     *
     * @param dataStorage The data storage where the data will be stored.
     */
    @Override
    public void connectAndReadData(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        client.connect();
    }

    /**
     * Stops the WebSocket client connection.
     */
    public void stop() {
        if (client != null) {
            client.close();
        }
    }

    /**
     * Sets a listener for when data is received from the WebSocket server.
     *
     * @param onDataReceivedListener The listener to be called when data is received.
     */
    public void setOnDataReceivedListener(Consumer<String> onDataReceivedListener) {
        this.onDataReceivedListener = onDataReceivedListener;
    }

    /**
     * Returns the WebSocket client.
     *
     * @return The WebSocket client.
     */
    public WebSocketClient getClient() {
        return client;
    }
}
