package data.resolvers;

/**
 * Created by hua on 2017/8/15.
 * json解析器接口
 */
public interface IObtainData<T> {

    //解析json文档获取新闻
    T obtain(String json);

}
