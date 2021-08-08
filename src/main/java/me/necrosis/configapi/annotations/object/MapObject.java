package me.necrosis.configapi.annotations.object;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Save object as a yml file
 *
 * <p></p>
 * <p><strong>filePath</strong>     - <i>Config file path</i></p>
 * <p><strong>fileName</strong>     - <i>Config file name in path</i></p>
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface MapObject {
    String filePath() default "";
    String fileName();
}
