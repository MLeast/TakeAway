package top.dccif.takeawayapk.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import top.dccif.takeawayapk.MainActivity;
import top.dccif.takeawayapk.R;
import top.dccif.takeawayapk.TopBar;
import top.dccif.takeawayapk.util.UserUtils;
import top.dccif.takeawayapk.util.Utils;

public class SignupActivity extends Activity {

    private UserSignupTask mSignUpTask = null;
    TopBar mTopBar;
    EditText mUsername, mPassword,mConfirm;
    Button mSignUp;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Utils.setStatusBarColor(SignupActivity.this, Color.rgb(0,0,0),true);

        mTopBar = (TopBar) findViewById(R.id.topbar);
        mTopBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void titleClick() {
                finish();
            }

            @Override
            public void menuClick() {
                //Toast.makeText(SignupActivity.this,"You clicked the menu button",Toast.LENGTH_SHORT).show();
            }
        });
        //mTopBar.setMenuButtonVisible(false);

        mUsername = (EditText) findViewById(R.id.username);
        Drawable ic_user = getResources().getDrawable(R.mipmap.ic_user,null);
        if (ic_user!=null)
            ic_user.setBounds(0,0,50,50);
        mUsername.setCompoundDrawables(ic_user,null,null,null);

        mPassword = (EditText) findViewById(R.id.password);
        Drawable ic_pass = getResources().getDrawable(R.mipmap.ic_pass,null);
        if (ic_pass!=null)
            ic_pass.setBounds(0,0,50,50);
        mPassword.setCompoundDrawables(ic_pass,null,null,null);

        mConfirm = (EditText) findViewById(R.id.password_confirm);
        Drawable ic_confirm = getResources().getDrawable(R.mipmap.ic_pass,null);
        if (ic_confirm!=null)
            ic_confirm.setBounds(0,0,50,50);
        mConfirm.setCompoundDrawables(ic_confirm,null,null,null);

        mSignUp = (Button) findViewById(R.id.sign_up_button);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SignupActivity","Attempt Sign up");
                attemptSignup();
            }
        });
    }

    /**
     * Attempt to signup.
     * Before send request to server,check the fields. if fields not valid,cancel the signup process.
     */

    private void attemptSignup() {

        boolean cancel=false;
        View focusView=null;

        //reset error
        mUsername.setError(null);
        mPassword.setError(null);
        mConfirm.setError(null);

        // Store values at the time of the login attempt.
        String account = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String confirm = mConfirm.getText().toString();

        //check account
        if (TextUtils.isEmpty(account)){
            mUsername.setError(getString(R.string.error_field_required));
            focusView=mUsername;
            cancel=true;
        }
        else if (!UserUtils.isUsernameValid(account))
        {
            mUsername.setError(getString(R.string.error_invalid_account));
            focusView=mUsername;
            cancel=true;
        }

        //check password

        if (TextUtils.isEmpty(password)){
            mPassword.setError(getString(R.string.error_field_required));
            focusView=mPassword;
            cancel=true;
        }
        else if (!UserUtils.isPasswordValid(password)){
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView=mPassword;
            cancel=true;
        }

        //check confirm
        if (TextUtils.isEmpty(confirm)){
            mConfirm.setError(getString(R.string.error_field_required));
            focusView=mConfirm;
            cancel=true;
        }
        else if (!password.equals(confirm)){
            mConfirm.setError(getString(R.string.error_password_different));
            focusView=mConfirm;
            cancel=true;
        }

        if (cancel){
            focusView.requestFocus();
        }
        else {
            //start login progress
            Log.d("SignupActivity", "Start signup");

            mSignUpTask = new UserSignupTask(account,password);
            mSignUpTask.execute();
        }

    }

    private void showProgress(boolean show){
        if (show) {
            dialog=new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setTitle("注册中...");
            dialog.setMessage("请稍等");
            dialog.show();
        }
        else {
            dialog.hide();
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserSignupTask extends AsyncTask<Void, Void, Boolean> {

        private final String email;
        private final String password;

        UserSignupTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result=false;
            try {
                result = UserUtils.signup(email,password);
            } catch (Exception e) {
                return false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSignUpTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                startActivity(intent);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setMessage(getString(R.string.error_username_occupied));
                builder.setPositiveButton("确定",null);
                builder.show();
                mUsername.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mSignUpTask = null;
            showProgress(false);
        }
    }
}
