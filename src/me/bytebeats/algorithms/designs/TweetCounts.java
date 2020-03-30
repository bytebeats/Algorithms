package me.bytebeats.algorithms.designs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TweetCounts {

    private List<Tweet> tweets;

    public TweetCounts() {
        tweets = new ArrayList<>(10000);
    }

    public void recordTweet(String tweetName, int time) {
        tweets.add(new Tweet(tweetName, time));
    }

    public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
        List<Integer> counts = new ArrayList<>();
        int delta = Freq.fromString(freq).delta;
        List<Tweet> tweets = this.tweets.stream().filter(it -> it.tweetName.equals(tweetName) && it.time >= startTime && it.time <= endTime).collect(Collectors.toList());
        int count = (endTime - startTime) / delta;
        for (int i = 0; i < count; i++) {
            int start = startTime + delta * i;
            int end = start + delta;
            counts.add((int) tweets.stream().filter(it -> it.time >= start && it.time < end).count());
        }
        if (startTime + delta * count <= endTime) {
            counts.add((int) tweets.stream().filter(it -> it.time >= startTime + delta * count && it.time <= endTime).count());
        }
        return counts;
    }

    enum Freq {
        M(60, "minute"), H(60 * 60, "hour"), D(60 * 60 * 24, "day");
        int delta;
        String freq;

        Freq(int delta, String freq) {
            this.delta = delta;
            this.freq = freq;
        }

        public static Freq fromString(String freq) {
            switch (freq) {
                case "hour":
                    return H;
                case "day":
                    return D;
                default:
                    return M;
            }
        }
    }

    class Tweet {
        public String tweetName;
        public int time;

        public Tweet(String tweetName, int time) {
            this.tweetName = tweetName;
            this.time = time;
        }
    }
}
