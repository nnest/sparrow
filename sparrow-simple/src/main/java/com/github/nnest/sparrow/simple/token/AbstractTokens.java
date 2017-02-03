/**
 * 
 */
package com.github.nnest.sparrow.simple.token;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

/**
 * abstract tokens
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractTokens implements Tokens {
	private final static Pattern PATTERN = Pattern.compile("\\$\\{(.*?)\\}");
	private List<Token> tokens = null;

	public AbstractTokens(String text) {
		this.setTokens(parse(text));
	}

	/**
	 * @param tokens
	 *            the tokens to set
	 */
	protected void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.token.Tokens#getTokens()
	 */
	@Override
	public List<Token> getTokens() {
		return this.tokens;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.token.Tokens#tokenCount()
	 */
	@Override
	public int tokenCount() {
		return this.tokens == null ? 0 : this.tokens.size();
	}

	/**
	 * parse string to tokens
	 * 
	 * @param str
	 *            string
	 * @return tokens
	 */
	public static List<Token> parse(String str) {
		assert Strings.nullToEmpty(str).length() != 0 : "String cannot be null or empty.";

		List<Token> tokens = new LinkedList<Token>();

		Matcher matcher = PATTERN.matcher(str);
		int previousEnd = 0;
		int start = 0;
		while (matcher.find(start)) {
			String text = matcher.group();
			int startPos = matcher.start();
			int endPos = matcher.end();
			start = endPos;

			if (startPos > previousEnd) {
				tokens.add(new StringToken(str.substring(previousEnd, startPos)));
			}
			if (text.length() == 3) {
				tokens.add(new StringToken(text));
			} else {
				tokens.add(new ReferenceToken(text.substring(2, text.length() - 1)));
			}
			previousEnd = endPos;
		}
		if (previousEnd < str.length()) {
			tokens.add(new StringToken(str.substring(previousEnd)));
		}
		return tokens;
	}
}
