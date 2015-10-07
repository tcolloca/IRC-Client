package util;

public interface IRCValues {

	public static final String MSG_END_SEQ = "\r\n";

	public static final char PREFIX_INDICATOR = ':';
	public static final char EMPTY_SPACE = ' ';
	public static final char USERNAME_INDICATOR = '!';
	public static final char HOST_INDICATOR = '@';
	public static final char TRAILING_INDICATOR = ':';
	public static final char IN_PARAM_SEPARATOR = ',';
	
	public static final char HALFOP_CHAR = '%';
	public static final char OP_CHAR = '@';
	public static final char SUPEROP_CHAR = '&';
	public static final char OWNER_CHAR = '~';
	public static final char VOICED_CHAR = '+';

	public static final String UNUSED = "*";
	public static final String ZERO = "0";

	public static final char ADD = '+';
	public static final char DEL = '-';
}
