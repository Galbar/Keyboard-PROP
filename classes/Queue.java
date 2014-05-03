package classes;

import java.util.LinkedList;
class Queue <T>
{
	private LinkedList<T> queue;

	public void push(T a)
	{
		queue.add(a);
	}

	public void pop()
	{
		queue.pop();
	}

	public T front()
	{
		return queue.getFirst();
	}

	public boolean isEmpty()
	{
		return (0 < queue.size());
	}
}