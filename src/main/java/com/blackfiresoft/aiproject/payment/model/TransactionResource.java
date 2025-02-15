package com.blackfiresoft.aiproject.payment.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TransactionResource {

    private String original_type;
    private String algorithm;
    private String ciphertext;
    private String associated_data;
    private String nonce;
}
