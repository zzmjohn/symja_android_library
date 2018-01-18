package org.matheclipse.core.reflection.system.rules;

import static org.matheclipse.core.expression.F.*;
import org.matheclipse.core.interfaces.IAST;

/**
 * <p>Generated by <code>org.matheclipse.core.preprocessor.RulePreprocessor</code>.</p>
 * <p>See GIT repository at: <a href="https://github.com/axkr/symja_android_library">github.com/axkr/symja_android_library under the tools directory</a>.</p>
 */
public interface ProductLogRules {
  /**
   * <ul>
   * <li>index 0 - number of equal rules in <code>RULES</code></li>
	 * </ul>
	 */
  final public static int[] SIZES = { 11, 1 };

  final public static IAST RULES = List(
    IInit(ProductLog, SIZES),
    // ProductLog(0)=0
    ISet(ProductLog(C0),
      C0),
    // ProductLog((-1)*1/2*Pi)=I*Pi/2
    ISet(ProductLog(Times(CN1D2,Pi)),
      Times(CC(0L,1L,1L,2L),Pi)),
    // ProductLog(-1/E)=-1
    ISet(ProductLog(Negate(Power(E,-1))),
      CN1),
    // ProductLog(E)=1
    ISet(ProductLog(E),
      C1),
    // ProductLog(-1,-Pi/2)=-1/2*Pi*I
    ISet(ProductLog(CN1,Times(CN1D2,Pi)),
      Times(CC(0L,1L,-1L,2L),Pi)),
    // ProductLog(-1,-1/E)=-1
    ISet(ProductLog(CN1,Negate(Power(E,-1))),
      CN1),
    // ProductLog(Infinity)=Infinity
    ISet(ProductLog(oo),
      oo),
    // ProductLog(-Infinity)=-Infinity
    ISet(ProductLog(Noo),
      Noo),
    // ProductLog(I*Infinity)=Infinity
    ISet(ProductLog(DirectedInfinity(CI)),
      oo),
    // ProductLog(-I*Infinity)=Infinity
    ISet(ProductLog(DirectedInfinity(CNI)),
      oo),
    // ProductLog(ComplexInfinity)=Infinity
    ISet(ProductLog(CComplexInfinity),
      oo),
    // ProductLog(x_)*E^ProductLog(x_):=x
    ISetDelayed(Times(Power(E,ProductLog(x_)),ProductLog(x_)),
      x),
    // ProductLog(0,x_):=ProductLog(x)
    ISetDelayed(ProductLog(C0,x_),
      ProductLog(x)),
    // ProductLog(n_NumberQ,0):=-Infinity/;n!=0
    ISetDelayed(ProductLog($p(n,NumberQ),C0),
      Condition(Negate(oo),Unequal(n,C0)))
  );
}
