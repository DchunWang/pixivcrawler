package crawler.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	/**
	 * ��ӡ���õ�ʱ��<br>
	 * ֻҪ��long�͵Ŀ�ʼʱ�䴫�룬�Ϳ��Դ�ӡ���ӿ�ʼʱ�䵽�˺���ִ��ʱ��������ʱ��<br>
	 * 
	 * @param startTime	����ʼ��ʱ��
	 */
	public static void printTime(long startTime){
		
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
		System.out.println("�����ڣ�" + endStr);
		System.out.println("�����ˣ�" + difDay + "��" + difHour + "Сʱ" + difMinute + "����" + difSecond + "��");
	}
}
