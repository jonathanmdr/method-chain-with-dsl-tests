package br.com.example.domain;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class CustomCallbackExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        System.out.println("[CUSTOM] Before all tests");
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        System.out.println("[CUSTOM] Before each test");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        System.out.println("[CUSTOM] After each test");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        System.out.println("[CUSTOM] After all tests");
    }

}
