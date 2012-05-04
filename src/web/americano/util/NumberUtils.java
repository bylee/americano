package americano.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * NumberUtils
 * 
 * 숫자와 관련된 기능을 모아놓은 API
 *
 */
public class
NumberUtils
{

	protected NumberUtils() {}


	/**
	 * <code>number</code>를 <code>targetClass</code> 타입의 객체로 변환한다.
	 * 
	 * @param number 변환될 객체
	 * @param targetClass 변환할 목표 타입
	 * 
	 * @return 변환된 객체
	 * 
	 * @throws IllegalArgumentException 변환이 불가능한 경우
	 */
	public static
	Number
	convert(
		final Number number,
		final Class<?> targetClass
	)
	throws IllegalArgumentException
	{

		Assert.notNull( number, "Number must not be null" );
		Assert.notNull( targetClass, "Target class must not be null" );

		if ( targetClass.isInstance( number ) )
		{
			// 인자값이 요구타입의 상속 타입인 경우
			return number;
		}
		else if ( targetClass.equals( Byte.class ) )
		{
			final long value = number.longValue();
			if ( value < Byte.MIN_VALUE || Byte.MAX_VALUE < value )
			{
				throwOverflowException( number, targetClass);
			}
			return new Byte( (byte) value );
		}
		else if ( targetClass.equals( Short.class ) )
		{
			final long value = number.longValue();
			if ( value < Short.MIN_VALUE || Short.MAX_VALUE < value )
			{
				throwOverflowException( number, targetClass);
			}
			return new Short( (short) value );
		}
		else if ( targetClass.equals( Integer.class ) )
		{
			final long value = number.longValue();
			if ( value < Integer.MIN_VALUE || Integer.MAX_VALUE < value )
			{
				throwOverflowException( number, targetClass);
			}
			return new Integer( (int) value );
		}
		else if ( targetClass.equals( Long.class ) )
		{
			return new Long( number.longValue() );
		}
		else if ( targetClass.equals( BigInteger.class ) )
		{
			if ( number instanceof BigDecimal )
			{
				// do not lose precision - use BigDecimal's own conversion
				return ( (BigDecimal) number ).toBigInteger();
			}
			else
			{
				// original value is not a Big* number - use standard long conversion
				return BigInteger.valueOf( number.longValue() );
			}
		}
		else if ( targetClass.equals( Float.class ) )
		{
			return new Float( number.floatValue() );
		}
		else if ( targetClass.equals( Double.class ) )
		{
			return new Double( number.doubleValue() );
		}
		else if ( targetClass.equals( BigDecimal.class ) )
		{
			// always use BigDecimal(String) here to avoid unpredictability of BigDecimal(double)
			// (see BigDecimal javadoc for details)
			return new BigDecimal( number.toString() );
		}
		else
		{
			throw new IllegalArgumentException( "Could not convert number [" + number + "] of type [" + number.getClass().getName()
				+ "] to unknown target class [" + targetClass.getName()+ "]" );
		}
	}

	/**
	 * <code>number</code>의 값이 <code>targetClass</code> 타입의 범위 밖의
	 * 
	 * 값인 경우 Overflow상황에 대한 예외를 던진다.
	 * 
	 * @param number 값을 담고 있는 객체
	 * @param targetClass 담을 타입
	 */
	private static
	void
	throwOverflowException(
		final Number number,
		final Class<?> targetClass
	)
	{
		throw new IllegalArgumentException( "Could not convert number [" + number + "] of type [" + number.getClass().getName()
			+ "] to target class [" + targetClass.getName() + "]: overflow" );
	}

	/**
	 * 문자열 <code>text</code>를 해석하여 <code>targetClass</code>타입의 Number 객체로 만든다.
	 * 
	 * @param text 해석할 문자열
	 * @param clazz 변환할 타입
	 * 
	 * @return 변환된 Number 객체
	 */
	public static
	Number
	parseNumber(
		final String text,
		final Class<?> clazz
	)
	{
		Assert.notNull( text, "Text must not be null" );
		Assert.notNull( clazz, "Target class must not be null" );

		final String trimmed = StringUtils.trimAllWhitespace( text );

		if ( clazz.equals( Byte.class ) || clazz.equals( byte.class ) )
		{
			return Byte.decode( trimmed );
		}
		else if ( clazz.equals( Short.class ) || clazz.equals( short.class ) )
		{
			return Short.decode( trimmed );
		}
		else if ( clazz.equals( Integer.class ) || clazz.equals( int.class ) )
		{
			return Integer.decode( trimmed );
		}
		else if ( clazz.equals( Long.class ) || clazz.equals( long.class ) )
		{
			return Long.decode( trimmed );
		}
		else if ( clazz.equals( BigInteger.class ) )
		{
			return decodeBigInteger( trimmed );
		}
		else if ( clazz.equals( Float.class ) || clazz.equals( float.class ) )
		{
			return Float.valueOf( trimmed );
		}
		else if ( clazz.equals( Double.class ) || clazz.equals( double.class ) )
		{
			return Double.valueOf( trimmed );
		}
		else if ( clazz.equals( BigDecimal.class ) || clazz.equals( Number.class ) )
		{
			return new BigDecimal( trimmed );
		}
		else
		{
			throw new IllegalArgumentException( "Cannot convert String ["+ text+ "] to target class ["+ clazz.getName()+ "]");
		}
	}

	/**
	 * 문자열 <code>text</code>를 <code>numberFormat</code>의 형식에 따라 해석하여
	 * 
	 * <code>targetClass</code> 타입의 Number 객체로 변환하여 반환한다.
	 * 
	 * @param text 변환할 문자열
	 * @param targetClass 변환할 타입
	 * @param numberFormat 숫자 포맷
	 * 
	 * @return 변환된 Number객체 
	 */
	public static
	Number
	parseNumber(
		final String text,
		final Class<?> targetClass,
		final NumberFormat numberFormat
	)
	{
		if ( null == numberFormat ) {
			// 숫자 포맷이 없는 경우
			parseNumber( text, targetClass );
		}

		Assert.notNull( text, "Text must not be null" );
		Assert.notNull( targetClass, "Target class must not be null" );
		DecimalFormat decimalFormat = null;
		boolean resetBigDecimal = false;
		if ( numberFormat instanceof DecimalFormat )
		{
			decimalFormat = (DecimalFormat)numberFormat;
			if ( BigDecimal.class.equals( targetClass ) && !decimalFormat.isParseBigDecimal() )
			{
				decimalFormat.setParseBigDecimal( true );
				resetBigDecimal = true;
			}
		}

		try
		{
			final Number number = numberFormat.parse( StringUtils.trimAllWhitespace( text ) );
			return convert( number, targetClass );
		}
		catch( final ParseException ex )
		{
			throw new IllegalArgumentException( "Could not parse number: " + ex.getMessage(), ex );
		}
		finally
		{
			if ( resetBigDecimal )
			{
				decimalFormat.setParseBigDecimal( false );
			}
		}
	}

	/**
	 * <code>value</code>를 해석해서 BigInteger로 변환한다.
	 * 
	 * 0x로 시작할 경우, 16진수로 해석한다.
	 * 
	 * 0로 시작할 경우, 8진수로 해석한다.
	 * 
	 * @param value 변환할 문자열
	 * 
	 * @return 변환된 BigInteger 객체
	 */
	private static
	BigInteger
	decodeBigInteger(
		final String value
	)
	{
		int radix = 10;
		int index = 0;
		boolean negative = false;

		// Handle minus sign, if present.
		if ( value.startsWith( "-" ) )
		{
			negative = true;
			++index;
		}

		// Handle radix specifier, if present.
		if ( value.startsWith( "0x", index ) || value.startsWith( "0X", index ) )
		{
			index += 2;
			radix = 16;
		}
		else if ( value.startsWith( "#", index ) )
		{
			++index;
			radix = 16;
		}
		else if ( value.startsWith( "0", index ) && 1 + index < value.length() )
		{
			++index;
			radix= 8;
		}

		final BigInteger result = new BigInteger( value.substring( index ), radix );
		return( negative ? result.negate() : result );
	}

}
