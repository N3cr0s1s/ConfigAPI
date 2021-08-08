package me.necrosis.configapi.annotations.load;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Load field value from custom config with custom path.
 *
 * <p></p>
 * <p><strong>filePath</strong>     - <i>Config file path</i></p>
 * <p><strong>fileName</strong>     - <i>Config file name in path</i></p>
 * <p><strong>filePath</strong>     - <i>Field path in Yml Config file</i></p>
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LoadFromCustomConfig {
    public String filePath() default "";
    public String fileName();
    public String fieldPath();
}
