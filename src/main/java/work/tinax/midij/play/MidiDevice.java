package work.tinax.midij.play;

import java.util.List;

public abstract class MidiDevice {
	public abstract List<String> getDeviceNames();
	
	public abstract void openDevice(String name) throws DeviceOpenException;
	public abstract void openDevice(int index) throws DeviceOpenException;
	public abstract void closeDevice();
	
	public abstract String getCurrentPortName();
	
	public abstract String getApiName();
	
	public abstract void sendMessage(byte[] message);
	
	public void sendMessage(List<Byte> message) {
		var array = new byte[message.size()];
		int i = 0;
		for (var b : message) {
			array[i] = b;
		}
		sendMessage(array);
	}
}
