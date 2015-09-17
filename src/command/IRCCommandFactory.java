package command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import parser.IRCMessage;
import util.IRCFrameworkErrorException;
import event.IRCEventBroadcaster;

/**
 * This class builds an {@link IRCCommand} given a {@link IRCMessage}.
 * 
 * @author Tomas
 */
public class IRCCommandFactory {

	private static final String COMMAND = "Command";
	private static final String LOCATION = "command";

	/**
	 * Returns the {@link IRCCommand} associated with the ircMessage received.
	 * 
	 * @param ircMessage
	 * @param broadcaster
	 *            Broadcaster whose corresponding method will be called when
	 *            calling the onExecute method of the IRCCommand.
	 * @return the {@link IRCCommand} associated with the ircMessage received.
	 * @throws InvalidCommandException
	 *             If the command in the ircMessage is not recognized.
	 * @throws IllegalArgumentException
	 *             If ircMessage or broadcaster is null.
	 */
	public IRCCommand build(IRCMessage ircMessage,
			IRCEventBroadcaster broadcaster) throws InvalidCommandException {
		if (ircMessage == null || broadcaster == null) {
			throw new IllegalArgumentException();
		}
		if (ircMessage.getCommand() == null) {
			throw new InvalidCommandException();
		}
		return getInstance(getClassName(ircMessage.getCommand()), ircMessage,
				broadcaster);
	}

	private String getClassName(String mode) {
		String upperCaseMode = mode.substring(0, 1).toUpperCase()
				+ mode.substring(1).toLowerCase();
		String simpleClassName = upperCaseMode + COMMAND;
		return LOCATION + "." + simpleClassName;
	}

	private IRCCommand getInstance(String className, IRCMessage message,
			IRCEventBroadcaster broadcaster) throws InvalidCommandException {
		try {
			Class<? extends IRCCommand> clazz = getIRCCommandClass(className);
			System.out.println(className + " " + clazz);
			Constructor<? extends IRCCommand> constructor;
			constructor = clazz.getConstructor(IRCMessage.class,
					IRCEventBroadcaster.class);
			return constructor.newInstance(message, broadcaster);
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
