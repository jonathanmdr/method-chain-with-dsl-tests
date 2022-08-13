# Testes Além do Código
Projeto utilizado na apresentação da palestra "Testes Além Do Código" na trilha Testes no TDC Connections 2022.

[![CI](https://github.com/jonathanmdr/method-chain-with-dsl-tests/actions/workflows/ci.yml/badge.svg)](https://github.com/jonathanmdr/method-chain-with-dsl-tests/actions/workflows/ci.yml)
[![CodeFactor](https://www.codefactor.io/repository/github/jonathanmdr/method-chain-with-dsl-tests/badge)](https://www.codefactor.io/repository/github/jonathanmdr/method-chain-with-dsl-tests)

## Sobre o Projeto
O projeto consiste em uma pequena calculadora contendo as quatro operações básicas: `soma`, `subtração`, `multiplicação` e `divisão`.

### Exemplo de teste tradicionalmente escrito:
```java
@Test
void shouldBeApplySum() {
    OperationData operationData = OperationData.builder()
        .firstNumber(10)
        .secondNumber(10)
        .build();

    BigDecimal actual = SUM.execute(operationData);

    Assertions.assertThat(actual)
        .usingComparator(BigDecimal::compareTo)
        .isEqualTo(BigDecimal.valueOf(20));
}
```

### Exemplo de teste utilizando DSL + Method Chaining:
```java
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
```

### Exemplo com cenário de falha utilizando DSL + Method Chaining:
```java
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
```

## Outros projetos com exemplos:
[Projeto: Java + Spring + JUnit + Mockito](https://github.com/jonathanmdr/DynamicDataSourceRouting)
<br>
[Projeto: Java + JUnit + Mockito + Generics](https://github.com/jonathanmdr/Shopping-Cart-TDD)
