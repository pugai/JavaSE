package util.algorithm;

import com.google.common.collect.Ordering;
import org.apache.commons.lang3.RandomUtils;
import util.algorithm.pojo.StatArticleRechargePv;

import java.util.*;

/**
 * @Description
 * @Auther ctl
 * @Date 2019/7/18
 */
public class TopK {

    public static void main(String[] args) {
        List<StatArticleRechargePv> datas = new ArrayList<>();
        Comparator<StatArticleRechargePv> comparator = Comparator.comparing(StatArticleRechargePv::getPv);
        int N = 1000;
        int K = 5;
        for (int i = 0; i < N; i++) {
            StatArticleRechargePv s = new StatArticleRechargePv();
            s.setPv(RandomUtils.nextLong(0L, 10000L));
            datas.add(s);
        }

//        System.out.println("------before");
//        datas.forEach(d -> System.out.print(d.getPv() + ","));
//        System.out.println();

        long start = System.currentTimeMillis();
        System.out.println(guavaOrder(datas, K));
        System.out.println("consume ms: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(pqAddAll(datas, K));
        System.out.println("consume ms: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(pqIter(datas, K));
        System.out.println("consume ms: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(getTopK(datas, K, Comparator.comparing(StatArticleRechargePv::getPv)));
        System.out.println("consume ms: " + (System.currentTimeMillis() - start));

//        System.out.println("------after");
//        datas.forEach(d -> System.out.print(d.getPv() + ","));
//        System.out.println();

//        System.out.println("------topK");
//        topK.forEach(d -> System.out.print(d.getPv() + ","));
    }

    /**
     * guava，可能是类似快排操作，返回有序
     * @param datas
     * @param K
     * @return
     */
    public static List<StatArticleRechargePv> guavaOrder(List<StatArticleRechargePv> datas, int K) {
        Comparator<StatArticleRechargePv> comparator = Comparator.comparing(StatArticleRechargePv::getPv);
        return Ordering.from(comparator).greatestOf(datas, K);
    }

    /**
     * 大顶堆，全部加入，依此弹出，返回有序
     * @param datas
     * @param K
     * @return
     */
    public static List<StatArticleRechargePv> pqAddAll(List<StatArticleRechargePv> datas, int K) {
        Comparator<StatArticleRechargePv> comparator = Comparator.comparing(StatArticleRechargePv::getPv);
        PriorityQueue<StatArticleRechargePv> maxHeap = new PriorityQueue<>(datas.size(), comparator.reversed());
        maxHeap.addAll(datas);
        List<StatArticleRechargePv> topK = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            topK.add(maxHeap.poll());
        }
        return topK;
    }

    /**
     * 遍历，小顶堆，返回小顶堆作为结果，无序
     * @param datas
     * @param K
     * @return
     */
    public static List<StatArticleRechargePv> pqIter(List<StatArticleRechargePv> datas, int K) {
        Comparator<StatArticleRechargePv> comparator = Comparator.comparing(StatArticleRechargePv::getPv);
        PriorityQueue<StatArticleRechargePv> minHeap = new PriorityQueue<>(K, comparator);
        for (StatArticleRechargePv s: datas) {
            if (minHeap.size() < K || comparator.compare(s, minHeap.peek()) > 0) {
                if (minHeap.size() == K) {
                    minHeap.poll();
                }
                minHeap.offer(s);
            }
        }
        return new ArrayList<>(minHeap);
    }

    /**
     * 建议采用此种方式，数据量大时节省内存
     * @param datas
     * @param K
     * @param comparator
     * @param <T>
     * @return
     */
    public static <T> List<T> getTopK(Collection<T> datas, int K, Comparator<? super T> comparator) {
        PriorityQueue<T> minHeap = new PriorityQueue<>(K, comparator);
        for (T s: datas) {
            if (minHeap.size() < K || comparator.compare(s, minHeap.peek()) > 0) {
                if (minHeap.size() == K) {
                    minHeap.poll();
                }
                minHeap.offer(s);
            }
        }
        return new ArrayList<>(minHeap);
    }

}
