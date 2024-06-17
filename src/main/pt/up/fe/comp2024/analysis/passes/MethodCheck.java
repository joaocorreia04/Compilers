package pt.up.fe.comp2024.analysis.passes;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
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

import java.util.List;
import java.util.Set;

public class MethodCheck extends AnalysisVisitor{
    @Override
    public void buildVisitor() {
        addVisit(Kind.EXPR_MEMBER_CALL, this::visitMethod);
        addVisit(Kind.METHOD_DECL, this::visitMethod2);
        addVisit(Kind.IMPORT_DECL, this::visitImport);
    }
    private final Set<String> importedNames = new java.util.HashSet<>();

    private Void visitMethod(JmmNode node, SymbolTable table) {
        try{
            Type type = TypeUtils.getExprType(node.getChild(0), table);

            if(type.getName().equals("")){
                return null;
           }

            var imports = table.getImports();
            for(String imp : imports){
                if(imp.contains(type.getName())){
                    return null;
                }
            }


            // Check if the method is declared in the symbol table and if it's extended/imported
            if (!table.getMethods().contains(node.get("name")) && table.getSuper() == null) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Method not declared in the symbol table",
                        null)
                );
            }

            int cont=0;
            boolean is_last=false;
            for(int i = 0; i < table.getParameters(node.get("name")).size(); i++){
                if(table.getParameters(node.get("name")).get(i).getType().hasAttribute("vararg")){
                    cont++;
                    if(i==table.getParameters(node.get("name")).size()-1){
                        is_last=true;
                    }
                }
            }

            if(cont>1){
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Method has more than one vararg",
                        null)
                );
            }

            if(!is_last && cont==1){
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Vararg is not the last parameter",
                        null)
                );
            }


            //when i call a method, check if the types of the arguments passed are equal to the types of the parameters of the method

            for(int i = 0; i < table.getParameters(node.get("name")).size(); i++) {
                Type parameterType = table.getParameters(node.get("name")).get(i).getType();

                if (parameterType.hasAttribute("vararg")) {
                    // Handle vararg parameter;
                    while (i < node.getChildren().size() - 1) {
                        Type argumentType = TypeUtils.getExprType(node.getChild(i + 1), table);
                        if (!TypeUtils.areTypesAssignable(argumentType, parameterType, table)) {
                            //System.out.println("entrei1");
                            addReport(Report.newError(
                                    Stage.SEMANTIC,
                                    NodeUtils.getLine(node),
                                    NodeUtils.getColumn(node),
                                    "Argument type does not match parameter type",
                                    null)
                            );
                        }
                        i++;
                    }
                    return null;
                }

                Type argumentType = TypeUtils.getExprType(node.getChild(i+1), table);
                if(!TypeUtils.areTypesAssignable(argumentType, parameterType, table)){
                    //System.out.println("entrei2");
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Argument type does not match parameter type",
                            null)
                    );
                }
            }

        }
        catch (Exception e) {
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

    private final Set<String> methodNames = new java.util.HashSet<>();
    private Void visitMethod2(JmmNode node, SymbolTable table) {
        try{
            List<JmmNode> descendants = node.getDescendants();

            //check if main has this
            if(node.get("name").equals("main")){
                for(JmmNode s : node.getDescendants()){
                    if(s.getKind().equals("ThisExpr")){
                        addReport(Report.newError(
                                Stage.SEMANTIC,
                                NodeUtils.getLine(node),
                                NodeUtils.getColumn(node),
                                "Main method cannot have 'this' expression",
                                null)
                        );
                    }
                }
            }

            //check if there are repeated methods
            List<String> methods = table.getMethods();
            for(String methodNode : methods){
                if(methodNames.contains(methodNode)){
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Method already exists",
                            null)
                    );
                } else {
                    methodNames.add(methodNode);
                }
            }
            methodNames.clear();

            //check if there are more than one return statement
            int count_returns = 0;
            for(JmmNode s : descendants){
                if(s.getKind().equals("ReturnStmt")){
                    count_returns++;
                }
            }
            if(count_returns>1){
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Method has more than one return statement",
                        null)
                );
            }
            if(!node.get("name").equals("main") && count_returns==0){
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Method has no return statement",
                        null)
                );
            }

            // Check if the method is declared in the symbol table and if it's extended/imported
            if (!table.getMethods().contains(node.get("name")) && table.getSuper() == null) {
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Method not declared in the symbol table",
                        null)
                );
            }

            int cont=0;
            boolean is_last=false;
            for(int i = 0; i < table.getParameters(node.get("name")).size(); i++){
                if(table.getParameters(node.get("name")).get(i).getType().hasAttribute("vararg")){
                    cont++;
                    if(i==table.getParameters(node.get("name")).size()-1){
                        is_last=true;
                    }
                }
            }

            if(cont>1){
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Method has more than one vararg",
                        null)
                );
            }

            if(!is_last && cont==1){
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(node),
                        NodeUtils.getColumn(node),
                        "Vararg is not the last parameter",
                        null)
                );
            }


            //when i call a method, check if the types of the arguments passed are equal to the types of the parameters of the method

            for(int i = 0; i < table.getParameters(node.get("name")).size(); i++) {
                //System.out.println("params " +table.getParameters(node.get("name")));
                Type parameterType = table.getParameters(node.get("name")).get(i).getType();
                if (parameterType.hasAttribute("vararg")) {
                    // Handle vararg parameter
                    Type pType = new Type(parameterType.getName(), false);
                    while (i < node.getChildren().size()) {
                        Type argumentType = TypeUtils.getExprType(node.getChild(i), table);
                        if (!TypeUtils.areTypesAssignable(argumentType, pType, table)) {
                            //System.out.println("entrei3");
                            addReport(Report.newError(
                                    Stage.SEMANTIC,
                                    NodeUtils.getLine(node),
                                    NodeUtils.getColumn(node),
                                    "Argument type does not match parameter type",
                                    null)
                            );
                        }
                        i++;
                    }
                    return null;
                }

//                Type argumentType = TypeUtils.getExprType(node.getChild(i+1), table);
//                System.out.println("node" + node.getChild(1));
//                if(!TypeUtils.areTypesAssignable(argumentType, parameterType, table)){
//                    System.out.println("argumentType: " + argumentType);
//                    System.out.println("parameterType: " + parameterType);
//                    System.out.println("entrei4");
//                    addReport(Report.newError(
//                            Stage.SEMANTIC,
//                            NodeUtils.getLine(node),
//                            NodeUtils.getColumn(node),
//                            "Argument type does not match parameter type",
//                            null)
//                    );
//                }
            }
        }
        catch (Exception e) {
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

    private Void visitImport(JmmNode node, SymbolTable table) {
        try {
            //get table imports
            List<String> imports = table.getImports();
            for (String importNode : imports) {
                if (importedNames.contains(importNode)) {
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(node),
                            NodeUtils.getColumn(node),
                            "Import already exists",
                            null)
                    );
                } else {
                    importedNames.add(importNode);
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
        importedNames.clear();
        return null;
    }

}
