// Generated from comp2024/grammar/Javamm.g4 by ANTLR 4.5.3

    package pt.up.fe.comp2024;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavammParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		EQUALS=1, SEMI=2, LCURLY=3, RCURLY=4, LPAREN=5, RPAREN=6, LBRACKET=7, 
		RBRACKET=8, MUL=9, ADD=10, SUB=11, DIV=12, AND=13, LESS=14, VARARGS=15, 
		CLASS=16, INT=17, STRING=18, BOOLEAN=19, PUBLIC=20, IF=21, ELSE=22, WHILE=23, 
		TRUE=24, FALSE=25, THIS=26, RETURN=27, NOT=28, DOT=29, LENGTH=30, COMMA=31, 
		NEW=32, IMPORT=33, EXTENDS=34, STATIC=35, VOID=36, MAIN=37, COMMENT=38, 
		MULTILINE_COMMENT=39, INTEGER=40, ID=41, WS=42;
	public static final int
		RULE_program = 0, RULE_importDecl = 1, RULE_classDecl = 2, RULE_varDecl = 3, 
		RULE_type = 4, RULE_methodDecl = 5, RULE_param = 6, RULE_stmt = 7, RULE_expr = 8;
	public static final String[] ruleNames = {
		"program", "importDecl", "classDecl", "varDecl", "type", "methodDecl", 
		"param", "stmt", "expr"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "';'", "'{'", "'}'", "'('", "')'", "'['", "']'", "'*'", "'+'", 
		"'-'", "'/'", "'&&'", "'<'", "'...'", "'class'", "'int'", "'String'", 
		"'boolean'", "'public'", "'if'", "'else'", "'while'", "'true'", "'false'", 
		"'this'", "'return'", "'!'", "'.'", "'length'", "','", "'new'", "'import'", 
		"'extends'", "'static'", "'void'", "'main'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "EQUALS", "SEMI", "LCURLY", "RCURLY", "LPAREN", "RPAREN", "LBRACKET", 
		"RBRACKET", "MUL", "ADD", "SUB", "DIV", "AND", "LESS", "VARARGS", "CLASS", 
		"INT", "STRING", "BOOLEAN", "PUBLIC", "IF", "ELSE", "WHILE", "TRUE", "FALSE", 
		"THIS", "RETURN", "NOT", "DOT", "LENGTH", "COMMA", "NEW", "IMPORT", "EXTENDS", 
		"STATIC", "VOID", "MAIN", "COMMENT", "MULTILINE_COMMENT", "INTEGER", "ID", 
		"WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Javamm.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JavammParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public ClassDeclContext classDecl() {
			return getRuleContext(ClassDeclContext.class,0);
		}
		public TerminalNode EOF() { return getToken(JavammParser.EOF, 0); }
		public List<ImportDeclContext> importDecl() {
			return getRuleContexts(ImportDeclContext.class);
		}
		public ImportDeclContext importDecl(int i) {
			return getRuleContext(ImportDeclContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(18);
				importDecl();
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(24);
			classDecl();
			setState(25);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportDeclContext extends ParserRuleContext {
		public Token ID;
		public List<Token> imports = new ArrayList<Token>();
		public TerminalNode IMPORT() { return getToken(JavammParser.IMPORT, 0); }
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public List<TerminalNode> ID() { return getTokens(JavammParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavammParser.ID, i);
		}
		public List<TerminalNode> DOT() { return getTokens(JavammParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(JavammParser.DOT, i);
		}
		public ImportDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterImportDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitImportDecl(this);
		}
	}

	public final ImportDeclContext importDecl() throws RecognitionException {
		ImportDeclContext _localctx = new ImportDeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			match(IMPORT);
			setState(28);
			((ImportDeclContext)_localctx).ID = match(ID);
			((ImportDeclContext)_localctx).imports.add(((ImportDeclContext)_localctx).ID);
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(29);
				match(DOT);
				setState(30);
				((ImportDeclContext)_localctx).ID = match(ID);
				((ImportDeclContext)_localctx).imports.add(((ImportDeclContext)_localctx).ID);
				}
				}
				setState(35);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(36);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclContext extends ParserRuleContext {
		public Token name;
		public Token parent;
		public TerminalNode CLASS() { return getToken(JavammParser.CLASS, 0); }
		public TerminalNode LCURLY() { return getToken(JavammParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavammParser.RCURLY, 0); }
		public List<TerminalNode> ID() { return getTokens(JavammParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavammParser.ID, i);
		}
		public TerminalNode EXTENDS() { return getToken(JavammParser.EXTENDS, 0); }
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<MethodDeclContext> methodDecl() {
			return getRuleContexts(MethodDeclContext.class);
		}
		public MethodDeclContext methodDecl(int i) {
			return getRuleContext(MethodDeclContext.class,i);
		}
		public ClassDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterClassDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitClassDecl(this);
		}
	}

	public final ClassDeclContext classDecl() throws RecognitionException {
		ClassDeclContext _localctx = new ClassDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(CLASS);
			setState(39);
			((ClassDeclContext)_localctx).name = match(ID);
			setState(42);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(40);
				match(EXTENDS);
				setState(41);
				((ClassDeclContext)_localctx).parent = match(ID);
				}
			}

			setState(44);
			match(LCURLY);
			setState(48);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(45);
					varDecl();
					}
					} 
				}
				setState(50);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << STRING) | (1L << BOOLEAN) | (1L << PUBLIC) | (1L << STATIC) | (1L << ID))) != 0)) {
				{
				{
				setState(51);
				methodDecl();
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(57);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclContext extends ParserRuleContext {
		public Token name;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TerminalNode MAIN() { return getToken(JavammParser.MAIN, 0); }
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public TerminalNode STRING() { return getToken(JavammParser.STRING, 0); }
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterVarDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitVarDecl(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			type();
			setState(60);
			((VarDeclContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << LENGTH) | (1L << MAIN) | (1L << ID))) != 0)) ) {
				((VarDeclContext)_localctx).name = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(61);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public boolean isArray = false;
		public Token name;
		public Token vararg;
		public TerminalNode LBRACKET() { return getToken(JavammParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(JavammParser.RBRACKET, 0); }
		public TerminalNode INT() { return getToken(JavammParser.INT, 0); }
		public TerminalNode VARARGS() { return getToken(JavammParser.VARARGS, 0); }
		public TerminalNode BOOLEAN() { return getToken(JavammParser.BOOLEAN, 0); }
		public TerminalNode STRING() { return getToken(JavammParser.STRING, 0); }
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type);
		try {
			setState(74);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(63);
				((TypeContext)_localctx).name = match(INT);
				setState(64);
				match(LBRACKET);
				setState(65);
				match(RBRACKET);
				((TypeContext)_localctx).isArray =  true;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(67);
				((TypeContext)_localctx).name = match(INT);
				setState(68);
				((TypeContext)_localctx).vararg = match(VARARGS);
				((TypeContext)_localctx).isArray =  true;
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(70);
				((TypeContext)_localctx).name = match(INT);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(71);
				((TypeContext)_localctx).name = match(BOOLEAN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(72);
				((TypeContext)_localctx).name = match(STRING);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(73);
				((TypeContext)_localctx).name = match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclContext extends ParserRuleContext {
		public boolean isPublic = false;
		public boolean isMain = false;
		public Token name;
		public Token v;
		public Token args;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public TerminalNode LCURLY() { return getToken(JavammParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavammParser.RCURLY, 0); }
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TerminalNode PUBLIC() { return getToken(JavammParser.PUBLIC, 0); }
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavammParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavammParser.COMMA, i);
		}
		public TerminalNode STATIC() { return getToken(JavammParser.STATIC, 0); }
		public TerminalNode STRING() { return getToken(JavammParser.STRING, 0); }
		public TerminalNode LBRACKET() { return getToken(JavammParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(JavammParser.RBRACKET, 0); }
		public TerminalNode VOID() { return getToken(JavammParser.VOID, 0); }
		public TerminalNode MAIN() { return getToken(JavammParser.MAIN, 0); }
		public MethodDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterMethodDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitMethodDecl(this);
		}
	}

	public final MethodDeclContext methodDecl() throws RecognitionException {
		MethodDeclContext _localctx = new MethodDeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_methodDecl);
		int _la;
		try {
			int _alt;
			setState(137);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				_la = _input.LA(1);
				if (_la==PUBLIC) {
					{
					setState(76);
					match(PUBLIC);
					((MethodDeclContext)_localctx).isPublic = true;
					}
				}

				setState(80);
				type();
				setState(81);
				((MethodDeclContext)_localctx).name = match(ID);
				setState(82);
				match(LPAREN);
				setState(91);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << STRING) | (1L << BOOLEAN) | (1L << ID))) != 0)) {
					{
					setState(83);
					param();
					setState(88);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(84);
						match(COMMA);
						setState(85);
						param();
						}
						}
						setState(90);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(93);
				match(RPAREN);
				setState(94);
				match(LCURLY);
				setState(98);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(95);
						varDecl();
						}
						} 
					}
					setState(100);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LCURLY) | (1L << LPAREN) | (1L << LBRACKET) | (1L << BOOLEAN) | (1L << IF) | (1L << WHILE) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << RETURN) | (1L << NOT) | (1L << NEW) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(101);
					stmt();
					}
					}
					setState(106);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(107);
				match(RCURLY);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(111);
				_la = _input.LA(1);
				if (_la==PUBLIC) {
					{
					setState(109);
					match(PUBLIC);
					((MethodDeclContext)_localctx).isPublic = true;
					}
				}

				setState(113);
				match(STATIC);
				setState(114);
				((MethodDeclContext)_localctx).v = match(VOID);
				setState(115);
				((MethodDeclContext)_localctx).name = match(MAIN);
				((MethodDeclContext)_localctx).isMain = true;
				setState(117);
				match(LPAREN);
				setState(118);
				match(STRING);
				setState(119);
				match(LBRACKET);
				setState(120);
				match(RBRACKET);
				setState(121);
				((MethodDeclContext)_localctx).args = match(ID);
				setState(122);
				match(RPAREN);
				setState(123);
				match(LCURLY);
				setState(127);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(124);
						varDecl();
						}
						} 
					}
					setState(129);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LCURLY) | (1L << LPAREN) | (1L << LBRACKET) | (1L << BOOLEAN) | (1L << IF) | (1L << WHILE) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << RETURN) | (1L << NOT) | (1L << NEW) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(130);
					stmt();
					}
					}
					setState(135);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(136);
				match(RCURLY);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public Token name;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitParam(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			type();
			setState(140);
			((ParamContext)_localctx).name = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	 
		public StmtContext() { }
		public void copyFrom(StmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CurlyStmtContext extends StmtContext {
		public TerminalNode LCURLY() { return getToken(JavammParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavammParser.RCURLY, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public CurlyStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterCurlyStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitCurlyStmt(this);
		}
	}
	public static class IfElseStmtContext extends StmtContext {
		public TerminalNode IF() { return getToken(JavammParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(JavammParser.ELSE, 0); }
		public IfElseStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterIfElseStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitIfElseStmt(this);
		}
	}
	public static class WhileStmtContext extends StmtContext {
		public TerminalNode WHILE() { return getToken(JavammParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public WhileStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitWhileStmt(this);
		}
	}
	public static class AssignStmtContext extends StmtContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQUALS() { return getToken(JavammParser.EQUALS, 0); }
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public AssignStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitAssignStmt(this);
		}
	}
	public static class EmptyCurlyContext extends StmtContext {
		public TerminalNode LCURLY() { return getToken(JavammParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavammParser.RCURLY, 0); }
		public EmptyCurlyContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterEmptyCurly(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitEmptyCurly(this);
		}
	}
	public static class ExprSemiContext extends StmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public ExprSemiContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterExprSemi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitExprSemi(this);
		}
	}
	public static class ReturnStmtContext extends StmtContext {
		public TerminalNode RETURN() { return getToken(JavammParser.RETURN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public ReturnStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterReturnStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitReturnStmt(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_stmt);
		int _la;
		try {
			setState(178);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new AssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(142);
				expr(0);
				setState(143);
				match(EQUALS);
				setState(144);
				expr(0);
				setState(145);
				match(SEMI);
				}
				break;
			case 2:
				_localctx = new IfElseStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(147);
				match(IF);
				setState(148);
				match(LPAREN);
				setState(149);
				expr(0);
				setState(150);
				match(RPAREN);
				setState(151);
				stmt();
				{
				setState(152);
				match(ELSE);
				setState(153);
				stmt();
				}
				}
				break;
			case 3:
				_localctx = new WhileStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(155);
				match(WHILE);
				setState(156);
				match(LPAREN);
				setState(157);
				expr(0);
				setState(158);
				match(RPAREN);
				setState(159);
				stmt();
				}
				break;
			case 4:
				_localctx = new ReturnStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(161);
				match(RETURN);
				setState(162);
				expr(0);
				setState(163);
				match(SEMI);
				}
				break;
			case 5:
				_localctx = new EmptyCurlyContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(165);
				match(LCURLY);
				setState(166);
				match(RCURLY);
				}
				break;
			case 6:
				_localctx = new CurlyStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(167);
				match(LCURLY);
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LCURLY) | (1L << LPAREN) | (1L << LBRACKET) | (1L << BOOLEAN) | (1L << IF) | (1L << WHILE) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << RETURN) | (1L << NOT) | (1L << NEW) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(168);
					stmt();
					}
					}
					setState(173);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(174);
				match(RCURLY);
				}
				break;
			case 7:
				_localctx = new ExprSemiContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(175);
				expr(0);
				setState(176);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AndExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode AND() { return getToken(JavammParser.AND, 0); }
		public AndExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitAndExpr(this);
		}
	}
	public static class TrueExprContext extends ExprContext {
		public TerminalNode TRUE() { return getToken(JavammParser.TRUE, 0); }
		public TrueExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterTrueExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitTrueExpr(this);
		}
	}
	public static class BooleanLiteralContext extends ExprContext {
		public Token value;
		public TerminalNode BOOLEAN() { return getToken(JavammParser.BOOLEAN, 0); }
		public BooleanLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitBooleanLiteral(this);
		}
	}
	public static class ArrayAccessExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LBRACKET() { return getToken(JavammParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(JavammParser.RBRACKET, 0); }
		public ArrayAccessExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterArrayAccessExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitArrayAccessExpr(this);
		}
	}
	public static class BinaryExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MUL() { return getToken(JavammParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(JavammParser.DIV, 0); }
		public TerminalNode ADD() { return getToken(JavammParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(JavammParser.SUB, 0); }
		public BinaryExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitBinaryExpr(this);
		}
	}
	public static class ParentExprContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public ParentExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterParentExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitParentExpr(this);
		}
	}
	public static class ConditionalExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LESS() { return getToken(JavammParser.LESS, 0); }
		public ConditionalExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterConditionalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitConditionalExpr(this);
		}
	}
	public static class FalseExprContext extends ExprContext {
		public TerminalNode FALSE() { return getToken(JavammParser.FALSE, 0); }
		public FalseExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterFalseExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitFalseExpr(this);
		}
	}
	public static class ArrayExprContext extends ExprContext {
		public TerminalNode LBRACKET() { return getToken(JavammParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(JavammParser.RBRACKET, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavammParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavammParser.COMMA, i);
		}
		public ArrayExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterArrayExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitArrayExpr(this);
		}
	}
	public static class ExprNewArrayContext extends ExprContext {
		public TerminalNode NEW() { return getToken(JavammParser.NEW, 0); }
		public TerminalNode INT() { return getToken(JavammParser.INT, 0); }
		public TerminalNode LBRACKET() { return getToken(JavammParser.LBRACKET, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(JavammParser.RBRACKET, 0); }
		public ExprNewArrayContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterExprNewArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitExprNewArray(this);
		}
	}
	public static class VarRefExprContext extends ExprContext {
		public Token name;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public VarRefExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterVarRefExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitVarRefExpr(this);
		}
	}
	public static class ExprMemberCallContext extends ExprContext {
		public Token name;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode DOT() { return getToken(JavammParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JavammParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavammParser.COMMA, i);
		}
		public ExprMemberCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterExprMemberCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitExprMemberCall(this);
		}
	}
	public static class LengthCallExprContext extends ExprContext {
		public Token length;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JavammParser.DOT, 0); }
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public LengthCallExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterLengthCallExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitLengthCallExpr(this);
		}
	}
	public static class NotExprContext extends ExprContext {
		public TerminalNode NOT() { return getToken(JavammParser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NotExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitNotExpr(this);
		}
	}
	public static class NewClassContext extends ExprContext {
		public Token name;
		public TerminalNode NEW() { return getToken(JavammParser.NEW, 0); }
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public NewClassContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterNewClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitNewClass(this);
		}
	}
	public static class ThisExprContext extends ExprContext {
		public TerminalNode THIS() { return getToken(JavammParser.THIS, 0); }
		public ThisExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterThisExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitThisExpr(this);
		}
	}
	public static class IntegerLiteralContext extends ExprContext {
		public Token value;
		public TerminalNode INTEGER() { return getToken(JavammParser.INTEGER, 0); }
		public IntegerLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitIntegerLiteral(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				_localctx = new ParentExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(181);
				match(LPAREN);
				setState(182);
				expr(0);
				setState(183);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(185);
				match(NOT);
				setState(186);
				expr(15);
				}
				break;
			case 3:
				{
				_localctx = new ArrayExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(187);
				match(LBRACKET);
				setState(196);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACKET) | (1L << BOOLEAN) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << NOT) | (1L << NEW) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					setState(188);
					expr(0);
					setState(193);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(189);
						match(COMMA);
						setState(190);
						expr(0);
						}
						}
						setState(195);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(198);
				match(RBRACKET);
				}
				break;
			case 4:
				{
				_localctx = new NewClassContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(199);
				match(NEW);
				setState(200);
				((NewClassContext)_localctx).name = match(ID);
				setState(201);
				match(LPAREN);
				setState(202);
				match(RPAREN);
				}
				break;
			case 5:
				{
				_localctx = new ExprNewArrayContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(203);
				match(NEW);
				setState(204);
				match(INT);
				setState(205);
				match(LBRACKET);
				setState(206);
				expr(0);
				setState(207);
				match(RBRACKET);
				}
				break;
			case 6:
				{
				_localctx = new FalseExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(209);
				match(FALSE);
				}
				break;
			case 7:
				{
				_localctx = new TrueExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(210);
				match(TRUE);
				}
				break;
			case 8:
				{
				_localctx = new ThisExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(211);
				match(THIS);
				}
				break;
			case 9:
				{
				_localctx = new IntegerLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(212);
				((IntegerLiteralContext)_localctx).value = match(INTEGER);
				}
				break;
			case 10:
				{
				_localctx = new BooleanLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(213);
				((BooleanLiteralContext)_localctx).value = match(BOOLEAN);
				}
				break;
			case 11:
				{
				_localctx = new VarRefExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(214);
				((VarRefExprContext)_localctx).name = match(ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(254);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(252);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(217);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(218);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(219);
						expr(15);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(220);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(221);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(222);
						expr(14);
						}
						break;
					case 3:
						{
						_localctx = new ConditionalExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(223);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(224);
						((ConditionalExprContext)_localctx).op = match(LESS);
						setState(225);
						expr(13);
						}
						break;
					case 4:
						{
						_localctx = new AndExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(226);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(227);
						((AndExprContext)_localctx).op = match(AND);
						setState(228);
						expr(12);
						}
						break;
					case 5:
						{
						_localctx = new LengthCallExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(229);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(230);
						match(DOT);
						setState(231);
						((LengthCallExprContext)_localctx).length = match(LENGTH);
						}
						break;
					case 6:
						{
						_localctx = new ExprMemberCallContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(232);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						{
						setState(233);
						match(DOT);
						setState(234);
						((ExprMemberCallContext)_localctx).name = match(ID);
						setState(235);
						match(LPAREN);
						setState(244);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACKET) | (1L << BOOLEAN) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << NOT) | (1L << NEW) | (1L << INTEGER) | (1L << ID))) != 0)) {
							{
							setState(236);
							expr(0);
							setState(241);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==COMMA) {
								{
								{
								setState(237);
								match(COMMA);
								setState(238);
								expr(0);
								}
								}
								setState(243);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(246);
						match(RPAREN);
						}
						}
						break;
					case 7:
						{
						_localctx = new ArrayAccessExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(247);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(248);
						match(LBRACKET);
						setState(249);
						expr(0);
						setState(250);
						match(RBRACKET);
						}
						break;
					}
					} 
				}
				setState(256);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 8:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 14);
		case 1:
			return precpred(_ctx, 13);
		case 2:
			return precpred(_ctx, 12);
		case 3:
			return precpred(_ctx, 11);
		case 4:
			return precpred(_ctx, 17);
		case 5:
			return precpred(_ctx, 16);
		case 6:
			return precpred(_ctx, 9);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3,\u0104\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\7\2"+
		"\26\n\2\f\2\16\2\31\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3\"\n\3\f\3\16"+
		"\3%\13\3\3\3\3\3\3\4\3\4\3\4\3\4\5\4-\n\4\3\4\3\4\7\4\61\n\4\f\4\16\4"+
		"\64\13\4\3\4\7\4\67\n\4\f\4\16\4:\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6M\n\6\3\7\3\7\5\7Q\n\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\7\7Y\n\7\f\7\16\7\\\13\7\5\7^\n\7\3\7\3\7\3\7\7\7c\n"+
		"\7\f\7\16\7f\13\7\3\7\7\7i\n\7\f\7\16\7l\13\7\3\7\3\7\3\7\3\7\5\7r\n\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u0080\n\7\f\7\16"+
		"\7\u0083\13\7\3\7\7\7\u0086\n\7\f\7\16\7\u0089\13\7\3\7\5\7\u008c\n\7"+
		"\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00ac\n\t\f\t\16"+
		"\t\u00af\13\t\3\t\3\t\3\t\3\t\5\t\u00b5\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\7\n\u00c2\n\n\f\n\16\n\u00c5\13\n\5\n\u00c7\n\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00da"+
		"\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\7\n\u00f2\n\n\f\n\16\n\u00f5\13\n\5\n\u00f7\n\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u00ff\n\n\f\n\16\n\u0102\13\n\3\n\2\3\22"+
		"\13\2\4\6\b\n\f\16\20\22\2\5\6\2\24\24  \'\'++\4\2\13\13\16\16\3\2\f\r"+
		"\u0129\2\27\3\2\2\2\4\35\3\2\2\2\6(\3\2\2\2\b=\3\2\2\2\nL\3\2\2\2\f\u008b"+
		"\3\2\2\2\16\u008d\3\2\2\2\20\u00b4\3\2\2\2\22\u00d9\3\2\2\2\24\26\5\4"+
		"\3\2\25\24\3\2\2\2\26\31\3\2\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30\32\3\2"+
		"\2\2\31\27\3\2\2\2\32\33\5\6\4\2\33\34\7\2\2\3\34\3\3\2\2\2\35\36\7#\2"+
		"\2\36#\7+\2\2\37 \7\37\2\2 \"\7+\2\2!\37\3\2\2\2\"%\3\2\2\2#!\3\2\2\2"+
		"#$\3\2\2\2$&\3\2\2\2%#\3\2\2\2&\'\7\4\2\2\'\5\3\2\2\2()\7\22\2\2),\7+"+
		"\2\2*+\7$\2\2+-\7+\2\2,*\3\2\2\2,-\3\2\2\2-.\3\2\2\2.\62\7\5\2\2/\61\5"+
		"\b\5\2\60/\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\638\3\2\2"+
		"\2\64\62\3\2\2\2\65\67\5\f\7\2\66\65\3\2\2\2\67:\3\2\2\28\66\3\2\2\28"+
		"9\3\2\2\29;\3\2\2\2:8\3\2\2\2;<\7\6\2\2<\7\3\2\2\2=>\5\n\6\2>?\t\2\2\2"+
		"?@\7\4\2\2@\t\3\2\2\2AB\7\23\2\2BC\7\t\2\2CD\7\n\2\2DM\b\6\1\2EF\7\23"+
		"\2\2FG\7\21\2\2GM\b\6\1\2HM\7\23\2\2IM\7\25\2\2JM\7\24\2\2KM\7+\2\2LA"+
		"\3\2\2\2LE\3\2\2\2LH\3\2\2\2LI\3\2\2\2LJ\3\2\2\2LK\3\2\2\2M\13\3\2\2\2"+
		"NO\7\26\2\2OQ\b\7\1\2PN\3\2\2\2PQ\3\2\2\2QR\3\2\2\2RS\5\n\6\2ST\7+\2\2"+
		"T]\7\7\2\2UZ\5\16\b\2VW\7!\2\2WY\5\16\b\2XV\3\2\2\2Y\\\3\2\2\2ZX\3\2\2"+
		"\2Z[\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2]U\3\2\2\2]^\3\2\2\2^_\3\2\2\2_`\7\b"+
		"\2\2`d\7\5\2\2ac\5\b\5\2ba\3\2\2\2cf\3\2\2\2db\3\2\2\2de\3\2\2\2ej\3\2"+
		"\2\2fd\3\2\2\2gi\5\20\t\2hg\3\2\2\2il\3\2\2\2jh\3\2\2\2jk\3\2\2\2km\3"+
		"\2\2\2lj\3\2\2\2mn\7\6\2\2n\u008c\3\2\2\2op\7\26\2\2pr\b\7\1\2qo\3\2\2"+
		"\2qr\3\2\2\2rs\3\2\2\2st\7%\2\2tu\7&\2\2uv\7\'\2\2vw\b\7\1\2wx\7\7\2\2"+
		"xy\7\24\2\2yz\7\t\2\2z{\7\n\2\2{|\7+\2\2|}\7\b\2\2}\u0081\7\5\2\2~\u0080"+
		"\5\b\5\2\177~\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082"+
		"\3\2\2\2\u0082\u0087\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0086\5\20\t\2"+
		"\u0085\u0084\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088"+
		"\3\2\2\2\u0088\u008a\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008c\7\6\2\2\u008b"+
		"P\3\2\2\2\u008bq\3\2\2\2\u008c\r\3\2\2\2\u008d\u008e\5\n\6\2\u008e\u008f"+
		"\7+\2\2\u008f\17\3\2\2\2\u0090\u0091\5\22\n\2\u0091\u0092\7\3\2\2\u0092"+
		"\u0093\5\22\n\2\u0093\u0094\7\4\2\2\u0094\u00b5\3\2\2\2\u0095\u0096\7"+
		"\27\2\2\u0096\u0097\7\7\2\2\u0097\u0098\5\22\n\2\u0098\u0099\7\b\2\2\u0099"+
		"\u009a\5\20\t\2\u009a\u009b\7\30\2\2\u009b\u009c\5\20\t\2\u009c\u00b5"+
		"\3\2\2\2\u009d\u009e\7\31\2\2\u009e\u009f\7\7\2\2\u009f\u00a0\5\22\n\2"+
		"\u00a0\u00a1\7\b\2\2\u00a1\u00a2\5\20\t\2\u00a2\u00b5\3\2\2\2\u00a3\u00a4"+
		"\7\35\2\2\u00a4\u00a5\5\22\n\2\u00a5\u00a6\7\4\2\2\u00a6\u00b5\3\2\2\2"+
		"\u00a7\u00a8\7\5\2\2\u00a8\u00b5\7\6\2\2\u00a9\u00ad\7\5\2\2\u00aa\u00ac"+
		"\5\20\t\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2"+
		"\u00ad\u00ae\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b5"+
		"\7\6\2\2\u00b1\u00b2\5\22\n\2\u00b2\u00b3\7\4\2\2\u00b3\u00b5\3\2\2\2"+
		"\u00b4\u0090\3\2\2\2\u00b4\u0095\3\2\2\2\u00b4\u009d\3\2\2\2\u00b4\u00a3"+
		"\3\2\2\2\u00b4\u00a7\3\2\2\2\u00b4\u00a9\3\2\2\2\u00b4\u00b1\3\2\2\2\u00b5"+
		"\21\3\2\2\2\u00b6\u00b7\b\n\1\2\u00b7\u00b8\7\7\2\2\u00b8\u00b9\5\22\n"+
		"\2\u00b9\u00ba\7\b\2\2\u00ba\u00da\3\2\2\2\u00bb\u00bc\7\36\2\2\u00bc"+
		"\u00da\5\22\n\21\u00bd\u00c6\7\t\2\2\u00be\u00c3\5\22\n\2\u00bf\u00c0"+
		"\7!\2\2\u00c0\u00c2\5\22\n\2\u00c1\u00bf\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3"+
		"\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c7\3\2\2\2\u00c5\u00c3\3\2"+
		"\2\2\u00c6\u00be\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8"+
		"\u00da\7\n\2\2\u00c9\u00ca\7\"\2\2\u00ca\u00cb\7+\2\2\u00cb\u00cc\7\7"+
		"\2\2\u00cc\u00da\7\b\2\2\u00cd\u00ce\7\"\2\2\u00ce\u00cf\7\23\2\2\u00cf"+
		"\u00d0\7\t\2\2\u00d0\u00d1\5\22\n\2\u00d1\u00d2\7\n\2\2\u00d2\u00da\3"+
		"\2\2\2\u00d3\u00da\7\33\2\2\u00d4\u00da\7\32\2\2\u00d5\u00da\7\34\2\2"+
		"\u00d6\u00da\7*\2\2\u00d7\u00da\7\25\2\2\u00d8\u00da\7+\2\2\u00d9\u00b6"+
		"\3\2\2\2\u00d9\u00bb\3\2\2\2\u00d9\u00bd\3\2\2\2\u00d9\u00c9\3\2\2\2\u00d9"+
		"\u00cd\3\2\2\2\u00d9\u00d3\3\2\2\2\u00d9\u00d4\3\2\2\2\u00d9\u00d5\3\2"+
		"\2\2\u00d9\u00d6\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00d8\3\2\2\2\u00da"+
		"\u0100\3\2\2\2\u00db\u00dc\f\20\2\2\u00dc\u00dd\t\3\2\2\u00dd\u00ff\5"+
		"\22\n\21\u00de\u00df\f\17\2\2\u00df\u00e0\t\4\2\2\u00e0\u00ff\5\22\n\20"+
		"\u00e1\u00e2\f\16\2\2\u00e2\u00e3\7\20\2\2\u00e3\u00ff\5\22\n\17\u00e4"+
		"\u00e5\f\r\2\2\u00e5\u00e6\7\17\2\2\u00e6\u00ff\5\22\n\16\u00e7\u00e8"+
		"\f\23\2\2\u00e8\u00e9\7\37\2\2\u00e9\u00ff\7 \2\2\u00ea\u00eb\f\22\2\2"+
		"\u00eb\u00ec\7\37\2\2\u00ec\u00ed\7+\2\2\u00ed\u00f6\7\7\2\2\u00ee\u00f3"+
		"\5\22\n\2\u00ef\u00f0\7!\2\2\u00f0\u00f2\5\22\n\2\u00f1\u00ef\3\2\2\2"+
		"\u00f2\u00f5\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f7"+
		"\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f6\u00ee\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7"+
		"\u00f8\3\2\2\2\u00f8\u00ff\7\b\2\2\u00f9\u00fa\f\13\2\2\u00fa\u00fb\7"+
		"\t\2\2\u00fb\u00fc\5\22\n\2\u00fc\u00fd\7\n\2\2\u00fd\u00ff\3\2\2\2\u00fe"+
		"\u00db\3\2\2\2\u00fe\u00de\3\2\2\2\u00fe\u00e1\3\2\2\2\u00fe\u00e4\3\2"+
		"\2\2\u00fe\u00e7\3\2\2\2\u00fe\u00ea\3\2\2\2\u00fe\u00f9\3\2\2\2\u00ff"+
		"\u0102\3\2\2\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101\23\3\2\2"+
		"\2\u0102\u0100\3\2\2\2\32\27#,\628LPZ]djq\u0081\u0087\u008b\u00ad\u00b4"+
		"\u00c3\u00c6\u00d9\u00f3\u00f6\u00fe\u0100";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}