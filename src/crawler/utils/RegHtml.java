package crawler.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ��HTMLԴ���������ƥ�䣬��ȡ���е�ͼƬ��ַ��������Ϣ��
 * 
 * @author Stargazer
 * @date 2017-04-14
 */
public class RegHtml {

	public RegHtml(){
		
	}
	
	/**
	 * �Թ������а�ҳ��Դ���������ƥ�䣬��ȡ���е�ǰ100������Ʒ��<br>
	 * ע�⣺��ȡ�Ľ���У���ΪͼƬ�ĵ�ַ������ֵ��Ϊ��ͼƬ������ID<br>
	 * ͼƬ��Сͼ ��ַ�����µı�ǩ��<br>
	 * data-src="https://i.pximg.net/c/150x150/img-master/img/2017/04/08/00/02/29/62303337_p0_master1200.jpg"data-type="illust"data-id="62303337"data-tags="ԭ�� ���ꥸ�ʥ� ���饲 ���ꥸ�ʥ�5000users���"data-user-id="11246082">
	 * 
	 * 
	 * 
	 * @param html
	 * @return
	 */
	public static Map<String, String> regRankingPageForPicAddr(String html){
		Map<String, String> result = new HashMap<>();
		
		//ע�����������ע�͵��ĳ���ƥ����data-tag�а��������ĺ����ģ���ʵ������Ϊdata-tag�����:()�����ķ��ţ������䷶Χ�ܹ㣬
		//��������ֻ��ƥ�䲿��û������������ŵģ�����ʹ��[\\S\\s&&[^>]]+������������ʽ��ƥ�����>�ķ���֮��������ַ���
		//��>��Ϊ��ǩ�������������綨ƥ��Ľ�β
		//String reg = "data\\-src=\"http[\\w\\-\\./:]+\"data\\-type=\"[\\w\\-\"=[\\u0800-\\u9fa5]\\s]+>";
		String reg = "data\\-src=\"http[\\w\\-\\./:]+\"data\\-type=\"[\\S\\s&&[^>]]+>";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(html);
		//System.out.println("=============����ƥ��===========");
		while(matcher.find()){
			//��ȡ�����µ��ַ���
			//data-src="https://i.pximg.net/c/150x150/img-master/img/2017/04/08/00/02/29/62303337_p0_master1200.jpg"data-type="illust"data-id="62303337"data-tags="ԭ�� ���ꥸ�ʥ� ���饲 ���ꥸ�ʥ�5000users���"data-user-id="11246082">
			//������
			//data-src="https://i.pximg.net/c/150x150/img-master/img/2017/04/08/19/25/22/62315243_p0_master1200.jpg"data-type="illust"data-id="62315243"data-tags="��֥饤��! �|�lϣ �k���}�� �Ϥ��Ȥ� ��ľҰ�抪 ??��[�˹� ʸ�ɤˤ� �ǿ��� СȪ��� �@�ﺣδ"data-user-id="11387642">
			String tmp = matcher.group(0);
			//System.out.println(tmp);
			
			//��������ַ��������ַ���ȡ��ֱ��ʹ��String�ķ���
			String dataSrc = tmp.substring(10, tmp.indexOf("data-type")-1);
			String userId = tmp.substring(tmp.lastIndexOf("=")+2, tmp.length()-2);
			
			result.put(dataSrc, userId);
		}
		
		System.out.println("===========�������а��ȡ����Сͼ��ַ��===========");
		for(Map.Entry entry : result.entrySet()){
			System.out.println(entry.getValue() + "==" + entry.getKey());
		}
		System.out.print("�ܹ���ȡ���Ĺ������а��ϵ�ͼƬ�У�");
		System.out.println(result.size());
		
		return result;
	}
	
	
	/**
	 * ����Pվ��ȡ�Ƽ���500��ͼƬ��id����ȡ��Դ���ʽ���£�<br>
	 * {"recommendations":[51176125,49258389,49908880,51645544,...,47881258,56457301,50007025]}<br>
	 * ����ͼƬid��500����Ϊ�������json��ʽ�����Կ��Բ���������ʽ��ֱ���ַ���ƥ�䣨������json����ع����ࣩ<br>
	 * 
	 * @param html
	 * @return
	 */
	public static String[] regRecommenderPageForIds(String html) throws IndexOutOfBoundsException {
		String[] ids;
		String idsStr = html.substring(html.indexOf("[") + 1, html.lastIndexOf("]"));
		ids = idsStr.split(",");
		return ids;
	}
	
	
	/**
	 * ���ղ��Ƽ�ҳ�棬ʹ�ö��ͼƬid��Ϊ�����������ȡ�ö��ͼƬ��Ӧ��Сͼ��ַ<br>
	 * <p>
	 * ����õ���Դ���ʽ���£�<br>
	 * "url":"https:\/\/i.pximg.net\/c\/150x150\/img-master\/img\/2015\/08\/02\/14\/00\/26\/51739759_p0_master1200.jpg","user_name":"\u54b2\u826f\u3086\u304d","illust_id":"51739759","illust_title":"Magician girl","illust_user_id":"1661253","illust_restrict"<br>
	 * ���Կ�������������ʽƥ���������ַ�������ͨ���ַ����Ĳ�������ȡͼƬ��url
	 * 
	 * @param html
	 * @return
	 */
	public static Map<String, String> regTagHtmlForIdAddrs(String html){
		Map<String, String> idAddrs = new HashMap<>();
		
		//System.out.println("=======================����ƥ������ĵ�ַ��===============");
		String reg = "url\":\"http[\\w\\W&&[^\"]]+\",\"user_name\":\"[\\w\\W&&[^\"]]+\",\"illust_id\":\"[\\d]+\",\"illust_title\":\"[\\w\\W&&[^\"]]+\",\"illust_user_id\":\"[\\d]+\"";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(html);
		while(matcher.find()){
			//�õ�������ʾ���ַ���
			//url":"https:\/\/i.pximg.net\/c\/150x150\/img-master\/img\/2015\/04\/19\/16\/29\/41\/49920423_p0_master1200.jpg","user_name":"\u9190\u5473\u5c51","illust_id":"49920423","illust_title":"\u771f\u59eb\u3061\u3083\u3093\u30aa\u30e1\u30b9","illust_user_id":"1815189"
			String uTn = matcher.group(0);
			String url = uTn.substring(uTn.indexOf("http"), uTn.indexOf("1200")+8);
			url = url.replaceAll("\\\\", "");		//�滻��url�е�б��\
			String authorId = uTn.substring(uTn.lastIndexOf(":")+2, uTn.length()-1);
			
			//System.out.println(uTn + "\n authorId=" + authorId + "\n url=" + url);
			idAddrs.put(url, authorId);
		}
		//System.out.println("==================��һ��ƥ���������===================");
		//System.out.println(idAddrs.size());
		
		return idAddrs;
	}
	
	/**
	 * ����ͼƬ��Сͼ��ַ��ȡ��ͼƬ��id<br>
	 * ͼƬ�ĵ�ַ�����¸�ʽ��<br>
	 * http://i3.pixiv.net/c/600x600/img-master/img/2017/01/29/16/20/56/61172838_p0_master1200.jpg<br>
	 * http://i3.pixiv.net/img-original/img/2017/01/29/16/20/56/61172838_p0.jpg
	 * 
	 * 
	 * @param picUrl ͼƬ��Сͼ ��ַ
	 * @return ͼƬ��Ӧ��ID
	 */
	public static String regPicUrlForPicId(String picUrl){
		String picId = "";
		//��Ϊ��ʽ�̶�����Լ򵥣�����ֱ��ʹ��String�ĺ�������ͼƬID����ȡ
		picId = picUrl.substring(picUrl.lastIndexOf("/")+1, picUrl.indexOf("_p"));
		/*String reg  = "";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(picUrl);
		if(matcher.find()){
			picId = matcher.group(0);
		}*/
		
		return picId;
	}
	
}
