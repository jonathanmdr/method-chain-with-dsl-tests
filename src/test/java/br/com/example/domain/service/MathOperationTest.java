package br.com.example.domain.service;

import br.com.example.domain.exception.BusinessException;
import br.com.example.domain.model.OperationData;
import br.com.example.domain.model.OperationDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Function;

import static br.com.example.domain.service.MathOperation.DIVISION;
import static br.com.example.domain.service.MathOperation.MULTIPLICATION;
import static br.com.example.domain.service.MathOperation.SUBTRACTION;
import static br.com.example.domain.service.MathOperation.SUM;

class MathOperationTest {

    @Test
    void shouldBeApplySum() {
        // Given: Cenário
        OperationData operationData = OperationDataBuilder.builder()
            .firstNumber(BigDecimal.TEN)
            .secondNumber(BigDecimal.TEN)
            .build();

        // When: Ação
        BigDecimal actual = SUM.execute(operationData);

        // Then: Asserção
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
    void shouldBeThrownBusinessExceptionWhenApplyingDivisionByZero() {
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

    class Dsl {
        private BigDecimal firstNumber;
        private BigDecimal secondNumber;

        public Dsl firstNumberEqualTo(final double number) {
            this.firstNumber = BigDecimal.valueOf(number);
            return this;
        }

        public Dsl secondNumberEqualTo(final double number) {
            this.secondNumber = BigDecimal.valueOf(number);
            return this;
        }

        public DslExecutor when(final Function<OperationData, BigDecimal> function) {
            OperationData operationData = new OperationData(this.firstNumber, this.secondNumber);
            return new DslExecutor(function, operationData);
        }

        class DslExecutor {
            private BigDecimal actual;
            private Throwable actualException;

            public DslExecutor(final Function<OperationData, BigDecimal> function, final OperationData operationData) {
                try {
                    this.actual = function.apply(operationData);
                } catch (BusinessException ex) {
                    this.actualException = ex;
                }
            }

            public DslAsserter then() {
                return new DslAsserter();
            }

            class DslAsserter {

                public DslResultAsserter assertResult() {
                    return new DslResultAsserter();
                }

                class DslResultAsserter {

                    public void isEqualTo(final double expected) {
                        Assertions.assertThat(actual)
                            .usingComparator(BigDecimal::compareTo)
                            .isEqualTo(BigDecimal.valueOf(expected));
                    }

                }

                public DslExceptionAsserter assertException() {
                    return new DslExceptionAsserter();
                }

                class DslExceptionAsserter {

                    public DslExceptionAsserter isInstanceOf(final Class<? extends BusinessException> expected) {
                        Assertions.assertThat(actualException).isInstanceOf(expected);
                        return this;
                    }

                    public void messageIsEqualTo(final String expected) {
                        Assertions.assertThat(actualException.getMessage()).isEqualTo(expected);
                    }

                }

            }

        }
    }

}
