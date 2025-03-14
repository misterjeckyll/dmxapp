package com.example.dmxinterface.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dmxinterface.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.setRotationX(0);
        root.setRotationY(0);
        root.setRotation(0);
        root.setScaleX(1);
        root.setScaleY(1);
        root.setPivotX(root.getWidth() / 2);
        root.setPivotY(root.getHeight() / 2);
        root.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        final TextView textView = binding.textView;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}