/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.rule.metrics;

import net.sourceforge.pmd.lang.java.ast.ASTAnyTypeDeclaration;
import net.sourceforge.pmd.lang.java.ast.MethodLikeNode;
import net.sourceforge.pmd.lang.java.ast.internal.PrettyPrintingUtil;
import net.sourceforge.pmd.lang.java.metrics.JavaMetrics;
import net.sourceforge.pmd.lang.java.metrics.api.JavaClassMetricKey;
import net.sourceforge.pmd.lang.java.metrics.api.JavaOperationMetricKey;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaMetricsRule;
import net.sourceforge.pmd.lang.java.rule.metrics.util.NameUtil;
import net.sourceforge.pmd.lang.metrics.MetricOptions;

public class NcssMetricRule extends AbstractJavaMetricsRule {
    @Override
    public Object visit(ASTAnyTypeDeclaration node, Object data) {
        super.visit(node, data);

        if (JavaClassMetricKey.NCSS.supports(node)) {
            int ncss = (int) JavaMetrics.get(JavaClassMetricKey.NCSS, node, MetricOptions.emptyOptions());
            addViolation(
                    data,
                    node,
                    new Object[] { PrettyPrintingUtil.kindName(node), node.getSimpleName(), ncss }
            );
        }

        return data;
    }

    @Override
    public final Object visit(MethodLikeNode node, Object data) {

        int ncss = (int) JavaMetrics.get(
                JavaOperationMetricKey.NCSS,
                node,
                MetricOptions.emptyOptions()
        );

        addViolation(data, node, new Object[] { NameUtil.getKindName(node), NameUtil.getOpname(node), ncss });
        return data;
    }
}
