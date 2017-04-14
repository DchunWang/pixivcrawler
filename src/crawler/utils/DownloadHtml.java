package crawler.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * ר������������ҳԴ�����
 * 
 * @author Stargazer
 * @date 2017-04-14
 */
public class DownloadHtml {
	
	public DownloadHtml(){
		
	}
	

	/**
	 * �����ṩ��url������Ӧ����ҳԴ��
	 * 
	 * @param url
	 * @return
	 * @throws IOException ���쳣�׳���㣬�����ȥ����
	 */
	public  String getHtml(String url) throws IOException{
		StringBuilder html = new StringBuilder();
		
		String cookie = "p_ab_id=2; p_ab_id_2=8; __utmt=1; PHPSESSID=22834429_ab70c72ac414ddf046c52b4ac2860982; device_token=8bd24f11934fdc4279bf54243ac09376; login_ever=yes; a_type=0; module_orders_mypage=%5B%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; is_sensei_service_user=1; __utma=235335808.495262193.1492173902.1492173902.1492173902.1; __utmb=235335808.5.10.1492173902; __utmc=235335808; __utmz=235335808.1492173902.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=female=1^6=user_id=22834429=1^9=p_ab_id=2=1^10=p_ab_id_2=8=1; _ga=GA1.2.495262193.1492173902";
		//try{
			URL htmlUrl = new URL(url);
			
			HttpURLConnection conn = (HttpURLConnection)htmlUrl.openConnection();
			
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(300000);
			
			//��������ͷ����
			conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.addRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4,ja;q=0.2");
			conn.addRequestProperty("Connection", "keep-alive");
			conn.addRequestProperty("Cookie", cookie);
			conn.addRequestProperty("DNT", "1");
			conn.addRequestProperty("Host", "www.pixiv.net");
			conn.addRequestProperty("Referer", "http://www.pixiv.net/");
			conn.addRequestProperty("Upgrade-Insecure-Requests", "1");
			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			
			conn.connect();
			
			String contentType = conn.getContentType();
			String contentEncoding = conn.getContentEncoding();
			String responseMessage = conn.getResponseMessage();
			int responseCode = conn.getResponseCode();
			int contentLength = conn.getContentLength();
			
			//����GZIPInputStram��ȡ���ص�gzip��ʽ�����ݣ�������ת��Ϊ�ַ���
			try(InputStream in = conn.getInputStream()){
				GZIPInputStream gzipIn = new GZIPInputStream(in);
				
				byte[] tmpArr = new byte[128];
				int c;
				while((c=gzipIn.read(tmpArr, 0, 128)) != -1){
					html.append(new String(tmpArr, 0, c, "UTF-8"));
				}
				
				return html.toString();
			}
			
			
		/*}catch(MalformedURLException ex){
			System.out.println("url��ʽ��������url�Ƿ���ȷ��");
			ex.printStackTrace();
			
		}catch(IOException e){
			System.out.println("�������Ӵ�����������");
			e.printStackTrace();
		}*/
		
		//return html.toString();
	}
	
	//����
	public static void main(String[] args){
		DownloadHtml tmp = new DownloadHtml();
		///String url = "http://www.pixiv.net/ranking_area.php?type=detail&no=5";		//���չ������а�
		//String url ="http://www.pixiv.net/ranking.php?mode=male";			//�����Ի�ӭ
		//String url = "http://www.pixiv.net/ranking.php?mode=female";		//��Ů�Ի�ӭ
		//String url = "http://www.pixiv.net/ranking.php?mode=male_r18";			//�����Ի�ӭR-18
		//String url = "http://www.pixiv.net/ranking.php?mode=female_r18";		//��Ů�Ի�ӭR-18
		//String url = "http://www.pixiv.net/ranking.php?mode=original";		//ԭ����Ʒ���а�
		//String url = "http://www.pixiv.net/ranking.php?mode=monthly";		//�ۺϱ������а�
		//String url = "http://www.pixiv.net/ranking.php?mode=weekly";			//�ۺϱ������а�
		//String url = "http://www.pixiv.net/ranking.php?mode=daily";				//�ۺϽ������а�
		//String url = "http://www.pixiv.net/ranking.php?mode=daily&date=20170412";	//ǰһ�յĽ������а�
		//String url = "http://www.pixiv.net/ranking.php?mode=monthly&date=20170410";		//20170410�յ��ۺϱ������а�Ҳ��20170312-20170410�����а�
		String url ="http://www.pixiv.net/bookmark_detail.php?illust_id=61246851";		//�ղص�һ��ͼƬ
		
		String html = "";
		try {
			html = tmp.getHtml(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("==========������ҳԴ�����==============");
			e.printStackTrace();
		}
		System.out.println("=================ĳһ���ۺϱ������а�=================");
		System.out.println(html);
		//Map<String, String> picAddr = RegHtml.regRankingPageForPicAddr(html);
	}
}
