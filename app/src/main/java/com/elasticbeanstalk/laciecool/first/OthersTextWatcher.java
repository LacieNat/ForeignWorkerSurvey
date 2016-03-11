package com.elasticbeanstalk.laciecool.first;

import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by lacie on 3/11/16.
 */
public abstract class OthersTextWatcher implements TextWatcher {

    private View v;

    public OthersTextWatcher(View parent) {
        v = parent;
    }

    public View getView() {
        return v;
    }
}