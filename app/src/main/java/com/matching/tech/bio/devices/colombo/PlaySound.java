package com.matching.tech.bio.devices.colombo;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * PlaySound.java  - This class is used to play sound once the fingerprint capture is done.
 * @author Shravan Nitta
 * @version 1.0
 */

public class PlaySound {

	private final int duration = 1; // seconds
	private final int sampleRate = 8000;
	private final int numSamples = this.duration * this.sampleRate;
	private final double sample[] = new double[this.numSamples];
	private final double freqOfTone = 880; // hz

	private final byte generatedSnd[] = new byte[2 * this.numSamples];

	public PlaySound() {
		for (int i = 0; i < this.numSamples; ++i) 
		{
			this.sample[i] = Math.sin(2 * Math.PI * i / (this.sampleRate / this.freqOfTone));
		}

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		int idx = 0;
		for (final double dVal : this.sample) 
		{
			// scale to maximum amplitude
			final short val = (short) ((dVal * 32767));
			// in 16 bit wav PCM, first byte is the low order byte
			this.generatedSnd[idx++] = (byte) (val & 0x00ff);
			this.generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

		}
	}
	/*playSound() method is used to play sound once the fingerprint capture is done.*/
	public void playSound(){
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				this.sampleRate, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT, this.numSamples,
				AudioTrack.MODE_STATIC);
		audioTrack.write(this.generatedSnd, 0, this.generatedSnd.length);
		audioTrack.play();
	}
}
