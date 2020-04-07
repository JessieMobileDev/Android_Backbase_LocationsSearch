package com.example.bblocations.utils.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bblocations.R;
import com.example.bblocations.utils.listeners.GenericListener;

import androidx.constraintlayout.widget.ConstraintLayout;

public class BBInputField extends ConstraintLayout implements TextWatcher {

    protected EditText inputField;
    protected View divider;
    protected GenericListener listener;

    public BBInputField(Context context) {
        super(context);
        if(!isInEditMode()) {
            initialize(null);
        }
    }

    public BBInputField(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            initialize(attrs);
        }
    }

    public BBInputField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()) {
            initialize(attrs);
        }
    }

    /**
     * Loads layout for UI element and bind views to class
     * @param attributeSet
     */
    private void initialize(AttributeSet attributeSet) {
        inflate(getContext(), R.layout.bb_input_field, this);
        bindViews();
    }

    /**
     * Binds views to the class
     */
    private void bindViews() {
        this.inputField = findViewById(R.id.editText);
        this.divider = findViewById(R.id.divider);
        this.inputField.addTextChangedListener(this);
    }

    public BBInputField withCallback(GenericListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        listener.searchListener(charSequence, start, before, count);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public EditText getEditText() {
        return this.inputField;
    }

    public void setText(String text) {
        this.inputField.setText(text);
    }

    public String getText() {
        return this.inputField.getText().toString();
    }
}
