package br.com.financial.adapters.inbound.api.controller;

import br.com.financial.adapters.inbound.api.dto.TransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/transactions")
public class TransactionController {

    @GetMapping
    public ResponseEntity<TransactionDTO> getTransactions() {

        var response = new TransactionDTO();
        response.setId("aaa");
        response.setName("Test");

        return ResponseEntity.ok(response);
    }
}
