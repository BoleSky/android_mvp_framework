package com.bolesky.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bolesky.base.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuidePageActivity extends AppCompatActivity {
    DownTimer mDownTimer;

    @Bind(R.id.tv_time)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        ButterKnife.bind(this);
        mTextView.setText(getResources().getString(R.string.jump_ad));
        mDownTimer = new DownTimer(3000, 1000);
        mDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 继承 CountDownTimer 防范
     * <p>
     * 重写 父类的方法 onTick() 、 onFinish()
     */
    class DownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public DownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(GuidePageActivity.this, MainActivity.class));
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }
    }
}
