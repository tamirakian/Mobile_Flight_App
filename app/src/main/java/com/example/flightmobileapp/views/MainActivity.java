package com.example.flightmobileapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.flightmobileapp.R;
import com.example.flightmobileapp.databinding.ActivityMainBinding;
import com.example.flightmobileapp.view_model.ViewModel;

public class MainActivity extends AppCompatActivity {
    private ViewModel vm = new ViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(vm);
        activityMainBinding.executePendingBindings();

        Joystick js = (Joystick)findViewById(R.id.joystick);
        js.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Check if the button is PRESSED
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    vm.setAileronValue(event.getX());
                    vm.setElevatorValue(event.getY());
                    return true;
                }

                else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    vm.setAileronValue(event.getX());
                    vm.setElevatorValue(event.getY());
                    return true;
                }

                // Check if the button is RELEASED
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vm.setAileronValue(400);
                    vm.setElevatorValue(400);
                    return true;
                }
                return false;
            }
        });
    }

    @BindingAdapter({"connectMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }


}