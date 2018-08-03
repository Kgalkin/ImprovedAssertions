package aspects;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.assertj.core.api.ProxyableObjectAssert;

public aspect SoftAssertWatcher {
    private ThreadLocal<String> methodName = new ThreadLocal<>();

    pointcut softGetMethodName(): cflowbelow(call( * asserts.SoftAssertion.assertThatResultOf(..)))&&!within(aspects.AssertWatcher)&&cflowbelow(call(* java.util.function.Supplier+.get()))&&execution(* *.get*());

    pointcut setName(String s, Object[] o): call(* *as(..))&&args(s, o)&&within(asserts.SoftAssertion );

    after(): softGetMethodName(){
        methodName.set(thisJoinPoint.getSignature().getDeclaringTypeName() + "." +thisJoinPoint.getSignature().getName());
    }

    @SuppressAjWarnings("uncheckedArgument")
    ProxyableObjectAssert around(String s, Object o): setName(s, o){
        return proceed(methodName.get(), o);
    }
}
