package pt.up.fe.comp2024.backend;

import org.specs.comp.ollir.*;
import org.specs.comp.ollir.tree.TreeNode;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.jasmin.JasminUtils;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import pt.up.fe.specs.util.exceptions.NotImplementedException;
import pt.up.fe.specs.util.utilities.StringLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static pt.up.fe.comp2024.ast.TypeUtils.getExprType;

/**
 * Generates Jasmin code from an OllirResult.
 * <p>
 * One JasminGenerator instance per OllirResult.
 */
public class JasminGenerator {

    private static final String NL = "\n";
    private static final String TAB = "   ";

    private final OllirResult ollirResult;

    List<Report> reports;

    Method currentMethod;

    String code;

    int stackValue;
    int maxStackValue;

    Set<Integer> locals = new HashSet<>();

    private final FunctionClassMap<TreeNode, String> generators;

    public JasminGenerator(OllirResult ollirResult) {

        this.ollirResult = ollirResult;
        this.generators = new FunctionClassMap<>();

        reports = new ArrayList<>();
        currentMethod = null;

        generators.put(ClassUnit.class, this::generateClassUnit);
        generators.put(Method.class, this::generateMethod);
        generators.put(AssignInstruction.class, this::generateAssign);
        generators.put(PutFieldInstruction.class, this::generatePutField);
        generators.put(GetFieldInstruction.class, this::generateGetField);
        generators.put(SingleOpInstruction.class, this::generateSingleOp);
        generators.put(LiteralElement.class, this::generateLiteral);
        generators.put(Operand.class, this::generateOperand);
        generators.put(BinaryOpInstruction.class, this::generateBinaryOp);
        generators.put(ReturnInstruction.class, this::generateReturn);
        generators.put(CallInstruction.class, this::generateCall);
        generators.put(SingleOpCondInstruction.class, this::generateSingleOpCond);
        generators.put(GotoInstruction.class, this::generateGoto);
        generators.put(UnaryOpInstruction.class, this::generateUnary);
        generators.put(OpCondInstruction.class, this::generateOpCond);
        generators.put(ArrayOperand.class, this::generateArrayOp);
        
    }

    private void changeStack(int val){
        stackValue = stackValue + val;
        if (stackValue > maxStackValue) maxStackValue = stackValue;
    }

    private void updateLocals(int values){
        for (int i = 0; i < values; i++) locals.add(i);
    }
    
    private String generateArrayOp(ArrayOperand arrayOp) {
        String code = "";

        var r = currentMethod.getVarTable().get(arrayOp.getName()).getVirtualReg();
        code += getSpaceor_("aload", r);
        changeStack(1);
        locals.add(r);

        var indexOps = arrayOp.getIndexOperands();

        for (var op : indexOps) {
            code += generators.apply(op);
        }

        code += "iaload" + NL;
        changeStack(-1);
        return code;
    }

    private String generateOpCond(OpCondInstruction opCondInst) {
        String code = "";

        var condInst = opCondInst.getCondition();
        var condInstCode = generateCondition(condInst);
        var label = opCondInst.getLabel();

        code += condInstCode + label + NL;


        return code;
    }

    private String generateCondition(OpInstruction op) {
        String code = "";

        var leftOperand = ((BinaryOpInstruction) op).getLeftOperand();
        var rightOperand = ((BinaryOpInstruction) op).getRightOperand();

        var leftCode = generators.apply(leftOperand);
        var rightCode = generators.apply(rightOperand);

        code += leftCode;
        code += rightCode;

        var operationType = op.getOperation().getOpType();

        switch (operationType) {
            case LTH -> code += "isub" + NL + "iflt ";
            case GTE -> code += "isub" + NL + "ifge ";
            default -> throw new NotImplementedException(operationType);
        }

        changeStack(-1);

        return code;
    }

    private String generateUnary(UnaryOpInstruction unary) {
        String code = "";

        var operand = unary.getOperand();
        var operCode = generators.apply(operand);
        code += operCode;

        code += "iconst_1" + NL;
        code += "ixor" + NL;

        return code;
    }

    private String generateGoto(GotoInstruction gotoInst) {
        return "goto " + gotoInst.getLabel() + NL;
    }

    private String generateSingleOpCond(SingleOpCondInstruction singleOpCond) {
        String code = "";

        code += generators.apply(singleOpCond.getCondition().getSingleOperand());
        code += "ifne " + singleOpCond.getLabel() + NL;

        changeStack(-1);

        return code;
    }

    private String generatePutField(PutFieldInstruction putField){

        String code = "";
        var node0 = putField.getChildren().get(0);
        var node1 = putField.getChildren().get(1);
        var node2 = putField.getChildren().get(2);
        code += generators.apply(node0) + generators.apply(node2);

        String name = ((Operand) node1).getName();
        String className = ollirResult.getOllirClass().getClassName();
        String type = putField.getField().getType().toString();
        if(type.contains("[]")){
            type = type.substring(0,type.length() - 2);
            type += "[" + getJasminTypeDescriptor(type);
        }
        else type = getJasminTypeDescriptor(type);

        changeStack(-2);
        code += "putfield " + className + "/" + name + " " + type;
        return code;
    }

    private String generateGetField(GetFieldInstruction getField){

        String code = "";
        var node0 = getField.getChildren().get(0);
        var node1 = getField.getChildren().get(1);
        code += generators.apply(node0);

        String name = ((Operand) node1).getName();
        String className = ollirResult.getOllirClass().getClassName();
        String type = getField.getField().getType().toString();
        if(type.contains("[]")){
            type = type.substring(0,type.length() - 2);
            type += "[" + getJasminTypeDescriptor(type);
        }
        else type = getJasminTypeDescriptor(type);

        code += "getfield " + className + "/" + name + " " + type + NL;
        return code;
    }

    private String generateCall(CallInstruction call){
        String code = "";
        if (call.getInvocationType() == CallType.invokestatic){
            var args = call.getArguments();

            for (int i = 0; i < args.size(); i++){
                code += generators.apply(args.get(i));
            }
            changeStack(-args.size());

            code += "invokestatic ";

            var caller = call.getCaller().toString();
            var method = call.getMethodName().toString();

            // get text in between ":" and "."
            code += caller.substring(caller.indexOf(":") + 2, caller.indexOf("."));
            // get method name in between \" \"

            code += "/" + method.substring(method.indexOf("\"") + 1, method.lastIndexOf("\"")) + "(";

            for (int i = 0; i < args.size(); i++){
                var arg = args.get(i);
                code += getJasminTypeDescriptor(arg.getType().toString());
            }

            code += ")" + getJasminTypeDescriptor(call.getReturnType().toString()) + NL;

        }
        else if (call.getInvocationType() == CallType.invokevirtual){
            var caller = call.getCaller().toString();
            var var = caller.substring(caller.indexOf(" ") + 1  , caller.indexOf("."));
            var r = currentMethod.getVarTable().get(var).getVirtualReg();


            code += generators.apply(call.getOperands().get(0));

            if(currentMethod.isConstructMethod()){
                code += "\tinvokevirtual" + call.getClass().getSuperclass() + "/<init>()V\n" + "\treturn\n";
            }
            else {
                var args = call.getArguments();
                for (int i = 0; i < args.size(); i++) {
                    code += generators.apply(args.get(i));
                }
                changeStack(-args.size());
                code += "invokevirtual ";
                var callerType = call.getCaller().getType().toString();
                var method = call.getMethodName().toString();
                // get text in between ":" and "."
                code += callerType.substring(callerType.indexOf("(") + 1, callerType.indexOf(")"));
                // get method name in between \" \"

                code += "/" + method.substring(method.indexOf("\"") + 1, method.lastIndexOf("\"")) + "(";

                for (int i = 0; i < args.size(); i++) {
                    var arg = args.get(i).getType().toString();
                    if(arg.contains("[]")){
                        arg = arg.substring(0, arg.length()-2);
                        code += "[" + getJasminTypeDescriptor(arg);
                    }
                    else code += getJasminTypeDescriptor(arg);
                }

                code += ")" + getJasminTypeDescriptor(call.getReturnType().toString()) + NL;
            }
        }
        else if (call.getReturnType().toString() == "VOID" || call.getInvocationType() == CallType.invokespecial){
            var operands = call.getOperands();
            changeStack(-operands.size());
            for (int i = 0; i < operands.size(); i++){
                var op = operands.get(i);
                if (op.isLiteral()){
                    code += "invokespecial ";

                    Pattern pattern = Pattern.compile("\\((.*?)\\)");

                    Matcher matcher = pattern.matcher(call.getCaller().toString());

                    if (matcher.find()) code += matcher.group(1) + "/";

                    code += generators.apply(op);

                    code += "()" + getJasminTypeDescriptor(call.getReturnType().toString()) + NL;
                }
                else code += generators.apply(op);
            }
        }
        else if (call.getInvocationType() == CallType.NEW){
            if (call.getReturnType().getTypeOfElement() == ElementType.ARRAYREF) {
                var count = call.getOperands().get(1);
                var countCode = generators.apply(count);
                code += countCode;
                code += "newarray int" + NL;
            }
            else {
                changeStack(1);
                code += "new ";
                Pattern pattern = Pattern.compile("\\((.*?)\\)");

                Matcher matcher = pattern.matcher(call.getReturnType().toString());

                String retType =((ClassType) call.getReturnType()).getName();

                if (matcher.find()) code += matcher.group(1) + NL;
                code += "dup\n";
                changeStack(1);
            }
        }
        else if (call.getInvocationType() == CallType.arraylength) {
            var caller = ((Operand) call.getCaller()).getName();
            var r = currentMethod.getVarTable().get(caller).getVirtualReg();
            code += getSpaceor_("aload", r);
            changeStack(1);
            locals.add(r);

            code += "arraylength" + NL;
        }

        return code;
    }

    public List<Report> getReports() {
        return reports;
    }

    public String build() {
        // This way, build is idempotent
        if (code == null) {
            code = generators.apply(ollirResult.getOllirClass());
        }

        System.out.println(code);

        return code;
    }


    private String generateClassUnit(ClassUnit classUnit) {

        var code = new StringBuilder();
        var accMod = "";

        // generate class name
        if(ollirResult.getOllirClass().getClassAccessModifier().toString() == "DEFAULT") accMod = "public ";

        var className = ollirResult.getOllirClass().getClassName();
        code.append(".class ").append(accMod).append(className).append(NL).append(NL);

        String superClass;
        superClass = ollirResult.getOllirClass().getSuperClass();

        if(superClass == null || superClass.equals("Object")) code.append(".super java/lang/Object").append(NL);
        else code.append(".super ").append(superClass).append(NL);

        for (int i = 0; i < classUnit.getNumFields(); i++){
            var field = classUnit.getField(i);
            var fieldType = "NULL";

            fieldType = getJasminTypeDescriptor(field.getFieldType().toString());

            //String f;
            //if (field.getFieldAccessModifier().toString() == "DEFAULT") f = field.getFieldAccessModifier().name().toLowerCase() + " ";
            //else f = "";

            String acc = "";
            if(field.getFieldAccessModifier() != AccessModifier.DEFAULT){
                acc = field.getFieldAccessModifier().toString().toLowerCase();
            }

            code.append(".field ").append(acc).append(" ").append(field.getFieldName()).append(" ").append(fieldType).append(NL);
        }



        // generate a single constructor method
        var path = new StringBuilder();
        if (superClass == null || superClass.equals("Object")) path.append("java/lang/Object/");
        else path.append(superClass + "/");

        var defaultConstructor = "";
        if (ollirResult.getOllirClass().getSuperClass() == null) {
            defaultConstructor = """
                    ;default constructor
                    .method public <init>()V
                         aload_0
                         invokespecial java/lang/Object/<init>()V
                         return
                    .end method
                    """;
            code.append(defaultConstructor);
        }
        else {

            String aux = path + "<init>()V" + NL;
            defaultConstructor =
                    """
                    .method public <init>()V
                        aload_0
                        invokespecial """;
            defaultConstructor += " " + aux +
                    """
                        return
                    .end method
                    """;

            code.append(defaultConstructor);
        }

        // generate code for all other methods
        for (var method : ollirResult.getOllirClass().getMethods()) {

            // Ignore constructor, since there is always one constructor
            // that receives no arguments, and has been already added
            // previously
            if (method.isConstructMethod()) {
                continue;
            }

            code.append(generators.apply(method));
        }

        return code.toString();
    }

    public static Map<String, String> javaToJasminTypeMap = new HashMap<>();

    static {
        // Primitive types
        javaToJasminTypeMap.put("INT32", "I");
        javaToJasminTypeMap.put("INT", "I");
        javaToJasminTypeMap.put("LONG", "J");
        javaToJasminTypeMap.put("FLOAT", "F");
        javaToJasminTypeMap.put("DOUBLE", "D");
        javaToJasminTypeMap.put("BOOLEAN", "Z");
        javaToJasminTypeMap.put("BYTE", "B");
        javaToJasminTypeMap.put("CHAR", "C");
        javaToJasminTypeMap.put("SHORT", "S");
        // Reference types
        javaToJasminTypeMap.put("java/lang/Object", "Ljava/lang/Object");
        javaToJasminTypeMap.put("STRING", "Ljava/lang/String;");
        // Void type
        javaToJasminTypeMap.put("VOID", "V");
    }

    public static String getJasminTypeDescriptor(String javaType) {
        return javaToJasminTypeMap.getOrDefault(javaType, "I");
    }

    private String generateMethod(Method method) {

        // set method
        currentMethod = method;

        var code = new StringBuilder();
        var finalCode = new StringBuilder();
        stackValue = 1;
        maxStackValue = 1;
        locals.clear();
        updateLocals(method.getParams().size() + 1);

        // calculate modifier
        var modifier = method.getMethodAccessModifier() != AccessModifier.DEFAULT ?
                method.getMethodAccessModifier().name().toLowerCase() + " " :
                "";

        if (method.isFinalMethod()) modifier += "final ";
        if (method.isStaticMethod()) modifier += "static ";

        var methodName = method.getMethodName();

        // TODO: Hardcoded param types and return type, needs to be expanded

        var params = currentMethod.getParams();
        String finalParams = "(";
        for(int i = 0; i < params.size(); i++){
            var l = currentMethod.getParam(i).getType().toString();
            if(l.contains("[]")){
                l = l.substring(0, l.length()-2);
                finalParams += "[" + getJasminTypeDescriptor(l);
            }
            else finalParams += getJasminTypeDescriptor(l);
        }
        finalParams += ")";

        String rT = currentMethod.getReturnType().toString();

        if(currentMethod.getReturnType().getTypeOfElement() == ElementType.OBJECTREF){
            rT = "L" + ollirResult.getOllirClass().getClassName() + ";";
            finalParams += rT;
        }
        else if(rT.contains("[]")){
            rT = rT.substring(0,rT.length() - 2);
            finalParams += "[" + getJasminTypeDescriptor(rT);
        }
        else finalParams += getJasminTypeDescriptor(rT);

        code.append("\n.method ").append(modifier).append(methodName).append(finalParams).append(NL);


        for (int i = 0; i < method.getInstructions().size(); i++) {

            var inst = method.getInstr(i);
            String instCode = "";

            var labelList = method.getLabels(inst);

            if (labelList.size() != 0) {
                for (var label : labelList) {
                    instCode += label + ":" + NL;
                }
            }

            instCode += StringLines.getLines(generators.apply(inst)).stream().collect(Collectors.joining(NL + TAB, TAB, NL));


            finalCode.append(instCode);


            if (inst instanceof CallInstruction) {
                var returnType = ((CallInstruction) inst).getReturnType().getTypeOfElement();
                CallType typeCall = ((CallInstruction) inst).getInvocationType();

                if ((!method.isConstructMethod()) && (returnType != ElementType.VOID)) {
                    finalCode.append(TAB).append("pop").append(NL);
                    changeStack(-1);
                }
            }

        }

        code.append(TAB).append(".limit stack " + maxStackValue).append(NL);
        code.append(TAB).append(".limit locals " + locals.size()).append(NL);

        code.append(finalCode);

        code.append(".end method\n");

        // unset method
        currentMethod = null;

        return code.toString();
    }


    private String generateAssign(AssignInstruction assign) {
        var code = new StringBuilder();

        // store value in the stack in destination
        var lhs = assign.getDest();
        var rhs = assign.getRhs();

        if (!(lhs instanceof Operand)) {
            throw new NotImplementedException(lhs.getClass());
        }

        var loperand = (Operand) lhs;

        // get register
        var reg = currentMethod.getVarTable().get(loperand.getName()).getVirtualReg();
        if(rhs.getInstType() == InstructionType.BINARYOPER) {
            BinaryOpInstruction BOp = (BinaryOpInstruction) rhs;
            OperationType opType = BOp.getOperation().getOpType();
            if (opType == OperationType.ADD || opType == OperationType.SUB || opType == OperationType.MUL || opType == OperationType.DIV) {
                String sign = "";
                if (opType == OperationType.SUB) sign = "-";
                Element op1 = BOp.getLeftOperand();
                Element op2 = BOp.getRightOperand();
                if (!op1.isLiteral() && op2.isLiteral()) {
                    Operand f = (Operand) op1;
                    Operand l = (Operand) lhs;

                    int num = Integer.parseInt(sign + ((LiteralElement) op2).getLiteral());
                    if (f.getName() == l.getName() && num >= -128 && num <= 127)
                        return "iinc " + reg + " " + num + '\n';
                } else if (op1.isLiteral() && !op2.isLiteral()) {
                    Operand s = (Operand) op2;
                    Operand l = (Operand) lhs;

                    int num = Integer.parseInt(sign + ((LiteralElement) op1).getLiteral());
                    if (s.getName() == l.getName() && num >= -128 && num <= 127)
                        return "iinc " + reg + " " + num + '\n';
                }
            }

        }

        String Srhs = generators.apply(rhs);
        ElementType ltype = lhs.getType().getTypeOfElement();
        if (ltype == ElementType.INT32 || ltype == ElementType.BOOLEAN || ltype == ElementType.STRING) {
            Descriptor aux  = currentMethod.getVarTable().get(loperand.getName());
            ElementType aux1 = aux.getVarType().getTypeOfElement();
            if (aux1 == ElementType.ARRAYREF ) {
                Element f = ((ArrayOperand) lhs).getIndexOperands().get(0);
                if(f.getClass() != LiteralElement.class) {
                    var fOp = (Operand) f;
                    int reg1 = (currentMethod.getVarTable()).get(fOp.getName()).getVirtualReg();
                    code.append(getSpaceor_("aload", reg));
                    changeStack(1);
                    locals.add(reg);
                    code.append(getSpaceor_("iload", reg1));
                    changeStack(1);
                    locals.add(reg1);
                    code.append(Srhs);
                    code.append("iastore\n");
                    changeStack(-3);
                }
                else{
                    code.append(getSpaceor_("aload", reg));
                    changeStack(1);
                    locals.add(reg);
                    code.append(generators.apply(f));
                    code.append(Srhs);
                    code.append("iastore\n");
                    changeStack(-3);
                }
            }
            else {
                code.append(Srhs);
                code.append(getSpaceor_("istore", reg));
                changeStack(-1);
                locals.add(reg);
            }
        } else if (ltype == ElementType.OBJECTREF || ltype == ElementType.THIS || ltype == ElementType.ARRAYREF) {
            code.append(Srhs);
            code.append(getSpaceor_("astore", reg));
            changeStack(-1);
            locals.add(reg);
        }

        return code.toString();
    }

    private String generateSingleOp(SingleOpInstruction singleOp) {
        return generators.apply(singleOp.getSingleOperand());
    }

    private String generateLiteral(LiteralElement literal) {
        if (literal.getLiteral().contains("<init>")){
            return literal.getLiteral().substring(1, literal.getLiteral().length() - 1);
        }
        else{
            changeStack(1);
            var reg = Integer.parseInt(literal.getLiteral());
            if(reg == -1){
                return "iconst_m1\n";
            }
            else if(reg >= 0 && reg <= 5){
                return "iconst_" + reg + "\n";
            }
            else if(reg >= -128 && reg <= 127){
                return "bipush " + reg + "\n";
            }
            else if(reg >= -32768 && reg <= 32767){
                return "sipush " + reg + "\n";
            }
            else {
                return "ldc " + reg + "\n";
            }
        }
    }

    private String generateOperand(Operand operand) {
        // get register
        var reg = currentMethod.getVarTable().get(operand.getName()).getVirtualReg();
        var type = operand.getType().getTypeOfElement();
        changeStack(1);
        locals.add(reg);
        if(reg < 0) return "aload_0\n";
        return switch (type) {
            case THIS -> "aload_0\n";
            case INT32, BOOLEAN -> getSpaceor_("iload", reg);
            case STRING, OBJECTREF, ARRAYREF, CLASS -> getSpaceor_("aload", reg);
            default -> throw new NotImplementedException(type);
        };
    }

    public String getSpaceor_(String instruction, int register){
        if(register >= 0 && register <= 3) return instruction + "_" + register + NL;
        else return instruction + " " + register + NL;
    }

    private String generateBinaryOp(BinaryOpInstruction binaryOp) {
        var code = new StringBuilder();

        // load values on the left and on the right
        code.append(generators.apply(binaryOp.getLeftOperand()));
        code.append(generators.apply(binaryOp.getRightOperand()));

        // apply operation
        var op = switch (binaryOp.getOperation().getOpType()) {
            case ADD -> "iadd";
            case MUL -> "imul";
            case SUB -> "isub";
            case DIV -> "idiv";
            case ANDB -> "iand";
            default -> throw new NotImplementedException(binaryOp.getOperation().getOpType());
        };
        changeStack(-1);
        code.append(op).append(NL);

        return code.toString();
    }

    private String getReturnType(Type type) {
        if(type.getTypeOfElement() == ElementType.ARRAYREF || type.getTypeOfElement() == ElementType.THIS || type.getTypeOfElement() == ElementType.OBJECTREF){
            return "areturn";
        }
        switch (type.toString()) {
            case "INT32": // int in Java
            case "BOOLEAN":
            case "BYTE":
            case "CHAR":
            case "SHORT":
                return "ireturn";
            case "INT64": // long in Java
                return "lreturn";
            case "FLOAT32": // float in Java
                return "freturn";
            case "FLOAT64": // double in Java
                return "dreturn";
            case "java/lang/String":
                return "areturn";
            case "VOID":
                return "return";
            default:
                // Check if it's an array type
                if (type.toString().contains("[")) {
                    return "areturn";
                }
                throw new UnsupportedOperationException("Unsupported type: " + type.toString());
        }
    }

    private String generateReturn(ReturnInstruction returnInst) {
        var code = new StringBuilder();

        if (returnInst.getReturnType().getTypeOfElement().equals(ElementType.VOID)) {
            code.append("return");
            code.append(NL);
            return code.toString();
        }

        var operand = returnInst.getOperand();
        var operCode = generators.apply(operand);

        code.append(operCode);

        // TODO: Hardcoded to int return type, needs to be expanded
        /*
        if(returnInst.getReturnType().toString() == "BOOLEAN" && !returnInst.getOperand().isLiteral()) {
            HashMap<String, Descriptor> varTable = currentMethod.getVarTable();
            var reg = varTable.get(((Operand) returnInst.getOperand()).getName()).getVirtualReg();
            code.append(getSpaceor_("iload", reg));
        }
        else if(returnInst.getReturnType().toString() == "INT32" && !returnInst.getOperand().isLiteral()) {
            HashMap<String, Descriptor> varTable = currentMethod.getVarTable();
            var reg = varTable.get(((Operand) returnInst.getOperand()).getName()).getVirtualReg();
            code.append(getSpaceor_("iload", reg));
        }
        else if(returnInst.getReturnType().toString() == "INT32" && returnInst.getOperand().isLiteral()) {
            HashMap<String, Descriptor> varTable = currentMethod.getVarTable();
            code.append(getSpaceor_("iconst", 0));
        } */

        var l = returnInst.getReturnType();
        code.append(getReturnType(l)).append(NL);


        return code.toString();
    }

}
