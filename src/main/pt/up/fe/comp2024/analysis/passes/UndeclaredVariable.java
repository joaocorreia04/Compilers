package pt.up.fe.comp2024.analysis.passes;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.comp2024.ast.TypeUtils;
import pt.up.fe.comp2024.symboltable.JmmSymbolTable;
import pt.up.fe.specs.util.SpecsCheck;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Checks if the type of the expression in a return statement is compatible with the method return type.
 *
 * @author JBispo
 */
public class UndeclaredVariable extends AnalysisVisitor {

    private String currentMethod;

    @Override
    public void buildVisitor() {
        addVisit(Kind.METHOD_DECL, this::visitMethodDecl);
        addVisit(Kind.VAR_REF_EXPR, this::visitVarRefExpr);
        addVisit(Kind.LENGTH_CALL_EXPR, this::visitLengthCallExpr);
    }

    private Void visitLengthCallExpr(JmmNode lengthCallExpr, SymbolTable table) {
        try {
            JmmNode array = lengthCallExpr.getChildren().get(0);
            Type arrayType = TypeUtils.getExprType(array, table);

            if (arrayType == null || !arrayType.isArray()) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(lengthCallExpr),
                        NodeUtils.getColumn(lengthCallExpr),
                        "Can not get length of non-array type",
                        null)
                );
            }

            if(!lengthCallExpr.get("length").equals("length")) {
                var message = "Method 'length' does not exist.";
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(lengthCallExpr),
                        NodeUtils.getColumn(lengthCallExpr),
                        message,
                        null)
                );
            }

            return null;

        } catch (Exception e) {
            // Create error report for any exceptions that occur during analysis
            var message = "Exception during semantic analysis: " + e.getMessage();
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(lengthCallExpr),
                    NodeUtils.getColumn(lengthCallExpr),
                    message,
                    null)
            );
        }
        return null;
    }

    private Void visitMethodDecl(JmmNode method, SymbolTable table) {
        currentMethod = method.get("name");
        return null;
    }

    Set<String> vars = new HashSet<>();
    private Void visitVarRefExpr(JmmNode varRefExpr, SymbolTable table) {
        SpecsCheck.checkNotNull(currentMethod, () -> "Expected current method to be set");

        try {
            //check if there are repeated variables
            List<Symbol> localVariables = table.getLocalVariables(currentMethod);
            for(Symbol s : localVariables){
                if(vars.contains(s.getName())){
                    var message = String.format("Variable '%s' is already declared.", s.getName());
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(varRefExpr),
                            NodeUtils.getColumn(varRefExpr),
                            message,
                            null)
                    );
                }
                else {
                    vars.add(s.getName());
                }
            }
            vars.clear();

            var varRefName = varRefExpr.get("name");

            if (table.getLocalVariables(currentMethod).stream()
                    .anyMatch(varDecl -> varDecl.getName().equals(varRefName))) {
                return null;
            }
            if (table.getParameters(currentMethod).stream()
                    .anyMatch(param -> param.getName().equals(varRefName))) {
                return null;
            }

            if (table.getFields().stream()
                    .anyMatch(param -> param.getName().equals(varRefName))) {
                return null;
            }

            for(String s : table.getImports()){
                if(s.contains(varRefName)){
                    return null;
                }
            }

            // Create error report
            var message = String.format("Variable '%s' does not exist.", varRefName);
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(varRefExpr),
                    NodeUtils.getColumn(varRefExpr),
                    message,
                    null)
            );
        } catch (Exception e) {
            // Create error report for any exceptions that occur during analysis
            var message = "Exception during semantic analysis: " + e.getMessage();
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(varRefExpr),
                    NodeUtils.getColumn(varRefExpr),
                    message,
                    null)
            );
        }
        return null;
    }



}
