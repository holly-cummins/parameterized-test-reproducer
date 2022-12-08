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


    //TODO Ideally, we would not hardcode, but this is a reproducer
    @BuildStep
    BytecodeTransformerBuildItem reworkClassLoadingOfParameterizedSourceTest2() {
        DotName simple = DotName.createSimple("sample.house.ParameterizedTest");
        return new BytecodeTransformerBuildItem.Builder()
                .setClassToTransform(simple.toString())
                .setVisitorFunction((ignored, visitor) -> new AnnotationAdjuster(visitor,
                        simple.toString()))
                .build();

    }


}