package me.necrosis.configapi.annotations.load;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Load field value from default config with custom path.
 *
 * <p></p>
 * <p><strong>filePath</strong>     - <i>Field path in default Config file</i></p>
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LoadFromConfig {
    public String fieldPath();
}
