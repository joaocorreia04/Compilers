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

public class BooleanOp extends AnalysisVisitor {
    @Override
    public void buildVisitor() {
        addVisit(Kind.BINARY_EXPR, this::visitBinaryOp);
    }
    private Void visitBinaryOp(JmmNode varRefExpr, SymbolTable table){
        try{//get the left and right nodes and op of the binary expression
        JmmNode varRefName = varRefExpr.getChildren().get(0);
        JmmNode varRefName2 = varRefExpr.getChildren().get(1);
        String op = varRefExpr.get("op");
        //get the type of the left and right nodes of the symbol table
        Type lefttype = TypeUtils.getExprType(varRefName, table);
        Type righttype = TypeUtils.getExprType(varRefName2, table);

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
        if (!lefttype.getName().equals(righttype.getName())) {
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
        if (op.equals("&&") || op.equals("||")) {
            //check if is not boolean, and report
            if (lefttype.isArray() || righttype.isArray()) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(varRefExpr),
                        NodeUtils.getColumn(varRefExpr),
                        "Can not apply operator " + op + " to arrays",
                        null)
                );
                return null;
            }
            //check if is not boolean, and report
            if (!lefttype.getName().equals("boolean") || !righttype.getName().equals("boolean")) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(varRefExpr),
                        NodeUtils.getColumn(varRefExpr),
                        "Can not apply operator " + op + " to non-boolean types",
                        null)
                );
                return null;
            }
        }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
