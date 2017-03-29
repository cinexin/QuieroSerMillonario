package upv.ejercicios.proyectofinal.quierosermillonario.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;

/**
 * Created by migui on 0028.
 */

public class HttpUtils {
    Context context;

    public HttpUtils() {
    }

    public HttpUtils(Context context) {
        this.context = context;
    }

    protected boolean checkConnectionAvailableAndAlive(int connectionType, NetworkInfo connectionInfo) {
        Logging logging = new Logging();
        boolean connectionOK = false;
        String connectionTypeLogLineAvailable = "";
        String connectionTypeLogLineNotAvailable = "";

        switch (connectionType) {
            case AppConstants.CONNECTION_TYPE_3G:
                connectionTypeLogLineAvailable = this.getClass().getName() + " 3G/4G connection available";
                connectionTypeLogLineNotAvailable = this.getClass().getName() + " 3G/4G connection NOT available";
                break;

            case AppConstants.CONNECTION_TYPE_WIFI:
                connectionTypeLogLineAvailable = this.getClass().getName() + " WIFI connection available";
                connectionTypeLogLineNotAvailable = this.getClass().getName() + " WIFI connection NOT available";
            default:
                logging.error("Connection " + connectionType + " unknown. Known types = 3G (" + AppConstants.CONNECTION_TYPE_3G + ") " +
                        " / WiFi ( " + AppConstants.CONNECTION_TYPE_WIFI + ")");
                break;
        }

        if (connectionInfo != null &&
                connectionInfo.isAvailable() &&
                connectionInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            connectionOK = true;
            logging.info(connectionTypeLogLineAvailable);
        } else {
            connectionOK = false;
            logging.info(connectionTypeLogLineNotAvailable);
        }

        return connectionOK;
    }

    public void postRequest(String baseURL, String httpMethod, List<NameValuePair> pairs) throws IOException {
        Logging logging = new Logging();
        String completeURL = baseURL + "?" + URLEncodedUtils.format(pairs, "utf-8");


        URL url = new URL(completeURL);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(httpMethod);
            conn.setDoOutput(true);
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(completeURL);
            writer.close();

        } catch (IOException ioEx) {
            logging.error(this.getClass().getName() + ". exception while performing postRequest: " + ioEx.getMessage() );
            throw ioEx;
        }

    }

    public boolean checkInternetConnection() {
        Logging logging = new Logging();
        if (this.context == null) {
            logging.error("HttpUtils.checkInternetConnection ERROR: Context not initialized. ");
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo conn3GInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo connWifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (checkConnectionAvailableAndAlive(AppConstants.CONNECTION_TYPE_3G, conn3GInfo)) {
                return true;
            }

            if (checkConnectionAvailableAndAlive(AppConstants.CONNECTION_TYPE_WIFI, connWifiInfo)) {
                return true;
            }

            return false;
        } else {
            return false;
        }

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
