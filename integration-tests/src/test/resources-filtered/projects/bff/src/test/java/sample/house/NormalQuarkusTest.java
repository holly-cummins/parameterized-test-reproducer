package sample.house;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import io.quarkiverse.acme.runtime.MyContextProvider;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * Sense check - do we see the added annotation without parameterization?
  */
@QuarkusTest
public class NormalQuarkusTest {


    @Test
    void executionAnnotationCheckingTestTemplate() {
        Annotation[] myAnnotations = this.getClass().getAnnotations();
        Assertions.assertTrue(Arrays.toString(myAnnotations).contains("AnnotationAddedByExtension"), "The test execution does not see the annotation, only sees " + Arrays.toString(myAnnotations));
    }
}
