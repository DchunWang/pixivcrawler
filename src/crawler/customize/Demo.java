package crawler.customize;

public class Demo {

	public static void main(String[] args){
		DownloadByDifferentCond demo = new DownloadByDifferentCond();
		
		String id = "4754550";
		
		//ֻ���ظó�Ա��������Ʒ
		//demo.downloadAllWorksByMemId(id);
		
		//���ظó�Ա����ע�������û���������Ʒ����������Ա�Լ�����Ʒ
		demo.downloadAllFollowersWorksByMemId(id);
	}
}
