package data.recommon;

import net.sf.json.JSONObject;
import network.IResponseListener;
import network.NetworkRequest;
import data.entitys.NormalNewsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 2017/8/17.
 * 请求网易头条页面的刷新接口，解析出新闻，然后存入数据库中
 *
 *
 */
public class HeadLinesTask implements Runnable {

    private static final String url = "https://c.m.163.com/recommend/getSubDocPic?tid=T1348647909107&from=toutiao&offset=0&size=10&fn=2&LastStdTime=0&prog=LMA1&passport=6ras%2B7eG1rU49VTGVUoaOSjDTS7evK17e33OxkeE9uM%3D&devId=JRx6OIZqM%2F23C3BSim%2FTFA%3D%3D&lat=&lon=&version=25.1&net=wifi&ts=1502957735&sign=11KrAcihCWVfrSRZWb4ylcy88TQkq5hTrkEQsNQYdJ148ErR02zJ6%2FKXOnxX046I&encryption=1&canal=netease_gw&mac=1TjHUFyrqgi7m8wLFqOZ6X9UZbg%2FOBGwvHnogEfb82I%3D&open=&openpath=";

    @Override
    public void run() {
        NetworkRequest.with()
                .url(url)
                .responseListener(new IResponseListener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        HeadLinesBean bean = (HeadLinesBean) JSONObject.toBean(result,HeadLinesBean.class);

                        //存放解析出来的一般类型新闻
                        List<NormalNewsEntity> infoList = new ArrayList<>();

                        List<HeadLinesBean.T1348647909107Bean> list = bean.getT1348647909107();
                        for (HeadLinesBean.T1348647909107Bean t1348647909107Bean : list) {

                        }


                    }

                    @Override
                    public void onError(Exception e) {

                    }
                })
                .request();

    }
}
