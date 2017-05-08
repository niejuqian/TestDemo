package com.testdemo.rxjava;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.adapter.ApplicationAdapter;
import com.testdemo.entity.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 26 09:19
 * @DESC：
 */

public class RxInActionActivity extends BaseAppCompatActivity {
    @BindView(R.id.applications_rv)
    RecyclerView applications_rv;
    ApplicationAdapter adapter;
    @BindView(R.id.state_tv)
    TextView state_tv;

    List<AppInfo> applicationInfos = new ArrayList<>();
    PackageManager packageManager;
    long start = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_rx_in_action);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        applications_rv.setLayoutManager(layoutManager);
        adapter = new ApplicationAdapter(applicationInfos);
        applications_rv.setAdapter(adapter);
        packageManager = getPackageManager();
        initList();
    }

    /**
     * 分组
     */
    @OnClick(R.id.group_btn)
    protected void anyncClick() {
        adapter.isGroup(true);
        Observable.create(new Observable.OnSubscribe<AppInfo>() {
            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {
                if (null != applicationInfos && applicationInfos.size() > 0) {
                    List<AppInfo> appInfos = new ArrayList<AppInfo>();
                    for (AppInfo appInfo : applicationInfos) {
                        appInfos.add(appInfo.clone());
                    }
                    Collections.sort(appInfos, new Compare());
                    for (AppInfo appInfo : appInfos) {
                        subscriber.onNext(appInfo);
                    }
                }
                subscriber.onCompleted();
            }
        })
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<AppInfo>>() {
            @Override
            public void call(List<AppInfo> appInfos) {
                Log.e(TAG, "------------------infos：" + appInfos);
                if (null != appInfos) {
                    applicationInfos.clear();
                    applicationInfos.addAll(appInfos);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick(R.id.no_group_btn)
    public void initList() {
        adapter.isGroup(false);
        //创建一个Observable
        Observable.create(new Observable.OnSubscribe<ApplicationInfo>() {
            @Override
            public void call(Subscriber<? super ApplicationInfo> subscriber) {
                //读取本地安装应用信息
                List<ApplicationInfo> list = getApplicationList();
                if (null != list && list.size() > 0) {
                    for (ApplicationInfo info : list) {
                        subscriber.onNext(info);
                    }
                }
                subscriber.onCompleted();
            }
        })
        .toList()//将分发对象转成list
        //对象转换：将系统对象转换为自定义对象
        .flatMap(new Func1<List<ApplicationInfo>, Observable<ApplicationInfo>>() {
            @Override
            public Observable<ApplicationInfo> call(List<ApplicationInfo> list) {
                return Observable.from(list);
            }
        })
        .map(new Func1<ApplicationInfo, AppInfo>() {
            @Override
            public AppInfo call(ApplicationInfo applicationInfo) {
                AppInfo appInfo = new AppInfo();
                appInfo.setAppIcon(applicationInfo.loadIcon(packageManager));
                appInfo.setAppName((String) applicationInfo.loadLabel(packageManager));
                appInfo.setPackageName(applicationInfo.packageName);
                filterApp(applicationInfo, appInfo);
                return appInfo;
            }
        })
        .subscribeOn(Schedulers.io())//在IO线程中处理业务
        .observeOn(AndroidSchedulers.mainThread())//在主线程回调，刷新页面
        .subscribe(new Subscriber<AppInfo>() {
            @Override
            public void onStart() {
                applicationInfos.clear();
                start = System.currentTimeMillis();
            }

            @Override
            public void onCompleted() {
                Log.e(TAG, "-------------共耗时:" + (System.currentTimeMillis() - start) + "毫秒");
                statCount();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e(TAG, "------------------onError：" + e.getMessage());
            }

            @Override
            public void onNext(AppInfo appInfo) {
                Log.e(TAG, "------------------info：" + appInfo);
                if (null != appInfo) {
                    applicationInfos.add(appInfo);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    private List<ApplicationInfo> getApplicationList() {
        return packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
    }

    /**
     * 判断是否系统程序
     *
     * @param info
     * @return 如果是第三方应用程序则返回true，如果是系统程序则返回false
     */
    public void filterApp(ApplicationInfo info, AppInfo appInfo) {
        boolean flag = false;
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            flag = true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            flag = true;
        }
        if (flag) {
            appInfo.setAppType(1);
            appInfo.setAppTypeName("第三方应用");
        } else {
            appInfo.setAppType(2);
            appInfo.setAppTypeName("系统应用");
        }
    }

    /**
     * 统计数量
     */
    public void statCount() {
        //统计系统应用数量
        /*Observable<List<AppInfo>> sysObservable = Observable.from(applicationInfos).filter(new Func1<AppInfo, Boolean>() {
            @Override
            public Boolean call(AppInfo appInfo) {
                return appInfo.getAppType() == 1;
            }
        }).toList();
        //统计第三方应用数量
        Observable<List<AppInfo>> otherObservable = Observable.from(applicationInfos).filter(new Func1<AppInfo, Boolean>() {
            @Override
            public Boolean call(AppInfo appInfo) {
                return appInfo.getAppType() == 2;
            }
        }).toList();

        Observable.zip(sysObservable, otherObservable, new Func2<List<AppInfo>, List<AppInfo>, StatCount>() {
            @Override
            public StatCount call(List<AppInfo> sysApps, List<AppInfo> otherApps) {
                StatCount statCount = new StatCount();
                statCount.setSysCount(sysApps.size());
                statCount.setOtherCount(otherApps.size());
                return statCount;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<StatCount>() {
            @Override
            public void call(StatCount statCount) {
                Log.e(TAG, "-------stat：" + statCount);
            }
        });*/

        Observable.zip(Observable.from(applicationInfos).filter(new Func1<AppInfo, Boolean>() {
            @Override
            public Boolean call(AppInfo appInfo) {
                return appInfo.getAppType() == 1;
            }
        }).toList(), Observable.from(applicationInfos).filter(new Func1<AppInfo, Boolean>() {
            @Override
            public Boolean call(AppInfo appInfo) {
                return appInfo.getAppType() == 2;
            }
        }).toList(), new Func2<List<AppInfo>, List<AppInfo>, StatCount>() {
            @Override
            public StatCount call(List<AppInfo> appInfo, List<AppInfo> appInfo2) {
                StatCount statCount = new StatCount();
                statCount.setOtherCount(appInfo2.size());
                statCount.setSysCount(appInfo.size());
                return statCount;
            }
        }).subscribe(new Action1<StatCount>() {
            @Override
            public void call(StatCount statCount) {
                Log.e(TAG, "-------stat：" + statCount);
                state_tv.setText(String.format(Locale.CHINA, getString(R.string.state_desc), statCount.getSysCount() + "", statCount.getOtherCount() + ""));
            }
        });
    }

    class Compare implements Comparator<AppInfo> {

        @Override
        public int compare(AppInfo lhs, AppInfo rhs) {
            return lhs.getAppType() - rhs.getAppType();
        }
    }

    class StatCount {
        int sysCount;
        int otherCount;

        public int getSysCount() {
            return sysCount;
        }

        public void setSysCount(int sysCount) {
            this.sysCount = sysCount;
        }

        public int getOtherCount() {
            return otherCount;
        }

        public void setOtherCount(int otherCount) {
            this.otherCount = otherCount;
        }

        @Override
        public String toString() {
            return "StatCount{" +
                    "sysCount=" + sysCount +
                    ", otherCount=" + otherCount +
                    '}';
        }
    }
}
