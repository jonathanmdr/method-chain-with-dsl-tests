package br.com.example.domain.service;

import br.com.example.domain.exception.BusinessException;
import br.com.example.domain.model.OperationData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Function;

import static br.com.example.domain.service.MathOperation.DIVISION;
import static br.com.example.domain.service.MathOperation.MULTIPLICATION;
import static br.com.example.domain.service.MathOperation.SUBTRACTION;
import static br.com.example.domain.service.MathOperation.SUM;

class MathOperationDslTest {

    @Test
    void shouldBeApplySum() {
        // Given
        final OperationData operationData = OperationData.builder()
            .firstNumber(10)
            .secondNumber(10)
            .build();

        // When
        final BigDecimal actual = SUM.execute(operationData);

        // Then
        Assertions.assertThat(actual)
            .usingComparator(BigDecimal::compareTo)
            .isEqualTo(BigDecimal.valueOf(20));
    }

    @Test
    void shouldBeApplySumWithDsl() {
         given()
             .firstNumberEqualTo(10)
             .secondNumberEqualTo(10)
         .when(SUM::execute)
         .then()
             .assertResult()
                .isEqualTo(20);
    }

    @Test
    void shouldBeApplySubtractionWithDsl() {
        given()
            .firstNumberEqualTo(10)
            .secondNumberEqualTo(10)
        .when(SUBTRACTION::execute)
        .then()
            .assertResult()
                .isEqualTo(0);
    }

    @Test
    void shouldBeApplyMultiplicationWithDsl() {
        given()
            .firstNumberEqualTo(10)
            .secondNumberEqualTo(10)
        .when(MULTIPLICATION::execute)
        .then()
            .assertResult()
                .isEqualTo(100);
    }

    @Test
    void shouldBeApplyDivisionWithDsl() {
        given()
            .firstNumberEqualTo(10)
            .secondNumberEqualTo(10)
        .when(DIVISION::execute)
        .then()
            .assertResult()
                .isEqualTo(1);
    }

    @Test
    void shouldBeApplyDivisionWithDslException() {
        given()
            .firstNumberEqualTo(10)
            .secondNumberEqualTo(0)
        .when(DIVISION::execute)
        .then()
            .assertException()
                .isInstanceOf(BusinessException.class)
                .messageIsEqualTo("An unexpected error occurred");
    }

    private Dsl given() {
        return new Dsl();
    }

    static class Dsl {
        private BigDecimal firstNumber;
        private BigDecimal secondNumber;

        private Dsl firstNumberEqualTo(final double value) {
            this.firstNumber = BigDecimal.valueOf(value);
            return this;
        }

        private Dsl secondNumberEqualTo(final double value) {
            this.secondNumber = BigDecimal.valueOf(value);
            return this;
        }

        private DslExecutor when(final Function<OperationData, BigDecimal> operation) {
            final var operationData = new OperationData(this.firstNumber, this.secondNumber);
            return new DslExecutor(operation, operationData);
        }

        static class DslExecutor {
            private BigDecimal actualResult;
            private Throwable actualException;

            public DslExecutor(final Function<OperationData, BigDecimal> operation, final OperationData operationData) {
                try {
                    this.actualResult = operation.apply(operationData);
                } catch (BusinessException ex) {
                    this.actualException = ex;
                }
            }

            private DslAsserter then() {
                return new DslAsserter();
            }

            class DslAsserter {

                private DslResultAsserter assertResult() {
                    return new DslResultAsserter();
                }

                class DslResultAsserter {

                    private void isEqualTo(final double expected) {
                        Assertions.assertThat(actualResult)
                            .usingComparator(BigDecimal::compareTo)
                            .isEqualTo(BigDecimal.valueOf(expected));
                    }

                }

                private DslExceptionAsserter assertException() {
                    return new DslExceptionAsserter();
                }

                class DslExceptionAsserter {

                    private DslExceptionAsserter isInstanceOf(final Class<? extends BusinessException> expected) {
                        Assertions.assertThat(actualException).isInstanceOf(expected);
                        return this;
                    }

                    private void messageIsEqualTo(final String expected) {
                        Assertions.assertThat(actualException.getMessage()).isEqualTo(expected);
                    }

                }

            }

        }

    }
}
