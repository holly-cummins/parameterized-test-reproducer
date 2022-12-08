package io.quarkiverse.acme.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.BytecodeTransformerBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import org.jboss.jandex.DotName;

class ExtensionProcessor {

    private static final String FEATURE = "classloaders-are-bad";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }


    @BuildStep
    BytecodeTransformerBuildItem reworkClassLoadingOfParameterizedSourceTest2() {
        // Ideally, we would not hardcode class names, but this is a reproducer so we can shortcut
        DotName simple = DotName.createSimple("sample.house.ParameterizedTest");
        return new BytecodeTransformerBuildItem.Builder()
                .setClassToTransform(simple.toString())
                .setVisitorFunction((ignored, visitor) -> new AnnotationAdjuster(visitor,
                        simple.toString()))
                .build();

    }

    @BuildStep
    BytecodeTransformerBuildItem reworkClassLoadingOfParameterizedSourceQuarkusTest() {
        // Ideally, we would not hardcode class names, but this is a reproducer so we can shortcut
        DotName simple = DotName.createSimple("sample.house.ParameterizedQuarkusTest");
        return new BytecodeTransformerBuildItem.Builder()
                .setClassToTransform(simple.toString())
                .setVisitorFunction((ignored, visitor) -> new AnnotationAdjuster(visitor,
                        simple.toString()))
                .build();
    }

    @BuildStep
    BytecodeTransformerBuildItem reworkClassLoadingOfNormalSourceQuarkusTest() {
        // Ideally, we would not hardcode class names, but this is a reproducer so we can shortcut
        DotName simple = DotName.createSimple("sample.house.NormalQuarkusTest");
        return new BytecodeTransformerBuildItem.Builder()
                .setClassToTransform(simple.toString())
                .setVisitorFunction((ignored, visitor) -> new AnnotationAdjuster(visitor,
                        simple.toString()))
                .build();
    }
}