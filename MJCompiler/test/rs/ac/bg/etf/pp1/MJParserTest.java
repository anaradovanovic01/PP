package rs.ac.bg.etf.pp1;

import java.io.*;

import java_cup.runtime.Symbol;
import jdk.javadoc.internal.doclets.toolkit.util.DocFinder.Output;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			System.setOut(new PrintStream(new FileOutputStream(new File("test/izlaz.out"))));
			System.setErr(new PrintStream(new FileOutputStream(new File("test/izlaz.err"))));
			File sourceCode;
			if(args.length > 1) sourceCode = new File(args[0]);
			else sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse(); 
	        
	        Program prog = (Program)(s.value);
	        DerivedTab.init();
			log.info(prog.toString(""));
			
			SemanticAnalyzer v = new SemanticAnalyzer();
			System.out.println("======================SEMANTICKA OBRADA========================");
			prog.traverseBottomUp(v); 
			
			// ispis prepoznatih programskih konstrukcija
			System.out.println("======================SINTAKSNA ANALIZA========================");
			System.out.println(v.globalConstants +  " globalnih konstanti");
			System.out.println(v.globalVariables +  " globalnih promenljivih");
			System.out.println(v.globalArrays +  " globalnih nizova");
			System.out.println(v.localVariables +  " lokalnih promenljivih");
			DerivedTab.dump();
			
			log.info("===================================");
			if(!p.errorDetected && !v.errorDetected){
				File objFile;
				if(args.length > 1) objFile = new File(args[1]);
				else objFile = new File("test/program.obj");
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));
				log.info("Parsiranje uspesno zavrseno!");
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
