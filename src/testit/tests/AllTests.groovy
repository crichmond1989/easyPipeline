package testit.tests

import testit.tests.JUnitConverterTests
import testit.tests.SuiteRunnerTests
import testit.tests.TestRunnerTests

class AllTests implements Serializable {
    List getSources() {
        return [
            new JUnitConverterTests(),
            new SuiteRunnerTests(),
            new TestRunnerTests()
        ]
    }
}