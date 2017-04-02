package crawler.customize;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import crawler.core.DownloadOriginalPic;
import crawler.core.DownloadPageSourceCode;
import crawler.vo.Followers;

public class DownloadByDifferentCond {
	
	/**
	 * ֻ���ظ�id��Ա��������Ʒ
	 * 
	 * @param id
	 * @param dir		�ó�Ա����Ʒͳһ�ŵ�dir�ļ����£�ͼƬ������ͳһ��id+"N"�ĸ�ʽ
	 */
	public void downloadAllWorksByMemId(String id){
		long startTime = System.currentTimeMillis();
		DownloadPageSourceCode demo = new DownloadPageSourceCode();
		
		String authorName = demo.getAuthorNameById(id);
		System.out.println("�ó�Ա��Pվ�ǳ�Ϊ��" + authorName);
		//�滻�������в������ڽ����ļ������������
		//authorName = authorName.re
		
		List<String> allUrls = demo.getWorksUrlByMemId(id);
		
		DownloadOriginalPic download = new DownloadOriginalPic();
		
		String filename =  authorName + "\\" + id + "N";
		
		for(String url : allUrls){
			try{
				download.download(url, filename);
			}catch(IOException ex){
				System.out.println("=========================������============================");
				//System.out.println(ex.getMessage());
				ex.printStackTrace();
				continue;
			}
		}
		printTime(startTime);
	}
	
	/**
	 * ���ظó�Ա����ע�����е��û���������Ʒ���������ó�Ա�Լ�����Ʒ
	 */
	public void downloadAllFollowersWorksByMemId(String id){
		long startTime = System.currentTimeMillis();
		DownloadPageSourceCode demo = new DownloadPageSourceCode();
		DownloadOriginalPic download = new DownloadOriginalPic();
		
		Followers followers = demo.getFollowersById(id);
		
		/**
		 * �ó�Ա����ע�����е��û���������Ʒ������id+��followers"���ļ����£�ͼƬ����ͳһ�ø��û����Ե�id+��N"
		 */
		String folder = id + "followers\\";
		
		List<String> followersId = followers.getFollowers_id();
		
		//followers�б��Ͳ�������Ա�Լ���id
		/*if(followersId.contains(id)){
			int self_id_index = followersId.indexOf(id);
			followersId.remove(self_id_index);
		}*/
		
		for(String followerId : followersId){
			List<String> followerAllUrl = demo.getWorksUrlByMemId(followerId);
			String filename = folder + followerId + "N";
			for(String url : followerAllUrl){
				try{
					download.download(url, filename);
				}catch(IOException e){
					System.out.println("=============================������===================");
					//System.out.println(e.getMessage());
					e.printStackTrace();
					continue;
				}
				
			}
			
		}
		
		printTime(startTime);
		
	}
	
	
	/**
	 * ��ӡ���õ�ʱ��
	 * 
	 * @param startTime	����ʼ��ʱ��
	 */
	private void printTime(long startTime){
		
		long endTime = System.currentTimeMillis();
		Date start = new Date(startTime);
		Date end = new Date(endTime);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startStr = format.format(start);
		String endStr = format.format(end);
		long difTime = endTime - startTime;
		int difDay = (int) (difTime/(1000*60*60*24));
		int difHour = (int) ((difTime-difDay*1000*60*60*24)/(1000*60*60));
		int difMinute = (int)((difTime-((difDay*24+difHour)*1000*60*60))/(1000*60));
		int difSecond = (int)((difTime-(difDay*24*60+difHour*60+difMinute)*1000*60)/1000);
		System.out.println("============================ͼƬ���ؽ���=======================");
		System.out.println("��ʼ�ڣ�" + startStr);
		System.out.println("�����" + endStr);
		System.out.println("�����ˣ�" + difDay + "��" + difHour + "Сʱ" + difMinute + "����" + difSecond + "��");
	}

}
