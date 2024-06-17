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

public class AssignmentCheck extends AnalysisVisitor {

    @Override
    public void buildVisitor() {
        addVisit(Kind.ASSIGN_STMT, this::visitAssign);
    }

    private Void visitAssign(JmmNode node, SymbolTable table) {
        try {
            // Get the left-hand side variable name
            JmmNode left = node.getChildren().get(0);
            String leftVarName = left.getKind().equals("VarRefExpr") ? left.get("name") : null;
            // Get the right-hand side expression
            JmmNode right = node.getChildren().get(1);


            // Get the types of the left-hand side and right-hand side expressions
            Type leftType = TypeUtils.getExprType(left, table);
            Type rightType = TypeUtils.getExprType(right, table);

            if (rightType == null) {
                // Report error if right-hand side type cannot be resolved
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Can not resolve type of the right-hand side expression",
                        null)
                );
                return null;
            }

            if (leftType == null) {
                // Report error if left-hand side variable is not found
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Left-hand side variable not found",
                        null)
                );
                return null;
            }

            // Check if the right-hand side expression is an array initializer
            if (leftType.isArray() && right.getKind().equals("ArrayExpr")) {
                // Check if the array initializer has correct types
                for (JmmNode child : right.getChildren()) {
                    Type childType = TypeUtils.getExprType(child, table);
                    //if isnt integer, report
                    if (childType == null || !childType.getName().equals("int")) {
                        addReport(Report.newError(
                                Stage.SEMANTIC,
                                NodeUtils.getLine(node),
                                NodeUtils.getColumn(node),
                                "Array initializer must have elements of type int",
                                null)
                        );
                        break;
                    }

                }
            }


            if (!TypeUtils.areTypesAssignable(rightType, leftType, table)) {
                // Report error if types of left-hand side and right-hand side expressions are not compatible
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Can not assign " + rightType.getName() + " to " + leftType.getName(),
                        null)
                );
                return null;
            }

            //if left type is not array and i try to assign array values
            if(!leftType.isArray() && right.getKind().equals("ArrayExpr")){
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Can not assign array values to a non-array variable",
                        null)
                );
                return null;
            }

            if(left.getKind().equals("ArrayAccessExpr")){
                if(!rightType.getName().equals("int") || rightType.isArray()){
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Can not assign " + rightType.getName() + " to " + leftType.getName(),
                            null)
                    );
                    return null;

                }

                if(!TypeUtils.getExprType(left.getChildren().get(0), table).isArray()){
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Can not assign to a non-array variable",
                            null)
                    );
                    return null;
                }
                //testar com aretypesassignable
                if(!TypeUtils.getExprType(left.getChildren().get(1), table).equals(TypeUtils.getIntType())){
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Can not assign to a non-array variable",
                            null)
                    );
                    return null;
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
