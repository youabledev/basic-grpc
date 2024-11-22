package com.youable.bank_client.account;

import com.youable.bank_server.AccountProto;
import com.youable.bank_server.AccountServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class GrpcAccountClient {
    private final AccountServiceGrpc.AccountServiceBlockingStub blockingStub;

    public GrpcAccountClient() {
        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        blockingStub = AccountServiceGrpc.newBlockingStub(channel);
    }

    public AccountProto.RegistAccountResponse registAccount(AccountProto.RegistAccountRequest request) {
        return blockingStub.registAccount(request);
    }
}
