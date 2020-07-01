package vn.rin.blog.config;

import org.hashids.Hashids;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Rin
 */
public class ID {

    public static void main(String[] args) {

        Map<String, Integer> map = new ConcurrentHashMap<>();

        AtomicInteger count = new AtomicInteger(0);
        boolean run[] = new boolean[]{true};

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            threads.add(new Thread(() -> {
                while (run[0]) {
                    String gen = gen(UUID.randomUUID().toString().replace("-", ""));
                    if (map.containsKey(gen))
                        map.put(gen, map.get(gen) + 1);
                    else
                        map.put(gen, 1);

                    System.out.println("key -> " + gen + "  count =>" + count.incrementAndGet());
                    if (count.get() > 100000) {
                        run[0] = false;
                        break;
                    }
                }
            }));
        }

        threads.forEach(thread -> {
            thread.start();
        });

        while (run[0]) {
            if (!run[0])
                break;
        }
        System.out.printf("out");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.forEach((s, integer) -> {
            System.out.println("id =>" + s + "  count =>" + integer);
            if (integer > 1)
                System.out.println("xxxx id =>" + s + "  count =>" + integer);

        });
    }

    public static String gen(String s) {
        Hashids hashids = new Hashids(s, 12, "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890");
        return hashids.encodeHex(hashids.encode(1, 2, 3));
    }
}
