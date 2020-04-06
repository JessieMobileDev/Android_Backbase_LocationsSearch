package com.example.bblocations.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bblocations.R;
import com.example.bblocations.utils.listeners.GenericListener;

import androidx.constraintlayout.widget.ConstraintLayout;

public class BBInputField extends ConstraintLayout implements EditText.OnEditorActionListener {

    protected EditText inputField;
    protected View divider;
    protected GenericListener listener;

    public BBInputField(Context context, GenericListener listener) {
        super(context);
        initialize(null, listener);
    }

    public BBInputField(Context context, AttributeSet attrs, GenericListener listener) {
        super(context, attrs);
        initialize(attrs, listener);
    }

    public BBInputField(Context context, AttributeSet attrs, int defStyleAttr, GenericListener listener) {
        super(context, attrs, defStyleAttr);
        initialize(attrs, listener);
    }

    /**
     * Loads layout for UI element and bind views to class
     * @param attributeSet
     */
    private void initialize(AttributeSet attributeSet, GenericListener listener) {
        inflate(getContext(), R.layout.bb_input_field, this);
        bindViews(listener);
    }

    /**
     * Binds views to the class
     */
    private void bindViews(GenericListener listener) {
        this.inputField = findViewById(R.id.editText);
        this.divider = findViewById(R.id.divider);
        this.listener = listener;
        this.inputField.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        listener.searchListener(textView.getText(), i, keyEvent);
        return false;
    }
}
