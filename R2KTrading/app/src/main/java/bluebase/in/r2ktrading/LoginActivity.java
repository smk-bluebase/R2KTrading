package bluebase.in.r2ktrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Context context = this;
//    ProgressDialog progressDialog;
    ProgressBarAnimation progressBarAnimation;
    JsonObject jsonObject;
    TextInputEditText userName;
    TextInputEditText password;

    String user = "admin";

    String urlLogin = CommonUtils.IP + "/R2KTrading/r2k_trading_android/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.9);

        ImageView background = findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        userName = findViewById(R.id.userName);
        userName.setText(user);
        password = findViewById(R.id.password);
        password.setText(user);

        Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userName.getText().equals("")){
                    if(!password.getText().equals("")){
//                        progressDialog = new ProgressDialog(context);
//                        progressDialog.setCancelable(false);
//                        progressDialog.setMessage("Loading...");
//                        progressDialog.show();

                        progressBarAnimation = new ProgressBarAnimation(context);
                        progressBarAnimation.show();


                        jsonObject = new JsonObject();
                        jsonObject.addProperty("userName", userName.getText().toString());

                        MD5 md5 = new MD5();
                        jsonObject.addProperty("password", md5.getMd5(password.getText().toString()));

                        PostLogin postLogin = new PostLogin(context);
                        postLogin.checkServerAvailability(2);
                    }else{
                        Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Enter UserName", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView forgotPassword = findViewById(R.id.forgetPassword);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private class PostLogin extends PostRequest{
        public PostLogin(Context context){
            super(context);
        }

        @Override
        public void serverAvailability(boolean isServerAvailable) {
            if(isServerAvailable){
                super.postRequest(urlLogin, jsonObject);
            }else {
                Toast.makeText(getApplicationContext(),"Connection to network \nnot Available",Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                progressBarAnimation.dismiss();
            }
        }

        @Override
        public void onFinish(JSONArray jsonArray) {
//            progressDialog.dismiss();
//            progressBarAnimation.dismiss();

            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

//                if(jsonObject.getBoolean("status")){
//                    if(jsonObject.getInt("role") == 1){
//                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
//                        intent.putExtra("email", jsonObject.getString("email"));
//                        startActivity(intent);
//                    }else {
//                        Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
//                        intent.putExtra("email", jsonObject.getString("email"));
//                        startActivity(intent);
//                    }
//                }

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}