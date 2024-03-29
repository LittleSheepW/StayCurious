### 排序算法的介绍：

排序也称排序算法(Sort Algorithm)，排序是将一组数据按指定的顺序进行排列的过程。

### 排序算法的分类：

- 内部排序(使用内存)：
    - 插入排序：
        - 直接插入排序：把n个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含有n-1个元素，排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，将它插入到有序表中的适当位置，使之成为新的有序表。
        - 希尔排序：先将要排序的一组数按某个增量d分成若干组，每组中记录的下标相差d，对每组中全部元素进行排序，然后再用一个较小的增量对它进行分组，在每组中再进行排序。当增量减到1时，整个要排序的数被分成一组，排序完成。一般的初次取序列的一半为增量，以后每次减半，直到增量为1。
    - 选择排序：
        - 简单选择排序：从欲排序的数据中按指定的规则选出某一元素再按规定交换位置后达到排序目的的一种算法。第一次从arr[0]~arr[n-1]中选取最小值，与arr[0]交换；第二次从arr[1]~arr[n-1]
          中选取最小值，与arr[1]交换；第i次从arr[i-1]~arr[n-1]中选取最小值，与arr[i-1]交换；第n-1次从arr[n-2]~arr[n-1]中选取最小值，与arr[n-2]
          交换。总共通过n-1次，得到一个按排序码从小到大排列的有序序列。
        - 堆排序：
    - 交换排序：
        - 冒泡排序：对待排序序列从前向后，依次比较相邻元素的值，发现逆序则进行交换，使值较大的元素逐渐从前移向后部，就象水底下的气泡一样逐渐向上冒。
        - 快速排序：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
    -  归并排序：首先将集合进行逐层的折半分组，一直到每组只有一个元素为止；之后各小组间进行比较和排序并合并成一个大组；大组之间继续比较和排序，再合并成更大的组；最终所有元素合并成了一个有序的集合。
    - 基数排序
- 外部排序(内外存结合)：

### 由易到难：

- 冒泡排序
- 选择排序
- 插入排序
- 希尔排序
- 快速排序