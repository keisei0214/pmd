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

public class AtfdMetricRule extends AbstractJavaMetricsRule {
    @Override
    public Object visit(ASTAnyTypeDeclaration node, Object data) {
        super.visit(node, data);

        if (JavaClassMetricKey.ATFD.supports(node)) {
            int atfd = (int) JavaMetrics.get(JavaClassMetricKey.ATFD, node, MetricOptions.emptyOptions());
            addViolation(
                    data,
                    node,
                    new Object[] { PrettyPrintingUtil.kindName(node), node.getSimpleName(), atfd }
            );
        }

        return data;
    }

    @Override
    public final Object visit(MethodLikeNode node, Object data) {

        int atfd = (int) JavaMetrics.get(
                JavaOperationMetricKey.ATFD,
                node,
                MetricOptions.emptyOptions()
        );

        addViolation(data, node, new Object[] { NameUtil.getKindName(node), NameUtil.getOpname(node), atfd });
        return data;
    }
}
