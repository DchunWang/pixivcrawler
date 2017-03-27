package crawler.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ֱ�Ӹ���������е���Щ������������ҳ��������󣬿������Ƿ��ܹ�����ɹ�
 * 
 * @author Stargazer
 * @date 2017-03-27
 */
public class DownloadOriginalPic {

	private File file;					//�����ص�ͼƬ���ݱ��浽����ļ���
	
	private String originalUrl;		//ԭʼ��ͼ�ĵ�ַ
	
	private String picSuffix = ".jpg";			//�������"."��Ҳ��".jpg"�����ģ�Ĭ����.jpg
	
	public DownloadOriginalPic(){
		
	}
	
	public void setFile(File file){
		this.file = file;
	}

	/**
	 * ����ԭʼͼƬ�ĵ�ַ����ͼƬ���ص���filenameΪ�ļ������ļ���
	 * 
	 * ע��1�����������е�Cookie����Ϣ�������������¼Pվ���ֶ�F12�鿴ĳһ��ԭʼͼƬ�ǵ�������Ϣ��
	 * 			 ������ܻ�ʧЧ��
	 * ע��2����Ϊ��ȡСͼ��ַ��Ϊ���ף����ͨ������ٴ�����ԭʼ��ͼ��ַ������Ҫ��һ����������
	 * 			 	ֱ���ڴ����н�Сͼ��ַת��Ϊ��ͼ��ַ������srcUrl��Сͼ��ַ
	 * 
	 * @param urlStr			��ҳ��Сͼ�ĵ�ַ
	 * @param filename		ͼƬ���ر��浽������ʱ���ļ���
	 */
	public void getPicture(String srcUrl, String filename){
		//ע�⣬����������ʽ��˳���ܵ�����ȻͼƬ��׺������Ĭ�ϵġ�.jpg��
		changeToOriginalUrl(srcUrl);
		newFileForPic(filename);
		
		try {
			URL url = new URL(originalUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			
			conn.addRequestProperty("Accept", "image/webp,image/*,*/*;q=0.8");
			conn.addRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
			conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4,ja;q=0.2");
			conn.addRequestProperty("Connection", "keep-alive");
			conn.addRequestProperty("Cookie", "p_ab_id=1; p_ab_id_2=5; PHPSESSID=22834429_dd1ee52a1c5fe902f87592004b3beb52; device_token=a5e1f2587fe13ad2ba0974ea10b8c4ae; module_orders_mypage=%5B%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; __utma=235335808.300421734.1490616508.1490616508.1490616508.1; __utmc=235335808; __utmz=235335808.1490616508.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=female=1^6=user_id=22834429=1^9=p_ab_id=1=1^10=p_ab_id_2=5=1^12=fanbox_subscribe_button=orange=1^13=fanbox_fixed_otodoke_naiyou=no=1^14=hide_upload_form=no=1^15=machine_translate_test=no=1; _ga=GA1.2.300421734.1490616508");
			conn.addRequestProperty("DNT", "1");
			conn.addRequestProperty("Host", "i3.pixiv.net");
			conn.addRequestProperty("Referer", "http://www.pixiv.net/member_illust.php?mode=medium&illust_id=6193441");
			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			
			
			String contentType = conn.getContentType();
			System.out.println("contentType=" + contentType);
				
			try(InputStream in = new BufferedInputStream(conn.getInputStream())){
				Reader reader = new InputStreamReader(in, "UTF-8");
				
				FileOutputStream fileStream = new FileOutputStream(file);
				
				long length = conn.getContentLength();
				int tmp;
				while(in.available() >=0){
					tmp = in.read();
					fileStream.write(tmp);
					
					long fileLength = file.length();
					if(fileLength >= length){
						break;
					}
				}
			}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException ex){
			System.out.println("�޷�����ͼƬ������ͼƬ��׺�Ƿ���ȷ��");
			ex.printStackTrace();
		}
		
	}
	
	private void newFileForPic(String filename){
		String dir = "F:\\crawlerTest\\pixiv\\phaseone\\";		//��ԭʼͼƬ���������·����
		File testDir = new File(dir);
		if(!testDir.exists()){
			testDir.mkdirs();
		}
		
		String dirFilename = dir+ filename + picSuffix;
		File tmpFile = new File(dirFilename);
		
		//����ļ��Ѿ����ڣ������ļ�����������֣���pixiv.jpg�ͱ�Ϊpixiv1.jpg
		int i = 1;
		while(tmpFile.exists()){
			//int dotIndex = filename.indexOf(".");
			//String prefix = filename.substring(0, dotIndex);
			//String suffix = filename.substring(dotIndex, filename.length());
			String addfix = String.valueOf(i);
			String filenameTmp = filename + addfix + picSuffix;
			dirFilename = dir + filenameTmp;
			tmpFile = new File(dirFilename);
			++i;
		}
		
		this.file = tmpFile;
	}
	
	/**
	 * ������ҳ��ȡ��Сͼ�ĵ�ַת��Ϊԭʼ��ͼ�ĵ�ַ
	 * Ҳ���ǽ�
	 * http://i3.pixiv.net/c/600x600/img-master/img/2016/08/03/11/08/24/58227702_p0_master1200.jpg
	 * ������Сͼ�ĵ�ַת��Ϊԭʼ��ͼ�ĵ�ַ
	 * http://i3.pixiv.net/img-original/img/2016/08/03/11/08/24/58227702_p0.jpg
	 * 
	 * @param srcUrl  Сͼ�ĵ�ַ
	 */
	private void changeToOriginalUrl(String srcUrl){
		System.out.println("Сͼ��ַ��" + "\n" + srcUrl);
		
		//��ȡСͼƬ���ļ�����ȥ����׺֮ǰ��_master1200����ԭʼ��ͼ���ļ���
		String srcFilename = srcUrl.substring(srcUrl.lastIndexOf("/") + 1, srcUrl.length());
		//ͼƬ�ĺ�׺��Ҳ����ԭʼ��ͼ���ļ�����׺������"."�ţ�Ҳ���� .jpg "
		picSuffix = srcFilename.substring(srcFilename.lastIndexOf("."), srcFilename.length());
		//ԭʼ��ͼ���ļ���ǰ׺
		String oriPicPrefix = srcFilename.substring(0, srcFilename.lastIndexOf("_"));
		//ԭʼ��ͼ���ļ���ȫ�� Ҳ����58227702_p0.jpg�� ����
		String originalPicName = oriPicPrefix + picSuffix;
		
		//��ȡ"img/2016/08/03/11/08/24/" ���֣��ⲿ�ֲ���Ҫ�޸�
		String imgdateStr = srcUrl.substring(srcUrl.lastIndexOf("img"), srcUrl.lastIndexOf("/") + 1);
		//��ȡ"/img-original/�� ���֣��ⲿ���ǹ̶���
		String imgOriginal = "/img-original/";
		
		//��ȡhttpͷ�����ǲ���"http://i3.pixiv.net"
		String httpHeader = srcUrl.substring(0, srcUrl.indexOf("net") + 3);
		
		/*
		 * ��Ҫע�⣺���httpͷ����������https://i.pximg.net �����ģ���Ҫת��Ϊ
		 * https://i3.pixiv.net������Ҳ��һ����i3��Ҳ�п�����i1,i2,i3,i4
		 */
		if(httpHeader.matches(".*pximg.net$")){
			httpHeader = httpHeader.substring(0, httpHeader.indexOf("/")+2) + "i2.pixiv.net";	
		}
		
		originalUrl = httpHeader + imgOriginal + imgdateStr + originalPicName;
		System.out.println("ԭʼ��ͼ��ַ��" + "\n" + originalUrl);
	}
	
	public static void main(String[] args){
		DownloadOriginalPic demo = new DownloadOriginalPic();
		String picUrl = "http://i2.pixiv.net/c/600x600/img-master/img/2016/12/12/11/28/16/60345393_p0_master1200.png";
		String filename = "pixiv";
		demo.getPicture(picUrl, filename);
	}
}




	