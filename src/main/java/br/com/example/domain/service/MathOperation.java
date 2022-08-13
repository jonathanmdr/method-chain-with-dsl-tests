package br.com.example.domain.service;

import br.com.example.domain.exception.BusinessException;
import br.com.example.domain.model.OperationData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public enum MathOperation {

    SUM(
        data -> data.firstNumber().add(data.secondNumber())
    ),
    SUBTRACTION(
        data -> data.firstNumber().subtract(data.secondNumber())
    ),
    MULTIPLICATION(
        data -> data.firstNumber().multiply(data.secondNumber())
    ),
    DIVISION(
        data -> {
            try {
                return data.firstNumber().divide(data.secondNumber(), RoundingMode.HALF_DOWN);
            } catch (ArithmeticException ex) {
                throw new BusinessException("An unexpected error occurred", ex);
            }
        }
    );

    private final Function<OperationData, BigDecimal> mathFunction;

    MathOperation(final Function<OperationData, BigDecimal> mathFunction) {
        this.mathFunction = mathFunction;
    }

    public BigDecimal execute(final OperationData data) {
        return mathFunction.apply(data);
    }

}
