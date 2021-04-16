package work.tinax.midij.gui;

final class StdoutFeedbackPlayer extends BasicFeedbackPlayer {

	@Override
	protected void playNote(int scale, int velocity, int channel) {
		checkChannelBounds(channel);
		checkScaleBounds(scale);
		checkVelocityBounds(velocity);
		byte[] msg = new byte[] {(byte) (0x90 | channel), (byte)scale, (byte)velocity};
		String msgAsString = unsignedBytesAsString(msg);
		System.out.printf("<note on (ch %d) %d %d> %s\n", channel, scale, velocity, msgAsString);
	}

	@Override
	protected void stopNote(int scale, int channel) {
		checkChannelBounds(channel);
		checkScaleBounds(scale);
		byte[] msg = new byte[] {(byte) (0x80 | channel), (byte)scale, 0};
		String msgAsString = unsignedBytesAsString(msg);
		System.out.printf("<note off (ch %d) %d> %s\n", channel, scale, msgAsString);
	}
	
	@Override
	public void setChannel(int channel) {
		super.setChannel(channel);
		System.out.printf("<set channel to (ch %d)>\n", channel);
	}
	
	private static String unsignedBytesAsString(final byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<bytes.length; i++) {
			if (i != 0) sb.append(' ');
			sb.append(Byte.toUnsignedInt(bytes[i]));
		}
		return sb.toString();
	}
}
