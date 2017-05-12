package crawler.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetRecommend {

	public GetRecommend(){
		
	}
	
	/**
	 * ͨ���ղ�һ����ͼ���Ӷ����Pվ�Ƽ�������500����ͼ��id<br>
	 * 
	 * <p>ע�⣺�������ֻ�ǻ�ȡ�Ƽ���500��ͼƬ��id�������ǻ�ȡ500��ͼƬ�ĵ�ַ��<br>
	 * ͼƬ��ַ�Ļ�ȡ��Ҫ�����һ��������������Ӧ��������л�ȡ��<br>
	 * ��ȡ��500���Ƽ���ͼID�������ַ�����µģ�<br>
	 * <b>https://www.pixiv.net/rpc/recommender.php?type=illust&sample_illusts=50243983&num_recommendations=500&tt=259ed8be42a041cce88449320b4c557c</b><br>
	 * ��������tt�������Բ��ã���sample_illusts=50243983������ʼ�ղص�����ͼƬ��id
	 * 
	 * @param id 		������Ϊ���ӵ���ʼ�ղ�ͼƬ��id
	 * @return
	 */
	public String[] getRecommendIdsFromBookmark(String id){
		String[] ids;
		String urlPre = "https://www.pixiv.net/rpc/recommender.php?type=illust&sample_illusts=";
		String urlSuf = "&num_recommendations=500";
		String url = urlPre + id + urlSuf;
		
		DownloadHtml downloader = new DownloadHtml();
		String html = "";
		
		try {
			html = downloader.getHtml(url);
		} catch (IOException e) {
			System.out.println("======��ȡ�ղ��Ƽ�IDsʧ��=======");
			e.printStackTrace();
		}
		
		ids = RegHtml.regRecommenderPageForIds(html);
		
		System.out.println("====================Pվ�Ƽ���ͼƬid�У�==================");
		for(int i = 0; i < ids.length; i++){
			System.out.println(ids[i]);
		}
		
		return ids;
	}
	
	/**
	 * ���ݶ��ͼƬ��id����ȡ��ӦͼƬ��Сͼ��ַ<br>
	 * <p>
	 * ����ĵ�ַ������ʾ��<br>
	 * <b>https://www.pixiv.net/rpc/illust_list.php?illust_ids=51176125%2C49258389%2C49908880%2C51645544%2C49810303%2C49934602%2C45629088%2C49908488%2C56884511%2C50759967%2C49831074%2C49911367%2C50936724%2C51145109%2C50215790&verbosity=&exclude_muted_illusts=1</b><br>
	 * ����һ��������15��ͼƬ�ĵ�ַ������������Ҳһ��ֻ����15��ͼƬ�ĵ�ַ
	 * 
	 * @param picIds
	 * @return
	 */
	public Map<String, String> getRecommendPicAddrs(String[] picIds){
		Map<String, String> picAddrs = new HashMap<>();
		String urlPre = "https://www.pixiv.net/rpc/illust_list.php?illust_ids=";
		String urlLast="&verbosity=&exclude_muted_illusts=1";
		
		for(int i = 0; i < picIds.length; i++){
			String urlSuf = "";
			
			//�����iλ�õ������15����ʱ�򣬾�ͨ�����ѭ����ͼƬ��id����׷�ӵ�url����
			if(i >= picIds.length-15){
				while(i < picIds.length){
					urlSuf += picIds[i++] + "%2C";
				}
			}else{
				//��15��Ԫ�طŵ�url�����������
				for(int j = 0; j < 15; j++){
					int tmp = i + j;
					String id = picIds[tmp];
					urlSuf += id + "%2C";
				}
				i += 15;
			}
			
			//��Ϊ�����������"&"�Ž�β���������յ�urlҪȥ���Ǹ�"&"��
			urlSuf = urlSuf.substring(0, urlSuf.length()-3);
			
			String url = urlPre + urlSuf + urlLast;
			
			DownloadHtml downloader = new DownloadHtml();
			String tagHtml = "";
			try {
				tagHtml = downloader.getHtml(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("========================��ȡ��ҳ��==========================");
			System.out.println(tagHtml);
			
			picAddrs.putAll(RegHtml.regTagHtmlForIdAddrs(tagHtml));
			
			
		}
		
		//System.out.println("====================��ȡ��������Ϊ==============");
		//System.out.println(picAddrs.size());
		
		return picAddrs;
	}
	
	
	public static void main(String[] args){
		GetRecommend demo = new GetRecommend();
		
		//String id = "50243983";
		//demo.getRecommendIdsFromBookmark(id);
		
		String[] ids= {
				"51176125","49258389","49908880","51645544","49810303",
				"49934602","45629088","49908488","56884511","50759967","49831074",
				"49911367","50936724","51145109","50215790","51739759",
				"50099922","50944690","47084218","52025622","52044837","49920423"
		};
		demo.getRecommendPicAddrs(ids);
		
		
	}
}
