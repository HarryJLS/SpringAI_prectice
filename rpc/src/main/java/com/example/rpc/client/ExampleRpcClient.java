package com.example.rpc.client;

/**
 * A client that simulates making a low-level RPC call.
 */
public class ExampleRpcClient {

    public String fetchDataFromServer(String id) {
        // In a real scenario, this would contain logic for:
        // 1. Connecting to the remote server.
        // 2. Serializing the request.
        // 3. Sending the request and receiving the response.
        // 4. Deserializing the response.
        System.out.println("Executing RPC call for ID: " + id);
        return "RPC Result for " + id;
    }
}