package com.trifacta.trifactaudfs;
import java.io.IOException;
import java.util.List;
import org.python.util.PythonInterpreter;

 
/**
 * Example UDF that concatenates two columns
 */
public class PythonUDF implements TrifactaUDF<String> {
  public String functionName;

  @Override
  public String exec(List<Object> inputs) throws IOException {
    if (inputs == null) {
      return null;
    }
    String[] params = {functionName, inputs.get(0).toString()}; 
    return PythonUDF(params);

/*    StringBuilder sb = new StringBuilder();
**    for (int i = 0; i < inputSchema().length; i += 1) {
**      if (inputs.get(i) == null) {
**        return null;
**      }
**      sb.append(inputs.get(i));
**    }
**    return sb.toString(); */
  }
  @SuppressWarnings("rawtypes")
  public Class[] inputSchema() {
    return new Class[]{String.class};
  }
  @Override
  public void finish() throws IOException {
  }
  @Override
  public void init(List<Object> initArgs) {
    if (initArgs.size() != 1) {
      System.out.println("PythonUDF takes in exactly one init argument: functionName"); 
    } 
    functionName = initArgs.get(0).toString();
  }

  public static String PythonUDF(String[] args) {
    try(PythonInterpreter pyInterp = new PythonInterpreter()) {
      String pythonExpr = "PythonUDF.";
      for(int i = 0; i < args.length; i++) {
        if(i == 0) pythonExpr = pythonExpr + args[i] + "("; 
        else { 
          pythonExpr = pythonExpr + "'" + args[i] + "'"; 
          if(i == args.length - 1) pythonExpr = pythonExpr + ")";
          else pythonExpr = pythonExpr + ",";
        }
      }
      System.out.println(pythonExpr);
      pyInterp.exec("import sys; sys.path.append('/udfs')");
      pyInterp.exec("import PythonUDF");
      return pyInterp.eval(pythonExpr).toString();
    }
  }


  public static void main(String[] args) {
    System.out.println(PythonUDF(args));
  }


}
