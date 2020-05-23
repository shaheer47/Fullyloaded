package com.app.fullyloaded.Utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.app.fullyloaded.R;

public class MyButton extends androidx.appcompat.widget.AppCompatButton {

    boolean black = false;
    boolean black_italic = false;
    boolean bold = false;
    boolean bold_italic = false;
    boolean extra_bold = false;
    boolean extra_bold_italic = false;
    boolean light = false;
    boolean light_italic = false;
    boolean italic = false;
    boolean extra_light = false;
    boolean extra_light_italic = false;
    boolean medium = false;
    boolean medium_italic = false;
    boolean regular = false;
    boolean semi_bold = false;
    boolean semi_bold_italic = false;
    boolean thin = false;
    boolean thin_italic = false;

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextView, 0, 0);
        try {
            black = a.getBoolean(R.styleable.MyTextView_black, false);
            black_italic = a.getBoolean(R.styleable.MyTextView_black_italic, false);
            bold = a.getBoolean(R.styleable.MyTextView_bold, false);
            bold_italic = a.getBoolean(R.styleable.MyTextView_bold_italic, false);
            extra_bold = a.getBoolean(R.styleable.MyTextView_extra_bold, false);
            extra_bold_italic = a.getBoolean(R.styleable.MyTextView_extra_bold_italic, false);
            light = a.getBoolean(R.styleable.MyTextView_light, false);
            light_italic = a.getBoolean(R.styleable.MyTextView_light_italic, false);
            italic = a.getBoolean(R.styleable.MyTextView_italic, false);
            extra_light = a.getBoolean(R.styleable.MyTextView_extra_light, false);
            extra_light_italic = a.getBoolean(R.styleable.MyTextView_extra_light_italic, false);
            medium = a.getBoolean(R.styleable.MyTextView_medium, false);
            medium_italic = a.getBoolean(R.styleable.MyTextView_medium_italic, false);
            regular = a.getBoolean(R.styleable.MyTextView_regular, false);
            semi_bold = a.getBoolean(R.styleable.MyTextView_semi_bold, false);
            semi_bold_italic = a.getBoolean(R.styleable.MyTextView_semi_bold_italic, false);
            thin = a.getBoolean(R.styleable.MyTextView_thin, false);
            thin_italic = a.getBoolean(R.styleable.MyTextView_thin_italic, false);
        } finally {
            a.recycle();
        }
        init();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextView, 0, 0);
        try {
            black = a.getBoolean(R.styleable.MyTextView_black, false);
            black_italic = a.getBoolean(R.styleable.MyTextView_black_italic, false);
            bold = a.getBoolean(R.styleable.MyTextView_bold, false);
            bold_italic = a.getBoolean(R.styleable.MyTextView_bold_italic, false);
            extra_bold = a.getBoolean(R.styleable.MyTextView_extra_bold, false);
            extra_bold_italic = a.getBoolean(R.styleable.MyTextView_extra_bold_italic, false);
            light = a.getBoolean(R.styleable.MyTextView_light, false);
            light_italic = a.getBoolean(R.styleable.MyTextView_light_italic, false);
            italic = a.getBoolean(R.styleable.MyTextView_italic, false);
            extra_light = a.getBoolean(R.styleable.MyTextView_extra_light, false);
            extra_light_italic = a.getBoolean(R.styleable.MyTextView_extra_light_italic, false);
            medium = a.getBoolean(R.styleable.MyTextView_medium, false);
            medium_italic = a.getBoolean(R.styleable.MyTextView_medium_italic, false);
            regular = a.getBoolean(R.styleable.MyTextView_regular, false);
            semi_bold = a.getBoolean(R.styleable.MyTextView_semi_bold, false);
            semi_bold_italic = a.getBoolean(R.styleable.MyTextView_semi_bold_italic, false);
            thin = a.getBoolean(R.styleable.MyTextView_thin, false);
            thin_italic = a.getBoolean(R.styleable.MyTextView_thin_italic, false);
        } finally {
            a.recycle();
        }
        init();
    }

    public MyButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = null;
        if (black) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "black.otf");
        } else if (black_italic) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "black_italic.otf");
        } else if (bold) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "bold.otf");
        } else if (bold_italic) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "bold_italic.otf");
        } else if (extra_bold) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "extra_bold.otf");
        } else if (extra_bold_italic) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "extra_bold_italic.otf");
        } else if (extra_light) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "extra_light.otf");
        } else if (extra_light_italic) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "extra_light_italic.otf");
        } else if (italic) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "italic.otf");
        } else if (light) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "light.otf");
        } else if (light_italic) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "light_italic.otf");
        } else if (medium) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "medium.otf");
        } else if (medium_italic) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "medium_italic.otf");
        } else if (regular) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "regular.otf");
        } else if (semi_bold) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "semi_bold.otf");
        } else if (semi_bold_italic) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "semi_bold_italic.otf");
        } else if (thin) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "thin.otf");
        } else if (thin_italic) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "thin_italic.otf");
        }
        setTypeface(tf);
    }
}
