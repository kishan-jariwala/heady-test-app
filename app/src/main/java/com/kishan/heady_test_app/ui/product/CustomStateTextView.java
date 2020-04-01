package com.kishan.heady_test_app.ui.product;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.kishan.heady_test_app.R;

public class CustomStateTextView extends LinearLayout {

    private TextView textView;
    private State currentState;
    private int position;
    private OnStateChangedCallback onStateChangedCallback;

    public CustomStateTextView(Context context) {
        super(context);
        init();
    }

    public CustomStateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomStateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomStateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.state_text_view, this, true);

        textView = findViewById(R.id.text_state);
        setState(State.ENABLED);
        textView.setOnClickListener(view -> {
            if(currentState != State.DISABLED) {
                if(currentState == State.ENABLED) {
                    setState(State.SELECTED);
                } else {
                    setState(State.ENABLED);
                }
                if(onStateChangedCallback != null) {
                    onStateChangedCallback.stateChanged(position, currentState);
                }
            }
        });
    }

    public void setText(String text) {
        if(textView != null) {
            textView.setText(text);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setState(State state) {
        currentState = state;
        switch (state) {
            case ENABLED:
                textView.setEnabled(true);
                textView.setSelected(false);
                break;
            case SELECTED:
                textView.setEnabled(true);
                textView.setSelected(true);
                break;
            case DISABLED:
                textView.setEnabled(false);
                textView.setSelected(false);
                break;
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public enum State {
        ENABLED, SELECTED, DISABLED;
    }

    public interface OnStateChangedCallback {
        void stateChanged(int position, State newState);
    }

    public void setOnStateChangedCallback(OnStateChangedCallback onStateChangedCallback) {
        this.onStateChangedCallback = onStateChangedCallback;
    }
}
