package modelTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;



public class HttpClientGP {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Defaultconfig dc = new Defaultconfig();
		String ac = dc.defaultcfg("http://192.168.186.133/discuz/member.php?mod=register");
//		System.out.println(ac);
		String bc = dc.getBodyString(ac);
		System.out.println(bc);
		dc.getRegister("http://192.168.186.133/discuz/member.php?mod=register&inajax=1", bc, "test001", "123456", "test001@qq.com");
//		Defaultconfig dfc = new Defaultconfig();
*/		try {
//			Defaultconfig dc = new Defaultconfig();
			HttpClientGP gp = new HttpClientGP();
			String ac = gp.defaultcfg("http://192.168.186.133/discuz/member.php?mod=register");
			String bc = gp.getBodyString(ac);
			System.out.println(bc);
			gp.getRegister("http://192.168.186.133/discuz/member.php?mod=register&inajax=1", bc, "test001", "123456", "test001@qq.com");
//			System.out.println(gp.postAPI("http://192.168.186.133/discuz/member.php?mod=register&inajax=1", bc, "test001", "123456", "test001@qq.com"));
//			System.out.println(HttpClientGP.postAPI("http://192.168.186.133/discuz/member.php?mod=register&inajax=1", bc, "test001", "123456", "test001@qq.com"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static HttpClient client = new DefaultHttpClient();
	private static Logger log = Logger.getLogger(HttpClientGP.class);
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
	public static String getAPI(String url) throws Exception {
		JSONObject dataObject=null;
		CloseableHttpClient client = HttpClients.createDefault();	//CloseableHttpClient是抽象类，无法实例化，需要父类引用指向子类实现实例化	
		try {
		HttpGet get = new HttpGet(url);
		log.info("执行API请求" + get.getRequestLine());
		ResponseHandler<String> responsehandler = new ResponseHandler<String>() {
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException{
				int status = response.getStatusLine().getStatusCode();
				if(status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity):null;
				} else{
					log.error("please request Error:" + response.getStatusLine().getStatusCode());
					throw new ClientProtocolException("response Error:" + status);
				}
			}
		};
		
			String responsebody = client.execute(get, responsehandler);
//			 dataObject = JSONObject.parseObject(responsebody.trim());
			 return responsebody;
		} finally {
			client.close();
		}
	}
	
	public static String postAPI(String url,String code,String name,String pwd,String email) throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(url);
			log.info("执行API请求" + post.getRequestLine());

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
			
			post.setEntity(new UrlEncodedFormEntity(namevaluePairs, "UTF-8"));
			
//			String	responsebody = client.execute(post, responsehandler);
//			return responsebody;
			HttpResponse response = client.execute(post);
			
			String result = EntityUtils.toString(response.getEntity());
			return result;
		} finally {
//			client.close();		
		}
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
