package br.com.example.domain.model;

import java.math.BigDecimal;

public class OperationDataBuilder {

    private BigDecimal firstNumber;
    private BigDecimal secondNumber;

    public static OperationDataBuilder builder() {
        return new OperationDataBuilder();
    }

    public OperationDataBuilder firstNumber(final BigDecimal firstNumber) {
        this.firstNumber = firstNumber;
        return this;
    }

    public OperationDataBuilder secondNumber(final BigDecimal secondNumber) {
        this.secondNumber = secondNumber;
        return this;
    }

    public OperationData build() {
        return new OperationData(firstNumber, secondNumber);
    }

}
