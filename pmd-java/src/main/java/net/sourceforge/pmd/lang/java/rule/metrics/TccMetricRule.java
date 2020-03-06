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

public class TccMetricRule extends AbstractJavaMetricsRule {
    @Override
    public Object visit(ASTAnyTypeDeclaration node, Object data) {
        super.visit(node, data);

        if (JavaClassMetricKey.TCC.supports(node)) {
            double tcc = JavaMetrics.get(JavaClassMetricKey.TCC, node, MetricOptions.emptyOptions());

            addViolation(
                    data,
                    node,
                    new Object[] { PrettyPrintingUtil.kindName(node), node.getSimpleName(), tcc }
            );
        }

        return data;
    }
}
