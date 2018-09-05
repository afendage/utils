import com.utils.HttpClientUtil;
import com.utils.JsonUtil;
import netscape.javascript.JSObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/2/002.
 */
public class UtilTest {

    @Test
    public void jsonTest(){
        String [] a={"1","2","3"};
        Object o1 =a;
        Object obj = new ArrayList();
    }

    @Test
    public void tt(){
        String result = HttpClientUtil.sendPost("http://jxdxfzold.zj.chinamobile.com/zhyw_cx/zhcx_list.aspx",
                "{tb_tele_num:18874180785," +
                        "__VIEWSTATE:ApLJY/8OlOU7pKgoINIpPXzAd+phymBfIK6IMa5Ce2GOkapDPyH8+4y1Tht1BUnEraYgoifka6iNyc6ue0vWAZRQXUbKOV9TQSr+n0LKVSMjvVT8b6tV3ty44hDOZAP7s6GYQi2Z2GorWY19wjKFA1GhwS8bENCkZkXoFFHV8yBKEvzBeXhEu72exB0UqVhdo0WEyQ7oJFfzRp4Nk+UbL4qA+YIxguuz28ptbHdotuf0Peafl1nhmVkrAdJQ4KLk}",
                "utf-8");
        System.out.println(result);
    }
}
