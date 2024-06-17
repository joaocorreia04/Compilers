package pt.up.fe.comp2024.analysis.passes;

import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.ReportType;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.comp2024.ast.TypeUtils;

public class ReturnCheck extends AnalysisVisitor {

    @Override
    public void buildVisitor() {
        addVisit(Kind.RETURN_STMT, this::visitReturn);
    }

    private Void visitReturn(JmmNode node, SymbolTable table) {
        try {
            JmmNode parent = node.getParent();

            // Ensure the return statement is within a method
//            if (!parent.getKind().equals(Kind.METHOD_DECL.toString())) {
//                addReport(Report.newError(
//                        Stage.SEMANTIC,
//                        NodeUtils.getLine(node),
//                        NodeUtils.getColumn(node),
//                        "Return statement not inside a method",
//                        null)
//                );
//                return null;
//            }


            // Get the return type of the method from the AST
            Type returnType = TypeUtils.getReturnType(parent, table);


            // Check if the return statement has an expression
            if (node.getNumChildren() == 0) {
                // Check if the method should have a return type
                if (!returnType.equals("void")) {
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Method must return a value of type " + returnType,
                            null)
                    );
                }
            } else {
                //check if main has a return
                if (!returnType.equals(null) && parent.get("name").equals("main")) {
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Main method cannot return a value",
                            null)
                    );
                }

                // Check if the method should not have a return type
                if (returnType.equals("void")) {
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Method with void return type cannot return a value",
                            null)
                    );
                    return null;
                }

                // Check the type of the expression in the return statement
                Type returnExpressionType = TypeUtils.getExprType(node.getJmmChild(0), table);
                if (!TypeUtils.areTypesAssignable(returnExpressionType,returnType,table)){
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Return type " + returnExpressionType +
                                    " does not match method return type " + returnType,
                            null)
                    );
                }
            }
        } catch (Exception e) {
            // Create error report for any exceptions that occur during analysis
            var message = "Exception during semantic analysis: " + e.getMessage();
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(node),
                    NodeUtils.getColumn(node),
                    message,
                    null)
            );
        }

        return null;
    }


}

