package asserts;

import org.assertj.core.api.AbstractStandardSoftAssertions;
import org.assertj.core.api.ProxyableObjectAssert;

import java.util.function.Supplier;

public class SoftAssertion extends AbstractStandardSoftAssertions {

    public <V> ProxyableObjectAssert<V> assertThatResultOf(Supplier<V> supplier){
        return assertThat(supplier.get()).as("");
    }

}
