package br.com.example.domain.service;

import br.com.example.domain.CustomCallbackExtension;
import br.com.example.domain.DefaultOperationDataValue;
import br.com.example.domain.DefaultOperationDataValueParameterResolverExtension;
import br.com.example.domain.MockSupportTest;
import br.com.example.domain.model.OperationData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static br.com.example.domain.service.MathOperation.DIVISION;
import static br.com.example.domain.service.MathOperation.MULTIPLICATION;
import static br.com.example.domain.service.MathOperation.SUBTRACTION;
import static br.com.example.domain.service.MathOperation.SUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MathOperationUnitTest {

    @Test
    void simpleUnit() {
        final OperationData operationData = OperationData.builder()
            .firstNumber(20)
            .secondNumber(10)
            .build();

        final BigDecimal actual = SUM.execute(operationData);

        assertThat(actual)
            .usingComparator(BigDecimal::compareTo)
            .isEqualTo(BigDecimal.valueOf(30));
    }

    @DisplayName("Subtract numbers")
//    @ParameterizedTest(name = "The subtraction of {0} - {1} should be equal to {2}")
    @ParameterizedTest(name = "Result: {2} | Operation: {0} - {1}")
    @MethodSource("methodSource")
    void methodSource(final double firstNumber, final double secondNumber, final BigDecimal expected) {
        final OperationData operationData = OperationData.builder()
            .firstNumber(firstNumber)
            .secondNumber(secondNumber)
            .build();

        final BigDecimal actual = SUBTRACTION.execute(operationData);

        assertThat(actual)
            .usingComparator(BigDecimal::compareTo)
            .isEqualTo(expected);
    }

    private static Stream<Arguments> methodSource() {
        return Stream.of(
            Arguments.of(10, 10, BigDecimal.ZERO),
            Arguments.of(10, 0, BigDecimal.TEN),
            Arguments.of(0, 10, BigDecimal.TEN.negate()),
            Arguments.of(0, 0, BigDecimal.ZERO)
        );
    }

    @DisplayName("Sum numbers")
    @ParameterizedTest(name = "The sum of {0} + {1} should be equal to {2}")
    @CsvSource({
        "10, 10, 20",
        "10, 0, 10",
        "0, 10, 10",
        "0, 0, 0"
    })
    void csvSource(final double firstNumber, final double secondNumber, final BigDecimal expected) {
        final OperationData operationData = OperationData.builder()
            .firstNumber(firstNumber)
            .secondNumber(secondNumber)
            .build();

        final BigDecimal actual = SUM.execute(operationData);

        assertThat(actual)
            .usingComparator(BigDecimal::compareTo)
            .isEqualTo(expected);
    }

    @Nested
    class NestedClassAnnotationsCallbacks {

        @BeforeAll
        static void beforeAll() {
            System.out.println("Before all tests");
        }

        @BeforeEach
        void beforeEach() {
            System.out.println("Before each test");
        }

        @Test
        void testOne() {
            System.out.println("Test One");
        }

        @Test
        void testTwo() {
            System.out.println("Test Two");
        }

        @AfterEach
        void afterEach() {
            System.out.println("After each test");
        }

        @AfterAll
        static void afterAll() {
            System.out.println("After all tests");
        }

    }

    @Nested
    @ExtendWith(CustomCallbackExtension.class)
    class NestedClassCustomCallbacksExtension {

        @Test
        void testOne() {
            System.out.println("[CUSTOM] Test One");
        }

        @Test
        void testTwo() {
            System.out.println("[CUSTOM] Test Two");
        }

    }

    @Nested
    @ExtendWith(DefaultOperationDataValueParameterResolverExtension.class)
    class NestedClassParameterResolver extends ExtensionByInheritance {

        // firstNumber = 10, secondNumber = 10 (using default values in the annotation parameters)
        @Test
        void customParameterAnnotationWithAnnotation(@DefaultOperationDataValue final OperationData operationData) {
            final BigDecimal actual = SUM.execute(operationData);

            assertThat(actual)
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.valueOf(20));
        }

        // firstNumber = 5, secondNumber = 5 (using the custom values in the annotation parameters)
        @Test
        void customParameterAnnotationWithAnnotationWithValues(@DefaultOperationDataValue(firstNumber = 5, secondNumber = 5) final OperationData operationData) {
            final BigDecimal actual = MULTIPLICATION.execute(operationData);

            assertThat(actual)
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.valueOf(25));
        }

        // firstNumber = 2, secondNumber = 2 (using default values in the extension class)
        @Test
        void customParameterWithoutAnnotation(final OperationData operationData) {
            final BigDecimal actual = DIVISION.execute(operationData);

            assertThat(actual)
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.ONE);
        }

    }

    @Nested
    class ExtensionByInheritance extends MockSupportTest {

        @Mock
        private OperationData operationData;

        @Override
        protected List<Object> mocks() {
            return List.of(this.operationData);
        }

        @BeforeEach
        void beforeEach() {
            System.out.println("Before each test");
        }

        @Test
        void testOne() {
            when(this.operationData.firstNumber()).thenReturn(BigDecimal.TEN);
            when(this.operationData.secondNumber()).thenReturn(BigDecimal.TEN);

            final var actual = SUM.execute(this.operationData);

            assertThat(actual)
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.valueOf(20));
        }

    }

}
