import data.BackThread;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * tomcat启动时做一些初始化工作
 * Created by hua on 2017/8/15.
 */
public class InitListener implements ServletContextListener{

    private BackThread thread;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        thread.stopThread();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        thread = new BackThread();
        thread.start();
    }

}
