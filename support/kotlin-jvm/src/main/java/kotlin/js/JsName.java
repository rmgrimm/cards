package kotlin.js;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Facilitate shared code by allowing @kotlin.js.JsName in JVM code
 *
 * Building into the {@link kotlin} package is disallowed in the Kotlin compiler, so this must be done
 * using Java.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface JsName {
    String value();
}
