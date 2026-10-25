import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidTransactionExceptionTest {

    @Test
    void showsTheRightMessageWhenExceptionIsCreated() {
        var exception = new InvalidTransactionException("Detta är ett felmeddelande");

        assertEquals("Detta är ett felmeddelande", exception.getMessage());
    }
}
