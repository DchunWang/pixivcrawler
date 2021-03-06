package crawler.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crawler.utils.RegHtml;

/**
 * 直接复制浏览器中的那些请求数据来对页面进行请求，看这里是否能够请求成功
 * 
 * @author Stargazer
 * @date 2017-03-27
 */
public class DownloadOriginalPic {

	private File file;					//将下载的图片数据保存到这个文件中
	
	private String originalUrl;		//原始大图的地址
	
	private String picSuffix = ".jpg";			//包括点号"."，也即".jpg"这样的，默认是.jpg
	
	String baseDir = "F:/crawlerTest/pixiv/phase2/";		//将原始图片保存在这个路径下
	
	//用于保存日志，配置文件为log4j2.xml
	private Logger logger = LogManager.getLogger("mylog");
	
	public DownloadOriginalPic(){
		
	}
	
	public void setFile(File file){
		this.file = file;
	}

	/**
	 * 根据原始图片的地址，将图片下载到以filename为文件名的文件中
	 * 
	 * 注意1：这里请求中的Cookie等信息是我在浏览器登录P站并手动F12查看某一张原始图片是的请求信息，
	 * 			 这里可能会失效！
	 * 注意2：因为获取小图地址较为容易，如果通过点击再次请求原始大图地址，则需要多一次请求，所以
	 * 			 	直接在代码中将小图地址转换为大图地址，这里srcUrl是小图地址
	 * 
	 * @param urlStr			网页上小图的地址
	 * @param filename		图片下载保存到磁盘上时的文件名
	 * @throws Exception 
	 */
	private void getPicture(String srcUrl, String filename) throws IOException, IndexOutOfBoundsException{
		//提取图片的ID，将设置为图片保存的文件名后面
		String picId = RegHtml.regPicUrlForPicId(srcUrl);
		filename += picId + "D";
		
		//注意，下面两个方式的顺序不能倒，不然图片后缀名会用默认的“.jpg”
		changeToOriginalUrl(srcUrl);
		newFileForPic(filename);
		
		//System.out.println("=======================图片地址====================");
		//System.out.println(originalUrl);
		//logger.info("==================图片原始地址=====================");
		//logger.info(originalUrl);
		
		
		//try {
			URL url = new URL(originalUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			
			conn.addRequestProperty("Accept", "image/webp,image/*,*/*;q=0.8");
			conn.addRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
			conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4,ja;q=0.2");
			conn.addRequestProperty("Connection", "keep-alive");
			conn.addRequestProperty("Cookie", "p_ab_id=1; p_ab_id_2=5; PHPSESSID=22834429_dd1ee52a1c5fe902f87592004b3beb52; device_token=a5e1f2587fe13ad2ba0974ea10b8c4ae; module_orders_mypage=%5B%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; __utma=235335808.300421734.1490616508.1490616508.1490616508.1; __utmc=235335808; __utmz=235335808.1490616508.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=female=1^6=user_id=22834429=1^9=p_ab_id=1=1^10=p_ab_id_2=5=1^12=fanbox_subscribe_button=orange=1^13=fanbox_fixed_otodoke_naiyou=no=1^14=hide_upload_form=no=1^15=machine_translate_test=no=1; _ga=GA1.2.300421734.1490616508");
			conn.addRequestProperty("DNT", "1");
			conn.addRequestProperty("Host", "i3.pixiv.net");
			conn.addRequestProperty("Referer", "https://www.pixiv.net/member_illust.php?mode=medium&illust_id=6193441");
			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);
			
			conn.connect();
			
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
			
			
		/*} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(FileNotFoundException fileEx){
			System.out.println("无法下载图片，请检查图片后缀是否正确！");
			System.out.println("原始大图地址：");
			System.out.println(originalUrl);
			
			throw new IOException();
		}catch(IOException ex){
			System.out.println("IO出错！");
			ex.printStackTrace();
		}*/
		
	}
	
	private void newFileForPic(String filename){
		String dir = baseDir;
		
		//如果传过来的文件名中有指定文件夹，则将该文件夹合并到dir中
		if(filename.indexOf("/") != -1){
			dir += filename.substring(0, filename.lastIndexOf("/")+1);
		}
		File testDir = new File(dir);
		if(!testDir.exists()){
			testDir.mkdirs();
		}
		
		String dirFilename = baseDir + filename + picSuffix;
		File tmpFile = new File(dirFilename);
		
		//如果文件已经存在，则在文件名后添加数字；如pixiv.jpg就变为pixiv1.jpg
		int i = 1;
		while(tmpFile.exists()){
			//int dotIndex = filename.indexOf(".");
			//String prefix = filename.substring(0, dotIndex);
			//String suffix = filename.substring(dotIndex, filename.length());
			String addfix = String.valueOf(i);
			String filenameTmp = filename + addfix + picSuffix;
			dirFilename = baseDir + filenameTmp;
			tmpFile = new File(dirFilename);
			++i;
		}
		
		/*System.out.println("=======================保存的图片名================");
		System.out.println(dirFilename);*/
		//logger.info("====================最终保存的图片文件名================");
		logger.info("保存到本地的图片文件名：" + dirFilename);
		
		this.file = tmpFile;
	}
	
	/**
	 * 将从网页获取的小图的地址转换为原始大图的地址
	 * 也就是将
	 * http://i3.pixiv.net/c/600x600/img-master/img/2016/08/03/11/08/24/58227702_p0_master1200.jpg
	 * 这样的小图的地址转换为原始大图的地址
	 * http://i3.pixiv.net/img-original/img/2016/08/03/11/08/24/58227702_p0.jpg
	 * 
	 * @param srcUrl  小图的地址
	 */
	private void changeToOriginalUrl(String srcUrl) throws IndexOutOfBoundsException {
		//System.out.println("小图地址：" + "\n" + srcUrl);
		logger.info("原始小图地址= " + srcUrl);
		
		//获取小图片的文件名，去掉后缀之前的_master1200就是原始大图的文件名
		String srcFilename = srcUrl.substring(srcUrl.lastIndexOf("/") + 1, srcUrl.length());
		//图片的后缀，也即是原始大图的文件名后缀，包括"."号，也即” .jpg "
		picSuffix = srcFilename.substring(srcFilename.lastIndexOf("."), srcFilename.length());
		//原始大图的文件名前缀
		String oriPicPrefix = srcFilename.substring(0, srcFilename.lastIndexOf("_"));
		//原始大图的文件名全称 也即“58227702_p0.jpg” 部分
		String originalPicName = oriPicPrefix + picSuffix;
		
		//获取"img/2016/08/03/11/08/24/" 部分，这部分不需要修改
		String imgdateStr = srcUrl.substring(srcUrl.lastIndexOf("img"), srcUrl.lastIndexOf("/") + 1);
		//获取"/img-original/“ 部分，这部分是固定的
		String imgOriginal = "/img-original/";
		
		//获取http头部的那部分"http://i3.pixiv.net"
		String httpHeader = srcUrl.substring(0, srcUrl.indexOf("net") + 3);
		
		/*
		 * 但要注意：如果http头部是类似于https://i.pximg.net 这样的，则要转换为
		 * https://i3.pixiv.net，不过也不一定是i3，也有可能是i1,i2,i3,i4
		 */
		if(httpHeader.matches(".*pximg.net$")){
			httpHeader = httpHeader.substring(0, httpHeader.indexOf("/")+2) + "i2.pixiv.net";	
		}
		
		originalUrl = httpHeader + imgOriginal + imgdateStr + originalPicName;
		//System.out.println("原始大图地址：" + "\n" + originalUrl);
		logger.info("原始大图地址= " + originalUrl);
	}
	
	/**
	 * 下载原始大图
	 * 
	 * 为了处理小图是jpg格式的图片，而原始大图却又是png格式的图片，所以再getPicture()方法外面
	 * 再包一层，已处理找不到jpg格式的原始大图问题；
	 * @param args
	 * @throws Exception 
	 */
	public void download(String picUrl, String filename) throws IOException, IndexOutOfBoundsException{
		try{
			getPicture(picUrl, filename);
		}catch(FileNotFoundException fileEx){
			/*System.out.println("==================================================");
			System.out.println("原小图地址：");
			System.out.println(picUrl);
			System.out.println("小图与大图的格式不匹配，现尝试以png格式请求图片的下载！");*/
			logger.warn("=======================图片格式不匹配==============================");
			logger.warn("原小图地址：");
			logger.warn(picUrl);
			logger.warn("小图与大图的格式不匹配，现尝试以png格式请求图片的下载！");
			picUrl = picUrl.replaceFirst("jpg", "png");
			getPicture(picUrl, filename);
		}
	}
	
	public static void main(String[] args) throws Exception{
		DownloadOriginalPic demo = new DownloadOriginalPic();
		String picUrl = "http://i2.pixiv.net/c/150x150/img-master/img/2017/03/17/00/00/43/61945597_p0_master1200.jpg";
		String filename = "secondPhase";
		
		demo.download(picUrl, filename);
		/*try {
			demo.getPicture(picUrl, filename);
		}catch(FileNotFoundException fileEx){
			System.out.println("原小图地址：");
			System.out.println(picUrl);
			System.out.println("图片不以jpg格式的图片，现尝试以png格式请求图片的下载！");
			picUrl = picUrl.replaceFirst("jpg", "png");
			demo.getPicture(picUrl, filename);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}




	