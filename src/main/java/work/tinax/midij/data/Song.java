package work.tinax.midij.data;

import java.util.ArrayList;

public class Song {
	private ArrayList<Track> tracks = new ArrayList<>();
	
	public Song() {
		
	}
	
	public Track getTrack(int trackIndex) {
		return tracks.get(trackIndex);
	}
	
	public void replaceTrack(int trackIndex, Track newTrack) {
		tracks.set(trackIndex, newTrack);
	}
	
	public void addTrack(Track newTrack) {
		tracks.add(newTrack);
	}
	
	private void sortAll() {
		tracks.forEach(track -> track.sortAll());
	}
}
