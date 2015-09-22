package model;

import java.util.ArrayList;
import java.util.List;

import util.StringConverter;

public class IRCFloodLimit {

	private int ctcps;
	private int joins;
	private int knocks;
	private int messages;
	private int nickchanges;
	private int lines;
	private int seconds;

	/**
	 * @param floodLimitRegex
	 *            Regex for the flood limit '[' &lt;amount&gt; &lt;floodType&gt;
	 *            {,...} ']' ':' <&lt;seconds&gt;.
	 */
	public IRCFloodLimit(String floodLimitRegex) {
		if (floodLimitRegex == null) {
			throw new IllegalArgumentException();
		}
		parseFloodLimit(floodLimitRegex);
	}

	// TODO : Limit number and seconds
	public IRCFloodLimit(int lines, int seconds) {
		super();
		this.lines = lines;
		this.seconds = seconds;
	}

	// TODO : Limit seconds
	public IRCFloodLimit(int seconds) {
		this.seconds = seconds;
	}

	// TODO : Limit number
	/**
	 * @param floodType
	 *            Flood type to set a limit.
	 * @param number
	 *            Limit of the flood type in the amount of seconds provided.
	 * @throws IllegalArgumentException
	 *             If floodType is null or number is not positive.
	 */
	public void setFloodType(FloodType floodType, int number) {
		if (floodType == null || number <= 0) {
			throw new IllegalArgumentException();
		}
		switch (floodType) {
		case CTCP:
			this.ctcps = number;
			break;
		case JOIN:
			this.joins = number;
			break;
		case KNOCK:
			this.knocks = number;
			break;
		case LINE:
			this.lines = number;
			break;
		case MESSAGE:
			this.messages = number;
			break;
		case NICKCHANGE:
			this.nickchanges = number;
			break;
		default:
			break;
		}
	}

	// TODO : Limit seconds
	/**
	 * Sets the amount of seconds for the different flood types before taking an
	 * action (kick action).
	 * 
	 * @param seconds
	 * @throws IllegalArgumentException
	 *             If seconds is not positive.
	 */
	public void setSeconds(int seconds) {
		if (seconds <= 0) {
			throw new IllegalArgumentException();
		}
		this.seconds = seconds;
	}

	/**
	 * Returns a String that represents the instance for an IRC message.
	 * 
	 * @return a String that represents the instance for an IRC message.
	 */
	public String getString() {
		List<String> floodLimits = new ArrayList<String>();
		if (ctcps != 0) {
			floodLimits.add(String.valueOf(ctcps) + FloodType.CTCP.getLetter());
		}
		if (joins != 0) {
			floodLimits.add(String.valueOf(joins) + FloodType.JOIN.getLetter());
		}
		if (knocks != 0) {
			floodLimits.add(String.valueOf(knocks)
					+ FloodType.KNOCK.getLetter());
		}
		if (messages != 0) {
			floodLimits.add(String.valueOf(messages)
					+ FloodType.MESSAGE.getLetter());
		}
		if (nickchanges != 0) {
			floodLimits.add(String.valueOf(nickchanges)
					+ FloodType.NICKCHANGE.getLetter());
		}
		if (lines != 0) {
			floodLimits.add(String.valueOf(lines) + FloodType.LINE.getLetter());
		}
		return "[" + StringConverter.stringfyList(floodLimits, ",", "", "")
				+ "]:" + String.valueOf(seconds);
	}

	public int getCtcps() {
		return ctcps;
	}

	public int getJoins() {
		return joins;
	}

	public int getKnocks() {
		return knocks;
	}

	public int getMessages() {
		return messages;
	}

	public int getNickchanges() {
		return nickchanges;
	}

	public int getLines() {
		return lines;
	}

	public int getSeconds() {
		return seconds;
	}

	private void parseFloodLimit(String floodLimitRegex) {
		char[] charArr = floodLimitRegex.toCharArray();
		int i = 0;
		if (i >= charArr.length || charArr[i++] != '[') {
			throw new IllegalArgumentException();
		}
		boolean prev = false;
		while (i < charArr.length && charArr[i] != ']') {
			if (prev) {
				if (charArr[i++] != ',') {
					throw new IllegalArgumentException();
				}
			}
			String numberString = parseNumber(charArr, i);
			i += numberString.length();
			if (i >= charArr.length) {
				throw new IllegalArgumentException();
			}
			FloodType floodType = FloodType.getFloodType(charArr[i++]);
			if (floodType == null) {
				throw new IllegalArgumentException();
			}
			setFloodType(floodType, Integer.valueOf(numberString));
			prev = true;
		}
		if (i >= charArr.length) {
			throw new IllegalArgumentException();
		}
		i++;
		if (i >= charArr.length || charArr[i++] != ':') {
			throw new IllegalArgumentException();
		}
		String numberString = parseNumber(charArr, i);
		i += numberString.length();
		if (i < charArr.length) {
			throw new IllegalArgumentException();
		}
		setSeconds(Integer.valueOf(numberString));
	}

	private String parseNumber(char[] charArr, int i) {
		StringBuilder numBuilder = new StringBuilder();
		while (i < charArr.length && Character.isDigit(charArr[i])) {
			numBuilder.append(charArr[i++]);
		}
		if (numBuilder.length() == 0) {
			throw new IllegalArgumentException();
		}
		return numBuilder.toString();
	}
}
