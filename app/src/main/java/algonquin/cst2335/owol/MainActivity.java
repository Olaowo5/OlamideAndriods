package algonquin.cst2335.owol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

   protected  Bitmap ImagOla = null;
   protected String imageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());



        runOnUiThread( (  )  -> {
            binding.temp.setVisibility(View.INVISIBLE);


            binding.Mintemp.setVisibility(View.INVISIBLE);


            binding.Maxtemp.setVisibility(View.INVISIBLE);


            binding.humidity.setVisibility(View.INVISIBLE);


            binding.description.setVisibility(View.INVISIBLE);


            //binding.icon.setImageBitmap(ImagOla);
            binding.icon.setVisibility(View.INVISIBLE);
            // do this for all the textViews...

        });

        binding.forecastButton.setOnClickListener(click -> {
            cityName = binding.cityfield.getText().toString();


            String stringURL = null;
            try {
                stringURL = URLEncoder.encode(String.valueOf(cityName), StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }





            String URL = null;
            //https://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=${KeyName}
            URL = "https://api.openweathermap.org/data/2.5/weather?q=" + stringURL + "&units=metric&appid=972f4a9632b1de8f2d0f4037996c1e53";

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
                    (response)->{

                try {
                    JSONObject coord = response.getJSONObject("coord");

                    JSONArray weatherArray = response.getJSONArray("weather");
                    JSONObject position0 = weatherArray.getJSONObject(0);

                    String descrip = position0.getString("description");
                    String iconName = position0.getString("icon");


                    JSONObject mainObject = response.getJSONObject("main");
                    double current = mainObject.getDouble("temp");
                    double min = mainObject.getDouble("temp_min");
                    double max = mainObject.getDouble("temp_max");
                    int humidity = mainObject.getInt("humidity");


                    int vis = response.getInt("visibility");
                    String name = response.getString("name");

                    //Binding Section

                   

                   
                    //IMAGE

                    String pathName = getFilesDir() + "/" + iconName + ".png";
                    File file = new File(pathName);

                    if (file.exists()) {
                        ImagOla = BitmapFactory.decodeFile(pathName);
                    }
                    else {
                        imageUrl = "https://openweathermap.org/img/w/" + iconName + ".png";


                       // Bitmap finalImage = imagOla;
                        ImageRequest imgReq = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                // Do something with loaded bitmap...
                                    String lp = "Kp";
                                  //  FileOutputStream fOut = null;
                                    try {
                                      //  fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                        ImagOla = bitmap;

                                        Bitmap temp = bitmap;
                                        ImagOla.compress(Bitmap.CompressFormat.PNG, 100,
                                                MainActivity.this.openFileOutput(iconName+".png", Activity.MODE_PRIVATE));
                                       // fOut.flush();
                                      //  fOut.close();
                                        
                                       
                                    } catch (IOException e) {
                                        e.printStackTrace();

                                    }

                            }
                        }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {

                        });

                        queue.add(imgReq);

                    }



                    runOnUiThread( (  )  -> {

                        binding.temp.setText("The Current Temperature is "+ current);
                        binding.temp.setVisibility(View.VISIBLE);

                        binding.Mintemp.setText("The Minimum Temperature is " + min);
                        binding.Mintemp.setVisibility(View.VISIBLE);

                        binding.Maxtemp.setText("The Maximum Temeperature is " + max);
                        binding.Maxtemp.setVisibility(View.VISIBLE);

                        binding.humidity.setText("The Humidity is " + humidity);
                        binding.humidity.setVisibility(View.VISIBLE);

                        binding.description.setText("Description : " + descrip);
                        binding.description.setVisibility(View.VISIBLE);


                        binding.icon.setImageBitmap(ImagOla);
                        binding.icon.setVisibility(View.VISIBLE);
                        // do this for all the textViews...

                    });
                }
                catch ( JSONException e) {
                    throw new RuntimeException(e);
                }

                    },
                    (error)->{ } );

            queue.add(request);
            
            
            

        });
    }



}