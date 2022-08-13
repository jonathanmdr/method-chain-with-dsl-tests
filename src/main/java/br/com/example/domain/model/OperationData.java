package br.com.example.domain.model;

import java.math.BigDecimal;

public record OperationData(
    BigDecimal firstNumber,
    BigDecimal secondNumber
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BigDecimal firstNumber;
        private BigDecimal secondNumber;

        public Builder firstNumber(final double value) {
            this.firstNumber = toBigDecimal(value);
            return this;
        }

        public Builder secondNumber(final double value) {
            this.secondNumber = toBigDecimal(value);
            return this;
        }

        public OperationData build() {
            return new OperationData(this.firstNumber, this.secondNumber);
        }

        private BigDecimal toBigDecimal(final double value) {
            return BigDecimal.valueOf(value);
        }
    }

}
