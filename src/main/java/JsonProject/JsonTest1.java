package JsonProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import net.sf.json.JSONObject;



public class JsonTest1 {  
	  
    /** 
     * @param args 
     */  
	@Test
    public void hehe() {  
		//1.设置默认参数 
        String uid="12345678";  
        String title="test";  
        String content="test a content";
        //2.将默认参数传递给sendSms方法
        String ret=sendSms(uid ,title,content);  
        System.out.println(ret);  
  
        if(ret.indexOf("失败")<0)  
        {  
            System.out.println("成功发送sms");  
        }  
        else  
        {  
            System.out.println("失败发送");  
        }  
  
    }  
      
      
	//sendSms方法
    public static String sendSms(String uid,String title,String content){ 
    	//实例化HttpClient
    	HttpClient httpclient = new DefaultHttpClient() ;  
    	//设置发送地址值
        String smsUrl="http://192.168.0.1/service/sendsms"; 
        //将发送地址值保存在HttpPost方法中，并实例化HttpPost
        HttpPost httppost = new HttpPost(smsUrl);  
        String strResult = "";  
        //配置post表单数据  
        try {  
              	//实例化arraylist -->nameValuePairs，数据类型为NameValuePair
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
                //实例化JSONObject -->jobj
                JSONObject jobj = new JSONObject();  
                //在jobj中存入name和value,
                jobj.put("uid", uid);  
                jobj.put("title", title);  
                jobj.put("content",content);  
                  
                //将jobj所有value的内容以字符串的形式增加到nameValuePairs中  
                nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));
                //配置http header头部基本信息
                httppost.addHeader("Content-type", "application/x-www-form-urlencoded");  
                //将nameValuePairs中的value值转码为“UTF-8”格式保存到httppost的entity中
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));  
                //执行请求
                HttpResponse response = httpclient.execute(httppost);  
                if (response.getStatusLine().getStatusCode() == 200) {  
                    /*读返回数据*/  
                    String conResult = EntityUtils.toString(response  
                            .getEntity());  
                    JSONObject sobj = new JSONObject();  
                    sobj = sobj.fromObject(conResult);  
                    String result = sobj.getString("result");  
                    String code = sobj.getString("code");  
                    if(result.equals("1")){  
                        strResult += "发送成功";  
                    }else{  
                        strResult += "发送失败，"+code;  
                    }  
                      
                } else {  
                    String err = response.getStatusLine().getStatusCode()+"";  
                    strResult += "发送失败:"+err;  
                }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return strResult;  
    }  
  
      
    private static String getStringFromJson(JSONObject adata) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("{");  
        for(Object key:adata.keySet()){  
            sb.append("\""+key+"\":\""+adata.get(key)+"\",");  
        }  
        String rtn = sb.toString().substring(0, sb.toString().length()-1)+"}";  
        return rtn;  
    }  
}  

