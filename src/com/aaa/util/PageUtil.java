package com.aaa.util;

public class PageUtil {

	/**��ǰҳ*/
	private int currentPage;
	
	/**��һҳ*/
	private int shangyiye;
	
	/**��һҳ*/
	private int xiayiye;
	
	/**βҳ*/
	private int weiye;
	
	/**��ҳ*/
	private int shouye = 1;
	
	/**������������*/
	private int gong;
	
	/**������������*/
	private int count;
	
	public PageUtil() {}
	
	public PageUtil(int currentPage, int gong , int count) {
		//����˵��ǰҳ��3  
		this.currentPage = currentPage;
		this.gong = gong;
		this.count = count;
		
		this.weiye = (gong + count - 1) / count;
		
		this.shangyiye = this.currentPage - 1 > 0 ? this.currentPage - 1 : 1;
		this.xiayiye = this.currentPage + 1 > this.weiye ? this.weiye : this.currentPage + 1;
		
		this.count = count;
	}
	/**
	 * ��ȡ��ǰҳ��Ӧ������
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	public int getIndex() {
		return (currentPage - 1) * count;
	}
	/**
	 * �趨��ǰҳ
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getShangyiye() {
		return shangyiye;
	}

	public void setShangyiye(int shangyiye) {
		this.shangyiye = shangyiye;
	}

	public int getXiayiye() {
		return xiayiye;
	}

	public void setXiayiye(int xiayiye) {
		this.xiayiye = xiayiye;
	}

	public int getWeiye() {
		return weiye;
	}

	public void setWeiye(int weiye) {
		this.weiye = weiye;
	}

	public int getShouye() {
		return shouye;
	}

	public void setShouye(int shouye) {
		this.shouye = shouye;
	}

	public int getGong() {
		return gong;
	}

	public void setGong(int gong) {
		this.gong = gong;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "PageUtil [currentPage=" + currentPage + ", shangyiye=" + shangyiye + ", xiayiye=" + xiayiye + ", weiye="
				+ weiye + ", shouye=" + shouye + ", gong=" + gong + "]";
	}
	
	
}
