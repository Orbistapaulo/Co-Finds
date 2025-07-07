    package com.example.androidapplication;
    import android.content.ContentResolver;
    import android.content.Context;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.Canvas;
    import android.graphics.Color;
    import android.graphics.Paint;
    import android.graphics.Path;
    import android.graphics.PorterDuff;
    import android.graphics.PorterDuffXfermode;
    import android.graphics.Rect;
    import android.graphics.RectF;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.view.View;
    import android.webkit.MimeTypeMap;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.Toast;
    import androidx.activity.result.ActivityResultLauncher;
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import com.bumptech.glide.Glide;
    import com.example.androidapplication.databinding.ActivityMainBinding;
    import com.github.dhaval2404.imagepicker.ImagePicker;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.OnProgressListener;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.StorageTask;
    import com.google.firebase.storage.UploadTask;
    import java.io.IOException;
    import java.util.HashMap;

    public class EditProfile extends AppCompatActivity {

        final  private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        final private StorageReference storageReference = FirebaseStorage.getInstance().getReference("profileImages");
        EditText editName, editEmail, editUsername, editPassword;
        Button saveButton;
        String nameUser, emailUser, usernameUser, passwordUser;
        DatabaseReference reference;
       private ImageView profile, back_button;
        ProgressBar progressBar;
        private Uri imageUri;
        private String myUri = "";
        private StorageTask uploadTask;
        private Context context;
        String imageUrl;
        private Uri mCropImageUri;
        ActivityResultLauncher<String> cropImage;
        ActivityMainBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_profile);
            reference = FirebaseDatabase.getInstance().getReference("users");
            back_button = findViewById(R.id.back_button);
            profile = findViewById(R.id.profileImg);
            editName = findViewById(R.id.editName);
            editEmail = findViewById(R.id.editEmail);
            editUsername = findViewById(R.id.editUsername);
            editPassword = findViewById(R.id.editPassword);
            saveButton = findViewById(R.id.saveButton);
            progressBar = findViewById(R.id.progressbar);
            progressBar.setVisibility(View.INVISIBLE);
            String newPassword = getIntent().getStringExtra("newPassword");
            if (newPassword != null) {
                reference.child(usernameUser).child("password").setValue(newPassword);
                passwordUser = newPassword;
                getIntent().removeExtra("newPassword");
            }
            showData();
            back_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), profile.class);

                    intent.putExtra("name", nameUser);
                    intent.putExtra("email", emailUser);
                    intent.putExtra("username", usernameUser);
                    intent.putExtra("password", passwordUser);
                    intent.putExtra("imageUri", imageUrl.toString());
                    startActivity(intent);
                }
            });
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isNameChanged() || isEmailChanged() || isPasswordChanged() || imageUri != null) {
                        if (isNameChanged()) {
                            reference.child(usernameUser).child("name").setValue(editName.getText().toString());
                        }
                        if (isEmailChanged()) {
                            reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
                        }
                        if (isPasswordChanged()) {
                            reference.child(usernameUser).child("password").setValue(editPassword.getText().toString());
                        }
                        if (imageUri != null) {
                            uploadToFirebase(imageUri);
                        }
                        updateUserData();
                    } else {
                        Toast.makeText(EditProfile.this, "No changes made", Toast.LENGTH_SHORT).show();
                    } }
                private void updateUserData() {
                    reference.child(usernameUser).updateChildren(new HashMap<String, Object>() {{
                        put("name", nameUser);
                        put("email", emailUser);
                        put("password", passwordUser);
                    }}).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(EditProfile.this, "Profile saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfile.this, profile.class);
                            intent.putExtra("name", nameUser);
                            intent.putExtra("email", emailUser);
                            intent.putExtra("username", usernameUser);
                            intent.putExtra("password", passwordUser);
                            intent.putExtra("imageUri", imageUrl);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(EditProfile.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImagePicker.with(EditProfile.this)
                            .crop()
                            .compress(1024)
                            .maxResultSize(1080, 1080)	
                            .start();
                }
            });
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == ImagePicker.REQUEST_CODE) {
                    imageUri = data.getData();

                    if (imageUri != null) {

                        setProfileImage(imageUri);
                    } else {
                        Toast.makeText(EditProfile.this, "Failed to get image", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(EditProfile.this, "No Image Selected", Toast.LENGTH_SHORT).show();
            }
        }
        private void setProfileImage(Uri imageUri) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                Bitmap roundedBitmap = getRoundedCornerBitmap(bitmap, 30);
                profile.setImageBitmap(roundedBitmap);
                profile.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(EditProfile.this, "Failed to set profile image", Toast.LENGTH_SHORT).show();
            }
        }

        private Bitmap getRoundedCornerBitmap(Bitmap bitmap, int cornerRadius) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float maxCornerRadius = Math.min(width, height) / 3f;
            cornerRadius = (int) Math.min(cornerRadius, maxCornerRadius);
            int rectWidth = Math.min(width, height);
            int rectHeight = Math.min(width, height);
            Bitmap output = Bitmap.createBitmap(rectWidth, rectHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, rectWidth, rectHeight);
            final RectF rectF = new RectF(rect);
            int left = (width - rectWidth) / 2;
            int top = (height - rectHeight) / 2;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.RED);
            Path path = new Path();
            path.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW);
            canvas.clipPath(path);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, left, top, rectWidth, rectHeight);
            canvas.drawBitmap(croppedBitmap, 0, 0, paint);
            return output;
        }
        public void showData() {
            Intent intent = getIntent();
            nameUser = intent.getStringExtra("name");
            emailUser = intent.getStringExtra("email");
            usernameUser = intent.getStringExtra("username");
            passwordUser = intent.getStringExtra("password");
            editName.setText(nameUser);
            editEmail.setText(emailUser);
            editUsername.setText(usernameUser);
            editPassword.setText(passwordUser);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                handleDownloadUrl(imageUrl);
            }

            DatabaseReference imageReference = databaseReference.child(usernameUser).child("profileImageUrl");
            imageReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        imageUrl = snapshot.getValue(String.class);
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            handleDownloadUrl(imageUrl);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });

        }
        private boolean isNameChanged() {
            if (!nameUser.equals(editName.getText().toString())){
                reference.child(usernameUser).child("name").setValue(editName.getText().toString());
                nameUser = editName.getText().toString();
                return true;
            } else {
                return false;
            }
        }
        public boolean isEmailChanged(){
            if (!emailUser.equals(editEmail.getText().toString())){
                reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
                emailUser = editEmail.getText().toString();
                return true;
            } else {
                return false;
            }
        }
        private boolean isPasswordChanged() {
            if (!passwordUser.equals(editPassword.getText().toString())){
                reference.child(usernameUser).child("password").setValue(editPassword.getText().toString());
                passwordUser = editPassword.getText().toString();
                return true;
            } else {
                return false;
            }
        }


        private void uploadToFirebase(Uri uri) {
            final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

            imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            databaseReference.child(usernameUser).child("profileImageUrl").setValue(downloadUri.toString());
                            updateUserData(downloadUri.toString());
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(EditProfile.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void updateUserData(String imageUri) {
            nameUser = editName.getText().toString();
            emailUser = editEmail.getText().toString();
            passwordUser = editPassword.getText().toString();
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(EditProfile.this, "Profile saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditProfile.this, profile.class);
            intent.putExtra("name", nameUser);
            intent.putExtra("email", emailUser);
            intent.putExtra("username", usernameUser);
            intent.putExtra("password", passwordUser);
            intent.putExtra("imageUri", imageUri);
            startActivity(intent);
        }

        private String getFileExtension(Uri fileUri){
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
        }
        private void handleDownloadUrl(String downloadUrl) {

            Glide.with(this)
                    .load(downloadUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(profile);
        }

    }

