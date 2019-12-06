package com.example.rizk.pharmacysystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import static com.example.rizk.pharmacysystem.R.layout.login;

public class Login extends AppCompatActivity {

    Button singin;
    EditText email,password;
    TextView signintxt, signuptxt, forgot_password, signin_signup_txt;
    CircleImageView circleImageView;
    HttpJsonParser jParser = new HttpJsonParser();
    JSONArray userArr = null;
    User user;
    boolean signinBool = true;
    private Login self;

    AlertDialog alertDialog;
    boolean finished = true;
    String msg;
    boolean checksingin, checksingup;

    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(login);
        self = this;


        init ();
        setAction();

    }

    private void init() {
        singin = (Button) findViewById(R.id.signin_btn);
        email = (EditText) findViewById(R.id.email);
        signintxt = (TextView) findViewById(R.id.signin);
        signuptxt = (TextView) findViewById(R.id.signup);
        password = (EditText) findViewById(R.id.password);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        signin_signup_txt = (TextView) findViewById(R.id.signin_signup_txt);
        checksingin=false;
        checksingup=false;
    }


    private void setAction() {
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                background bg = new background(Login.this);
//                bg.execute(email.getText().toString(), password.getText().toString());
                alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                pDialog = new ProgressDialog(Login.this);
                pDialog.setMessage("Login In. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
                if(email.getText().toString().equals("")||password.getText().toString().equals(""))
                    Toast.makeText(Login.this, "email is already exist",Toast.LENGTH_LONG).show();
                 else {
                    if (signinBool) {
                        signinSuccessful(email.getText().toString(), password.getText().toString());
                        Log.e("zzzzzzzzzz", "return trueeeeeeeeeeeeeeeeee");
                        if (checksingin) {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            Log.e("zzzzzzzzzz", "return true");
                            pDialog.dismiss();
                            checksingin=false;
                            checksingup=false;
                            startActivity(intent);

                        } else {
                            Log.e("zzzzzzzzzz", "return false");

                            checksingin=false;
                            checksingup=false;
                            pDialog.dismiss();
                            Toast.makeText(Login.this, "Email or Password is invalid", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        signupSuccessful(email.getText().toString(), password.getText().toString());
                        if (checksingup) {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            pDialog.dismiss();
                            checksingin=false;
                            checksingup=false;
                            startActivity(intent);

                        } else {

                            checksingin=false;
                            checksingup=false;
                            pDialog.dismiss();
                            Toast.makeText(Login.this, "email is already exist", Toast.LENGTH_LONG).show();
                        }
                    }
                }
//                Intent intent = new Intent(Login.this,MainActivity.class);
//                startActivity(intent);
//                new Login.LoginIt().execute(new String[]{email.getText().toString(), password.getText().toString()});
            }


        });

        signintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signintxt.setTextColor(Color.parseColor("#FFFFFF"));
                signintxt.setBackgroundColor(Color.parseColor("#26A69A"));
                signuptxt.setTextColor(Color.parseColor("#FF2729C3"));
                signuptxt.setBackgroundResource(R.drawable.bordershape);
//                circleImageView.setImageResource(R.drawable.sigin_boy_img);
                signin_signup_txt.setText("Sign In");
                signintxt.setText("Sign In");
                singin.setText("Sign In");
                forgot_password.setVisibility(View.VISIBLE);
                signinBool = true;
                email.setText("");
                password.setText("");
                checksingin=false;
                checksingup=false;
            }
        });
        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signuptxt.setTextColor(Color.parseColor("#FFFFFF"));
                signuptxt.setBackgroundColor(Color.parseColor("#26A69A"));
                signintxt.setTextColor(Color.parseColor("#FF2729C3"));
                signintxt.setBackgroundResource(R.drawable.bordershape);
//                circleImageView.setImageResource(R.drawable.sigup_boy_img);
                singin.setText("Sign Up");
                signintxt.setText("Sign In");
                signin_signup_txt.setText("Sign Up");
                signinBool = false;
                forgot_password.setVisibility(View.INVISIBLE);
                email.setText("");
                password.setText("");
                checksingin=false;
                checksingup=false;
            }
        });
    }

    private void signupSuccessful(String emailAddress, String passwordUser){

        String text = emailAddress+" "+passwordUser;
        FileOutputStream fos = null;
        signinSuccessful(emailAddress,passwordUser);
        if(!checksingin)
            checksingup = true;

        try {
            if(checksingup){
                fos = openFileOutput(FILE_NAME, MODE_APPEND);
                text = text + "\n";
                fos.write(text.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void signinSuccessful(String emailAddress, String passwordUser) {
        FileInputStream fis = null;
        Log.e("xxxxx",emailAddress+".."+passwordUser);
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null)
                sb.append(text).append("\n");

            text = sb.toString();
            String lines[] = text.split("\\r?\\n");
            for (String l : lines) {
                String[] splited = l.split("\\s+");
                Log.e("ffffffff",splited[0]+"...."+splited[1]);
                Log.e("hhhhhh",(emailAddress.equals(splited[0]) && passwordUser.equals(splited[1]))+"....");
                if (emailAddress.equals(splited[0]) && passwordUser.equals(splited[1]))
                    checksingin = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Background Async Task to Load all books by making HTTP Request
     * */
    class LoginIt extends AsyncTask<String, String, String> {

        AlertDialog alertDialog;
        boolean finished = true;
        String msg;

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(Login.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Login In. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            params.put("username", args[0]);
            params.put("password", args[1]);
            JSONObject json;
            // getting JSON string from URL
            Log.e("Success equal= ", "1111111111111111111");

            json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/loginUser.php", "GET", params);
//            json = jParser.makeHttpRequest("http://localhost/lampp/Android_DB_connect/loginUser.php", "GET", params);
            Log.e("Success equal= ", "2222222222222222222");
            Log.e("Success equal=", json + "");

            try {
                // Checking for SUCCESS TAG
                Log.e("Success equal= ", "33333333333333333");
                int success = json.getInt("success");
                Log.e("Success equal= ", ""+success);
                Log.e("Success equal= ", "44444444444444444");
                if (success == 1) {
                    userArr = json.getJSONArray("user");
                    finished = true;
                    JSONObject c = userArr.getJSONObject(0);
                    String username = c.getString("userName");
                    String password = c.getString("Password");
                    String firstN = c.getString("firstName");
                    String lastN = c.getString("lastName");
                    String email = c.getString("Email_Address");
                    String phone = c.getString("phoneNumber");
                    user = new User(email, username, firstN, lastN, password, phone);
                } else  {
                    finished = false;
                    msg = json.getString("msg");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
//            pDialog.dismiss();
            if(finished) {
                // updating UI from Background Thread
                Intent intent = new Intent(Login.this, DrugActivity.class);
//                intent.putExtra("user", user);
//                intent.putExtra("pri", priority);
                startActivity(intent);
            } else  {
                runOnUiThread(new Runnable() {
                    public void run() {
                        email.setText("");
                        password.setText("");
                    }
                });
//                alertDialog.setMessage(msg);
//                alertDialog.show();
            }
        }
    }
    private static final String FILE_NAME = "User.txt";

}
