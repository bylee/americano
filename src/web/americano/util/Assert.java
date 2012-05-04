package americano.util;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

/**
 * 단언( 반드시 성립해야 하는 명제 )에 대한 처리를 하는 클래스
 * 
 * 다음에 대해 검증할 수 있다.
 * 
 * - expression 에 대한 논리값 판정
 * 
 * - 객체의 null 여부 판정
 * 
 */
public class 
Assert
{
	protected Assert() { }

	/**
	 * 예외 IllegalArgumentException에 메세지 "Object must NOT be null"를 담아 던진다.  
	 * 
	 * @param msg 던질 예외 메세지
	 * 
	 */
	public static
	void
	fail(
		final Object msg
	)
	{
		if ( msg instanceof String )
		{
			throw new IllegalArgumentException( (String) msg );
		} else if ( null != msg )
		{
			throw new IllegalArgumentException( msg.toString() );
		}
	}

	/**
	 * 논리변수 <code>exp</code>의 값이 <code>true</code>인지 확인한다.
	 * 
	 * <code>false</code>인 경우, 예외 IllegalArgumentException에 
	 * 
	 * 예외 메세지 <code>msg</code>를 담아 던진다.  
	 * 
	 * @param exp 검증할 논리값
	 * @param msg <code>exp</code>가 false일 경우, 던질 예외 메세지
	 */
	public static
	void
	isTrue(
		final boolean exp,
		final Object msg
	)
	{
		if ( exp )
		{
			return ;
		}
		fail( msg );
	}
	
	/**
	 * 논리변수 <code>exp</code>의 값이 <code>false</code>인지 확인한다.
	 * 
	 * <code>true</code>인 경우, 예외 IllegalArgumentException에 
	 * 
	 * 예외 메세지 <code>msg</code>를 담아 던진다.  
	 * 
	 * @param exp 검증할 논리값
	 * @param msg <code>exp</code>가 true일 경우, 던질 예외 메세지
	 */
	public static
	void
	isFalse(
		final boolean exp,
		final Object msg
	)
	{
		if ( !exp )
		{
			return ;
		}
		fail( msg );
	}
	
	/**
	 * 논리변수 <code>exp</code>의 값이 <code>true</code>인지 확인한다.
	 * 
	 * <code>false</code>인 경우, 예외 IllegalArgumentException에 
	 * 
	 * 예외 메세지 "Expression must be true"를 담아 던진다.  
	 * 
	 * @param exp 검증할 논리값
	 * 
	 */
	public static void 
	isTrue(
		final boolean exp
	)
	{
		isTrue( exp, "Expression must be true" );
	}
	
	/**
	 * 논리변수 <code>exp</code>의 값이 <code>false</code>인지 확인한다.
	 * 
	 * <code>true</code>인 경우, 예외 IllegalArgumentException에 
	 * 
	 * 예외 메세지 "Expression must be false"를 담아 던진다.  
	 * 
	 * @param exp 검증할 논리값
	 * 
	 */
	public static 
	void 
	isFalse(
		final boolean exp
	)
	{
		isFalse( exp, "Expression must be false" );
	}
	
	/**
	 * 객체 <code>obj</code>가 <code>null</code>인지 확인한다.
	 * 
	 * <code>null</code>이 아닌 경우, 예외 IllegalArgumentException에 
	 * 
	 * 예외 메세지 <code>msg</code>를 담아 던진다.  
	 * 
	 * @param obj 검사할 객체
	 * @param msg <code>obj</code>가 null이 아닌 경우, 던질 예외 메세지
	 * 
	 */
	public static
	void
	isNull(
		final Object obj,
		final Object msg
	)
	{
		isTrue( null == obj, msg );
	}

	/**
	 * 객체 <code>obj</code>가 <code>null</code>이 아닌지 확인한다.
	 * 
	 * <code>null</code>인 경우, 예외 IllegalArgumentException에 
	 * 
	 * 예외 메세지 <code>msg</code>를 담아 던진다.  
	 * 
	 * @param obj 검사할 객체
	 * @param msg <code>obj</code>가 null인 경우, 던질 예외 메세지
	 * 
	 */
	public static
	void
	notNull(
		final Object obj,
		final Object msg
	)
	{
		isFalse( null == obj, msg );
	}
	
	/**
	 * 객체 <code>obj</code>가 <code>null</code>인지 확인한다.
	 * 
	 * <code>null</code>이 아닌 경우, 예외 IllegalArgumentException에 
	 * 
	 * 예외 메세지 "Object must be null"를 담아 던진다.  
	 * 
	 * @param obj 검사할 객체
	 */
	public static
	void
	isNull(
		final Object obj
	)
	{
		isNull( obj, "Object must be null" );
	}
	
	/**
	 * 객체 <code>obj</code>가 <code>null</code>이 아닌지 확인한다.
	 * 
	 * <code>null</code>인 경우, 예외 IllegalArgumentException에 
	 * 
	 * 예외 메세지 "Object must NOT be null"를 담아 던진다.  
	 * 
	 * @param obj 검사할 객체
	 */
	public static 
	void 
	notNull(
		final Object obj
	)
	{
		notNull( obj, "Object must NOT be null" );
	}
	
	
	/**
	 * 객체 <code>obj1</code>과 <code>obj2</code>가 같은지 확인한다.
	 * 
	 * 같지 않은 경우, 예외 {@link IllegalArgumentException}에 예외 메시지
	 * 
	 * <code>message</code>를 담아 던진다.
	 * 
	 * @param obj1 비교할 객체1
	 * @param obj2 비교할 객체2
	 * @param message 예외 메시지
	 */
	public static
	void
	isEqual(
		final Object obj1,
		final Object obj2,
		final String message
	)
	{
		if ( ObjectUtils.equals( obj1, obj2 ) )
		{
			return ;
		}
		
		throw new IllegalArgumentException( message );
	}
	
	/**
	 * 객체 <code>obj1</code>과 <code>obj2</code>가 같은지 확인한다.
	 * 
	 * 같지 않은 경우, 예외 {@link IllegalArgumentException}에 예외 메시지
	 * 
	 * "Objects are not equal"를 담아 던진다.
	 * 
	 * @param obj1 비교할 객체1
	 * @param obj2 비교할 객체2
	 */
	public static
	void
	isEqual(
		final Object obj1,
		final Object obj2
	)
	{
		isEqual( obj1, obj2, "Objects are not equal" );
	}
	
	/**
	 * <code>text</code>가 문자를 포함하고 있는지 검증한다.
	 * 
	 * <code>text</code>가 <code>null</code>이거나 빈 문자열인 경우,
	 *  
	 * <code>message</code>를 메시지로 하는 {@link IllegalArgumentException}을 던진다.
	 *  
	 * @param text 검증할 문자열
	 * @param message 예외 메시지
	 */
	public static
	void
	hasLength(
		final String text,
		final String message
	)
	{
		if( StringUtils.hasLength( text ) )
		{
			return ; 
		}
		throw new IllegalArgumentException( message ); 
	}
	/**
	 * <code>text</code>가 문자를 포함하고 있는지 검증한다.
	 * 
	 * <code>text</code>가 <code>null</code>이거나 빈 문자열인 경우,
	 *  
	 * {@link IllegalArgumentException}을 던진다.
	 *  
	 * @param text 검증할 문자열
	 */
	public static
	void
	hasLength(
		final String text
	)
	{
		hasLength( text, "[Assertion failed] - this String argument must have length; it must not be null or empty" );
	}

	/**
	 * <code>text</code>가 문자를 포함하고 있는지 검증한다.
	 * 
	 * <code>text</code>가 <code>null</code>이거나 빈 문자열, 또는 white space로만
	 *  
	 * 이루어져 있는 경우, <code>message</code>를 메시지로 하는
	 *  
	 * {@link IllegalArgumentException}을 던진다.
	 *  
	 * @param text 검증할 문자열
	 */
	public static
	void
	hasText(
		final String text,
		final String message
	)
	{
		if ( StringUtils.hasText( text ) )
		{
			return ; 
		}
		throw new IllegalArgumentException( message ); 
	}

	/**
	 * <code>text</code>가 문자를 포함하고 있는지 검증한다.
	 * 
	 * <code>text</code>가 <code>null</code>이거나 빈 문자열, 또는 white space로만
	 *  
	 *  이루어져 있는 경우, {@link IllegalArgumentException}을 던진다.
	 *  
	 * @param text 검증할 문자열
	 */
	public static
	void
	hasText(
		final String text
	)
	{
		hasText( text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank" );
	}

	/**
	 * <code>textToSearch</code>에 substring이 포함되어 있지 않는지 검증한다.
	 * 
	 * Spring Util의 doesNotContain 매쏘드와 정반대로 검증한다.
	 * 
	 * 검증에 실패했을 경우, <code>message</code>를 메시지로 하는 {@link IllegalArgumentException}를 던진다.
	 * 
	 * <code>textToSearch</code>나 <code>substring</code>가 <code>null</code>인 경우, 검증을 통과한다.
	 * 
	 * Spring Util의 Assert#doesNotContain과 반대의 결과
	 * 
	 * @param textToSearch 검색을 적용할 문자열
	 * @param substring 찾을 문자열
	 * @param message 찾을 경우, 예외 메시지
	 *
	 */
	public static
	void
	doesNotContain(
		final String textToSearch,
		final String substring,
		final String message
	)
	{
		if ( !StringUtils.hasLength( textToSearch ) )
		{ 
			return ;
		}
		
		if ( !StringUtils.hasLength( substring ) )
		{
			return ;
		}
		
		if ( !textToSearch.contains( substring ) )
		{
			return ;
		}
		throw new IllegalArgumentException( message );
	}

	/**
	 * <code>textToSearch</code>에 substring이 포함되어 있지 않는지 검증한다.
	 * 
	 * Spring Util의 doesNotContain 매쏘드와 정반대로 검증한다.
	 * 
	 * 검증에 실패했을 경우, {@link IllegalArgumentException}를 던진다.
	 * 
	 * <code>textToSearch</code>나 <code>substring</code>가 <code>null</code>인 경우, 검증을 통과한다.
	 * 
	 * Spring Util의 Assert#doesNotContain과 반대의 결과
	 * 
	 * @param textToSearch 검색을 적용할 문자열
	 * @param substring 찾을 문자열
	 * 
	 */
	public static
	void
	doesNotContain(
		final String textToSearch,
		final String substring
	)
	{
		doesNotContain(
			textToSearch,
			substring,
			"[Assertion failed] - this String argument must not contain the substring [" + substring + "]"
		);
	}
	
	/**
	 * <code>textToSearch</code>에 substring이 포함되어 있는지 검증한다.
	 * 
	 * Spring Util의 doesNotContain 매쏘드와 동일하게 검증한다.
	 * 
	 * 검증에 실패했을 경우, <code>message</code>를 메시지로 하는 {@link IllegalArgumentException}를 던진다.
	 * 
	 * <code>textToSearch</code>나 <code>substring</code>가 <code>null</code>인 경우, 검증을 통과한다.
	 * 
	 * Spring Util의 Assert#doesNotContain과 동일한 결과
	 * 
	 * @param textToSearch 검색을 적용할 문자열
	 * @param substring 찾을 문자열
	 * @param message 찾을 경우, 예외 메시지
	 */
	public static
	void
	contains(
		final String textToSearch,
		final String substring,
		final String message
	)
	{
		if ( !StringUtils.hasLength( textToSearch ) )
		{
			return ;
		}
		if ( !StringUtils.hasLength( substring ) ) 
		{
			return ;
		}
		if ( textToSearch.contains( substring ) ) 
		{
			return ;
		}
		throw new IllegalArgumentException( message );
	}

	/**
	 * <code>textToSearch</code>에 substring이 포함되어 있는지 검증한다.
	 * 
	 * Spring Util의 doesNotContain 매쏘드와 동일하게 검증한다.
	 * 
	 * 검증에 실패했을 경우, {@link IllegalArgumentException}를 던진다.
	 * 
	 * <code>textToSearch</code>나 <code>substring</code>가 <code>null</code>인 경우, 검증을 통과한다.
	 * 
	 * Spring Util의 Assert#doesNotContain과 동일한 결과
	 * 
	 * @param textToSearch 검색을 적용할 문자열
	 * @param substring 찾을 문자열
	 */
	public static
	void
	contains(
		final String textToSearch,
		final String substring
	)
	{
		contains(
			textToSearch,
			substring,
			"[Assertion failed] - this String argument must not contain the substring [" + substring + "]"
		);
	}

	/**
	 * <code>array</code>가 비어 있지 않은 배열인지 검증한다. 
	 * 
	 * 검증에 실패했을 경우, <code>message</code>를 메시지로 하는 {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param array 검증할 배열
	 * @param message 예외 메시지
	 * 
	 * @see CollectionUtils#isEmpty( Object[] )
	 */
	public static
	void
	notEmpty(
		final Object[] array,
		final String message
	)
	{
		if( CollectionUtils.isEmpty( array ) )
		{
			throw new IllegalArgumentException( message );
		}
	}

	/**
	 * <code>array</code>가 비어 있지 않은 배열인지 검증한다. 
	 * 
	 * 검증에 실패했을 경우, {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param array 검증할 배열
	 * 
	 * @see Assert#notEmpty( Object[], String )
	 */
	public static
	void
	notEmpty(
		final Object[] array
	)
	{
		notEmpty( array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element" );
	}

	/**
	 * <code>array</code> 배열 안에 <code>null</code> 객체를 담고 있지 않음을 검증한다.
	 * 
	 * 검증에 실패했을 경우, <code>message</code>를 메시지로 하는 {@link IllegalArgumentException}를 던진다.
	 * 
	 * <code>array</code>가 <code>null</code>인 경우, 검증을 통과한다.
	 * 
	 * @param array 검증할 배열
	 * @param message 예외 메시지
	 */
	public static
	void
	noNullElements(
		final Object[] array,
		final String message
	)
	{
		
		for ( int i=0, n=CollectionUtils.length( array ) ; i<n ; ++i ) {
			if ( null != array[i] ) continue;
			
			throw new IllegalArgumentException( message ); 
		}
	}

	/**
	 * <code>array</code> 배열 안에 <code>null</code> 객체를 담고 있지 않음을 검증한다.
	 * 
	 * 검증에 실패했을 경우, {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param array 검증할 배열
	 * 
	 * @see Assert#noNullElements( Object[], String )
	 */
	public static
	void
	noNullElements(
		final Object[] array
	) {
		noNullElements( array, "[Assertion failed] - this array must not contain any null elements" );
	}

	/**
	 * <code>collection</code>가 비어있지 않음을 검증한다.
	 * 
	 * 검증에 실패했을 경우, <code>message</code>를 메시지로 하는 {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param collection 검증할 Collection 객체
	 * @param message 예외 메시지
	 * 
	 * @see CollectionUtils#isEmpty( Collection )
	 */
	public static
	void
	notEmpty(
		final Collection<?> collection,
		final String message
	)
	{
		if( !CollectionUtils.isEmpty( collection ) )
		{
			return ; 
		}
		throw new IllegalArgumentException( message ); 
	}

	/**
	 * <code>collection</code>가 비어있지 않음을 검증한다.
	 * 
	 * 검증에 실패했을 경우, {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param collection 검증할 Collection 객체
	 * 
	 * @see Assert#notEmpty( Collection, String )
	 */
	public static
	void
	notEmpty(
		final Collection<?> collection
	)
	{
		notEmpty( collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element" );
	}

	/**
	 * <code>map</code>가 비어있지 않음을 검증한다.
	 * 
	 * 검증에 실패했을 경우, <code>message</code>를 메시지로 하는 {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param map 검증할 맵 객체
	 * @param message 예외 메시지
	 * 
	 * @see CollectionUtils#isEmpty( Map )
	 */
	public static
	void
	notEmpty(
		final Map<?, ?> map,
		final String message
	)
	{
		if ( !CollectionUtils.isEmpty( map ) )
		{
			return ; 
		}
		throw new IllegalArgumentException( message ); 
	}

	/**
	 * <code>map</code>가 비어있지 않음을 검증한다.
	 * 
	 * 검증에 실패했을 경우, {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param map 검증할 맵 객체
	 * 
	 * @see Assert#notEmpty( Map )
	 */
	public static
	void
	notEmpty(
		final Map<?, ?> map
	)
	{
		notEmpty( map, "[Assertion failed] - this map must not be empty; it must contain at least one entry" );
	}

	/**
	 * <code>obj</code>가 <code>type</code> 객체인지 검증한다.
	 * 
	 * 검증에 실패했을 경우, <code>message</code>를 메시지로 하는 {@link IllegalArgumentException}를 던진다.
	 * 
	 * <code>type</code>가 <code>null</code>인 경우, 예외를 발생시킨다
	 * 
	 * @param type 검증할 클래스
	 * @param obj 검증할 객체 인스턴스
	 * @param message 예외 메시지
	 */
	public static
	void
	isInstanceOf(
		final Class<?> type,
		final Object obj,
		final String message
	)
	{
		notNull( type, "Type to check against must not be null" );
		if( type.isInstance( obj ) )
		{
			return ; 
		}
		
		final String msg = MessageFormat.format(
			"{0}, Object of class [{1}] must be aan instance of {2}",
			message,
			(( null == obj ) ?"null":obj.getClass().getName()),
			type
		);
		throw new IllegalArgumentException( msg ); 
	}
	
	/**
	 * <code>obj</code>가 <code>type</code> 객체인지 검증한다.
	 * 
	 * 검증에 실패했을 경우, {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param clazz 검증할 클래스
	 * @param obj 검증할 객체 인스턴스
	 * 
	 * @see Assert#isInstanceOf( Class, Object, String )
	 */
	public static
	void
	isInstanceOf(
		final Class<?> clazz,
		final Object obj
	)
	{
		isInstanceOf( clazz, obj, "" );
	}

	/**
	 * <code>superType</code>이 <code>subType</code>을 할당받을 수 있는지 검증한다.
	 * 
	 * 검증에 실패했을 경우, <code>message</code>를 메시지로 하는 {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param superType 검증할 클래스
	 * @param subType 할당할 클래스
	 * @param message 예외 메시지
	 * 
	 * @see Class#isAssignableFrom( Class )
	 */
	public static
	void
	isAssignable(
		final Class<?> superType,
		final Class<?> subType,
		final String message
	)
	{
		notNull( superType, "Type to check against must not be null" );
		notNull( subType, "Type to assign must not be null" );
		if( superType.isAssignableFrom( subType ) )
		{
			return ;
		}
		final String msg = MessageFormat.format(
			"{0}, {1} is not assignable to {2}",
			message,
			subType,
			superType
		);
		throw new IllegalArgumentException( msg ); 
	}

	/**
	 * <code>superType</code>이 <code>subType</code>을 할당받을 수 있는지 검증한다.
	 * 
	 * 검증에 실패했을 경우, {@link IllegalArgumentException}를 던진다.
	 * 
	 * @param superType 검증할 클래스
	 * @param subType 할당할 클래스
	 * 
	 * @see Assert#isAssignable( Class, Class, String )
	 */
	public static
	void
	isAssignable(
		final Class<?> superType,
		final Class<?> subType
	)
	{
		isAssignable( superType, subType, "" );
	}

}
