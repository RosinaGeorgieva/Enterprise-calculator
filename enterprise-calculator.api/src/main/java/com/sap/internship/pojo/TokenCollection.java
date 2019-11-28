package com.sap.internship.pojo;

import java.util.ArrayList;
import java.util.List;

import com.sap.internship.interfac.IToken;

public class TokenCollection {
	private List<IToken> tokens;
	
	public TokenCollection(List<IToken> tokens) {
		this.tokens = new ArrayList<IToken>(tokens);
	}
	
	public List<IToken> getTokens(){
		return this.tokens;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tokens == null) ? 0 : tokens.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenCollection other = (TokenCollection) obj;
		if (tokens == null) {
			if (other.tokens != null)
				return false;
		} else if (!tokens.equals(other.tokens))
			return false;
		return true;
	}
	
	public void print() {
		for(IToken e : tokens) {
			if(e instanceof Operand) {
				System.out.println(((Operand)e).getValue());
			}
			else {
//				System.out.println(((com.sap.internship.pojo.enumeration.Operator)e).getChar());
				System.out.println(((Operator)e).getChar());
			}
		}
	}
}
