package by.grodno.vasili.presentation.feature.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import by.grodno.vasili.presentation.R;
import by.grodno.vasili.presentation.databinding.ToolbarViewBinding;

/**
 * Top toolbar with customizable title, ok and cancel handlers
 */
public class ToolbarView extends ConstraintLayout {
    private ToolbarViewBinding binding;

    public ToolbarView(Context context) {
        super(context);
        init(context, null);
    }

    public ToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.toolbar_view, this, true);
        if (attrs != null) {
            applyAttributes(context, attrs);
        }
        setDefaultListeners((Activity) context);
    }

    private void applyAttributes(@NonNull Context context, @NonNull AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ToolbarView);
        try {
            String title = array.getString(R.styleable.ToolbarView_toolbarTitle);
            binding.toolbarTitle.setText(title);
        } finally {
            array.recycle();
        }
    }

    private void setDefaultListeners(Activity context) {
        binding.checkImage.setOnClickListener(v -> context.finish());
        binding.closeImage.setOnClickListener(v -> context.finish());
    }

    /**
     * Set handler for ok icon pressing
     */
    public void setOkListener(OnClickListener okListener) {
        binding.checkImage.setOnClickListener(okListener);
    }

    /**
     * Set handler for cancel icon pressing
     */
    public void setCancelListener(OnClickListener cancelListener) {
        binding.closeImage.setOnClickListener(cancelListener);
    }
}
