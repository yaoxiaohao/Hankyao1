package modelTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



import net.sf.json.JSONObject;

public class Defaultconfig {
	static HttpClient client = new DefaultHttpClient();
	public String defaultcfg(String url) {
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			String BodyResult = EntityUtils.toString(response.getEntity());
//			System.out.println(BodyResult);
			return BodyResult;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	
	}
	
	public static String getBodyString(String gbs) {
		String hsahcode="";
		hsahcode=gbs.substring(gbs.indexOf("formhash"));
		hsahcode=hsahcode.substring(17, 25);
		return hsahcode;
	}
	
	public static void getRegister(String url, String code,String name,String pwd,String email) {
		HttpPost post = new HttpPost(url);
		String strResult = "";
//		--------
		List<NameValuePair> namevaluePairs = new ArrayList<NameValuePair>();
		namevaluePairs.add(new BasicNameValuePair("regsubmit","yes"));
		namevaluePairs.add(new BasicNameValuePair("referer", "http://192.168.186.133/discuz/forum.php"));
		namevaluePairs.add(new BasicNameValuePair("activationauth", ""));
		namevaluePairs.add(new BasicNameValuePair("e5I5FI", name));
		namevaluePairs.add(new BasicNameValuePair("c5hJvz", pwd));
		namevaluePairs.add(new BasicNameValuePair("IY6Pyy", pwd));
		namevaluePairs.add(new BasicNameValuePair("c3JrNR", email));
		namevaluePairs.add(new BasicNameValuePair("formhash", code));
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
//		--------
		try {
			post.setEntity(new UrlEncodedFormEntity(namevaluePairs, "UTF-8"));   //设置post表单数据
			HttpResponse responses = client.execute(post);
			strResult = EntityUtils.toString(responses.getEntity());
			System.out.println(strResult);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
