package data;

import data.tasks.ChannelTask;
import data.tasks.NeiHanDuanZi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 2017/8/15.
 * 后台线程，每隔一定时间从各网易服务器 / 新闻门户网站获取新闻数据，并写入数据库
 */
public class BackThread extends Thread {

    private String testUrl = "http://r.inews.qq.com/getQQNewsUnreadList?uid=8fc7d65bda8afd33&omgbizid=64623dde46fb6f40e0781f8c12d00290c0e2005021070b&Cookie=lskey%3D%3Bluin%3D%3Bskey%3D%3Buin%3D%3B%20logintype%3D0%3B%20main_login%3Dqq%3B%20&network_type=wifi&store=118&hw=Xiaomi_MI4LTE&qn-sig=b9e3968053f6f3b6ebebdd6ee8dbcd7f&orig_store=118&user_chlid=news_news_sz%2Cnews_news_gd%2Cnews_news_ent%2Cnews_news_sports%2Cnews_news_nba%2Cnews_news_finance%2Cnews_news_zongyi&activefrom=icon&mac=a0%253A86%253Ac6%253A35%253A20%253A2b&last_time=1502866283&chlid=news_news_top&origin_imei=867080026126221&qqnetwork=wifi&rom_type=MIUI%20V7&real_device_width=2.4&imsi_history=460023134564824&forward=0&page=1&newsTopPage=0&picType=1%2C0%2C0%2C0%2C1%2C0%2C0%2C0%2C1%2C0%2C0%2C0%2C0%2C1%2C0%2C0%2C0%2C0%2C1%2C0&sceneid=&dpi=480&apptype=android&lc_ids=NEW20160505065905ZW&qn-rid=227286f9-90f6-4f3d-8c84-754be96e9c56&devid=867080026126221&screen_width=1080&real_device_height=4.29&appver=19_android_5.3.82&is_chinamobile_oem=0&patchver=5382&last_id=NEW2017081603205100&mid=0de8aba1eec065104b0b8060558658ff11918707&imsi=460023134564824&omgid=13c003137b1da44a973bd4dcdb791f354c84001021070b&isoem=0&screen_height=1920";

    /**
     * 任务队列。
     */
    private List<Runnable> mTaskList;
    private boolean isStop;

    public BackThread() {
        isStop = false;
        initWebUrl();
    }

    private void initWebUrl() {
        mTaskList = new ArrayList<>();

        mTaskList.add(new NeiHanDuanZi()); //内涵段子数据

        mTaskList.add(new ChannelTask("T1348648517839")); //网易娱乐频道
        mTaskList.add(new ChannelTask("T1348649079062")); //网易体育频道
        mTaskList.add(new ChannelTask("T1348648756099")); //网易财经频道
        mTaskList.add(new ChannelTask("T1348648037603")); //网易社会频道
        mTaskList.add(new ChannelTask("T1348649145984")); //网易NBA频道
        mTaskList.add(new ChannelTask("T1348648756099")); //网易历史频道
        mTaskList.add(new ChannelTask("T1467284926140")); //网易要闻频道

    }

    public void stopThread() {
        isStop = true;
    }

    @Override
    public void run() {
        super.run();
        isStop = false;
        while (!isStop) {
            try {
                for (Runnable runnable : mTaskList) {
                    runnable.run();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(30 * 60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
