package command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import model.IRCDao;
import parser.IRCMessage;
import util.IRCFrameworkErrorException;

public class IRCCommandFactoryImpl implements IRCCommandFactory {

	private static final String NUMERIC_RESPONSE = "\\d\\d\\d";
	private static final String COMMAND = "Command";
	private static final String LOCATION = "command";
	private static final String CONNECTION_REPLY = "ConnectionReply";

	@Override
	public IRCCommand build(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		if (dao == null || ircMessage == null) {
			throw new IllegalArgumentException();
		}
		if (ircMessage.getCommand() == null) {
			throw new InvalidCommandException();
		}
		if (ircMessage.getCommand().matches(NUMERIC_RESPONSE)) {
			return getInstance(getReplyClassName(ircMessage.getCommand()), dao,
					ircMessage);
		}
		return getInstance(getCommandClassName(ircMessage.getCommand()), dao,
				ircMessage);
	}

	private String getReplyClassName(String replyNumber)
			throws InvalidCommandException {
		Integer number = Integer.valueOf(replyNumber);
		if (0 <= number && number < 100) {
			return LOCATION + "." + CONNECTION_REPLY + COMMAND;
		}
		throw new InvalidCommandException();
	}

	private String getCommandClassName(String mode) {
		String upperCaseMode = mode.substring(0, 1).toUpperCase()
				+ mode.substring(1).toLowerCase();
		String simpleClassName = upperCaseMode + COMMAND;
		return LOCATION + "." + simpleClassName;
	}

	private IRCCommand getInstance(String className, IRCDao dao,
			IRCMessage message) throws InvalidCommandException {
		try {
			Class<? extends IRCCommand> clazz = getIRCCommandClass(className);
			Constructor<? extends IRCCommand> constructor;
			constructor = clazz.getConstructor(IRCDao.class, IRCMessage.class);
			return constructor.newInstance(dao, message);
		} catch (InvocationTargetException e) {
			System.out.println("Couldn't load command " + className);
			throw new InvalidCommandException();
		} catch (InvalidCommandException e) {
			System.out.println("Couldn't load command " + className);
			throw e;
		} catch (Exception e) {
			System.out.println("Couldn't load command " + className);
			e.printStackTrace();
			throw new IRCFrameworkErrorException();
		}
	}

	@SuppressWarnings("unchecked")
	private Class<? extends IRCCommand> getIRCCommandClass(String className)
			throws InvalidCommandException {
		try {
			return (Class<IRCCommand>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new InvalidCommandException();
		}
	}
}
