package americano.util;

import java.io.ByteArrayInputStream;

/**
 * LoggingUtils
 * 
 * 로깅을 남기는 기능과 관련된 유틸리티
 */
public class
LoggingUtils
implements ILoggingConstants
{

	protected LoggingUtils() {}

	/**
	 * </p>
	 * <code>ch</code>에 해당하는 16진수값을 <code>buffer</code>에 추가한다.
	 * 
	 * 이 때, 0을 왼쪽에 padding하여 두자리수를 유지한다.
	 * </p>
	 * 
	 * @param buffer 16진수값을 쓸 버퍼
	 * @param ch 변환할 10진수
	 */
	public static
	void
	appendHexa(
		final StringBuilder buffer,
		final int ch
	)
	{
		if ( ch < 16 )
		{
			// 한자리인 경우
			buffer.append( '0' );
			buffer.append( HEXA_CHARS[( 0x0f & ( ch ) )] );
		}
		else
		{
			// 두자리인 경우
			buffer.append( HEXA_CHARS[( 0x0f & ( ch >> 4 ) )] );
			buffer.append( HEXA_CHARS[( 0x0f & ( ch ) )] );
		}
	}

	protected static
	void
	lineEnd(
		final StringBuilder hexPart,
		final StringBuilder textPart,
		final StringBuilder ret
	)
	{
		// 영역별 구분자를 넣는다.
		hexPart.append( "     |" );

		// 마지막 구분자를 넣는다.
		textPart.append( "|\n" );

		// 두영역을 결합한다.
		ret.append( hexPart );
		ret.append( textPart );

		// 영역 버퍼를 비운다.
		hexPart.delete( 0, hexPart.capacity() );
		textPart.delete( 0, textPart.capacity() );
	}

	/**
	 * <p>
	 * <code>data</code>를 Logger에 찍기 위한 16진수 포맷으로 변환한다.
	 * 
	 * 출력 레이아웃<br>
	 * <table>
	 * <tr><td>주소</td><td>바이트</td><td>16진수값</td></tr>
	 * </table>
	 * 
	 * 바이트 영역에서 화면에 출력할 수 없는 문자인 경우 '.'로 대체한다.<br>
	 * </p>
	 * 
	 * @param data 변환할 byte배열
	 * 
	 * @return 변환된 문자열
	 */
	public static 
	String 
	text2hexa(
		final byte[] data
	)
	{
		if ( null == data )
		{
			return "<<null>>";
		}
		return text2hexa( data, 0, data.length );
	}
	/**
	 * <p>
	 * <code>data</code>의 내용 중 <code>offset</code>에서 길이가 <code>length</code>인 
	 * 
	 * byte들을 Logger에 찍기 위한 16진수 포맷으로 변환한다.<br>
	 * 
	 * 출력 레이아웃<br>
	 * <table>
	 * <tr><td>주소</td><td>바이트</td><td>16진수값</td></tr>
	 * </table>
	 * 
	 * 바이트 영역에서 화면에 출력할 수 없는 문자인 경우 '.'로 대체한다.
	 * </p>
	 * 
	 * @param data 변환할 byte배열
	 * @param offset
	 * @param length
	 * 
	 * @return 변환된 문자열
	 */
	public static 
	String 
	text2hexa(
		final byte[] data,
		final int offset,
		final int length
	)
	{

		if ( null == data )
		{
			return "<<null>>";
		}
		if ( data.length <= 0 )
		{
			return "<<EMPTY BYTES>>";
		}

		final ByteArrayInputStream reader = new ByteArrayInputStream( data, offset, length );
		final StringBuilder ret = new StringBuilder();
		final StringBuilder hexPart = new StringBuilder();
		final StringBuilder textPart = new StringBuilder();

		int address = 0;
		int ch = -1;
		int printByte = 0;
		int cnt = 0;

		// 주소 부분의 타이틀 부분은 빈 문자열( ' ' )로 채운다.
		hexPart.append( "          " );

		// 눈금자 표식을 넣는다.
		for ( int i = 0, n = WIDTH_PER_LINE / 4 ; i < n ; i++ )
		{
			hexPart.append( "+-------" );
			textPart.append( "+---" );
		}

		lineEnd( hexPart, textPart, ret );

		while ( 0 <= ( ch = reader.read() ) )
		{
			if ( 0 == cnt )
			{
				// 시작 주소값을 계산하여 조립한다.
				for ( int i = N_INT_BY_BYTE - 1 ; i >= 0 ; i-- )
				{
					printByte = 0xFF & ( address >> ( 8 * i ) );
					appendHexa( hexPart, printByte );
				}
				hexPart.append( "  " );
				address += WIDTH_PER_LINE;
			}

			appendHexa( hexPart, ch );
			if ( ( ch & 0x80 ) != 0 || ch < 32 )
			{
				// 제어 문자인 경우, 출력하면 안된다.
				textPart.append( CONTROL_CHARS_SHOWER );
			}
			else
			{
				textPart.append( (char) ch );
			}
			cnt++;

			if ( WIDTH_PER_LINE == cnt )
			{
				lineEnd( hexPart, textPart, ret );
				cnt = 0;
			}
		} // END of while ( 0 <= (ch = reader.read() ) )

		// 나머지 부분을 채워넣는다.
		if ( 0 != cnt )
		{
			for ( ; cnt < WIDTH_PER_LINE ; ++cnt )
			{
				hexPart.append( "  " );
				textPart.append( ' ' );
			}
			lineEnd( hexPart, textPart, ret );
		}

		return ret.toString();
	}
	
}
