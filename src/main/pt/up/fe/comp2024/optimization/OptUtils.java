package pt.up.fe.comp2024.optimization;

import org.specs.comp.ollir.Instruction;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.specs.util.exceptions.NotImplementedException;

import java.util.List;
import java.util.Optional;

import static pt.up.fe.comp2024.ast.Kind.TYPE;

public class OptUtils {
    private static int tempNumber = -1;

    private static int currentIf = 0;

    private static int currentWhile = 0;

    public static String getTemp() {

        return getTemp("temp");
    }

    public static String getTemp(String prefix) {

        return prefix + getNextTempNum();
    }

    public static int getNextTempNum() {

        tempNumber += 1;
        return tempNumber;
    }

    public static String toOllirType(JmmNode typeNode) {

        TYPE.checkOrThrow(typeNode);

        String typeName = typeNode.get("name");

        return toOllirType(typeName);
    }

    public static String toOllirType(Type type) {
        if(type.isArray())
            return ".array" + toOllirType(type.getName());
        return toOllirType(type.getName());
    }

    private static String toOllirType(String typeName) {
        StringBuilder type = new StringBuilder();
        switch (typeName) {
            case "int" -> type.append(".i32");
            case "boolean" -> type.append(".bool");
            case "void" -> type.append(".V");
            case "" -> type.append(typeName);
            default -> type.append("." + typeName);
        };
        return type.toString();
    }

    public static int getCurrentIf(){
        int ret = currentIf;
        currentIf++;
        return ret;
    }

    public static int getCurrentWhile(){
        int ret = currentWhile;
        currentWhile++;
        return ret;
    }


}
