package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;


import model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullname;
    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private EditText homepage;
    private EditText about;
    private ImageView image_profile;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.text_fullname);
        email = findViewById(R.id.text_email);
        password = findViewById(R.id.text_password);
        confirm_password = findViewById(R.id.text_confirm_password);
        homepage = findViewById(R.id.text_homepage);
        about = findViewById(R.id.text_about);
        image_profile = findViewById(R.id.img_profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        image_profile.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                image_profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE){
            if (data != null){
                try{
                    Uri imageUri = data.getData();
                    Bitmap bitmap =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    image_profile.setImageBitmap(bitmap);
                } catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public static boolean isValidEmail(CharSequence email) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    public static boolean isValidUrl(CharSequence url){
        return (Patterns.WEB_URL.matcher(url).matches());
    }

    public void handleOk(View view) {
        if(TextUtils.isEmpty(email.getText().toString().trim())){
            Toast.makeText(view.getContext(), "Email tidak boleh kosong!", Toast.LENGTH_LONG).show();
        }
        // Validasi inputan tipe email
        else if(!isValidEmail(email.getText().toString().trim())) {
            Toast.makeText(view.getContext(), "Email tidak valid!", Toast.LENGTH_LONG).show();
        }
        else if(!isValidUrl(homepage.getText().toString().trim())){
            Toast.makeText(view.getContext(), "Homepage Tidak Valid", Toast.LENGTH_SHORT).show();
        }
        else {
            String fullnameText = fullname.getText().toString();
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            String confirmText = confirm_password.getText().toString();
            String homepageText = homepage.getText().toString();
            String aboutText = about.getText().toString();

            if(!(fullnameText).equals("") && !(emailText).equals("") && !(passwordText).equals("") && !(confirmText).equals("") && !(homepageText).equals("") && !(aboutText).equals("")){
                if((passwordText).equals(confirmText)){
                    Intent intent = new Intent(this,ProfileActivity.class);
                    image_profile.buildDrawingCache();
                    Bitmap image= image_profile.getDrawingCache();
                    Bundle extras = new Bundle();
                    extras.putParcelable("IMAGE_KEY", image);
                    intent.putExtras(extras);

                    intent.putExtra("FULLNAME_KEY", fullnameText);
                    intent.putExtra("EMAIL_KEY", emailText);
                    intent.putExtra("PASSWORD_KEY", passwordText);
                    intent.putExtra("CONFIRM_KEY", confirmText);
                    intent.putExtra("HOMEPAGE_KEY", homepageText);
                    intent.putExtra("ABOUT_KEY", aboutText);
                    startActivity(intent);
                }
                else if(TextUtils.isEmpty(email.getText().toString().trim())){
                    Toast.makeText(view.getContext(), "Email tidak boleh kosong!", Toast.LENGTH_LONG).show();
                }
                else if(!isValidEmail(email.getText().toString().trim())) {
                    Toast.makeText(view.getContext(), "Email tidak valid!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, "Password dan Confirm password harus sama !",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Tolong isi semua data !",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public void handleChangeAvatar(View view) {
        selectImage(RegisterActivity.this);
    }
}