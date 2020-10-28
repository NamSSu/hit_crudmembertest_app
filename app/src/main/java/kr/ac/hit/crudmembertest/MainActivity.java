package kr.ac.hit.crudmembertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static boolean isReadFirst = false;

    public static JSONArray memberListDataArray = new JSONArray();

    private MemberListAdapter memberListAdapter;

    private ListView memberListView;

    private Button readDataButton;
    private Button createDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readDataButton = (Button)findViewById(R.id.button_read);
        createDataButton = (Button)findViewById(R.id.button_create);

        memberListView = (ListView)findViewById(R.id.member_list_view);

        memberListAdapter = new MemberListAdapter(this);
        memberListView.setAdapter(memberListAdapter);

        Log.d(TAG, "-----" + memberListDataArray.length());

        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent memberViewIntent = new Intent(getApplicationContext(), MemberViewActivity.class);
                memberViewIntent.putExtra("userPosition", i);
                memberViewIntent.putExtra("isCreateMode", false);
                startActivity(memberViewIntent);
            }
        });

        readDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFunction();
                memberListAdapter.notifyDataSetChanged();
            }
        });

        createDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memberViewIntent = new Intent(getApplicationContext(), MemberViewActivity.class);
                memberViewIntent.putExtra("isCreateMode", true);
                startActivity(memberViewIntent);
            }
        });

    }

    public void readFunction() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://114.71.61.251/~sjjang/member/android/read.php").newBuilder();

            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG, "----- Read Func " + e + " -----");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        String data = response.body().string();

                        if (data != null) {
                            memberListDataArray = new JSONArray(data);
                            isReadFirst = true;

                            Log.d(TAG, "----- Member Data Size is : " + memberListDataArray.length());
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            memberListAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}