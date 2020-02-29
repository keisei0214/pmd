/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.rule.metrics;

import net.sourceforge.pmd.lang.java.ast.ASTAnyTypeDeclaration;
import net.sourceforge.pmd.lang.java.ast.MethodLikeNode;
import net.sourceforge.pmd.lang.java.ast.internal.PrettyPrintingUtil;
import net.sourceforge.pmd.lang.java.metrics.JavaMetrics;
import net.sourceforge.pmd.lang.java.metrics.api.JavaOperationMetricKey;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaMetricsRule;
import net.sourceforge.pmd.lang.java.rule.metrics.util.NameUtil;
import net.sourceforge.pmd.lang.metrics.MetricOptions;
import net.sourceforge.pmd.lang.metrics.ResultOption;

public class CycloMetricRule extends AbstractJavaMetricsRule {
    @Override
    public Object visit(ASTAnyTypeDeclaration node, Object data) {

        super.visit(node, data);

        int total = (int) JavaMetrics.get(
                JavaOperationMetricKey.CYCLO,
                node,
                MetricOptions.emptyOptions(),
                ResultOption.SUM);

        addViolation(
                data,
                node,
                new Object[] { PrettyPrintingUtil.kindName(node), node.getSimpleName(), total });
        return data;
    }

    @Override
    public final Object visit(MethodLikeNode node, Object data) {

        int cyclo = (int) JavaMetrics.get(
                JavaOperationMetricKey.CYCLO,
                node,
                MetricOptions.emptyOptions()
        );

        addViolation(data, node, new Object[] { NameUtil.getKindName(node), NameUtil.getOpname(node), cyclo });
        return data;
    }
}
