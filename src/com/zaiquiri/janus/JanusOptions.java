package com.zaiquiri.janus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface JanusOptions {
    String basePackage();
}

