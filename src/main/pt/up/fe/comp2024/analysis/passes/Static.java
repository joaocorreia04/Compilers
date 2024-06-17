package pt.up.fe.comp2024.analysis.passes;

import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.NodeUtils;

public class Static extends AnalysisVisitor {
    @Override
    public void buildVisitor() {
        addVisit(Kind.METHOD_DECL, this::visitMethodDecl);
    }

    private Void visitMethodDecl(JmmNode node, SymbolTable table) {
        try {
            String method_name = node.get("name");

            if (method_name.equals("main") && node.hasAttribute("v")) {
//                if(node.getDescendants().stream().anyMatch(n -> n.getKind().equals(Kind.THIS_EXPR.toString()))){
//                    addReport(Report.newError(
//                            Stage.SEMANTIC,
//                            NodeUtils.getLine(node),
//                            NodeUtils.getColumn(node),
//                            "Main method cannot have 'this' expression",
//                            null)
//                    );
//                }
                for(var n : node.getDescendants()){
                    if(n.getKind().equals(Kind.THIS_EXPR.toString())){
                        addReport(Report.newError(
                                Stage.SEMANTIC,
                                NodeUtils.getLine(node),
                                NodeUtils.getColumn(node),
                                "Main method cannot have 'this' expression",
                                null)
                        );
                        return null;
                    }

                    if(n.getKind().equals(Kind.VAR_REF_EXPR.toString())){
                        for(var locals : table.getLocalVariables(method_name)){
                            if(locals.getName().equals(n.get("name"))){
                                return null;
                            }
                        }

                        for(var params: table.getParameters(method_name)){
                            if(params.getName().equals(n.get("name"))){
                                return null;
                            }
                        }

                        for(var fields : table.getFields()){
                            if(fields.getName().equals(n.get("name"))){
                                addReport(Report.newError(
                                        Stage.SEMANTIC,
                                        NodeUtils.getLine(node),
                                        NodeUtils.getColumn(node),
                                        "Variable already declared",
                                        null)
                                );
                            }
                        }
                    }

                }

            }

            return null;

        } catch (Exception e) {
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(node),
                    NodeUtils.getColumn(node),
                    "Error in Static analysis",
                    null)
            );
            return null;
        }
    }

}
