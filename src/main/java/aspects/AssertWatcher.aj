package aspects;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.assertj.core.api.AbstractObjectAssert;

public aspect AssertWatcher {
    private ThreadLocal<String> methodName = new ThreadLocal<>();

    pointcut enteredAssert() : cflowbelow(call(static * asserts.ImprovedAssertions.assertThatResultOf(..)))&&!within(aspects.AssertWatcher);

    pointcut callPoint(): enteredAssert()&&cflowbelow(call(* java.util.function.Supplier+.get()))&&execution(* *.get*());

    pointcut setName(String s, Object[] o): enteredAssert()&&call(* *as(..))&&args(s, o)&&within(asserts.ImprovedAssertions);

    after(): callPoint(){
        System.out.println(thisJoinPoint.getSignature().getName());
        methodName.set(thisJoinPoint.getSignature().getDeclaringTypeName() + "." +thisJoinPoint.getSignature().getName());
    }

    @SuppressAjWarnings("uncheckedArgument")
    AbstractObjectAssert around(String s, Object o): setName(s, o){
        return proceed(methodName.get(), o);
    }


}
