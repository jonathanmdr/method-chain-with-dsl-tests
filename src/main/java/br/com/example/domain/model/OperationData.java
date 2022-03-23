package br.com.example.domain.model;

import java.math.BigDecimal;

public record OperationData(
    BigDecimal firstNumber,
    BigDecimal secondNumber
) {

}
