package crawler.customize;

public class Demo {

	public static void main(String[] args){
		DownloadByDifferentCond demo = new DownloadByDifferentCond();
		
		//String id = "418969";
		//String id = "5206721";
		
		//ֻ���ظó�Ա��������Ʒ
		//demo.downloadAllWorksByMemId(id);
		
		//���ظó�Ա����ע�������û���������Ʒ����������Ա�Լ�����Ʒ
		//demo.downloadAllFollowersWorksByMemId(id);
		
		//���ظó�Ա���ղص�����ͼƬ
		//demo.downloadAllFavoritePicByMemId(id);
		
		//���ؽ��չ������а��ǰ100������Ʒ
		//demo.downloadInternationalRankingPicForToday(6);
		
		//����2017��3�µ����еĽ������ݣ�Ҳ��30��*50��
		demo.downloadRankingByDay();
	}
}
