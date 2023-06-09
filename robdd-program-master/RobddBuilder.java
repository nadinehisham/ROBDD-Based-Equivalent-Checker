
public class RobddBuilder {
	/*
	 * @param expression The postfix boolean expression to be converted to an ROBDD.
	 * @param variableOrder The char array containing the variable order.
	 * @return The RobddNode that holds the root of the ROBDD.
	 */
	public static RobddNode build(char[] exp, char[] variableOrder, Operators ops) throws ExpressionError {
		UniqueTable h = new UniqueTable(500);
		RobddNodeTable t =  new RobddNodeTable(200);
		int root = buildHelper(exp, 0, variableOrder, t, h, ops);
		return t.get(root);
	}
	/*
	 * @param expression The postfix boolean expression to be converted to an ROBDD.
	 * @param variableOrder The char array containing the variable order.
	 * @param i The current variable being expanded; i refers to its index in the variable array.
	 * @param t The table holding the ROBDD nodes.
	 * @param h The unique table used to look up the ROBDD nodes.
	 * @return The int value for table index that holds the root of the ROBDD.
	 */
	private static int buildHelper(char[] exp, int i, char[]varOrder, RobddNodeTable t, 
									UniqueTable h, Operators ops) throws ExpressionError {
		if(i < varOrder.length) {
			int v0 = buildHelper(shannonExpansion(exp, varOrder[i], '0'), (i + 1), varOrder, t, h, ops);
			int v1 = buildHelper(shannonExpansion(exp, varOrder[i], '1'), (i + 1), varOrder, t, h, ops);
			return make(i, v0, v1, t, h);
		} else {
			return postfixEvaluator(exp, ops);
		}
	}
	
	/*
	 * @param i The variable number.
	 * @param l The index for the low path.
	 * @param h The index for the high path.
	 * @param t The table holding the ROBDD nodes.
	 * @param h The unique table used to look up the ROBDD nodes.
	 * @return The int value for table index that holds the root of the ROBDD.
	 */
	private static int make(int i, int l, int h, RobddNodeTable t, UniqueTable hTable) {
		if(l == h) {
			return l;
		} else if(hTable.isMember(i, l, h)) {
			return hTable.findMember(i, l, h);
		} else {
			int u = t.add(i, l, h);
			hTable.insert(u, i, l, h);
			return u;
		}
	}

    /*
	 * @param exp The boolean expression to be evaluated.
	 * @return The result of the evaluation.
	 */
	public static int postfixEvaluator(char[] exp, Operators ops) throws ExpressionError {
		NodeStack<Character> stack = new NodeStack<>();
		int index = 0;
		char currChar = 'a';
		
		while(index < exp.length) {
			currChar = exp[index];
			if(currChar == '0' || currChar =='1') {
				// It is a variable.
				stack.push(new Character(currChar));
				
			} else if(ShuntingYardAlgorithm.checkArray(currChar, ops.operators)) {
				// It is an operator.
				int operatorArgs = ops.getArity(currChar);
				
				if(operatorArgs > stack.size()) {
					throw new ExpressionError("Insufficient number of variables in expression.");
				}
				
				if(operatorArgs == 2) {
					stack.push(new Character(ops.performOperation
										(currChar, stack.pop().charValue(), stack.pop().charValue())));
				} else {
					stack.push(new Character(ops.performOperation(currChar, stack.pop().charValue())));
				}
			} else {
				throw new ExpressionError("Not all variables have been initialized to values.");
			}
			index++;
		}
		
		if(stack.size() == 1) {
			return Character.getNumericValue(stack.pop().charValue());
		} else {
			throw new ExpressionError("There are too many variables in the Expression.");
		}
	}
	
	/** Performs Shannon Expansion on expression by replacing variables with replacement.
	 * Replacement should be either 0 or 1 to correctly expand the expression.
	 * @param exp The boolean expression to be expanded.
	 * @param variable The variable to be replaced.
	 * @param replacement The value to take the variable's place.
	 * @return The result of the expansion.
	 */
	private static char[] shannonExpansion(char[] exp, char variable, char replacement) {
		int index = 0;
		char currChar = 'a';
		char[] newExp = new char[exp.length];
		
		while(index < newExp.length) {
			// Take char from old expression
			currChar = exp[index];
			
			// Use the replacement in position i if variable matches, else use value from exp
			if(currChar == variable) {
				newExp[index] = replacement;
			} else {
				newExp[index] = currChar;
			}
			index ++;
		}
		return newExp;
	}
}