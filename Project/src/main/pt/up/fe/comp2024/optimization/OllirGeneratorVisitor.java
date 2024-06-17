package pt.up.fe.comp2024.optimization;

import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.comp2024.ast.TypeUtils;

import java.util.ArrayList;
import java.util.List;

import static pt.up.fe.comp2024.ast.Kind.*;

/**
 * Generates OLLIR code from JmmNodes that are not expressions.
 */
public class OllirGeneratorVisitor extends AJmmVisitor<Void, String> {

    private static final String SPACE = " ";
    private static final String ASSIGN = ":=";
    private final String END_STMT = ";\n";
    private final String NL = "\n";
    private final String L_BRACKET = " {\n";
    private final String R_BRACKET = "}\n";

    private final SymbolTable table;

    private final OllirExprGeneratorVisitor exprVisitor;

    public OllirGeneratorVisitor(SymbolTable table) {
        this.table = table;
        exprVisitor = new OllirExprGeneratorVisitor(table);
    }


    @Override
    protected void buildVisitor() {

        addVisit(PROGRAM, this::visitProgram);
        addVisit(CLASS_DECL, this::visitClass);
        addVisit(METHOD_DECL, this::visitMethodDecl);
        addVisit(PARAM, this::visitParam);
        addVisit(RETURN_STMT, this::visitReturn);
        addVisit(ASSIGN_STMT, this::visitAssignStmt);
        addVisit(IMPORT_DECL, this::visitImport);
        addVisit(VAR_DECL, this::visitVarDecl);
        addVisit(EXPR_SEMI, this::visitExprSemi);
        addVisit(IF_ELSE_STMT, this::visitIfElse);
        addVisit(WHILE_STMT, this::visitWhile);

        setDefaultVisit(this::defaultVisit);
    }

    private String visitIfElse(JmmNode node, Void unused){
        StringBuilder code = new StringBuilder();

        var condition = exprVisitor.visit(node.getJmmChild(0));
        var thenBlock = node.getJmmChild(1);
        var elseBlock = node.getJmmChild(2);

        var ifNum = OptUtils.getCurrentIf();

        code.append(condition.getComputation());
        code.append("if(");
        code.append(condition.getCode());
        code.append(") goto if_then_");
        code.append(ifNum);
        code.append(END_STMT);

        for (var child : thenBlock.getChildren()) {
            code.append(visit(child));
        }

        code.append("goto if_end_");
        code.append(ifNum);
        code.append(END_STMT);

        code.append("if_then_");
        code.append(ifNum);
        code.append(":\n");

        for (var child : elseBlock.getChildren()) {
            code.append(visit(child));
        }

        code.append("if_end_");
        code.append(ifNum);
        code.append(":\n");

        return code.toString();
    }

    private String visitWhile(JmmNode node, Void unused){

        String code = "";

        var condition = exprVisitor.visit(node.getJmmChild(0));
        var block = node.getJmmChild(1);
        var whileNum = OptUtils.getCurrentWhile();

        code += "goto ";
        code += "while_cond";
        code += whileNum;
        code += END_STMT;

        code += "while_body_";
        code += whileNum;
        code += ":\n";

        for (var child : block.getChildren()) {
            code += visit(child);
        }

        code += "while_cond_";
        code +=  whileNum;
        code += ":\n";

        code += condition.getComputation();
        code += "if (";
        code += condition.getCode();
        code += ") goto while_body_";
        code += whileNum;
        code += END_STMT;


        return code;
    }


    private String visitExprSemi(JmmNode node, Void unused){
        StringBuilder code = new StringBuilder();
        var visitor = exprVisitor.visit(node.getJmmChild(0));
        code.append(visitor.getComputation());
        code.append(visitor.getCode());
        code.append(END_STMT);
        return code.toString();
    }
    private String visitVarDecl(JmmNode node, Void unused) {

        StringBuilder code = new StringBuilder("");
        var parentKind = Kind.fromString(node.getParent().getKind());
        if(parentKind == CLASS_DECL){
            code.append(".field public ");
            code.append(node.get("name"));
            var type = TypeUtils.getExprType(node.getJmmChild(0), table);
            code.append(OptUtils.toOllirType(type));
            code.append(";"+ NL);
        }
        return code.toString();
    }
    private String visitImport(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();
        code.append("import " + node.get("imports").replaceAll("[\\[\\]]", "").replace(", ", "."));
        code.append(END_STMT);
        return code.toString();
    }
    private String visitAssignStmt(JmmNode node, Void unused) {

        var left = node.getJmmChild(0);

//        System.out.println("NODE in ASSIGN: " + node);
//        System.out.println("children: " + node.getChildren());

        var leftSide = node.getJmmChild(0);

        if(leftSide.getKind().equals(ARRAY_ACCESS_EXPR.toString())){
            return visitArrayAssign(node, unused);
        }

        var lhs = exprVisitor.visit(node.getJmmChild(0));
        var rhs = exprVisitor.visit(node.getJmmChild(1));

        StringBuilder code = new StringBuilder();

        // code to compute the children
        code.append(lhs.getComputation());
        code.append(rhs.getComputation());



        Type type = TypeUtils.getExprType(left, table);
        String leftType = OptUtils.toOllirType(type);

        var aux = node;

        while(!aux.getKind().equals(METHOD_DECL.toString())){
            aux = aux.getParent();
        }

        var method = aux.get("name");
        var methoodVars = table.getLocalVariables(method);

        ArrayList<String> vars = new ArrayList();

        for(var var: methoodVars){
            vars.add(var.getName());
        }

        boolean isField = false;

        for (var symbol : table.getFields()){
            if(left.hasAttribute("name")){
                if(symbol.getName().equals(left.get("name")) && !vars.contains(left.get("name"))){
                    code.append("putfield(this." + table.getClassName() + ", " + left.get("name") + leftType + ", " + rhs.getCode() + ").V;\n");
                    isField = true;
                }
            }
        }


        // code to compute self
        // statement has type of lhs
        Type thisType = TypeUtils.getExprType(node.getJmmChild(0), table);
        String typeString = OptUtils.toOllirType(thisType);


        if(!isField) {
            code.append(lhs.getCode());
            code.append(SPACE);

            code.append(ASSIGN);
            code.append(typeString);
            code.append(SPACE);


            code.append(rhs.getCode());

            code.append(END_STMT);
        }


        return code.toString();
    }

    private String codeToAppend(JmmNode node, String code){
        return switch (node.getKind()){
            case "IntegerLiteral" -> node.get("value") + ".i32";
            default -> code;
        };
    }

    private String visitArrayAssign(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();
        var childLeft1 = node.getJmmChild(0).getJmmChild(0);
        var childLeft2 = node.getJmmChild(0).getJmmChild(1);
        var childRight = node.getJmmChild(1);

//        System.out.println("CHILD 0 : " + node.getChild(0));
//        System.out.println("CHILD 2 : " + node.getChild(1));

        var lhs = exprVisitor.visit(childLeft2);
        var rhs = exprVisitor.visit(childRight);

        code.append(lhs.getComputation());
        code.append(rhs.getComputation());

        String aux = codeToAppend(node.getJmmChild(0), lhs.getCode());

        String aux1 = codeToAppend(node.getJmmChild(1), rhs.getCode());

        var ollirType = OptUtils.toOllirType(TypeUtils.getExprType(node.getJmmChild(0), table));

        code.append(node.getChild(0).getChild(0).get("name"));
        code.append("[");
        code.append(aux);
        code.append("]");
        code.append(ollirType);
        code.append(" :=");
        code.append(ollirType);
        code.append(SPACE);
        code.append(aux1);
        code.append(END_STMT);

        return code.toString();
    }


    private String visitReturn(JmmNode node, Void unused) {

        String methodName = node.getAncestor(METHOD_DECL).map(method -> method.get("name")).orElseThrow();
        Type retType = table.getReturnType(methodName);

        StringBuilder code = new StringBuilder();

        var expr = OllirExprResult.EMPTY;

        if (node.getNumChildren() > 0) {
            expr = exprVisitor.visit(node.getJmmChild(0));
        }

        code.append(expr.getComputation());
        code.append("ret");
        code.append(OptUtils.toOllirType(retType));
        code.append(SPACE);

        code.append(expr.getCode());

        code.append(END_STMT);

        return code.toString();
    }


    private String visitParam(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();
        var typeCode = OptUtils.toOllirType(node.getJmmChild(0));
        var name = node.get("name");
        code.append(name);
        code.append(typeCode);
        return code.toString();
    }


    private String visitMethodDecl(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder(".method ");

        boolean isPublic = NodeUtils.getBooleanAttribute(node, "isPublic", "false");
        boolean isMain = NodeUtils.getBooleanAttribute(node, "isMain", "false");

        if (isPublic) {
            code.append("public ");
        }

        var name = node.get("name");
        var paramCode = "";
        var retType = "";
        var returnStm = "";

        if(isMain) {
            code.append("static ");
            code.append(name);
            paramCode = "args.array.String";
            retType = ".V";
            returnStm = "ret.V;";
        }
        else{
            code.append(name);
            if (!node.getChildren(PARAM).isEmpty()) {
                paramCode = node.getChildren(PARAM).stream()
                        .map(this::visit)
                        .reduce((a, b) -> a + ", " + b)
                        .orElseThrow();
            } else {
                if (node.getChildren(PARAM).size() == 1) {
                    node.getChildren(PARAM).get(0).getKind();
                }
            }
            Type returnType = TypeUtils.getExprType(node.getChildren(TYPE).get(0), table);
            retType = OptUtils.toOllirType(returnType);
        }
        code.append("(" + paramCode + ")");
        code.append(retType);
        code.append(L_BRACKET);

        List<JmmNode> params = node.getChildren(PARAM);
        for (JmmNode child : node.getChildren()) {
            if (!params.contains(child)) {
                var childCode = visit(child);
                code.append(childCode);
            }
        }
        if(isMain){
            code.append(returnStm);
            code.append(NL);
        }
        code.append(R_BRACKET);
        code.append(NL);

        return code.toString();
    }


    private String visitClass(JmmNode node, Void unused) {
        System.out.println(node.toTree());
        StringBuilder code = new StringBuilder();
        code.append(table.getClassName());
        if(table.getSuper() != null){
            code.append(" extends ");
            code.append(table.getSuper());
        }
        else {
            code.append(" extends Object");
        }
        code.append(L_BRACKET);
        code.append(NL);

        code.append(NL);
        var needNl = true;

        for (var child : node.getChildren()) {
            var result = visit(child);

            if (METHOD_DECL.check(child) && needNl) {
                code.append(NL);
                needNl = false;
            }

            code.append(result);
        }
        code.append(buildConstructor());
        code.append(R_BRACKET);

        return code.toString();
    }

    private String buildConstructor() {

        return ".construct " + table.getClassName() + "().V {\n" +
                "invokespecial(this, \"<init>\").V;\n" +
                "}\n";
    }


    private String visitProgram(JmmNode node, Void unused) {

        StringBuilder code = new StringBuilder();

        node.getChildren().stream()
                .map(this::visit)
                .forEach(code::append);
        return code.toString();
    }

    /**
     * Default visitor. Visits every child node and return an empty string.
     *
     * @param node
     * @param unused
     * @return
     */
    private String defaultVisit(JmmNode node, Void unused) {

        for (var child : node.getChildren()) {
            visit(child);
        }

        return "";
    }
}
