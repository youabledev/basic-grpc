package com.youable.bank_client.account;

import com.youable.bank_server.AccountProto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v0/account")
@RequiredArgsConstructor
@RestController
public class AccountController {
    private final GrpcAccountClient client;

    @PostMapping("/regist")
    public String regist() {
        AccountProto.RegistAccountRequest request = AccountProto.RegistAccountRequest
                .newBuilder()
                .setUserId(1)
                .setBalance("2000")
                .build();
        AccountProto.RegistAccountResponse accountResponse = client.registAccount(request);
        return accountResponse.getAccountNumber();
    }
}
