package command.reply;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import command.IRCCommand;
import command.InvalidCommandException;
import parser.IRCMessage;
import util.IRCFrameworkErrorException;

public class IRCCommandFactoryImpl implements IRCReplyFactory {

	private static final String NUMERIC_RESPONSE = "\\d\\d\\d";
	private static final String COMMAND = "Command";
	private static final String LOCATION = "command";
	private static final String CONNECTION_REPLY = "ConnectionReply";
	private static final String COMMAND_REPLY = "CommandReply";

	@Override
	public IRCCommand build(IRCMessage ircMessage)
			throws InvalidCommandException {
		if (ircMessage == null) {
			throw new IllegalArgumentException();
		}
		if (ircMessage.getCommand() == null) {
			throw new InvalidCommandException();
		}
		if (ircMessage.getCommand().matches(NUMERIC_RESPONSE)) {
			return getInstance(getReplyClassName(ircMessage.getCommand()),
					ircMessage);
		}
		return getInstance(getCommandClassName(ircMessage.getCommand()),
				ircMessage);
	}

	private String getReplyClassName(String replyNumber)
			throws InvalidCommandException {
		Integer number = Integer.valueOf(replyNumber);
		if (0 <= number && number < 100) {
			return LOCATION + "." + CONNECTION_REPLY + COMMAND;
		} else if (200 <= number && number < 400) {
			return LOCATION + "." + COMMAND_REPLY + COMMAND;
		}
		throw new InvalidCommandException();
	}

	private String getCommandClassName(String mode) {
		String upperCaseMode = mode.substring(0, 1).toUpperCase()
				+ mode.substring(1).toLowerCase();
		String simpleClassName = upperCaseMode + COMMAND;
		return LOCATION + "." + simpleClassName;
	}

	private IRCCommand getInstance(String className, IRCMessage message)
			throws InvalidCommandException {
		try {
			Class<? extends IRCCommand> clazz = getIRCCommandClass(className);
			Constructor<? extends IRCCommand> constructor;
			constructor = clazz.getConstructor(IRCMessage.class);
			return constructor.newInstance(message);
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
