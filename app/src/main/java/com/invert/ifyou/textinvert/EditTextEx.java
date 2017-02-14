package com.invert.ifyou.textinvert;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by ifyou on 25.04.2016.
 */
public class EditTextEx extends EditText {
    public EditTextEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextEx(Context context) {
        super(context);
    }

    public EditTextEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            dispatchKeyEvent(event);
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
