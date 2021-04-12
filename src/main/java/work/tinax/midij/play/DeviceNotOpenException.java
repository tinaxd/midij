package work.tinax.midij.play;

public class DeviceNotOpenException extends MidiPlayException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5611696456152584841L;

	public DeviceNotOpenException(String msg) {
		super(msg);
	}
	
	public DeviceNotOpenException() {
		super("no devices are opened for sending midi messages");
	}

}
