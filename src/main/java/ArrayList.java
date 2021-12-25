import java.util.Iterator;

public class ArrayList <Entity> implements Iterable<Entity> {
	public static int DEFAULT_INIT_CAPACITY = 10;
	Object [] array;
	int size = 0;
	int capacity = 0;
	double resizeMultiplier = 1.5;

	public ArrayList () {
		this(DEFAULT_INIT_CAPACITY);
	}

	public ArrayList (int initCapacity) {
		array = new Object[initCapacity];
		size = 0;
		capacity = initCapacity;
	}

	public Entity get (int index) {
		checkRange(index);
		return (Entity) array[index];
	}

	public void set(int index, Entity entity) {
		checkRange(index);
		array[index] = entity;
	}

	public void add(Entity entity) {
		add(size, entity);
	}

	public void add(int index, Entity entity) {
//		checkRange(index);
		IndexOutOfRangeException.checkRange(index, 0, size + 1);
		addIncludingEnd(index, entity);
	}

	public void addIncludingEnd(int index, Entity entity) {
		IndexOutOfRangeException.checkRange(index, 0, size + 1);

		if (size < capacity) {
			if (index < size) {
				System.arraycopy(array, index, array, index + 1, size - index);
			}

			array[index] = entity;
			size++;
		} else {
			int newCapacity = (int)(capacity * resizeMultiplier);
			Object [] newArray = new Object [newCapacity];

			if (index > 0) {
				System.arraycopy(array, 0, newArray, 0, index);
			}

			newArray[index] = entity;

			if (index < size) {
				System.arraycopy(array, index, newArray, index + 1, size - index);
			}

			array = newArray;
			capacity = newCapacity;
			size++;
		}
	}

	public Entity remove(int index) {
		checkRange(index);
		Entity entity = get(index);

		if (index < size -1) {
			System.arraycopy(array, index + 1, array, index, size - index - 1);
		}

		size--;
		return entity;
	}

	void resize () {
			resize((int)(capacity * resizeMultiplier));
	}

	void resize (int newCapacity) {
			Object [] newArray = new Object [newCapacity];

			System.arraycopy(array, 0, newArray, 0, size);
			array = newArray;
			capacity = newCapacity;
	}

	/**
	 * Check if index belongs to range of it's ArrayList size
	 * @param index index
	 * @throws IndexOutOfRangeException if index is out of range
	 * */
	public void checkRange(int index) {
		IndexOutOfRangeException.checkRange(index, 0, size - 1);
	}

	public int size() {
		return size;
	}

	@Override
	public Iterator<Entity> iterator() {
		return new ArrayListIterator(this);
	}
}

class ArrayListIterator<Entity> implements Iterator<Entity> {
	ArrayList<Entity> arrayList;
	int iterationPosition = 0;

	ArrayListIterator(ArrayList<Entity> arrayList) {
		this.arrayList = arrayList;
	}

	@Override
	public boolean hasNext() {
		if (iterationPosition < arrayList.size) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Entity next() {
		arrayList.checkRange(iterationPosition);
		return arrayList.get(iterationPosition++);
	}

	@Override
	public void remove() {
		arrayList.remove(iterationPosition);
	}
}
