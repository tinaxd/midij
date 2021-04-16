package work.tinax.midij.gui;

public interface FeedbackPlayer {
	void setChannel(int channel);
	void playNote(int scale, int velocity);
	void stopNote(int scale);
	void stopAllNotes();
}
