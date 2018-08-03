package asserts;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class ImprovedAssertions extends Assertions{

    public static <V> ObjectAssert<V> assertThatResultOf(Supplier<V> supplier){
        return Assertions.assertThat(supplier.get()).as("");
    }
}
