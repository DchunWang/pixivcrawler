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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.net.HttpURLConnection;

public class DownloadPageSourceCode {

	//private String url;			//��ҳ�ĵ�ַ���磺http://www.pixiv.net/member_illust.php?mode=medium&illust_id=61367958
	
	private String thumbnailPicUrl;		//Сͼ�ĵ�ַ
	
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
			conn.connect();
			
			String contentType = conn.getContentType();
			String responseMessage = conn.getResponseMessage();
			int responseCode = conn.getResponseCode();
			String contentEncoding = conn.getContentEncoding();
			long contentLength = conn.getContentLength();
			
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
		
		//��ȡ��ҳ��Դ��
		String memHtml = getHtml(memUrl);
		//��ȡ�ó�Ա��Ʒ��ҳ����Ҳ���ó�Ա�ܹ��ж���ҳ����Ʒ������ÿҳ�ĵ�ַ�ŵ�worksPages��
		List<String> worksPages = regHtmlForWorksPages(memHtml);
		//��ȡ�ó�Ա��һҳ�ϵ�������Ʒ��ַ
		List<String> worksUrl = regHtmlForMemWorksUrl(memHtml);
		
		allWorksUrl.addAll(worksUrl);
		for(String worksPage : worksPages){
			String sourceCodeHtml = getHtml(worksPage);
			worksUrl = regHtmlForMemWorksUrl(sourceCodeHtml);
			allWorksUrl.addAll(worksUrl);
		}
		int worksCounts = allWorksUrl.size();
		System.out.println("==========================================================");
		System.out.println("id=" + id + "�ĳ�Ա����" + worksCounts + "����Ʒ��");
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
	
	public static void main(String[] args) throws IOException{
		//String url = "http://www.pixiv.net/member_illust.php?mode=medium&illust_id=59665229";
		//String id = "4462245";		//�����\��
		String id="27517";				//��ԭ
		
		DownloadPageSourceCode demo = new DownloadPageSourceCode();
		List<String> urls = demo.getWorksUrlByMemId(id);
		DownloadOriginalPic downloadDemo = new DownloadOriginalPic();
		String filename = "id_" + id + "N";
		for(String picUrl : urls){
			downloadDemo.download(picUrl, filename);
		}
		/*String picUrl = demo.getThumbnailPicUrl(url);
		DownloadOriginalPic downloadDemo = new DownloadOriginalPic();
		String filename = "secondPhase";
		downloadDemo.download(picUrl, filename);*/
		//demo.getHtml();
		
	}
}
