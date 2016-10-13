package com.bolesky.base.dagger.component;

import com.bolesky.base.activity.MainActivity;
import com.bolesky.base.dagger.scope.PerActivity;

import dagger.Component;

/**
 * Created by xiaoyong.cui
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface MainActivityComponent {

    MainActivity inject(MainActivity activity);

}
