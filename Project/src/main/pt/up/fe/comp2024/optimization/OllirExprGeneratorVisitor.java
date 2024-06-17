package pt.up.fe.comp2024.optimization;

import org.specs.comp.ollir.Ollir;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.ast.PreorderJmmVisitor;
import pt.up.fe.comp.jmm.ollir.OllirUtils;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.TypeUtils;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static pt.up.fe.comp2024.ast.Kind.*;

/**
 * Generates OLLIR code from JmmNodes that are expressions.
 */
public class OllirExprGeneratorVisitor extends AJmmVisitor<Void, OllirExprResult> {

    private static final String SPACE = " ";
    private static final String ASSIGN = ":=";
    private final String END_STMT = ";\n";

    private final SymbolTable table;

    public OllirExprGeneratorVisitor(SymbolTable table) {
        this.table = table;
    }

    @Override
    protected void buildVisitor() {
        addVisit(VAR_REF_EXPR, this::visitVarRef);
        addVisit(BINARY_EXPR, this::visitBinExpr);
        addVisit(INTEGER_LITERAL, this::visitInteger);
        addVisit(EXPR_MEMBER_CALL, this::visitExprMemberCall);
        addVisit(TRUE_EXPR, this::visitBoolLiteral);
        addVisit(FALSE_EXPR, this::visitBoolLiteral);
        addVisit(NEW_CLASS, this::visitNewClass);
        addVisit(CONDITIONAL_EXPR, this::visitConditionalExpr);
        addVisit(AND_EXPR, this::visitAndExpr);
        addVisit(NOT_EXPR, this::visitNotExpr);
        addVisit(EXPR_NEW_ARRAY, this::visitNewArray);
        addVisit(LENGTH_CALL_EXPR, this::visitArrayLength);
        addVisit(THIS_EXPR, this::visitThisExpr);
        addVisit(ARRAY_EXPR, this::visitArrayExpr);
        addVisit(ARRAY_ACCESS_EXPR, this::visitArrayAccessExpr);

        setDefaultVisit(this::defaultVisit);
    }

    private OllirExprResult visitArrayAccessExpr(JmmNode node, Void unused){
        StringBuilder code = new StringBuilder();
        StringBuilder computation = new StringBuilder();

        var childLeft = (node.getJmmChild(0));
        var childRight = (node.getJmmChild(1));

        var visitLeft = visit(childLeft);
        var visitRight = visit(childRight);

        //System.out.println("NODE HERE: " + node);
        //System.out.println("CHILD : " + node.getChildren());

        computation.append(visitLeft.getComputation());
        computation.append(visitRight.getComputation());

        var temp = OptUtils.getTemp();
        var type = OptUtils.toOllirType(TypeUtils.getExprType(node, table));

        //System.out.println("TYPE : " + type);

        code.append(temp);
        code.append(type);

        computation.append(temp);
        computation.append(type);
        computation.append(SPACE);
        computation.append(ASSIGN);
        computation.append(type);
        computation.append(SPACE);
        computation.append(visitLeft.getCode());
        computation.append("[");
        computation.append(visitRight.getCode());
        computation.append("]");
        computation.append(type);
        computation.append(END_STMT);

        return new OllirExprResult(code.toString(), computation.toString());
    }

    private OllirExprResult visitArrayExpr(JmmNode node, Void unused){
        StringBuilder code = new StringBuilder();
        StringBuilder computation = new StringBuilder();
        var temp = OptUtils.getTemp();
        var type = OptUtils.toOllirType(TypeUtils.getExprType(node, table));
        computation.append(temp).append(type).append(SPACE).append(ASSIGN).append(type).append(SPACE).append("new(array, ").append(node.getNumChildren()).append(".i32)").append(type).append(END_STMT);
        String arrayTempName = "__varargs_array_0";
        computation.append(arrayTempName).append(type).append(SPACE).append(ASSIGN).append(type).append(SPACE).append(temp).append(type).append(END_STMT);
        for (int i = 0; i < node.getNumChildren(); i++) {
            var child = visit(node.getJmmChild(i));
            computation.append(child.getComputation());
            computation.append(arrayTempName).append(".array.i32").append("[").append(i).append(".i32").append("].i32 :=").append(".i32").append(SPACE).append(child.getCode()).append(END_STMT);
        }
        code.append(arrayTempName).append(type);
        return new OllirExprResult(code.toString(), computation.toString());
    }
    private OllirExprResult visitThisExpr(JmmNode node, Void unused){
        StringBuilder code = new StringBuilder();
        code.append("this.");
        code.append(table.getClassName());
        return new OllirExprResult(code.toString());
    }
    private OllirExprResult visitNewArray(JmmNode node, Void unused){

        StringBuilder code = new StringBuilder();
        StringBuilder computation = new StringBuilder();

        var size = visit(node.getJmmChild(0));
        computation.append(size.getComputation());

        var temp = OptUtils.getTemp();
        String type = OptUtils.toOllirType(TypeUtils.getExprType(node, table));

        computation.append(temp);
        computation.append(".i32");
        computation.append(SPACE);
        computation.append(ASSIGN);
        computation.append(".i32");
        computation.append(SPACE);
        computation.append(size.getCode());
        computation.append(END_STMT);

        code.append("new(array, ");
        code.append(temp);
        code.append(".i32");
        code.append(")");
        code.append(type);

        return new OllirExprResult(code.toString(), computation.toString());
    }
    private OllirExprResult visitArrayLength(JmmNode node, Void unused){
        var visited = visit(node.getJmmChild(0));
        StringBuilder code = new StringBuilder();
        StringBuilder computation = new StringBuilder();

        computation.append(visited.getComputation());
        String type = OptUtils.toOllirType(TypeUtils.getExprType(node, table));

        var temp = OptUtils.getTemp();
        computation.append(temp);
        computation.append(type);
        computation.append(SPACE);
        computation.append(ASSIGN);
        computation.append(type);
        computation.append(SPACE);
        computation.append("arraylength(");
        computation.append(visited.getCode());
        computation.append(")");
        computation.append(type);
        computation.append(END_STMT);
        code.append(temp);
        code.append(type);

        return new OllirExprResult(code.toString(), computation.toString());
    }


    private OllirExprResult visitNotExpr(JmmNode node, Void unused){
        StringBuilder code = new StringBuilder();
        StringBuilder computation = new StringBuilder();

        var expr = visit(node.getJmmChild(0));
        computation.append(expr.getComputation());

        var temp = OptUtils.getTemp();
        var type = OptUtils.toOllirType(TypeUtils.getBooleanType());

        code.append(temp + type);
        computation.append(code);
        computation.append(SPACE);
        computation.append(ASSIGN);
        computation.append(type);
        computation.append(SPACE);
        computation.append("!");
        computation.append(type);
        computation.append(SPACE);
        computation.append(expr.getCode());
        computation.append(END_STMT);

        return new OllirExprResult(code.toString(), computation.toString());
    }

    private OllirExprResult visitConditionalExpr(JmmNode node, Void unused){

        StringBuilder computation = new StringBuilder();
        StringBuilder code = new StringBuilder();
        var ifNum = OptUtils.getCurrentIf();

        var left = visit(node.getJmmChild(0));
        var right = visit(node.getJmmChild(1));

        var temp = OptUtils.getTemp();

        var type = OptUtils.toOllirType(TypeUtils.getBooleanType());

        code.append(temp + type);

        computation.append(left.getComputation());
        computation.append(right.getComputation());
        computation.append("if(");
        computation.append(left.getCode());
        computation.append(SPACE);
        computation.append("<");
        computation.append(type);
        computation.append(SPACE);
        computation.append(right.getCode());
        computation.append(") goto ");
        computation.append("true_");
        computation.append(ifNum);
        computation.append(END_STMT);

        computation.append("goto ");
        computation.append("end_");
        computation.append(ifNum);
        computation.append(END_STMT);

        computation.append("true_");
        computation.append(ifNum);
        computation.append(":\n");
        computation.append(code);
        computation.append(SPACE);
        computation.append(ASSIGN);
        computation.append(type);
        computation.append(SPACE);
        computation.append("1");
        computation.append(type);
        computation.append(END_STMT);

        return new OllirExprResult(code.toString(), computation.toString());
    }

    private OllirExprResult visitAndExpr(JmmNode node, Void unused){

        StringBuilder code = new StringBuilder();
        StringBuilder computation = new StringBuilder();
        var ifNum = OptUtils.getCurrentIf();

        var left = visit(node.getJmmChild(0));
        var right = visit(node.getJmmChild(1));

        var temp = OptUtils.getTemp();
        var type = OptUtils.toOllirType(TypeUtils.getBooleanType());

        code.append(temp + type);

        computation.append(left.getComputation());
        computation.append("if(");
        computation.append(left.getCode());
        computation.append(")");
        computation.append(SPACE);
        computation.append("goto ");
        computation.append("true_");
        computation.append(ifNum);
        computation.append(END_STMT);

        computation.append(code);
        computation.append(SPACE);
        computation.append(ASSIGN);
        computation.append(type);
        computation.append(SPACE);
        computation.append("0");
        computation.append(type);
        computation.append(END_STMT);
        computation.append("goto");
        computation.append(SPACE);
        computation.append("end_");
        computation.append(ifNum);
        computation.append(END_STMT);

        computation.append("true_");
        computation.append(ifNum);
        computation.append(":\n");

        computation.append(right.getComputation());

        computation.append(code);
        computation.append(SPACE);
        computation.append(ASSIGN);
        computation.append(type);
        computation.append(SPACE);
        computation.append(right.getCode());
        computation.append(END_STMT);


        computation.append("end_");
        computation.append(ifNum);
        computation.append(":\n");

        return new OllirExprResult(code.toString(), computation.toString());
    }

    private OllirExprResult visitNewClass(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();
        StringBuilder computation = new StringBuilder();
        String className = node.get("name");
        String OllirType = OptUtils.toOllirType(new Type(className, false));
        var temp = OptUtils.getTemp();
        code.append(temp + OllirType);
        computation.append(code);
        computation.append(SPACE);
        computation.append(ASSIGN);
        computation.append(OllirType);
        computation.append(SPACE);
        computation.append("new(");
        computation.append(className);
        computation.append(")");
        computation.append(OllirType);
        computation.append(END_STMT);
        computation.append("invokespecial");
        computation.append("(");
        computation.append(code);
        computation.append(", ");
        computation.append("\"<init>\"");
        computation.append(")");
        computation.append(".V");
        computation.append(END_STMT);
        return new OllirExprResult(code.toString(), computation);
    }

    private OllirExprResult visitBoolLiteral(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();
        switch (node.getKind()){
            case "TrueExpr":
                code.append("1.bool");
                break;
            case "FalseExpr":
                code.append("0.bool");
                break;
        }
        return new OllirExprResult(code.toString());
    }


    private OllirExprResult visitExprMemberCall(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();
        StringBuilder computation = new StringBuilder();

        var targetNode = node.getJmmChild(0);
        var object = visit(node.getJmmChild(0));
        var methodName = node.get("name");
        var target = object.getCode();
        computation.append(object.getComputation());

        List<String> instaceImports = new ArrayList<>();
        for (var imp : table.getImports()) {
            String importString = imp.replaceAll("[\\[\\]]", "");
            String[] parts = importString.split(",");
            String lastElement = parts[parts.length - 1].trim();
            instaceImports.add(lastElement);
        }
        String invoke = getInvoke(node);
        code.append(invoke);
        code.append("(");
        code.append(target);
        code.append(", \"");
        code.append(node.get("name"));
        code.append("\"");
        for (int i = 1; i < node.getNumChildren(); i++) {
            var child = visit(node.getJmmChild(i));
            computation.append(child.getComputation());
            code.append(", ");
            code.append(child.getCode());
        }
        code.append(")");
        String returnType = "";
        if(node.getParent().isInstance(RETURN_STMT)){
            var outerMethod = node.getAncestor(METHOD_DECL);
            var outerMethodName = outerMethod.get().get("name");
            returnType = OptUtils.toOllirType(table.getReturnType(outerMethodName));
        }
        else if(node.getParent().isInstance(ASSIGN_STMT)){
            var lhs = node.getParent().getJmmChild(0);
            var lhsType = TypeUtils.getExprType(lhs, table);
            returnType = OptUtils.toOllirType(lhsType);
        }
        else if(node.getParent().isInstance(EXPR_SEMI) || node.getParent().isInstance(EXPR_MEMBER_CALL)){
            if(table.getMethods().contains(methodName)){
                returnType = OptUtils.toOllirType(table.getReturnType(methodName));
            }
            else if(table.getImports().contains("[" + target + "]") || instaceImports.contains(target)){
                returnType = OptUtils.toOllirType(new Type("void", false));
            }
            else {
                returnType = OptUtils.toOllirType(new Type("void", false));
            }
        } else if(node.getParent().isInstance(AND_EXPR)){
            var type = new Type("boolean", false);
            returnType = OptUtils.toOllirType(type);
        }
        else {
            var temp = OptUtils.getTemp();
            computation = new StringBuilder(temp);
            returnType = OptUtils.toOllirType(table.getReturnType(methodName));
            computation.append(returnType).append(SPACE).append(ASSIGN).append(returnType).append(SPACE);
            computation.append(code);
            computation.append(returnType);
            computation.append(END_STMT);
            code = new StringBuilder(temp);
        }
        if(node.getParent().isInstance(ASSIGN_STMT) || node.getParent().isInstance(EXPR_MEMBER_CALL)){
            var temp = OptUtils.getTemp();
            computation = new StringBuilder(temp);
            computation.append(returnType).append(SPACE).append(ASSIGN).append(returnType).append(SPACE);
            computation.append(code);
            computation.append(returnType);
            computation.append(END_STMT);
            code = new StringBuilder(temp);
        }
        code.append(returnType);
        return new OllirExprResult(code.toString(), computation.toString());
    }
    private String getInvoke(JmmNode node){
        var methodName = node.get("name");
        var target = node.getJmmChild(0);
        if(table.getMethods().contains(methodName)){
            return "invokevirtual";
        }
        else if(!table.getMethods().contains(methodName)){
            var ancestor = node.getAncestor(METHOD_DECL);
            if(ancestor.isPresent()){
                var locals = table.getLocalVariables(ancestor.get().get("name"));
                for (var local : locals){
                    if(local.getName().equals(target.get("name"))){
                        return "invokevirtual";
                    }
                }
            }
        }
        if(target.isInstance(THIS_EXPR)){
            return "invokevirtual";
        }
        var ancestor = node.getAncestor(METHOD_DECL);
        if(ancestor.isPresent()){
            var parameters = table.getParameters(ancestor.get().get("name"));
            for (var param : parameters){
                if(param.getName().equals(target.get("name"))){
                    return "invokevirtual";
                }
            }
        }
        var fields = table.getFields();
        for (var field : fields){
            if(field.getName().equals(target.get("name"))){
                return "invokevirtual";
            }
        }
        var imports = table.getImports();
        for (var imp : imports){
            if(imp.equals("["+target.get("name")+"]")){
                return "invokestatic";
            }
        }
        return "invokestatic";
    }
    private OllirExprResult visitInteger(JmmNode node, Void unused) {
        var intType = new Type(TypeUtils.getIntTypeName(), false);
        String ollirIntType = OptUtils.toOllirType(intType);
        String code = node.get("value") + ollirIntType;
        return new OllirExprResult(code);
    }




    private OllirExprResult visitBinExpr(JmmNode node, Void unused) {
        var lhs = visit(node.getJmmChild(0));
        var rhs = visit(node.getJmmChild(1));

        StringBuilder computation = new StringBuilder();

        // code to compute the children
        computation.append(lhs.getComputation());
        computation.append(rhs.getComputation());

        // code to compute self
        Type resType = TypeUtils.getExprType(node, table);
        String resOllirType = OptUtils.toOllirType(resType);
        String code = OptUtils.getTemp() + resOllirType;

        computation.append(code).append(SPACE)
                .append(ASSIGN).append(resOllirType).append(SPACE)
                .append(lhs.getCode()).append(SPACE);

        Type type = TypeUtils.getExprType(node, table);
        computation.append(node.get("op")).append(OptUtils.toOllirType(type)).append(SPACE)
                .append(rhs.getCode()).append(END_STMT);

        return new OllirExprResult(code, computation);
    }


    private OllirExprResult visitVarRef(JmmNode node, Void unused) {;
        var id = node.get("name");
        Type type = TypeUtils.getExprType(node, table);
        String ollirType = OptUtils.toOllirType(type);

        String code = id + ollirType;

        return new OllirExprResult(code);
    }

    /**
     * Default visitor. Visits every child node and return an empty result.
     *
     * @param node
     * @param unused
     * @return
     */
    private OllirExprResult defaultVisit(JmmNode node, Void unused) {

        for (var child : node.getChildren()) {
            visit(child);
        }

        return OllirExprResult.EMPTY;
    }

}
