package com.youable.bank_server.account.domain.service;

import com.youable.bank_server.AccountProto;
import com.youable.bank_server.AccountServiceGrpc;
import com.youable.bank_server.account.domain.Account;
import com.youable.bank_server.account.domain.repository.AccountRepository;
import com.youable.bank_server.common.util.BigDecimalConverter;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class AccountServiceImpl extends AccountServiceGrpc.AccountServiceImplBase {
    private final AccountRepository accountRepository;

    @Override
    public void registAccount(AccountProto.RegistAccountRequest request, StreamObserver<AccountProto.RegistAccountResponse> responseObserver) {
        BigDecimal balance = BigDecimalConverter.fromString(request.getBalance());
        long userId = request.getUserId();

        Account account = Account.builder()
                .accountNumber(UUID.randomUUID().toString())
                .balance(balance)
                .userId(userId)
                .build();

        accountRepository.save(account);

        AccountProto.RegistAccountResponse response = AccountProto.RegistAccountResponse.newBuilder()
                .setAccountNumber(account.getAccountNumber())
                .setUserId(account.getUserId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // 계좌조회
    @Override
    public void getAccountDetails(AccountProto.AccountRequest request, StreamObserver<AccountProto.AccountResponse> responseObserver) {
        long userId = request.getUserId();
        Account account = accountRepository.findByUserId(userId);
        AccountProto.AccountResponse response = AccountProto.AccountResponse.newBuilder()
                .setAccountId(account.getId())
                .setAccountNumber(account.getAccountNumber())
                .setIsActive(account.isActive())
                .setBalance(BigDecimalConverter.toString(account.getBalance()))
                .build();
        // 클라이언트에게 응답 전달
        responseObserver.onNext(response);
        // 에러를 전달해야 하는 경우
//        responseObserver.onError(new RuntimeException("Account not found"));
        responseObserver.onCompleted();
    }
}
