package sample.house;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import io.quarkiverse.acme.runtime.MyContextProvider;

import java.lang.annotation.Annotation;
import java.util.Arrays;

// No QuarkusTest annotation
public class ParameterizedTest {


    @TestTemplate
    @ExtendWith(MyContextProvider.class)
    void verificationTestTemplate(ExtensionContext context) {
        Annotation[] contextAnnotations = context.getRequiredTestClass().getAnnotations();
        Annotation[] myAnnotations = this.getClass().getAnnotations();

        Assertions.assertEquals(myAnnotations.length, contextAnnotations.length, "The test template sees a different version of the class than the test execution" + Arrays.toString(myAnnotations) + " vs " + Arrays.toString(contextAnnotations));
    }


    @TestTemplate
    @ExtendWith(MyContextProvider.class)
    void classloaderIntrospectionTestTemplate(ExtensionContext context) {
        ClassLoader loader = this.getClass().getClassLoader();
        ClassLoader contextLoader = context.getRequiredTestClass().getClassLoader();

        Assertions.assertEquals(loader, contextLoader, "The test template is using a different classloader to the actual test." );
    }

    @TestTemplate
    @ExtendWith(MyContextProvider.class)
    void contextAnnotationCheckingTestTemplate(ExtensionContext context) {
        Annotation[] contextAnnotations = context.getRequiredTestClass().getAnnotations();
        Assertions.assertTrue(Arrays.toString(contextAnnotations).contains("AnnotationAddedByExtension"), "The JUnit extension context does not see the annotation, only sees " + Arrays.toString(contextAnnotations));
    }

    @TestTemplate
    @ExtendWith(MyContextProvider.class)
    void executionAnnotationCheckingTestTemplate(ExtensionContext context) {
        Annotation[] myAnnotations = this.getClass().getAnnotations();
        Assertions.assertTrue(Arrays.toString(myAnnotations).contains("AnnotationAddedByExtension"), "The test execution does not see the annotation, only sees " + Arrays.toString(myAnnotations));
    }
}
