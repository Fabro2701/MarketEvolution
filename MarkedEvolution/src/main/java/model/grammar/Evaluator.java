package model.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import backtesting.order.Order.OrderType;



public class Evaluator {
	
	JSONObject _statement;
	HashMap<String,String>_variables;//for simplicity all variables are global
	
	/**
	 * Reads a AST in JSON format
	 * @param program
	 */
	public Evaluator(JSONObject program){
		_variables = new HashMap<String,String>();
		
		_statement = program.getJSONArray("body").getJSONObject(0);
		
	}
	
	public OrderType getNext() {
		String result = this._evaluate(_statement);
		if(result==null)return OrderType.NOTHING;
		try {
			OrderType r = OrderType.valueOf(result);
			return r;
		}catch (Exception e) {}
		
		//parse number format
		Float f = Float.parseFloat(result);
		if(f>=0.66f) {
			return OrderType.BUY;
		}
		else if(f<0.66f && f>0.33f) {
			return OrderType.NOTHING;
		}
		else {
			return OrderType.SELL;
		}

	}
	
	/**
	 * Evaluates the given query delegating the evaluation depending on the statement type
	 * @param query
	 * @return
	 */
	private String _evaluate(JSONObject query) {
		String type = query.getString("type");
		
		if(type.contains("FunctionCall")) {
			return _functionEvaluation(query.getString("name"),query.getJSONArray("parameters"));
		}
		
		if(type.contains("ReturnStatement")) {
			int op = 1;
			if(op==0) {//numerical
				return _evaluate(query.getJSONObject("result"));
			}
			else {//string
				return query.getString("result");
			}
			//
		}
		
		if(type.contains("Literal")) {
			return query.getString("value");
		}
		
		//Normal expression
		if(type.equals("ExpressionStatement")) {
			return _evaluate(query.getJSONObject("expression"));
		}
		
		//Binary expression: left operator right
		if(type.equals("BinaryExpression")) {
			String operator = query.getString("operator");
			JSONObject left = query.getJSONObject("left");
			JSONObject right = query.getJSONObject("right");
			return _binaryEvaluation(left,operator,right);
		}
		
		//Logical expression: left operator right
		if(type.equals("LogicalExpression")) {
			String operator = query.getString("operator");
			JSONObject left = query.getJSONObject("left");
			JSONObject right = query.getJSONObject("right");
			return _logicalEvaluation(left,operator,right);
		}
		
		//If Statement
		//if(test) consequent
		//else alternate
		if(type.equals("IfStatement")) {
			JSONObject test = query.getJSONObject("test");
			
			JSONObject consequent = query.getJSONObject("consequent");
			
			JSONObject alternate = null;
			if(query.has("alternate")) {
				alternate = query.getJSONObject("alternate");
			}
			return _ifEvaluation(test,consequent,alternate);
		}
		
		//Block Statement 
		if(type.equals("BlockStatement")) {
			JSONArray body = query.getJSONArray("body");
			if(body.length()>1)System.err.println("body has more than 1 statements");
			return this._evaluate(body.getJSONObject(0));			
		}
		
		//Unary Expression
		// operator (argument)
		if(type.equals("UnaryExpression")) {
			JSONObject argument = query.getJSONObject("argument");
			String operator = query.getString("operator");
			return this._unaryEvaluation(argument, operator);
		}
		
		//Identifier are the variables
		if(type.equals("Identifier")) {
			String name = query.getString("name");
			String r = _variables.get(name);//we look up them directly on our map
			if(r==null)System.err.println("variable "+name+" not found");
			return r;
		}
		
		System.err.println("type "+type+" not found");

		return null;
	}
	
	
	private String _functionEvaluation(String string, JSONArray arguments) {
		Float r = Float.parseFloat(this._evaluate(arguments.getJSONObject(0)));
		if(string.equals("max")) {
			for(int i=1;i<arguments.length();i++) {
				r = Math.max(Float.parseFloat(this._evaluate(arguments.getJSONObject(i))), r);
			}
		}
		else {
			for(int i=1;i<arguments.length();i++) {
				r = Math.min(Float.parseFloat(this._evaluate(arguments.getJSONObject(i))), r);
			}
		}
		return r.toString();
	}
	private Float max(Float... arr) {
		Float r = arr[0];
		for(Float e:arr) r = Math.max(r, e);
		return r;
	}
	private Float min(Float... arr) {
		Float r = arr[0];
		for(Float e:arr) r = Math.min(r, e);
		return r;
	}

	/**
	 * Evaluates if statement 
	 * @param test
	 * @param consequent
	 * @param alternate
	 * @return
	 */
	private String _ifEvaluation(JSONObject test, JSONObject consequent, JSONObject alternate) {
		Boolean cond = _BooleanValueOf(this._evaluate(test));
		if(cond) {
			return this._evaluate(consequent);
		}
		else if(alternate!=null) {
			return this._evaluate(alternate);
		}
		else {
			return null;
		}
	}
	/**
	 * Evaluates logical operation 
	 * @param left
	 * @param operator
	 * @param right
	 * @return
	 */
	private String _logicalEvaluation(JSONObject left, String operator, JSONObject right) {
		Boolean result=null;
		switch (operator) {
			case "||":
				result = _BooleanValueOf(_evaluate(left)) || _BooleanValueOf(_evaluate(right));
				break;
			case "&&":
				result = _BooleanValueOf(_evaluate(left)) && _BooleanValueOf(_evaluate(right));
				break;
			case "<":
				result = Float.valueOf(_evaluate(left)).floatValue() < Float.valueOf(_evaluate(right)).floatValue();
				break;
			case "<=":
				result = Float.valueOf(_evaluate(left)).floatValue() <= Float.valueOf(_evaluate(right)).floatValue();
				break;
			case ">":
				result = Float.valueOf(_evaluate(left)).floatValue() > Float.valueOf(_evaluate(right)).floatValue();
				break;
			case ">=":
				result = Float.valueOf(_evaluate(left)).floatValue() >= Float.valueOf(_evaluate(right)).floatValue();
				break;
			case "==":
				result = Float.valueOf(_evaluate(left)).floatValue() == Float.valueOf(_evaluate(right)).floatValue();
				break;
		}
		return result.toString();
	}
	/**
	 * Evaluates binary operations
	 * @param left
	 * @param operator
	 * @param right
	 * @return
	 */
	private String _binaryEvaluation(JSONObject left, String operator, JSONObject right) {
		Float result=null;
		switch (operator) {
			case "+":
				result = Float.valueOf(_evaluate(left)) + Float.valueOf(_evaluate(right));
				break;
			case "-":
				result = Float.valueOf(_evaluate(left)) - Float.valueOf(_evaluate(right));
				break;
			case "*":
				result = Float.valueOf(_evaluate(left)) * Float.valueOf(_evaluate(right));
				break;
			case "/":
				result = Float.valueOf(_evaluate(left)) / Float.valueOf(_evaluate(right));
				break;
		}
		return result.toString();
		
	}
	/**
	 * Evaluates unary operations
	 * @param argument
	 * @param operator
	 * @return
	 */
	private String _unaryEvaluation(JSONObject argument, String operator) {
		if(operator.equals("!")) {
			return String.valueOf(!_BooleanValueOf(this._evaluate(argument)));
		}
		return null;
	}
	/**
	 * Alternative to Boolean.valueOf() 
	 * This method evaluates true the numbers>0
	 * @param s
	 * @return
	 */
	private boolean _BooleanValueOf(String s) {
		switch(s) {
			case "true":
				return true;
			case "false":
				return false;
			default:
				return Float.valueOf(s)>0.f;
		}
	}
	/**
	 * Add observations to the global variable environment
	 * @param obs
	 */
	public void addObservations(HashMap<String,String>obs) {
		this._variables.putAll(obs);
	}
	public static void main(String args[]) {
		String test1 = "if(min(3,4,5)==max(min(3,8),10)){\n"
					 + "	return 0.5+1;\n"
					 + "}"
					 + "else{"
					 + "return 0;"
					 + "}";
		Parser parser = new Parser();
		JSONObject program = parser.parse(test1);
		System.out.println(program.toString(4));
		
		Evaluator evaluator = new Evaluator(program);
		evaluator._variables.put("ma25[7]", "4.2");
		evaluator._variables.put("y", "true");
		System.out.println("Resulting move1: "+evaluator.getNext());
		System.out.println("Resulting move2: "+evaluator.getNext());
		evaluator._variables.put("y", "false");
		
		System.out.println("Resulting move3: "+evaluator.getNext());
		System.out.println("Resulting move4: "+evaluator.getNext());
		System.out.println("Resulting move5: "+evaluator.getNext());
		System.out.println("Resulting move6: "+evaluator.getNext());
		System.out.println("Resulting move7: "+evaluator.getNext());
	}
}
