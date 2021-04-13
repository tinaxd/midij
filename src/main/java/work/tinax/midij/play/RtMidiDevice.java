package work.tinax.midij.play;

import java.util.List;

public class RtMidiDevice extends MidiDevice {

	@Override
	public native List<String> getDeviceNames();

	@Override
	public native void openDevice(String name) throws DeviceOpenException;

	@Override
	public native void openDevice(int index) throws DeviceOpenException;

	@Override
	public native void closeDevice();

	@Override
	public native String getCurrentPortName();

	@Override
	public String getApiName() {
		return "RtMidi - " + getRtMidiApiName();
	}
	
	private native String getRtMidiApiName();

	@Override
	public native void sendMessage(byte[] message);

}
