/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.rule.metrics;

import net.sourceforge.pmd.lang.java.ast.ASTAnyTypeDeclaration;
import net.sourceforge.pmd.lang.java.ast.internal.PrettyPrintingUtil;
import net.sourceforge.pmd.lang.java.metrics.JavaMetrics;
import net.sourceforge.pmd.lang.java.metrics.api.JavaClassMetricKey;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaMetricsRule;
import net.sourceforge.pmd.lang.metrics.MetricOptions;

public class WocMetricRule extends AbstractJavaMetricsRule {
    @Override
    public Object visit(ASTAnyTypeDeclaration node, Object data) {
        super.visit(node, data);

        if (JavaClassMetricKey.WOC.supports(node)) {
            double woc = JavaMetrics.get(JavaClassMetricKey.WOC, node, MetricOptions.emptyOptions());

            addViolation(
                    data,
                    node,
                    new Object[] { PrettyPrintingUtil.kindName(node), node.getSimpleName(), woc }
            );
        }

        return data;
    }
}
