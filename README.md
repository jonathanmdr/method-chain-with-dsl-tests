# Testes Além do Código
Projeto utilizado na apresentação da palestra "Testes Além Do Código" na trilha Testes no TDC Connections 2022.

## Sobre o Projeto
O projeto consiste em uma pequena calculadora contendo as quatro operações básicas
sendo elas: `soma`, `subtração`, `multiplicação` e `divisão`.

### Exemplo de teste tradicionalmente escrito:
```java
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

### Exemplo de com cenário de falha utilizando DSL + Method Chaining:
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
