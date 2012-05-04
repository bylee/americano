package americano.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * ICollectionConstants
 * 
 * 자바 Collection들에서 사용하게 될 상수 정의
 */
public interface
ICollectionConstants
{
	
	/**
	 * 빈 bytes
	 */
	byte[] EMPTY_BYTE_ARRAY = new byte[0]; 
	byte[] EMPTY_BYTES = EMPTY_BYTE_ARRAY;

	/* Object */
	/**
	 * 빈 객체 배열
	 */
	Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	
	/**
	 * 비어있는 Collection
	 */
	Collection<Object> EMPTY_COLLECTION = 
		Collections.unmodifiableCollection( new ArrayList<Object>() );

	/**
	 * 빈 문자 배열
	 */
	String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * 빈 파일 배열
	 */
	File[] EMPTY_FILE_ARRAY = new File[0];
	
}
