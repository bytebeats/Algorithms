package me.bytebeats.algo.arrays;

import java.util.*;

/**
 * @author bytebeats
 * @version 1.0
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/30 17:02
 * @description TO-DO
 */

public class ArrayQuiz2 {
    public int largestComponentSize(int[] A) {//952
        int N = A.length;

        // factored[i] = a list of unique prime factors of A[i]
        ArrayList<Integer>[] factored = new ArrayList[N];
        for (int i = 0; i < N; ++i) {
            factored[i] = new ArrayList<>();
            int d = 2, x = A[i];
            while (d * d <= x) {
                if (x % d == 0) {
                    while (x % d == 0)
                        x /= d;
                    factored[i].add(d);
                }

                d++;
            }

            if (x > 1 || factored[i].isEmpty())
                factored[i].add(x);
        }

        // primesL : a list of all primes that occur in factored
        Set<Integer> primes = new HashSet();
        for (List<Integer> facs : factored)
            for (int x : facs)
                primes.add(x);

        int[] primesL = new int[primes.size()];
        int t = 0;
        for (int x : primes)
            primesL[t++] = x;

        // primeToIndex.get(v) == i  iff  primes[i] = v
        Map<Integer, Integer> primeToIndex = new HashMap();
        for (int i = 0; i < primesL.length; ++i)
            primeToIndex.put(primesL[i], i);

        DSU dsu = new DSU(primesL.length);
        for (List<Integer> facs : factored)
            for (int x : facs)
                dsu.union(primeToIndex.get(facs.get(0)), primeToIndex.get(x));

        int[] count = new int[primesL.length];
        for (List<Integer> facs : factored)
            count[dsu.find(primeToIndex.get(facs.get(0)))]++;

        int ans = 0;
        for (int x : count)
            if (x > ans)
                ans = x;
        return ans;
    }

    public int largestOverlap(int[][] A, int[][] B) {//835
        int N = A.length;
        int[][] count = new int[2 * N + 1][2 * N + 1];
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                if (A[i][j] == 1)
                    for (int i2 = 0; i2 < N; ++i2)
                        for (int j2 = 0; j2 < N; ++j2)
                            if (B[i2][j2] == 1)
                                count[i - i2 + N][j - j2 + N] += 1;

        int ans = 0;
        for (int[] row : count)
            for (int v : row)
                ans = Math.max(ans, v);
        return ans;
    }
}

class DSU {
    int[] parent;

    public DSU(int N) {
        parent = new int[N];
        for (int i = 0; i < N; ++i)
            parent[i] = i;
    }

    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    public void union(int x, int y) {
        parent[find(x)] = find(y);
    }
}
