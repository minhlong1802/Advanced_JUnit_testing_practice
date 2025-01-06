package com.example.AdvancedJUnitTest;

import java.util.*;

public class AlgorithmUtils {

    // 1. Tìm số nguyên tố trong khoảng cho trước
    public static List<Integer> findPrimes(int start, int end) {
        List<Integer> primes = new ArrayList<>();
        if (start < 2) start = 2;
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) primes.add(i);
        }
        return primes;
    }

    private static boolean isPrime(int num) {
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    // 2. Merge Sort
    public static int[] mergeSort(int[] array) {
        if (array.length <= 1) return array;
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(mergeSort(left), mergeSort(right));
    }

    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            result[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while (i < left.length) result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];
        return result;
    }

    // 3. Dijkstra
    public static int[] dijkstra(int[][] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = findMinDistance(dist, visited);
            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }
        return dist;
    }

    private static int findMinDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) { // Note the change here: <= min
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    // 4. Kiểm tra chuỗi đối xứng
    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

    // 5. Tìm tổ hợp k phần tử
    public static List<List<Integer>> findCombinations(List<Integer> list, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), list, k, 0);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> temp, List<Integer> list, int k, int start) {
        if (temp.size() == k) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i < list.size(); i++) {
            temp.add(list.get(i));
            backtrack(result, temp, list, k, i + 1);
            temp.remove(temp.size() - 1);
        }
    }
}

