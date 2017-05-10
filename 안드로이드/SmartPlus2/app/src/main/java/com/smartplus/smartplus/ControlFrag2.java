package com.smartplus.smartplus;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class ControlFrag2 extends Fragment {


    View view;

    ImageView tv_frag1_m;
    ImageView tv_frag2_m;
    ImageView tv_frag3_m;
    ImageView tv_frag4_m;
    ImageView tv_frag5_m;

    TextView[]tvArr;
    ImageView[]ivArr;

    TextView tv_frag2_t11;
    TextView tv_frag2_t12;
    TextView tv_frag2_t13;

    TextView tv_frag2_t21;
    TextView tv_frag2_t22;
    TextView tv_frag2_t23;

    TextView tv_frag2_t31;
    TextView tv_frag2_t32;
    TextView tv_frag2_t33;

    TextView tv_frag2_t41;
    TextView tv_frag2_t42;
    TextView tv_frag2_t43;

    TextView tv_frag2_t51;
    TextView tv_frag2_t52;
    TextView tv_frag2_t53;


    String streamTitle = "";
    String strTmEf="";
    String strWf="";
    String strTmn="";
    String strTmx="";



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_control_frag2, container, false);


        tvArr = new TextView[15];
        ivArr = new ImageView[5];

        ivArr[0] =  (ImageView)view.findViewById(R.id.tv_frag2_m1);
        ivArr[1] =  (ImageView)view.findViewById(R.id.tv_frag2_m2);
        ivArr[2] =  (ImageView)view.findViewById(R.id.tv_frag2_m3);
        ivArr[3] =  (ImageView)view.findViewById(R.id.tv_frag2_m4);
        ivArr[4] =  (ImageView)view.findViewById(R.id.tv_frag2_m5);

        tvArr[0] = (TextView)view.findViewById(R.id.tv_frag2_t11);
        tvArr[1] = (TextView)view.findViewById(R.id.tv_frag2_t12);
        tvArr[2] =  (TextView)view.findViewById(R.id.tv_frag2_t13);

        tvArr[3] = (TextView)view.findViewById(R.id.tv_frag2_t21);
        tvArr[4] = (TextView)view.findViewById(R.id.tv_frag2_t22);
        tvArr[5] = (TextView)view.findViewById(R.id.tv_frag2_t23);

        tvArr[6] = (TextView)view.findViewById(R.id.tv_frag2_t31);
        tvArr[7] = (TextView)view.findViewById(R.id.tv_frag2_t32);
        tvArr[8] = (TextView)view.findViewById(R.id.tv_frag2_t33);

        tvArr[9] = (TextView)view.findViewById(R.id.tv_frag2_t41);
        tvArr[10] = (TextView)view.findViewById(R.id.tv_frag2_t42);
        tvArr[11] = (TextView)view.findViewById(R.id.tv_frag2_t43);

        tvArr[12] = (TextView)view.findViewById(R.id.tv_frag2_t51);
        tvArr[13] = (TextView)view.findViewById(R.id.tv_frag2_t52);
        tvArr[14] = (TextView)view.findViewById(R.id.tv_frag2_t53);







        ProcessXmlTask xmlTask = new ProcessXmlTask();
        xmlTask.execute("http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=108");


        return view;
    }


    private class ProcessXmlTask extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... urls) {
            try {

                URL rssUrl = new URL(urls[0]);
                SAXParserFactory mySAXParserFactory = SAXParserFactory.newInstance();
                SAXParser mySAXParser = mySAXParserFactory.newSAXParser();
                XMLReader myXMLReader = mySAXParser.getXMLReader();
                RSSHandler myRSSHandler = new RSSHandler();
                myXMLReader.setContentHandler(myRSSHandler);
                InputSource myInputSource = new InputSource(rssUrl.openStream());
                myXMLReader.parse(myInputSource);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.i("THIS",strTmEf);
            Log.i("THIS",strTmn);
            Log.i("THIS",strTmx);
            Log.i("THIS",strWf);


            getWidget();

            super.onPostExecute(result);
        }
    }


    private class RSSHandler extends DefaultHandler {
        final int stateUnknown = 0;
        final int stateTitle = 1;
        int state = stateUnknown;

        int numberOfTitle = 0;
        String strTitle = "";
        String strElement = "";

        @Override
        public void startDocument() throws SAXException {
            strTitle = "--- Start Document ---\n";
        }

        @Override
        public void endDocument() throws SAXException {
            strTitle += "--- End Document ---";
            streamTitle = "Number Of Title: " + String.valueOf(numberOfTitle) + "\n" + strTitle;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            if (localName.equalsIgnoreCase("tmEf")) {
                state = stateTitle;
                strElement = "tmEf: ";
                numberOfTitle++;
            }
            else if (localName.equalsIgnoreCase("wf")) {
                state = stateTitle;
                strElement = "wf: ";
                numberOfTitle++;
            }
            else if (localName.equalsIgnoreCase("tmn")) {
                state = stateTitle;
                strElement = "tmn: ";
                numberOfTitle++;
            }
            else if (localName.equalsIgnoreCase("tmx")) {
                state = stateTitle;
                strElement = "tmx: ";
                numberOfTitle++;
            }
            else {
                state = stateUnknown;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

            if (localName.equalsIgnoreCase("tmEf")) {
                strTmEf+=strElement+"@";
            }
            if (localName.equalsIgnoreCase("wf")) {
                strWf += strElement + "@";
            }
            if (localName.equalsIgnoreCase("tmn")) {
                strTmn += strElement + "@";
            }
            if (localName.equalsIgnoreCase("tmx")) {
                strTmx += strElement + "@";
            }
            state = stateUnknown;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String strCharacters = new String(ch, start, length);
            if (state == stateTitle) {
                strElement += strCharacters;
            }
        }
    }


    private void getWidget() {


        Log.i("THIS",strTmEf);
        Log.i("THIS",strTmn);
        Log.i("THIS",strTmx);
        Log.i("THIS",strWf);



        Weather w = new Weather();
        String[]strWeather = w.getWeathehr().split("@");

        int count=0;
        int count2=0;
        for(int i = 0 ; i < 25 ; i+=5)
        {
            Log.i("AA", strWeather[i]); //맑음
            Log.i("AA", strWeather[i + 1]); // 최소
            Log.i("AA", strWeather[i + 2]); // 최대
            Log.i("AA", strWeather[i + 3]); // 월
            Log.i("AA", strWeather[i + 4]); // 일


            if(i==0) {
                tvArr[count++].setText( "Today  "+ String.valueOf(strWeather[i + 3]) + " / " + String.valueOf(strWeather[i + 4]));
                tvArr[count++].setText("");
                tvArr[count++].setText( "최소 : "+ strWeather[i+1] +"   /   최대 : " + strWeather[i + 2]);

            }
            else {
                tvArr[count++].setText(String.valueOf(strWeather[i + 3]) + " / " + String.valueOf(strWeather[i + 4]));
                tvArr[count++].setText("최소 : " + strWeather[i + 1]);
                tvArr[count++].setText("최대 : " + strWeather[i + 2]);
            }


            if(strWeather[i].compareTo("맑음")==0)
            {
                ivArr[count2++].setImageDrawable(getResources().getDrawable(R.drawable.w_sunny));
            }
            else if(strWeather[i].compareTo("흐림")==0)
            {
                ivArr[count2++].setImageDrawable(getResources().getDrawable(R.drawable.w_cloudy));
            }
            else if(strWeather[i].compareTo("비")==0)
            {
                ivArr[count2++].setImageDrawable(getResources().getDrawable(R.drawable.w_rain));
            }

        }







    }


}

