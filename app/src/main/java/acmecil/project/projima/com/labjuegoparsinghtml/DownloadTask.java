package acmecil.project.projima.com.labjuegoparsinghtml;

import android.os.AsyncTask;

import android.util.Log;

/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
public class DownloadTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        String pURL = urls[0];
        URL url;
/*
        try {
            url = new URL(pURL);


            HttpClient httpclient = new DefaultHttpClient();

            // Revisar si se le manda url (objeto de tipo URL) o pURL (un String)
            HttpGet httpget = new HttpGet(pURL);

            // Execute the request
            HttpResponse response;
            try {
                response = httpclient.execute(httpget);
                Log.i("statusline", response.getStatusLine().toString());
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream instream = entity.getContent();
                    result = convertStreamToString(instream);
                    instream.close();
                }

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        */
        return result;

    }

        private static String convertStreamToString(InputStream is) {
            /*
             * To convert the InputStream to String we use the BufferedReader.readLine()
             * method. We iterate until the BufferedReader return null which means
             * there's no more data to read. Each line will appended to a StringBuilder
             * and returned as String.
             */
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
}