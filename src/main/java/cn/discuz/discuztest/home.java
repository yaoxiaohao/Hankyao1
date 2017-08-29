package cn.discuz.discuztest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class home {
	@Test
	public void home() throws ClientProtocolException, IOException {
		//SetPostMode(client);
		//GetRequestMode(client);
		// client.getConnectionManager().shutdown();
		// getHttpHost(httpclient);
		 demo1();
		// getURL();
		// BasicResponse();
		// outputResponse();
		// iteratorResponse();
		// StringEntity();
		// baiduResponse(httpclient);
		// BufferedHttpEntity(httpclient);
		// ContentProducer();
		// urlencodedFormEntity();
		// StringEntity entity = new StringEntity("姚晓浩", "UTF-8");
		// entity.setChunked(true);
		// HttpPost post = new HttpPost("http://localhost/action.do");
		// post.setEntity(entity);
		// System.out.println(EntityUtils.toString(entity));
	}

	private void SetPostMode(DefaultHttpClient client) throws IOException, ClientProtocolException {
		HttpPost post = new HttpPost("url");
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("p", "1"));
		formparams.add(new BasicNameValuePair("t", "2"));
		formparams.add(new BasicNameValuePair("e", "3"));
		UrlEncodedFormEntity urlentity = 
				new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		post.setEntity(urlentity);
		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		System.out.println("login form get: " + response.getStatusLine() + entity.getContent());
		System.out.println("Post login cookies: ");
		List<Cookie> cookies = client.getCookieStore().getCookies();
		for (int i = 0; i < cookies.size(); i++) {
			System.out.println("- " + cookies.get(i).toString());
		}
		client.getConnectionManager().shutdown();
	}

	private void GetRequestMode(HttpClient client)
			throws IOException, ClientProtocolException, UnsupportedEncodingException {
		HttpGet get = new HttpGet("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13530172130");
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		String i="";
		if (entity != null) {
			System.out.println(entity.getContentEncoding());
			System.out.println(entity.getContentType());
			InputStream instream = entity.getContent();// 读
			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(instream, "GB2312"));//写
//			System.out.println(reader.readLine());//输出
//			System.out.println(EntityUtils.toString(entity));//输出
			i = EntityUtils.toString(entity);

		}
		System.out.println("===========");
//		System.out.println(EntityUtils.toString(entity));//输出
		String jsonString = i;
		String json=jsonString.substring(jsonString.indexOf("=")+1);
		System.out.println(json);
		JSONObject js=JSONObject.parseObject(json);
		System.out.println(js.get("province"));
	}

	private void getHttpHost(HttpClient httpclient) throws IOException, ClientProtocolException {
		HttpContext baiducontext = new BasicHttpContext();
		HttpGet get = new HttpGet("http://www.baidu.com");
		HttpResponse response = httpclient.execute(get, baiducontext);
		HttpHost target = (HttpHost) baiducontext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		System.out.println("Final target: " + target);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			entity.consumeContent();
		}
	}

	private void urlencodedFormEntity() throws UnsupportedEncodingException, IOException {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("param1", "value1"));
		formparams.add(new BasicNameValuePair("param2", "value2"));
		System.out.println(formparams);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		System.out.println(entity);
		HttpPost post = new HttpPost("http://localhost/handler.do");
		post.setEntity(entity);
		System.out.println(EntityUtils.toString(entity, "UTF-8"));
	}

	private void ContentProducer() {
		ContentProducer cp = new ContentProducer() {

			public void writeTo(OutputStream outstream) throws IOException {
				// TODO Auto-generated method stub
				Writer writer = new OutputStreamWriter(outstream, "UTF-8");
				writer.write("<response>");
				writer.write("<content>");
				writer.write(" important stuff");
				writer.write("</content>");
				writer.write("</response>");
				writer.flush();
			}
		};
		HttpEntity entity = new EntityTemplate(cp);
		HttpPost post = new HttpPost("http://localhost/handle.do");
		post.setEntity(entity);
	}

	private void BufferedHttpEntity(HttpClient httpclient) throws IOException, ClientProtocolException {
		HttpGet get = new HttpGet("http://www.baidu.com");
		HttpResponse response = httpclient.execute(get);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			entity = new BufferedHttpEntity(entity);

			System.out.println(EntityUtils.toString(entity, "UTF-8"));
			// String a=EntityUtils.toString(entity);
			// System.out.println(a);
		}
	}

	private void baiduResponse(HttpClient httpclient) throws IOException, ClientProtocolException {
		HttpGet get = new HttpGet("http://www.baidu.com");
		HttpResponse response = httpclient.execute(get);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			long len = entity.getContentLength();
			if (len != -1 && len < 8048) {
				System.out.println(EntityUtils.toString(entity));
			} else {
				System.out.println("get out");
			}
		}
	}

	private void StringEntity() throws IOException {
		StringEntity myentity = new StringEntity("important message", "GBK");
		System.out.println(myentity.getContentType());
		System.out.println(myentity.getContentLength());
		System.out.println(EntityUtils.getContentCharSet(myentity));
		System.out.println(EntityUtils.toString(myentity));
		System.out.println(EntityUtils.toByteArray(myentity).length);
	}

	private void iteratorResponse() {
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
		response.addHeader("Set-Cookie", "c2=b; path=/, c3=c; domain=\"localhost\"");
		HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Set-Cookie"));
		while (it.hasNext()) {
			HeaderElement elem = it.nextElement();
			System.out.println(elem.getName() + " = " + elem.getValue());
			NameValuePair[] params = elem.getParameters();
			for (int i = 0; i < params.length; i++) {
				System.out.println(" " + params[i]);
			}
		}
	}

	private void outputResponse() {
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		response.addHeader("Set-Cookie", "c1=a; path=/; domain=192.168.186.133");
		response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"192.168.186.133\"");
		Header h1 = response.getFirstHeader("Set-Cookie");
		System.out.println(h1);
		Header h2 = response.getLastHeader("Set-Cookie");
		System.out.println(h2);
		Header[] hs = response.getHeaders("Set-Cookie");
		System.out.println(hs.length);
	}

	private void BasicResponse() {
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		System.out.println(response.getProtocolVersion());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		System.out.println(response.getStatusLine().toString());
	}

	private void getURL() {
		CloseableHttpClient httpclient = null;
		HttpGet get = new HttpGet("http://192.168.186.133/discuz");
		System.out.println(get.getURI());
	}

	private void demo1() throws IOException, ClientProtocolException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://www.baidu.com");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int l;
			byte[] tmp = new byte[2048];
			while ((l = instream.read(tmp)) != -1) {
				// System.out.println(tmp.toString());
				out.write(tmp);
			}
			byte[] a = out.toByteArray(); // 将字节转换成为字符
			String param = new String(a, "UTF-8"); // 将字符转换成字符串
			System.out.println(param + " ");
			out.close();
			instream.close();
		}
	}
}
