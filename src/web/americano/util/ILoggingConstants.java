package americano.util;

/**
 * ILoggingConstants
 * 
 * 로거에 관련된 상수
 */
public interface ILoggingConstants
{
	/* Logger */
	String LOGGER_DEFAULT = "__default";
	String LOGGER_QUIET = "__quiet";

	/* Level */
	int P_ERROR = -10000;
	int P_WARN = 10000;
	int P_INFO = 30000;
	int P_DEBUG =50000;
	
	/* Dump Format */
	char CONTROL_CHARS_SHOWER = '.';

	char[] HEXA_CHARS = new char[] { 
		'0', '1', '2', '3', '4', '5', '6', '7', 
		'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	};

	int N_INT_BY_BYTE = 4;

	int WIDTH_PER_LINE = 16;

	char TWO_BYTES_CHARS_SHOWER = '?';

	
	/* Predefined Message */
	Object FLUSH = new Object()
	{
		public String toString()
		{
			return null;
		}
	};
	Object DISCARD = new Object()
	{
		public String toString()
		{
			return null;
		}
		
	};

}
