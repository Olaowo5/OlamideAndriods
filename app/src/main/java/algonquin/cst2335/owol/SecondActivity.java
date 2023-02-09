package algonquin.cst2335.owol;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();

        String EmailA = fromPrevious.getStringExtra("EmailAddress:");
        TextView viewText =  findViewById(R.id.textView);

        viewText.setText(EmailA);
        HandleActivity();
        //ClickHandler();

        ActivityResultLauncher<Intent> cameraResult = HandleActivity();
        String FileName = "PictureOla.png";


        //------Check for file------//
        ImageView profileImage= findViewById(R.id.imageView);
        File file = new File(getFilesDir(),FileName);
        if(file.exists())
        {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            profileImage.setImageBitmap(theImage);
        }


        //-----Load Phone Number-----
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        String SavedPhoneNo = prefs.getString("PhoneNo", "");
        EditText PhoneInput = findViewById(R.id.editTextPhone);
        PhoneInput.setText(SavedPhoneNo);

        //--- Handle Image Button
        Button imageBut = findViewById(R.id.butpic);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageBut.setOnClickListener(clk ->{
            cameraResult.launch(cameraIntent);
        });

        //-- Handle Phone Call Button
        Button PhoneBut = findViewById(R.id.buttcall);
        PhoneBut.setOnClickListener(clk ->{
            OnCall(PhoneInput.getText().toString());
        });
    }



    protected ActivityResultLauncher<Intent> HandleActivity()
        {
            ImageView profileImage= findViewById(R.id.imageView);





            ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if(result.getResultCode() == Activity.RESULT_OK)
                            {
                                Intent data = result.getData();
                                Bitmap thumbnail = data.getParcelableExtra("data");

                                profileImage.setImageBitmap(thumbnail);

                                //catch --- all
                                //save the file

                                FileOutputStream  fOut = null;
                                try
                                {
                                    fOut = openFileOutput("PictureOla.png", Context.MODE_PRIVATE);

                                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                                    fOut.flush();

                                    fOut.close();
                                }//end try
                                catch (FileNotFoundException f)
                                {
                                    f.printStackTrace();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }

            );

            return cameraResult;
        }


    @Override
    protected void onPause() {
        super.onPause();
        EditText PhoneInput = findViewById(R.id.editTextPhone);

        String PhoneNo = PhoneInput.getText().toString();

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNo", PhoneNo);

        editor.apply();
    }

    protected void OnCall(String Cp)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + Cp));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(SecondActivity.this, "No app found to handle this request", Toast.LENGTH_SHORT).show();
        }
    }
}