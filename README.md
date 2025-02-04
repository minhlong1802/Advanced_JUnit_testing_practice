## Advanced JUnit testing practice with Java

Dưới đây là một bài tập kiểm thử nâng cao với JUnit, áp dụng cho một số thuật toán thông dụng với các cấu trúc điều khiển và kiểu dữ liệu phong phú. 

---

### **Đề bài**: 
Viết một chương trình Java thực hiện các chức năng sau:  
1. **Tìm số nguyên tố trong khoảng cho trước.**  
2. **Sắp xếp mảng bằng thuật toán Merge Sort.**  
3. **Tìm đường đi ngắn nhất trong đồ thị (Dijkstra).**  
4. **Kiểm tra chuỗi đối xứng (palindrome).**  
5. **Tìm tổ hợp k phần tử từ một danh sách.**

Sau đó, viết các bộ kiểm thử nâng cao bằng JUnit để kiểm tra toàn diện các chức năng trên, bao gồm:
- **Kiểm thử giá trị biên.**
- **Kiểm thử đường dẫn (Path Testing).**
- **Kiểm thử với các cấu trúc dữ liệu phức tạp.**

---

### **Hướng dẫn giải:**

#### 1. **Chương trình Java:**
```java
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
        if (source < 0 || source >= n) {
            throw new IllegalArgumentException("Source index is out of bounds");
        }
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
        if (s == null) {
            return false;
        }
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // 5. Tìm tổ hợp k phần tử
    public static List<List<Integer>> findCombinations(List<Integer> input, int k) {
        if (k == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        findCombinationsHelper(input, k, 0, new ArrayList<>(), result);
        return result;
    }

    private static void findCombinationsHelper(List<Integer> input, int k, int start, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < input.size(); i++) {
            current.add(input.get(i));
            findCombinationsHelper(input, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
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
```

---

#### 2. **Bộ kiểm thử JUnit nâng cao:**

```java
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
    }
}
```

---

### 3. **Yêu cầu nâng cao:**
1. **Tăng độ phức tạp**:
   - Thêm các bộ kiểm thử với input lớn hoặc đặc biệt (e.g., mảng đã sắp xếp, đồ thị có chu trình).
   - Kiểm tra ngoại lệ (e.g., tham số không hợp lệ).

2. **Phân tích mã bao phủ (Code Coverage)**:
   - Sử dụng công cụ như JaCoCo để đảm bảo mã nguồn được kiểm thử toàn diện.

3. **Kiểm thử hiệu năng**:
   - Đánh giá thời gian chạy của các thuật toán với dữ liệu lớn.
  
---
### 4. **Kết quả**
![image](https://github.com/user-attachments/assets/90abfee9-a140-402a-b763-428a34034042)


Độ coverage

![image](https://github.com/user-attachments/assets/3d99a4af-e12a-4082-b99c-c17cb4e4bb98)

---
### 5. **Tham khảo**
[Chat GPT](https://chatgpt.com/share/677bb365-a7cc-8000-8abd-553120fc313b)
