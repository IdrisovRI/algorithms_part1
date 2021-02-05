import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] deck;
    private Item[] new_deck;
    private int current = 0;

    // construct an empty deque
    public Deque(){
        deck =(Item[]) new Object[1];
    }

    private void increaseMemoryAmount(int shift){
        new_deck = (Item[]) new Object[deck.length+1];

        for(int i=0; i < deck.length; i++){
            new_deck[i+shift] = deck[i];
        }
        deck = new_deck;
    }

    private void decreaseMemoryAmount(){
        new_deck = (Item[]) new Object[deck.length-1];
        for(int i=0; i < deck.length-1; i++){
            new_deck[i] = deck[i];
        }
        deck = new_deck;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return deck[0] == null;
    }

    // return the number of items on the deque
    public int size(){
        int size = 0;
        if(deck[0] != null){
            size = deck.length;
        }
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null)
            throw new IllegalArgumentException();

        if (deck[0] != null) {
            increaseMemoryAmount(1);
        }
        deck[0] = item;
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null)
            throw new IllegalArgumentException();
        if (deck[0] != null) {
            increaseMemoryAmount(0);
        }
        deck[deck.length-1] = item;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(deck[0] == null) throw new NoSuchElementException();

        Item removedValue = deck[0];

        if (deck.length == 1){
            deck[0] = null;
        }
        else {
            Item[] new_deck = (Item[]) new Object[deck.length - 1];

            //for avoid loitering
            deck[0]=null;

            for (int i = 1; i < deck.length; i++) {
                new_deck[i - 1] = deck[i];
                //for avoid loitering
                deck[i] = null;
            }
            deck = new_deck;
        }
        return removedValue;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(deck[0] == null) throw new NoSuchElementException();

        Item removedValue = deck[deck.length-1];

        if (deck.length == 1){
            deck[0] = null;
        }
        else {
            Item[] new_deck = (Item[]) new Object[deck.length - 1];

            //for avoid loitering
            deck[deck.length-1] = null;

            for (int i = 0; i < deck.length-1; i++) {
                new_deck[i] = deck[i];
                //for avoid loitering
                deck[i]=null;
            }
            deck = new_deck;
        }
        return removedValue;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item>
    {
        private int current = 0;
        public boolean hasNext() { return (current < deck.length && deck[0]!=null); }
        public void remove() {
            /* not supported */
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next()
        {
            if(current > deck.length) throw new java.util.NoSuchElementException();

            Item item = deck[current];
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

        Deque<Apple> de = new Deque<Apple>();
        StdOut.println("isEmpty=" + de.isEmpty());
        StdOut.println("size=" + de.size());
        de.addFirst(new Apple("Red", 100));
        de.addFirst(new Apple("Green", 110));
        de.addFirst(new Apple("Orange", 50));
        de.addLast(new Apple("Red", 100));
        StdOut.println("isEmpty=" + de.isEmpty());
        StdOut.println("size=" + de.size());
        de.addLast(new Apple("Red", 70));
        de.addLast(new Apple("Red", 60));
        de.removeLast();
        de.addFirst(new Apple("Orange", 200));
        de.removeFirst();
        StdOut.println("isEmpty=" + de.isEmpty());
        StdOut.println("size=" + de.size());

        Iterator iterator = de.iterator();
        StdOut.println("hasNext=" + iterator.hasNext());
        StdOut.println("iterator=" + iterator.next());
        StdOut.println("hasNext=" + iterator.hasNext());
        StdOut.println("iterator=" + iterator.next());
        StdOut.println("hasNext=" + iterator.hasNext());
        StdOut.println("iterator=" + iterator.next());
        StdOut.println("hasNext=" + iterator.hasNext());
        StdOut.println("iterator=" + iterator.next());
        StdOut.println("hasNext=" + iterator.hasNext());
        StdOut.println("iterator=" + iterator.next());
        StdOut.println("hasNext=" + iterator.hasNext());
    }

}
