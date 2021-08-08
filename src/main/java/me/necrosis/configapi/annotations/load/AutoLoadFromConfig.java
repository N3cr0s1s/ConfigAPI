package me.necrosis.configapi.annotations.load;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Automatic find the field value by field name.</p>
 * <p>Field value is in default config file.</p>
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoLoadFromConfig {
}
