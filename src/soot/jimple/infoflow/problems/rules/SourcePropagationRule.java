package soot.jimple.infoflow.problems.rules;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import soot.Value;
import soot.jimple.DefinitionStmt;
import soot.jimple.Stmt;
import soot.jimple.infoflow.InfoflowManager;
import soot.jimple.infoflow.aliasing.Aliasing;
import soot.jimple.infoflow.data.Abstraction;
import soot.jimple.infoflow.data.AccessPath;
import soot.jimple.infoflow.source.SourceInfo;
import soot.jimple.infoflow.util.ByReferenceBoolean;

/**
 * Rule to introduce unconditional taints at sources
 * 
 * @author Steven Arzt
 *
 */
public class SourcePropagationRule extends AbstractTaintPropagationRule {

	public SourcePropagationRule(InfoflowManager manager, Aliasing aliasing,
			Abstraction zeroValue) {
		super(manager, aliasing, zeroValue);
	}

	private Collection<Abstraction> propagate(Abstraction d1,
			Abstraction source, Stmt stmt, ByReferenceBoolean killSource) {
		if (source == getZeroValue()) {
			// We never propagate zero facts onwards
			killSource.value = true;
			
			// Is this a source?
			final SourceInfo sourceInfo = getManager().getSourceSinkManager() != null
					? getManager().getSourceSinkManager().getSourceInfo(stmt, getManager().getICFG()) : null;
			if (sourceInfo != null && !sourceInfo.getAccessPaths().isEmpty()) {
				Set<Abstraction> res = new HashSet<>();
				Value leftOp = stmt instanceof DefinitionStmt ? ((DefinitionStmt) stmt).getLeftOp() : null;
				for (AccessPath ap : sourceInfo.getAccessPaths()) {
					Abstraction abs = new Abstraction(ap,
							stmt,
							sourceInfo.getUserData(),
							false,
							false);
					res.add(abs);
					
					// Compute the aliases
					if (leftOp != null)
						if (getAliasing().canHaveAliases(stmt, leftOp, abs))
							getAliasing().computeAliases(d1, stmt, leftOp,
									res, getManager().getICFG().getMethodOf(stmt), abs);
					
					// Set the corresponding call site
					if (stmt.containsInvokeExpr())
						abs.setCorrespondingCallSite(stmt);
				}
				return res;
			}
		}
		return null;
	}

	@Override
	public Collection<Abstraction> propagateNormalFlow(Abstraction d1,
			Abstraction source, Stmt stmt, ByReferenceBoolean killSource,
			ByReferenceBoolean killAll) {
		return propagate(d1, source, stmt, killSource);
	}

	@Override
	public Collection<Abstraction> propagateCallToReturnFlow(Abstraction d1,
			Abstraction source, Stmt stmt, ByReferenceBoolean killSource) {
		return propagate(d1, source, stmt, killSource);
	}

	@Override
	public Collection<Abstraction> propagateReturnFlow(
			Collection<Abstraction> callerD1s, Abstraction source, Stmt stmt) {
		return null;
	}

	@Override
	public Collection<Abstraction> propagateCallFlow(Abstraction d1,
			Abstraction source, Stmt stmt, ByReferenceBoolean killAll) {
		return null;
	}

}