package cn.discuz.discuztest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import net.sf.json.JSONObject;



public class RegisterDiscuz {
	@Test
	public void open() {
		String name = "admin";
		String password = "admin";
		String url = "http://192.168.186.133/discuz/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			List<NameValuePair> namevaluePairs = new ArrayList<NameValuePair>();
			/*JSONObject jobo = new JSONObject();
			jobo.put("username", name);
			jobo.put("password", password);
			
			namevaluePairs.add(new BasicNameValuePair("username", getStringFromJson(jobo)));*/
			namevaluePairs.add(new BasicNameValuePair("username",name));
			namevaluePairs.add(new BasicNameValuePair("password", password));
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setEntity(new UrlEncodedFormEntity(namevaluePairs, "UTF-8"));
			HttpResponse response = client.execute(post);
			String conResult = EntityUtils.toString(response.getEntity());
			System.out.println(conResult);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	/*private static String getStringFromJson(JSONObject adata) {
		StringBuffer sb = new StringBuffer();
//		sb.append("{");
		for (Object key:adata.keySet()) {
				sb.append( key+ "=" +adata.get(key)+ "&");
		}
		
		String rtn = sb.toString().substring(0,sb.toString().length()-1);
		return rtn;
	}*/
	
	
	
}
