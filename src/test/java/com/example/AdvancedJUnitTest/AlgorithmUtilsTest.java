package com.example.AdvancedJUnitTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class AlgorithmUtilsTest {

    @Test
    void testFindPrimes() {
        assertEquals(List.of(2, 3, 5, 7), AlgorithmUtils.findPrimes(0, 10));
        assertEquals(List.of(101, 103, 107, 109), AlgorithmUtils.findPrimes(100, 110));
        assertEquals(Collections.emptyList(), AlgorithmUtils.findPrimes(-5, 1));
    }

    @Test
    void testMergeSort() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, AlgorithmUtils.mergeSort(new int[]{5, 3, 1, 4, 2}));
        assertArrayEquals(new int[]{}, AlgorithmUtils.mergeSort(new int[]{}));
        assertArrayEquals(new int[]{1}, AlgorithmUtils.mergeSort(new int[]{1}));
    }

    @Test
    public void testDijkstraOnFullyConnectedGraph() {
        int[][] graph = {
                {0, 10, 0, 30, 100},
                {10, 0, 50, 0, 0},
                {0, 50, 0, 20, 10},
                {30, 0, 20, 0, 60},
                {100, 0, 10, 60, 0}
        };
        assertArrayEquals(new int[]{0, 10, 50, 30, 60}, AlgorithmUtils.dijkstra(graph, 0));
    }

    //Addition of test case for higher coverage
    @Test
    public void testDijkstraOnDisconnectedGraph() {
        int[][] graph2 = {
                {0, 0, 0, 0, 0},
                {0, 0, 50, 0, 0},
                {0, 50, 0, 20, 10},
                {0, 0, 20, 0, 60},
                {0, 0, 10, 60, 0}
        };
        assertArrayEquals(new int[]{0, Integer.MAX_VALUE, Integer.MAX_VALUE,
                        Integer.MAX_VALUE, Integer.MAX_VALUE},
                AlgorithmUtils.dijkstra(graph2, 0));
    }

    @Test
    void testDijkstraWithNegativeWeights() {
        int[][] graph3 = {
                {0, 10, 0, 30, Integer.MAX_VALUE},
                {10, 0, 50, 0, 0},
                {0, 50, 0, 20, 10},
                {30, 0, 20, 0, 60},
                {Integer.MAX_VALUE, 0, 10, 60, 0}
        };
        assertArrayEquals(new int[]{0, 10, 50, 30, 60},
                AlgorithmUtils.dijkstra(graph3, 0));
    }

    @Test
    void testIsPalindrome() {
        assertTrue(AlgorithmUtils.isPalindrome("madam"));
        assertFalse(AlgorithmUtils.isPalindrome("hello"));
        assertTrue(AlgorithmUtils.isPalindrome("a"));
        assertTrue(AlgorithmUtils.isPalindrome(""));
    }

    @Test
    void testFindCombinations() {
        List<List<Integer>> result = AlgorithmUtils.findCombinations(List.of(1, 2, 3, 4), 2);
        assertTrue(result.containsAll(List.of(
                List.of(1, 2), List.of(1, 3), List.of(1, 4),
                List.of(2, 3), List.of(2, 4), List.of(3, 4))));
        assertEquals(6, result.size());
    }
}

