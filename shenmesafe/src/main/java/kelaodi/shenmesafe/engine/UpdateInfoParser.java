package kelaodi.shenmesafe.engine;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;

import kelaodi.shenmesafe.domain.UpdateInfo;

/**
 * Created by Administrator on 2015/4/17.
 */
public class UpdateInfoParser {
    /**
     * 解析服务器返回的更新信息
     *
     * @param is 服务器返回的流
     * @return 如果发生异常 返回null
     */

    public static UpdateInfo getUpdateInfo(InputStream is) {
        try {
            XmlPullParser pullParser = Xml.newPullParser();
            pullParser.setInput(is, "UTF-8");
            int type = pullParser.getEventType();
            UpdateInfo info = null;
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("info".equals(pullParser.getName())) {
                            info = new UpdateInfo();
                        } else if ("version".equals(pullParser.getName())) {
                            info.setVersion(pullParser.nextText());
                        } else if ("description".equals(pullParser.getName())) {
                            info.setDescription(pullParser.nextText());
                        } else if ("apkurl".equals(pullParser.getName())) {
                            info.setApkurl(pullParser.nextText());
                        }
                        break;
                }
                type = pullParser.next();
            }
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
