package crawler.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import crawler.vo.Followers;

import java.net.HttpURLConnection;

public class DownloadPageSourceCode {

	//private String url;			//��ҳ�ĵ�ַ���磺http://www.pixiv.net/member_illust.php?mode=medium&illust_id=61367958
	
	private String thumbnailPicUrl;		//Сͼ�ĵ�ַ
	
	private String worksPageHtml;			//��Ա������Ʒҳ���HTMLԴ��
	
	public DownloadPageSourceCode(){
		
	}
	/*public DownloadPageSourceCode(String url){
		this.url = url;
	}*/
	
	/**
	 * �ṩ���ⲿ���ã��Ի�ȡ��ҳ�е�Сͼ��ַ
	 */
	public String getThumbnailPicUrl(String url){
		if(url == null){
			System.out.println("Ϊ�ṩ��ҳ�ĵ�ַ���޷���ȡСͼ��ַ��");
			return "Error";
		}
		String html = getHtml(url);
		return regHtmlForThumbnailPicUrl(html);
	}
	
	/**
	 * ��ȡҳ���Դ���룬�Ա��������
	 * 
	 * @return
	 */
	private String getHtml(String url){
		
		//urlStr = "http://www.pixiv.net/member_illust.php?mode=medium&illust_id=61367958";
		
		StringBuilder html = new StringBuilder();
		
		try{
			URL htmlUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) htmlUrl.openConnection();
			
			/**
			 * ��������ֻ��������������ֶ���¼���ƹ����ģ���ʱû��ʵ�ֳ����ģ���¼��֮����޸� //TODO
			 */
			conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.addRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4,ja;q=0.2");
			conn.addRequestProperty("Connection", "keep-alive");
			conn.addRequestProperty("Cookie", "p_ab_id=3; p_ab_id_2=4; PHPSESSID=22834429_a7bd4b03f1a5409251074572ec5f4b69; device_token=3ff01dea2c65aad9a03dfe01ab07f3f2; a_type=0; is_sensei_service_user=1; login_ever=yes; __utmt=1; module_orders_mypage=%5B%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; _gat_UA-74360115-3=1; __utma=235335808.1102416087.1490788700.1490788700.1490788700.1; __utmb=235335808.7.10.1490788700; __utmc=235335808; __utmz=235335808.1490788700.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=female=1^6=user_id=22834429=1^9=p_ab_id=3=1^10=p_ab_id_2=4=1^12=fanbox_subscribe_button=orange=1^13=fanbox_fixed_otodoke_naiyou=yes=1^14=hide_upload_form=yes=1^15=machine_translate_test=no=1; _ga=GA1.2.1102416087.1490788700");
			conn.addRequestProperty("DNT", "1");
			conn.addRequestProperty("Host", "www.pixiv.net");
			conn.addRequestProperty("Referer", "http://www.pixiv.net/member.php?id=4462245");
			conn.addRequestProperty("Upgrade-Insecure-Request", "1");
			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			//conn.addRequestProperty("mode", "medium");
			//conn.addRequestProperty("illust_id", "61367958");
			conn.setConnectTimeout(30000);
			conn.connect();
			
			String contentType = conn.getContentType();
			String responseMessage = conn.getResponseMessage();
			int responseCode = conn.getResponseCode();
			String contentEncoding = conn.getContentEncoding();
			long contentLength = conn.getContentLength();
			
			//conn.setConnectTimeout(3000);
			
			Object content = conn.getContent();
			
			System.out.println("ContentType : " + contentType);
			System.out.println("ResponseMessage : " + responseMessage);
			System.out.println("ResponseCode : " + responseCode);
			System.out.println("contentEncoding : " + contentEncoding);
			System.out.println("ContentLength : " + contentLength);
			System.out.println("Content:" + content);  
			
			try(InputStream in = conn.getInputStream()){
				//BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				/**
				 * ����ǰ��ĵõ���contentEncoding֪������ȡ����content��gzip��ʽ�ģ������������GZIPInputStream
				 * ���Ի�ȡ����InputStream���н��ܣ�
				 */
				GZIPInputStream gzipInput = new GZIPInputStream(in);
				
				
				byte[] tmp = new byte[128];
				int c;
				while((c=gzipInput.read(tmp, 0, 128)) != -1){
					html.append(new String(tmp, 0, c, "UTF-8"));
				}
				
				//System.out.println("========================HTML========================");
				//System.out.println(html.toString());
			}
			
			//String tmpStr = regHtml(html.toString());
			return html.toString();
			
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		return "Error";

	}
	
	/**
	 * ������õ���ҳ��Դ��HTML����������ƥ�䣬�ҳ�Сͼ�ĵ�ַ��������������Сͼλ����ε�div��
	 * <div class="_layout-thumbnail ui-modal-trigger">
	 * <img src="http://i3.pixiv.net/c/600x600/img-master/img/2017/02/10/00/03/04/61367958_p0_master1200.jpg" alt="Colours">
	 * </div>
	 * 
	 * ���ԣ�����Ҫƥ���"_layout-thumbnail ui-modal-trigger"�����Ȼ���ҳ����е�img��ǩ��
	 * �Ӷ��õ���src����ֵ��Ҳ��Сͼ�ĵ�ַ
	 * 
	 * 
	 * @param args
	 */
	private String regHtmlForThumbnailPicUrl(String sourceCode){
		String result = "";
		
		
		//�Ȱ�Сͼ���ڵ�DIV��ǩƥ�����
		String reg = "class=\"_layout-thumbnail ui-modal-trigger\"><img src=\"[a-zA-Z0-9:/\\.\\-_]*\"";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(sourceCode);
		if(matcher.find()){
			 result = matcher.group(0);
		}
		
		System.out.println("=========================Сͼ��ַDIV============================");
		System.out.println(result);
		
		//�ٰ�Сͼ��ַƥ�����
		String reg2 = "http[\\w:/\\.\\-]+";
		pattern = Pattern.compile(reg2);
		matcher = pattern.matcher(result);
		if(matcher.find()){
			thumbnailPicUrl = matcher.group(0);
		}else{
			thumbnailPicUrl = "Error";
		}
		System.out.println("Сͼ��ַ��");
		System.out.println(thumbnailPicUrl);
		
		return thumbnailPicUrl;
	}
	
	/**
	 * ���ݻ�Ա��id����ȡ��Ա��������Ʒ��Сͼ��ַ
	 * ��http://www.pixiv.net/member_illust.php?id=4462245
	 * ���ҳ���ϱ���id=4462245���Pվ��Ա�ĸ���������Ʒ�������ҳ���ϱ�����Щ��Ʒ�ĵ�ַ
	 * �磺http://i4.pixiv.net/c/150x150/img-master/img/2016/12/25/14/21/03/60547339_p0_master1200.jpg
	 * <del>�磺http://www.pixiv.net/member_illust.php?mode=medium&illust_id=60680252</del>
	 * 
	 * 
	 * @param args
	 * @throws IOException
	 */
	public List<String> getWorksUrlByMemId(String id){
		String memUrlPrefix = "http://www.pixiv.net/member_illust.php?id=";
		String memUrl = memUrlPrefix + id;
		List<String> allWorksUrl = new ArrayList<>();
		
		/*if(worksPageHtml == null || worksPageHtml == ""){
			//��ȡ��ҳ��Դ��
			worksPageHtml = getHtml(memUrl);
		}*/
		//��ȡ��ҳ��Դ��
		worksPageHtml = getHtml(memUrl);
		//��ȡ�ó�Ա��Ʒ��ҳ����Ҳ���ó�Ա�ܹ��ж���ҳ����Ʒ������ÿҳ�ĵ�ַ�ŵ�worksPages��
		List<String> worksPages = regHtmlForWorksPages(worksPageHtml);
		//��ȡ�ó�Ա��һҳ�ϵ�������Ʒ��ַ
		List<String> worksUrl = regHtmlForMemWorksUrl(worksPageHtml);
		//��ȡ�ó�Ա��Pվ�ǳ�
		String pixivName = regHtmlForAuthorName(worksPageHtml);
		
		allWorksUrl.addAll(worksUrl);
		for(String worksPage : worksPages){
			String sourceCodeHtml = getHtml(worksPage);
			worksUrl = regHtmlForMemWorksUrl(sourceCodeHtml);
			allWorksUrl.addAll(worksUrl);
		}
		int worksCounts = allWorksUrl.size();
		System.out.println("==========================================================");
		System.out.println(pixivName + "(id=" + id + ")�ĳ�Ա����" + worksCounts + "����Ʒ��");
		System.out.println("������Ʒ��Сͼ��ַ�ǣ�");
		for(String small : allWorksUrl){
			System.out.println(small);
		}
		
		return allWorksUrl;
	}
	/**
	 * ����Pվ��Ա��Ʒ����ַҳ���Դ�룬��ȡ���ó�Ա��������Ʒ��ַ
	 * ������
	 * ���������֣������Դ���о��ܻ�ȡ��Pվ��Ա�ڸ�Ҳ�е�������Ʒ��Сͼ��ַ
	 * �磺data-src="http://i2.pixiv.net/c/150x150/img-master/img/2017/03/17/00/00/43/61945597_p0_master1200.jpg"
	 * Ҳ������data-src��һ�����У�����ֻҪ��ȡ������data-src���Ե�ֵ��������Сͼ�ĵ�ַ��
	 * 
	 * @param args
	 * @throws IOException
	 */
	private List<String> regHtmlForMemWorksUrl(String memHtml){
		List<String> firstPageUrls = new ArrayList<>();
		//System.out.println("=================��id��Ա��Ʒ����һҳ������Ʒ�ĵ�ַdata-src=====================");
		
		String reg = "data\\-src=\"http[\\w\\.\\-/:]+\"";		//http[\\w:/\\.\\-]+
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(memHtml);
		while(matcher.find()){
			String tmp = matcher.group(0);
			//System.out.println(tmp);
			
			//�ٴӺ���data-src���ַ�������ȡ��Сͼ�ĵ�ַ
			//�磺data-src="http://i1.pixiv.net/c/150x150/img-master/img/2016/06/10/00/12/30/57313892_p0_master1200.jpg"
			//��ȡ����http://i1.pixiv.net/c/150x150/img-master/img/2016/06/10/00/12/30/57313892_p0_master1200.jpg
			String uReg = "http[\\w\\.\\-/:]+";
			Pattern uPattern = Pattern.compile(uReg);
			Matcher uMatcher = uPattern.matcher(tmp);
			String imgUrl = "";
			if(uMatcher.find()){
				imgUrl = uMatcher.group(0);
			}
			firstPageUrls.add(imgUrl);
		}
		/*System.out.println("=======================�����Ӧ�ľ����ַΪ========================");
		for(String url : firstPageUrls){
			System.out.println(url);
		}*/

		return firstPageUrls;
	}
	
	/**
	 * ���ݳ�Ա����Ʒ��ҳ���Դ�룬�ҳ��ó�Ա����Ʒ�ܹ��м�ҳ������ÿҳ�ĵ�ַ����List�з���;
	 * ������ҳ��Դ�룬����ҳ����class="pager-container"��DIV��
	 * 
	 * @param html
	 * @return
	 */
	private List<String> regHtmlForWorksPages(String html){
		System.out.println("===============ƥ�����ҳ��DIVΪ===================");
		ArrayList<String> worksPagesUrl = new ArrayList<>();
		String worksPagePrefix = "http://www.pixiv.net/member_illust.php";
		
		String reg = "class=\"pager\\-container\"[\\w\\s\\?\\.\\-/=\"<>;&]+class=\"next\"";		 //[\\w/<>;\"=\\s\\?&]+class=\"next\"
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(html);
		if(matcher.find()){
			String divStr = matcher.group(0);
			
			System.out.println(divStr);
			
			//����ȡ��DIV�ַ���������ȡ�������ҳ���ĵ�ַ
			String numReg = "\\?id=\\d+&amp;type=all&amp;p=\\d";
			Pattern numPattern = Pattern.compile(numReg);
			Matcher numMatcher = numPattern.matcher(divStr);
			System.out.println("========================�ó�Ա��Ʒ�ĸ�ҳ��ַ(��������һҳ��=====================");
			while(numMatcher.find()){
				String pageUrlSuffix = numMatcher.group(0);
				String pageUrl = worksPagePrefix + pageUrlSuffix;
				System.out.println(pageUrl);
				worksPagesUrl.add(pageUrl);
			}
		}
		
		return worksPagesUrl;
	}
	
	/**
	 * ���ݳ�Ա��ע��ҳ���Դ���ҳ��ó�Ա�ܹ���ע�˶����û�
	 * ��������<span class="count-badge">252</span>��ǩ��
	 * 
	 * @param html
	 * @return
	 */
	private String regFollowersPageForFollowersCounts(String html){
		String counts = "";
		
		String reg = "class=\"count\\-badge\">\\d+";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(html);
		if(matcher.find()){
			String tmp = matcher.group(0);
			reg = "\\d+";
			pattern = Pattern.compile(reg);
			matcher = pattern.matcher(tmp);
			if(matcher.find()){
				counts = matcher.group(0);
			}
		}
		System.out.println("===================================================");
		System.out.println("�ó�Ա�ܹ���ע�� " + counts + " �������û�");
		
		return counts;
	}
	
	/**
	 * ��ȡ��ҳ���иó�Ա����ע�����е��û��İ���id���Ӽ����û�����li��ǩ����
	 * <li><div class="usericon"><a href="member.php?id=465133" class="ui-profile-popup" data-user_id="465133" data-user_name="������"
	 * ��ǩ����Χ�����м������ù�ע�û���ID ,���������û�������alt
	 * 
	 * 
	 * �õ��Ľ����List,ÿ����¼������
	 * <li><div class="usericon"><a href="member.php?id=484261" class="ui-profile-popup" data-user_id="484261" data-user_name="irua"
	 * <li><div class="usericon"><a href="member.php?id=36" class="ui-profile-popup" data-user_id="36" data-user_name="�����"
	 * 
	 * @param html
	 * @return  
	 */
	private List<String> regFollowersPageForFollowersLiTag(String html){
		List<String> followersLiTag = new ArrayList<>();
		
		String tmp;
		//�������������ʽ�У����ƥ������֣�������ġ�Ӣ�������������֣��������Ǹ�����
		//������ʱ�� [\\S&&[^\"]]+ ��ƥ��һ�������ǿհ��ַ������ų������ţ����п��ܻ�Ѻ����ڶ�������޹��ַ���ƥ�����
		//��Ȼ����  �Aʯ���Τȡ����ߣ�����35a��   ���������֡�����
		String reg = "<li><div class=\"usericon\"><a href=\"member\\.php\\?id=\\d+\" class=\"ui\\-profile\\-popup\" data\\-user_id=\"\\d+\" data\\-user_name=\"[\\S\\s&&[^\"]]+\"";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(html);
		//System.out.println("========================��ע��<li>��ǩ===================");
		while(matcher.find()){
			tmp = matcher.group(0);
			//System.out.println(tmp);
			followersLiTag.add(tmp);
			/*
			//�ҳ������û�ID�����ӣ�Ҳ��ƥ��member.php?id=465133
			String tmpReg = "member\\.php\\?id=\\d+";
			Pattern tmpPattern = Pattern.compile(tmpReg);
			Matcher tmpMatcher = tmpPattern.matcher(tmp);
			if(tmpMatcher.find()){
				followersLinkSuffix.add(tmpMatcher.group(0));
			}
			
			//�ҳ���Ӧ�û������ƣ�Ҳ��ƥ���data-user_name="������"
			tmpReg = "data\\-user_name=\"[\\S\\s&&[^\"]]+\"";
			tmpPattern = Pattern.compile(tmpReg);
			tmpMatcher = tmpPattern.matcher(tmp);
			if(tmpMatcher.find()){
				followersName.add(tmpMatcher.group(0));
			}
			*/
		}
		
		/*System.out.println("======================��ע�û������Ӻ�׺======================");
		for(String Link : followersLinkSuffix){
			System.out.println(Link);
		}
		System.out.println("======================��ע�û�������======================");
		for(String name : followersName){
			System.out.println(name);
		}
		*/
		return followersLiTag;
	}
	
	/**
	 * ��<li><div class="usericon"><a href="member.php?id=36" class="ui-profile-popup" data-user_id="36" data-user_name="�����"
	 * ��������ƥ���
	 * data-user_id="484261"
	 * ��ƥ���484261�����ǹ�ע�û���ID
	 * @param html
	 * @return
	 */
	private List<String> regFollowersPageForFollowersId(String html){
		List<String> followersId = new ArrayList<>();
		
		//�ȴ�ÿһ��li��ǩ��ƥ���data-user_id="36"
		String reg = "data\\-user_id=\"\\d+\"";
		Pattern pattern = Pattern.compile(reg);
		
		List<String> liTagList = regFollowersPageForFollowersLiTag(html);
		for(String tag : liTagList){
			//ÿһ��tag��������
			//<li><div class="usericon"><a href="member.php?id=36" class="ui-profile-popup" data-user_id="36" data-user_name="�����"
			//�ı�ǩ
			Matcher matcher = pattern.matcher(tag);
			
			if(matcher.find()){
				String tmp = matcher.group(0);
				//��data-user_id="36"ƥ���36��������ǹ�ע�û���ID
				String tmpReg = "\\d+";
				Pattern tmpPattern = Pattern.compile(tmpReg);
				Matcher tmpMatcher = tmpPattern.matcher(tmp);
				if(tmpMatcher.find()){
					followersId.add(tmpMatcher.group(0));
				}
			}
			
		}

		/*System.out.println("======================��ע�û���ID=======================");
		for(String id : followersId){
			System.out.println(id);
		}*/
		
		
		return followersId;
	}
	private Map<String, String> regFollowersPageForFollowersName(String html){
		Map<String, String> followersName = new HashMap<>();
		
		//�ȴ�ÿһ��li��ǩ��ƥ���data-user_name="�����"��ע���û������ַ����ӣ����п��ܰ����ո������ַ��ȵȣ�
		//�����в����еľ������š������Կ������������ų�������������ƥ��
		String reg = "data\\-user_name=\"[\\S\\s&&[^\"]]+\"";
		Pattern pattern = Pattern.compile(reg);
		
		//�ȴ�ÿһ��li��ǩ��ƥ���data-user_id="36"
		String idreg = "data\\-user_id=\"\\d+\"";
		Pattern idpattern = Pattern.compile(idreg);
		
		List<String> liTagList = regFollowersPageForFollowersLiTag(html);
		for(String tag : liTagList){
			//ÿһ��tag��������
			//<li><div class="usericon"><a href="member.php?id=36" class="ui-profile-popup" data-user_id="36" data-user_name="�����"
			//�ı�ǩ
			Matcher matcher = pattern.matcher(tag);
			
			Matcher idmatcher = idpattern.matcher(tag);
			
			if(matcher.find() && idmatcher.find()){
				String tmp = matcher.group(0);
				//��data-user_name="�����"�ҳ��� ����� ��������ǹ�ע�û�������
				//��Ϊtmp�ĸ�ʽ�ǹ̶���data-user_name="�û���"�����Կ���ֱ�����ַ����ķ��������и�
				String name = tmp.substring(16, tmp.length()-1);
				
				String followersId = idmatcher.group(0);
				followersId = followersId.substring(14, followersId.length()-1);
				
				followersName.put(followersId, name);
			}
			
		}

		/*System.out.println("======================��ע�û�������=======================");
		for(String name : followersName){
			System.out.println(name);
		}	*/	
		
		
		return followersName;
	}
	private Map<String, String> regFollowersPageForFollowersLink(String html){
		Map<String, String> followersLink = new HashMap<>();
		
		
		//�ȴ�ÿһ��li��ǩ��ƥ���member.php?id=36
		String reg = "member\\.php\\?id=\\d+";
		Pattern pattern = Pattern.compile(reg);
		
		//�ȴ�ÿһ��li��ǩ��ƥ���data-user_id="36"
		String idreg = "data\\-user_id=\"\\d+\"";
		Pattern idpattern = Pattern.compile(idreg);
		
		List<String> liTagList = regFollowersPageForFollowersLiTag(html);
		for(String tag : liTagList){
			//ÿһ��tag��������
			//<li><div class="usericon"><a href="member.php?id=36" class="ui-profile-popup" data-user_id="36" data-user_name="�����"
			//�ı�ǩ
			Matcher matcher = pattern.matcher(tag);
			
			Matcher idmatcher = idpattern.matcher(tag);
			
			if(matcher.find()&&idmatcher.find()){
				String suffix = matcher.group(0);
				String link = "http://www.pixiv.net/" + suffix;
				
				String followersId = idmatcher.group(0);
				followersId = followersId.substring(14, followersId.length()-1);
				
				
				followersLink.put(followersId, link);
				
			}
			
		}

		/*System.out.println("======================��ע�û������ӵ�ַ=======================");
		for(String link : followersLink){
			System.out.println(link);
		}*/
		return followersLink;
	}
	
	/**
	 * �ӹ�ע�ߵ�һ��ҳ���ȡ����ҳ������ӣ��Ա�����ȡ���еĹ�ע���û���Ϣ
	 * ���У�����ҳ������������µı�ǩ��
	 * <a href="bookmark.php?type=user&amp;id=27517&amp;rest=show&amp;p=2">2</a>
	 * @param html
	 * @return
	 */
	private List<String> regFollowersPageForPagesLink(String html){
		List<String> links = new ArrayList<>();
		
		String reg = "bookmark\\.php\\?[\\w=;&]+\">\\d</a>";
		
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(html);
		System.out.println("====================�ó�Ա��ע���û�������ҳ�������=======================");
		while(matcher.find()){
			//��ȡ�� bookmark.php?type=user&amp;id=27517&amp;rest=show&amp;p=6">
			String tmp = matcher.group(0);
			//System.out.println(tmp);
			
			String prefix = "http://www.pixiv.net/";
			tmp = tmp.replaceAll("&amp;", "&");
			tmp = tmp.substring(0, tmp.length()-7);
			String link = prefix + tmp;
			
			links.add(link);
		}
		
		for(String l : links){
			System.out.println(l);
		}
		return links;
	}
	
	/**
	 * ���ݻ�ԱId���õ�������ע�������û�id
	 * ��Ա����ע���û���ҳ��������
	 * http://www.pixiv.net/bookmark.php?type=user&id=27517
	 * http://www.pixiv.net/bookmark.php?type=user&id=27517&rest=show&p=2
	 * ��Щҳ���У���Ϊ�˲�ͬҳ
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public Followers getFollowersById(String id){
		Followers followers = new Followers(id);
		
		List<String> followersID = new ArrayList<>();
		Map<String, String> followersName = new HashMap<>();
		Map<String, String> followersLink = new HashMap<>();
		Map<String, String> followersWorksLink = new HashMap<>();
		Map<String, String> followersFavoriteLink = new HashMap<>();
		
		//����ע���û���һҳ
		String followersUrlPrefix = "http://www.pixiv.net/bookmark.php?type=user&id=";
		String followersUrl = followersUrlPrefix + id;
		
		//��ȡ��һҳ��Դ���룬�����ҳ���һҳ���й�ע���û���id���Լ��Ƿ��ж�ҳ�Ĺ�ע�û�
		String firstPage = getHtml(followersUrl);
		
		//��ȡ��ע���û���
		String followersCounts = regFollowersPageForFollowersCounts(firstPage);
		
		//��ȡ����ҳ������
		List<String> moreLinks =  regFollowersPageForPagesLink(firstPage);
		List<String> pagesLinks = new ArrayList<>();
		//�ó�Ա����ע���û����ж���ҳ
		pagesLinks.add(followersUrl);
		pagesLinks.addAll(moreLinks);
		
		for(String pageUrl : pagesLinks){
			String html = getHtml(pageUrl);
			
			//��ȡ��ҳ�����г����Ĺ�ע���û���id
			followersID.addAll(regFollowersPageForFollowersId(html));
			
			//��ȡ��ҳ�����г����Ĺ�ע���û�����
			followersName.putAll(regFollowersPageForFollowersName(html));
			
			//��ȡ��ҵ�����г����Ĺ�ע�û������ӵ�ַ
			followersLink.putAll(regFollowersPageForFollowersLink(html));
		}
		/*
		//��ȡ��ҳ�����г����Ĺ�ע���û���id
		List<String> firstPageFollowersID = regFollowersPageForFollowersId(firstPage);
		
		//��ȡ��ҳ�����г����Ĺ�ע���û�����
		List<String> firstPageFollowersName = regFollowersPageForFollowersName(firstPage);
		
		//��ȡ��ҵ�����г����Ĺ�ע�û������ӵ�ַ
		List<String> firstPageFollowersLink = regFollowersPageForFollowersLink(firstPage);
		*/
		System.out.println("==============================���й�ע�û���ID======================");
		for(String tmp : followersID){
			System.out.println(tmp);
		}
		System.out.println("==============================���й�ע�û�������======================");
		for(Map.Entry tmp : followersName.entrySet()){
			System.out.println(tmp.getKey() + "==" + tmp.getValue());
		}
		System.out.println("==============================���й�ע�û������ӵ�ַ====================");
		for(Map.Entry tmp : followersLink.entrySet()){
			System.out.println(tmp.getKey() + "==" + tmp.getValue());
		}
		
		/*
		 * ��Ϊ�û��ĸ������ϵ�ַ����Ʒ��ַ���ղص�ַ�����ƣ�����ֱ���ڸ������ϵ�ַ����������û���
		 * ��Ʒ��ַ���ղص�ַ
		 * �������ϵ�ַ��http://www.pixiv.net/member.php?id=27517
		 * ��Ʒ��ַ��http://www.pixiv.net/member_illust.php?id=27517
		 * �ղص�ַ��http://www.pixiv.net/bookmark.php?id=27517
		 */
		for(Map.Entry entry : followersLink.entrySet()){
			String followerUserId = (String) entry.getKey();
			String followerLink = (String)entry.getValue();
			String followerWorksLink = followerLink.replaceAll("member", "member_illust");
			String followerFavoriteLink = followerLink.replaceAll("member", "bookmark");
			followersWorksLink.put(followerUserId, followerWorksLink);
			followersFavoriteLink.put(followerUserId, followerFavoriteLink);
		}
		
		if(followersCounts != "" && followersCounts != null){
			
			followers.setFollowers_num(Integer.parseInt(followersCounts));
		}
		followers.setFollowers_id(followersID);
		followers.setFollowers_link(followersLink);
		followers.setFollowers_works_link(followersWorksLink);
		followers.setFollowers_favorite_link(followersFavoriteLink);

		return followers;
	}
	
	/**
	 * �ӳ�Ա��������Ʒҳ���л�ȡ�ó�Ա��Pվ���ǳ�
	 * 
	 * @param worksPageSourceCode �ó�Ա������Ʒҳ���HTML�ַ���
	 * @return
	 */
	private String regHtmlForAuthorName(String html){
		String name = "";
		
		
		String reg = "class=\"user\">[\\s\\S&&[^</h1>]]+</h1>";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(html);
		if(matcher.find()){
			String tmp = matcher.group(0);
			int index = tmp.indexOf(">");
			name = tmp.substring(index+1, tmp.length()-5);
		}
		
		return name;
	}
	
	public String getAuthorNameById(String id){
		String name = "";
		String memUrlPrefix = "http://www.pixiv.net/member_illust.php?id=";
		String memUrl = memUrlPrefix + id;
		
		/*if(worksPageHtml == null || worksPageHtml == ""){
			//�Ȼ�ȡ��id��Ա������Ʒҳ���htmlԴ��
			worksPageHtml = getHtml(memUrl);
		}*/
		//�Ȼ�ȡ��id��Ա������Ʒҳ���htmlԴ��
		worksPageHtml = getHtml(memUrl);
		
		//�ڻ�ȡ��id����Ӧ��Pվ�ǳ�
		name = regHtmlForAuthorName(worksPageHtml);
		
		return name;
	}
	
	/*********************************************************�ղ�ͼƬҳ��**************************************************************/
	
	/**
	 * ��ȡ�ղ�ͼƬҳ�����ܹ��ж��ټ�ͼƬ
	 * ͼƬ�������������µı�ǩ��
	 * <span class="count-badge">516��</span>
	 * 
	 * @param html
	 * @return
	 */
	private String regFavoritePageForPicCounts(String html){
		String pageCounts = "";
		
		String reg = "class=\"count\\-badge\">\\d+";
		
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(html);
		if(matcher.find()){
			String tmp = matcher.group(0);
			pageCounts = tmp.substring(tmp.lastIndexOf(">")+1, tmp.length());
		}
		
		return pageCounts;
	}
	
	/**
	 * ��ȡһ���ղ�ҳ���е�����ͼƬ�����ӵ�ַ�����Ӧ��id
	 * 
	 * ��ʵһ��ҳ��20��ͼƬ���������µı�ǩ�е�
	 * 
	 * class="_layout-thumbnail"><img src="http://source.pixiv.net/www/images/common/transparent.gif"
	 * class="ui-scroll-view"data-filter="thumbnail-filter lazy-image"
	 * data-src="https://i.pximg.net/c/150x150/img-master/img/2016/02/09/20/24/38/55184464_p0_master1200.jpg"
	 * data-type="illust"data-id="55184464"
	 * data-tags="Ů���� ����???�ʥ� �Ʒ� ���`��`�� �Х�󥿥��� �ٺ� �\������ ����� �ٺ�1000users���"
	 * data-user-id="1765103">
	 * 
	 * ע�⣺
	 * 1��һ����Ա�п����ղ�ͬһ���û��Ķ���ͼƬ�����Ծ��Բ������û�id����ΪMap�ļ�����Ӧ����ͼƬ�ĵ�ַ��
	 * 		��ΪMap�ļ�������Ӧ���û�id����Ϊֵ
	 * 
	 * @param favoritePage
	 * @return 
	 */
	private Map<String, String> regFavoritePageForAllPicLink(String favoritePage){
		Map<String, String> allPicLinkFromSinglePage = new HashMap<>();
		/**
		 * data-src="https://i.pximg.net/c/150x150/img-master/img/2016/02/09/20/24/38/55184464_p0_master1200.jpg"data-type="illust"data-id="55184464"data-tags="Ů���� ����???�ʥ� �Ʒ� ���`��`�� �Х�󥿥��� �ٺ� �\������ ����� �ٺ�1000users���"data-user-id="1765103"
		 */
		//������������������ʾ�ı�ǩ����
		//String firstReg = "data\\-src=\"http[\\w\\-\\./:]+\"data\\-type=\"[\\w]+\" data\\-id=\"[\\w]+\"data\\-tags=\"[\\s\\S&&[^\"]+\"data\\-user\\-id=\"[\\d]+\"";
		
		String firstReg = "data\\-src=\"http[\\w\\-\\./:]+\"data\\-type=\"[\\w]+\"data\\-id=\"[\\w]+\"data\\-tags=\"[\\s\\S&&[^\"]]+\"data\\-user\\-id=\"[\\d]+\"";
		Pattern firstPattern = Pattern.compile(firstReg);
		
		Matcher firstMatcher = firstPattern.matcher(favoritePage);
		while(firstMatcher.find()){
			String divStr = firstMatcher.group(0);
			
			String link = "";			//��ͼƬ������
			String id = "";				//��ͼƬ������id
			
			//��ȡdata-src���֣�������ȡ����ͼƬ�����ӵ�ַ
			link = divStr.substring(10, divStr.indexOf("data-type")-1);
			//��ȡdata-user-id���֣�������ȡ����ͼƬ������id
			id = divStr.substring(divStr.lastIndexOf("=")+2, divStr.length()-1);
			allPicLinkFromSinglePage.put(link, id);
		}
		
		for(Map.Entry tmp : allPicLinkFromSinglePage.entrySet()){
			System.out.println("id=" + tmp.getValue() + "==" + tmp.getKey());
		}
		System.out.println("===========================================================");
		
		return allPicLinkFromSinglePage;
	}
	
	/**
	 * ���ݳ�Աid��ѯ���ó�Ա���ղص�����ͼƬ�ĵ�ַ
	 * 
	 * ע�⣬���ص�Map�У�����Сͼ�����ӵ�ַ����ֵ���Ƕ�Ӧ�û���id
	 * 
	 * @param id		��Ա��id
	 * @return 			���ص�Сͼ�ĵ�ַ������DownloadOriginalPic.java������Զ�ת������Ӧ��ԭʼ��ͼ��ַ
	 */
	public Map<String, String> getFavoriteWorksUrlByMemId(String id){
		//��Ա�ղ�ͼƬ��ҳ���ַ
		String prefix_url = "http://www.pixiv.net/bookmark.php?id=";
		String url = prefix_url + id;
		
		//���ݸõ�ַ��ȡ�ղ�ͼƬҳ���Դ��
		String favoritePageHtml = getHtml(url);
		//System.out.println("==========================�ղ�ͼƬҳ��Դ��======================");
		//System.out.println(favoritePageHtml);
		
		//��ȡ��id��Pվ�ǳ�
		
		//����Դ��ҳ�棬��ȡʱ�ղ�ͼƬ������
		String count_badge = regFavoritePageForPicCounts(favoritePageHtml);
		System.out.println("==========================�ղص�ͼƬ����======================");
		System.out.println("id=" + id +"�ĳ�Ա�ܹ��ղ���" + count_badge + "��ͼƬ");
		
		/**
		 * ��ȡ�ղ�ͼƬ��ҳ���ܹ��ж���ҳ��
		 * ����Ҫע�⣬�ղ�ҳ���У�ÿһҳ��ʾ20��ͼƬ��ÿ�����ֻ����ʾ9ҳ������ղس�����180��ͼƬ
		 * �ǵ�9ҳ֮���ҳ����ҳ��Դ�����޷��ó��ģ������������ֱ�����ܵ��ղ�ͼƬ��������ÿҳ��ʾ��ͼƬ����
		 * �����ͻ�ȡ�����ղ�ͼƬ���ܵ�ҳ�����Ӷ�ƴ�յó�ÿҳ����Ӧ���ӵ�ַ
		 */
		int picCounts = Integer.valueOf(count_badge);
		int pageCounts;
		if(picCounts > 20){
			pageCounts = picCounts / 20 +1;
		}else{
			pageCounts = 1;
		}
		
		//�ղ�ͼƬҳ���ÿҳ���Ӷ������¸�ʽ��
		//http://www.pixiv.net/bookmark.php?id=512849&rest=show&p=2
		List<String> pageUrls = new ArrayList<>();
		for(int i = 1; i <= pageCounts; i++){
			pageUrls.add("http://www.pixiv.net/bookmark.php?id=" + id + "&rest=show&p=" + i);
		}
		
		
		//�ӵ�һҳ��ʼ�������һҳ���ֱ��ȡ��Ӧ�ĵ�Դ��,��������ȡ����ÿҳ������ͼƬ���ӵ�ַ
		Map<String, String> allPicUrls = new HashMap<>();
		for(String pageUrl : pageUrls){
			String page = getHtml(pageUrl);
			//System.out.println("���ҳ���ڵ�ͼƬ����Щ��" + pageUrl);
			//��ȡ��ҳ��������ͼƬ�����ӵ�ַ�Լ�ͼƬ��Ӧ��id
			allPicUrls.putAll(regFavoritePageForAllPicLink(page));
		}
		
		
		System.out.println("===========================�����ղص�ͼƬ��ַΪ==================");
		for(Map.Entry entry : allPicUrls.entrySet()){
			System.out.println(entry.getValue() + "==" + entry.getKey());
		}
		
		System.out.println("============================�ղ�ͳ��==========================");
		System.out.println("����" + pageUrls.size() + "ҳ�ղ�");
		System.out.println("���û����ղ���" + picCounts + "��ͼƬ");
		if(allPicUrls.size() != picCounts){
			System.out.println("����ȡ����ͼƬ��ַֻ��" + allPicUrls.size() + "��ͼƬ");
		}
		
		
		return allPicUrls;

		
	}
	
	public static void main(String[] args) throws IOException{
		//String url = "http://www.pixiv.net/member_illust.php?mode=medium&illust_id=59665229";
		//String id = "4462245";		//�����\��
		//String id="27517";				//��ԭ
		//String id = "490219";			//Hiten
		//String id	= "152142";			//������
		String id = "512849";			//����
		//String id = "1584611";			//�������˼�
		//String id = "548883";			//����
		//String id = "4754550";
		DownloadPageSourceCode demo = new DownloadPageSourceCode();
		demo.getFavoriteWorksUrlByMemId(id);
		
		
		
		/*long start = System.currentTimeMillis();
		Date startTime = new Date(start);
		DownloadPageSourceCode demo = new DownloadPageSourceCode();
		
		Followers followers = demo.getFollowersById(id);
		List<String> AllFollowersId = followers.getFollowers_id();
		for(String user_id : AllFollowersId){
			
			//����id��ȡ�ó�Ա��������Ʒ
			List<String> urls = demo.getWorksUrlByMemId(user_id);
			DownloadOriginalPic downloadDemo = new DownloadOriginalPic();
			String filename = "id_" +user_id + "N";
			for(String picUrl : urls){
				try{
					
					downloadDemo.download(picUrl, filename);
				}catch(IOException ex){
					System.out.println("==========================������==========================");
					System.out.println(ex.getMessage());
					ex.printStackTrace();
					continue;
				}
			}
			
		}*/
		
		/*
		//����id��ȡ�ó�Ա��������Ʒ
		List<String> urls = demo.getWorksUrlByMemId(id);
		DownloadOriginalPic downloadDemo = new DownloadOriginalPic();
		String filename = "id_" + id + "N";
		for(String picUrl : urls){
			try{
				
				downloadDemo.download(picUrl, filename);
			}catch(IOException ex){
				System.out.println("==========================������==========================");
				System.out.println(ex.getMessage());
				ex.printStackTrace();
				continue;
			}
		}
		*/
		/*String picUrl = demo.getThumbnailPicUrl(url);
		DownloadOriginalPic downloadDemo = new DownloadOriginalPic();
		String filename = "secondPhase";
		downloadDemo.download(picUrl, filename);*/
		//demo.getHtml();
		
		
		/*long end = System.currentTimeMillis();
		Date endTime = new Date(end);
		long dif = end-start;
		//String difTime = en
		System.out.println("��ʼʱ�䣺" + startTime.toString());
		System.out.println("����ʱ�䣺" + endTime.toString());*/
		//System.out.println("����ʱ�䣺" + difTime.toString());
	}
}
