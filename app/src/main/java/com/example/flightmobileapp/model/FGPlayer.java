package com.example.flightmobileapp.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FGPlayer {
    private String IP;
    private String port;
    private boolean stop = false;

    private float throttle;
    private float rudder;
    private float aileron;
    private float elevator;


    private Socket fg;
    PrintWriter out;

    BlockingQueue<Runnable> dispatchQueue
            = new LinkedBlockingQueue<Runnable>();

    public FGPlayer(String IP, String port) {
        this.IP = IP;
        this.port = port;
        fg = null;
        out = null;
        aileron = 0;
        elevator = 0;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getIP() {
        return IP;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setThrottle(float throttle) throws InterruptedException {
        this.throttle = throttle;
        dispatchQueue.put(new Runnable() {
            public void run() {
                out.print("set /controls/engines/current-engine/throttle " + throttle + "\r\n");
                out.flush();
            }
        });
    }

    public float getThrottle() {
        return throttle;
    }

    public void setRudder(float rudder) throws InterruptedException {
        this.rudder = rudder;
        dispatchQueue.put(new Runnable() {
            public void run() {
                out.print("set /controls/flight/rudder " + rudder + "\r\n");
                out.flush();
            }
        });
    }

    public float getRudder() {
        return rudder;
    }

    public float getAileron() {
        return aileron;
    }

    public void setAileron(float aileron) throws InterruptedException {
        this.aileron = aileron;
        dispatchQueue.put(new Runnable() {
            public void run() {
                out.print("set /controls/flight/aileron " + aileron + "\r\n");
                out.flush();
            }
        });
    }

    public float getElevator() {
        return elevator;
    }

    public void setElevator(float elevator) throws InterruptedException {
        this.elevator = elevator;
        dispatchQueue.put(new Runnable() {
            public void run() {
                out.print("set /controls/flight/elevator " + elevator + "\r\n");
                out.flush();
            }
        });
    }

    public int connectToSim() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    fg = new Socket(IP, Integer.parseInt(port));
                } catch (IOException e) {
                }
                try {
                    out = new PrintWriter(fg.getOutputStream(), true);
                } catch (IOException e) {
                }
                while (!stop) {
                    try {
                        dispatchQueue.take().run();
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
        return 0;
    }
}
