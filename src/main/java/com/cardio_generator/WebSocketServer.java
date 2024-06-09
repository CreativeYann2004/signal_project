package com.cardio_generator;

import com.cardio_generator.outputs.WebSocketOutputStrategy;

public class WebSocketServer {
    public static void main(String[] args) {
        WebSocketOutputStrategy server = new WebSocketOutputStrategy(8080);

        server.output(1, System.currentTimeMillis(), "HeartRate", "80");
    }
}
