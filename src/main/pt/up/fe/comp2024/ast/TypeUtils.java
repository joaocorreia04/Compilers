package pt.up.fe.comp2024.ast;

import org.specs.comp.ollir.Element;
import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;

import java.sql.SQLOutput;

public class TypeUtils {

    private static final String INT_TYPE_NAME = "int";
    private static final String BOOL_TYPE_NAME = "boolean";

    public static String getIntTypeName() {
        return INT_TYPE_NAME;
    }

    public static Type getBooleanType() {
        return new Type(BOOL_TYPE_NAME, false);
    }

    public static Type getIntType() {
        return new Type(INT_TYPE_NAME, false);
    }

    public static Type getReturnType(JmmNode methodDecl, SymbolTable table) {
        return getExprType(methodDecl.getChild(0), table);
    }

    //get return type of a method
//    public static Type getMethodReturnType(JmmNode methodDecl, SymbolTable table) {
//        Type returnType = new Type("void", false);
//        for (JmmNode child : methodDecl.getChildren()) {
//            if (child.getKind().equals(Kind.TYPE.toString())) {
//                returnType = new Type(child.get("name"), false);
//                if (child.get("isArray") != null && Boolean.parseBoolean(child.get("isArray"))) {
//                    returnType = new Type(returnType.getName(), true);
//                }
//            }
//        }
//        return returnType;
//    }

    public static Type getArrayType(Type elementType) {
        return new Type(elementType.getName(), false);
    }

    public static Type getArrayValuesExprType(JmmNode arrayExpr, SymbolTable table){
        var values = arrayExpr.getChildren();
        if(values.isEmpty()){
            return new Type("int", false);
        }
        Type type = getExprType(values.get(0), table);
        for(int i =1 ; i<values.size(); i++ ){
            Type currentType = getExprType(values.get(i), table);
            if(!areTypesAssignable(currentType, type, table)){
                throw new RuntimeException("Array values must be of the same type");
            }
        }
        return new Type(type.getName(), false);
    }

    public static Type getArrayType2(Type elementType) {
        return new Type(elementType.getName(), true);
    }

    /**
     * Gets the {@link Type} of an arbitrary expression.
     *
     * @param expr
     * @param table
     * @return
     */
    public static Type getExprType(JmmNode expr, SymbolTable table) {
        var kind = Kind.fromString(expr.getKind());

        Type type = switch (kind) {
            case BINARY_EXPR -> getBinExprType(expr);
            case VAR_REF_EXPR -> getVarExprType(expr, table);
            case INTEGER_LITERAL -> getIntType();
            case ARRAY_ACCESS_EXPR -> getIntType();//getArrayValuesExprType(expr, table);
            case ARRAY_EXPR -> new Type ("int", true);//getArrayValuesExprType(expr,table);//getArrayType2(getExprType(expr.getChild(0), table));
            case EXPR_NEW_ARRAY -> getArrayType2(getExprType(expr.getChild(0), table));
            case BOOL_LITERAL, FALSE_EXPR, TRUE_EXPR -> getBooleanType();
            case CONDITIONAL_EXPR -> new Type(BOOL_TYPE_NAME, false);
            case NEW_CLASS -> new Type (expr.get("name"), false);
            case THIS_EXPR -> new Type(table.getClassName(), false);
            case EXPR_MEMBER_CALL -> getMethodCallOnObjectExprType(expr, table);
            case PARENT_EXPR -> getExprType(expr.getChild(0), table);
            case METHOD_CALL -> getMethodCallExprType(expr, table);
            case PARAM -> new Type(expr.getChild(0).get("name"), false);
            case TYPE -> new Type(expr.get("name"), expr.get("isArray").equals("true"));
            case NOT_EXPR -> new Type(BOOL_TYPE_NAME, false);
            case RETURN_STMT -> getReturnType(expr, table);
            case LENGTH_CALL_EXPR -> new Type(INT_TYPE_NAME, false);
            default -> throw new UnsupportedOperationException("Can't compute type for expression kind '" + kind + "'");
        };

        return type;
    }

    private static Type getMethodCallOnObjectExprType(JmmNode methodCallOnObjectExpr, SymbolTable table) {
        String methodName = methodCallOnObjectExpr.get("name");
        var obj = methodCallOnObjectExpr.getChildren().get(0);

        Type objType = getExprType(obj, table);

        if(objType.getName().equals(table.getClassName())){
            for (String method : table.getMethods()) {
                if (method.equals(methodName))
                    return table.getReturnType(method);
            }
            if(!table.getSuper().isEmpty()){
                return new Type("imported", false);
            }
        } else if(objType.getName().equals("imported")){
            return new Type("imported", false);
        } else{
            var imports = table.getImports();
            for (String imp : imports) {
                if (imp.equals("["+objType.getName()+"]")) {
                    return new Type("imported", false);
                }
            }
        }



        throw new RuntimeException("Method '" + methodName + "' not found in symbol table");
    }

    private static Type getMethodCallExprType(JmmNode methodCallExpr, SymbolTable table) {
        String methodName = methodCallExpr.get("name");

        JmmNode parent = methodCallExpr.getJmmParent();
        while (!parent.getKind().equals(Kind.METHOD_DECL.toString())) {
            parent = parent.getJmmParent();
        }

        for (String method : table.getMethods()) {
            if (method.equals(methodName))
                return table.getReturnType(method);
        }
        throw new RuntimeException("Method '" + methodName + "' not found in symbol table");
    }

    private static Type getBinExprType(JmmNode binaryExpr) {
        String operator = binaryExpr.get("op");

        return switch (operator) {
            case "+", "*" , "-", "/" -> new Type(INT_TYPE_NAME, false);
            case "!", "&&", "<" -> new Type(BOOL_TYPE_NAME, false);
            default -> throw new RuntimeException("Unknown operator '" + operator + "' of expression '" + binaryExpr + "'");
        };
    }

    private static Type getVarExprType(JmmNode varRefExpr, SymbolTable table) {
        String varName = varRefExpr.get("name");
        JmmNode parent = varRefExpr.getParent();

        while(!parent.getKind().equals(Kind.METHOD_DECL.toString())){
            parent = parent.getJmmParent();
        }
        for(Symbol local : table.getLocalVariables(parent.get("name"))){
            if(local.getName().equals(varName)){
                return local.getType();
            }
        }

        for(Symbol param : table.getParameters(parent.get("name"))){
            if(param.getName().equals(varName)){
                return param.getType();
            }
        }

        for ( Symbol field: table.getFields() ) {
            if (field.getName().equals(varName)) {
                return field.getType();
            }
        }

        var imports = table.getImports();
        for(String imp : imports){
            if(imp.contains(varName)){
                return new Type("", false);
            }
        }
        throw new RuntimeException("Variable '" + varName + "' not found in symbol table");

    }

    /**
     * @param sourceType
     * @param destinationType
     * @return true if sourceType can be assigned to destinationType
     */
    public static boolean areTypesAssignable(Type sourceType, Type destinationType, SymbolTable table) {
        if(sourceType.getName().equals(destinationType.getName())){
            boolean isArray = sourceType.isArray() == destinationType.isArray();
            var array_vararg = (!sourceType.isArray() && destinationType.hasAttribute("vararg"));
            return isArray || array_vararg;
        }


        if(table.getClassName().equals(sourceType.getName())){
            return destinationType.getName().equals(table.getSuper());
        }
        var imports = table.getImports();
        var sImp=false;
        var dImp=false;
        for (String imp : imports) {
            if (imp.equals("["+sourceType.getName()+"]")) {
                sImp=true;
            }
            if (imp.equals("["+destinationType.getName()+"]")) {
                dImp=true;
            }
        }

        if(sourceType.getName().equals("imported") || (sImp && destinationType.getName().equals("imported"))){
            return true;
        }

        return sImp && dImp;
    }
}
