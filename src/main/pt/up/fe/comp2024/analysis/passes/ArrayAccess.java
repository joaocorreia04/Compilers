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

public class ArrayAccess extends AnalysisVisitor {

    @Override
    public void buildVisitor() {
        addVisit(Kind.ARRAY_ACCESS_EXPR, this::visitArrayAccess);
        addVisit(Kind.ARRAY_EXPR, this::visitArrayExpr);
        addVisit(Kind.EXPR_NEW_ARRAY, this::visitNewArrayExpr);
    }

    private Void visitNewArrayExpr(JmmNode arrayExpr, SymbolTable table) {
        try{
            var child = arrayExpr.getChild(0);
            var type = TypeUtils.getExprType(child, table);
            if(type.isArray() ||  (!type.getName().equals("int") && !type.getName().equals("imported"))){
                addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(arrayExpr),
                    NodeUtils.getColumn(arrayExpr),
                    "Array initializer must have a type of array",
                    null)
                );
            }
            return null;
        } catch (Exception e) {
//            addReport(Report.newError(
//                    Stage.SEMANTIC,
//                    NodeUtils.getLine(arrayExpr),
//                    NodeUtils.getColumn(arrayExpr),
//                    "Error in NewArrayExpr analysis",
//                    null)
//            );
            return null;
        }


    }

    private Void visitArrayExpr(JmmNode arrayExpr, SymbolTable table) {
        try{var childs = arrayExpr.getChildren();

        if(childs.size() == 0) {
            return null;
        }

        var type = new Type("int", false);

        for(var child : childs) {
            if(TypeUtils.getExprType(child, table) == null) {
                addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(arrayExpr),
                    NodeUtils.getColumn(arrayExpr),
                    "Array initializer must have a type",
                    null)
                );
            }
            else if(!TypeUtils.getExprType(child, table).equals(type)) {
//                addReport(Report.newError(
//                    Stage.SEMANTIC,
//                    NodeUtils.getLine(arrayExpr),
//                    NodeUtils.getColumn(arrayExpr),
//                    "Array initializer must have a type of int",
//                    null)
//                );
            }
        }

        return null;}
        catch (Exception e) {
//            addReport(Report.newError(
//                    Stage.SEMANTIC,
//                    NodeUtils.getLine(arrayExpr),
//                    NodeUtils.getColumn(arrayExpr),
//                    "Error in ArrayExpr analysis",
//                    null)
//            );
            return null;
        }
    }

    private Void visitArrayAccess(JmmNode arrayAccess, SymbolTable table) {
        try{// Handle the case when there are not enough children
        if (arrayAccess.getChildren().size() < 2) {
            return null;
        }

        JmmNode array = arrayAccess.getChildren().get(0);
        JmmNode accessor = arrayAccess.getChildren().get(1);

        Type arrayType = TypeUtils.getExprType(array, table);
        Type accessorType = TypeUtils.getExprType(accessor, table);


        // Check if operand is an array
        if (arrayType == null || !arrayType.isArray()) {
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(arrayAccess),
                    NodeUtils.getColumn(arrayAccess),
                    "Can not access array element of non-array type",
                    null)
            );
        }

        // Check if accessor is of type int
        if (accessorType == null || !accessorType.getName().equals("int") || accessorType.isArray()) {
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(arrayAccess),
                    NodeUtils.getColumn(arrayAccess),
                    "Array accessor must be of type int",
                    null)
            );
        }

        return null;} catch (Exception e) {
//            addReport(Report.newError(
//                    Stage.SEMANTIC,
//                    NodeUtils.getLine(arrayAccess),
//                    NodeUtils.getColumn(arrayAccess),
//                    "Error in ArrayAccess analysis",
//                    null)
//            );
            return null;
        }
    }
}
