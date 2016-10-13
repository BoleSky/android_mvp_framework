package com.bolesky.base.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by xiaoyong.cui
 * on 2016/9/24
 * E-Mail:hellocui@aliyun.com
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
