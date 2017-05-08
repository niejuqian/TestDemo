package com.testdemo.network;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.entity.Person;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 04 09:43
 * @DESC：
 */

public class NetworkActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_network);
    }

    @OnClick(R.id.xml_btn)
    void xml(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(getXmlString());
                subscriber.onCompleted();
            }
        }).compose(new Observable.Transformer<String, List<Person>>() {
            @Override
            public Observable<List<Person>> call(Observable<String> stringObservable) {
                return stringObservable.flatMap(new Func1<String, Observable<List<Person>>>() {
                    @Override
                    public Observable<List<Person>> call(String xmlString) {
                        List<Person> list = parseXmlWithPull(xmlString);
                        return Observable.just(list);
                    }
                });
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<Person>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<Person> persons) {
                Log.e(TAG,"----persons：" + persons);
            }
        });
    }

    /**
     * 使用pull解析xml
     * @param xmlString
     * @return
     */
    private List<Person> parseXmlWithPull(String xmlString) {
        List<Person> persons = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));
            int eventType = parser.getEventType();
            String name = "";
            int age = 0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG : {
                        if ("name".equals(nodeName)){
                            name = parser.nextText();
                        }else if ("age".equals(nodeName)) {
                            age = Integer.valueOf(parser.nextText());
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        persons.add(new Person(name,age));
                        break;
                    }

                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return persons;
    }

    /**
     * 获取xml字符串
     * @return
     */
    private String getXmlString(){
        InputStream in = null;
        BufferedReader bf = null;
        StringBuilder sb = new StringBuilder("");
        try {
            in = getAssets().open("test.xml");
            bf = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != bf) {
                    bf.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }



    @OnClick(R.id.http_btn)
    void network(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(getShopInfo());
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG,"-------------response：" + s);
                    }
                });
    }

    String getShopInfo(){
        StringBuilder sb = new StringBuilder("");
        InputStream is = null;
        BufferedReader br = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://192.168.1.121:8989/legend/interface/public/shop/searchShop?cityCode=440306&orderBy=5&longitude=113.887239&latitude=22.548752");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != br) {
                    br.close();
                }
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (null != connection) {
                connection.disconnect();
            }
        }
        return sb.toString();
    }
}
