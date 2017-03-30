package crawler.vo;

import java.util.List;
import java.util.Map;

/**
 * ��Ա����ע�������û�		//Ŀǰֻ���ռ��������Ĺ�ע�û�
 * 
 * @author Stargazer
 * @date 2017-03-30
 */
public class Followers {
	
	//��Ա��id
	private String user_id;			
	
	//����ע�û�������
	private int followers_num;
	
	/**
	 * ��ע���û���id
	 */
	private List<String> followers_id;			
	
	/**
	 * ��ע���û������ƣ���Ϊ��ע�û���id��ֵΪ���û�������
	 */
	private Map<String, String> followers_name;		
	
	/**
	 * ��ע���û��ĸ������ϵ�ַ����Ϊ��ע�û���id��ֵΪ���û������ӵ�ַ
	 * ע�⣬�������ӵ������û��ĸ������ϵ�ַ
	 * http://www.pixiv.net/member.php?id=4713
	 */
	private Map<String, String> followers_link;
	
	/**
	 * ��ע�û�����Ʒ��ַ����Ϊ��ע�û���id,ֵΪ���û�����Ʒ��ַ
	 * http://www.pixiv.net/member_illust.php?id=4713
	 */
	private Map<String, String> followers_works_link;
	
	/**
	 * ��ע�û����ղص�ַ����Ϊ��ע�û���id,ֵΪ���û����ղص�ַ
	 * http://www.pixiv.net/bookmark.php?id=4713
	 */
	private Map<String, String> followers_favorite_link;
	
	
	/**
	 * ���ע���û��Ƿ��໥��ע��������໥��ע�����ں�����𼶲�ѯ��ע�û���ʱ��
	 * ����������ѭ�������Ҫ��ǰע��! 
	 * ���У���Ϊ�û���id��ֵΪ�Ƿ��໥��ע
	 * ��ֵΪ"1"����ʾֻ�ǵ�����Ĺ�ע��Ҳ����Ա��ע���û������û�û�й�ע��Ա��
	 * ��ֵΪ"2"����ʾ���໥��ע�����߶���ע�˶Է���
	 * 
	 */
	private Map<String, String> follow_each_other;
	
	public Followers(String user_id){
		this.user_id = user_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getFollowers_num() {
		return followers_num;
	}

	public void setFollowers_num(int followers_num) {
		this.followers_num = followers_num;
	}

	public List<String> getFollowers_id() {
		return followers_id;
	}

	public void setFollowers_id(List<String> followers_id) {
		this.followers_id = followers_id;
	}

	public Map<String, String> getFollowers_name() {
		return followers_name;
	}

	public void setFollowers_name(Map<String, String> followers_name) {
		this.followers_name = followers_name;
	}

	public Map<String, String> getFollowers_link() {
		return followers_link;
	}

	public void setFollowers_link(Map<String, String> followers_link) {
		this.followers_link = followers_link;
	}

	public Map<String, String> getFollowers_works_link() {
		return followers_works_link;
	}

	public void setFollowers_works_link(Map<String, String> followers_works_link) {
		this.followers_works_link = followers_works_link;
	}

	public Map<String, String> getFollowers_favorite_link() {
		return followers_favorite_link;
	}

	public void setFollowers_favorite_link(Map<String, String> followers_favorite_link) {
		this.followers_favorite_link = followers_favorite_link;
	}

	public Map<String, String> getFollow_each_other() {
		return follow_each_other;
	}

	public void setFollow_each_other(Map<String, String> follow_each_other) {
		this.follow_each_other = follow_each_other;
	}
	

}
