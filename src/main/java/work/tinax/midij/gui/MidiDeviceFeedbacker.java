package work.tinax.midij.gui;

import work.tinax.midij.play.MidiDevice;

final class MidiDeviceFeedbacker extends BasicFeedbackPlayer {
	private final MidiDevice midiDevice;
	
	public MidiDeviceFeedbacker(MidiDevice midiDevice) {
		if (midiDevice == null) throw new NullPointerException("midiDevice is null");
		this.midiDevice = midiDevice;
	}
	
	public MidiDevice getMidiDevice() {
		return midiDevice;
	}

	@Override
	protected void playNote(int scale, int velocity, int channel) {
		checkChannelBounds(channel);
		checkScaleBounds(scale);
		checkVelocityBounds(velocity);
		byte[] msg = new byte[] {(byte) (0x90 | channel), (byte)scale, (byte)velocity};
		midiDevice.sendMessage(msg);
	}
	
	@Override
	protected void stopNote(int scale, int channel) {
		checkChannelBounds(channel);
		checkScaleBounds(scale);
		byte[] msg = new byte[] {(byte) (0x80 | channel), (byte)scale, 0};
		midiDevice.sendMessage(msg);
	}
}
