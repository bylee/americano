package americano.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils
implements IStringConstants
{
	protected StringUtils() {}
	
	protected static final Logger logger = LoggerFactory.getLogger( StringUtils.class );

	public static final String lineSeparator = System.getProperty( "line.separator" ); 

	public static final String tab = "\t";

	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String PARENT_PATH = "..";

	private static final String CURRENT_PATH = ".";

	private static final char EXTENSION_SEPARATOR = '.';


	/* 검사 */
	/**
	 * <code>str</code>이 null이거나 의미적으로 빈 문자열인지 검사한다.
	 * 
	 * @param str 검사할 문자열
	 * 
	 * @return 의미적으로 빈문자열인지 여부
	 * 
	 * @see StringUtils#hasText( CharSequence )
	 */
	public static 
	boolean 
	isEmpty(
		final CharSequence str
	)
	{
		if ( null == str )
		{
			return true;
		}

		for ( int i=0, n=str.length() ; i<n ; ++i )
		{
			if ( Character.isWhitespace( str.charAt( i ) ) )
			{
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * 문자열 <code>str</code>의 길이를 반환한다.
	 * 
	 * <code>str</code>가 <code>null</code>인 경우, 길이는 0으로 취급하여 0을 반환한다.
	 * 
	 * @param str 길이를 구할 문자열
	 * 
	 * @return <code>str</code>의 길이
	 */
	public static
	int
	length(
		final CharSequence str
	)
	{
		if ( null == str )
		{
			return 0;
		}
		return str.length();
	}

	/**
	 * 문자열이 <code>null</code>도 아니고 빈문자열도 아닌지 검사한다.
	 * 
	 * @param str 검사할 문자열
	 * 
	 * @return 문자열을 갖는지 여부
	 */
	public static
	boolean
	hasLength(
		final CharSequence str
	)
	{
		return 0 < length( str );
	}

	/**
	 * <code>str</code>에 의미있는 문자열을 포함하는지 여부를 반환한다.
	 * 
	 * @param str 검사할 문자열
	 * 
	 * @return 의미있는 문자열을 포함하는지 여부
	 * 
	 * @see StringUtils#isEmpty( CharSequence )
	 */
	public static
	boolean
	hasText(
		final CharSequence str
	)
	{
		return !isEmpty( str );
	}

	/* 변환 */
	/**
	 * 문자열 배열 <code>strs</code>를 검사하여 <code>null</code>이 아닌 문자열을 반환한다.
	 * 
	 * <code>null</code>인 경우, 빌문자열 <code>""<code>을 반환한다.
	 * 
	 * @param strs 입력 문자열 가변 배열
	 * 
	 * @return 반환 문자열
	 */
	public static 
	String 
	nvl(
		final String... strs
	)
	{
		String val = ObjectUtils.nvl( strs );
		if ( null == val )
		{
			return "";
		}
		return val;
	}

	/**
	 * 문자열 <code>str</code>의 앞부분과 뒤부분의 white space를 제거한다.
	 * 
	 * <code>str</code>가 <code>null</code>인 경우, 빈문자열( "" )을 반환한다.
	 * 
	 * @param str 변환할 문자열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	trim(
		final String str
	) {
		if ( null == str )
		{
			return null;
		}
		return str.trim();
	}

	/**
	 * 검색이나 문자열 비교시 앞뒤의 빈 공백이나 
	 * 
	 * 글자의 대소문자를 가리지 않기 위한 문자열로 변환한다.
	 * 
	 * @param str 변환할 문자열
	 * 
	 * @return 변환된 문자열
	 */
	public static
	String
	getMeaningful(
		final String str
	) {
		if ( null == str )
		{
			return EMPTY_STRING;
		}
		return trim( str ).toLowerCase();
	}

	/**
	 * 문자열 <code>str</code>에 대해 마스킹을 수행한다.
	 * 
	 * <code>str</code>의 문자열의 수만큼 <code>maskingStr</code>문자로 대체한다.
	 * 
	 * @param str 마스킹할 문자열
	 * @param maskingStr 마스킹될 문자열
	 * 
	 * @return 마스킹되어 변환된 문자열
	 */
	public static
	String
	mask(
		final String str,
		final String maskingStr
	) {
		if ( null == str )
		{
			return "null";
		}
		
		final StringBuilder buffer = new StringBuilder();
		for ( int i=0, n=str.length() ; i<n ; ++i )
		{
			buffer.append( maskingStr );
		}
		return buffer.toString();
	}

	/* 분류화 안됨 */
	/**
	 * delimeter로 분류된 문자열에서 <code>targetIndex</code>번째 문자를 
	 * 
	 * 추출하여 반환한다.
	 * 
	 * @param str <code>delimeter</code>로 분리된 문자열
	 * @param delimeter 문자열을 구분할 문자열
	 * @param escaper delimeter에 대한 escaper
	 * @param targetIndex 추출한 문자열의 순서
	 * 
	 * @return 추출된 문자열
	 */
	public static
	String
	getParamater(
		final String str,
		final char delimeter,
		final int escaper,
		final int targetIndex
	)
	throws IOException
	{
		try
		{
			if ( null == str )
			{
				return null;
			}
			final StringReader reader = new StringReader( str );
			StringWriter writer = null;
			int ch = 0;
			final int ST_NORMAL = 0;
			final int ST_ESCAPE = 1;
			int status = ST_NORMAL;
			int index = 0;
			if ( 0 == targetIndex ) writer = new StringWriter();
			while ( 0 <= ( ch = reader.read() ) ) {
				if ( ST_ESCAPE == status ) {
					status = ST_NORMAL;
				} else {
					if ( escaper == ch ) {
						status = ST_ESCAPE;
						continue;
					} else if ( delimeter == ch ) {
						if ( index == targetIndex ) return writer.toString();
						++index;
						if ( index == targetIndex ) writer = new StringWriter();
						continue;
					}
				}
				if ( null != writer ) writer.write( (int) ch );
			}
			if ( index == targetIndex ) return writer.toString();
			return null;
		}
		catch ( IOException e )
		{
			// 사실상 발생되지 않음
			throw new IllegalStateException( e );
		}
	}

	/**
	 * <code>str</code>에서 끝에서 <code>beginIndexFromLast</code> 떨어진 인덱스부터의
	 * 
	 * 부분 문자열을 반환한다.
	 */
	public static
	String
	lastSubstring(
		final String str,
		final int beginIndexFromLast
	)
	{
		if ( null == str )
		{
			return null;
		}
		
		final int length = str.length();
		
		return str.substring( length - beginIndexFromLast );
	}

	/**
	 * <code>str</code>을 <code>separator</code>로 나눈 것들 중에
	 * 
	 * 가장 마지막 부분을 반환한다.
	 * 
	 * @param str 나눌 문자열
	 * @param separator 문자열을 나눌 기준 문자열
	 * 
	 * @return 나눠진 문자열들 중에 마지막 문자열
	 */
	public static
	String
	getLastSegment(
		final String str,
		final String separator
	)
	{
		if ( null == str )
		{
			return EMPTY_STRING;
		}
		
		final int index = str.lastIndexOf( separator );
		if ( index < 0 )
		{
			return str;
		}
		
		return str.substring( index + separator.length() );
	}


	/**
	 * <code>symbol</code>가 <code>n</code>번 반복되는 문자열을 만든다. 
	 * 
	 * @param symbol 반복할 문자열
	 * @param n 반복 횟수
	 * 
	 * @return 만들어진 문자열
	 */
	public static
	String
	multiply(
		final String symbol,
		final int n
	)
	{
		if ( null == symbol ) {
			return EMPTY_STRING;
		}
		
		final StringBuilder buffer = new StringBuilder();
		for ( int i=0 ; i<n ; ++i )
		{
			buffer.append( symbol );
		}
		return buffer.toString();
	}

	/**
	 * 문자열 <code>str</code> 에 공백( white space )을 가지고 있는지 확인한다.
	 * 
	 * @param str 확인할 문자열
	 * @return <code>str</code>가 공백을 갖고 있는지 여부
	 */
	public static
	boolean
	containsWhitespace(
		final CharSequence str
	)
	{
		final int nChar = length( str );
		
		for ( int i= 0; i< nChar; ++i )
		{
			if ( Character.isWhitespace( str.charAt( i ) ) )
			{
				return true; 
			}
		}
		return false;
	}

	public static
	String
	trimWhitespace(
		final String str
	)
	{
		if ( !hasLength( str ) )
		{
			return str;
		}
		
		final StringBuilder buf= new StringBuilder( str );

		for ( int i=0, n=buf.length() ; i<n ; ++i )
		{
			if ( !Character.isWhitespace( buf.charAt( i ) ) )
			{
				buf.delete( 0, i );
				break;
			}
		}

		for ( int i=buf.length()-1 ; 0<=i ; --i )
		{
			if ( !Character.isWhitespace( buf.charAt( i ) ) )
			{
				buf.delete( i+1, buf.length() );
				break;
			}
		}

		return buf.toString();
	}

	public static
	String
	trimAllWhitespace(
		final String str
	)
	{
		if( !hasLength( str ) )
		{
			return str; 
		}
		StringBuilder buf= new StringBuilder( str );
		int index= 0;
		while ( buf.length() > index )
		{
			if( Character.isWhitespace( buf.charAt( index ) ) )
			{
				buf.deleteCharAt( index);
			}
			else
			{
				index++;
			}
		}
		return buf.toString();
	}

	/**
	 * 문자열의 왼쪽에 있는 빈 공백들을 제거한다.
	 * 
	 * @param str 제거할 공백을 가진 문자열
	 * 
	 * @return 왼쪽 공백이 제거된 문자열
	 */
	public static
	String
	trimLeadingWhitespace(
		final String str
	)
	{
		if( !hasLength( str ) )
		{
			return str;
		}

		final char[] chs = str.toCharArray();
		for ( int i=0, n=chs.length ; i<n ; ++i )
		{
			if ( !Character.isWhitespace( chs[i] ) )
			{
				return new String( chs, i, str.length() - i );
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * 문자열 오른쪽에 일는 빈 공백들을 제거한다.
	 * 
	 * @param str 제거할 공백을 가진 문자열
	 * 
	 * @return 오른쪽 공백이 제거된 문자열
	 */
	public static
	String
	trimTrailingWhitespace(
		final String str
	)
	{
		if( !hasLength( str ) )
		{
			return str;
		}

		final char[] chs = str.toCharArray();
		for ( int i=chs.length-1, j=chs.length ; 0<j ; --i, --j )
		{
			if ( !Character.isWhitespace( chs[i] ) )
			{
				return new String( chs, 0, j );
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * 문자열 <code>str</code>의 왼쪽에 있는 문자 
	 * 
	 * <code>leadingCharacter</code>들을 제거한다.
	 * 
	 * @param str 제거할 문자를 포함한 문자열
	 * @param leadingCharacter 제거할 문자열
	 * 
	 * @return 왼쪽에 <code>leadingCharacter</code>문자가 제거된 문자열
	 */
	public static
	String
	trimLeadingCharacter(
		final String str,
		final char leadingCharacter
	)
	{
		if( !hasLength( str ) )
		{
			return str;
		}

		final char[] chs = str.toCharArray();
		for ( int i=0, n=chs.length ; i<n ; ++i )
		{
			if ( leadingCharacter != chs[i] )
			{
				return new String( chs, i, str.length() - i );
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * 문자열 <code>str</code>의 오른쪽에 있는 문자 
	 * 
	 * <code>trailingCharacter</code>들을 제거한다.
	 * 
	 * @param str 제거할 문자를 포함한 문자열
	 * @param trailingCharacter 제거할 문자열
	 * 
	 * @return 오른쪽에 <code>trailingCharacter</code>문자가 제거된 문자열
	 */
	public static
	String
	trimTrailingCharacter(
		final String str,
		final char trailingCharacter
	)
	{
		if( !hasLength( str ) )
		{
			return str;
		}

		final char[] chs = str.toCharArray();
		for ( int i=chs.length-1, j=chs.length ; 0<j ; --i, --j )
		{
			if ( trailingCharacter != chs[i] )
			{
				return new String( chs, 0, j );
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * 문자열 <code>str</code>의 양쪽에 있는 문자 <code>character</code>들을 제거한다.
	 * 
	 * @param str 제거할 문자를 포함한 문자열
	 * @param character 제거할 문자열
	 * 
	 * @return 양쪽에 <code>character</code>문자가 제거된 문자열
	 */
	public static
	String
	trimCharacter(
		final String str,
		final char character
	)
	{
		if( !hasLength( str ) )
		{
			return str;
		}

		final char[] chs = str.toCharArray();
		int startIndex = 0, endIndex = chs.length - 1;
		for ( int n=chs.length ; startIndex<n ; ++startIndex )
		{
			if ( character != chs[startIndex] )
			{
				break;
			}
		}

		for ( ; startIndex <= endIndex ; --endIndex )
		{
			if ( character != chs[endIndex] )
			{
				return new String( chs, startIndex, endIndex - startIndex + 1 );
			}
		}
		return EMPTY_STRING;
	}

	public static boolean startsWithIgnoreCase( String str, String prefix)
	{
		if( str== null|| prefix== null)		return false;
		if( str.startsWith( prefix)) 				return true;
		if( str.length()< prefix.length())		return false;

		String lcStr= str.substring( 0, prefix.length()).toLowerCase();
		String lcPrefix= prefix.toLowerCase();
		return lcStr.equals( lcPrefix);
	}

	public static boolean endsWithIgnoreCase( String str, String suffix)
	{
		if( str== null|| suffix== null) 		return false;
		if( str.endsWith( suffix)) 				return true;
		if( str.length()< suffix.length()) 	return false;

		String lcStr= str.substring( str.length()- suffix.length()).toLowerCase();
		String lcSuffix= suffix.toLowerCase();
		return lcStr.equals( lcSuffix);
	}

	public static boolean substringMatch( CharSequence str, int index, CharSequence substring)
	{
		for( int j= 0; j< substring.length(); j++)
		{
			int i= index+ j;
			if( i>= str.length()|| str.charAt( i)!= substring.charAt( j)) 
				return false;
		}
		return true;
	}

	public static int countOccurrencesOf( String str, String sub)
	{
		if( str== null|| sub== null|| str.length()== 0|| sub.length()== 0) 
			return 0; 

		int count= 0, pos= 0, idx= 0;
		while( ( idx= str.indexOf( sub, pos))!= -1)
		{
			++count;
			pos= idx+ sub.length();
		}
		return count;
	}

	public static String replace( String inString, String oldPattern, String newPattern)
	{
		if( inString== null)	return null;
		if( oldPattern== null|| newPattern== null) return inString;

		StringBuilder sbuf= new StringBuilder();
		// output StringBuilder we'll build up
		int pos= 0; // our position in the old string
		int index= inString.indexOf( oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen= oldPattern.length();
		while( index>= 0)
		{
			sbuf.append( inString.substring( pos, index));
			sbuf.append( newPattern);
			pos= index+ patLen;
			index= inString.indexOf( oldPattern, pos);
		}
		sbuf.append( inString.substring( pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	public static String delete( String inString, String pattern)
	{
		return replace( inString, pattern, "");
	}

	public static String deleteAny( String inString, String charsToDelete)
	{
		if( !hasLength( inString)|| !hasLength( charsToDelete)) 
			return inString;

		StringBuilder out= new StringBuilder();
		for( int i= 0; i< inString.length(); i++)
		{
			char c= inString.charAt( i);
			if( charsToDelete.indexOf( c)== -1)
				out.append( c);
		}
		return out.toString();
	}

	public static String quote( String str)
	{
		return( str!= null ? "'"+ str+ "'" : null);
	}

	public static Object quoteIfString( Object obj)
	{
		return( obj instanceof String ? quote( (String)obj) : obj);
	}

	public static String unqualify( String qualifiedName)
	{
		return unqualify( qualifiedName, '.');
	}

	public static String unqualify( String qualifiedName, char separator)
	{
		return qualifiedName.substring( qualifiedName.lastIndexOf( separator)+ 1);
	}

	public static String capitalize( String str)
	{
		return changeFirstCharacterCase( str, true);
	}

	public static String uncapitalize( String str)
	{
		return changeFirstCharacterCase( str, false);
	}

	private static String changeFirstCharacterCase( String str, boolean capitalize)
	{
		if( str== null|| str.length()== 0)	return str;
		StringBuilder buf= new StringBuilder( str.length());
		if( capitalize)
			buf.append( Character.toUpperCase( str.charAt( 0)));
		else
			buf.append( Character.toLowerCase( str.charAt( 0)));

		buf.append( str.substring( 1));
		return buf.toString();
	}

	public static String getFilename( String path)
	{
		if( path== null) 	return null;
		int separatorIndex= path.lastIndexOf( FOLDER_SEPARATOR);
		return( separatorIndex!= -1 ? path.substring( separatorIndex+ 1) : path);
	}

	public static String getFilenameExtension( String path)
	{
		if( path== null) 	return null;
		int sepIndex= path.lastIndexOf( EXTENSION_SEPARATOR);
		return( sepIndex!= -1 ? path.substring( sepIndex+ 1) : null);
	}

	public static String stripFilenameExtension( String path)
	{
		if( path== null) 	return null;
		int sepIndex= path.lastIndexOf( EXTENSION_SEPARATOR);
		return( sepIndex!= -1 ? path.substring( 0, sepIndex) : path);
	}

	public static String applyRelativePath( String path, String relativePath)
	{
		int separatorIndex= path.lastIndexOf( FOLDER_SEPARATOR);
		if( separatorIndex!= -1)
		{
			String newPath= path.substring( 0, separatorIndex);
			if( !relativePath.startsWith( FOLDER_SEPARATOR))
				newPath+= FOLDER_SEPARATOR;

			return newPath+ relativePath;
		}
		else
			return relativePath;
	}

	public static String cleanPath( String path)
	{
		String pathToUse= replace( path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

		// Strip prefix from path to analyze, to not treat it as part of the
		// first path element. This is necessary to correctly parse paths like
		// "file:core/../core/io/Resource.class", where the ".." should just
		// strip the first "core" directory while keeping the "file:" prefix.
		int prefixIndex= pathToUse.indexOf( ":");
		String prefix= "";
		if( prefixIndex!= -1)
		{
			prefix= pathToUse.substring( 0, prefixIndex+ 1);
			pathToUse= pathToUse.substring( prefixIndex+ 1);
		}

		String[] pathArray= delimitedListToStringArray( pathToUse, FOLDER_SEPARATOR);
		List<String> pathElements= new LinkedList<String>();
		int tops= 0;

		for( int i= pathArray.length- 1; i>= 0; i--)
		{
			if( CURRENT_PATH.equals( pathArray[i]))
			{
				// Points to current directory - drop it.
			}
			else if( PARENT_PATH.equals( pathArray[i]))
			{
				// Registering top path found.
				tops++;
			}
			else
			{
				if( tops> 0)
				{
					// Merging path element with corresponding to top path.
					tops--;
				}
				else
				{
					// Normal path element found.
					pathElements.add( 0, pathArray[i]);
				}
			}
		}

		// Remaining top paths need to be retained.
		for( int i= 0; i< tops; i++)
			pathElements.add( 0, PARENT_PATH);

		return prefix+ collectionToDelimitedString( pathElements, FOLDER_SEPARATOR);
	}

	public static boolean pathEquals( String path1, String path2)
	{
		return cleanPath( path1).equals( cleanPath( path2));
	}

	public static Locale parseLocaleString( String localeString)
	{
		String[] parts= tokenizeToStringArray( localeString, "_ ", false, false);
		String language= ( parts.length> 0 ? parts[0] : "");
		String country= ( parts.length> 1 ? parts[1] : "");
		String variant= "";
		if( parts.length>= 2)
		{
			// There is definitely a variant, and it is everything after the country
			// code sans the separator between the country code and the variant.
			int endIndexOfCountryCode= localeString.indexOf( country)+ country.length();
			// Strip off any leading '_' and whitespace, what's left is the variant.
			variant= trimLeadingWhitespace( localeString.substring( endIndexOfCountryCode));
			if( variant.startsWith( "_"))
				variant= trimLeadingCharacter( variant, '_');
		}
		return( language.length()> 0 ? new Locale( language, country, variant) : null);
	}

	public static String[] addStringToArray( String[] array, String str)
	{
		if( CollectionUtils.isEmpty( array)) 
			return new String[]{ str }; 

		String[] newArr= new String[array.length+ 1];
		System.arraycopy( array, 0, newArr, 0, array.length);
		newArr[array.length]= str;
		return newArr;
	}

	public static String[] concatenateStringArrays( String[] array1, String[] array2)
	{
		if( CollectionUtils.isEmpty( array1)) 	return array2;
		if( CollectionUtils.isEmpty( array2)) 	return array1;
		String[] newArr= new String[array1.length+ array2.length];
		System.arraycopy( array1, 0, newArr, 0, array1.length);
		System.arraycopy( array2, 0, newArr, array1.length, array2.length);
		return newArr;
	}

	public static String[] mergeStringArrays( String[] array1, String[] array2)
	{
		if( CollectionUtils.isEmpty( array1)) 	return array2;
		if( CollectionUtils.isEmpty( array2)) 	return array1;
		List<String> result= new ArrayList<String>();
		result.addAll( Arrays.asList( array1));
		for( int i= 0; i< array2.length; i++)
		{
			String str= array2[i];
			if( !result.contains( str))
				result.add( str);
		}
		return toStringArray( result);
	}

	public static String[] sortStringArray( String[] array)
	{
		if( CollectionUtils.isEmpty( array)) return new String[0];
		Arrays.sort( array);
		return array;
	}

	/**
	 * {@link String} 객체를 담고 있는 {@link Collection} 객체 <code>collection</code>를
	 * 
	 * {@link String} 배열로 변환한다.
	 * 
	 * @param collection 변환할 {@link Collection}
	 * 
	 * @return 변환된 배열
	 */
	public
	static
	String[]
	toStringArray(
		final Collection<String> collection
	)
	{
		if ( null == collection )
		{
			return null;
		}
		return collection.toArray( new String[collection.size()] );
	}

	/**
	 * {@link String} 객체를 순회하는 {@link Enumeration} 객체를 이용하여 
	 * 
	 * {@link String} 배열로 변환한다.
	 * 
	 * @param enumeration 변환할 {@link Enumeration}
	 * 
	 * @return 변환된 배열
	 * 
	 * @see #toStringArray(Collection)
	 */
	public
	static
	String[]
	toStringArray(
		final Enumeration<String> enumeration
	)
	{
		if ( null == enumeration )
		{
			return null;
		}
		final List<String> list = Collections.list( enumeration );
		return toStringArray( list );
	}

	public static String[] trimArrayElements( String[] array)
	{
		if( CollectionUtils.isEmpty( array))	return new String[0];
		String[] result= new String[array.length];
		for( int i= 0; i< array.length; i++)
		{
			String element= array[i];
			result[i]= ( element!= null ? element.trim() : null);
		}
		return result;
	}

	public static String[] removeDuplicateStrings( String[] array)
	{
		if( CollectionUtils.isEmpty( array)) 	return array;
		Set<String> set= new TreeSet<String>();
		for( int i= 0; i< array.length; i++)
			set.add( array[i]);

		return toStringArray( set);
	}

	public static String[] split( String toSplit, String delimiter)
	{
		if( !hasLength( toSplit)|| !hasLength( delimiter))
			return null;
		int offset = toSplit.indexOf( delimiter);
		if( offset< 0 ) 	
			return null;

		String beforeDelimiter= toSplit.substring( 0, offset);
		String afterDelimiter= toSplit.substring( offset+ delimiter.length());
		return new String[]{ beforeDelimiter, afterDelimiter };
	}

	public static Properties splitArrayElementsIntoProperties( String[] array, String delimiter)
	{
		return splitArrayElementsIntoProperties( array, delimiter, null);
	}

	public static Properties splitArrayElementsIntoProperties( String[] array, String delimiter, String charsToDelete)
	{

		if( CollectionUtils.isEmpty( array))
			return null;
		Properties result= new Properties();
		for( int i= 0; i< array.length; i++)
		{
			String element= array[i];
			if( charsToDelete!= null)
				element= deleteAny( array[i], charsToDelete);

			String[] splittedElement= split( element, delimiter);
			if( splittedElement== null)
				continue;

			result.setProperty( splittedElement[0].trim(), splittedElement[1].trim());
		}
		return result;
	}

	public static String[] tokenizeToStringArray( String str, String delimiters)
	{
		return tokenizeToStringArray( str, delimiters, true, true);
	}

	public static String[] tokenizeToStringArray( String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens)
	{

		if( str== null)	 return null;

		StringTokenizer st= new StringTokenizer( str, delimiters);
		List<String> tokens= new ArrayList<String>();
		while( st.hasMoreTokens())
		{
			String token= st.nextToken();
			if( trimTokens)
				token= token.trim();
			if( !ignoreEmptyTokens|| token.length()> 0)
				tokens.add( token);
		}
		return toStringArray( tokens);
	}

	public static String[] delimitedListToStringArray( String str, String delimiter)
	{
		return delimitedListToStringArray( str, delimiter, null);
	}

	public static String[] delimitedListToStringArray( String str, String delimiter, String charsToDelete)
	{
		if( str== null) 			return new String[0];
		if( delimiter== null) 	return new String[]{ str }; 
		List<String> result= new ArrayList<String>();
		if( "".equals( delimiter))
		{
			for( int i= 0; i< str.length(); i++)
				result.add( deleteAny( str.substring( i, i+ 1), charsToDelete));
		}
		else
		{
			int pos= 0;
			int delPos= 0;
			while( ( delPos= str.indexOf( delimiter, pos))!= -1)
			{
				result.add( deleteAny( str.substring( pos, delPos), charsToDelete));
				pos= delPos+ delimiter.length();
			}
			if( str.length()> 0&& pos<= str.length())
			{
				// Add rest of String, but not in case of empty input.
				result.add( deleteAny( str.substring( pos), charsToDelete));
			}
		}
		return toStringArray( result);
	}

	public static String[] commaDelimitedListToStringArray( String str)
	{
		return delimitedListToStringArray( str, ",");
	}

	public static Set<String> commaDelimitedListToSet( String str)
	{
		Set<String> set= new TreeSet<String>();
		String[] tokens= commaDelimitedListToStringArray( str);
		for( int i= 0; i< tokens.length; i++)
			set.add( tokens[i]);

		return set;
	}

	public static String collectionToDelimitedString( Collection<String> coll, String delim, String prefix, String suffix)
	{
		if( CollectionUtils.isEmpty( coll))	return "";
		StringBuilder sb= new StringBuilder();
		Iterator<String> it= coll.iterator();
		while( it.hasNext())
		{
			sb.append( prefix).append( it.next()).append( suffix);
			if( it.hasNext())
				sb.append( delim);
		}
		return sb.toString();
	}

	public static String collectionToDelimitedString( Collection<String> coll, String delim)
	{
		return collectionToDelimitedString( coll, delim, "", "");
	}

	public static String collectionToCommaDelimitedString( Collection<String> coll)
	{
		return collectionToDelimitedString( coll, ",");
	}

	public static String arrayToDelimitedString( Object[] arr, String delim)
	{
		if( CollectionUtils.isEmpty( arr)) 	return "";
		StringBuilder sb= new StringBuilder();
		for( int i= 0; i< arr.length; i++)
		{
			if( i> 0)
				sb.append( delim);

			sb.append( arr[i]);
		}
		return sb.toString();
	}

	public static String arrayToCommaDelimitedString( Object[] arr)
	{
		return arrayToDelimitedString( arr, ",");
	}


	/**
	 * 입력 문자열들을 비교하여 같은지 여부를 판단한다.
	 * 
	 * null 과 빈문자열( "" )을 동일하게 취급한다.
	 * 
	 * @param strs 비교할 문자열들
	 * 
	 * @return 입력 문자열들이 같은지 여부
	 */
	public static
	boolean
	equals(
		final String... strs
	) {
		if ( 0 == CollectionUtils.size( strs ) )
		{
			return true;
		}
		
		String iter = strs[0];
		for ( String str : strs )
		{
			if ( iter == str )
			{
				continue;
			}
			if ( isEmpty( iter ) && isEmpty( str ) )
			{
				continue;
			}
			if ( null == iter )
			{
				return false;
			}
			else if ( !iter.equals( str ) )
			{
				return false;
			}
		}
		
		return true;
	}

	public static
	String
	getWord(
		final String doc,
		final int index
	)
	{
		final StringBuilder buffer = new StringBuilder();

		int position = index;

		if ( doc.length() < index )
		{
			return "";
		}

		for ( int i = position - 1 ; 0 <= i ; --i )
		{

			int ch = doc.charAt( i );

			if ( Character.isWhitespace( ch ) )
			{
				break;
			}
			buffer.append( (char) ch );
		}
		buffer.reverse();

		for ( int i = position , n = doc.length() ; i < n ; ++i )
		{
			int ch = doc.charAt( i );
			if ( Character.isWhitespace( ch ) )
			{
				break;
			}
			buffer.append( (char) ch );
		}

		return buffer.toString();
	}

	public static
	String
	getPreviousWord(
		final String doc,
		final int index
	)
	{
		final StringBuilder buffer = new StringBuilder();

		if ( index < 0 || doc.length() < index)
		{
			logger.debug( "인덱스[{0}]가 범위[0:{1}]를 벗어났습니다.", index, doc.length() );
			return "";
		}

		int ch = 0;
		boolean bNeedWord = false;
		if ( index < doc.length() )
		{
			ch = doc.charAt( index );
			bNeedWord =  Character.isWhitespace( ch );
		}
		logger.debug( "인덱스 위치의 글자는 '{0}'입니다.", (char ) ch );
		for ( int i = index - 1 ; 0 <= i ; --i )
		{
			ch = doc.charAt( i );
			
			if ( Character.isWhitespace( ch ) )
			{
				if ( bNeedWord )
				{
					continue;
				}
				logger.debug( "{0}에서 공백을 만났습니다.", i );
				break;
			}
			bNeedWord = false;
			buffer.append( (char) ch );
		}
		
		return buffer.reverse().toString();
	}

}
