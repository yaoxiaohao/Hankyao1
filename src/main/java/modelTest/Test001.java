package modelTest;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class Test001 {

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
}
