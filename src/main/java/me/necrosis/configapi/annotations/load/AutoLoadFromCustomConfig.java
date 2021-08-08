package me.necrosis.configapi.annotations.load;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Automatic find the field value by field name.</p>
 * <p>Field value is in custom file.</p>
 *
 * <p></p>
 * <p><strong>filePath</strong>     - <i>Config file path</i></p>
 * <p><strong>fileName</strong>     - <i>Config file name in path</i></p>
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoLoadFromCustomConfig {
    String filePath() default "";
    String fileName();
}
