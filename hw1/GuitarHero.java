/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
/** @source: GuitarHeroLite.java */

import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    private static final double CONCERT_A = 440.0;

    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] stringArray = new GuitarString[keyboard.length()];
        for (int i = 0; i < keyboard.length(); i++) {
            stringArray[i] = new GuitarString(CONCERT_A * Math.pow(2.0, (i - 24) / 12.0));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int ind = keyboard.indexOf(key);
                if (ind > 0) {
                    stringArray[ind].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (int i = 0; i < stringArray.length; i++) {
                sample += stringArray[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < stringArray.length; i++) {
                stringArray[i].tic();
            }
        }
    }
}

