package crawler.customize;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crawler.core.DownloadOriginalPic;
import crawler.core.DownloadPageSourceCode;
import crawler.utils.DownloadHtml;
import crawler.utils.GetRecommend;
import crawler.utils.RegHtml;
import crawler.utils.TimeUtil;
import crawler.vo.Followers;

public class DownloadByDifferentCond {
	
	//�����־֧��
	private Logger logger = LogManager.getLogger("mylog");
	
	/**
	 * ֻ���ظ�id��Ա��������Ʒ
	 * 
	 * @param id
	 * @throws Exception 
	 */
	public void downloadAllWorksByMemId(String id){
		Logger logger = LogManager.getLogger("mylog");
		
		
		//long startTime = System.currentTimeMillis();
		DownloadPageSourceCode demo = new DownloadPageSourceCode();
		
		String authorName = demo.getAuthorNameById(id);
		System.out.println("�ó�Ա��Pվ�ǳ�Ϊ��" + authorName);
		logger.info("�ó�Ա��Pվ�ǳ�Ϊ��" + authorName);
		//�滻�������в������ڽ����ļ������������
		//authorName = authorName.re
		
		List<String> allUrls = demo.getWorksUrlByMemId(id);
		
		DownloadOriginalPic download = new DownloadOriginalPic();
		
		String filename =  id + "Works/" + id + "N";
		logger.info("δ��ͼƬid֮ǰ�ı����ļ���Ϊ��" + filename);
		for(String url : allUrls){
			logger.info("��ʼ����ͼƬ��" + url);
			try{
				//System.out.println("**************************************************������ʾ***************************************");
				//System.out.println("ͼƬ��ַ��" + url);
				download.download(url, filename);
				logger.info("=============ͼƬ���سɹ�============");
				logger.info("=============��ʼͼƬ����============");
			}catch(IndexOutOfBoundsException e){
				//System.out.println("=========================������============================");
				//System.out.println("�����ͼƬΪ��" + url);
				logger.error("===========================ͼƬ���س���=========================");
				logger.error("���س����ͼƬ��ַΪ��" + url);
				logger.error(e.getMessage());
				e.printStackTrace();
				continue;
			}catch(IOException ex){
				System.out.println("=========================������============================");
				System.out.println("�����ͼƬΪ��" + url);
				logger.error("===========================ͼƬ���س���=========================");
				logger.error("���س����ͼƬ��ַΪ��" + url);
				logger.error(ex.getMessage());
				ex.printStackTrace();
				continue;
			}
		}
		//TimeUtil.printTime(startTime);
	}
	
	/**
	 * ���ظó�Ա����ע�����е��û���������Ʒ���������ó�Ա�Լ�����Ʒ
	 * @throws Exception 
	 */
	public void downloadAllFollowersWorksByMemId(String id) throws Exception{
		//long startTime = System.currentTimeMillis();
		DownloadPageSourceCode demo = new DownloadPageSourceCode();
		DownloadOriginalPic download = new DownloadOriginalPic();
		
		Followers followers = demo.getFollowersById(id);
		
		/**
		 * �ó�Ա����ע�����е��û���������Ʒ������id+��followers"���ļ����£�ͼƬ����ͳһ�ø��û����Ե�id+��N"
		 */
		String folder = id + "Followers/";
		
		List<String> followersId = followers.getFollowers_id();
		
		//followers�б��Ͳ�������Ա�Լ���id
		/*if(followersId.contains(id)){
			int self_id_index = followersId.indexOf(id);
			followersId.remove(self_id_index);
		}*/
		
		for(String followerId : followersId){
			logger.info("================��ʼ�����ղ��û�idΪ " + followerId + "��ͼƬ===============");
			
			
			List<String> followerAllUrl = demo.getWorksUrlByMemId(followerId);
			String filename = folder + followerId + "N";
			for(String url : followerAllUrl){
				try{
					download.download(url, filename);
				}catch(IndexOutOfBoundsException ex){
					logger.error("===========ͼƬ���س���===========");
					logger.error("�����ͼƬ��ַΪ" + url);
					logger.error(ex.getMessage());
					ex.printStackTrace();
					continue;
				}catch(IOException e){
					//System.out.println("=============================������===================");
					logger.error("===========ͼƬ���س���===========");
					logger.error("�����ͼƬ��ַΪ" + url);
					logger.error(e.getMessage());
					e.printStackTrace();
					continue;
				}
				
			}
			
		}
		
		//TimeUtil.printTime(startTime);
		
	}
	
	/**
	 * ���ظó�Ա���ղص�����ͼƬ<br>
	 * ͼƬ������Ըó�Աid+"favorites"�������ļ����У�ͼƬ���û�id+"N"ͳһ����<br>
	 * @param id
	 * @throws Exception 
	 */
	public void downloadAllFavoritePicByMemId(String id) throws Exception{
		long startTime = System.currentTimeMillis();
		DownloadPageSourceCode dpsc = new DownloadPageSourceCode();
		DownloadOriginalPic download = new DownloadOriginalPic();
		
		String folder = id + "Favorites/";
		
		Map<String, String> linkAndId = dpsc.getFavoriteWorksUrlByMemId(id);
		for(Map.Entry entry : linkAndId.entrySet()){
			String userId = (String) entry.getValue();
			String filename = folder + userId + "N";
			String url = (String)entry.getKey();
			try{
				download.download(url, filename);
			}catch(IndexOutOfBoundsException e){
				logger.error("===========ͼƬ���س���===========");
				logger.error("�����ͼƬ��ַΪ" + url);
				logger.error(e.getMessage());
				e.printStackTrace();
				continue;
			}catch(IOException ex){
				//System.out.println("=========================������==========================");
				//System.out.println("�����ͼƬ��ַΪ��" + url);
				logger.error("===========ͼƬ���س���===========");
				logger.error("�����ͼƬ��ַΪ" + url);
				logger.error(ex.getMessage());
				ex.printStackTrace();
				continue;
			}
		}
		
		//TimeUtil.printTime(startTime);
	}
	
	/**
	 * �ѽ��չ������а��ϵ�100��ͼƬ��������<br>
	 * https://www.pixiv.net/ranking_area.php?type=detail&no=6<br>
	 * ���������no=6�޸ĳ�0-5�����֣���ֱ��ʾ�ձ�6�������ĵ������а񣬵�����Щ�����ֻ��ǰ50����Ʒ<br>
	 * 0��Ӧ������/����<br>
	 * 1��Ӧ�ض�<br>
	 * 2��Ӧ�в�<br>
	 * 3��Ӧ����<br>
	 * 4��Ӧ�й�/�Ĺ�<br>
	 * 5��Ӧ����/����<br>
	 * 6��Ӧ����<br>
	 * @throws Exception 
	 */
	public void downloadInternationalRankingPicForToday(int regionCode) throws Exception{
		//long startTime = System.currentTimeMillis();
		
		String url = "https://www.pixiv.net/ranking_area.php?type=detail&no=" + regionCode;
		
		DownloadHtml download = new DownloadHtml();
		Map<String, String> picAddrs = new HashMap<>();
		try {
			picAddrs = RegHtml.regRankingPageForPicAddr(download.getHtml(url));
		} catch (IOException e) {
			System.out.println("==========������ҳԴ�����,��Ҫ����===========");
			e.printStackTrace();
			
		}
		DownloadOriginalPic down = new DownloadOriginalPic();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(new Date());
		String folder = today + "Ranking/";
		
		for(Map.Entry entry : picAddrs.entrySet()){
			String authorId = (String)entry.getValue();
			String filename = folder + authorId + "N";
			String picUrl = (String)entry.getKey();
			try{
				down.download(picUrl, filename);
			}catch(IndexOutOfBoundsException e){
				logger.error("===========ͼƬ���س���===========");
				logger.error("�����ͼƬ��ַΪ" + picUrl);
				logger.error(e.getMessage());
				e.printStackTrace();
				continue;
			}catch(IOException ex){
				//System.out.println("========����ͼƬ�ǳ�����=======");
				//System.out.println("ͼƬ��ַ��" + picUrl);
				logger.error("===========ͼƬ���س���===========");
				logger.error("�����ͼƬ��ַΪ" + picUrl);
				logger.error(ex.getMessage());
				ex.printStackTrace();
				continue;
			}
		}
		//TimeUtil.printTime(startTime);
		
	}
	
	/**
	 * ����2016��ĳ��ÿ��ĵ����������Ի�ӭ��50��ͼƬ<br>
	 * ��ַ���£�<br>
	 * https://www.pixiv.net/ranking.php?mode=male&date=20170409
	 * @throws Exception 
	 */
	public void downloadRankingByDay() throws Exception{
		//long startTime = System.currentTimeMillis();
		
		DownloadOriginalPic download = new DownloadOriginalPic();
		DownloadHtml downloadHtml = new DownloadHtml();
		String folder = "201612DayRanking/";
		String urlSuffix;
		for(int i = 1; i <= 31; i++){
			urlSuffix = String.valueOf(i);
			if(i<10){
				urlSuffix = "0" + urlSuffix;
			}
			String urlPre = "https://www.pixiv.net/ranking.php?mode=male&date=201612";
			String url = urlPre + urlSuffix;
			try{
				Map<String, String> picAddrs = RegHtml.regRankingPageForPicAddr(downloadHtml.getHtml(url));
				
				for(Map.Entry entry : picAddrs.entrySet()){
					String authorId = (String)entry.getValue();
					String filename = folder + authorId + "N";
					String picUrl = (String)entry.getKey();
					try{
						download.download(picUrl, filename);
					}catch(IndexOutOfBoundsException ex){
						logger.error("===========ͼƬ���س���===========");
						logger.error("�����ͼƬ��ַΪ" + url);
						logger.error(ex.getMessage());
						ex.printStackTrace();
						continue;
					}catch(IOException e){
						System.out.println("===========����ͼƬ����===========");
						System.out.println("����ͼƬ��ַ��" + picUrl);
						logger.error("===========ͼƬ���س���===========");
						logger.error("�����ͼƬ��ַΪ" + picUrl);
						logger.error(e.getMessage());
						e.printStackTrace();
						continue;
					}
					
				}
			}catch(IOException ex){
				System.out.println("======������ҳԴ��������Դ��յ�����====");
				System.out.println("����Ϊ2017��3�µ�" + i + "��");
				ex.printStackTrace();
				continue;
			}			
			
		}

	}
	
	/**
	 * ͨ���ղ�һ��ͼƬ���ٸ��ݸ�ͼƬ��id����ȡPվ�Ƽ���500����ص�ͼƬ
	 * 
	 * @param id ͼƬ��id
	 * @throws Exception 
	 */
	public void downloadRecommendPicByPicId(String id) throws Exception{
		
		//Logger logger = LogManager.getLogger("mylog");
		
		//long startTime = System.currentTimeMillis();
		GetRecommend recommender = new GetRecommend();
		String folder = id + "Recommend/";
		
		String[] ids = recommender.getRecommendIdsFromBookmark(id);
		Map<String, String> picUrls = recommender.getRecommendPicAddrs(ids);
		System.out.println("==================�ܹ���ȡ�����Ƽ�ͼƬ����Ϊ======================");
		System.out.println(picUrls.size() + "��ͼƬ");
		logger.info("===============�ܹ���ȡ�����Ƽ�ͼƬ����Ϊ======================");
		logger.info(picUrls.size() + "��ͼƬ");
		logger.info("===============��ȡ����ͼƬ���ӵ�ַ��=========================");
		
		for(Map.Entry entry : picUrls.entrySet()){
			System.out.println(entry.getValue() + "=" + entry.getKey());
		}
		
		DownloadOriginalPic download = new DownloadOriginalPic();
		
		for(Map.Entry entry : picUrls.entrySet()){
			String authorId = (String)entry.getValue();
			String filename = folder + authorId + "N";
			String picUrl = (String)entry.getKey();
			logger.info("ͼƬ����id=" + authorId);
			logger.info(picUrl);
			
			try{
				download.download(picUrl, filename);
			}catch(IndexOutOfBoundsException iex){
				System.out.println("==================�ַ���ƥ�����================");
				iex.printStackTrace();
				continue;
			}catch(IOException ex){
				System.out.println("==================����ͼƬ����,��ͼƬ��ַΪ==================");
				System.out.println(picUrl);
				ex.printStackTrace();
				continue;
			
			}
		}
		
		
		//TimeUtil.printTime(startTime);
		
	}
	
	/**
	 * ��������id��ȡ��������Ʒ��ͼƬid���ٸ�����ЩͼƬid��ȡPվ�Ƽ�������ͼƬ<br>
	 * <p>
	 * ��Ϊÿ��ͼƬid�ܹ���ȡ��500�������Bug�����Ƽ�ͼƬ�����Ի�ȡ��<br>
	 * ͼƬ���� = ������Ʒ��*500<br>
	 * ��ͼƬͳһ���浽"����id+AuthorIDRec/ + ͼƬ����id+N + ͼƬid"<br>
	 * ������DownloadOriginalPic.java�л���������"ͼƬid+D"���Ա㴦���ظ�����<br>
	 * 
	 * @param id
	 */
	public void downloadRecommendPicByAuthorId(String id){
		DownloadOriginalPic download = new DownloadOriginalPic();
		DownloadPageSourceCode dpsc = new DownloadPageSourceCode();
		//���Ȼ�ȡָ����Ա��������Ʒurl
		List<String> allWorksUrl = dpsc.getWorksUrlByMemId(id);
		
		for(String worksUrl : allWorksUrl){
			//�ٻ�ȡ����һ��ͼƬ��id,�Ӷ����ݸ�ͼƬid��ȡ�Ƽ���500��ͼƬ
			//��ȡͼƬid
			String worksPicId = RegHtml.regPicUrlForPicId(worksUrl);
			logger.info("=========��ʼ����idΪ" + worksPicId + "���Ƽ�ͼƬ=======");
			
			//��ȡ�Ƽ���500��ͼƬ��id
			GetRecommend gr = new GetRecommend();
			String[] recIds = gr.getRecommendIdsFromBookmark(worksPicId);
			//��װ��ȡ500��ͼƬ�ľ���Сͼ��ַ
			Map<String, String> recPicsUrl = gr.getRecommendPicAddrs(recIds);
			for(Map.Entry entry : recPicsUrl.entrySet()){
				String picAuthorId = (String)entry.getValue();
				String picUrl = (String)entry.getKey();
				String picId = RegHtml.regPicUrlForPicId(picUrl);
				String filename = id + "AuthorIdRec/" + picAuthorId + "N";
				
				try{
					download.download(picUrl, filename);
				}catch(IndexOutOfBoundsException e){
					logger.error("===========ͼƬ���س���===========");
					logger.error("�����ͼƬ��ַΪ " + picUrl);
					logger.error(e.getMessage());
					e.printStackTrace();
					continue;
				}catch(IOException ex){
					logger.error("===========ͼƬ���س���===========");
					logger.error("�����ͼƬ��ַΪ " + picUrl);
					logger.error(ex.getMessage());
					ex.printStackTrace();
					continue;
				}
				
				
			}
			
		}
		
		
		
	}

}
