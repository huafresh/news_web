package user;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import user.entitys.ColumnEntity;
import utils.TextUtils;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析column.xml文件得到栏目配置列表
 *
 * @author hua
 * @version 2017/10/18 19:41
 */
public class ColumnXmlParseHelper {

    private SAXParser saxParser;
    private ColumnHandler handler;

    public ColumnXmlParseHelper() {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParser = saxParserFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从输入流中解析得到栏目列表信息。
     * 输入流会自动关闭。
     *
     * @param inputStream 输入流
     * @return 栏目列表信息
     */
    public List<ColumnEntity> getColumnEntityList(InputStream inputStream) {
        if (saxParser == null || inputStream == null) {
            return null;
        }

        ColumnHandler handler = new ColumnHandler();
        try {
            saxParser.parse(inputStream, handler);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return handler.list;
    }

    private class ColumnHandler extends DefaultHandler {
        List<ColumnEntity> list;

        @Override
        public void startDocument() throws SAXException {
            list = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("item".equals(qName)) {
                ColumnEntity entity = new ColumnEntity();
                entity.setName(attributes.getValue("name"));
                entity.setAdd(attributes.getValue("isAdd").equals("true"));
                entity.setColumn_id(attributes.getValue("columnId"));
                entity.setType(Integer.valueOf(attributes.getValue("type")));
                entity.setChannel(attributes.getValue("channelId"));
                list.add(entity);
            }
        }
    }

}
