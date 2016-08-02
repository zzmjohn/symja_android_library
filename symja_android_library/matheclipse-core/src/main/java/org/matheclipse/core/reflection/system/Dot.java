package org.matheclipse.core.reflection.system;

import org.apache.commons.math4.linear.FieldMatrix;
import org.apache.commons.math4.linear.FieldVector;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.convert.Convert;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractNonOrderlessArgMultiple;
import org.matheclipse.core.expression.ASTRealMatrix;
import org.matheclipse.core.expression.ASTRealVector;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

public class Dot extends AbstractNonOrderlessArgMultiple {

	public Dot() {
		// default ctor
	}

	@Override
	public IExpr e2ObjArg(final IExpr o0, final IExpr o1) {
		if (o0.isRealMatrix()) {
			if (o1.isRealMatrix()) {
				RealMatrix m0 = o0.toRealMatrix();
				RealMatrix m1 = o1.toRealMatrix();
				return new ASTRealMatrix(m0.multiply(m1), false);
			} else if (o1.isRealVector()) {
				RealMatrix m0 = o0.toRealMatrix();
				RealVector m1 = o1.toRealVector();
				return new ASTRealVector(m0.operate(m1), false);
			}
		} else if (o0.isRealVector()) {
			if (o1.isRealMatrix()) {
				RealVector m0 = o0.toRealVector();
				RealMatrix m1 = o1.toRealMatrix();
				return new ASTRealVector(m1.preMultiply(m0), false);
			} else if (o1.isRealVector()) {
				RealVector m0 = o0.toRealVector();
				RealVector m1 = o1.toRealVector();
				return F.num(m0.dotProduct(m1));
			}
		}
		FieldMatrix<IExpr> matrix0;
		FieldMatrix<IExpr> matrix1;
		FieldVector<IExpr> vector0;
		FieldVector<IExpr> vector1;
		try {
			IAST list;

			if (o0.isMatrix() != null) {
				list = (IAST) o0;
				matrix0 = Convert.list2Matrix(list);
				if (o1.isMatrix() != null) {
					list = (IAST) o1;
					matrix1 = Convert.list2Matrix(list);
					return Convert.matrix2List(matrix0.multiply(matrix1));
				} else if (o1.isVector() != (-1)) {
					list = (IAST) o1;
					vector1 = Convert.list2Vector(list);
					IAST res = Convert.vector2List(matrix0.operate(vector1));
					if (res == null) {
						return F.NIL;
					}
					return res;
				}
			} else if (o0.isVector() != (-1)) {
				list = (IAST) o0;
				vector0 = Convert.list2Vector(list);
				if (o1.isMatrix() != null) {
					list = (IAST) o1;
					matrix1 = Convert.list2Matrix(list);
					return Convert.vector2List(matrix1.preMultiply(vector0));
				} else if (o1.isVector() != (-1)) {
					list = (IAST) o1;
					vector1 = Convert.list2Vector(list);
					if (vector1 != null) {
						return vector0.dotProduct(vector1);
					}
				}
			}

		} catch (final ClassCastException e) {
			if (Config.SHOW_STACKTRACE) {
				e.printStackTrace();
			}
		} catch (final IndexOutOfBoundsException e) {
			if (Config.SHOW_STACKTRACE) {
				e.printStackTrace();
			}
		}
		return F.NIL;
	}

	@Override
	public IExpr numericEval(final IAST ast, EvalEngine engine) {
		return evaluate(ast, engine);
	}

	@Override
	public void setUp(final ISymbol symbol) {
		symbol.setAttributes(ISymbol.FLAT | ISymbol.ONEIDENTITY);
	}

}