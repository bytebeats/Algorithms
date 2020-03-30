package me.bytebeats.algorithms.designs;

public class VersionControlImpl extends VersionControl {
    @Override
    boolean isBadVersion(int version) {
        return false;
    }

//    public int firstBadVersion(int n) {
//        int low = 1;
//        int high = n;
//        int mid = 0;
//        while (low <= high) {
//            mid = low + (high - low) / 2;
//            if (!isBadVersion(mid)) {
//                low = mid + 1;
//            } else {
//                if (!isBadVersion(mid - 1)) {
//                    return mid;
//                } else {
//                    high = mid - 1;
//                }
//            }
//        }
//        return 1;
//    }

    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
