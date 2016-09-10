
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

interface DequeInterface<T>{
        public boolean isEmpty();
        public T getFirst();
        public void addFirst(T toAdd);
        public T removeFirst();
        public T getLast();
        public void addLast(T toAdd);
        public T removeLast();
        public T get(int index);
}
public class CircularArrayDeque<T> implements DequeInterface<T>{
    private T[] items;
    private int currentSize, capacity, front, back;
    private static final int DEFAULT_CAPACITY =10;
    CircularArrayDeque(){
        items=null;
        currentSize=0;
        capacity=DEFAULT_CAPACITY;
        front = 0;
        back = 0;
    }
    CircularArrayDeque(T[] array){
        items=array;
        currentSize=array.length;
        capacity = currentSize;
        front=0;
        back=currentSize-1;
    }
    @Override
    public boolean isEmpty() {
        return currentSize==0;
    }
    public boolean isFull(){
        return currentSize==capacity;
    }
    @Override
    public T getFirst() {
        if(isEmpty())
            return null;
        else
            return items[front];     
    }
    @Override
    public void addFirst(T toAdd) {
         if(isFull())
             doubleQueue();
         front = decrement(front);
         items[front]=toAdd;
         currentSize++;
    }
    @Override
    public T removeFirst() {
        if(isEmpty())
            return null;
        else{
            T toReturn = items[front];
            items[front]=null;
            front=increment(front);
            currentSize--;
            return toReturn;
        }
    }
    @Override
    public T getLast() {
        if(isEmpty())
            return null;
        else
            return items[back];  
    }
    @Override
    public void addLast(T toAdd) {
        if(isFull())
            doubleQueue();
        back = increment(back);
        items[back]=toAdd;
        currentSize++;
    }
    @Override
    public T removeLast() {
        if(isEmpty())
            return null;
        else{
            T toReturn = items[back];
            items[back]=null;
            back = decrement(back);
            currentSize--;
            return toReturn;
        }
    }
    @Override
    public T get(int index) {
        return items[(front+index)%capacity];
    }
    private int increment(int x){
        if(x+1==capacity)
            return 0;
        else
            return ++x;
    }
    private int decrement(int x){
        if(x-1==-1)
            return capacity-1;
        else
            return --x;
    }
    private void doubleQueue(){
        T[] newArray = (T[]) new Object[capacity*2];
        for(int i=0; i<currentSize;i++){
            newArray[i]=items[front];
            front = increment(front);
        }
        items=newArray;
        front=0;
        back=currentSize-1;
        capacity=capacity*2;
    }
    public void printItems(){
        System.out.println("--------------------");
        System.out.println(Arrays.toString(items));
        System.out.println("front: "+front);
        System.out.println("back: "+back);
        System.out.println("capacity: "+ capacity);
        System.out.println("currentsize: "+currentSize);
        System.out.println("---------------------");
    }
    public static void main(String[] args) {
        //testing
        Integer[] array = {8, 7, 5, 3, 6, 7, 12, 4};
        CircularArrayDeque cad = new CircularArrayDeque(array);
        cad.printItems();
        cad.addLast(1);
        cad.printItems();
    }
}

