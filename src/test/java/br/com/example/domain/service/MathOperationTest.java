package br.com.example.domain.service;

import br.com.example.domain.model.OperationData;
import br.com.example.domain.model.OperationDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.example.domain.service.MathOperation.SUM;

class MathOperationTest {

    @Test
    void shouldBeApplySum() {
        OperationData operationData = OperationDataBuilder.builder()
            .firstNumber(BigDecimal.TEN)
            .secondNumber(BigDecimal.TEN)
            .build();

        BigDecimal actual = SUM.execute(operationData);

        Assertions.assertThat(actual)
            .usingComparator(BigDecimal::compareTo)
            .isEqualTo(BigDecimal.valueOf(20));
    }

}
