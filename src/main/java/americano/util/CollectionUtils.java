package americano.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * {@link Collection}에 대한 Helper API를 제공하는 클래스
 * 
 * 여기에서 Collection 이라 함은 다음을 지칭한다.
 * 
 * - 배열
 * - java.util.Collection을 상속한 객체
 *
 */
public class
CollectionUtils
implements ICollectionConstants, IStringConstants
{
		
	protected CollectionUtils() {}
	
	private static final String ARRAY_START = "{";
	
	private static final String ARRAY_END = "}";
	
	private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;
	
	private static final String ARRAY_ELEMENT_SEPARATOR = ", ";

	private static final Set<Class<?>> approximableCollectionTypes = 
		Collections.unmodifiableSet( new HashSet<Class<?>>( Arrays.asList( new Class<?>[] {
			Collection.class,
			Set.class, HashSet.class, SortedSet.class, LinkedHashSet.class, TreeSet.class,
			List.class, LinkedList.class, ArrayList.class
		} ) ) );

	private static final Set<Class<?>> approximableMapTypes = 
		Collections.unmodifiableSet( new HashSet<Class<?>>( Arrays.asList( new Class<?>[] {
			Map.class, SortedMap.class, HashMap.class, LinkedHashMap.class, TreeMap.class
		} ) ) );
	
	
    /**
     * <code>keys</code>와 <code>values</code>에 대한 맵을 반환한다.
     * 
     * @param <K> 맵의 키 타입
     * @param <V> 맵의 값 타입
     * @param keys 맵의 키들
     * @param values 맵의 값들
     * 
     * @return 생성된 맵
     */
    public static <K, V>
    Map<K, V>
    asMap(
    	final K[] keys,
    	final V[] values
    )
    {
    	final HashMap<K, V> map = new HashMap<K, V>();
    	if ( null == keys || null == values )
    	{
    		return map;
    	}
    	
    	for ( int i=0, n=Math.min( keys.length, values.length ) ; i<n ; ++i )
    	{
    		map.put( keys[i], values[i] );
    	}
    	return map;
    }
    
	/**
	 * <code>objs</code>를 Map<A, B>에 담아 반환한다.
	 * 
	 * @param <A> 맵의 키 타입
	 * @param <B> 맵의 값 타입
	 * @param objs 맵에 들어갈 인자들
	 * 
	 * @return 생성된 값들
	 */
	@SuppressWarnings("unchecked")
	public static <A extends Object, B extends Object>
	Map<A, B>
	asMap(
		final Object[][] objs
	)
	{
    	final HashMap<A, B> map = new HashMap<A, B>();
    	for ( int i=0, n=length( objs ) ; i<n ; ++i )
    	{
    		if ( 2 != objs[i].length )
    		{
    			continue;
    		}
    		map.put( (A) objs[i][0], (B) objs[i][1] );
    	}
    	return map;
    }
	
	/**
	 * 배열 객체 <code>objs</code>가 처리할 element를 갖지 않는지 검사한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우도 처리할 것이 없는 것으로
	 * 
	 * 간주하여 <code>null</code>를 반환한다.
	 * 
	 * @param objs 검사할 배열 객체
	 * 
	 * @return <code>objs</code>가 비어있는지 여부
	 * 
	 * @see CollectionUtils#isEmpty(Collection)
	 */
	public static
	boolean
	isEmpty(
		final Object[] objs
	)
	{
		return ( 0 == length( objs ) );
	}
	
	
	/**
	 * Collection 객체 <code>col</code>가 처리할 element를 갖지 않는지 검사한다.
	 * 
	 * <code>col</code>가 <code>null</code>인 경우도 처리할 것이 없는 것으로
	 * 
	 * 간주하여 <code>null</code>를 반환한다.
	 * 
	 * @param col 검사할 Collection 객체
	 * 
	 * @return <code>col</code>가 비어있는지 여부
	 * 
	 * @see CollectionUtils#isEmpty(Object[])
	 */
	public static
	boolean
	isEmpty(
		final Collection<?> col
	)
	{
		return ( 0 == length( col ) );
	}
	
	/**
	 * <code>map</code>가 비어있는지 여부를 반환한다.
	 * 
	 * <code>map</code>가 <code>null</code>인 경우, <code>true</code>를 반환한다.
	 * 
	 * @param map 검사할 Map객체
	 * 
	 * @return 비어있는지 여부
	 */
	public static
	boolean
	isEmpty(
		final Map<?, ?> map
	)
	{
		return ( 0 == length( map ) );
	}
	
	/**
	 * 객체 <code>obj</code>가 배열이나 {@link Collection}이면 그 크기를 반환한다.
	 * 
	 * 그 외의 타입이면 <code>0</code>를 반환한다.
	 * 
	 * @param obj 검사할 객체
	 * 
	 * @return 객체의 크기
	 */
	public static
	int
	length(
		final Object obj
	)
	{
		if ( obj instanceof Object[] )
		{
			return length( ( Object[] ) obj );
		}
		else if ( obj instanceof boolean[] )
		{
			return length( ( boolean[] ) obj );
		}
		else if ( obj instanceof byte[] )
		{
			return length( ( byte[] )obj );
		}
		else if ( obj instanceof char[] )
		{
			return length( ( char[] ) obj );
		}
		else if ( obj instanceof short[] )
		{
			return length( ( short[] )obj );
		}
		else if ( obj instanceof int[] )
		{
			return length( (int[] ) obj );
		}
		else if ( obj instanceof long[] )
		{
			return length( ( long[] ) obj );
		}
		else if ( obj instanceof float[] )
		{
			return length( (float[] ) obj );
		}
		else if ( obj instanceof double[] )
		{
			return length( ( double[] ) obj );
		}
		else if ( obj instanceof Collection )
		{
			return length( (Collection<?>) obj );
		}
		return 0;
	
	}
	
	/**
	 * boolean 배열의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param objs 크기를 확인할 boolean 배열 객체
	 * 
	 * @return 배열의 크기
	 */
	public static
	int
	length(
		final boolean[] objs
	) {
		if ( null == objs )
		{
			return 0;
		}
		return objs.length;
	}
	/**
	 * byte 배열의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param objs 크기를 확인할 byte 배열 객체
	 * 
	 * @return 배열의 크기
	 */
	public static
	int
	length(
		final byte[] objs
	) {
		if ( null == objs )
		{
			return 0;
		}
		return objs.length;
	}
	/**
	 * char 배열의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param objs 크기를 확인할 char 배열 객체
	 * 
	 * @return 배열의 크기
	 */
	public static
	int
	length(
		final char[] objs
	) {
		if ( null == objs )
		{
			return 0;
		}
		return objs.length;
	}
	
	/**
	 * short 배열의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param objs 크기를 확인할 short 배열 객체
	 * 
	 * @return 배열의 크기
	 */
	public static
	int
	length(
		final short[] objs
	) {
		if ( null == objs )
		{
			return 0;
		}
		return objs.length;
	}
	
	/**
	 * int 배열의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param objs 크기를 확인할 int 배열 객체
	 * 
	 * @return 배열의 크기
	 */
	public static
	int
	length(
		final int[] objs
	) {
		if ( null == objs )
		{
			return 0;
		}
		return objs.length;
	}
	
	/**
	 * long 배열의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param objs 크기를 확인할 long 배열 객체
	 * 
	 * @return 배열의 크기
	 */
	public static
	int
	length(
		final long[] objs
	) {
		if ( null == objs )
		{
			return 0;
		}
		return objs.length;
	}
	
	/**
	 * float 배열의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param objs 크기를 확인할 float 배열 객체
	 * 
	 * @return 배열의 크기
	 */
	public static
	int
	length(
		final float[] objs
	) {
		if ( null == objs )
		{
			return 0;
		}
		return objs.length;
	}
	
	/**
	 * double 배열의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param objs 크기를 확인할 double 배열 객체
	 * 
	 * @return 배열의 크기
	 */
	public static
	int
	length(
		final double[] objs
	) {
		if ( null == objs )
		{
			return 0;
		}
		return objs.length;
	}
	
	/**
	 * 배열의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param objs 크기를 확인할 객체 배열 객체
	 * 
	 * @return 배열의 크기
	 */
	public static <T>
	int
	length(
		final T[] objs
	) {
		if ( null == objs )
		{
			return 0;
		}
		return objs.length;
	}
	

	/**
	 * Collection 객체에 담겨있는 객체의 갯수를 반환한다.
	 * 
	 * 객체가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * <code>collection</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param collection 객체의 갯수를 셀 Collection
	 * 
	 * @return 객체들의 갯수
	 */
	public static
	int
	length(
		final Collection<?> collection
	) {
		if ( null == collection )
		{
			return 0;
		}
		return collection.size();
	}

	/**
	 * Map 객체에 담겨있는 객체의 갯수를 반환한다.
	 * 
	 * <code>map</code>가 <code>null</code>인 경우, 0을 반환한다.
	 * 
	 * @param map 객체의 갯수를 셀 맵
	 * 
	 * @return 객체들의 갯수
	 */
	public static
	int
	length(
		final Map<?, ?> map
	)
	{
		if ( null == map )
		{
			return 0;
		}
		return map.size();
	}
	
	/**
	 * <code>params</code>가 <code>null</code>이면, 빈 Object[]를 반환한다.
	 * 
	 * @param params 검사할 Object배열
	 * 
	 * @return <code>null</code>가 아닌 Object배열
	 */
	protected static 
	Object[] 
	nvl(
		final Object[] params
	)
	{
		return ObjectUtils.nvl( params, EMPTY_OBJECT_ARRAY );
	}

	/**
	 * <code>array</code>에 <code>obj</code>를 추가한 새로운 배열을 생성한다.
	 * 
	 * <code>array</code>가 <code>null</code>인 경우는 <code>obj</code>만 갖는 배열을 생성한다. 
	 * 
	 * @param array 추가할 배열
	 * @param obj 추가할 객체
	 * 
	 * @return 새로 생성된 배열 객체
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[]
	add(
		final Object[] array,
		final Object obj
	)
	{
		Class<?> compType = Object.class;
		if ( null != array )
		{
			compType = array.getClass().getComponentType();
		}
		else if ( null != obj )
		{
			compType = obj.getClass();
		}

		final int newArrLength = length( array ) + 1;
		final Object[] newArr = 
			(Object[]) Array.newInstance( compType, newArrLength );
		
		if ( null != array )
		{
			System.arraycopy( array, 0, newArr, 0, array.length );
		}
		newArr[newArrLength - 1] = obj;
		return (T[]) newArr;
	}
	
	/**
	 * 배열 객체 <code>array</code>에서 <code>startIndex</code>부터 <code>endIndex</code>까지의
	 * 
	 * 객체들을 제거한 배열을 반환한다.
	 * 
	 * @param array 객체를 제거할 배열
	 * @param startIndex 제거할 범위의 시작
	 * @param endIndex 제거할 범위의 마지막
	 * 
	 * @return 지정한 범위의 객체들을 제거한 배열
	 * 
	 * @throws ArrayIndexOutOfBoundsException
	 * 		지정한 <code>startIndex</code>나 <code>endIndex</code>가
	 * <code>array</code>의 범위를 벗어난 경우
	 * @throws IllegalArgumentException
	 * 		<code>startIndex</code>가 <code>endIndex</code>보다 큰 경우
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[]
	remove(
		final Object[] array,
		final int startIndex,
		final int endIndex
	)
	{
		if ( endIndex < startIndex )
		{
			throw new IllegalArgumentException(
				"start indnex(" + startIndex + ") is greater than end index(" + endIndex + ")"
			);
		}
		Class<?> compType = Object.class;
		if ( null != array )
		{
			compType = array.getClass().getComponentType();
		}
		
		int removeSize = endIndex - startIndex;

		final int newArrLength = length( array ) - removeSize;
		
		final Object[] newArr = 
			(Object[]) Array.newInstance( compType, newArrLength );
		
		System.arraycopy( array, 0, newArr, 0, startIndex );
		System.arraycopy( array, endIndex, newArr, startIndex, array.length-endIndex );
		
		return (T[]) newArr;
	}

	/**
	 * 배열 객체 <code>array</code>에서 <code>index</code>번째 객체를 제거한 배열을 반환한다.
	 * 
	 * @param array 객체를 제거할 배열
	 * @param index 제거할 객체의 인덱스
	 * 
	 * @return 객체가 제거된 배열
	 * 
	 * @throws ArrayIndexOutOfBoundsException 지정한 <code>index</code>가 <code>array</code>의 범위를 벗어난 경우
	 */
	public static <T> T[]
	remove(
		final Object[] array,
		final int index
	)
	{
		return remove( array, index, index + 1);
	}
	
	/**
	 * <code>source</code> 객체를 객체 배열로 바꾼다.
	 * 
	 * 기본적으로 배열이 아닌 경우, 예외가 발생한다.
	 * 
	 * @param source 바꿀 객체 
	 * 
	 * @return 변환된 배열 객체
	 */
	public static
	Object[]
	toObjectArray(
		final Object source
	)
	{
		if ( source instanceof Object[] )
		{
			return ( Object[] )source; 
		}

		if ( null == source )
		{
			return EMPTY_OBJECT_ARRAY; 
		}

		Assert.isTrue(
			source.getClass().isArray(),
			"Source is not an array: " + source
		);

		final int length = Array.getLength( source );
		if ( 0 == length )
		{
			return EMPTY_OBJECT_ARRAY; 
		}
		
		final Class<?> wrapperType= Array.get( source, 0 ).getClass();
		
		final Object[] newArray =
			(Object[]) Array.newInstance( wrapperType, length );
		for( int i=0 ; i<length ; ++i )
		{
			newArray[i] = Array.get( source, i );
		}
		
		return newArray;
	}


	/**
	 * 배열을 리스트로 변환한다.
	 * 
	 * @param source 변환할 배열 객체
	 * 
	 * @return 변환된 리스트
	 */
	public static
	List<?>
	arrayToList(
		final Object source
	)
	{
		return Arrays.asList( toObjectArray( source ) );
	}

	/**
	 * 배열 <code>array</code>의 객체들을 Collection <code>collection</code>에 추가한다.
	 * 
	 * @param array 추가할 배열 객체
	 * @param collection 추가할 Collection 객체
	 */
	public static
	void
	mergeArrayIntoCollection(
		final Object array,
		final Collection<Object> collection
	)
	{
		Assert.notNull( collection, "Collection must not be null" ); 

		final Object[] arr = toObjectArray( array );
		for ( int i=0, n=arr.length ; i<n ; ++i )
		{
			collection.add( arr[i] );
		}
	}

	/**
	 * Properties 객체 <code>props</code>를 Map객체 <code>map</code>에 추가한다.
	 * 
	 * @param props 추가할 Properties 객체
	 * @param map 추가할 Map 객체
	 */
	public static
	void
	mergePropertiesIntoMap(
		final Properties props,
		final Map<Object, Object> map
	)
	{
		Assert.notNull( map, "Map must not be null" ); 
		if ( null == props )
		{
			return ;
		}
		final Enumeration<?> en = props.propertyNames();
		while (  en.hasMoreElements() )
		{
			final String key = (String) en.nextElement();
			map.put( key, props.getProperty( key ) );
		}
	}

	/**
	 * <code>iterator</code>가 순회하는 객체들 중에 <code>element</code>가
	 * 
	 * 있는지 여부를 반환한다.
	 * 
	 * @param iterator 순회하는 Iterator
	 * @param element 비교할 객체
	 * 
	 * @return 객체의 존재 여부
	 */
	public static
	boolean
	contains(
		final Iterator<Object> iterator,
		final Object element
	)
	{
		if ( null == iterator )
		{
			return false;
		}
		
		while ( iterator.hasNext() )
		{
			final Object candidate = iterator.next();
			if ( ObjectUtils.equals( candidate, element ) ) 
			{
				return true; 
			}
		}
		return false;
	}

	/**
	 * <code>enumeration</code>가 순회하는 객체들 중에 <code>element</code>가
	 * 
	 * 있는지 여부를 반환한다.
	 * 
	 * @param enumeration 순회하는 Enumeration
	 * @param element 비교할 객체
	 * 
	 * @return 객체의 존재 여부
	 */
	public static
	boolean
	contains(
		final Enumeration<Object> enumeration,
		final Object element
	)
	{
		if ( null == enumeration )
		{
			return false;
		}
		while( enumeration.hasMoreElements() )
		{
			final Object candidate = enumeration.nextElement();
			if ( ObjectUtils.equals( candidate, element ) )
			{
				return true; 
			}
		}
		return false;
	}

	/**
	 * <code>collection</code>에 <code>element</code>가 포함되어 있는지 여부를 반환한다.
	 * 
	 * @param collection 검사할 배열
	 * @param element 검사할 객체
	 * 
	 * @return 포함 여부
	 */
	public static <K, V>
	boolean
	contains(
		final K[] collection,
		final V element
	)
	{
		if ( null == collection )
		{
			return false;
		}
		
		for ( final Object candidate : collection )
		{
			if ( ObjectUtils.equals( candidate, element ) )
			{
				return true; 
			}
		}
		return false;
	}
	
	/**
	 * <code>collection</code>에 <code>element</code>가 포함되어 있는지 여부를 반환한다.
	 * 
	 * @param collection 검사할 Collection객체
	 * @param element 검사할 객체
	 * 
	 * @return 포함 여부
	 */
	public static
	boolean
	contains(
		final Collection<Object> collection,
		final Object element
	)
	{
		if ( null == collection )
		{
			return false;
		}
		for ( final Object candidate : collection )
		{
			if ( ObjectUtils.equals( candidate, element ) )
			{
				return true; 
			}
		}
		return false;
	}
	
	/**
	 * 배열 <code>collection</code>에서 <code>index</code> 번째 객체를 반환한다.
	 * 
	 * <code>index</code>가 <code>collection</code>의 범위를 벗어나는 경우,
	 * 
	 * <code>null</code>을 반환한다.
	 * 
	 * @param <K> 처리할 타입
	 * @param collection 배열
	 * @param index 배열 인덱스
	 * @return 배열의 <code>index</code>번째 객체
	 */
	public static <K>
	K
	get(
		final K[] collection,
		final int index
	)
	{
		if ( null == collection )
		{
			return null;
		}
		if ( index < 0 )
		{
			return null;
		}
		if ( collection.length <= index )
		{
			return null;
		}
		
		return collection[index];
	}

	/**
	 * <code>source</code>에서 <code>candidates</code> 내의 객체들 중에
	 * 
	 * 하나라도 포함하는지 여부를 반환한다.
	 * 
	 * @param source 검사할 Collection객체
	 * @param candidates 포함되어 있는지 확인할 객체들의 Collection 객체
	 * 
	 * @return 포함여부
	 */
	public static
	boolean
	containsAny(
		final Collection<?> source,
		final Collection<?> candidates
	)
	{
		if ( isEmpty( source ) || isEmpty( candidates) )
		{
			return false; 
		}

		for ( final Object candidate : candidates )
		{
			if ( source.contains( candidate ) )
			{
				return true; 
			}
		}
		return false;
	}

	/**
	 * <code>source</code>에서 <code>candidates</code>내의 객체중에
	 * 
	 * 가장 처음으로 일치하는 객체를 반환한다.
	 * 
	 * @param source 검사할 Collection객체
	 * @param candidates 포함되어 있는지 확인할 객체들의 Collection 객체
	 * 
	 * @return 처음으로 일치하는 객체
	 */
	public static <K>
	K
	findFirstMatch(
		final Collection<K> source,
		final Collection<K> candidates
	)
	{
		if ( isEmpty( source ) || isEmpty( candidates ) )
		{
			return null; 
		}

		for ( final K candidate : candidates  )
		{
			if ( source.contains( candidate ) )
			{
				return candidate; 
			}
		}
		return null;
	}

	/**
	 * <code>collection</code>내의 객체중에서 <code>type</code> 타입인
	 * 
	 * 객체를 반환한다.
	 * 
	 * <code>collection</code>내에 <code>type</code> 타입이 둘 이상인 경우는 <code>null</code>를 반환한다.
	 * 
	 * @param <K> 찾으려는 타입
	 * @param collection 검색할 Collection객체
	 * @param type 찾으려는 타입의 클래스
	 * 
	 * @return <code>type</code>클래스의 객체
	 */
	@SuppressWarnings("unchecked")
	public static <K>
	K
	findValueOfType(
		final Collection<?> collection,
		final Class<K> type
	)
	{
		if ( isEmpty( collection ) )
		{
			return null; 
		}

		K value = null;
		for ( final Object obj : collection )
		{
			if ( null == type || type.isInstance( obj ) )
			{
				if ( null != value )
				{
					// 지정한 타입이 둘이상인 경우, null을 반환한다.
					return null;
				}
				value = (K) obj;
			}
		}
		return value;
	}

	/**
	 * <code>collection</code>내의 객체중, <code>types</code> 클래스들 중
	 * 
	 * 하나의 객체이면 그 객체를 반환한다.
	 * 
	 * @param collection 검색할 Collection객체
	 * @param types 찾으려는 타입의 클래스들
	 * 
	 * @return <code>types</code> 타입 중 하나인 객체
	 * 
	 * @see CollectionUtils#findValueOfType( Collection, Class )
	 */
	public static
	Object
	findValueOfType(
		final Collection<?> collection,
		final Class<?>[] types
	)
	{
		if ( isEmpty( collection ) || isEmpty( types ) )
		{
			return null; 
		}
	
		for ( final Class<?> type : types ) {
			final Object value = findValueOfType( collection, type );
			if ( null == value )
			{
				continue;
			}
			return value; 
		}
		return null;
	}

	/**
	 * <code>collection</code>내에 하나의 객체만을 포함하는지 여부를 반환한다.
	 * 
	 * 동일한 객체가 여러번 할당되어 들어간 경우는 <code>true</code>를 반환한다.
	 * 
	 * @param collection 검사할 Collection객체
	 * 
	 * @return 들어가 있는 객체가 하나인지 여부
	 */
	public static
	boolean
	hasUniqueObject(
		final Collection<?> collection
	)
	{
		if ( isEmpty( collection ) )
		{
			return false; 
		}

		boolean hasCandidate = false;
		Object candidate = null;
		for ( final Object elem : collection )
		{
			
			if ( !hasCandidate )
			{
				hasCandidate = true;
				candidate = elem;
			}
			else if ( candidate != elem )
			{
				return false; 
			}
		}
		return true;
	}

	/**
	 * <code>collectionType</code>가 ApproximableCollection 타입인지 여부를 반환한다.
	 * 
	 * @param collectionType 검사할 클래스
	 * 
	 * @return ApproximableCollection 타입 여부
	 */
	public static
	boolean
	isApproximableCollectionType(
		final Class<?> collectionType
	)
	{
		return approximableCollectionTypes.contains( collectionType );
	}
	
	/**
	 * <code>mapType</code>가 ApproximableMap타입인지 여부를 반환한다.
	 * 
	 * @param mapType 검사할 클래스
	 * 
	 * @return ApproximableMap 타입 여부
	 */
	public static
	boolean
	isApproximableMapType(
		final Class<?> mapType
	)
	{
		return approximableMapTypes.contains( mapType );
	}
	
	/**
	 * <code>collection</code>에 해당하는 ApproximableCollection 타입을 생성한다.
	 * 
	 * @param <K> 담을 객체 타입
	 * @param collection Collection인자
	 * @param initialCapacity 생성할 Collection의 초기 인자
	 * 
	 * @return 생성한 ApproximableCollection
	 */
	public static <K>
	Collection<K>
	createApproximableCollection(
		final Collection<K> collection,
		final int initialCapacity
	)
	{
		if ( collection instanceof LinkedList<?> )
		{
			return new LinkedList<K>();
		}
		else if ( collection instanceof List<?> )
		{
			return new ArrayList<K>( initialCapacity);
		}
		else if ( collection instanceof SortedSet<?> )
		{
			return new TreeSet<K>( ( (SortedSet<K>)collection).comparator() );
		}
		else
		{
			return new LinkedHashSet<K>( initialCapacity);
		}
	}
	
	/**
	 * @param <K> 맵의 Key 타입
	 * @param <V> 맵의 Value 타입
	 * @param map Map인자
	 * @param initialCapacity 생성할 Map의 초기인자
	 * 
	 * @return 생성한 ApproximableMap
	 */
	public static <K, V>
	Map<K, V>
	createApproximableMap(
		final Map<K, V> map,
		final int initialCapacity
	)
	{
		if ( map instanceof SortedMap<?, ?> )
		{
			return new TreeMap<K, V>( ( (SortedMap<K, V>) map).comparator() );
		}
		else
		{
			return new LinkedHashMap<K, V>( initialCapacity );
		}
	}
	
	
	/**
	 * 객체 <code>obj</code>가 배열이나 {@link Collection}이면 그 크기를 반환한다.
	 * 
	 * 그 외의 타입이면 <code>0</code>를 반환한다.
	 * 
	 * @param obj 검사할 객체
	 * 
	 * @return 객체의 크기
	 * 
	 * @see CollectionUtils#length(Object)
	 */
	public static
	int
	size( final Object objs )
	{
		return length( objs );
	}

	/**
	 * Collection객체 <code>col</code>의 크기를 반환한다.
	 * 
	 * <code>col</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param col 크기를 확인할 Collection 객체
	 * 
	 * @return <code>col</code>의 크기
	 * 
	 * @see CollectionUtils#size(Object[])
	 */
	public static
	int
	size(
		final Collection<?> col
	)
	{
		return length( col );
	}
	
	/**
	 * bollean 배열 객체 <code>objs</code>의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param objs 크기를 확인할 boolean 배열 객체
	 * 
	 * @return <code>objs</code>의 크기
	 * 
	 * @see CollectionUtils#length(boolean[])
	 */
	public static
	int
	size(
		final boolean[] objs
	)
	{
		return length( objs );
	}
	
	/**
	 * byte 배열 객체 <code>objs</code>의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param objs 크기를 확인할 byte 배열 객체
	 * 
	 * @return <code>objs</code>의 크기
	 * 
	 * @see CollectionUtils#length(byte[])
	 */
	public static
	int
	size(
		final byte[] objs
	)
	{
		return length( objs );
	}
	
	/**
	 * char 배열 객체 <code>objs</code>의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param objs 크기를 확인할 char 배열 객체
	 * 
	 * @return <code>objs</code>의 크기
	 * 
	 * @see CollectionUtils#length(char[])
	 */
	public static
	int
	size(
		final char[] objs
	)
	{
		return length( objs );
	}
	
	/**
	 * short 배열 객체 <code>objs</code>의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param objs 크기를 확인할 short 배열 객체
	 * 
	 * @return <code>objs</code>의 크기
	 * 
	 * @see CollectionUtils#length(short[])
	 */
	public static
	int
	size(
		final short[] objs
	)
	{
		return length( objs );
	}
	
	/**
	 * int 배열 객체 <code>objs</code>의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param objs 크기를 확인할 int 배열 객체
	 * 
	 * @return <code>objs</code>의 크기
	 * 
	 * @see CollectionUtils#length(int[])
	 */
	public static
	int
	size(
		final int[] objs
	)
	{
		return length( objs );
	}
	
	/**
	 * long 배열 객체 <code>objs</code>의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param objs 크기를 확인할 long 배열 객체
	 * 
	 * @return <code>objs</code>의 크기
	 * 
	 * @see CollectionUtils#length(long[])
	 */
	public static
	int
	size(
		final long[] objs
	)
	{
		return length( objs );
	}
	
	/**
	 * float 배열 객체 <code>objs</code>의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param objs 크기를 확인할 float 배열 객체
	 * 
	 * @return <code>objs</code>의 크기
	 * 
	 * @see CollectionUtils#length(float[])
	 */
	public static
	int
	size(
		final float[] objs
	)
	{
		return length( objs );
	}
	
	/**
	 * double 배열 객체 <code>objs</code>의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param objs 크기를 확인할 double 배열 객체
	 * 
	 * @return <code>objs</code>의 크기
	 * 
	 * @see CollectionUtils#length(double[])
	 */
	public static
	int
	size(
		final double[] objs
	)
	{
		return length( objs );
	}
	
	/**
	 * 배열 객체 <code>objs</code>의 크기를 반환한다.
	 * 
	 * <code>objs</code>가 <code>null</code>인 경우에, <code>0</code>을 반환한다.
	 * 
	 * @param objs 크기를 확인할 배열 객체
	 * 
	 * @return <code>objs</code>의 크기
	 * 
	 * @see CollectionUtils#size(Collection)
	 */
	public static <T>
	int
	size(
		final T[] objs
	)
	{
		return length( objs );
	}

	
	/* 출력 */
	
	/**
	 * 객체 <code>obj</code>를 문자열로 변환한다.
	 * 
	 * @param obj 변환할 객체
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	toString(
		final Object obj
	)
	{
		if ( null == obj )
		{
			return NULL_STRING;
		}

		if ( obj instanceof String )
		{
			return (String) obj;
		}
		else if ( obj instanceof Object[] )
		{
			return toString( ( Object[] ) obj );
		}
		else if ( obj instanceof boolean[] )
		{
			return toString( ( boolean[] ) obj );
		}
		else if ( obj instanceof byte[] )
		{
			return toString( ( byte[] )obj );
		}
		else if ( obj instanceof char[] )
		{
			return toString( ( char[] ) obj );
		}
		else if ( obj instanceof double[] )
		{
			return toString( ( double[] ) obj );
		}
		else if ( obj instanceof float[] )
		{
			return toString( (float[] ) obj );
		}
		else if ( obj instanceof int[] )
		{
			return toString( (int[] ) obj );
		}
		else if ( obj instanceof long[] )
		{
			return toString( ( long[] ) obj );
		}
		else if ( obj instanceof short[] )
		{
			return toString( ( short[] )obj );
		}
		return StringUtils.nvl( obj.toString() );
	}
	
	/**
	 * boolean 배열 <code>array</code>를 문자열로 변환한다.
	 * 
	 * @param array 변환할 boolean 배열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	toString(
		final boolean[] array
	)
	{
		if ( null == array )
		{
			return NULL_STRING;
		}
		
		final int length = array.length;
		
		if ( 0 == length )
		{
			return EMPTY_ARRAY; 
		}

		final StringBuilder buffer= new StringBuilder();
		
		buffer.append( ARRAY_START);
		for ( int i=0 ; i<length ; ++i )
		{
			if ( 0 != i ) {
				buffer.append( ARRAY_ELEMENT_SEPARATOR );
			}

			buffer.append( toString( array[i] ) );
		}
		buffer.append( ARRAY_END );
		return buffer.toString();
	}

	/**
	 * byte 배열 <code>array</code>를 문자열로 변환한다.
	 * 
	 * @param array 변환할 byte 배열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	toString(
		final byte[] array
	)
	{
		if ( null == array )
		{
			return NULL_STRING;
		}

		final int length = array.length;

		if ( 0 == length )
		{
			return EMPTY_ARRAY; 
		}

		final StringBuilder buffer= new StringBuilder();
		
		buffer.append( ARRAY_START);
		for ( int i=0 ; i<length ; ++i )
		{
			if ( 0 != i ) {
				buffer.append( ARRAY_ELEMENT_SEPARATOR );
			}

			buffer.append( toString( array[i] ) );
		}
		buffer.append( ARRAY_END );
		return buffer.toString();
	}

	/**
	 * char 배열 <code>array</code>를 문자열로 변환한다.
	 * 
	 * @param array 변환할 char 배열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	toString(
		final char[] array
	)
	{
		if ( null == array )
		{
			return NULL_STRING;
		}
		
		final int length = array.length;
		
		if ( 0 == length )
		{
			return EMPTY_ARRAY; 
		}

		final StringBuilder buffer= new StringBuilder();
		
		buffer.append( ARRAY_START);
		for ( int i=0 ; i<length ; ++i )
		{
			if ( 0 != i )
			{
				buffer.append( ARRAY_ELEMENT_SEPARATOR );
			}

			buffer.append( toString( array[i] ) );
		}
		buffer.append( ARRAY_END );
		return buffer.toString();
	}
	

	/**
	 * short 배열 <code>array</code>를 문자열로 변환한다.
	 * 
	 * @param array 변환할 short 배열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	toString(
		final short[] array
	)
	{
		if ( null == array ) 
		{
			return NULL_STRING; 
		}

		final int length = array.length;
		
		if ( 0 == length ) 
		{
			return EMPTY_ARRAY; 
		}

		final StringBuilder buffer = new StringBuilder();
		for( int i=0 ; i<length ; ++i )
		{
			if ( 0 == i )
			{
				buffer.append( ARRAY_START );
			}
			else
			{
				buffer.append( ARRAY_ELEMENT_SEPARATOR );
			}

			buffer.append( array[i] );
		}
		buffer.append( ARRAY_END );
		return buffer.toString();
	}
	
	/**
	 * int 배열 <code>array</code>를 문자열로 변환한다.
	 * 
	 * @param array 변환할 int 배열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	toString(
		final int[] array
	)
	{
		if ( null == array )
		{
			return NULL_STRING; 
		}

		final int length = array.length;
		
		if ( 0 == length )
		{
			return EMPTY_ARRAY; 
		}

		final StringBuilder buffer = new StringBuilder();
		for( int i=0 ; i<length ; ++i )
		{
			if ( 0 == i )
			{
				buffer.append( ARRAY_START );
			}
			else
			{
				buffer.append( ARRAY_ELEMENT_SEPARATOR );
			}

			buffer.append( array[i] );
		}
		buffer.append( ARRAY_END );
		return buffer.toString();
	}

	/**
	 * long 배열 <code>array</code>를 문자열로 변환한다.
	 * 
	 * @param array 변환할 long 배열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	toString(
		final long[] array
	)
	{
		if ( null == array ) 
		{
			return NULL_STRING; 
		}

		int length = array.length;
		
		if ( 0 == length ) 
		{
			return EMPTY_ARRAY; 
		}

		final StringBuilder buffer = new StringBuilder();
		for( int i=0 ; i<length ; ++i)
		{
			if ( 0 == i )
			{
				buffer.append( ARRAY_START );
			}
			else
			{
				buffer.append( ARRAY_ELEMENT_SEPARATOR );
			}

			buffer.append( array[i] );
		}
		buffer.append( ARRAY_END );
		return buffer.toString();
	}

	/**
	 * double 배열 <code>array</code>를 문자열로 변환한다.
	 * 
	 * @param array 변환할 double 배열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	toString(
		final double[] array
	)
	{
		if ( null == array )
		{ 
			return NULL_STRING;
		}
		
		int length= array.length;
		
		if ( 0 == length )
		{
			return EMPTY_ARRAY; 
		}

		StringBuilder buffer = new StringBuilder();
		for( int i=0 ; i<length ; ++i )
		{
			if ( 0 == i )
			{
				buffer.append( ARRAY_START);
			}
			else
			{
				buffer.append( ARRAY_ELEMENT_SEPARATOR);
			}

			buffer.append( array[i] );
		}
		buffer.append( ARRAY_END );
		return buffer.toString();
	}

	/**
	 * float 배열 <code>array</code>를 문자열로 변환한다.
	 * 
	 * @param array 변환할 float 배열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	toString(
		final float[] array
	)
	{
		if ( null == array )
		{
			return NULL_STRING; 
		}

		int length = array.length;
		
		if ( 0 == length )
		{
			return EMPTY_ARRAY; 
		}

		final StringBuilder buffer = new StringBuilder();
		for( int i=0 ; i < length ; ++i )
		{
			if ( 0 == i )
			{
				buffer.append( ARRAY_START );
			}
			else
			{
				buffer.append( ARRAY_ELEMENT_SEPARATOR );
			}

			buffer.append( array[i] );
		}
		buffer.append( ARRAY_END );
		return buffer.toString();
	}

	/**
	 * 객체 배열 <code>array</code>를 문자열로 변환한다.
	 * 
	 * @param array 변환할 객체 배열
	 * 
	 * @return 변환된 문자열
	 */
	public static <T>
	String
	toString(
		final T[] array
	)
	{
		if ( null == array )
		{
			return NULL_STRING;
		}
		
		final int length = array.length;
		
		if ( 0 == length )
		{
			return EMPTY_ARRAY; 
		}

		final StringBuilder buffer= new StringBuilder();
		
		buffer.append( ARRAY_START);
		buffer.append( concatenate( array, ARRAY_ELEMENT_SEPARATOR ) );
		buffer.append( ARRAY_END );
		return buffer.toString();
	}

	/**
	 * 객체 배열 <code>array</code>를 구분 문자열을 이용해서 하나의 문자열로 만들어 반환한다.
	 * 
	 * @param array 이어불일 객체 배열
	 * @param separator 붙일 때 사용할 문자열
	 * 
	 * @return 이어 붙여진 문자열
	 */
	public static <E>
	String
	concatenate(
		final E[] array,
		final String separator
	)
	{
		return concatenate( new ArrayIterator<E>( array ), separator ); 
	}
		
	/**
	 * {@link Collection} 객체 <code>col</code>의 요소를 구분 문자열을 이용해서 
	 * 
	 * 하나의 문자열로 만들어 반환한다.
	 * 
	 * @param col 이어불일 객체 배열
	 * @param separator 붙일 때 사용할 문자열
	 * 
	 * @return 이어 붙여진 문자열
	 */
	public static <E>
	String
	concatenate(
		final Collection<E> col,
		final String separator
	)
	{
		return concatenate( col.iterator(), separator ); 
	}
	
	/**
	 * {@link Iterator}에 의해 순회하는 객체들에 대해 구분 문자열을 이용해서 하나의 문자열로 만들어 반환한다.
	 * 
	 * @param iter 이어불일 객체들을 순회하는 {@link Iterator}
	 * @param separator 붙일 때 사용할 문자열
	 * 
	 * @return 이어 붙여진 문자열
	 */
	public static <E>
	String
	concatenate(
		Iterator<E> iter,
		final String separator
	)
	{
		if ( null == iter )
		{
			return NULL_STRING;
		}
		
		if ( !iter.hasNext() )
		{
			return EMPTY_ARRAY; 
		}
		
		final StringBuilder buffer= new StringBuilder();
		boolean bInit = false;
		
		while ( iter.hasNext() )
		{
			Object obj = iter.next();
			if ( bInit )
			{
				buffer.append( separator );
			}
			bInit = true;
			
			buffer.append( toString( obj ) );
		}
		return buffer.toString();
	}
	
	/* Hash */
	private static final int MULTIPLIER= 31;

	private static final int INITIAL_HASH = 7;
	
	/**
	 * 객체에 대한 해쉬값을 생성한다.
	 * 
	 * 배열의 경우, 배열 내의 객체들의 해쉬값을 이용하여 해쉬값을 생성한다.
	 * 
	 * @param obj 해쉬값을 구할 객체
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final Object obj
	)
	{
		if ( null == obj )
		{
			return 0;
		}
		if ( obj.getClass().isArray() )
		{
			if ( obj instanceof Object[] )
			{
				return hashCode( (Object[] )obj );
			}
			else if ( obj instanceof boolean[] )
			{
				return hashCode( (boolean[] )obj );
			}
			else if ( obj instanceof byte[] )
			{
				return hashCode( (byte[] )obj);
			}
			else if ( obj instanceof char[] )
			{
				return hashCode( (char[] )obj);
			}
			else if ( obj instanceof double[] )
			{
				return hashCode( (double[] )obj);
			}
			else if ( obj instanceof float[] )
			{
				return hashCode( (float[] )obj);
			}
			else if ( obj instanceof int[] )
			{
				return hashCode( (int[] )obj);
			}
			else if ( obj instanceof long[] )
			{
				return hashCode( (long[] )obj);
			}
			else if ( obj instanceof short[] )
			{
				return hashCode( (short[] )obj);
			}
		}
		
		return obj.hashCode();
	}

	/**
	 * 객체 배열<code>array</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param array 해쉬값을 구할 객체 배열
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final Object[] array
	)
	{
		if ( null == array )
		{
			return 0;
		}
		
		int hash = INITIAL_HASH;
		for( int i=0, arraySize=array.length ; i<arraySize ; ++i )
		{
			hash = MULTIPLIER * hash + hashCode( array[i] );
		}

		return hash;
	}

	/**
	 * boolean 배열<code>array</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param array 해쉬값을 구할 boolean 배열
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final boolean[] array
	)
	{
		if ( null == array )
		{
			return 0;
		}
		
		int hash = INITIAL_HASH;
		for( int i=0, arraySize=array.length ; i<arraySize ; ++i )
		{
			hash = MULTIPLIER * hash + hashCode( array[i] );
		}

		return hash;
	}

	/**
	 * int 배열<code>array</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param array 해쉬값을 구할 int 배열
	 * 
	 * @return 해쉬값
	 */
	public static int
	hashCode(
		final byte[] array
	)
	{
		if ( null == array )
		{
			return 0;
		}
		
		int hash = INITIAL_HASH;
		for( int i=0, arraySize=array.length ; i<arraySize ; ++i )
		{
			hash = MULTIPLIER * hash + array[i];
		}

		return hash;
	}

	/**
	 * char 배열<code>array</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param array 해쉬값을 구할 char 배열
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final char[] array
	)
	{
		if ( null == array )
		{
			return 0;
		}
		
		int hash = INITIAL_HASH;
		for( int i=0, arraySize=array.length ; i<arraySize ; ++i )
		{
			hash = MULTIPLIER * hash + hashCode( array[i] );
		}

		return hash;
	}

	/**
	 * double 배열<code>array</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param array 해쉬값을 구할 double 배열
	 * 
	 * @return 해쉬값
	 */
	public static
	int 
	hashCode(
		final double[] array
	)
	{
		if ( null == array )
		{
			return 0;
		}
		
		int hash = INITIAL_HASH;
		for( int i=0, arraySize=array.length ; i<arraySize ; ++i )
		{
			hash = MULTIPLIER * hash + hashCode( array[i] );
		}

		return hash;
	}

	/**
	 * float 배열<code>array</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param array 해쉬값을 구할 float 배열
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final float[] array
	)
	{
		if ( null == array )
		{
			return 0;
		}
		
		int hash = INITIAL_HASH;
		for( int i=0, arraySize=array.length ; i<arraySize ; ++i )
		{
			hash = MULTIPLIER * hash + hashCode( array[i] );
		}

		return hash;
	}

	/**
	 * int 배열<code>array</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param array 해쉬값을 구할 int 배열
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final int[] array
	)
	{
		if ( null == array )
		{
			return 0;
		}
		
		int hash = INITIAL_HASH;
		for( int i=0, arraySize=array.length ; i<arraySize ; ++i )
		{
			hash = MULTIPLIER * hash + hashCode( array[i] );
		}

		return hash;
	}

	/**
	 * long 배열<code>array</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param array 해쉬값을 구할 long 배열
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final long[] array
	)
	{
		if ( null == array )
		{
			return 0;
		}
		
		int hash = INITIAL_HASH;
		for( int i=0, arraySize=array.length ; i<arraySize ; ++i )
		{
			hash = MULTIPLIER * hash + hashCode( array[i] );
		}

		return hash;
	}

	/**
	 * short 배열<code>array</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param array 해쉬값을 구할 short 배열
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final short[] array
	)
	{
		if ( null == array )
		{
			return 0;
		}
		
		int hash = INITIAL_HASH;
		for( int i=0, arraySize=array.length ; i<arraySize ; ++i )
		{
			hash = MULTIPLIER * hash + hashCode( array[i] );
		}

		return hash;
	}

	/**
	 * boolean <code>bool</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param bool 해쉬값을 구할 boolean
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final boolean bool
	)
	{
		return bool ? 1231 : 1237;
	}

	/**
	 * double <code>dbl</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param dbl 해쉬값을 구할 double
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final double dbl
	)
	{
		long bits = Double.doubleToLongBits( dbl );
		return hashCode( bits );
	}

	/**
	 * float <code>flt</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param flt 해쉬값을 구할 float
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		float flt
	)
	{
		return Float.floatToIntBits( flt );
	}


	/**
	 * long <code>lng</code>에 대한 해쉬값을 반환한다.
	 * 
	 * @param lng 해쉬값을 구할 long
	 * 
	 * @return 해쉬값
	 */
	public static
	int
	hashCode(
		final long lng
	)
	{
		return (int) ( lng ^ ( lng >>> 32 ) );
	}
	
	/* Pickup */
	/**
	 * <code>collection</code> 내의 객체중에서 가장 앞에 있는 객체를 반환한다.
	 * 
	 * <code>collection</code>가 비어 있어거나 <code>null</code>이어서 첫번째 객체를
	 * 
	 * 얻을 수 없다면, <code>null</code>를 반환한다.
	 * 
	 * @param <O> {@link Collection}에 담긴 객체 타입
	 * @param collection 객체를 얻을 {@link Collection}
	 * 
	 * @return 첫번째 객체
	 */
	public static <O>
	O
	pickupFirst(
		final Collection<O> collection
	)
	{
		if ( isEmpty( collection ) )
		{
			return null;
		}
		
		final Iterator<O> iter = collection.iterator();
		if ( iter.hasNext() )
		{
			return iter.next();
		}
		return null;
		
	}
	
	/**
	 * <code>array</code> 내의 객체중에서 가장 앞에 있는 객체를 반환한다.
	 * 
	 * <code>array</code>가 비어 있어거나 <code>null</code>이어서 첫번째 객체를
	 * 
	 * 얻을 수 없다면, <code>null</code>를 반환한다.
	 * 
	 * @param <O> 배열에 담긴 객체 타입
	 * @param array 객체를 얻을 배열
	 * 
	 * @return 첫번째 객체
	 */
	public static <O>
	O
	pickupFirst(
		final O[] array
	)
	{
		if ( isEmpty( array ) )
		{
			return null;
		}
		return array[0];
	}
	
	/**
	 * <code>collection</code> 내의 객체중에서 가장 뒤에 있는 객체를 반환한다.
	 * 
	 * <code>collection</code>가 비어 있어거나 <code>null</code>이어서 객체를
	 * 
	 * 얻을 수 없다면, <code>null</code>를 반환한다.
	 * 
	 * @param <O> {@link Collection}에 담긴 객체 타입
	 * @param collection 객체를 얻을 {@link Collection}
	 * 
	 * @return 마지막 객체
	 */
	public static <O>
	O
	pickupLast(
		final Collection<O> collection
	)
	{
		if ( isEmpty( collection ) )
		{
			return null;
		}
		
		final Iterator<O> iter = collection.iterator();
		O temp = null;
		while ( iter.hasNext() )
		{
			temp = iter.next();
		}
		return temp;
		
	}
	
	/**
	 * <code>array</code> 내의 객체중에서 가장 뒤에 있는 객체를 반환한다.
	 * 
	 * <code>array</code>가 비어 있어거나 <code>null</code>이어서 마지막 객체를
	 * 
	 * 얻을 수 없다면, <code>null</code>를 반환한다.
	 * 
	 * @param <O> 배열에 담긴 객체 타입
	 * @param array 객체를 얻을 배열
	 * 
	 * @return 마지막 객체
	 */
	public static <O>
	O
	pickupLast(
		final O[] array
	)
	{
		if ( isEmpty( array ) )
		{
			return null;
		}
		return array[array.length-1];
	}
	
	/* Equals */
	/**
	 * {@link Collection} 객체들을 비교하여 같은 객체를 가지고 있는지 여부를 반환한다.
	 * 
	 * @param <K> {@link Collection}에 담긴 객체 타입
	 * @param cols {@link Collection} 객체들
	 * 
	 * @return 모두 동일한 객체들을 담고 있는지 여부
	 */
	@SuppressWarnings("unchecked")
	public static <K>
	boolean
	equals(
		final Collection<? extends K>... cols
	)
	{
		// 첫번째가 null이어서 null인지 계속 확인해야 하는지 여부
		boolean bInit = false;
		int size = 0;
		for ( final Collection<? extends K> col : cols )
		{
			if ( !bInit )
			{
				if ( null == col )
				{
					size = -1;
				}
				else
				{
					size = col.size();
				}
				bInit = true;
			}
			if ( size < 0 )
			{
				if ( null != col )
				{
					return false;
				}
			}
			else if ( null == col || col.size() != size )
			{
				return false;
			}
		}
		if ( size < 0 )
		{
			return true;
		}

		final Iterator<? extends K>[] iters = new Iterator[cols.length];
		for ( int i = 0, n = iters.length ; i<n ; ++i )
		{
			iters[i] = cols[i].iterator();
		}
		
		while ( iters[0].hasNext() )
		{
			final K obj = iters[0].next();
			for ( int i = 1, n = iters.length ; i<n ; ++i )
			{
				final K other = iters[i].next();
				
				if ( !ObjectUtils.equals( obj, other ) )
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 배열 객체들을 비교하여 같은 객체를 가지고 있는지 여부를 반환한다.
	 * 
	 * @param objsVar 배열 객체들
	 * 
	 * @return 모두 동일한 객체들을 담고 있는지 여부
	 */
	public static
	boolean
	equals(
		final Object[]... objsVar
	)
	{
		// 첫번째가 null이어서 null인지 계속 확인해야 하는지 여부
		boolean bInit = false;
		int size = 0;
		for ( final Object[] objs : objsVar )
		{
			if ( !bInit )
			{
				if ( null == objs )
				{
					size = -1;
				}
				else
				{
					size = objs.length;
				}
				bInit = true;
			}
			if ( size < 0 )
			{
				if ( null != objs )
				{
					return false;
				}
			}
			else if ( null == objs || objs.length != size )
			{
				return false;
			}
		}
		if ( size < 0 )
		{
			return true;
		}

		
		for ( int i=1, n=objsVar.length ; i<n ; ++i )
		{
			for ( int j=0 ; j<size ; ++j )
			{
				if ( !ObjectUtils.equals( objsVar[0][j], objsVar[i][j] ) )
				{
					return false;
				}
			}
		}
		return true;

	}
	
	/**
	 * <p>
	 * 배열 <code>objs</code>의 <code>i</code>번째 객체와 <code>j</code>번째 객체의
	 * 위치를 서로 바꾼다.
	 * </p>
	 * @param objs 배열
	 * @param i 바꿀 객체 1의 위치
	 * @param j 발꿀 객체 2의 위치
	 */
	public static
	void
	swap(
		final Object[] objs,
		final int i,
		final int j
	)
	{
		Object temp = objs[i];
		objs[i] = objs[j];
		objs[j] = temp;
	}
	
}
