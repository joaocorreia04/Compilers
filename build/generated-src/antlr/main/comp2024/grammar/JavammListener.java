// Generated from comp2024/grammar/Javamm.g4 by ANTLR 4.5.3

    package pt.up.fe.comp2024;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JavammParser}.
 */
public interface JavammListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JavammParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(JavammParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(JavammParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void enterImportDecl(JavammParser.ImportDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void exitImportDecl(JavammParser.ImportDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void enterClassDecl(JavammParser.ClassDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void exitClassDecl(JavammParser.ClassDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(JavammParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(JavammParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(JavammParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(JavammParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void enterMethodDecl(JavammParser.MethodDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void exitMethodDecl(JavammParser.MethodDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(JavammParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(JavammParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(JavammParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(JavammParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfElseStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfElseStmt(JavammParser.IfElseStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfElseStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfElseStmt(JavammParser.IfElseStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(JavammParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(JavammParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReturnStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(JavammParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReturnStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(JavammParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyCurly}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterEmptyCurly(JavammParser.EmptyCurlyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyCurly}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitEmptyCurly(JavammParser.EmptyCurlyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CurlyStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterCurlyStmt(JavammParser.CurlyStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CurlyStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitCurlyStmt(JavammParser.CurlyStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprSemi}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterExprSemi(JavammParser.ExprSemiContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprSemi}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitExprSemi(JavammParser.ExprSemiContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(JavammParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(JavammParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TrueExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTrueExpr(JavammParser.TrueExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TrueExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTrueExpr(JavammParser.TrueExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanLiteral}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(JavammParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanLiteral}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(JavammParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAccessExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessExpr(JavammParser.ArrayAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAccessExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessExpr(JavammParser.ArrayAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(JavammParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(JavammParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParentExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParentExpr(JavammParser.ParentExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParentExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParentExpr(JavammParser.ParentExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionalExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpr(JavammParser.ConditionalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionalExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpr(JavammParser.ConditionalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FalseExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFalseExpr(JavammParser.FalseExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FalseExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFalseExpr(JavammParser.FalseExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(JavammParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(JavammParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprNewArray}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprNewArray(JavammParser.ExprNewArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprNewArray}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprNewArray(JavammParser.ExprNewArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarRefExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVarRefExpr(JavammParser.VarRefExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarRefExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVarRefExpr(JavammParser.VarRefExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprMemberCall}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprMemberCall(JavammParser.ExprMemberCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprMemberCall}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprMemberCall(JavammParser.ExprMemberCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LengthCallExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLengthCallExpr(JavammParser.LengthCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LengthCallExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLengthCallExpr(JavammParser.LengthCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(JavammParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(JavammParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewClass}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewClass(JavammParser.NewClassContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewClass}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewClass(JavammParser.NewClassContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ThisExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterThisExpr(JavammParser.ThisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ThisExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitThisExpr(JavammParser.ThisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntegerLiteral}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(JavammParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerLiteral}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(JavammParser.IntegerLiteralContext ctx);
}