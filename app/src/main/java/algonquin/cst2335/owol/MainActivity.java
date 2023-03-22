package algonquin.cst2335.owol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import algonquin.cst2335.owol.databinding.ActivityMainBinding;

/**
 * @author Olamide Owolabi
 * @version 1.0.0.1
 */
public class MainActivity extends AppCompatActivity {

    protected String cityName;
   protected RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );

        binding.forecastButton.setOnClickListener(click -> {
            cityName = binding.cityfield.getText().toString();


            String stringURL = null;
            try {
                stringURL = URLEncoder.encode(String.valueOf(cityName), StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            String URL = null;
            URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=" + stringURL + "&api-key=972f4a9632b1de8f2d0f4037996c1e53&units=metric";

            /*
            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        URLEncoder.encode(cityName,"UTF-8")
                        +"&appid={972f4a9632b1de8f2d0f4037996c1e53&units=metric}";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
*/
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                    (response)->{},
                    (error)->{ } );

            queue.add(request);

        });
    }
}