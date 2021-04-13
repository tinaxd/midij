package work.tinax.midij.play;

public class MidiPlayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2962082046569087016L;
	
	public MidiPlayException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public MidiPlayException(String msg) {
		super(msg);
	}

}
