package org.matheclipse.core.convert;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apfloat.Apfloat;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.builtin.function.Blank;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.builtin.function.Complex;
import org.matheclipse.core.builtin.function.Pattern;
import org.matheclipse.core.builtin.function.Rational;
import org.matheclipse.parser.client.ast.ASTNode;
import org.matheclipse.parser.client.ast.FloatNode;
import org.matheclipse.parser.client.ast.FractionNode;
import org.matheclipse.parser.client.ast.FunctionNode;
import org.matheclipse.parser.client.ast.IntegerNode;
import org.matheclipse.parser.client.ast.Pattern2Node;
import org.matheclipse.parser.client.ast.Pattern3Node;
import org.matheclipse.parser.client.ast.PatternNode;
import org.matheclipse.parser.client.ast.StringNode;
import org.matheclipse.parser.client.ast.SymbolNode;

/**
 * Converts a parsed <code>org.matheclipse.parser.client.ast.ASTNode</code> expression into an IExpr expression
 * 
 */
public class AST2Expr {
	public final static String[] UPPERCASE_SYMBOL_STRINGS = { "D", "E", "I", "N" };

	public final static String[] SYMBOL_STRINGS = { "Algebraics", "Booleans", "ComplexInfinity", "Catalan", "Complexes", "Degree",
			"EulerGamma", "False", "Flat", "Glaisher", "GoldenRatio", "HoldAll", "HoldFirst", "HoldForm", "HoldRest",
			"Indeterminate", "Infinity", "Integer", "Integers", "Khinchin", "Listable", "Modulus", "Null", "NumericFunction",
			"OneIdentity", "Orderless", "Pi", "Primes", "Rationals", "Real", "Reals", "Slot", "SlotSequence", "String", "Symbol",
			"True" };

	public final static String[] FUNCTION_STRINGS = { "Abs", "AddTo", "And", "Apart", "AppellF1", "Append", "AppendTo", "Apply",
			"ArcCos", "ArcCosh", "ArcCot", "ArcCoth", "ArcCsc", "ArcCsch", "ArcSec", "ArcSech", "ArcSin", "ArcSinh", "ArcTan",
			"ArcTanh", "Arg", "Array", "ArrayDepth", "ArrayQ", "AtomQ", "Attributes", "BernoulliB", "Binomial", "Blank", "Block",
			"Boole", "BooleanConvert", "BooleanMinimize", "Break", "Cancel", "CartesianProduct", "Cases", "CatalanNumber", "Catch",
			"Ceiling", "CharacteristicPolynomial", "ChebyshevT", "ChessboardDistance", "Chop", "Clear", "ClearAll", "Coefficient",
			"CoefficientList", "Collect", "Complement", "Complex", "ComplexExpand", "ComplexInfinity", "ComposeList",
			"CompoundExpression", "Condition", "Conjugate", "ConjugateTranspose", "ConstantArray", "Continue", "ContinuedFraction",
			"CoprimeQ", "Cos", "Cosh", "CosIntegral", "CoshIntegral", "Cot", "Coth", "Count", "Cross", "Csc", "Csch", "Curl",
			"Decrement", "Default", "Defer", "Definition", "Delete", "DeleteCases", "DeleteDuplicates", "Denominator", "Depth",
			"Derivative", "Det", "DiagonalMatrix", "DigitQ", "Dimensions", "DirectedInfinity", "Discriminant", "Distribute",
			"Divergence", "DivideBy", "Divisible", "Divisors", "Do", "Dot", "Drop", "Eigenvalues", "Eigenvectors", "Element",
			"Eliminate", "EllipticE", "EllipticF", "EllipticPi", "Equal", "Equivalent", "Erf", "Erfc", "Erfi", "EuclidianDistance",
			"EulerE", "EulerPhi", "EvenQ", "Exp", "Expand", "ExpandAll", "ExpIntegralE", "ExpIntegralEi", "Exponent",
			"ExtendedGCD", "Extract", "Factor", "Factorial", "Factorial2", "FactorInteger", "FactorSquareFree",
			"FactorSquareFreeList", "FactorTerms", "Flatten", "Fibonacci", "FindRoot", "First", "Fit", "FixedPoint", "Floor",
			"Fold", "FoldList", "For", "FractionalPart", "FreeQ", "FresnelC", "FresnelS", "FrobeniusSolve", "FromCharacterCode",
			"FromContinuedFraction", "FullForm", "FullSimplify", "Function", "Gamma", "GCD", "GeometricMean", "Graphics",
			"Graphics3D", "Graphics3D", "Greater", "GreaterEqual", "GroebnerBasis", "HarmonicNumber", "Head", "HermiteH",
			"HilbertMatrix", "Hold", "Horner", "HornerForm", "HurwitzZeta", "HypergeometricPFQ", "Hypergeometric2F1", "Identity",
			"IdentityMatrix", "If", "Im", "Implies", "Increment", "Inner", "Insert", "IntegerPart", "IntegerPartitions",
			"IntegerQ", "Integrate", "InterpolatingFunction", "InterpolatingPolynomial", "Intersection", "Inverse", "InverseErf",
			"InverseFunction", "JacobiMatrix", "JacobiSymbol", "JavaForm", "Join", "KOrderlessPartitions", "KPartitions", "Last",
			"LCM", "LeafCount", "LaguerreL", "LegendreP", "Length", "Less", "LessEqual", "LetterQ", "Level", "Limit", "Line",
			"LinearProgramming", "LinearSolve", "List", "ListQ", "Log", "Log2", "Log10", "LogGamma", "LogicalExpand",
			"LogIntegral", "LowerCaseQ", "LUDecomposition", "ManhattanDistance", "Map", "MapAll", "MapThread", "MatchQ",
			"MathMLForm", "MatrixForm", "MatrixPower", "MatrixQ", "MatrixRank", "Max", "Mean", "Median", "MemberQ", "Min", "Mod",
			"Module", "MoebiusMu", "MonomialList", "Most", "Multinomial", "Nand", "Negative", "Nest", "NestList", "NestWhile",
			"NestWhileList", "NextPrime", "NFourierTransform", "NIntegrate", "NonCommutativeMultiply", "NonNegative", "Nor",
			"Norm", "Not", "NRoots", "NSolve", "NullSpace", "NumberQ", "Numerator", "NumericQ", "OddQ", "Options", "Or", "Order",
			"OrderedQ", "Out", "Outer", "Package", "PadLeft", "PadRight", "ParametricPlot", "Part", "Partition", "Pattern",
			"Permutations", "Piecewise", "Plot", "Plot3D", "Plus", "Pochhammer", "PolyGamma", "PolyLog", "PolynomialExtendedGCD",
			"PolynomialGCD", "PolynomialLCM", "PolynomialQ", "PolynomialQuotient", "PolynomialQuotientRemainder",
			"PolynomialRemainder", "Position", "Positive", "PossibleZeroQ", "Power", "PowerExpand", "PowerMod", "PreDecrement",
			"PreIncrement", "Prepend", "PrependTo", "Prime", "PrimeQ", "PrimitiveRoots", "Print", "Product", "ProductLog", "Quiet",
			"Quotient", "RandomInteger", "RandomReal", "RandomSample", "Range", "Rational", "Rationalize", "Re", "Reap",
			"ReplaceAll", "ReplacePart", "ReplaceRepeated", "Rest", "Resultant", "Return", "Reverse", "Riffle", "RootIntervals",
			"RootOf", "Roots", "Surd", "RotateLeft", "RotateRight", "Round", "RowReduce", "Rule", "RuleDelayed", "SameQ", "Scan",
			"Sec", "Sech", "Select", "Sequence", "Set", "SetAttributes", "SetDelayed", "Show", "Sign", "SignCmp", "Simplify",
			"Sin", "Sinc", "SingularValueDecomposition", "Sinh", "SinIntegral", "SinhIntegral", "Solve", "Sort", "Sow", "Sqrt",
			"SquaredEuclidianDistance", "SquareFreeQ", "StirlingS2", "StringDrop", "StringJoin", "StringLength", "StringTake",
			"Subfactorial", "Subscript", "Subsuperscript", "Subsets", "SubtractFrom", "Sum", "Superscript", "Switch",
			"SyntaxLength", "SyntaxQ", "Table", "Take", "Tan", "Tanh", "Taylor", "TeXForm", "Thread", "Through", "Throw",
			"TimeConstrained", "Times", "TimesBy", "Timing", "ToCharacterCode", "Together", "ToString", "Total", "ToUnicode", "Tr",
			"Trace", "Transpose", "TrigExpand", "TrigReduce", "TrigToExp", "TrueQ", "Tuples", "Unequal", "Unevaluated", "Union",
			"Unique", "UnitStep", "UnitVector", "UnsameQ", "UpperCaseQ", "UpSet", "UpSetDelayed", "ValueQ", "VandermondeMatrix",
			"Variables", "VectorQ", "Which", "While", "Xor", "Zeta" };

	public static Map<String, Integer> RUBI_STATISTICS_MAP;

	public static final Map<String, String> PREDEFINED_SYMBOLS_MAP = new HashMap<String, String>(997);

	private final static String[] ALIASES_STRINGS = { "ACos", "ASin", "ATan", "ACosh", "ASinh", "ATanh", "ComplexInfinity", "Diff",
			"EvalF", "Infinity", "Int", "Ln", "Trunc", "NthRoot", "Root" };

	private final static IExpr[] ALIASES_SYMBOLS = { F.ArcCos, F.ArcSin, F.ArcTan, F.ArcCosh, F.ArcSinh, F.ArcTanh,
			F.CComplexInfinity, F.D, F.N, F.CInfinity, F.Integrate, F.Log, F.IntegerPart, F.Surd, F.Surd };

	/**
	 * Aliases which are mapped to the standard function symbols.
	 */
	public static final Map<String, IExpr> PREDEFINED_ALIASES_MAP = new HashMap<String, IExpr>(97);

	// public static final String LIST_STRING = Config.PARSER_USE_LOWERCASE_SYMBOLS ? "list" : "List";
	// public static final String POWER_STRING = Config.PARSER_USE_LOWERCASE_SYMBOLS ? "power" : "Power";
	// public static final String PLUS_STRING = Config.PARSER_USE_LOWERCASE_SYMBOLS ? "plus" : "Plus";
	public static final String TIMES_STRING = Config.PARSER_USE_LOWERCASE_SYMBOLS ? "times" : "Times";
	// public static final String FALSE_STRING = Config.PARSER_USE_LOWERCASE_SYMBOLS ? "false" : "False";
	public static final String TRUE_STRING = Config.PARSER_USE_LOWERCASE_SYMBOLS ? "true" : "True";

	// public static final String PART_STRING =
	// Config.PARSER_USE_LOWERCASE_SYMBOLS ? "part" : "Part";
	// public static final String SLOT_STRING =
	// Config.PARSER_USE_LOWERCASE_SYMBOLS ? "slot" : "Slot";
	// public static final String HOLD_STRING =
	// Config.PARSER_USE_LOWERCASE_SYMBOLS ? "hold" : "Hold";
	// public static final String DIRECTEDINFINITY_STRING =
	// Config.PARSER_USE_LOWERCASE_SYMBOLS ? "directedinfinity"
	// : "DirectedInfinity";
	static {
		for (String str : UPPERCASE_SYMBOL_STRINGS) {
			// these constants must be written in upper case characters
			PREDEFINED_SYMBOLS_MAP.put(str, str);
		}
		for (String str : SYMBOL_STRINGS) {
			PREDEFINED_SYMBOLS_MAP.put(str.toLowerCase(), str);
		}
		for (String str : FUNCTION_STRINGS) {
			PREDEFINED_SYMBOLS_MAP.put(str.toLowerCase(), str);
		}
		if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
			for (int i = 0; i < ALIASES_STRINGS.length; i++) {
				PREDEFINED_ALIASES_MAP.put(ALIASES_STRINGS[i].toLowerCase(), ALIASES_SYMBOLS[i]);
			}
		}
		if (Config.RUBI_CONVERT_SYMBOLS) {
			for (int i = 0; i < ALIASES_STRINGS.length; i++) {
				PREDEFINED_SYMBOLS_MAP.put(ALIASES_STRINGS[i].toLowerCase(), ALIASES_STRINGS[i]);
			}
		}
		if (Config.RUBI_CONVERT_SYMBOLS) {
			RUBI_STATISTICS_MAP = new TreeMap<String, Integer>();
		}

	}
	/**
	 * Typical instance of an ASTNode to IExpr converter
	 */
	public final static AST2Expr CONST = new AST2Expr();

	public final static AST2Expr CONST_LC = new AST2Expr(true);

	private int fPrecision;

	private boolean fLowercaseEnabled;

	/**
	 * 
	 * @param sType
	 * @param tType
	 * @deprecated
	 */
	public AST2Expr(final Class<ASTNode> sType, final Class<IExpr> tType) {
		this(false);
	}

	public AST2Expr() {
		this(false);
	}

	public AST2Expr(boolean lowercaseEnabled) {
		super();
		fLowercaseEnabled = lowercaseEnabled;
	}

	/**
	 * Converts a parsed FunctionNode expression into an IAST expression
	 */
	public IAST convert(IAST ast, FunctionNode functionNode) throws ConversionException {
		ast.set(0, convert(functionNode.get(0)));
		for (int i = 1; i < functionNode.size(); i++) {
			ast.add(convert(functionNode.get(i)));
		}
		return ast;
	}

	public IExpr convert(ASTNode node, EvalEngine engine) throws ConversionException {
		fPrecision = 15;
		if (engine != null) {
			fPrecision = engine.getNumericPrecision();
		}
		return convert(node);
	}

	/**
	 * Converts a parsed ASTNode expression into an IExpr expression
	 */
	public IExpr convert(ASTNode node) throws ConversionException {
		if (node == null) {
			return null;
		}
		// if (node instanceof Pattern2Node) {
		// throw new
		// UnsupportedOperationException("'__' pattern-matching expression not implemented");
		// }
		if (node instanceof Pattern3Node) {
			throw new UnsupportedOperationException("'___' pattern-matching expression not implemented");
		}
		if (node instanceof FunctionNode) {
			final FunctionNode functionNode = (FunctionNode) node;
			final IAST ast = F.ast(convert(functionNode.get(0)), functionNode.size(), false);
			for (int i = 1; i < functionNode.size(); i++) {
				ast.add(convert(functionNode.get(i)));
			}
			IExpr head = ast.head();
			if (ast.isAST(F.N, 3)) {
				try {
					int precision = Validate.checkIntType(ast.arg2());
					if (EvalEngine.isApfloat(precision)) {
						fPrecision = precision;
						ast.set(1, convert(functionNode.get(1)));
					}
					return ast;
				} catch (WrongArgumentType wat) {

				}
			} else if (ast.isAST(F.Sqrt, 2)) {
				// rewrite from input: Sqrt(x) => Power(x, 1/2)
				return F.Power(ast.arg1(), F.C1D2);
			} else if (ast.isAST(F.Exp, 2)) {
				// rewrite from input: Exp(x) => E^x
				return F.Power(F.E, ast.arg1());
			} else if (ast.isPower() && ast.arg1().isPower() && ast.arg2().isMinusOne()) {
				IAST arg1 = (IAST) ast.arg1();
				if (arg1.arg2().isNumber()) {
					// Division operator
					// rewrite from input: Power(Power(x, <number>),-1) => Power(x, - <number>)
					return F.Power(arg1.arg1(), ((INumber) arg1.arg2()).negate());
				}
			} else if (ast.isASTSizeGE(F.GreaterEqual, 3)) {
				ISymbol compareHead = F.Greater;
				return rewriteLessGreaterAST(ast, compareHead);
			} else if (ast.isASTSizeGE(F.Greater, 3)) {
				ISymbol compareHead = F.GreaterEqual;
				return rewriteLessGreaterAST(ast, compareHead);
			} else if (ast.isASTSizeGE(F.LessEqual, 3)) {
				ISymbol compareHead = F.Less;
				return rewriteLessGreaterAST(ast, compareHead);
			} else if (ast.isASTSizeGE(F.Less, 3)) {
				ISymbol compareHead = F.LessEqual;
				return rewriteLessGreaterAST(ast, compareHead);
			} else if (head.equals(F.PatternHead)) {
				final IExpr expr = Pattern.CONST.evaluate(ast);
				if (expr != null) {
					return expr;
				}
			} else if (head.equals(F.BlankHead)) {
				final IExpr expr = Blank.CONST.evaluate(ast);
				if (expr != null) {
					return expr;
				}
			} else if (head.equals(F.Complex)) {
				final IExpr expr = Complex.CONST.evaluate(ast);
				if (expr != null) {
					return expr;
				}
			} else if (head.equals(F.Rational)) {
				final IExpr expr = Rational.CONST.evaluate(ast);
				if (expr != null) {
					return expr;
				}
			}
			return ast;
		}
		if (node instanceof SymbolNode) {
			String nodeStr = node.getString();
			if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
				if (nodeStr.length() == 1) {
					if (nodeStr.equals("I")) {
						// special - convert on input
						return F.CI;
					}
					return F.$s(nodeStr);
				}
				nodeStr = nodeStr.toLowerCase();
				if (nodeStr.equals("infinity")) {
					// special - convert on input
					return F.CInfinity;
				} else if (nodeStr.equals("complexinfinity")) {
					// special - convert on input
					return F.CComplexInfinity;
				}
				IExpr temp = PREDEFINED_ALIASES_MAP.get(nodeStr);
				if (temp != null) {
					return temp;
				}
				return F.$s(nodeStr);
			} else {
				if (fLowercaseEnabled) {
					nodeStr = nodeStr.toLowerCase();
					String temp = PREDEFINED_SYMBOLS_MAP.get(nodeStr);
					if (temp != null) {
						nodeStr = temp;
					}
				}

				if (Config.RUBI_CONVERT_SYMBOLS) {
					Integer num = RUBI_STATISTICS_MAP.get(nodeStr);
					if (num == null) {
						RUBI_STATISTICS_MAP.put(nodeStr, 1);
					} else {
						RUBI_STATISTICS_MAP.put(nodeStr, num + 1);
					}
				}

				if (nodeStr.equals("I")) {
					// special - convert on input
					return F.CI;
				} else if (nodeStr.equals("Infinity")) {
					// special - convert on input
					return F.CInfinity;
				}
				return F.$s(nodeStr);
			}
		}
		// because of inheritance check Pattern2Node before PatternNode
		if (node instanceof Pattern2Node) {
			final Pattern2Node p2n = (Pattern2Node) node;
			return F.$ps((ISymbol) convert(p2n.getSymbol()), convert(p2n.getConstraint()), p2n.isDefault());
		}
		if (node instanceof PatternNode) {
			final PatternNode pn = (PatternNode) node;
			return F.$p((ISymbol) convert(pn.getSymbol()), convert(pn.getConstraint()), pn.isDefault());
		}

		if (node instanceof IntegerNode) {
			final IntegerNode integerNode = (IntegerNode) node;
			final String iStr = integerNode.getString();
			if (iStr != null) {
				return F.integer(iStr, integerNode.getNumberFormat());
			}
			return F.integer(integerNode.getIntValue());
		}
		if (node instanceof FractionNode) {
			FractionNode fr = (FractionNode) node;
			IInteger numerator = (IInteger) convert(fr.getNumerator());
			IInteger denominator = (IInteger) convert(fr.getDenominator());
			if (denominator.isZero()) {
				return F.Rational(fr.isSign() ? numerator.negate() : numerator, denominator);
			}
			return F.fraction(numerator, fr.isSign() ? (IInteger) denominator.negate() : denominator);
		}
		if (node instanceof StringNode) {
			return F.stringx(node.getString());
		}
		if (node instanceof FloatNode) {
			if (EvalEngine.isApfloat(fPrecision)) {
				return F.num(new Apfloat(node.getString(), fPrecision));
			}
			return F.num(Double.parseDouble(node.getString()));
		}

		return F.$s(node.toString());
	}

	/**
	 * Convert less or greter relations on input. Example: convert expressions like <code>a<b<=c</code> to
	 * <code>Less[a,b]&&LessEqual[b,c]</code>.
	 * 
	 * @param ast
	 * @param compareHead
	 * @return
	 */
	private IExpr rewriteLessGreaterAST(final IAST ast, ISymbol compareHead) {
		IExpr temp;
		boolean evaled = false;
		IAST andAST = F.ast(F.And);
		for (int i = 1; i < ast.size(); i++) {
			temp = ast.get(i);
			if (temp.isASTSizeGE(compareHead, 3)) {
				IAST lt = (IAST) temp;
				andAST.add(lt);
				ast.set(i, lt.get(lt.size() - 1));
				evaled = true;
			}
		}
		if (evaled) {
			andAST.add(ast);
			return andAST;
		} else {
			return ast;
		}
	}
}
