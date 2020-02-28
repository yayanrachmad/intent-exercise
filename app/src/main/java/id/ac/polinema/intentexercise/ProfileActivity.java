package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import model.User;

public class ProfileActivity extends AppCompatActivity {
    private TextView fullname, email, homepage, about;
    private ImageView image_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        image_profile = findViewById(R.id.img_profile);
        fullname = findViewById(R.id.label_fullname);
        email = findViewById(R.id.label_email);
        homepage = findViewById(R.id.label_homepage);
        about = findViewById(R.id.label_about);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String fullnameText = bundle.getString("FULLNAME_KEY");
            String emailText = bundle.getString("EMAIL_KEY");
            String homepageText = bundle.getString("HOMEPAGE_KEY");
            String aboutText = bundle.getString("ABOUT_KEY");
            Bundle extras = getIntent().getExtras();
            Bitmap bmp = (Bitmap) extras.getParcelable("IMAGE_KEY");

            image_profile.setImageBitmap(bmp );

            fullname.setText(fullnameText);
            email.setText(emailText);
            homepage.setText(homepageText);
            about.setText(aboutText);
        }
    }

    public void handleOpenHomepage(View view) {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String homepageText = bundle.getString("HOMEPAGE_KEY");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+homepageText));
            startActivity(intent);
        }
    }
}
