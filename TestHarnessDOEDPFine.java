/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaguchHadamardNoise;

import static TaguchHadamardNoise.HadamardNoise.StrengthMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class TestHarnessDOEDPFine {

    public static Logger log = Logger.getLogger(TestHarnessDOEDPFine.class.getName());
    // Maximum number of Double Fractional Digits displayed in Equation
    private static int MaximumCompFractionDigits = 2;
    public static String TestCaseRow="";
    
    TestHarnessDOEDPFine( String All, String MyEndTestCase,String RegrORIndivORLimit) {
        
        UsageDOEDPFine.LastTestCaseHarness=30;
        if ((All.equalsIgnoreCase("All")) && (RegrORIndivORLimit.equalsIgnoreCase("Regression"))) {
            UsageDOEDPFine.TestCase = -1;
            UsageDOEDPFine.TestCaseLoopTimes = UsageDOEDPFine.LastTestCaseHarness;
        } else if ((All.equalsIgnoreCase("All")) && (RegrORIndivORLimit.equalsIgnoreCase("LimitedRegression"))) {
            UsageDOEDPFine.TestCase = -1;
            UsageDOEDPFine.EndTestCase=Integer.parseInt(MyEndTestCase);
            UsageDOEDPFine.EndTestCase=Integer.parseInt(MyEndTestCase);
        } else if (RegrORIndivORLimit.equalsIgnoreCase("Individual")) {
            UsageDOEDPFine.TestCase = Integer.parseInt(All);
            UsageDOEDPFine.TestCaseLoopTimes = 1;
            UsageDOEDPFine.EndTestCase=Integer.parseInt(MyEndTestCase);
        } else if ((All.equalsIgnoreCase("All")) && (RegrORIndivORLimit.equalsIgnoreCase("Individual"))) {
            System.out.println(" Wrong Input :All & Individual");
            UsageDOEDPFine.TestCase = -1;
            UsageDOEDPFine.TestCaseLoopTimes = 1;
            UsageDOEDPFine.EndTestCase=Integer.parseInt(MyEndTestCase);
        } else if ((!All.equalsIgnoreCase("All")) && (RegrORIndivORLimit.equalsIgnoreCase("Regression"))) {
            UsageDOEDPFine.TestCase = Integer.parseInt(All);
            UsageDOEDPFine.TestCaseLoopTimes = UsageDOEDPFine.LastTestCaseHarness;
        }else if ((!All.equalsIgnoreCase("All")) && (RegrORIndivORLimit.equalsIgnoreCase("LimitedRegression"))) {
            UsageDOEDPFine.TestCase = Integer.parseInt(All);
            UsageDOEDPFine.TestCaseLoopTimes = Integer.parseInt(MyEndTestCase)-UsageDOEDPFine.TestCase+1;
            UsageDOEDPFine.EndTestCase=Integer.parseInt(MyEndTestCase);
        }
        
    }

    public static boolean PassFailTestCase(int myTestCase) {
        boolean ReturnFlag1 = false;
        boolean ReturnFlag2 = false;
        boolean ReturnFlag3 = false;
        boolean ReturnFlag4 = false;
        boolean ReturnFlag5 = false;
        boolean ReturnFlag6 = false;
        boolean ReturnFlag7 = false;
        boolean ReturnFlag8 = false;
        
        System.out.println("Business Domain :Sanity Test Rules");
//        if(PassFailRule1(myTestCase)==true)
//                    ReturnFlag1=true;
//        if(PassFailRule2(myTestCase)==true)
//                    ReturnFlag2=true;
//        //if(PassFailRule3(myTestCase)==true)
//                    ReturnFlag3=true;
//        if(PassFailRule4(myTestCase)==true)
//                    ReturnFlag4=true;
//        if(PassFailRule5(myTestCase)==true)
//                    ReturnFlag5=true;
//        if(PassFailRule6(myTestCase)==true)
//                    ReturnFlag6=true;
        System.out.println("Business Domain :Process Test Rules");
        //if(PassFailRule7(myTestCase)==true)
                    ReturnFlag7=true;
        //if(PassFailRule8(myTestCase)==true)
                    ReturnFlag8=true;
        
        
        return ((ReturnFlag1)&&(ReturnFlag2)&&(ReturnFlag3)
                &&(ReturnFlag4)&&(ReturnFlag5)&&(ReturnFlag6)
                &&(ReturnFlag7)&&(ReturnFlag8) );
        
    }
    //Test Pass Fail Rules Functions
    //Sanity - PassFailRule1
    //LevelMinIndex <= CellValue <=LevelMaxIndex
    public static boolean PassFailRule1(int myTestCase) {
        boolean ReturnFlagMap1 = false;
        boolean PrintOnceFlag = false;
        for (int i = 0; i < HadamardNoise.ROWSDOE; i++) {
            for (int j = 0; j < HadamardNoise.Length; j++) {
                int LiMin = HadamardNoise.LevelCntrlArrayGet(0, j, HadamardNoise.LevelMinIndex);
                int LiMax = HadamardNoise.LevelCntrlArrayGet(0, j, HadamardNoise.LevelMaxIndex);
                int CellValue = HadamardNoise.DataArrayGet(0, i, j);
                if ((LiMin <= CellValue) && (CellValue <= LiMax)) {
                    ReturnFlagMap1 = true;
                } else if (PrintOnceFlag==false) {
                    System.out.println(ConsoleColors.RED + "PassFailRule1:Test Case=" + myTestCase + " Row=" + i + " Column=" + j + " LiMin=" + LiMin +  "Cell Value=" +CellValue + " LiMax=" + LiMax + ConsoleColors.RESET);
                    ReturnFlagMap1 = false;
                    PrintOnceFlag=true;
                }
            }
        }
        if (ReturnFlagMap1 == true) {
            System.out.println(ConsoleColors.GREEN + " Test Case=" + myTestCase + " Status=" + "Pass PassFailRule1" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + " Test Case=" + myTestCase + " Status=" + "FAIL PassFailRule1" + ConsoleColors.RESET);
        }
        return ((ReturnFlagMap1));
    }
    //Sanity - PassFailRule2
    //No Duplicates
    public static boolean PassFailRule2(int myTestCase) {
        boolean ReturnFlagMap1 = true;
        ReturnFlagMap1=HadamardNoise.DupCheckMatrixMode(0);
        if (ReturnFlagMap1 == false) {
            System.out.println(ConsoleColors.GREEN + " Test Case=" + myTestCase + " Status=" + "Pass PassFailRule2" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "PassFailRule2:Test Case=" + myTestCase + " Status=" + "FAIL PassFailRule2" + ConsoleColors.RESET);
        }
        return ((!ReturnFlagMap1) );
    }
    //Sanity - PassFailRule1
    //Each Column Sum of Levels=100%
    public static boolean PassFailRule3(int myTestCase) {
        boolean ReturnFlagMap1 = false;
        int FullFactorialCnt=0;
        for (int c=0,DataCount=0,FullFactIndivCol=0; c < HadamardNoise.Length; c++,DataCount=0) {
                for ( int r = 0; r < HadamardNoise.ROWSDOE; r++) {
                            DataCount += HadamardNoise.DataArrayGet(0, r, c);
                    }
                if (c==0) FullFactorialCnt=DataCount;
                if (FullFactorialCnt==DataCount) 
                    ReturnFlagMap1=true;
                else 
                    ReturnFlagMap1=false;
            }
        if (ReturnFlagMap1 == true) {
            System.out.println(ConsoleColors.GREEN + " Test Case=" + myTestCase + " Status=" + "Pass PassFailRule3" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + " Test Case=" + myTestCase + " Status=" + "FAIL PassFailRule3" + ConsoleColors.RESET);
        }
        return ((ReturnFlagMap1) );
    } 
    //Sanity - PassFailRule4
    //Number of Cells in a Row = Length
    public static boolean PassFailRule4(int myTestCase) {
        boolean ReturnFlagMap1 = false;
        int j = 0;
        for (int i = 0; i < HadamardNoise.ROWSDOE; i++) {
            for (j = 0; j < HadamardNoise.Length; j++) {
                int CellValue = HadamardNoise.DataArrayGet(0, i, j);
                if (j < HadamardNoise.Length) {
                    ReturnFlagMap1 = true;
                } else {
                    System.out.println(ConsoleColors.RED + "PassFailRule4:Test Case=" + myTestCase + " Row=" + i + " Column=" + j +  "Cell Value=" +CellValue  + ConsoleColors.RESET);
                    ReturnFlagMap1 = false;
                }
            }
        }
        if ((ReturnFlagMap1 == true) && (j == HadamardNoise.Length)) {
            System.out.println(ConsoleColors.GREEN + " Test Case=" + myTestCase + " Status=" + "Pass PassFailRule4" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + " Test Case=" + myTestCase + " Status=" + "FAIL PassFailRule4" + ConsoleColors.RESET);
        }
        return ((ReturnFlagMap1));
    } 
    //Sanity - PassFailRule5
    //Number of Rows = ROWSDOE
    public static boolean PassFailRule5(int myTestCase) {
        boolean ReturnFlagMap1 = false;
        int i = 0;
        for ( i = 0; i < HadamardNoise.ROWSDOE; i++) {
            for (int j = 0; j < HadamardNoise.Length; j++) {
                int CellValue = HadamardNoise.DataArrayGet(0, i, j);
                if (i < HadamardNoise.ROWSDOE) {
                    ReturnFlagMap1 = true;
                } else {
                    System.out.println(ConsoleColors.RED + "PassFailRule5: Test Case=" + myTestCase + " Row=" + i + " Column=" + j +  "Cell Value=" +CellValue + ConsoleColors.RESET);
                    ReturnFlagMap1 = false;
                }
            }
        }
        if ((ReturnFlagMap1 == true) && (i == HadamardNoise.ROWSDOE)) {
            System.out.println(ConsoleColors.GREEN + " Test Case=" + myTestCase + " Status=" + "Pass PassFailRule5" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + " Test Case=" + myTestCase + " Status=" + "FAIL PassFailRule5" + ConsoleColors.RESET);
        }
        return ((ReturnFlagMap1));
    } 
    //Sanity - PassFailRule6
    //No Value < LevelMinIndex && CellValue !=0
    public static boolean PassFailRule6(int myTestCase) {
        boolean ReturnFlagMap1 = false;
        boolean PrintOnceFlag = false;
        int i = 0;
        for ( i = 0; i < HadamardNoise.ROWSDOE; i++) {
            for (int j = 0; j < HadamardNoise.Length; j++) {
                int LiMin = HadamardNoise.LevelCntrlArrayGet(0, j, HadamardNoise.LevelMinIndex);
                int LiMax = HadamardNoise.LevelCntrlArrayGet(0, j, HadamardNoise.LevelMaxIndex);
                int CellValue = HadamardNoise.DataArrayGet(0, i, j);
                if ((CellValue >= LiMin)&&(CellValue != 0) ) {
                    ReturnFlagMap1 = true;
                } else if (PrintOnceFlag==false) {
                    System.out.println(ConsoleColors.RED + "PassFailRule6:Test Case=" + myTestCase + " Row=" + i + " Column=" + j +  "Cell Value=" +CellValue + ConsoleColors.RESET);
                    ReturnFlagMap1 = false;
                    PrintOnceFlag=true;
                }
            }
        }
        if ((ReturnFlagMap1 == true) && (i == HadamardNoise.ROWSDOE)) {
            System.out.println(ConsoleColors.GREEN + " Test Case=" + myTestCase + " Status=" + "Pass PassFailRule6" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + " Test Case=" + myTestCase + " Status=" + "FAIL PassFailRule6" + ConsoleColors.RESET);
        }
        return ((ReturnFlagMap1));
    } 
    //Business Domain Process - PassFailRule7
    // CellValue Follows SimpleModulo:(CellValue == (CellValueM1+1)) :(CellValue == (CellValueP1-1)
    public static boolean PassFailRule7(int myTestCase) {
        boolean ReturnFlagMap1 = false;
        boolean PrintOnceFlag = false;
        for (int i = 1; i < HadamardNoise.ROWSDOE; i++) {
            for (int j = 0; j < HadamardNoise.Length; j++) {
                int CellValue = HadamardNoise.DataArrayGet(0, i, j);
                int CellValueM1 = HadamardNoise.DataArrayGet(0, i-1, j);
                int CellValueP1 = HadamardNoise.DataArrayGet(0, i+1, j);
                if ( (CellValue == (CellValueM1+1)) && (CellValue == (CellValueP1-1)) ) {
                    ReturnFlagMap1 = true;
                } else if(PrintOnceFlag==false) {
                    System.out.println(ConsoleColors.RED + "PassFailRule7:Test Case=" + myTestCase + " Row=" + i + " Column=" + j  +  " Cell Value=" +CellValue + ConsoleColors.RESET);
                    ReturnFlagMap1 = false;
                    PrintOnceFlag=true;
                }
            }
        }
        if (ReturnFlagMap1 == true) {
            System.out.println(ConsoleColors.GREEN + " Test Case=" + myTestCase + " Status=" + "Pass PassFailRule7" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + " Test Case=" + myTestCase + " Status=" + "FAIL PassFailRule7" + ConsoleColors.RESET);
        }
        return ((ReturnFlagMap1));
    }
    //Business Domain Process- PassFailRule8
    // StrengthMode    
    public static boolean PassFailRule8(int myTestCase) {
        boolean ReturnFlagMap1 = false;
        boolean PrintOnceFlag = false;
        int r1 = 1;
        for (r1 = 1; r1 < (HadamardNoise.LEVELS + 1); r1++) {
            for (int c = 0; c < HadamardNoise.Length; c++) {
                int LiMax = HadamardNoise.LevelCntrlArrayGet(0, c, HadamardNoise.LevelMaxIndex);
                if(StrengthMode[c][r1] == 
                        (HadamardNoise.ROWSDOE/LiMax) )
                    ReturnFlagMap1=true;
                 else if(PrintOnceFlag==false) {
                    System.out.println(ConsoleColors.RED + "PassFailRule8:Test Case=" + myTestCase + " Level=" + (r1) + " Column=" + c  +  " StrengthMode[c][r1]=" +StrengthMode[c][r1] + " Should be " + (HadamardNoise.ROWSDOE/LiMax)+ ConsoleColors.RESET);
                    ReturnFlagMap1 = false;
                    PrintOnceFlag=true;
                }
            }
        }
        if (ReturnFlagMap1 == true) {
            System.out.println(ConsoleColors.GREEN + " Test Case=" + myTestCase + " Status=" + "Pass PassFailRule8" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + " Test Case=" + myTestCase + " Status=" + "FAIL PassFailRule8" + ConsoleColors.RESET);
        }
        return ((ReturnFlagMap1));
    }
    
    
    
    //Sanity Test Cases
    public static void SanityTestCases(String AlgoName,int j){
        if(((AlgoName.equalsIgnoreCase("mainDiffTest"))&&(DifferentialGap(j))) )
        {
            System.out.println(ConsoleColors.RED +"Coefficients TestCase:j= " + j  + ": Infinite Loop GAP" + ConsoleColors.RESET);
        }
        else {
            switch(j){
            case -1:
                TestCaseDiffM001();
                break;
            case 0:
                TestCaseDiff000();
                break;
            case 1:
                TestCaseDiff001();
                break;
            case 2:
                TestCaseDiff002();
                break;
            case 3:
                TestCaseDiff003();
                break;
            case 4:
                TestCaseDiff004();
                break;
            case 5:
                TestCaseDiff005();
                break;
            case 6:
                TestCaseDiff006();
                break;
            case 7:
                TestCaseDiff007();
                break;    
            case 8:
                TestCaseDiff008();
                break;
            case 9:
                TestCaseDiff009();
                break;
            case 10:
                TestCaseDiff010();
                break;
            case 11:
                TestCaseDiff011();
                break;
            case 12:
                TestCaseDiff012();
                break;
            case 13:
                TestCaseDiff013();
                break;
            case 14:
                TestCaseDiff014();
                break;
            case 15:
                TestCaseDiff015();
                break;
            case 16:
                TestCaseDiff016();
                break;
            case 17:
                TestCaseDiff017();
                break;    
            case 18:
                TestCaseDiff018();
                break;
            case 19:
                TestCaseDiff019();
                break;
            case 20:
                TestCaseDiff020();
                break;
            case 21:
                TestCaseDiff021();
                break;
            case 22:
                TestCaseDiff022();
                break;
            case 23:
                TestCaseDiff023();
                break;
            case 24:
                TestCaseDiff024();
                break;
            case 25:
                TestCaseDiff025();
                break;
            case 26:
                TestCaseDiff026();
                break;
            case 27:
                TestCaseDiff027();
                break;    
            case 28:
                TestCaseDiff028();
                break;
            case 29:
                TestCaseDiff029();
                break;
            default:
                System.out.println(ConsoleColors.RED +"Names TestCase:j= " + j  + ": Gap" + ConsoleColors.RESET);
                break;
        }
    }
}
     
    public static boolean DifferentialGap(int j){
        //Infinite Loops
        //if((j==34)||(j==35))
        //    return true;
        //else 
        //if((j==20016)||(j==20017)||(j==20046)||(j==20047)||(j==20061)||(j==20062)||(j==20076)||(j==20077))
        //      return true;
        //else if ((j==80001)||(j==80006))
        //    return true;
        return false;
    }
    public static void TestCaseDiffM001() {
        //Test Case 000
        UsageDOEDPFine.TestCase = -1;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:Basic Sanity:Default Values";
        UsageDOEDPFine.TestCaseMatrixID="Sanity TestCase";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoiseDefault";
        TestCaseRow="";
    }
    public static void TestCaseDiff000() {
        //Test Case 000
        UsageDOEDPFine.TestCase = 0;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-000";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise000";
        TestCaseRow="Row=0 1 1 1 1 1 1 1 1 1";
    }
   
    public static void TestCaseDiff001() {
        //Test Case 001
        UsageDOEDPFine.TestCase = 1;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-001";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise001";
        TestCaseRow="Row=1 1 2 2 2 2 2 2 2 2";
    }
    public static void TestCaseDiff002() {
        //Test Case 002
        UsageDOEDPFine.TestCase = 2;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-002";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise002";
        TestCaseRow="Row=2 1 3 2 2 2 2 3 2 3";
    }
    public static void TestCaseDiff003() {
        //Test Case 003
        UsageDOEDPFine.TestCase = 3;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-003";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise003";
        TestCaseRow="Row=3 1 4 2 2 2 2 4 2 2";
    }
    public static void TestCaseDiff004() {
        //Test Case 004
        UsageDOEDPFine.TestCase = 4;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-004";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise004";
        TestCaseRow="Row=4 1 5 2 2 2 2 5 2 3";
    }
    public static void TestCaseDiff005() {
        //Test Case 005
        UsageDOEDPFine.TestCase = 5;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-005";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise005";
        TestCaseRow="Row=5 1 1 2 1 2 1 2 1 2";
    }
    public static void TestCaseDiff006() {
        //Test Case 006
        UsageDOEDPFine.TestCase = 6;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-006";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise006";
        TestCaseRow="Row=6 2 2 1 2 1 2 3 2 3";
    }
    public static void TestCaseDiff007() {
        //Test Case 007
        UsageDOEDPFine.TestCase = 7;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-007";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise007";
        TestCaseRow="Row=7 2 3 2 1 2 1 4 1 1";
    }
    public static void TestCaseDiff008() {
        //Test Case 008
        UsageDOEDPFine.TestCase = 8;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-008";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise008";
        TestCaseRow="Row=8 2 4 2 1 1 2 5 2 3";
    }
    public static void TestCaseDiff009() {
        //Test Case 009
        UsageDOEDPFine.TestCase = 9;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-009";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise009";
        TestCaseRow="Row=9 2 5 1 2 1 1 1 1 1";
    }
    public static void TestCaseDiff010() {
        //Test Case 010
        UsageDOEDPFine.TestCase = 10;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-010";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise010";
        TestCaseRow="Row=10 2 1 2 1 2 1 2 1 2";
    }
    public static void TestCaseDiff011() {
        //Test Case 011
        UsageDOEDPFine.TestCase = 11;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-011";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise011";
        TestCaseRow="Row=11 2 2 1 2 1 2 3 2 3";
    }
    public static void TestCaseDiff012() {
        //Test Case 012
        UsageDOEDPFine.TestCase = 12;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-012";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise012";
        TestCaseRow="Row=12 3 3 1 1 2 1 4 1 1";
    }
    public static void TestCaseDiff013() {
        //Test Case 013
        UsageDOEDPFine.TestCase = 13;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-013";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise013";
        TestCaseRow="Row=13 3 4 1 2 1 2 5 2 3";
    }
    public static void TestCaseDiff014() {
        //Test Case 014
        UsageDOEDPFine.TestCase = 14;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-014";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise014";
        TestCaseRow="Row=14 3 5 1 1 2 1 1 1 1";
    }
    public static void TestCaseDiff015() {
        //Test Case 015
        UsageDOEDPFine.TestCase = 15;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-015";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise015";
        TestCaseRow="Row=15 3 1 2 1 1 2 3 2 3";
    }
    public static void TestCaseDiff016() {
        //Test Case 016
        UsageDOEDPFine.TestCase = 16;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-016";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise016";
        TestCaseRow="Row=16 3 2 1 2 1 1 4 1 1";
    }
    public static void TestCaseDiff017() {
        //Test Case 017
        UsageDOEDPFine.TestCase = 17;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-017";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise017";
        TestCaseRow="Row=17 3 3 1 1 2 1 5 2 3";
    }
    public static void TestCaseDiff018() {
        //Test Case 018
        UsageDOEDPFine.TestCase = 18;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-018";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise018";
        TestCaseRow="Row=18 4 4 1 2 1 2 1 1 1";
    }
    public static void TestCaseDiff019() {
        //Test Case 019
        UsageDOEDPFine.TestCase = 19;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-019";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise019";
        TestCaseRow="Row=19 4 5 2 1 2 1 2 1 2";
    }
    public static void TestCaseDiff020() {
        //Test Case 020
        UsageDOEDPFine.TestCase = 20;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-020";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise020";
        TestCaseRow="Row=20 4 1 2 1 1 2 3 2 3";
    }
    public static void TestCaseDiff021() {
        //Test Case 021
        UsageDOEDPFine.TestCase = 21;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-021";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise021";
        TestCaseRow="Row=21 4 2 1 2 1 1 4 1 1";
    }
    public static void TestCaseDiff022() {
        //Test Case 022
        UsageDOEDPFine.TestCase = 22;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-022";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise022";
        TestCaseRow="Row=22 4 3 1 1 2 1 5 2 3";
    }
    public static void TestCaseDiff023() {
        //Test Case 023
        UsageDOEDPFine.TestCase = 23;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-023";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise023";
        TestCaseRow="Row=23 4 4 1 2 1 2 1 1 1";
    }
    public static void TestCaseDiff024() {
        //Test Case 024
        UsageDOEDPFine.TestCase = 24;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-024";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise024";
        TestCaseRow="Row=24 5 5 1 1 2 1 2 1 2";
    }
    public static void TestCaseDiff025() {
        //Test Case 025
        UsageDOEDPFine.TestCase = 25;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-025";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise025";
        TestCaseRow="Row=25 5 1 2 1 1 2 3 2 3";
    }
    public static void TestCaseDiff026() {
        //Test Case 026
        UsageDOEDPFine.TestCase = 26;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-026";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise026";
        TestCaseRow="Row=26 5 2 1 2 1 1 4 1 1";
    }
    public static void TestCaseDiff027() {
        //Test Case 027
        UsageDOEDPFine.TestCase = 27;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-027";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise027";
        TestCaseRow="Row=27 5 3 1 1 2 1 5 2 3";
    }
    public static void TestCaseDiff028() {
        //Test Case 028
        UsageDOEDPFine.TestCase = 28;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-028";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise028";
        TestCaseRow="Row=28 5 4 1 2 1 2 1 1 1";
    }
    public static void TestCaseDiff029() {
        //Test Case 029
        UsageDOEDPFine.TestCase = 29;
        UsageDOEDPFine.EqnRegressionDiffStatus = "All Green Including Values";
        UsageDOEDPFine.TestCaseName = "Standard:No Coefficients:Basic Equation";
        UsageDOEDPFine.TestCaseMatrixID="TestCase:Row-029";
        HadamardNoise.GPConfigFileName="DOEDPFine\\DOEDPNoise029";
        TestCaseRow="Row=29 5 5 1 1 2 1 2 1 2";
    }
}
