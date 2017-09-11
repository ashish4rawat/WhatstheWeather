package thechnical.whatstheweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
        String city = "";
        String con = "";
        String show;

        class DownloadWeatherContent extends AsyncTask<String, Void, String> {

                String result = "";

                @Override
                protected String doInBackground(String... strings) {

                        try {
                                URL url = new URL(strings[0]);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                InputStream is = connection.getInputStream();
                                InputStreamReader reader = new InputStreamReader(is);
                                int data = reader.read();
                                while (data != -1) {

                                        char ch = (char) data;
                                        result += ch;

                                        data = reader.read();

                                }

                                return result;

                        } catch (Exception e) {
                                Log.i("Info", "Exception in downloading content");
                        }


                        return null;
                }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
        }

        // API key = 1cb7b9604e47c6c736f40818fa0359f7
        //name = mykey

        public void findButtonClicked(View view) {

                EditText ashishText = (EditText) findViewById(R.id.ashishText);
                city = ashishText.getText().toString();
                downloadContent();

                try {
                        JSONObject jsonObject = new JSONObject(con);
                        String wea = jsonObject.getString("weather");
                        JSONArray arr = new JSONArray(wea);
                        for (int i = 0; i < arr.length(); i++) {

                                JSONObject jsonPart = arr.getJSONObject(i);
                                show = jsonPart.getString("main") + " : " + jsonPart.getString("description");
                                show = show+"\n";



                        }

                } catch (Exception e) {
                        Log.i("Info", "Json exception");
                        show="";
                }

                TextView ashishDes = (TextView)findViewById(R.id.ashishDes);

                if(show=="")
                        ashishDes.setText("Enter a Valid City");
                  else
                        ashishDes.setText(show);


        }

        public void downloadContent() {

                DownloadWeatherContent obj = new DownloadWeatherContent();
                try {
                        con = obj.execute("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=1cb7b9604e47c6c736f40818fa0359f7").get();
                        //Log.i("Content",con);


                } catch (Exception e) {
                        Log.i("Info", "exception");
                        show="";
                }


        }


}
