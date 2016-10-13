package com.bolesky.base.api;

import rx.Observable;

/**
 * Created by xiaoyong.cui
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
 */

public class ApiWrapper extends Api {

    public Observable<String> getInfo() {
        return applySchedulers(getService().getData());
    }

    /**
     * 测试函数
     * @return String 字符串：【【xiaoyong.cui->Test】】
     */
    public String getSimple(){
        return "【【xiaoyong.cui->Test】】";
    }
}
