import java.util.Iterator;

public class LinkedList<Entity> implements Iterable<Entity> {
	LinkedListItem<Entity> first;
	LinkedListItem<Entity> last;
	int size = 0;

	public Entity add(Entity entity) {
		return add(size, entity);
	}

	public Entity add(int index, Entity entity) {
		IndexOutOfRangeException.checkRange(index, 0, size);

		LinkedListItem<Entity> newItem = new LinkedListItem<>(entity);

		if (first == null) {
			// creating the first item in empty LinkedList
			first = newItem;
			last = newItem;
		} else if (index == 0) {
			// creating item at 1st position
			join(newItem, first);
			first = newItem;
		} else if (index < size) {
			// creating non-first non-after-last item
			LinkedListItem<Entity> positionItem = getItem(index);
			join(positionItem.previous, newItem);
			join(newItem, positionItem);
		} else if (index == size) {
			// creating item after last existing item
			join(last, newItem);
			last = newItem;
		}

		size++;
		return newItem.value;
	}

	/* Join 2 Items to become neighbors */
	public void join(LinkedListItem<Entity> previous, LinkedListItem<Entity> next) {
		if (previous != null) {
			previous.next = next;
		}

		if (next != null) {
			next.previous = previous;
		}
	}

	public Entity get(int index) {
		return getItem(index).value;
	}

	protected LinkedListItem<Entity> getItem(int index) {
		checkRange(index);

		if (index == size -1) {
			return last;
		}

		LinkedListItem<Entity> item = first;

		for (int i = 0; i < index && item != null; i++) {
			item = item.next;
		}

		return item;
	}

	/**
	 * Check if index belongs to range of it's LinkedList size
	 * @param index index
	 * @throws IndexOutOfRangeException if index is out of range
	 * */
	public void checkRange(int index) {
		IndexOutOfRangeException.checkRange(index, 0, size - 1);
	}

	public Entity remove(int index) {
		return removeItem(getItem(index));
	}

	protected Entity removeItem(LinkedListItem<Entity> item) {
		if (first == last) {
			first = null;
			last = null;
		} else if (item == first) {
			first = item.next;
			first.previous = null;
		} else if (item == last) {
			last = item.previous;
			last.next = null;
		} else {
			item.previous.next = item.next;
			item.next.previous = item.previous;
		}

		size--;
		return item.value;
	}

	@Override
	public Iterator<Entity> iterator() {
		return new LinkedListIterator(this);
	}

	@Override
	public LinkedList<Entity> clone() {
		return null;
	}

	public LinkedList<Entity> addAll(LinkedList<Entity> linkedList) {
		for (Entity entity : linkedList) {
			add(entity);
		}

		return this;
	}

	public int size() {
		return size;
	}
}

class LinkedListItem<Entity> {
	LinkedListItem<Entity> previous;
	LinkedListItem<Entity> next;
	Entity value;

	public LinkedListItem (Entity value) {
		this.value = value;
	}
}

class LinkedListIterator <Entity> implements Iterator<Entity> {
	LinkedList<Entity> linkedList;
	LinkedListItem<Entity> next;
	int iterationPosition = 0;

	LinkedListIterator(LinkedList<Entity> linkedList) {
		this.linkedList = linkedList;
		next = linkedList.first;
		iterationPosition = 0;
	}

	@Override
	public boolean hasNext() {
		if (next == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Entity next() {
		if (next == null) {
			return null;
		}

		LinkedListItem<Entity> current = next;
		next = next.next;
		return current.value;
	}

	@Override
	public void remove() {
		try {
			linkedList.removeItem(next);
		} catch (IndexOutOfRangeException e) {
			e.printStackTrace();
		}
	}
}
