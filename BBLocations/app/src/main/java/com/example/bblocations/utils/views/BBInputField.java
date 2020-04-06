package com.example.bblocations.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import com.example.bblocations.R;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BBInputField extends ConstraintLayout {

    protected EditText inputField;
    protected View divider;

    public BBInputField(Context context) {
        super(context);
        initialize(null);
    }

    public BBInputField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public BBInputField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
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
    }
}
