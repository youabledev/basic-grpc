package com.youable.bank_server.account.domain.service;

import com.youable.bank_server.AccountProto;
import com.youable.bank_server.AccountServiceGrpc;
import com.youable.bank_server.account.domain.Account;
import com.youable.bank_server.account.domain.repository.AccountRepository;
import com.youable.bank_server.common.util.BigDecimalConverter;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountServiceImpl extends AccountServiceGrpc.AccountServiceImplBase {
    private final AccountRepository accountRepository;

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
        responseObserver.onCompleted();
    }
}
