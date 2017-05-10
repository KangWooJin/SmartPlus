package com.smartplus.smartplus;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class WeatherTest {

	public static void main(String args[]) {
		WeatherTest wt = new WeatherTest();

		wt.getWeatherToday();
		 wt.getWeatherWeek();

	}

	public void getWeatherToday() {
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = f.newDocumentBuilder();
			String url = "http://web.kma.go.kr/wid/queryDFSRSS.jsp?zone=1159068000";
			URL u = new URL(url);
			InputStreamReader is = new InputStreamReader(u.openStream(),
					"utf-8");

			Document xmlDoc = null;
			xmlDoc = parser.parse(new InputSource(is));
			NodeList root = xmlDoc.getElementsByTagName("channel");

			NodeList channel = root.item(0).getChildNodes();

			NodeList item = channel.item(13).getChildNodes();

			NodeList description = item.item(10).getChildNodes();

			NodeList body = description.item(3).getChildNodes();

			NodeList data = body.item(1).getChildNodes();

			Date d = new Date();
			Calendar cal = Calendar.getInstance();
		    cal.setTime(d);
		    int year = cal.get(Calendar.YEAR);
		    int month = cal.get(Calendar.MONTH);
		    int day = cal.get(Calendar.DAY_OF_MONTH);
		    
			String mDate = year+"-"+month+"-"+day;
			String wf = data.item(15).getTextContent();
			String mn = data.item(9).getTextContent();
			String mx = data.item(7).getTextContent();

			System.out.println(mDate + " " + wf + " " + mn + " " + mx);
			

		} catch (Exception e) {
		}

	}

	public void getWeatherWeek() {
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = f.newDocumentBuilder();
			String url = "http://www.kma.go.kr/weather/forecast/mid-term-xml.jsp?stnId=109";
			URL u = new URL(url);
			InputStreamReader is = new InputStreamReader(u.openStream(),
					"utf-8");
			Document xmlDoc = null;
			xmlDoc = parser.parse(new InputSource(is));
			NodeList root = xmlDoc.getElementsByTagName("body");
			NodeList s = root.item(0).getChildNodes();
			NodeList ss = s.item(1).getChildNodes();

			String mDate;
			String wf;
			String mn;
			String mx;

			for (int i = 5; i < ss.getLength(); i += 2) {
				NodeList sss = ss.item(i).getChildNodes();

				mDate = sss.item(3).getTextContent();
				wf = sss.item(5).getTextContent();
				mn = sss.item(7).getTextContent();
				mx = sss.item(9).getTextContent();

				System.out.println(mDate + " " + wf + " " + mn + " " + mx);

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
