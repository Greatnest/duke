import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DeadlineTest {
    @Test
    public void TestToString() {
        LocalDateTime testTime = LocalDateTime.of(2017, 2, 13, 15, 56);
        Deadline newDeadline = new Deadline("To Complete Test", testTime);
        Assertions.assertEquals("[D][NOT DONE] To Complete Test (by: 13/2/2017 1556)", newDeadline.toString());

        newDeadline.markAsDone();
        Assertions.assertEquals("[D][DONE] To Complete Test (by: 13/2/2017 1556)", newDeadline.toString());
    }
}