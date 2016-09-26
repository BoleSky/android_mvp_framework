package com.bolesky.base.sportsinfo.dagger.component;

import com.bolesky.base.sportsinfo.activity.MainActivity;
import com.bolesky.base.sportsinfo.dagger.scope.PerActivity;

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
