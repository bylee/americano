package americano.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayIterator
 * 
 * 배열에 대해 {@link Iterator} 인터페이스로 접근할 수 있게 하는 객체
 *
 * @param <K> 배열의 타입
 */
public class
ArrayIterator<K>
implements Iterator<K>
{
	/**
	 * 순회할 테이타들
	 */
	protected final K[] objs;
	
	/**
	 * 현재 순회하는 위치
	 */
	protected int index = 0;
	
	/**
	 * 순회할 데이타들을 인자로 갖는 생성자
	 * 
	 * @param objs 순회할 데이타의 배열
	 */
	public
	ArrayIterator(
		final K[] objs
	)
	{
		this.objs = objs;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public
	boolean
	hasNext()
	{
		if ( null == objs )
		{
			return false;
		}
		return ( index < objs.length );
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public
	K
	next()
	{
		if ( null == this.objs )
		{
			throw new NoSuchElementException();
		}
		if ( objs.length <= index  )
		{
			throw new NoSuchElementException();
		}
		return objs[index++];
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public
	void
	remove()
	{
		throw new UnsupportedOperationException();
	}

}
