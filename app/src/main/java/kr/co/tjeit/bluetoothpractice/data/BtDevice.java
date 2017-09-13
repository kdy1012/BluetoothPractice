package kr.co.tjeit.bluetoothpractice.data;

import java.io.Serializable;

/**
 * Created by the on 2017-09-13.
 */

public class BtDevice implements Serializable {

    private String deviceName; // KyungJin의 G3
    private String deviceAddress; // BCDADS12-4561-21345


    public BtDevice() {

    }

    public BtDevice(String deviceName, String deviceAddress) {
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }
}
