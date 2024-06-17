package pt.up.fe.comp2024.analysis.passes;

import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.comp2024.ast.TypeUtils;


public class BinaryOp extends AnalysisVisitor {

    @Override
    public void buildVisitor() {
        addVisit(Kind.BINARY_EXPR, this::visitBinaryOp);
    }

    private Void visitBinaryOp(JmmNode varRefExpr, SymbolTable table) {
        try {//get the left and right nodes and op of the binary expression
            JmmNode varRefName = varRefExpr.getChildren().get(0);
            JmmNode varRefName2 = varRefExpr.getChildren().get(1);
            System.out.println(varRefName);
            System.out.println(varRefName2);
            String op = varRefExpr.get("op");

            //get the type of the left and right nodes of the symbol table
            Type lefttype = TypeUtils.getExprType(varRefName, table);
            Type righttype = TypeUtils.getExprType(varRefName2, table);

            if (op.equals("!")) {
                if (!TypeUtils.areTypesAssignable(lefttype, new Type("boolean", false), table)) {
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(varRefExpr),
                            NodeUtils.getColumn(varRefExpr),
                            "Invalid type for operation " + op + ": " + lefttype.getName(),
                            null)
                    );
                    return null;
                }
            }

            //check if the types are null
            if (lefttype == null || righttype == null) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(varRefExpr),
                        NodeUtils.getColumn(varRefExpr),
                        "One or more types cannot be resolved",
                        null)
                );
                return null;
            }

            //check if the types are not equal
            if (!lefttype.equals(righttype)) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(varRefExpr),
                        NodeUtils.getColumn(varRefExpr),
                        "Can not apply operator " + op,
                        null)
                );
                return null;
            }

            //check operator
            if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")) {
                //check if either operand is an array
                if (lefttype.isArray() || righttype.isArray()) {
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(varRefExpr),
                            NodeUtils.getColumn(varRefExpr),
                            "Can not apply operator " + op + " to array",
                            null)
                    );
                    return null;
                }

                //check if the types are not int
//                if (!(lefttype.getName().equals("int") && righttype.getName().equals("int"))) {
//                    addReport(Report.newError(
//                            Stage.SEMANTIC,
//                            NodeUtils.getLine(varRefExpr),
//                            NodeUtils.getColumn(varRefExpr),
//                            "Can not apply operator " + op + " to " + lefttype.getName(),
//                            null)
//                    );
//                    return null;
//                }
                if(!(TypeUtils.areTypesAssignable(lefttype, new Type("int", false), table) && TypeUtils.areTypesAssignable(righttype, new Type("int", false), table))){
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(varRefExpr),
                            NodeUtils.getColumn(varRefExpr),
                            "Can not apply operator " + op + " to " + lefttype.getName(),
                            null)
                    );
                }
            }
            return null;
        } catch (Exception e) {

            return null;
        }
    }
}