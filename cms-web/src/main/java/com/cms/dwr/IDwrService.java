package com.cms.dwr;

public interface IDwrService {
	/**
	 * 添加GroupChannel对象
	 * @param group
	 * @param channel
	 */
	public void addGroupChannel(int gid, int cid);
	
	/**
	 * 删除用户栏目
	 * @param gid
	 * @param cid
	 */
	public void deleteGroupChannel(int gid, int cid);
	
	/**
	 * 改变图片是否是附件
	 * @param aId
	 */
	public void changeIsAttach(long aId);
	/**
	 * 改变图片是否是首页图片
	 * @param aId
	 */
	public void changeIsIndex(long aId);

	/**
	 * 更新首页图片的顺序
	 * @param id
	 * @param oldPos
	 * @param newPos
	 */
	public void updatePicPos(Long id, int oldPos, int newPos);

}
