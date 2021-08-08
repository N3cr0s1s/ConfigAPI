package me.necrosis.configapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>Comment annotated field in yaml get a comment</p>
 * <p></p>
 * <p><strong>value</strong>     - <i>Comment</i></p>
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {
    public String[] value();
}
