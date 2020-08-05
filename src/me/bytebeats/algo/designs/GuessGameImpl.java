package me.bytebeats.algo.designs;

public class GuessGameImpl implements GuessGame {
    @Override
    public int guess(int num) {
        return 0;
    }

    public int guessNumber(int n) {
        int low = 1;
        int high = n;
        int mid = 0;
        while (low < high) {
            mid = low + (high - low) / 2;
            if (guess(mid) > 0) {
                low = mid + 1;
            } else if (guess(mid) < 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return 1;
    }
}
