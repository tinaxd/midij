package work.tinax.midij.gui;

import java.util.LinkedList;
import java.util.List;

abstract class BasicFeedbackPlayer implements FeedbackPlayer {
	private int channel = -1;
	private final List<ChannelAndNote> playingNotes = new LinkedList<>();

	protected abstract void playNote(int scale, int velocity, int channel);
	
	private void playNoteAndRegister(int scale, int velocity, int channel) {
		playNote(scale, velocity, channel);
		playingNotes.add(new ChannelAndNote(scale, channel));
	}
	
	protected abstract void stopNote(int scale, int channel);
	
	@Override
	public void playNote(int scale, int velocity) {
		if (channel == -1) return;
		playNoteAndRegister(scale, velocity, channel);
	}

	@Override
	public void stopNote(int scale) {
		var iter = playingNotes.listIterator();
		while (iter.hasNext()) {
			var chnote = iter.next();
			if (chnote.scale() == scale) {
				stopNote(scale, chnote.channel());
				iter.remove();
			}
		}
	}
	
	@Override
	public void stopAllNotes() {
		playingNotes.forEach(chnote -> stopNote(chnote.scale(), chnote.channel()));
	}

	@Override
	public void setChannel(int channel) {
		stopAllNotes();
		this.channel = channel;
	}
	
	protected static void checkChannelBounds(int channel) {
		if (0 > channel || channel >= 16)
			throw new IllegalArgumentException("channel is out of bounds: " + channel);
	}
	
	protected static void checkScaleBounds(int scale) {
		if (0 > scale || scale > 127)
			throw new IllegalArgumentException("scale is out of bounds: " + scale);
	}
	
	protected static void checkVelocityBounds(int velocity) {
		if (0 > velocity || velocity > 127)
			throw new IllegalArgumentException("velocity is out of bounds: " + velocity);
	}
}

record ChannelAndNote(int scale, int channel) {}
