package americano.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

/**
 * ObjectUtils
 * 
 * 모든 객체의 기본적인 API 사용을 위한 Helper Utility
 * 
 * 이 클래스는 다음에 대해 API를 제공한다
 * 
 * - null 변환
 * - 객체 비교
 */
public class
ObjectUtils
{
	protected ObjectUtils() {}

	// 16진수화한 IP
	private static String hexServerIP = null;

	// GUID에 사용될 난수의 SEED생성기
	private static final SecureRandom SEEDER = new SecureRandom();

	/**
	 * <code>objs</code>에 담겨있는 객체중 <code>null</code>이 아닌
	 * 
	 * 최초의 객체를 반환한다.
	 * 
	 * @param <K> 검사할 객체 타입 
	 * @param objs 검사할 객체 배열
	 * 
	 * @return 결과 객체
	 */
	public static <K>
	K
	nvl(
		final K... objs
	)
	{
		for ( int i=0, n=CollectionUtils.length( objs ) ; i<n ; ++i )
		{
			if ( null == objs[i] )
			{
				continue;
			}
			return objs[i];
		}
		return null;
	}


	/**
	 * 두 객체에 대한 비교를 수행한다.
	 * 
	 * 일반적으로 두 객체를 비교함에 있어서  {@link Object#equals(Object)}를 사용하는데,
	 * 
	 * 이 API를 사용하기 전에 반드시 null 확인을 수행해야 한다.
	 * 
	 * 또한, 객체가 null이더라도 비교 대상 객체가 null 인 경우에는 두 객체가 같은 것으로
	 * 
	 * 인정해야 한다.
	 * 
	 * 일반적인 비교
	 * <pre>
	 * if ( null == obj1 ) {
	 * 	if ( obj1 == obj2 ) {
	 * 		...
	 * 	}
	 * } else if ( obj1.equals( obj2 ) ) {
	 * 		...
	 * } 
	 * </pre>
	 * 
	 * Helper API를 사용한 경우
	 * <pre>
	 * if ( ObjectUtils.equals( obj1, obj2 ) ) {
	 * ...
	 * }
	 * 
	 * 또한, 배열인 경우, 내부 객체를 Recursive하게 탐색하면서 비교한다.
	 * 
	 * </pre>
	 * 
	 * @param obj1 비교대상1
	 * @param obj2 비교대상2
	 * 
	 * @return <code>obj1</code>와 <code>obj2</code>가 같은지 여부
	 */
	@SuppressWarnings({
			"unchecked", "rawtypes"
	})
	public static
	boolean
	equals( 
		final Object obj1,
		final Object obj2
	)
	{
		if ( obj1 == obj2 )
		{
			return true;
		}
		if ( null == obj1 || null == obj2 )
		{
			return false;
		}

		if ( !obj1.getClass().isArray() || !obj2.getClass().isArray() )
		{
			return obj1.equals( obj2 );
		}

		// 배열인 경우 처리
		if ( obj1 instanceof boolean[] && obj2 instanceof boolean[] )
		{
			return Arrays.equals( ( boolean[] ) obj1, ( boolean[] ) obj2 );
		}
		else if ( obj1 instanceof byte[] && obj2 instanceof byte[] )
		{
			return Arrays.equals( ( byte[] ) obj1, ( byte[] ) obj2 );
		}
		else if ( obj1 instanceof char[] && obj2 instanceof char[] )
		{
			return Arrays.equals( ( char[] ) obj1, ( char[] ) obj2 );
		}
		else if ( obj1 instanceof double[] && obj2 instanceof double[] )
		{
			return Arrays.equals( ( double[] ) obj1, ( double[] ) obj2 );
		}
		else if ( obj1 instanceof float[] && obj2 instanceof float[] )
		{
			return Arrays.equals( ( float[] ) obj1, ( float[] ) obj2 );
		}
		else if ( obj1 instanceof int[] && obj2 instanceof int[] )
		{
			return Arrays.equals( ( int[] ) obj1, ( int[] ) obj2 );
		}
		else if ( obj1 instanceof long[] && obj2 instanceof long[] )
		{
			return Arrays.equals( ( long[] ) obj1, ( long[] ) obj2 );
		}
		else if ( obj1 instanceof short[] && obj2 instanceof short[] )
		{
			return Arrays.equals( ( short[] ) obj1, ( short[] ) obj2 );
		}
		else if ( obj1 instanceof Collection && obj2 instanceof Collection )
		{
			return CollectionUtils.equals( (Collection) obj1, (Collection) obj2 );
		}
		else if ( obj1 instanceof Object[] && obj2 instanceof Object[] )
		{
			return CollectionUtils.equals( (Object[]) obj1, (Object[]) obj2 );
		}
		return false;
	}

	/**
	 * 바이트 배열에서 정수값으로 변환한다.
	 * 
	 * @param bytes 변환할 바이트 배열
	 * 
	 * @return 변환된 정수
	 */
	public static
	int
	getInt(
		final byte bytes[]
	)
	{
		int i= 0;
		int j= 24;
		for( int k=0 ; 0<=j ; ++k )
		{
			int l = bytes[k] & 0xff;
			i += l << j;
			j -= 8;
		}
		return i;
	}

	/**
	 * <code>s</code>을 자릿수 <code>i</code>로 Padding한다.
	 * 
	 * 자릿수를 넘는 문자열인 경우에는 기존 문자열을 그대로 반환한다.
	 * 
	 * Padding문자는 '0'이다.
	 * 
	 * @param s 확장할 문자열
	 * @param i 자릿수
	 * 
	 * @return Padding한 문자열
	 */
	private static
	String
	padHex(
		final String s,
		final int i
	)
	{
		final StringBuilder buffer = new StringBuilder();
		for( int j=0, n=i-s.length() ; j<n ; ++j )
		{
			buffer.append( '0' );
		}
		buffer.append( s );
		return buffer.toString();
	}

	/**
	 * <code>i</code>를 <code>j</code>자릿수의 16진수로 바꾸어 반환한다.
	 * 
	 * @param i 변환할 수
	 * @param j 자릿수
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	hexFormat(
		final int i,
		final int j
	)
	{
		final String s = Integer.toHexString( i );
		return padHex( s, j );
	}


	/**
	 * 32 byte의 GUID를 생성한다.
	 * 
	 * 생성된 값은 개발자가 인식되는 용도로 사용되는 것이 아니라 프로그램에 의해서 사용된다.
	 * 
	 * 내부 구성
	 * +-----------------+--------------+----------------------+------------------+
	 * | Current Time(8) | Server IP(8) | Object Hash Value(8) | Random Number(8) |
	 * +-----------------+--------------+----------------------+------------------+
	 * 
	 * @param o 해쉬를 만들기 위한 객체
	 * @return 생성된 GUID
	 */
	public static final
	String
	generateGUID(
		final Object o
	)
	{
		final StringBuilder guid = new StringBuilder( 32 );

		// 시간값
		long timeNow = System.currentTimeMillis();
		int timeLow = (int) timeNow& 0xFFFFFFFF;
		guid.append( hexFormat( timeLow, 8 ) );

		// 서버 IP
		if ( null == hexServerIP )
		{
			InetAddress localInetAddress = null;
			try
			{
				// get the inet address
				localInetAddress = InetAddress.getLocalHost();
			}
			catch( final UnknownHostException uhe )
			{
				try
				{
					localInetAddress = InetAddress.getByName( "localhost" );
				}
				catch ( final UnknownHostException e )
				{
					e.printStackTrace();
					return null;
				}
			}

			byte serverIP[] = localInetAddress.getAddress();

			hexServerIP = hexFormat( getInt( serverIP ), 8 );
		}
		guid.append( hexServerIP );

		// 객체 해쉬값
		guid.append( hexFormat( System.identityHashCode( o ), 8 ) );

		// 난수
		int node= -1;
		synchronized( SEEDER )
		{
			node = SEEDER.nextInt();
		}
		guid.append( hexFormat( node, 8 ) );
		return guid.toString();
	}

	/* Serialize / Deserialize */
	/**
	 * 객체 직렬화를 수행한다.
	 * 
	 * @param obj 직렬화할 객체
	 * 
	 * @return 직렬화된 바이트 배열
	 * 
	 * @throws IOException 직렬화할 수 없는 경우
	 * 
	 * @see ObjectOutputStream#writeObject( Object )
	 */
	public static
	byte[]
	serialize(
		final Object obj
	)
	throws IOException
	{
		final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		final ObjectOutputStream objOut = new ObjectOutputStream( byteOut );
		objOut.writeObject( obj );
		return byteOut.toByteArray();
	}

	/**
	 * 바이트 배열을 객체화한다.
	 * 
	 * @param bytes 객체화할 바이트 배열
	 * 
	 * @return 객체화된 객체
	 * 
	 * @throws IOException 객체화할 수 없는 경우
	 * 
	 * @throws ClassNotFoundException 해당 클래스를 찾을 수 없는 경우
	 * 
	 * @see ObjectInputStream#readObject()
	 */
	public static
	Object
	deserialize(
		final byte[] bytes
	)
	throws IOException, ClassNotFoundException
	{
		Assert.notNull( bytes, "bytes must not be null" );
		final ByteArrayInputStream byteIn = new ByteArrayInputStream( bytes );
		final ObjectInputStream objIn = new ObjectInputStream( byteIn );
		return objIn.readObject();
	}

	/**
	 * 객체 <code>obj</code>에 대한 Simple Name을 생성한다.
	 * 
	 * 기존 {@link Object#toString()}의 경우, Qualified Name과 8자리 hash값으로 생성되나
	 * 
	 * 이 메쏘드는 Simple Name과 4자리 Hash값을 사용한다.
	 * 
	 * @param obj toString문자열을 생성할 객체
	 * 
	 * @return <code>obj</code> 대한 문자열
	 */
	public static
	String
	toString(
		final Object obj
	)
	{
		if ( null == obj )
		{
			return "null";
		}
		final StringBuilder builder = new StringBuilder();
		builder.append( obj.getClass().getSimpleName() );
		builder.append( '@' );
		int hash = obj.hashCode();
		
		for ( int i = 8 ; i>=0 ; i-=8 )
		{
			LoggingUtils.appendHexa( builder, 0xFF & ( hash >> i ) );
		}
		
		
		return builder.toString();
	}
}
