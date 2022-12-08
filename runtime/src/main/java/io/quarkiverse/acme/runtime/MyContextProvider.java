package io.quarkiverse.acme.runtime;


import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class MyContextProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }


    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        System.out.println("The classloader used for the context is " + this.getClass().getClassLoader());
        Class testClass = extensionContext.getTestClass().get();
        System.out.println("Test class is " + testClass);
        System.out.println("Test class annotations is " + Arrays.toString(testClass.getAnnotations()));
        System.out.println("Test class classloader is " + testClass.getClassLoader());

        return Stream.of(
                context(new AnnotationCountingTestCase(testClass.getAnnotations(), testClass.getClassLoader())));

    }

    private TestTemplateInvocationContext context(AnnotationCountingTestCase testCase) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return testCase.getDisplayString();
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return asList(
                        new ParameterResolver() {
                            @Override
                            public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
                                return true;
                            }

                            @Override
                            public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
                                return extensionContext;
                            }
                        }
                );
            }


        };
    }
}
