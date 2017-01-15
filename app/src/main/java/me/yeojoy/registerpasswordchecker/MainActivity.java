package me.yeojoy.registerpasswordchecker;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import java.util.regex.Pattern;

import me.yeojoy.registerpasswordchecker.databinding.ActivityMainBinding;
import me.yeojoy.registerpasswordchecker.utils.PasswordChecker;
import me.yeojoy.registerpasswordchecker.utils.SpannableUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        Log.i(TAG, "initViews()");

        mBinding.editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkPassword(editable);
            }
        });

        mBinding.editTextPasswordSecond.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkPasswordSecond(editable);
            }
        });

        mBinding.checkboxShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    showPassword();
                } else {
                    hidePassword();
                }
            }
        });

    }

    private void checkPasswordSecond(CharSequence password) {
        Log.i(TAG, "checkPassword()");

        if (TextUtils.isEmpty(password)) {
            mBinding.textInputLayout.setError("");
            return;
        }

        mBinding.textInputLayout.setError(SpannableUtil.getPasswordText(this, password));
    }

    private void checkPassword(CharSequence password) {
        Log.i(TAG, "checkPassword()");

        if (TextUtils.isEmpty(password)) {
            Log.e(TAG, "password is null or empty.");
            hideCheckList();
            return;
        } else {
            showCheckList();
        }

        if (PasswordChecker.isPasswordMoreThanEight(password)) {
            mBinding.textViewLeastCharCount.setTypeface(null, Typeface.BOLD);
        } else {
            mBinding.textViewLeastCharCount.setTypeface(null, Typeface.NORMAL);
        }

        if (PasswordChecker.hasCapital(password)) {
            mBinding.textViewCapitalOne.setTypeface(null, Typeface.BOLD);
        } else {
            mBinding.textViewCapitalOne.setTypeface(null, Typeface.NORMAL);
        }

        if (PasswordChecker.hasLowerCase(password)) {
            mBinding.textViewLowerCaseOne.setTypeface(null, Typeface.BOLD);
        } else {
            mBinding.textViewLowerCaseOne.setTypeface(null, Typeface.NORMAL);
        }

        if (PasswordChecker.hasNumber(password)) {
            mBinding.textViewNumberOne.setTypeface(null, Typeface.BOLD);
        } else {
            mBinding.textViewNumberOne.setTypeface(null, Typeface.NORMAL);
        }
    }

    private void showCheckList() {
        Log.i(TAG, "showCheckList()");
        mBinding.linearLayoutCheckList.setVisibility(View.VISIBLE);
    }

    private void hideCheckList() {
        Log.i(TAG, "hideCheckList()");
        mBinding.textViewLeastCharCount.setTypeface(null, Typeface.NORMAL);
        mBinding.textViewCapitalOne.setTypeface(null, Typeface.NORMAL);
        mBinding.textViewLowerCaseOne.setTypeface(null, Typeface.NORMAL);
        mBinding.textViewNumberOne.setTypeface(null, Typeface.NORMAL);

        mBinding.linearLayoutCheckList.setVisibility(View.GONE);
    }

    private void showPassword() {
        Log.i(TAG, "showPassword()");
        mBinding.editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        int length = mBinding.editTextPassword.getText().toString().length();
        mBinding.editTextPassword.setSelection(length);
    }

    private void hidePassword() {
        Log.i(TAG, "hidePassword()");
        mBinding.editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        int length = mBinding.editTextPassword.getText().toString().length();
        mBinding.editTextPassword.setSelection(length);
    }
}
