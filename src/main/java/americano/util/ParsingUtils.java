package americano.util;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ParsingUtils
 * 
 * 문자열 해석과 관련된 API를 갖는 클래스
 * 
 * 이 때, 변환은 사용자 측면에서 Reasonable한 방식으로 수행되어야 한다.
 * 
 */
public class
ParsingUtils
{

	private ParsingUtils() {}

	// boolean의 true를 의미하는 문자열들
	protected static final Collection<String> TRUES = new HashSet<String>(
		Arrays.asList( new String[] { "yes", "y","true", "1" } )
	);

	// boolean의 false를 의미하는 문자열들
	protected static final Collection<String> FALSES = new HashSet<String>(
		Arrays.asList( new String[] { "no", "n", "false", "0" } )
	);

	/**
	 * 문자열 <code>str</code>를 해석하여 논리값을 반환한다.
	 * 
	 * 기본값 <code>defaultValue</code>가 <code>false</code> 인 경우에는 
	 * 
	 * <code>str</code>가 <code>true</code>를 의미하는지 확인하고,
	 * 
	 * <code>defaultValue</code>가 <code>true</code>인 경우에는 <code>str</code>가
	 * 
	 * <code>false</code>를 의미하는지 확인하게 된다.
	 * 
	 * @param str 해석할 문자열
	 * @param defaultValue 문자열이 특정한 의미를 갖지 않는 경우, 반환한 기본값
	 * 
	 * @return <code>str</code>를 해석한 논리값
	 * 
	 * @see ParsingUtils#TRUES
	 * @see ParsingUtils#FALSES
	 */
	public static
	boolean
	parseBoolean(
		final String str,
		final boolean defaultValue
	)
	{
		if ( null == str )
		{
			return defaultValue;
		}

		final String safeStr = StringUtils.trim( str ).toLowerCase();

		if ( defaultValue )
		{
			// 기본값이 true인 경우
			return !FALSES.contains( safeStr );
		}
		else
		{
			// 기본값이 false인 경우
			return TRUES.contains( safeStr );
		}
	}


	/**
	 * <code>val</code>를 해석하여 boolean값을 반환한다.
	 * 
	 * @param val boolean 상수를 의미하는 문자열
	 * 
	 * @return boolean값
	 * 
	 * @see ParsingUtils#parseBoolean( String, boolean )
	 */
	public static
	boolean
	parseBoolean(
		final String val
	)
	{
		return parseBoolean( val, false );
	}

	/**
	 * <code>val</code>를 해석하여 long값을 반환한다.
	 * 
	 * @param val 해석할 문자열
	 * 
	 * @return long값
	 * 
	 * @see ParsingUtils#parseLong( String )
	 */
	public static
	long
	parseLong(
		final String val
	)
	{
		return parseLong( val, 0 );
	}

	/**
	 * <code>val</code>를 해석하여 long값을 반환한다.
	 * 
	 * 값을 해석할 수 없는 경우, 기본값으로 <code>defaultValue</code>를 반환한다.
	 * 
	 * @param val 해석할 문자열
	 * @param defaultValue 기본값
	 * 
	 * @return long값
	 * 
	 * @see NumberUtils#parseNumber( String, Class )
	 */
	public static
	long 
	parseLong(
		final String val,
		final long defaultValue
	)
	{
		if ( null == val )
		{
			return defaultValue;
		}
		try
		{
			return (Long) NumberUtils.parseNumber( val, long.class );
		}
		catch ( final Throwable e )
		{
			return defaultValue;
		}
	}

	/**
	 * <code>val</code>를 해석하여 int값을 반환한다.
	 * 
	 * @param val 해석할 문자열
	 * 
	 * @return int값
	 * 
	 * @see ParsingUtils#parseInt( String, int )
	 */
	public static
	int 
	parseInt(
		final String val
	)
	{
		return parseInt( val, 0 );
	}

	/**
	 * <code>val</code>를 해석하여 int값을 반환한다.
	 * 
	 * 값을 해석할 수 없는 경우, 기본값으로 <code>defaultValue</code>를 반환한다.
	 * 
	 * @param val 해석할 문자열
	 * @param defaultValue 기본값
	 * 
	 * @return int값
	 * 
	 * @see NumberUtils#parseNumber( String, Class )
	 */
	public static
	int 
	parseInt(
		final String val,
		final int defaultValue
	)
	{
		try
		{
			return (Integer) NumberUtils.parseNumber( val, int.class );
		}
		catch ( final Throwable e )
		{
			return defaultValue;
		}
	}

	/**
	 * <code>val</code>를 해석하여 double값을 반환한다.
	 * 
	 * @param val 해석할 문자열
	 * 
	 * @return double 값
	 * 
	 * @see ParsingUtils#parseDouble( String, double )
	 */
	public static
	double
	parseDouble(
		final String val
	)
	{
		return parseDouble( val, 0 );
	}


	/**
	 * <code>val</code>를 해석하여 double값을 반환한다.
	 * 
	 * 값을 해석할 수 없는 경우, 기본값으로 <code>defaultValue</code>를 반환한다.
	 * 
	 * @param val 해석할 문자열
	 * @param defaultValue 기본값
	 * 
	 * @return double 값
	 * 
	 * @see NumberUtils#parseNumber( String, Class )
	 */
	public static
	double
	parseDouble(
		final String val,
		final double defaultValue
	)
	{
		if ( null == val )
		{
			return defaultValue;
		}
		try
		{
			return (Double) NumberUtils.parseNumber( val, double.class );
		}
		catch ( final Throwable e )
		{
			return defaultValue;
		}
	}

	/*
	 * Parsing Interval
	 */
	// 시간 단위들, 밀리세컨드를 기준 단위로 사용한다.
	protected static final List<ScaleUnit> INTERVALS =
		Collections.unmodifiableList( Arrays.asList(
			new ScaleUnit[] {
				new ScaleUnit( "milliseconds", 1 ),
				new ScaleUnit( "ms", 1 ),
				new ScaleUnit( "seconds", 1000 ),
				new ScaleUnit( "sec", 1000 ),
				new ScaleUnit( "minutes", 60000 ),
				new ScaleUnit( "min", 60000 ),
				new ScaleUnit( "m", 60000 ),
				new ScaleUnit( "hours", 3600000 ),
				new ScaleUnit( "hr", 3600000 ),
				new ScaleUnit( "h", 3600000 ),
				new ScaleUnit( "s", 1000 ),
			}
		) );

	/**
	 * 시간을 의미하는 문자열 <code>val</code> 파싱해서 Milliseconds 단위 값으로 반환한다.
	 * 
	 * @param val 시간 문자열
	 * 
	 * @return 밀리세컨드 단위의 시간값
	 * 
	 * @throws ParseException 단위를 해석할 수 없는 경우 
	 */
	public static
	long
	parseTime(
		final String val
	)
	throws ParseException
	{
		if ( null == val ) return 0;

		final String lowerVal = val.toLowerCase();
		for ( final ScaleUnit unit : INTERVALS )
		{
			final String units = unit.units;
			if ( lowerVal.endsWith( units ) )
			{
				final String digits = lowerVal.substring( 0, lowerVal.length() - units.length() ).trim();
				return ( unit.scale * Integer.parseInt( digits ) );
			}
		}

		throw new ParseException( val, 0 );
	}

	/**
	 * 시간을 의미하는 문자열 <code>val</code>을 해석하여
	 * 
	 * 시간단위인 <code>unit</code> 단위의 값으로 변환하여 반환한다.
	 * 
	 * @param val 시간 문자열
	 * @param unit 시간 단위
	 * 
	 * @return <code>unit</code> 단위의 시간값
	 * 
	 * @throws ParseException 단위를 해석할 수 없는 경우
	 * 
	 *  @see ParsingUtils#parseTime(String)
	 */
	public static
	long
	parse(
		final String val, 
		final TimeUnit unit
	)
	throws ParseException
	{
		long time = parseTime( val );
		return TimeUnit.MILLISECONDS.convert( time, unit );
	}


}

/**
 * ScaleUnit
 * 시간 단위 정보
 */
class
ScaleUnit
{

	// 시간 단위의 표기
	protected final String units;

	// 기준 단위에 대한 비율
	protected final long scale;

	public
	ScaleUnit(
		final String units,
		final long scale
	) {
		this.units = units;
		this.scale = scale;
	}

}
