package de.wps.ddd.kino.common.architecture;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class or interface is an "Application Service", i.e., a service that orchestrates
 * multiple domain services/repositories/factories/etc. to implement a use case.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApplicationService {
}
