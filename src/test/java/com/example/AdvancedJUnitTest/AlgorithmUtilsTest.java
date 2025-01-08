package com.example.AdvancedJUnitTest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmUtilsTest {

    @Test
    void testFindPrimes() {
        // Basic cases
        assertEquals(List.of(2, 3, 5, 7), AlgorithmUtils.findPrimes(0, 10));
        assertEquals(List.of(101, 103, 107, 109), AlgorithmUtils.findPrimes(100, 110));
        assertEquals(Collections.emptyList(), AlgorithmUtils.findPrimes(-5, 1));

        // Edge cases
        assertEquals(List.of(), AlgorithmUtils.findPrimes(10, 2)); // Start > End
        assertEquals(List.of(2), AlgorithmUtils.findPrimes(2, 2)); // Single prime range
    }

    @Test
    void testMergeSort() {
        // Basic cases
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, AlgorithmUtils.mergeSort(new int[]{5, 3, 1, 4, 2}));
        assertArrayEquals(new int[]{}, AlgorithmUtils.mergeSort(new int[]{}));
        assertArrayEquals(new int[]{1}, AlgorithmUtils.mergeSort(new int[]{1}));

        // Edge cases
        assertArrayEquals(new int[]{-3, -2, -1, 0, 1, 2}, AlgorithmUtils.mergeSort(new int[]{0, -1, 2, -3, 1, -2}));
        assertArrayEquals(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE},
                AlgorithmUtils.mergeSort(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0}));

        // Performance testing
        int[] largeArray = new int[1000000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = largeArray.length - i;
        }
        int[] sortedArray = new int[1000000];
        for (int i = 0; i < sortedArray.length; i++) {
            sortedArray[i] = i + 1;
        }
        assertArrayEquals(sortedArray, AlgorithmUtils.mergeSort(largeArray));
    }

    @Test
    void testDijkstraOnFullyConnectedGraph() {
        int[][] graph = {
                {0, 10, 0, 30, 100},
                {10, 0, 50, 0, 0},
                {0, 50, 0, 20, 10},
                {30, 0, 20, 0, 60},
                {100, 0, 10, 60, 0}
        };
        assertArrayEquals(new int[]{0, 10, 50, 30, 60}, AlgorithmUtils.dijkstra(graph, 0));
    }

    @Test
    void testDijkstraOnDisconnectedGraph() {
        int[][] graph = {
                {0, 0, 0, 0, 0},
                {0, 0, 50, 0, 0},
                {0, 50, 0, 20, 10},
                {0, 0, 20, 0, 60},
                {0, 0, 10, 60, 0}
        };
        assertArrayEquals(new int[]{0, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                AlgorithmUtils.dijkstra(graph, 0));
    }

    @Test
    void testDijkstraWithNegativeWeights() {
        int[][] graph = {
                {0, 10, 0, 30, Integer.MAX_VALUE},
                {10, 0, 50, 0, 0},
                {0, 50, 0, 20, 10},
                {30, 0, 20, 0, 60},
                {Integer.MAX_VALUE, 0, 10, 60, 0}
        };
        assertArrayEquals(new int[]{0, 10, 50, 30, 60}, AlgorithmUtils.dijkstra(graph, 0));
    }

    @Test
    void testDijkstraWithInvalidSource() {
        int[][] graph = {
                {0, 10, 0, 30, 100},
                {10, 0, 50, 0, 0},
                {0, 50, 0, 20, 10},
                {30, 0, 20, 0, 60},
                {100, 0, 10, 60, 0}
        };
        assertThrows(IllegalArgumentException.class, () -> AlgorithmUtils.dijkstra(graph, -1));
    }

    @Test
    void testIsPalindrome() {
        // Basic cases
        assertTrue(AlgorithmUtils.isPalindrome("madam"));
        assertFalse(AlgorithmUtils.isPalindrome("hello"));
        assertTrue(AlgorithmUtils.isPalindrome("a"));
        assertTrue(AlgorithmUtils.isPalindrome(""));

        // Edge cases
        assertTrue(AlgorithmUtils.isPalindrome("A man a plan a canal Panama".replaceAll("\\s+", "").toLowerCase()));
        assertFalse(AlgorithmUtils.isPalindrome(null));

        // Performance testing
        StringBuilder largePalindrome = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            largePalindrome.append("a");
        }
        assertTrue(AlgorithmUtils.isPalindrome(largePalindrome.toString()));
    }

    @Test
    void testFindCombinations() {
        // Basic cases
        List<List<Integer>> result = AlgorithmUtils.findCombinations(List.of(1, 2, 3, 4), 2);
        assertTrue(result.containsAll(List.of(
                List.of(1, 2), List.of(1, 3), List.of(1, 4),
                List.of(2, 3), List.of(2, 4), List.of(3, 4))));
        assertEquals(6, result.size());

        // Edge cases
        assertEquals(Collections.emptyList(), AlgorithmUtils.findCombinations(List.of(1, 2, 3), 0)); // k = 0
        assertEquals(List.of(List.of(1, 2, 3)), AlgorithmUtils.findCombinations(List.of(1, 2, 3), 3)); // k = n
        assertEquals(Collections.emptyList(), AlgorithmUtils.findCombinations(List.of(), 2)); // Empty input

        // Performance testing
        List<Integer> largeInput = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            largeInput.add(i + 1);
        }
        assertEquals(190, AlgorithmUtils.findCombinations(largeInput, 2).size());
    }
}


