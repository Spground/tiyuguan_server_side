package com.dlut.cx.action;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dlut.cx.util.C;

public class NewsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int newsNum = 5;

	public void setNewsNum(int newsNum) {
		this.newsNum = newsNum;
	}

	public String getNews() throws IOException {
		String url = "http://tyg.dlut.edu.cn/index.htm";
		Elements links = Jsoup.connect(url).get().select("a[href][class=c63065]");

		List<Map<String, String>> newsList = new ArrayList<Map<String, String>>();
		int i = 0;
		for (Element link : links) {
			if (i == newsNum)
				break;

			Document document = Jsoup.connect(link.attr("abs:href")).get();//
			Elements el = document.select("span[class=timestyle63106]");
			Element elDiv = document.getElementById("vsb_newscontent");//�������µ�div
			Element firstImageEl = elDiv.getElementsByTag("img").first();//��һ���DƬ
			
			Map<String, String> newsMap = new HashMap<String, String>();
			newsMap.put("url", link.attr("abs:href"));
			newsMap.put("title", link.attr("title"));
			newsMap.put("time", el.text());
			/**get the image source of this news**/
			String imageSrc = null;
			imageSrc = firstImageEl.attr("src");
			imageSrc = imageSrc == null ? "http://www.dlut.edu.cn/xiaohua.jpg" : ("http://tyg.dlut.edu.cn/" + imageSrc.substring(6));
			newsMap.put("firstImageSrc", imageSrc);
			newsList.add(newsMap);
			i++;
		}

		setResultMap(C.code.NEWS, C.message.SUCCESS, C.name.NEWS_MAPNAME, newsList);
		return SUCCESS;
	}

}
