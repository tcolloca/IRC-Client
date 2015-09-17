package util;

public interface IRCValues {

	public static final String MSG_END_SEQ = "\r\n";
	
	public static final char PREFIX_INDICATOR = ':';
	public static final char EMPTY_SPACE = ' ';
	public static final char USER_INDICATOR = '!';
	public static final char HOST_INDICATOR = '@';
	public static final char TRAILING_INDICATOR = ':';
	
	public static final String UNUSED = "*";
	
	public static final String PASS_COMMAND = "PASS";
	public static final String NICK_COMMAND = "NICK";
	public static final String USER_COMMAND = "USER";

	public static final String OPER_COMMAND = "OPER";
	public static final String MODE_COMMAND = "MODE";
	public static final String SERVICE_COMMAND = "SERVICE";
	public static final String QUIT_COMMAND = "QUIT";

	public static final String PONG_COMMAND = "PONG";
	public static final String JOIN_COMMAND = "JOIN";
	public static final String PART_COMMAND = "PART";
	
	public static final String NOTICE_COMMAND = "NOTICE";
	public static final String PING_COMMAND = "PING";
	
	//
	// private static final String NOTICE_AUTH_COMMAND = "NOTICE AUTH";
	// private static final String PING_COMMAND = "PING";
	// private static final String MODE_COMMAND = "MODE";
	// private static final String OPER_COMMAND = "OPER";
}
