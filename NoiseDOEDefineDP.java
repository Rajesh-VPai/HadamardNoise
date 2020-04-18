/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaguchHadamardNoise;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class NoiseDOEDefineDP {
 public static Logger log=Logger.getLogger(NoiseDOEDefineDP.class.getName());
 public static void Myfscanf(FileInputStream fp)
{
    String s = new String();
    String[] sTemp = null;
    int param = 0;
    int exp = 0;
    try{
//DataInputStream din=new DataInputStream(fp);

BufferedReader din=new BufferedReader(new InputStreamReader(fp));
HadamardNoise.Seed=Integer.parseInt(ProcessString(din.readLine()));

HadamardNoise.LCG_ValueInit=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.LCG_ValueOrder=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.EqnLCG_Value=ProcessString(din.readLine());

HadamardNoise.LCG_levelCntrlInit=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.LCG_levelCntrlOrder=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.EqnLCG_levelCntrl=ProcessString(din.readLine());

HadamardNoise.LCG_FactValueInit=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.LCG_FactValueOrder=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.EqnLCG_FactValue=ProcessString(din.readLine());

HadamardNoise.LCG_NPRFactValueInit=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.LCG_NPRFactValueOrder=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.EqnLCG_NPRFactValue=ProcessString(din.readLine());

HadamardNoise.LCG_FactorialInit=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.LCG_FactorialOrder=Integer.parseInt(ProcessString(din.readLine()));
HadamardNoise.EqnLCG_Factorial=ProcessString(din.readLine());	

HadamardNoise.UseSoftwareNoise=Boolean.parseBoolean(ProcessString(din.readLine()));
HadamardNoise.UseHardwareNoise=Boolean.parseBoolean(ProcessString(din.readLine()));
HadamardNoise.UseIntegration=Boolean.parseBoolean(ProcessString(din.readLine()));
HadamardNoise.AvoidStackOverflow=Boolean.parseBoolean(ProcessString(din.readLine()));

fp.close();
//log.error("GlblVars.Precision=" + GlblVars.Precision);
} catch(Exception HyperDOEse){ 
                            log.info("Exception: define Myfscanf");
                            System.out.println(HyperDOEse.getStackTrace());
                            System.out.println(HyperDOEse.getMessage());
                            HyperDOEse.printStackTrace();}
}

public static void Myprintf()
{
    try{
System.out.print(System.lineSeparator());
System.out.printf("Hadamard Noise Parameters Begin");
System.out.print(System.lineSeparator());
System.out.printf("Seed = %d \n" ,HadamardNoise.Seed);
System.out.printf("LCG_ValueInit = %d \n" ,HadamardNoise.LCG_ValueInit);
System.out.printf("LCG_ValueOrder = %d \n" ,HadamardNoise.LCG_ValueOrder);
System.out.printf("EqnLCG_Value = %s \n" ,HadamardNoise.EqnLCG_Value);

System.out.printf("LCG_levelCntrlInit = %d \n" ,HadamardNoise.LCG_levelCntrlInit);
System.out.printf("LCG_levelCntrlOrder = %d \n" ,HadamardNoise.LCG_levelCntrlOrder);
System.out.printf("EqnLCG_levelCntrl = %s \n" ,HadamardNoise.EqnLCG_levelCntrl);

System.out.printf("LCG_FactValueInit = %d \n" ,HadamardNoise.LCG_FactValueInit);
System.out.printf("LCG_FactValueOrder = %d \n" ,HadamardNoise.LCG_FactValueOrder);
System.out.printf("EqnLCG_FactValue = %s \n" ,HadamardNoise.EqnLCG_FactValue);

System.out.printf("LCG_NPRFactValueInit = %d \n" ,HadamardNoise.LCG_NPRFactValueInit);
System.out.printf("LCG_NPRFactValueOrder = %d \n" ,HadamardNoise.LCG_NPRFactValueOrder);
System.out.printf("EqnLCG_NPRFactValue = %s \n" ,HadamardNoise.EqnLCG_NPRFactValue);

System.out.printf("LCG_FactorialInit = %d \n" ,HadamardNoise.LCG_FactorialInit);
System.out.printf("LCG_FactorialOrder = %d \n" ,HadamardNoise.LCG_FactorialOrder);
System.out.printf("EqnLCG_Factorial = %s \n" ,HadamardNoise.EqnLCG_Factorial);

System.out.printf("UseSoftwareNoise = %s \n",HadamardNoise.UseSoftwareNoise);
System.out.printf("UseHardwareNoise = %s \n",HadamardNoise.UseHardwareNoise);
System.out.printf("UseIntegration = %s \n",HadamardNoise.UseIntegration);
System.out.printf("AvoidStackOverflow = %s \n",HadamardNoise.AvoidStackOverflow);

System.out.print(System.lineSeparator());
    } catch(Exception HyperDOEse){ 
                            log.info("Exception: define Myfprintf");
                            System.out.println(HyperDOEse.getStackTrace());
                            System.out.println(HyperDOEse.getMessage());
                            HyperDOEse.printStackTrace();}
}
public static String ProcessString(String input){
    String[] TempDataStr=new String[5];
    String[] TempValuesStr=new String[5];
    String[] Temp=new String[5];
    String[] Temp1=new String[5];
    if ((input.contains("Values("))||(input.contains("="))) {
      if(input.contains("Values(")){
        Temp=input.split("[\\(]");  
        TempValuesStr=Temp[1].split(",");
        Temp1=TempValuesStr[TempValuesStr.length-1].split("[\\)]");
        TempValuesStr[TempValuesStr.length-1]=Temp1[0];
        TempDataStr=input.split("=");
        for(int i=0; i < TempValuesStr.length;i++){
            //if Value is in the permissible range
           if((TempValuesStr[i]!= null)
                   &&(!TempValuesStr[i].equalsIgnoreCase(""))
               &&(TempValuesStr[i].equalsIgnoreCase(TempDataStr[1].trim()))
                   )
            return TempDataStr[1].trim();
        }
        //If Value has a spelling mistake or is outside the permissible range
        //accept new string anyways
        //return null:False
        return null;
      }  
      else {
        TempDataStr=input.split("=");
        // Take 1 Equal to(=) as the one seperating Value of Variable if Length==2
        if((TempDataStr.length)==2){
        //log.error("Read String=" + TempDataStr[1].trim());
        return TempDataStr[1].trim();
        }
        // Take last Equal to(=) as the one seperating Value of Variable
        else {
            return TempDataStr[TempDataStr.length-1].trim();
        }
        }
    }
    else if (!(input.trim().isEmpty())) return input.trim();
    else return null;
  }
public static void Myscanf()
{
    try{
//DataInputStream din=new DataInputStream(fp);
BufferedReader din=new BufferedReader(new InputStreamReader(System.in));
System.out.print(System.lineSeparator());
System.out.printf("Hadamard Noise Parameters Begin");
System.out.print(System.lineSeparator());

String Data;
Data=MyPrompt( din, "Seed", ("" +HadamardNoise.Seed+"") );
HadamardNoise.Seed=Integer.parseInt(Data);

Data=MyPrompt( din, "LCG_ValueInit", ("" +HadamardNoise.LCG_ValueInit+"") );
HadamardNoise.LCG_ValueInit=Integer.parseInt(Data);

Data=MyPrompt( din, "LCG_ValueOrder", ("" +HadamardNoise.LCG_ValueOrder+"") );
HadamardNoise.LCG_ValueOrder=Integer.parseInt(Data);

Data=MyPrompt( din, "EqnLCG_Value", HadamardNoise.EqnLCG_Value );
HadamardNoise.EqnLCG_Value=Data;

Data=MyPrompt( din, "LCG_levelCntrlInit", ("" +HadamardNoise.LCG_levelCntrlInit+"") );
HadamardNoise.LCG_levelCntrlInit=Integer.parseInt(Data);

Data=MyPrompt( din, "LCG_levelCntrlOrder", ("" +HadamardNoise.LCG_levelCntrlOrder+"") );
HadamardNoise.LCG_levelCntrlOrder=Integer.parseInt(Data);

Data=MyPrompt( din, "EqnLCG_levelCntrl", HadamardNoise.EqnLCG_levelCntrl );
HadamardNoise.EqnLCG_levelCntrl=Data;

Data=MyPrompt( din, "LCG_FactValueInit", ("" +HadamardNoise.LCG_FactValueInit+"") );
HadamardNoise.LCG_FactValueInit=Integer.parseInt(Data);

Data=MyPrompt( din, "LCG_FactValueOrder", ("" +HadamardNoise.LCG_FactValueOrder+"") );
HadamardNoise.LCG_FactValueOrder=Integer.parseInt(Data);

Data=MyPrompt( din, "EqnLCG_FactValue", HadamardNoise.EqnLCG_FactValue );
HadamardNoise.EqnLCG_FactValue=Data;

Data=MyPrompt( din, "LCG_NPRFactValueInit", ("" +HadamardNoise.LCG_NPRFactValueInit+"") );
HadamardNoise.LCG_NPRFactValueInit=Integer.parseInt(Data);

Data=MyPrompt( din, "LCG_NPRFactValueOrder", ("" +HadamardNoise.LCG_NPRFactValueOrder+"") );
HadamardNoise.LCG_NPRFactValueOrder=Integer.parseInt(Data);

Data=MyPrompt( din, "EqnLCG_NPRFactValue", HadamardNoise.EqnLCG_NPRFactValue );
HadamardNoise.EqnLCG_NPRFactValue=Data;

Data=MyPrompt( din, "LCG_FactorialInit", ("" +HadamardNoise.LCG_FactorialInit+"") );
HadamardNoise.LCG_FactorialInit=Integer.parseInt(Data);

Data=MyPrompt( din, "LCG_FactorialOrder", ("" +HadamardNoise.LCG_FactorialOrder+"") );
HadamardNoise.LCG_FactorialOrder=Integer.parseInt(Data);

Data=MyPrompt( din, "EqnLCG_Factorial", HadamardNoise.EqnLCG_Factorial );
HadamardNoise.EqnLCG_Factorial=Data;

Data = MyPrompt(din, "UseSoftwareNoise", ("" + HadamardNoise.UseSoftwareNoise + ""));
HadamardNoise.UseSoftwareNoise=Boolean.parseBoolean(Data);
Data = MyPrompt(din, "UseHardwareNoise", ("" + HadamardNoise.UseHardwareNoise + ""));
HadamardNoise.UseHardwareNoise=Boolean.parseBoolean(Data);
Data = MyPrompt(din, "UseIntegration", ("" + HadamardNoise.UseIntegration + ""));
HadamardNoise.UseIntegration=Boolean.parseBoolean(Data);
Data = MyPrompt(din, "AvoidStackOverflow", ("" + HadamardNoise.AvoidStackOverflow + ""));
HadamardNoise.AvoidStackOverflow=Boolean.parseBoolean(Data);

System.out.print(System.lineSeparator());

System.out.print(System.lineSeparator());



} catch(Exception HyperDOEse){ 
                            log.info("Exception: define Myfscanf");
                            System.out.println(HyperDOEse.getStackTrace());
                            System.out.println(HyperDOEse.getMessage());
                            HyperDOEse.printStackTrace();}
}
public static void Myfprintf(String Path)
{
    try{
PrintWriter fpout=null;
if ((fpout = new PrintWriter(Path+"HadamardNoise" )) == null)
	{
		System.out.printf("can't open %s\n", "HadamardNoise");
		System.out.printf("Setup aborted.\n");
		System.exit(1);
	}

fpout.printf("Seed = %d \n" ,HadamardNoise.Seed);
fpout.printf("LCG_ValueInit = %d \n" ,HadamardNoise.LCG_ValueInit);
fpout.printf("LCG_ValueOrder = %d \n" ,HadamardNoise.LCG_ValueOrder);
fpout.printf("EqnLCG_Value = %s \n" ,HadamardNoise.EqnLCG_Value);

fpout.printf("LCG_levelCntrlInit = %d \n" ,HadamardNoise.LCG_levelCntrlInit);
fpout.printf("LCG_levelCntrlOrder = %d \n" ,HadamardNoise.LCG_levelCntrlOrder);
fpout.printf("EqnLCG_levelCntrl = %s \n" ,HadamardNoise.EqnLCG_levelCntrl);

fpout.printf("LCG_FactValueInit = %d \n" ,HadamardNoise.LCG_FactValueInit);
fpout.printf("LCG_FactValueOrder = %d \n" ,HadamardNoise.LCG_FactValueOrder);
fpout.printf("EqnLCG_FactValue = %s \n" ,HadamardNoise.EqnLCG_FactValue);

fpout.printf("LCG_NPRFactValueInit = %d \n" ,HadamardNoise.LCG_NPRFactValueInit);
fpout.printf("LCG_NPRFactValueOrder = %d \n" ,HadamardNoise.LCG_NPRFactValueOrder);
fpout.printf("EqnLCG_NPRFactValue = %s \n" ,HadamardNoise.EqnLCG_NPRFactValue);

fpout.printf("LCG_FactorialInit = %d \n" ,HadamardNoise.LCG_FactorialInit);
fpout.printf("LCG_FactorialOrder = %d \n" ,HadamardNoise.LCG_FactorialOrder);
fpout.printf("EqnLCG_Factorial = %s \n" ,HadamardNoise.EqnLCG_Factorial);


fpout.printf("UseSoftwareNoise = %s \n",HadamardNoise.UseSoftwareNoise);
fpout.printf("UseHardwareNoise = %s \n",HadamardNoise.UseHardwareNoise);
fpout.printf("UseIntegration = %s \n",HadamardNoise.UseIntegration);
fpout.printf("AvoidStackOverflow = %s \n",HadamardNoise.AvoidStackOverflow);
fpout.close();
} catch(Exception HyperDOEse){ 
                            log.info("Exception: define Myfprintf");
                            System.out.println(HyperDOEse.getStackTrace());
                            System.out.println(HyperDOEse.getMessage());
                            HyperDOEse.printStackTrace();
}
 }

public static String MyPrompt(BufferedReader din, String Param, String magic ){
    String TempStr="";
    try{
    System.out.print(Param + " [" + magic + "] = " );
    TempStr=ProcessString(din.readLine());
    if (TempStr== null ) return magic.trim();
    else return TempStr.trim();
    } catch(Exception HyperDOEse){ 
                            log.info("Exception: define MyPrompt");
                            System.out.println(HyperDOEse.getStackTrace());
                            System.out.println(HyperDOEse.getMessage());
                            HyperDOEse.printStackTrace();
}
    return null;
}
}
