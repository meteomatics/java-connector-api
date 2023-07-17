// run on the command line as:
// javac MeteomaticsExample.java
// java MeteomaticsExample username password
​
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.net.URI;
import java.nio.charset.StandardCharsets;
​
public class MeteomaticsExample {
    public static void main(String[] args) throws Exception {
        try {
            String username = args[0];
            String password = args[1];
            System.out.print("username: " + username + ", password: " + password + "\n");
            URI uri = new URI("https://api.meteomatics.com/todayT00Z/wind_speed_mean_10m_6h:kmh,wind_dir_mean_10m_6h:d/42,-36+41,-36/json");
            URL url = uri.toURL();
            String credentials = username + ":" + password;
            String encoding = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
​
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + encoding);
​
            if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
​
            BufferedReader streamReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
​
            StringBuilder responseStrBuilder = new StringBuilder();
​
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
            }
​
            System.out.print(responseStrBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
