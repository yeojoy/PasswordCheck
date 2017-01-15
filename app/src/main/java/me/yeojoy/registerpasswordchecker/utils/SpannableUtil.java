package me.yeojoy.registerpasswordchecker.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;

import me.yeojoy.registerpasswordchecker.R;

/**
 * Created by yeojoy on 2016. 12. 1..
 */

public class SpannableUtil {
    private static final String TAG = SpannableUtil.class.getSimpleName();

    public static SpannableStringBuilder getPasswordText(Context context, CharSequence password) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        SpannableString spannableString = new SpannableString(context.getString(R.string.at_least_8));
        if (PasswordChecker.isPasswordMoreThanEight(password)) {
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannableStringBuilder.append(spannableString).append(" ");

        spannableString = new SpannableString(context.getString(R.string.capital));
        if (PasswordChecker.hasCapital(password)) {
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannableStringBuilder.append(spannableString).append(" ");

        spannableString = new SpannableString(context.getString(R.string.lower_case));
        if (PasswordChecker.hasLowerCase(password)) {
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannableStringBuilder.append(spannableString).append(" ");

        spannableString = new SpannableString(context.getString(R.string.number));
        if (PasswordChecker.hasNumber(password)) {
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannableStringBuilder.append(spannableString);

        return spannableStringBuilder;
    }
}
