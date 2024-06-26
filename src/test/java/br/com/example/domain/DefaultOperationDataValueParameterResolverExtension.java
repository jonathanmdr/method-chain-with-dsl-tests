package br.com.example.domain;

import br.com.example.domain.model.OperationData;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.Optional;

public class DefaultOperationDataValueParameterResolverExtension implements ParameterResolver {

    private static final int DEFAULT_NUMBER_VALUE = 2;

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(DefaultOperationDataValue.class) || parameterContext.getParameter().getType().equals(OperationData.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return defaultOperationData(parameterContext.getParameter());
    }

    private static OperationData defaultOperationData(final Parameter parameter) {
        final var firstNumber = Optional.ofNullable(parameter.getAnnotation(DefaultOperationDataValue.class))
            .map(DefaultOperationDataValue::firstNumber)
            .orElse(DEFAULT_NUMBER_VALUE);
        final var secondNumber = Optional.ofNullable(parameter.getAnnotation(DefaultOperationDataValue.class))
            .map(DefaultOperationDataValue::secondNumber)
            .orElse(DEFAULT_NUMBER_VALUE);

        return OperationData.builder()
            .firstNumber(firstNumber)
            .secondNumber(secondNumber)
            .build();
    }

}
