package com.example.rpc.client;

import com.example.domain.client.ExternalService;
import org.springframework.stereotype.Service;

/**
 * The infrastructure-layer implementation of the ExternalService interface.
 * This class uses the RPC client to fulfill the contract defined in the domain layer.
 */
@Service // Mark it as a Spring bean so it can be injected automatically
public class ExternalServiceImpl implements ExternalService {

    // In a real app, this client might be configured and injected as well.
    private final ExampleRpcClient rpcClient = new ExampleRpcClient();

    @Override
    public String getExternalInfo(String id) {
        // Delegate the call to the actual RPC client.
        return rpcClient.fetchDataFromServer(id);
    }
}
