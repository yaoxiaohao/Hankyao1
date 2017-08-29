package cn.discuz.discuztest;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;

public class test1 {
	@Test
	public  void ab() throws IOException, Exception {
		String loginurl = "http://192.168.186.133/discuz/member.php";
	    Cookie[] cookies = {};

	    HttpClient httpClient = new HttpClient() ;
	    
	    String email = "admin";//你的email
	    String psw = "admin";//你的密码
	    // 消息发送的action
	    String url = "http://192.168.186.133/discuz/forum.php";
	    HttpClientParams httparams = new HttpClientParams();
	    httparams.setSoTimeout(30000);
        httpClient.setParams(httparams);	
        
        httpClient.getHostConfiguration().setHost("http://192.168.186.133/discuz/forum.php", 80);
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        PostMethod login = new PostMethod(loginurl);
        login.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        NameValuePair Email = new NameValuePair("username", email);// 邮箱
        NameValuePair password = new NameValuePair("password", psw);// 密码
        NameValuePair quickforward = new NameValuePair("quickforward", "yes");// 密码
        NameValuePair handlekey = new NameValuePair("handlekey", "ls");// 密码
        // NameValuePair code = new NameValuePair( "code"
        // ,"????");//有时候需要验证码，暂时未解决

        NameValuePair[] data = { Email, password, quickforward, handlekey };
        login.setRequestBody(data);

        httpClient.executeMethod(login);
        int statuscode = login.getStatusCode();
        System.out.println(statuscode + "-----------");
        String result = login.getResponseBodyAsString();
        System.out.println(result+"++++++++++++");

        cookies = httpClient.getState().getCookies();
	}
}
