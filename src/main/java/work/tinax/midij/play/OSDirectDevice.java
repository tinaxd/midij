package work.tinax.midij.play;

public final class OSDirectDevice extends MidiDevice {

	@Override
	public native String[] getDeviceNames();

	@Override
	public native void openDevice(String name) throws DeviceOpenException;

	@Override
	public native void openDevice(int index) throws DeviceOpenException;

	@Override
	public native void closeDevice();

	@Override
	public native String getCurrentPortName();

	@Override
	public native String getApiName();

	@Override
	public native void sendMessage(byte[] message);

}
