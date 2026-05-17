package com.example.ssh_ch10_ex6.services;

import com.example.ssh_ch10_ex6.exceptions.NotEnoughMoneyException;
import com.example.ssh_ch10_ex6.model.PaymentDetails;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public PaymentDetails processPayment() {
        throw new NotEnoughMoneyException();
    }
}
