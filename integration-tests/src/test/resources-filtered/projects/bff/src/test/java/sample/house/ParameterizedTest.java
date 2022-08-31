package sample.house;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import quarkus.io.pact.runtime.MyContextProvider;

import java.lang.annotation.Annotation;
import java.util.Arrays;

@QuarkusTest // This starts the server on port 8081 for convenience in testing
public class ParameterizedTest {

    @BeforeEach
    void before() {
        ClassLoader loader = this.getClass().getClassLoader();
        System.out.println("This tests's loader is " + this.getClass().getClassLoader());
        System.out.println("This tests's annotations is " + Arrays.toString(this.getClass().getAnnotations()));

    }


    @TestTemplate
    @ExtendWith(MyContextProvider.class)
    void verificationTestTemplate(ExtensionContext context) {
        ClassLoader loader = this.getClass().getClassLoader();
        Annotation[] contextAnnotations = context.getRequiredTestClass().getAnnotations();
        Annotation[] myAnnotations = this.getClass().getAnnotations();

        Assertions.assertEquals(myAnnotations.length, contextAnnotations.length, "The test template sees a different version of the class than the test execution" + Arrays.toString(myAnnotations) + " vs " + Arrays.toString(contextAnnotations));
    }

}
