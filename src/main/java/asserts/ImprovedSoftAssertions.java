package asserts;

import org.assertj.core.api.SoftAssertionError;
import org.assertj.core.api.iterable.Extractor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.String.format;
import static org.assertj.core.groups.FieldsOrPropertiesExtractor.extract;

public class ImprovedSoftAssertions extends SoftAssertion {
    private Extractor<Throwable, String> errorDescriptionExtractor = throwable -> {
        Throwable cause = throwable.getCause();
        if (cause == null) {
            return throwable.getMessage();
        }
        // error has a cause, display the cause message and the first stack trace elements.
        StackTraceElement[] stackTraceFirstElements = Arrays.copyOf(cause.getStackTrace(), 5);
        StringBuilder stackTraceDescription = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTraceFirstElements) {
            stackTraceDescription.append(format("\tat %s%n", stackTraceElement));
        }
        return format("%s%n" +
                        "cause message: %s%n" +
                        "cause first five stack trace elements:%n" +
                        "%s",
                throwable.getMessage(),
                cause.getMessage(),
                stackTraceDescription.toString());
    };

    public void assertAll() {
        List<Throwable> errors = errorsCollected();
        if (!errors.isEmpty()) throw new SoftAssertionError(describeErrors(errors));
    }

    private List<String> describeErrors(List<Throwable> errors) {
        return extract(errors, errorDescriptionExtractor);
    }

    public static void assertSoftly(Consumer<SoftAssertion> softly) {
        ImprovedSoftAssertions assertions = new ImprovedSoftAssertions();
        softly.accept(assertions);
        assertions.assertAll();
    }
}
