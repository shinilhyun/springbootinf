package com.shin.springbootinf;

import com.shin.springbootinf.account.Account;
import com.shin.springbootinf.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account ilhyun = accountService.createAccount("ilhyun", "1234");
        System.out.println(ilhyun.getUsername() + " " + ilhyun.getPassword());
    }
}
