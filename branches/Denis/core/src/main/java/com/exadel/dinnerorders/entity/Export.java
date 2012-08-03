package com.exadel.dinnerorders.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: Dima Shulgin
 * Date: 01.08.12
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Export {
    String column() default "column";

    boolean collection() default false;
}
