package by.grodno.vasili.presentation.feature.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<VDB extends ViewDataBinding> extends DaggerAppCompatActivity {
    protected VDB binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        binding = DataBindingUtil.setContentView(this, getContentView());
    }

    protected abstract int getContentView();

    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
