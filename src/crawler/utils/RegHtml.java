package crawler.utils;

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
		System.out.println(result.size());
		for(Map.Entry entry : result.entrySet()){
			System.out.println(entry.getValue() + "==" + entry.getKey());
		}
		System.out.print("�ܹ���ȡ���Ĺ������а��ϵ�ͼƬ�У�");
		System.out.println(result.size());
		
		return result;
	}
	
}
