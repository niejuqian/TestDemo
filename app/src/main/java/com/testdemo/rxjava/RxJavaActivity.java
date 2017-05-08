package com.testdemo.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 18 15:27
 * @DESC：
 */

public class RxJavaActivity extends BaseAppCompatActivity {
    List<String> dataList = new ArrayList<>();
    @BindView(R.id.result_tv)
    TextView result_tv;
    @BindView(R.id.interval_btn)
    Button interval_btn;
    boolean intervalFlag = false;
    Subscriber<Long> subscriber;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_rx_java);
        initData();
    }

    @OnClick(R.id.interval_btn)
    protected void intervalClick() {
        Log.e(TAG,"----------------intervalClick");
        if (intervalFlag) {
            if (subscriber != null && !subscriber.isUnsubscribed()) {
                subscriber.unsubscribe();
                subscriber = null;
            }
            interval_btn.setText("interval定时器");
            count = 0;
            intervalFlag = false;
            return;
        }
        intervalFlag = true;
        subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Long aLong) {
                Log.e(TAG,"----------------d：" + aLong);
                ++count;
                interval_btn.setText("计时器：" +count);
            }
        };
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @OnClick(R.id.timeout_btn)
    protected void timerClick() {
        Observable
                .timer(5,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Long aLong) {
                Log.e(TAG,"----------------d：" + aLong);
                Toast.makeText(RxJavaActivity.this,"定时器执行了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.action_btn)
    protected void action() {
        startActivity(new Intent(this, RxInActionActivity.class));
    }

    @OnClick(R.id.test_btn)
    protected void onClick() {
        result_tv.setText("");
        /*Observable.fromArray(dataList)
                .filter(new Func1<String,Boolean>(){

                    @Override
                    public Boolean call(String s) {
                        return (Integer.parseInt(s.substring(2)) / 2 == 0);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1);*/


        //对数据源进行过滤，筛选出位数能被2整除的值
        /*Observable.from(dataList)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        int d = Integer.parseInt(s.substring(3));
                        double re = (d % 2);
                        //Log.e(TAG,"-----------s：" + s + "，d：" + d + ",re：" + re);
                        return (re == 0);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        int d = Integer.parseInt(s.substring(3));
                        return d > 20;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(5)
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG,"-----result：" + s);
                    result_tv.append(s + " ");
                    }
                });*/
        //遍历集合
        /*Observable.from(dataList)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG,"-----result：" + s);
                        result_tv.append(s + " ");
                    }
                });*/

        /*Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello Word");
                subscriber.onNext("Hello Word1");
                subscriber.onNext("Hello Word2");
                subscriber.onNext("Hello Word3");
                subscriber.onCompleted();
            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"--------------" + s);
                result_tv.setText(s);
            }
        };
        observable.subscribe(subscriber);*/
        //map操作符，用于变换Observable对象
        /*Observable.just("Helo word")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " laonie";
                    }
                })
                .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG,"--------------" + s);
                result_tv.setText(s);
            }
        });*/

        /*Observable.just("Hello Word")
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.just(s);
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });*/
        /*Observable.from(dataList)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        Log.e(TAG,"------s:" + s);
                        List<String> strings = new ArrayList<String>();
                        int d = Integer.parseInt(s.substring(3));
                        double re = (d % 2);
                        if (re == 0) {
                            strings.add(s);
                        }
                        return Observable.from(strings);
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {

                        Log.e(TAG,"------o:" + o);
                    }
                });*/
        /*Observable.from(dataList)
                //过滤集合中，尾数能被2整除的值
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        int d = Integer.parseInt(s.substring(3));
                        double re = (d % 2);
                        return re == 0;
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return "哈哈-" + s;
                    }
                })
                .toList()
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        Log.e(TAG,"-------s1：" + strings);
                    }
                })
                //对集合进行排序
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        Collections.reverse(strings);
                        return Observable.from(strings);
                    }
                })
                .toList()
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> s) {
                        Log.e(TAG,"-------s2：" + s);
                    }
                });*/

        Observable.just("Hello word")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        String d = null;
                        if (d.equals(s)) {
                            return null;
                        }
                        return s;
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted-----");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError------e：" + e);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "onNext------s：" + s);
                    }
                });

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });
    }

    public void initData() {
        for (int i = 0; i < 100; i++) {
            dataList.add("ddd" + i);
        }
    }
}
