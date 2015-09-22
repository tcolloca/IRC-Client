package model;

public class IRCUserFlagsImpl implements IRCUserFlags {

	private boolean isAway;
	private boolean isIRCOp;
	private boolean isBot;
	private boolean isRegistered;
	private boolean isDeaf;

	@Override
	public boolean isAway() {
		return isAway;
	}

	@Override
	public boolean isIRCOp() {
		return isIRCOp;
	}

	@Override
	public boolean isBot() {
		return isBot;
	}

	@Override
	public boolean isRegistered() {
		return isRegistered;
	}

	@Override
	public boolean isDeaf() {
		return isDeaf;
	}

}
