import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

// Specify the test classes to include in the suite
@Suite
@SelectClasses({
    BookManager_Test.class,
    Book_Test.class,
    Loan_Test.class,
    LoanManager_Test.class,
    PayingFee_Test.class
})
public class TestRunner {
    // This class remains empty; its purpose is only to hold the annotations
}
