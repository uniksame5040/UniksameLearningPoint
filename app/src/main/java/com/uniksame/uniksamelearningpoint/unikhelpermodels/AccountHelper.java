package com.uniksame.uniksamelearningpoint.unikhelpermodels;

public class AccountHelper {

    private String accountNumber;
    private String ifscCode;

    public AccountHelper() {
    }

    public AccountHelper(String accountNumber, String ifscCode) {
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
