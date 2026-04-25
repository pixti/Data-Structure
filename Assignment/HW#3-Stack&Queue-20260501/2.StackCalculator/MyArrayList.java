public class MyArrayList<E> {
    private Object[] data;
    private int count;

    public MyArrayList() {
        data = new Object[4];
        count = 0;
    }

    public void add(E item) {
        if (count == data.length) {
            Object[] newData = new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
        data[count] = item;
        count++;
    }

    public E get(int index) {
        return (E) data[index];
    }

    public int size() {
        return count;
    }
}