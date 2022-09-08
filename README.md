What’s the reproducer doing? An extension defines some byte code transformations which apply to a test class. 

The test uses a Junit 5 @TestTemplate for tests. At runtime, the version of the test that runs is the version which was transformed by the extension. However, the ContextProvider for the test template is initialised with the non-transformed version of the test class (this is the problem). 

Note that QuarkusDevModeTest may not seem to be enough to show this issue, it may need a proper integration testing running in dev mode. (I had another issue which definitely did not show up in the QuarkusDevModeTest but did with mvn Quarkus:dev.)

```
2022-09-08 21:59:10,425 INFO  [io.qua.test] (Test runner thread) Running 1/0. Running: sample.house.ParameterizedTest#verificationTestTemplate(ExtensionContext)
Test class is class sample.house.ParameterizedTest
Test class annotations is [@io.quarkus.test.junit.QuarkusTest()]
Test class classloader is QuarkusClassLoader:Deployment Class Loader: TEST@7881b3d8
2022-09-08 21:59:10,428 INFO  [io.qua.test] (Test runner thread) Running 1/0. Running: sample.house.ParameterizedTest#[@io.quarkus.test.junit.QuarkusTest()]
This tests's loader is QuarkusClassLoader:Quarkus Runtime ClassLoader: TEST restart no:1@576a1438
This tests's annotations is [@quarkus.io.pact.runtime.AnnotationAddedByExtension(), @io.quarkus.test.junit.QuarkusTest()]
```

To see the failure, 

```  rm -rf integration-tests/src/test/resources-filtered/projects/bff/target && mvn clean  install verify  -DskipTests=false -DskipITs=false
```

To play with the failing test in more detail, 

```
cd integration-tests/src/test/resources-filtered/projects/bff
mvn quarkus:dev
```

`mvn verify` also fails.
