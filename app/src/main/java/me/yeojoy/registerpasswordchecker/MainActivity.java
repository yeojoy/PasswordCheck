package me.yeojoy.registerpasswordchecker;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ViewAnimator;

import java.util.regex.Pattern;

import me.yeojoy.registerpasswordchecker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    private int mCheckListViewHeight;

    private boolean mIsOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mCheckListViewHeight = getResources().getDimensionPixelSize(R.dimen.check_list_view_height);
        initViews();
    }

    private void initViews() {
        Log.i(TAG, "initViews()");

        mBinding.editTextPassword.addTextChangedListener(new SimplePasswordTextWatcher());

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

        if (mIsOpen) {
            ViewCompat.setTranslationY(mBinding.editTextName, 0f);
        } else {
            ViewCompat.setTranslationY(mBinding.editTextName, -mCheckListViewHeight);
        }
    }

    private void checkPassword(CharSequence password) {
        Log.i(TAG, "checkPassword()");

        if (TextUtils.isEmpty(password)) {
            Log.e(TAG, "password is null or empty.");
            hideCheckList();
            return;
        }

        if (!mIsOpen) {
            showCheckList();
        }

        if (password.length() < 8) {
            mBinding.textViewLeastCharCount.setTypeface(null, Typeface.NORMAL);
        } else {
            mBinding.textViewLeastCharCount.setTypeface(null, Typeface.BOLD);
        }

        String upperCaseMoreThanOnePattern = "[A-Z]+";
        String lowerCaseMoreThanOnePattern = "[a-z]+";
        String numberMoreThanOnePattern = "[\\d]+";
        Pattern upperCasePattern = Pattern.compile(upperCaseMoreThanOnePattern);
        Pattern lowerCasePattern = Pattern.compile(lowerCaseMoreThanOnePattern);
        Pattern numberPattern = Pattern.compile(numberMoreThanOnePattern);

        if (upperCasePattern.matcher(password).find()) {
            mBinding.textViewCapitalOne.setTypeface(null, Typeface.BOLD);
        } else {
            mBinding.textViewCapitalOne.setTypeface(null, Typeface.NORMAL);
        }

        if (lowerCasePattern.matcher(password).find()) {
            mBinding.textViewLowerCaseOne.setTypeface(null, Typeface.BOLD);
        } else {
            mBinding.textViewLowerCaseOne.setTypeface(null, Typeface.NORMAL);
        }

        if (numberPattern.matcher(password).find()) {
            mBinding.textViewNumberOne.setTypeface(null, Typeface.BOLD);
        } else {
            mBinding.textViewNumberOne.setTypeface(null, Typeface.NORMAL);
        }
    }

    private void showCheckList() {
        Log.i(TAG, "showCheckList()");
        mIsOpen = true;
        mBinding.linearLayoutCheckList.setVisibility(View.VISIBLE);
        ValueAnimator heightAnimator = new ValueAnimator();
        heightAnimator.setFloatValues(-mCheckListViewHeight, 0f);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewCompat.setTranslationY(mBinding.editTextName, (Float) valueAnimator.getAnimatedValue());
            }
        });
        ValueAnimator alphaAnimator = new ValueAnimator();
        alphaAnimator.setFloatValues(0f, 1.0f);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewCompat.setAlpha(mBinding.linearLayoutCheckList, (Float) valueAnimator.getAnimatedValue());
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(heightAnimator, alphaAnimator);
        animatorSet.start();
    }

    private void hideCheckList() {
        Log.i(TAG, "hideCheckList()");
        mIsOpen = false;
        ValueAnimator heightAnimator = new ValueAnimator();
        heightAnimator.setFloatValues(0f, -mCheckListViewHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewCompat.setTranslationY(mBinding.editTextName, (Float) valueAnimator.getAnimatedValue());
            }
        });
        ValueAnimator alphaAnimator = new ValueAnimator();
        alphaAnimator.setFloatValues(1f, 0f);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewCompat.setAlpha(mBinding.linearLayoutCheckList, (Float) valueAnimator.getAnimatedValue());
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(heightAnimator, alphaAnimator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mBinding.linearLayoutCheckList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
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

    class SimplePasswordTextWatcher implements TextWatcher {

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
    }
}
