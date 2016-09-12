
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


//I am using this interface because when you actually implement Deque there
//are too many unused methods
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
//this class demonstrates a generic "wraparound" array double ended queue
public class CircularArrayDeque<T> implements DequeInterface<T>{
    //an array to hold data
    private T[] items;
    //variables to track specific info about above array
    private int currentSize, capacity, front, back;
    //used to set items size within noArg constructor
    private static final int DEFAULT_CAPACITY=10;
    //noArg constructor
    CircularArrayDeque(){
        //initialize array and variables
        items=(T[]) new Object[0];
        currentSize=0;
        capacity=DEFAULT_CAPACITY;
        front = 0;
        back = 0;
    }
    //overloading constructors
    //accepting any array of objects
    CircularArrayDeque(T[] array){
        //initialize array and variables
        items =(T[]) new Object[array.length];
        for(int i=0; i<array.length;i++)
            items[i]=array[i];
        currentSize=array.length;
        capacity = currentSize;
        front=0;
        back=currentSize-1;
    }
    //accepting specifically a primitive int array
    //this could be done for any primitive array
    CircularArrayDeque(int[] array){
        //initialize array and variables
        items = (T[]) new Object[array.length];
        for(int i=0; i<array.length; i++)
            items[i]=(T)Integer.valueOf(array[i]);
        currentSize=array.length;
        capacity = currentSize;
        front = 0;
        back = currentSize-1;
    }
    //accepting any collection of objects
    CircularArrayDeque(Collection<? extends Object> collection){
        //initialize the collection iterator, array and variables
        Iterator iterator = collection.iterator();
        items = (T[])new Object[collection.size()];
        int i=0;
        while(iterator.hasNext()){
            items[i]=(T)iterator.next();
            i++;
        }
        currentSize=items.length;
        capacity=currentSize;
        front=0;
        back=currentSize-1;   
    }
    //returns if items(array) is empty
    @Override
    public boolean isEmpty() {
        return currentSize==0;
    }
    //returns if items(array) is full
    public boolean isFull(){
        return currentSize==capacity;
    }
    //returns the front(variable) element of items if it exists
    @Override
    public T getFirst() {
        if(isEmpty())
            return null;
        else
            return items[front];     
    }
    //move front(variable) and add element to updated location
    @Override
    public void addFirst(T toAdd) {
         if(isFull())
             doubleQueue();
         front = decrement(front);
         items[front]=toAdd;
         currentSize++;
    }
    //remove element at front(variable) and update location
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
    //return last(variable) element if it exists
    @Override
    public T getLast() {
        if(isEmpty())
            return null;
        else
            return items[back];  
    }
    //manages array size, moves back(variable) and adds an elements
    @Override
    public void addLast(T toAdd) {
        if(isFull())
            doubleQueue();
        back = increment(back);
        items[back]=toAdd;
        currentSize++;
    }
    //removes back(variable) element and updates location
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
    //returns the element at index where index is relative to front(variable)
    //rather than to the beginning index of items(array)
    @Override
    public T get(int index) {
        return items[(front+index)%capacity];
    }
    //used to update location of front/back(variables)
    private int increment(int x){
        if(x+1==capacity)
            return 0;
        else
            return ++x;
    }
    //used to update location of front/back(variables)
    private int decrement(int x){
        if(x-1==-1)
            return capacity-1;
        else
            return --x;
    }
    //doubles items(array) size and copies contents
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
    //used to output information about items 
    public void printInfo(){
        System.out.println("The Array: "+Arrays.toString(items));
        System.out.println("Index of front: "+front);
        System.out.println("Index of back: "+back);
        System.out.println("Element at front: "+items[front]);
        System.out.println("Element at back: "+items[back]);
        System.out.println("Capacity: "+ capacity);
        System.out.println("CurrentSize: "+currentSize);
        System.out.println("------------------------------------------------"
                + "------------------------------");
    }
    public static void main(String[] args) {
        System.out.println("                                Begin testing");
        System.out.println("------------------------------------------------"
                + "------------------------------");
        int[] array = {8, 7, 5, 3, 6, 7, 12, 4};
        CircularArrayDeque cad = new CircularArrayDeque(array);
        System.out.println("Initial Array: ");
        cad.printInfo();
        cad.addLast(1);
        cad.addLast(2);
        cad.addLast(3);
        cad.removeLast();
        System.out.println("After addLast(1), addLast(2), addLast(3), removeLast():");
        cad.printInfo();
        cad.removeFirst();
        cad.addLast(3);
        System.out.println("After removeFirst(), addLast(3):");
        cad.printInfo();
        System.out.println("Result of get(5): "+cad.get(5));
        System.out.println("------------------------------------------------"
                + "------------------------------");
        System.out.println("                                End testing");
    }
}

