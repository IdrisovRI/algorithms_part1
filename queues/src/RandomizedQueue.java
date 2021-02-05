import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private Item[] new_queue;

    // construct an empty randomized queue
    public RandomizedQueue(){
        queue =(Item[]) new Object[1];
//        StdOut.println(1);
    }

    private void increaseMemoryAmount(){
        new_queue = (Item[]) new Object[queue.length+1];

        for(int i=0; i < queue.length; i++){
            new_queue[i] = queue[i];
        }
        queue = new_queue;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return queue[0] == null;
    }

    // return the number of items on the randomized queue
    public int size(){
        int size = 0;
        if(queue[0] != null){
            size = queue.length;
        }
        return size;
    }

    // add the item
    public void enqueue(Item item){
//        StdOut.print("enqueue start=");
//        for(Item t: queue)
//            StdOut.print("" + t + " ");
//        StdOut.println(" ");

        if (item == null)
            throw new IllegalArgumentException();

        if (queue[0] != null) {
            increaseMemoryAmount();
        }
        queue[queue.length-1] = item;
//        StdOut.print("enqueue end=");
//        for(Item t: queue)
//            StdOut.print("" + t + " ");
//        StdOut.println(" ");
    }

    // remove and return a random item
    public Item dequeue(){
        if(queue[0] == null)
            throw new NoSuchElementException();

        Item result;
        if (queue.length > 1) {
            int randomValue = StdRandom.uniform(queue.length);
            result = queue[randomValue];
            new_queue = (Item[]) new Object[queue.length - 1];
            int shift = 0;

            for (int i = 0; i < queue.length; i++) {
                if (i == randomValue) {
                    shift++;
                    continue;
                }
                new_queue[i-shift] = queue[i];
            }
            queue = new_queue;
        }
        else{
            result=queue[0];
            queue[0]=null;
        }
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(queue[0] == null)
            throw new NoSuchElementException();

        int randomValue = StdRandom.uniform(queue.length);
        return queue[randomValue];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Item[] randomQueue;;
        private int current = 0;

        private ListIterator(){
            randomQueue = (Item[]) new Object[queue.length];
            for(int t=0; t<queue.length; t++)
                randomQueue[t]=queue[t];

            int N = queue.length;

            for (int i = 0; i < N; i++)
            { // Exchange a[i] with random element in a[i..N-1]
                int r = i + StdRandom.uniform(N-i);
                Item temp = randomQueue[i];
                randomQueue[i] = randomQueue[r];
                randomQueue[r] = temp;
            }

//            StdOut.print("ListIterator start randomQueue=");
//            for(Item t: randomQueue)
//                StdOut.print("" + t + " ");
//            StdOut.println(" ");
//            for(Item t: queue)
//                StdOut.print("" + t + " ");
//            StdOut.println(" ");
//
//            StdOut.println("randomQueueLen="+randomQueue.length);
//            for(int i=0; i<queue.length; i++){
//                Item buff = queue[i];
//                StdOut.println("i=" + i + " buff="+ buff);
//                if (i > 0) {
//                    int randomVal = StdRandom.uniform(i);
//
//                    randomQueue[i] = queue[randomVal];
//                    randomQueue[randomVal] = buff;
//
//                    for(Item t: randomQueue)
//                        StdOut.print("" + t + " ");
//                    StdOut.println(" ");
//                }
//                else{
//                    randomQueue[0] = queue[0];
//
//                    for(Item t: randomQueue)
//                        StdOut.print("" + t + " ");
//                    StdOut.println(" ");
//                }
//            }
//
//            StdOut.print("ListIterator end randomQueue=");
//            for(Item t: randomQueue)
//                StdOut.print("" + t + " ");
//            StdOut.println(" ");

        }
        public boolean hasNext() { return (current < randomQueue.length) && randomQueue[0]!=null; }
        public void remove() {
            /* not supported */
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next()
        {
            if(current > randomQueue.length) throw new java.util.NoSuchElementException();

            Item item = randomQueue[current];
            current++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args){

        class Apple
        {
            public String Color;
            public Float Weight;

            public Apple(String color, float weight) {
                String Color = color;
                Float Weight = weight;
            }

            public String getColor(){
                return Color;
            }

            public Float getWeigth(){
                return Weight;
            }

        }

        RandomizedQueue<Apple> rq = new RandomizedQueue<Apple>();
        StdOut.println("isEmpty=" + rq.isEmpty());
        StdOut.println("Size=" + rq.size());
        rq.enqueue(new Apple("Red", 100));
        rq.enqueue(new Apple("Green", 110));
        rq.enqueue(new Apple("Orange", 50));
        rq.enqueue(new Apple("Red", 100));
        StdOut.println("isEmpty=" + rq.isEmpty());
        StdOut.println("Size=" + rq.size());
        StdOut.println("Sample=" + rq.sample());
        StdOut.println("Sample=" + rq.sample());
        StdOut.println("Sample=" + rq.sample());
        StdOut.println(" ");
        StdOut.println("dequeue=" + rq.dequeue());
        StdOut.println("Size=" + rq.size());
        StdOut.println("dequeue=" + rq.dequeue());
        StdOut.println("Size=" + rq.size());
        StdOut.println("dequeue=" + rq.dequeue());
        StdOut.println("Size=" + rq.size());
        StdOut.println("dequeue=" + rq.dequeue());
        StdOut.println("isEmpty=" + rq.isEmpty());
        StdOut.println("Size=" + rq.size());
        StdOut.println(" ");
        RandomizedQueue<Integer> rq2 = new RandomizedQueue<Integer>();
        StdOut.println("isEmpty=" + rq2.isEmpty());
        StdOut.println("Size=" + rq2.size());
        rq2.enqueue(296);
        rq2.enqueue(335);
        rq2.enqueue(408);
        StdOut.println("isEmpty=" + rq2.isEmpty());
        StdOut.println("Size=" + rq2.size());
        rq2.iterator();
        StdOut.println(" ");
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int j = 1; j <= 10; j++)
            queue.enqueue(j);
        Iterator<Integer> iterator = queue.iterator();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());
        iterator.next();
        StdOut.println("hasNext=" + iterator.hasNext());

        StdOut.println(" ");
        StdOut.println("---------0-------------------");
        RandomizedQueue<Integer> rq5 = new RandomizedQueue<Integer>();
        StdOut.println("---------1-------------------");
        rq5.iterator();
        StdOut.println("---------2-------------------");
        rq5.iterator();
        StdOut.println("---------3-------------------");
        rq5.enqueue(365);
        rq5.enqueue(231);
        rq5.enqueue(162);
        rq5.enqueue(238);
//        rq5.iterator();
//        rq5.iterator();
//        rq5.iterator();
        for (Integer item : rq5) {
            StdOut.println(item);
        }
//        StdOut.println(" ");
//        rq5.enqueue(236);
//        rq5.enqueue(317);
//        for (Integer item : rq5) {
//            StdOut.println(item);
//        }
    }

}
