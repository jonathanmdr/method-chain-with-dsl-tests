package br.com.example.domain;

import br.com.example.domain.model.OperationData;
import br.com.example.domain.model.OperationDataBuilder;
import br.com.example.domain.service.MathOperation;

import java.math.BigDecimal;
import java.util.Arrays;

public class Main {

    public static void main(String ... args) {
        OperationData operationData = OperationDataBuilder.builder()
            .firstNumber(BigDecimal.TEN)
            .secondNumber(BigDecimal.TEN)
            .build();

        Arrays.stream(MathOperation.values())
            .forEach(operation -> {
                BigDecimal result = operation.execute(operationData);
                print(operation.name(), result);
            }
        );
    }

    private static void print(final String operationName, final BigDecimal result) {
        String message = operationName.concat(": %s\n");
        System.out.printf(message, result);
    }

}
