package org.matheclipse.core.reflection.system.rules;

import static org.matheclipse.core.expression.F.*;
import org.matheclipse.core.interfaces.IAST;

/**
 * <p>Generated by <code>org.matheclipse.core.preprocessor.RulePreprocessor</code>.</p>
 * <p>See GIT repository at: <a href="https://github.com/axkr/symja_android_library">github.com/axkr/symja_android_library under the tools directory</a>.</p>
 */
public interface PowerRules {
  /**
   * <ul>
   * <li>index 0 - number of equal rules in <code>RULES</code></li>
	 * </ul>
	 */
  final public static int[] SIZES = { 4, 10 };

  final public static IAST RULES = List(
    IInit(Power, SIZES),
    // E^(3/2*I*Pi)=-I
    ISet(Power(E,Times(CC(0L,1L,3L,2L),Pi)),
      CNI),
    // E^(Pi*c_Complex):=Module({r=Re(c),j=Im(c)},If(EvenQ(j),1,-1)/;r==0&&IntegerQ(j))
    ISetDelayed(Power(E,Times(Pi,$p(c,Complex))),
      Module(List(Set(r,Re(c)),Set(j,Im(c))),Condition(If(EvenQ(j),C1,CN1),And(Equal(r,C0),IntegerQ(j))))),
    // E^(x_+Pi*c_Complex):=Module({r=Re(c),j=Im(c)},If(EvenQ(j),E^x,-E^x)/;r==0&&IntegerQ(j))
    ISetDelayed(Power(E,Plus(Times(Pi,$p(c,Complex)),x_)),
      Module(List(Set(r,Re(c)),Set(j,Im(c))),Condition(If(EvenQ(j),Power(E,x),Negate(Power(E,x))),And(Equal(r,C0),IntegerQ(j))))),
    // E^(I*Infinity)=Indeterminate
    ISet(Power(E,DirectedInfinity(CI)),
      Indeterminate),
    // E^(-I*Infinity)=Indeterminate
    ISet(Power(E,DirectedInfinity(CNI)),
      Indeterminate),
    // E^ComplexInfinity=Indeterminate
    ISet(Power(E,CComplexInfinity),
      Indeterminate),
    // E^Log(x_):=x
    ISetDelayed(Power(E,Log(x_)),
      x),
    // E^(a_*Log(x_)/;FreeQ(a,x)):=x^a
    ISetDelayed(Power(E,Condition(Times(a_,Log(x_)),FreeQ(a,x))),
      Power(x,a)),
    // Tan(x_)^m_?(IntegerQ(#1)&&#1<0&):=Cot(x)^(-m)
    ISetDelayed(Power(Tan(x_),PatternTest(m_,Function(And(IntegerQ(Slot1),Less(Slot1,C0))))),
      Power(Cot(x),Negate(m))),
    // Cot(x_)^m_?(IntegerQ(#1)&&#1<0&):=Tan(x)^(-m)
    ISetDelayed(Power(Cot(x_),PatternTest(m_,Function(And(IntegerQ(Slot1),Less(Slot1,C0))))),
      Power(Tan(x),Negate(m))),
    // Sec(x_)^m_?(IntegerQ(#1)&&#1<0&):=Cos(x)^(-m)
    ISetDelayed(Power(Sec(x_),PatternTest(m_,Function(And(IntegerQ(Slot1),Less(Slot1,C0))))),
      Power(Cos(x),Negate(m))),
    // Cos(x_)^m_?(IntegerQ(#1)&&#1<0&):=Sec(x)^(-m)
    ISetDelayed(Power(Cos(x_),PatternTest(m_,Function(And(IntegerQ(Slot1),Less(Slot1,C0))))),
      Power(Sec(x),Negate(m))),
    // Csc(x_)^m_?(IntegerQ(#1)&&#1<0&):=Sin(x)^(-m)
    ISetDelayed(Power(Csc(x_),PatternTest(m_,Function(And(IntegerQ(Slot1),Less(Slot1,C0))))),
      Power(Sin(x),Negate(m))),
    // Sin(x_)^m_?(IntegerQ(#1)&&#1<0&):=Csc(x)^(-m)
    ISetDelayed(Power(Sin(x_),PatternTest(m_,Function(And(IntegerQ(Slot1),Less(Slot1,C0))))),
      Power(Csc(x),Negate(m)))
  );
}
