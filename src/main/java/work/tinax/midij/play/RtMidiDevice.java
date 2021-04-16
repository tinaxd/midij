package work.tinax.midij.play;

import java.util.List;

public class RtMidiDevice extends MidiDevice {
	
	static {
		System.loadLibrary("rtmididevice");
	}
	
	private long rtMidiOut; // contains C pointer, do not change!!
	
	public RtMidiDevice() throws RtMidiException {
		super();
		//RtMidiInitializer.getInstance().initializeRtMidi();
		setRtMidiOut();
	}
	
	protected void finalize() {
		destroyRtMidiOut();
	}
	
	private native void setRtMidiOut() throws RtMidiException;
	private native void destroyRtMidiOut();
	
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
	public String getApiName() {
		return "RtMidi - " + getRtMidiApiName();
	}
	
	private native String getRtMidiApiName();

	@Override
	public synchronized native void sendMessage(byte[] message);
	
	@Override
	public synchronized void sendMessage(List<Byte> message) {
		super.sendMessage(message);
	}
}

//class RtMidiInitializer {
//	static {
//		System.loadLibrary("rtmididevice");
//	}
//	
//	private static RtMidiInitializer instance;
//	private RtMidiInitializer() {
//	}
//	
//	public static synchronized RtMidiInitializer getInstance() {
//		if (instance == null) {
//			instance = getInstance();
//		}
//		return instance;
//	}
//	
//	public synchronized void initializeRtMidi() throws RtMidiException {
//		if (!initialized) {
//			initializeRtMidiInternal();
//		}
//	}
//	private native void initializeRtMidiInternal() throws RtMidiException;
//	private boolean initialized = false;
//}
