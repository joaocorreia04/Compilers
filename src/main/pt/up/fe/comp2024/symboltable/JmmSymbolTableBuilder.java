package pt.up.fe.comp2024.symboltable;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.TypeUtils;
import pt.up.fe.specs.util.SpecsCheck;

import java.net.StandardSocketOptions;
import java.util.*;
import java.util.stream.Collectors;

import static pt.up.fe.comp2024.ast.Kind.*;
public class JmmSymbolTableBuilder {


    public static JmmSymbolTable build(JmmNode root) {
        var classDecl = root.getJmmChild(root.getNumChildren()-1);
        List<String> imports = buildImports(root);
        SpecsCheck.checkArgument(Kind.CLASS_DECL.check(classDecl), () -> "Expected a class declaration: " + classDecl);
        String className = classDecl.get("name");
        String parent = classDecl.getOptional("parent").orElse(null);
        List<Symbol> fields = buildFields(classDecl);
        var methods = buildMethods(classDecl);
        var returnTypes = buildReturnTypes(classDecl);
        var params = buildParams(classDecl);
        var locals = buildLocals(classDecl);

        return new JmmSymbolTable(className, methods, returnTypes, params, locals, imports, parent, fields);
    }

    private static List<Symbol> buildFields(JmmNode classDecl) {
        List<Symbol> fields = new ArrayList<Symbol>();
        classDecl.getChildren(VAR_DECL).stream()
                .forEach(varDecl -> varDecl.getChildren().forEach(
                        type -> fields.add(new Symbol(new Type(type.get("name"), type.get("isArray").equals("true")), varDecl.get("name")))
                ));
        return fields;
    }
    private static List<String> buildImports(JmmNode program){
        List<String> imports = new ArrayList<String>();
        program.getChildren(IMPORT_DECL).stream()
                .forEach(
                        importDecl -> imports.add(importDecl.get("imports"))
                );
        return imports;
    }
    private static Map<String, Type> buildReturnTypes(JmmNode classDecl) {
        Map<String, Type> map = new HashMap<>();

        /*classDecl.getChildren(METHOD_DECL).stream()
                .forEach(method -> method.getChildren(TYPE).forEach(
                        type -> map.put(method.get("name"), new Type(type.get("name"), type.get("isArray").equals("true")))
                ));*/

        var methods = classDecl.getChildren(METHOD_DECL);
        Type type;
        for(var method : methods){
            if(method.hasAttribute("v")){
                type = new Type("void", false);
            }else {
                type = new Type(method.getChild(0).get("name"), method.getDescendants().get(0).get("isArray").equals("true"));
            }
            map.put(method.get("name"), type);
        }


        return map;
    }

    private static Map<String, List<Symbol>> buildParams(JmmNode classDecl) {

        Map<String, List<Symbol>> map = new HashMap<>();

        classDecl.getChildren(METHOD_DECL).stream()
                .forEach(method -> map.put(method.get("name"),
                        method.getChildren(PARAM).stream().map(param -> {
                            if (param.getChild(0).hasAttribute("vararg")) {
                                Type type = new Type(param.getChild(0).get("name"), true);
                                type.putObject("vararg", true);
                                return new Symbol(type, param.get("name"));
                            } else {
                                return new Symbol(
                                        new Type(param.getChild(0).get("name"), param.getDescendants().get(0).get("isArray").equals("true")), param.get("name"));
                            }
                        }).toList()));

        return map;
    }

    private static Map<String, List<Symbol>> buildLocals(JmmNode classDecl) {
        Map<String, List<Symbol>> map = new HashMap<>();
        classDecl.getChildren(METHOD_DECL).stream()
                .forEach(method -> map.put(method.get("name"), getLocalsList(method)));
        return map;
    }

    private static List<String> buildMethods(JmmNode classDecl) {
//        List<String> methods = classDecl.getChildren(METHOD_DECL).stream()
//                .map(method -> method.get("name"))
//                .collect(Collectors.toList());
//        if(!classDecl.getChildren(METHOD_DECL).isEmpty()){
//            methods.add("main");
//
//        }
//        return methods;
        return classDecl.getChildren(METHOD_DECL).stream()
                .map(method -> method.get("name"))
                .toList();
    }


    private static List<Symbol> getLocalsList(JmmNode methodDecl) {
        List<Symbol> locals = new ArrayList<>();
        methodDecl.getChildren(VAR_DECL).stream()
                .forEach(varDecl -> varDecl.getChildren().forEach(
                        type -> locals.add(new Symbol(new Type(type.get("name"), type.get("isArray").equals("true")), varDecl.get("name")))
                ));
        return locals;
    }
}
