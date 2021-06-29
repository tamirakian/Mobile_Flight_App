package com.example.flightmobileapp.view_model;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.example.flightmobileapp.BR;
import com.example.flightmobileapp.model.FGPlayer;
import com.example.flightmobileapp.views.Joystick;

public class ViewModel extends BaseObservable {

    private FGPlayer fg;
    private Joystick js;

    public ViewModel() {
        fg = new FGPlayer("", "");
    }

    private String successMessage = "Connection was successful";
    private String errorMessage = "IP or Port not valid";

    @Bindable
    private String connectMessage = null;

    public String getConnectMessage() {
        return connectMessage;
    }


    private void setConnectMessage(String connectMessage) {

        this.connectMessage = connectMessage;
        notifyPropertyChanged(BR.connectMessage);
    }

    public void setUserIP(String IP) {
        fg.setIP(IP);
        notifyPropertyChanged(BR.userIP);
    }

    @Bindable
    public String getUserIP() {
        return fg.getIP();
    }

    @Bindable
    public String getUserPort() {
        return fg.getPort();
    }

    public void setUserPort(String Port) {
        fg.setPort(Port);
        notifyPropertyChanged(BR.userPort);
    }

    public boolean isInputDataValid() {
        return !TextUtils.isEmpty(getUserIP()) && Patterns.IP_ADDRESS.matcher(getUserIP()).matches() && Integer.parseInt(getUserPort()) > 1024 && Integer.parseInt(getUserPort()) < 65535;
    }

    public void onConnectClicked() {
        if (isInputDataValid()) {
            setConnectMessage(successMessage);
            if (fg.connectToSim() == -1) {
                setConnectMessage(errorMessage);
            }
        } else
            setConnectMessage(errorMessage);
    }

    public void setRudderValue(int rudder) {
        float realVal = ((float) rudder / 1000) - 1;
        try {
            fg.setRudder(realVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyPropertyChanged(BR.rudderValue);
    }

    @Bindable
    public int getRudderValue() {
        int realVal = (int) ((fg.getRudder() + 1) * 1000);
        return realVal;
    }

    public void setThrottleValue(int throttle) {
        float realVal = ((float) throttle / 1000);
        try {
            fg.setThrottle(realVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyPropertyChanged(BR.throttleValue);
    }

    @Bindable
    public int getThrottleValue() {
        int realVal = (int) ((fg.getThrottle()) * 1000);
        return realVal;
    }

    public void setAileronValue(float aileron) {
        float realVal = (aileron / 200) - 2;
        try {
            fg.setAileron(realVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyPropertyChanged(BR.aileronValue);
    }

    @Bindable
    public float getAileronValue() {
        float realVal = (fg.getAileron() + 2) * 200;
        return realVal;
    }

    public void setElevatorValue(float elevator) {
        float realVal = (elevator / 200) - 2;
        try {
            fg.setElevator(realVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyPropertyChanged(BR.elevatorValue);
    }

    @Bindable
    public float getElevatorValue() {
        float realVal = (fg.getElevator() + 2) * 200;
        return realVal;
    }

    public void onChange(){
        js.onChange(getAileronValue(), getElevatorValue());
        js.invalidate();
    }
}
