/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.ast

import net.sourceforge.pmd.lang.ast.test.shouldBe
import net.sourceforge.pmd.lang.java.ast.JavaVersion.Companion.Latest
import net.sourceforge.pmd.lang.java.ast.JavaVersion.J1_7
import net.sourceforge.pmd.lang.java.ast.JavaVersion.J9

/**
 * @author Clément Fournier
 * @since 7.0.0
 */
class ASTTryStatementTest : ParserTestSpec({
    parserTest("Test try with resources", javaVersions = J1_7..Latest) {

        "try (Foo a = 2){}" should matchStmt<ASTTryStatement> {

            child<ASTResourceList> {
                child<ASTResource> {
                    it::isConciseResource shouldBe false
                    it::getStableName shouldBe "a"

                    it::getInitializer shouldBe fromChild<ASTLocalVariableDeclaration, ASTExpression> {
                        it::isFinal shouldBe false
                        classType("Foo")
                        fromChild<ASTVariableDeclarator, ASTExpression> {
                            variableId("a")
                            int(2)
                        }
                    }
                }
            }

            block()
        }

        "try (final Foo a = 2){}" should matchStmt<ASTTryStatement> {

            child<ASTResourceList> {
                child<ASTResource> {
                    it::isConciseResource shouldBe false
                    it::getStableName shouldBe "a"

                    it::getInitializer shouldBe fromChild<ASTLocalVariableDeclaration, ASTExpression> {
                        it::isFinal shouldBe true
                        classType("Foo")
                        fromChild<ASTVariableDeclarator, ASTExpression> {
                            variableId("a")
                            int(2)
                        }
                    }
                }
            }

            block()
        }

    }
    parserTest("Test concise try with resources", javaVersions = J9..Latest) {


        "try (a){}" should matchStmt<ASTTryStatement> {

            child<ASTResourceList> {
                child<ASTResource> {
                    it::isConciseResource shouldBe true
                    it::getStableName shouldBe "a"

                    it::getInitializer shouldBe variableAccess("a")
                }
                it::hasTrailingSemiColon shouldBe false
            }

            block()
        }


        "try (a;){}" should matchStmt<ASTTryStatement> {

            child<ASTResourceList> {
                child<ASTResource> {
                    it::isConciseResource shouldBe true
                    it::getStableName shouldBe "a"

                    it::getInitializer shouldBe variableAccess("a")
                }
                it::hasTrailingSemiColon shouldBe true
            }

            block()
        }


        "try (a.b){}" should matchStmt<ASTTryStatement> {

            child<ASTResourceList> {
                child<ASTResource> {
                    it::isConciseResource shouldBe true
                    it::getStableName shouldBe "a.b"

                    it::getInitializer shouldBe fieldAccess("b") {
                        ambiguousName("a")
                    }
                }

            }

            block()
        }


        "try ( a.foo() ){}" should notParseIn(StatementParsingCtx)
        "try (new Foo()){}" should notParseIn(StatementParsingCtx)

    }

})
