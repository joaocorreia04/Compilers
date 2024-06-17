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

public class Conditional extends AnalysisVisitor{
    @Override
    public void buildVisitor() {
        addVisit(Kind.IF_ELSE_STMT, this::visitIf);
        addVisit(Kind.WHILE_STMT, this::visitWhile);
    }

    private Void visitIf(JmmNode ifNode, SymbolTable table) {
        try {
            JmmNode condition = ifNode.getChild(0);

            Type conditionType = TypeUtils.getExprType(condition, table);

            if (conditionType == null) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(ifNode),
                        NodeUtils.getColumn(ifNode),
                        "Type can not be resolved inside if expression",
                        null)
                );
            }

            if (conditionType.isArray()) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(ifNode),
                        NodeUtils.getColumn(ifNode),
                        "Cannot apply operator if to array",
                        null)
                );
            }

            if (!conditionType.getName().equals("boolean") && !conditionType.getName().equals("imported")) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(ifNode),
                        NodeUtils.getColumn(ifNode),
                        "Condition must be of type boolean",
                        null)
                );
            }
        } catch (Exception e) {

            return null;
        }
        return null;
    }


    private Void visitWhile(JmmNode whileNode, SymbolTable table) {
        try {
            JmmNode condition = whileNode.getChild(0);
            Type conditionType = TypeUtils.getExprType(condition, table);

            if (conditionType == null) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(whileNode),
                        NodeUtils.getColumn(whileNode),
                        "Type can not be resolved inside while expression",
                        null)
                );
            }

            if (conditionType.isArray()) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(whileNode),
                        NodeUtils.getColumn(whileNode),
                        "Cannot apply operator while to array",
                        null)
                );
            }


            if (!conditionType.getName().equals("boolean") && !conditionType.getName().equals("imported")) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(whileNode),
                        NodeUtils.getColumn(whileNode),
                        "Condition must be of type boolean",
                        null)
                );
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
