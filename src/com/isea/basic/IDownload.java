package com.isea.basic;

public interface IDownload extends ILoggerProgress{

	/**
	 * 批量下载图片
	 * @param savePath
	 * @param url
	 * @param selector
	 * @param page
	 * @param pageSize
	 * @param first true - 首页不同
	 */
	void downloadMore(String savePath, String url,
			String selector, String page, int start, int pageSize, boolean first);
	
	/**
	 * 停止下载
	 */
	void stop();

}