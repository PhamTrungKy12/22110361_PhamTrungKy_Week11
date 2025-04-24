package com.example.videoshortwithfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.callback.ErrorInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BeginActivity extends AppCompatActivity {
    private static final int REQUEST_VIDEO_PICK = 101;
    private Button btnupload;
    private Button btnWatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_begin);

        MediaManager.init(this, new HashMap<String, String>() {{
            put("cloud_name", "dpz4iaxtp");
            put("api_key", "518293479688826");
            put("api_secret", "fbBPIsHGdu47QWMcM0PGgHyfseE");
            put("secure", "true");
        }});

        AnhXa();
        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVideoPicker();
            }
        });
    }
    public void AnhXa(){
        btnupload = (Button) findViewById(R.id.btnupload);
        btnWatch = (Button) findViewById(R.id.btnWatch);

    }
    private void openVideoPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*"); // sửa đúng mime type
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Chọn video"), REQUEST_VIDEO_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_VIDEO_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedVideoUri = data.getData();
            uploadVideoToCloudinary(selectedVideoUri);
        }
    }

    private void uploadVideoToCloudinary(Uri videoUri) {
        MediaManager.get().upload(videoUri)
                .option("resource_type", "video")
                .callback(new UploadCallback() {
                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String videoUrl = resultData.get("secure_url").toString();
                        Log.d("Upload", "Upload thành công: " + videoUrl);

                        // Sau khi upload thành công, lưu vào Firebase nếu muốn
                        saveToFirebase(videoUrl);
                    }

                    @Override public void onError(String requestId, ErrorInfo error) {
                        Log.e("Upload", "Lỗi upload: " + error.getDescription());
                    }

                    @Override public void onStart(String requestId) {}
                    @Override public void onProgress(String requestId, long bytes, long totalBytes) {}
                    @Override public void onReschedule(String requestId, ErrorInfo error) {}
                })
                .dispatch();
    }

    private void saveToFirebase(String videoUrl) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("videos");
        ref.push().setValue(new HashMap<String, Object>() {{
            put("desc", "Được upload từ điện thoại");
            put("title", "Video từ người dùng");
            put("url", videoUrl);
        }});
    }
}