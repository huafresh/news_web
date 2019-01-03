package user.entitys;

/**
 * Created by hua on 2017/6/5.
 * 首页一个栏目实体
 */
public class ColumnEntity{

    /**
     * name : 栏目名称
     * isAdd : 是否在首页显示
     * column_id : 栏目唯一标识
     * type : 内容类型：图文、视频、直播
     * channel : 请求内容所需的频道id
     */

    private String name;
    private boolean isAdd;
    private String column_id;
    private int type;
    private String channel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getColumn_id() {
        return column_id;
    }

    public void setColumn_id(String column_id) {
        this.column_id = column_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}
