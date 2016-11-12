package net.ballmerlabs.scatterbrain.network;

import android.app.Activity;
import android.app.Service;

import net.ballmerlabs.scatterbrain.SettingsManager;
import net.ballmerlabs.scatterbrain.network.bluetooth.ScatterBluetoothManager;

/**
 * Collection of global objects for use by scatterbrain
 * network (inb4 'global is bad')
 */
public class NetTrunk {

    public GlobalNet globnet;
    public ScatterBluetoothManager blman;
    public DeviceProfile profile;
    public SettingsManager settings;
    public ScatterRoutingService mainService;


    public NetTrunk(ScatterRoutingService mainService) {
        this.mainService = mainService;
        byte tmp[] = {0,0,0,0,0,0};
        profile = new DeviceProfile(DeviceProfile.deviceType.ANDROID, DeviceProfile.MobileStatus.MOBILE,
                DeviceProfile.HardwareServices.BLUETOOTHLE, tmp);
        globnet = new GlobalNet(this);
        settings = new SettingsManager();
       // globnet.getWifiManager().startWifiDirctLoopThread();
        blman = new ScatterBluetoothManager(this);
    }
}
