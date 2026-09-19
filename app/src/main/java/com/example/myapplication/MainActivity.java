package com.example.myapplication;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;
    private TextView tvForgotPassword;
    private TextView tvSignUp;
    private ImageButton btnGoogle;
    private ImageButton btnFacebook;
    private ImageButton btnGithub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnGithub = findViewById(R.id.btnGithub);

        // Format "Don't have an account? Sign Up" text
        String signUpText = getString(R.string.dont_have_account_sign_up);
        tvSignUp.setText(Html.fromHtml(signUpText, Html.FROM_HTML_MODE_LEGACY));
    }

    // Inside your Activity
    private void hideKeyboard() {
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();

        // If no view is focused, fallback to the decor view so we have a valid window token
        if (view == null) {
            view = getWindow().getDecorView();
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && view.getWindowToken() != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    // We MUST hide the keyboard BEFORE clearing focus!
                    hideKeyboard();
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void checkFieldsForEmptyValues() {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        // Enable button only if both fields are not empty
        btnLogin.setEnabled(!email.isEmpty() && !password.isEmpty());
    }

    private void setupListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Attach the watcher to both input fields
        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);

        // Run an initial check to disable the button when the app starts
        checkFieldsForEmptyValues();

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

            Toast.makeText(this, "Logging in as " + email + "...", Toast.LENGTH_SHORT).show();

            // Clear the text fields after successful submission
            etEmail.setText("");
            etPassword.setText("");
            
            // Clear focus and hide the keyboard
            etEmail.clearFocus();
            etPassword.clearFocus();
            hideKeyboard();
        });

        tvForgotPassword.setOnClickListener(v ->
            Toast.makeText(this, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
        );

        tvSignUp.setOnClickListener(v ->
            Toast.makeText(this, "Sign Up clicked", Toast.LENGTH_SHORT).show()
        );

        btnGoogle.setOnClickListener(v ->
            Toast.makeText(this, "Google login clicked", Toast.LENGTH_SHORT).show()
        );

        btnFacebook.setOnClickListener(v ->
            Toast.makeText(this, "Facebook login clicked", Toast.LENGTH_SHORT).show()
        );

        btnGithub.setOnClickListener(v ->
            Toast.makeText(this, "GitHub login clicked", Toast.LENGTH_SHORT).show()
        );
    }
}
