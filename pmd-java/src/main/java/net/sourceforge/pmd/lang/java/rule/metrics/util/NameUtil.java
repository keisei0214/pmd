/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.rule.metrics.util;

import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodOrConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.MethodLikeNode;
import net.sourceforge.pmd.lang.java.ast.internal.PrettyPrintingUtil;

public final class NameUtil {
    private NameUtil() {
    }

    public static String getOpname(MethodLikeNode node) {
        return node instanceof ASTMethodOrConstructorDeclaration
                ? PrettyPrintingUtil.displaySignature((ASTMethodOrConstructorDeclaration) node)
                : "lambda";
    }

    public static String getKindName(MethodLikeNode node) {
        return node instanceof ASTMethodOrConstructorDeclaration
                ? node instanceof ASTConstructorDeclaration ? "constructor" : "method"
                : "lambda";
    }
}
