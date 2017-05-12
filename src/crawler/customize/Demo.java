package crawler.customize;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crawler.utils.TimeUtil;

public class Demo {

	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		Logger logger = LogManager.getLogger("mylog");
		logger.trace("��������");
		
		
		DownloadByDifferentCond demo = new DownloadByDifferentCond();
		
		String id = "7616522";
		//String id = "5206721";
		
		//ֻ���ظó�Ա��������Ʒ
		try {
			logger.trace("==============================����ָ����Ա��������Ʒ============================");
			logger.trace("��ʼ���س�ԱidΪ" + id + "������ͼƬ");
			demo.downloadAllWorksByMemId(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("=====================����ָ����Ա��������Ʒ����=================================");
			logger.error("�ó�Ա��idΪ��" + id);
			logger.error("������ϢΪ��");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		//���ظó�Ա���ղص�����ͼƬ
		//demo.downloadAllFavoritePicByMemId(id);
		
		//���ظó�Ա����ע�������û���������Ʒ����������Ա�Լ�����Ʒ
		//demo.downloadAllFollowersWorksByMemId(id);
		
		
		//���ؽ��չ������а��ǰ100������Ʒ
		//demo.downloadInternationalRankingPicForToday(6);
		
		//����2017��3�µ����еĽ������ݣ�Ҳ��30��*50��
		//demo.downloadRankingByDay();
		
		//ͼƬid
		/*String[] picids ={};
		for(int i = 0; i < picids.length; i++){
			try{
				logger.info("ͼƬid=" + picids[i]);
				demo.downloadRecommendPicByPicId(picids[i]);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("===================�Ƽ�ͼƬ���ز���ȫ============================");
				logger.error("��ͼƬid=" + picids[i] + "�����Ƽ�ͼƬ����û�н������Ƽ���ͼƬ��������");
				continue;
			}
		}*/
		
		TimeUtil.printTime(startTime);
	}
}
