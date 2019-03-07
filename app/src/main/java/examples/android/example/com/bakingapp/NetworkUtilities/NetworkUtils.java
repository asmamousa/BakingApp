package examples.android.example.com.bakingapp.NetworkUtilities;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import examples.android.example.com.bakingapp.Constants;

final public class NetworkUtils {

  private static final String LOG_TAG=Constants.error;



    private NetworkUtils(){}

    public static URL buildUrl() {

        Uri builtUri = Uri.parse(Constants.JsonBaseUrl).buildUpon()
                .path(Constants.JsonPath)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {

            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
