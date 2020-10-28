package kr.ac.hit.crudmembertest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MemberViewActivity extends AppCompatActivity {
    private static final String TAG = "MemberViewActivity";

    private EditText idEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private EditText itqidEditText;
    private EditText sexEditText;
    private EditText hobbyEditText;
    private EditText phoneEditText;
    private EditText hphoneEditText;
    private EditText zipcode1EditText;
    private EditText zipcode2EditText;
    private EditText addressEditText;
    private EditText addressetcEditText;

    private Button changeButton;
    private Button deleteButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberview);

        int userPosition = getIntent().getExtras().getInt("userPosition");
        boolean isCreateMode = getIntent().getExtras().getBoolean("isCreateMode");

        JSONObject jsonObject = null;
        try {
            jsonObject = MainActivity.memberListDataArray.getJSONObject(userPosition);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        idEditText = (EditText)findViewById(R.id.user_id);
        passwordEditText = (EditText)findViewById(R.id.user_password);
        nameEditText = (EditText)findViewById(R.id.user_name);
        itqidEditText = (EditText)findViewById(R.id.user_itqid);
        sexEditText = (EditText)findViewById(R.id.user_sex);
        hobbyEditText = (EditText)findViewById(R.id.user_hobby);
        phoneEditText = (EditText)findViewById(R.id.user_phone);
        hphoneEditText = (EditText)findViewById(R.id.user_hphone);
        zipcode1EditText = (EditText)findViewById(R.id.user_zipcode1);
        zipcode2EditText = (EditText)findViewById(R.id.user_zipcode2);
        addressEditText = (EditText)findViewById(R.id.user_address);
        addressetcEditText = (EditText)findViewById(R.id.user_address2);

        changeButton = (Button)findViewById(R.id.button_change);
        deleteButton = (Button)findViewById(R.id.button_delete);

        if (isCreateMode) {
            changeButton.setText("Create DATA");
        }

        if (jsonObject != null && !isCreateMode) {
            try {
                idEditText.setText(jsonObject.getString("id"));
                passwordEditText.setText(jsonObject.getString("pswd"));
                nameEditText.setText(jsonObject.getString("name"));
                itqidEditText.setText(jsonObject.getString("itqid"));
                sexEditText.setText(jsonObject.getString("sex"));
                hobbyEditText.setText(jsonObject.getString("hobby"));
                phoneEditText.setText(jsonObject.getString("phone"));
                hphoneEditText.setText(jsonObject.getString("hphone"));
                zipcode1EditText.setText(jsonObject.getString("zipcode1"));
                zipcode2EditText.setText(jsonObject.getString("zipcode2"));
                addressEditText.setText(jsonObject.getString("address"));
                addressetcEditText.setText(jsonObject.getString("address_etc"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            idEditText.setText("");
            passwordEditText.setText("");
            nameEditText.setText("");
            itqidEditText.setText("");
            sexEditText.setText("");
            hobbyEditText.setText("");
            phoneEditText.setText("");
            hphoneEditText.setText("");
            zipcode1EditText.setText("");
            zipcode2EditText.setText("");
            addressEditText.setText("");
            addressetcEditText.setText("");
        }

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCreateMode) {
                    createFunction();
                } else {
                    changeFunction();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCreateMode) {
                    Toast.makeText(getApplicationContext(), "생성 모드에선 지울 수 없습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberViewActivity.this);
                    builder.setTitle("정말 삭제 하시겠습니까?");
                    builder.setMessage("삭제 시 내용은 사라집니다.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteFunction();
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }
            }
        });

    }

    public void createFunction() {

    }

    public void changeFunction() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://114.71.61.251/~sjjang/member/android/update.php").newBuilder();
            urlBuilder.addQueryParameter("id", idEditText.getText().toString());
            urlBuilder.addQueryParameter("pswd", passwordEditText.getText().toString());
            urlBuilder.addQueryParameter("name", nameEditText.getText().toString());
            urlBuilder.addQueryParameter("itqid", itqidEditText.getText().toString());
            urlBuilder.addQueryParameter("sex", sexEditText.getText().toString());
            urlBuilder.addQueryParameter("hobby", hobbyEditText.getText().toString());
            urlBuilder.addQueryParameter("phone", phoneEditText.getText().toString());
            urlBuilder.addQueryParameter("hphone", hphoneEditText.getText().toString());
            urlBuilder.addQueryParameter("zipcode1", zipcode1EditText.getText().toString());
            urlBuilder.addQueryParameter("zipcode2", zipcode2EditText.getText().toString());
            urlBuilder.addQueryParameter("address", addressEditText.getText().toString());
            urlBuilder.addQueryParameter("address_etc", addressetcEditText.getText().toString());

            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG, "----- Change Func " + e + " -----");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        String data = response.body().string();

                        if (data != null) {
                            if (data.contains("success")) {
                                makeToast("변경 되었습니다.");
                                finish();

                            } else if (data.contains("failed update")) {
                                makeToast("생성이 되지 않았습니다.");

                            } else if (data.contains("no id")) {
                                makeToast("아이디 항목이 없습니다.");

                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFunction() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://114.71.61.251/~sjjang/member/android/delete.php").newBuilder();
            urlBuilder.addQueryParameter("id", idEditText.getText().toString());

            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG, "----- Delete Func " + e + " -----");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        String data = response.body().string();

                        if (data != null) {
                            if (data.contains("success")) {
                                makeToast("삭제 되었습니다.");
                                finish();

                            } else {
                                makeToast("삭제가 되지 않았습니다.");
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeToast(String desc) {
        if (desc != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Toast toast = Toast.makeText(getApplicationContext(), desc, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
