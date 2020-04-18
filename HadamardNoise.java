/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaguchHadamardNoise;

import static TaguchHadamardNoise.MathsContxtLAv0_1_Prod.MyFuncDiff;
import static TaguchHadamardNoise.MathsContxtLAv0_1_Prod.MyFuncExpress;
import static TaguchHadamardNoise.MathsContxtLAv0_1_Prod.MyFuncSimple;
import static TaguchHadamardNoise.MathsContxtLAv0_1_Prod.parse;
import static TaguchHadamardNoise.MathsContxtLAv0_1_Prod.parseDiff;
import static TaguchHadamardNoise.MathsContxtLAv0_1_Prod.parseIntegr;
import static TaguchHadamardNoise.MathsContxtLAv0_1_Prod.parseSimple;
import static TaguchHadamardNoise.Usage.FormEquation;
import static TaguchHadamardNoise.UsageDOE.mainRegresssionDOE;
import static TaguchHadamardNoise.UsageDOEDPFine.FailingTestHarnessDiff;
import static TaguchHadamardNoise.UsageDOEDPFine.mainRegresssionDOEDP;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class HadamardNoise {

    public static Logger log = Logger.getLogger(HadamardNoise.class.getName());
    // Global Variables
    public static double SYSTEM_BIGNUMBER = 1.0e50;
    public static double SYSTEM_Delta = 1.0e-28;
    public static double SYSTEM_BIGNUMBERnPr = 1.0e6;
    public static String Path = "data\\";
    public static String ConfigFileName = "DOEHadamardDefault";
    public static String GPConfigFileName = "DOEDPFine\\DOEDPNoiseDefault";
    public static int STATSMOREDATAFACTOR = 3;
    public static long ROWSDOE = 1;
    public static long ROWSDOEComputed = 0;
    public static long ROWSDOERecommended = 0;
    public static long LengthRecommended = 0;
    // Number of Levels of each Factor (Signal Columns) in Ortho Matrix
    public static int LEVELS = 5;
    public static long Length = 1000;// Length (Number of Signal Columns of Orthogonal Matrix)

    // HyperDOE Data Elements
    // High Level Variables for Signal Noise Nature
    public static boolean HasNoise = true;
    public static boolean UseLevelsFromFile = false;
    public static boolean UseLevelsFromArray = false;

    // Generation of DOE Boolean Control
    public static boolean ClassicTaguchiAlg = false;
    public static boolean UseFullFactorial = true;
    public static boolean PrintStackTraceFlag = false;

    //Signal Data Elements
    private static long OverallDupCount = 0;
    private static long OverallUniqueCount = 0;
    private static LinkedList<String> DupsList;
    //Variable for Levels Control
    private static int levelCntrl = 1;
    //Variable for Levels Control
    private static int levelCntrlBack = 1;
    // Number of positions to interchange when we get a Duplicate
    public static int FixDepth = 20;
    // Number of times Dups have been detected
    public static int DupCount = 0;
    // Number of times Dups have been detected
    public static int ZeroCount = 0;
    public static long DupCheckColStart;
    public static long DupCheckColEnd;
    public static long DupCheckRowStart;
    // Boolean Type of Columns
    public static boolean HasNormal = true;
    //Data Array
    private static ByteBuffer DataArray;

    public static String[] MeanColModeTracker;//Length :Number of Datum
    public static int[] ColMode;//Length :Number of Datum:The Frequency of each  Value
    public static int[] ColModeValue;//Value :Individual Different Datum

    public static String[] MeanInputModeTracker;//SigLength :Number of Datum
    public static long[] Mode;//SigLength :Number of Datum:The Frequency of each  Value
    public static long[] ModeValue;//Value :Individual Different Datum
    public static long[] ModeIndex;//Value :Individual Different Datum

    public static int[][] StrengthMode;
    public static int[] DataCountSum = null;
    //LCG Variables
    private static long LCG_TempValue = 1;
    private static long LCG_Value = 1;
    private static long LCG_levelCntrl = 1;
    private static long LCG_FactValue = 1;
    private static long LCG_NPRFactValue = 1;
    private static long SimpleModuloLevelXn = 1;
    private static long SimpleModuloLevelXnRow = 1;

    private static ByteBuffer LevelCntrlArray;

    //Storage Tweak
    public static int SIZEOFCELL = 8;

    public static double[][] LevelValueStore;

    public static int[] LevelValueInit;//-1=LevelValuePair Not Initialized

    public static String[] Name;//Name of the Column(Display)
    //InitLevels is used only for holding Control Level Bits.
    // Noise Levels are = LEVELS
    // Rows of InitLevels= Noise Column Length
    private static int[][] InitLevels = {
        {0, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //Column, LevelMinIndex, LevelMaxIndex, LevelCtrlIndex, LastIssuedLevel, LastIssuedValue, FactorialRow1Lev, FactSpreadRow1Lev, FactRowRow1Lev, FactRowSpreadRow1Lev, FactorialRow3Lev, FactSpreadRow3Lev, FactRowRow3Lev, FactRowSpreadRow3Lev, FactorialNMinusLev, FactSpreadNMinusLev, FactRowNMinusLev, FactRowSpreadNMinusLev, 
        {1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {4, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {5, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {6, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {7, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {8, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};
    public static int LevelMinIndex = 0;
    public static int LevelMaxIndex = 1;
    public static int LevelCtrlIndex = 2;
    public static int LastIssuedLevel = 3;
    public static int LastIssuedValue = 4;

    public static int FactorialRow1Lev = 5;
    public static int FactSpreadRow1Lev = 6;
    public static int FactRowRow1Lev = 7;

    public static int FactorialRow3Lev = 8;
    public static int FactSpreadRow3Lev = 9;
    public static int FactRowRow3Lev = 10;

    public static int FactorialNMinusLev = 11;
    public static int FactSpreadNMinusLev = 12;
    public static int FactRowNMinusLev = 13;

    //Statistical Data Variables (Super Variables)
    private static long rowdimension;
    private static long coldimension;

    public double[][] MeanTracker;
    public double[][] VarianceTracker;
    public double[][] MeanOutputTracker;
    public double[][] VarianceOutputTracker;
    public String[] MeanOutputModeTracker;//Length :Number of Datum

    public static Long DOEBestBitsStr = 0L;
    public static double DOEBestOutput = 0;
    private double SmallBetter = 0.0;
    private double NominalBest = 0.0;
    private double LargerBetter = 0.0;
    private double DataMinInp = 0.0;
    private double DataMaxInp = 0.0;
    private double ErrorDiffInp = 0.0;
    private double DataDiffInp = 0.0;
    private double DataDiff = 0.0;
    private int sizeyi = 0;
    private int Outputsizeyi = 0;
    private double DataMinOut = 0.0;
    private double DataMaxOut = 0.0;
    private double DataDiffOut = 0.0;
    private double ErrorDiffOut = 0.0;
    public double MeanWithinGroupsInput;
    public double MeanWithinGroupsOutput;

    //Value Tracker Data Structs
    private IntBuffer ValueTrkIndex;
    private IntBuffer RowValueTrkIndex;

    // Column Equation Details for each Input Column
    public static ColConfigration[] EqnDetails;
    public static int[] EqnParsed;
    // Random Generator Values & Variables
    private static Map<String, Double> variables = new HashMap<>();
    private static Map<String, String> variablesDiff = new HashMap<>();

    // GP Parameters
    public static int Seed = 0;
    public static int LCG_ValueInit = 0;
    public static int LCG_ValueOrder = 0;
    public static String EqnLCG_Value = "";
    public static int LCG_levelCntrlInit = 0;
    public static int LCG_levelCntrlOrder = 0;
    public static String EqnLCG_levelCntrl = "";
    public static int LCG_FactValueInit = 0;
    public static int LCG_FactValueOrder = 0;
    public static String EqnLCG_FactValue = "";
    public static int LCG_NPRFactValueInit = 0;
    public static int LCG_NPRFactValueOrder = 0;
    public static String EqnLCG_NPRFactValue = "";
    public static int LCG_FactorialInit = 0;
    public static int LCG_FactorialOrder = 0;
    public static String EqnLCG_Factorial = "";

    public static boolean UseSoftwareNoise = true;
    public static boolean UseHardwareNoise = true;
    public static boolean UseIntegration = true;
    public static boolean AvoidStackOverflow = true;

    public static void GenerateDOEHammard(int planeNum, long rows, long col) {
        try {
            long MyStartROWSDOE = ROWSDOE;
            long MyStartCOLMDOE = Length;
            long Levels = LEVELS;
            long LastZ = MyStartCOLMDOE - 1;

            DupCheckColStart = 0;
            DupCheckColEnd = MyStartCOLMDOE;
            long StartRowOrg = 0;
            long StartColOrg = DupCheckColStart;

            long DupCheckColStartOrg = DupCheckColStart;//DupCheckColStart;
            long DupCheckColEndOrg = DupCheckColEnd;//DupCheckColEnd;

            if ((UseFullFactorial == false)) {
                log.error("GenerateDOEHammard:Signal Dimensions Being Processed:");
                if (HasNoise == true) {
                    GenerateArray(planeNum);
                }
            } else if ((UseFullFactorial == true)) {
                if (HasNoise == true) {
                    GenerateArrayFF(planeNum);
                }
            }

            long i = 0;

            DupCheckColStart = 2;
            DupCheckColEnd = MyStartCOLMDOE;
            log.fatal("Starting Duplicate Check:OverallDupCount=" + OverallDupCount + " DupCheckColStart=" + DupCheckColStart + " DupCheckColEnd=" + DupCheckColEnd);
            DupCount = 0;
            long OverallDupCountOrg = OverallDupCount;
            if ((HasNoise == true) && (HasNormal == true)) {
                log.fatal("GenerateDOEHammard:Started Signal DupCheckMatrixMode");
                DupCheckMatrixMode(planeNum);
            }
            System.out.print(System.lineSeparator());

            long OverallDupCountDiff = OverallDupCount - OverallDupCountOrg;
            System.out.println("Overall Duplicate Count=" + OverallDupCountDiff + " OverallUniqueCount=" + OverallUniqueCount);
            AlgoParamLogger("Main", StartRowOrg, StartColOrg, DupCheckColStart, DupCheckColEnd, OverallDupCountOrg, MyStartROWSDOE, MyStartCOLMDOE, OverallDupCount);

        } catch (Exception HyperE) {
            log.info("Exception: GenerateDOEHammard");
            System.out.println("Exception: GenerateDOEHammard");
            System.out.println(HyperE.getStackTrace());
            System.out.println(HyperE.getMessage());
            HyperE.printStackTrace();
        }
    }

    /* ********************                                  ************ */
 /* ********************                                  ************ */
 /* ************************DOEMatrixArray Core Cell Generating Functions Start Here* */
 /* ********************                                  ************ */
 /* ********************                                  ************ */
    private static void AlgoParamLogger(String AlgoName, long StartRowOrg, long StartColOrg, long DupCheckColStartOrg, long DupCheckColEndOrg, long OverallDupCountOrg, long StartRowEnd, long StartColEnd, long OverallDupCountEnd) {
        log.error("AlgoParamLogger:Logging Parameters for AlgoName=" + AlgoName);
        log.error("AlgoName=" + AlgoName + " StartRowOrg=" + StartRowOrg);
        log.error("AlgoName=" + AlgoName + " StartRowEnd=" + StartRowEnd);
        log.error("AlgoName=" + AlgoName + " StartColOrg=" + StartColOrg);
        log.error("AlgoName=" + AlgoName + " StartColEnd=" + StartColEnd);
        log.error("AlgoName=" + AlgoName + " DupCheckColStartOrg=" + DupCheckColStartOrg);
        log.error("AlgoName=" + AlgoName + " DupCheckColEndOrg=" + DupCheckColEndOrg);
        log.error("AlgoName=" + AlgoName + " OverallDupCountOrg=" + OverallDupCountOrg);
        log.error("AlgoName=" + AlgoName + " OverallDupCountEnd=" + OverallDupCountEnd);
        log.error("AlgoName=" + AlgoName + " Dup Diff Count=" + (OverallDupCountEnd - OverallDupCountOrg));
        log.error("AlgoName=" + AlgoName + " Row Count=" + (StartRowEnd - StartRowOrg));
        if (StartColEnd > StartColOrg) {
            log.error("AlgoName=" + AlgoName + " Col Count=" + (StartColEnd - StartColOrg));
        } else {
            log.error("AlgoName=" + AlgoName + " Col Count=" + (StartColOrg - StartColEnd));
        }

    }

    /* ********************                                  ************ */
 /* ********************                                  ************ */
 /* ************************Signal Core Cell Generating Functions Start Here* */
 /* ********************                                  ************ */
 /* ********************                                  ************ */
    public static void GenerateArray(int planeNum) {
        try {
            long NumROWSDOE = ROWSDOE;
            long NumCOLMDOE = Length;
            long Levels = LEVELS;
            double levelCntrl = 1;
            if (HasNormal == true) {
                setArrayLevelCntrlArray(planeNum);
                setArrayDataArray(planeNum);
                if (Checker(planeNum, false, HasNormal, (int) Length) == true)
                    ;// Do nothing
                else {
                    log.error("GenerateArray:Signal:NormalChecker Failed:Should get an Exception");
                }
                levelCntrl = LevelCntrlArrayGet(planeNum, 0, LevelMinIndex);
                RowLevel2Levels(planeNum, 0, 0);
                log.error("GenerateArray:Signal: RowLevel2Levels Completed ");
                //printDataOutput(0);

                if (ClassicTaguchiAlg == true) {
                    ClassicTaguchiRowLevel3Levels(planeNum);
                } else {
                    if (((NumROWSDOE >= 3) && (NumROWSDOE >= 10 * Levels))) {
                        RowLevel3Levels(planeNum, 0, 0, 0);;
                    } else if ((NumROWSDOE >= 3) && (NumROWSDOE <= 10 * Levels) && (NumROWSDOE > Levels)) {
                        RowLevel3Levels(planeNum, 0, 0, 0);;
                    } else {
                        log.error("GenerateArray:Signal: Skipping RowLevel3Levels");
                    }
                    log.error("GenerateArray:Signal: RowLevel3Levels Completed ");
                    //printDataOutput(0);

                    if (((NumROWSDOE >= 3) && (NumROWSDOE >= 10 * Levels))) {
                        levelCntrl = LevelCntrlArrayGet(planeNum, NumCOLMDOE - 2, LevelMinIndex);
                        ColumnNMinus2Levels(planeNum, (long) Levels, (long) (NumCOLMDOE - 2), ((int) LevelCntrlArrayGet(planeNum, NumCOLMDOE - 2, LevelMaxIndex)), 0);
                        log.error("GenerateArray:Signal: ColumnNMinus2Levels Completed ");
                        levelCntrl = LevelCntrlArrayGet(planeNum, NumCOLMDOE - 1, LevelMinIndex);
                        ColumnN(planeNum, Levels, NumCOLMDOE - 1, Levels);
                        log.error("GenerateArray:Signal: ColumnN Completed ");
                    } else if ((NumROWSDOE >= 3) && ((NumCOLMDOE - 2) > LEVELS)) {
                        levelCntrl = LevelCntrlArrayGet(planeNum, NumCOLMDOE - 2, LevelMinIndex);
                        ColumnNMinus2Levels(planeNum, (long) Levels, (long) (NumCOLMDOE - 2), ((int) LevelCntrlArrayGet(planeNum, NumCOLMDOE - 2, LevelMaxIndex)), 0);
                        log.error("GenerateArray:Signal: ColumnNMinus2Levels Completed ");
                        levelCntrl = LevelCntrlArrayGet(planeNum, NumCOLMDOE - 1, LevelMinIndex);
                        ColumnN(planeNum, Levels, NumCOLMDOE - 1, Levels);
                        log.error("GenerateArray:Signal: ColumnN Completed ");
                    } else {
                        log.error("GenerateArray:Signal: Skipping ColumnNMinus2Levels & ColumnN ");
                    }
                }
            }
            log.info(System.lineSeparator());
        } catch (Exception HyperE) {
            log.info("Exception: GenerateArray:Signal:");
            System.out.println("Exception: GenerateArray:Signal:");
            System.out.println(HyperE.getStackTrace());
            System.out.println(HyperE.getMessage());
            HyperE.printStackTrace();
        }
    }

    public static void GenerateArrayFF(int planeNum) {
        try {
            log.error("GenerateArrayFF:Signal:Entered");
            long NumROWSDOE = ROWSDOE;
            long NumCOLMDOE = Length;
            long Levels = LEVELS;
            double levelCntrl = 1;
            if (HasNormal == true) {
                setArrayLevelCntrlArray(planeNum);
                setArrayDataArray(planeNum);
                if (Checker(planeNum, false, HasNormal, (int) Length) == true)
                    ;// Do nothing
                else {
                    log.error("GenerateArrayFF:Signal:NormalChecker Failed:Should get an Exception");
                }
                log.fatal("GenerateArrayFF:Signal: RowLevelupto1Levels Completed ");
                ColumnFullFactorial(planeNum, 0, NumCOLMDOE, Levels);
                //printDataOutput(0);
            }
            log.fatal("GenerateArrayFF:Exited");
        } catch (Exception HyperE) {
            log.info("Exception: GenerateArrayFF:Signal:");
            System.out.println("Exception: GenerateArrayFF:Signal:");
            System.out.println(HyperE.getStackTrace());
            System.out.println(HyperE.getMessage());
            HyperE.printStackTrace();
        }
    }

    /* ********************                                  ************ */
 /* ********************                                  ************ */
 /* ************************Signal Cell Generating Functions Start Here ***** */
 /* ********************                                  ************ */
 /* ********************                                  ************ */
 /* ********************                                  ************ */
    public static void RowLevel2Levels(int planeNum, long Levels, long col) {
        log.error("Processing RowLevel2Levels ");
        long NumROWSDOE = ROWSDOE;
        long NumCOLMDOE = Length;
        long row = 0;
        long StartRowOrg = Levels;
        long StartColOrg = col;
        long OverallDupCountOrg = OverallDupCount;
        long DupCheckColStartOrg = col;//DupCheckColStart;
        long DupCheckColEndOrg = NumCOLMDOE - Levels + 1;//DupCheckColEnd;

        long LastZ = NumCOLMDOE;
        long RowNum = NumROWSDOE;
        long ColNum = NumCOLMDOE - Levels + 1;
        long TempValue = 0;
        if (NumROWSDOE > (10 * Levels)) {
            RowNum = (10 * Levels);
        } else if ((NumROWSDOE < (10 * Levels)) && (NumROWSDOE > Levels)) {
            RowNum = 2 * Levels;
        } else {
            RowNum = NumROWSDOE;
        }
        ComputeFactorial(planeNum, "RowLevel2Levels",
                col,
                LEVELS,
                (NumCOLMDOE),
                RowNum,
                ColNum,
                FactorialRow1Lev,
                FactSpreadRow1Lev,
                FactRowRow1Lev
        );
        for (; (row < RowNum); row++) {
            for (int j = (int) col; (j < NumCOLMDOE); j++) {
                log.info("RowLevel2Levels  row=" + row + " col=" + j
                        + " FactorialRow1Lev Value=" + LevelCntrlArrayGet(planeNum, j, FactorialRow1Lev)
                        + " FactSpreadRow1Lev Value=" + LevelCntrlArrayGet(planeNum, j, FactSpreadRow1Lev)
                        + " FactRowRow1Lev Value=" + LevelCntrlArrayGet(planeNum, j, FactRowRow1Lev));
                TempValue = ComplexCommonRCMain(planeNum, "RowLevel1Levels",
                        /* row*/ (long) row,
                        /* col*/ (long) j,
                        /* Levels*/ (long) LevelCntrlArrayGet(planeNum, j, LevelMaxIndex),
                        /*Value*/ (long) LevelCntrlArrayGet(planeNum, j, LastIssuedValue),
                        /* RowSub*/ 1,
                        /*ColSub*/ (long) 1,
                        /*AlgoRowFactIndex*/ FactorialRow1Lev,
                        /* AlgoRowSpreadIndex*/ FactSpreadRow1Lev,
                        /*AlgoFactRowFactIndex*/ FactRowRow1Lev);
                LevelCntrlArrayPut(planeNum, j, LastIssuedValue, TempValue);
            }
            for (int x = (int) col; (x < NumCOLMDOE - Levels + 1); x++) {
                long TempValue1 = LevelCntrlArrayGet(planeNum, x, FactRowRow1Lev);
                LevelCntrlArrayPut(planeNum, x,
                        FactRowRow1Lev,
                        (TempValue1 - 1));
            }
            if (row < RowNum) {
                //FindnFixDuplicate(planeNum, "RowLevel1Levels", row, DupCheckColStartOrg, DupCheckColEndOrg, NumCOLMDOE, LastZ);
                //DupCheckColMode( planeNum);
            }
        }
        AlgoParamLogger("RowLevel2Levels", StartRowOrg, StartColOrg, DupCheckColStartOrg, DupCheckColEndOrg, OverallDupCountOrg, row, (NumCOLMDOE - Levels + 1), OverallDupCount);
        log.error("Completed RowLevel2Levels ");
    }

    public static void RowLevel3Levels(int planeNum, long row, long col, long Value) {
        log.error("Processing RowLevel3Levels ");
        long NumROWSDOE = ROWSDOE;
        long NumCOLMDOE = Length;
        long Levels = LEVELS;

        long LastZ = NumCOLMDOE;
        long StartRowOrg = row;
        long StartColOrg = col;
        long OverallDupCountOrg = OverallDupCount;
        long DupCheckColStartOrg = col;//DupCheckColStart;
        long DupCheckColEndOrg = NumCOLMDOE - Levels + 1;//DupCheckColEnd;

        long RowNum = row;
        long ColNum = 0;

        if (NumROWSDOE > (10 * Levels)) {
            RowNum = NumROWSDOE;//(10 * Levels);
        } else if ((NumROWSDOE < (10 * Levels)) && (NumROWSDOE > Levels)) {
            RowNum = NumROWSDOE;
        } else {
            RowNum = NumROWSDOE;
        }

        ComputeFactorial(planeNum, "RowLevel3Levels", col, Levels, (NumCOLMDOE - Levels + 1), RowNum, ColNum,
                FactorialRow3Lev,
                FactSpreadRow3Lev,
                FactRowRow3Lev);

        for (; row < RowNum; row++) {
            for (int j = (int) col; j < NumCOLMDOE; j++) {
//                log.error("RowLevel3Levels  row=" + row + " col=" + j 
//                        + " FactorialRow1Lev Value=" +  LevelCntrlArrayGet(planeNum, j,  FactorialRow3Lev) 
//                        + " FactSpreadRow1Lev Value=" +  LevelCntrlArrayGet(planeNum, j,  FactSpreadRow3Lev) 
//                        + " FactRowRow1Lev Value=" +  LevelCntrlArrayGet(planeNum, j,  FactRowRow3Lev) );
                long TempValue = ComplexCommonRCMain(planeNum, "RowLevel3Levels",
                        /* row*/ (long) row,
                        /* col*/ (long) j,
                        /* Levels*/ (long) LevelCntrlArrayGet(planeNum, j, LevelMaxIndex),
                        /*Value*/ (long) LevelCntrlArrayGet(planeNum, j, LastIssuedValue),
                        /* RowSub*/ 1,
                        /*ColSub*/ (long) 1,
                        /*AlgoRowFactIndex*/ (long) FactorialRow3Lev,
                        /* AlgoRowSpreadIndex*/ (long) FactSpreadRow3Lev,
                        /*AlgoFactRowFactIndex*/ (long) FactRowRow3Lev
                );
                log.info("RowLevel3Levels:First For Loop:  row=" + row + " col=" + j + " TempValue=" + TempValue);
                LevelCntrlArrayPut(planeNum, j, LastIssuedValue, TempValue);
                if (row < RowNum) {
                    //FindnFixDuplicate(planeNum, "RowLevel3Levels", row, DupCheckColStartOrg, DupCheckColEndOrg, NumCOLMDOE, LastZ);
                    //DupCheckColMode( planeNum);
                }
            }
        }
        AlgoParamLogger("RowLevel3Levels", StartRowOrg, StartColOrg, DupCheckColStartOrg, DupCheckColEndOrg, OverallDupCountOrg, row, (NumCOLMDOE - Levels + 1), OverallDupCount);
        log.error("Completed RowLevel3Levels ");
    }

    public static void ColumnNMinus2Levels(int planeNum, long row, long col, long Levels1, long Value) {

        log.info("Processing ColumnNMinus2Levels ");

        try {
            long NumROWSDOE = ROWSDOE;
            long NumCOLMDOE = Length;
            long Levels = LEVELS;
            long LevelSpread = NumCOLMDOE / Levels;
            long LastZ = col;
            long j = col;
            long StartRowOrg = row;
            long StartColOrg = col;
            long OverallDupCountOrg = OverallDupCount;
            long DupCheckColStartOrg = col - Levels;//DupCheckColStart;
            long DupCheckColEndOrg = col;//DupCheckColEnd;

            long RowNum = NumROWSDOE - (10 * Levels);
            long ColNum = Levels;
            ComputeFactorial(planeNum, "ColumnNMinus2Levels", col, Levels, (Levels), RowNum, ColNum,
                    FactorialNMinusLev,
                    FactSpreadNMinusLev,
                    FactRowNMinusLev
            );
            for (; (row < NumROWSDOE); row++) {
                for (j = col; (j > (col - Levels + 1)); j--) {
                    log.error("ColumnNMinus2Levels col=" + j + " row=" + row + " ColumnNMinus2Levels Value=" + Value);
                    LevelCntrlArrayPut(planeNum, j,
                            LastIssuedValue, (ComplexCommonRCMain(planeNum, "ColumnNMinus2Levels", row, j, Levels,
                                    LevelCntrlArrayGet(planeNum, j,
                                            LastIssuedValue), 8, Levels,
                                    FactorialNMinusLev,
                                    FactSpreadNMinusLev,
                                    FactRowNMinusLev
                            )));
                }
                for (int x = (int) col; (x < col - Levels + 1); x++) {
                    LevelCntrlArrayPut(planeNum, x,
                            FactRowNMinusLev, ((LevelCntrlArrayGet(planeNum, x,
                                    FactRowNMinusLev) - 1)));
                }
                if (row < ROWSDOE) {
                    //FindnFixDuplicate(planeNum, "ColumnNMinus2Levels", row, DupCheckColStartOrg, DupCheckColEndOrg, col, LastZ);
                    //DupCheckColMode( planeNum);
                }
            }
            AlgoParamLogger("ColumnNMinus2Levels", StartRowOrg, StartColOrg, DupCheckColStartOrg, DupCheckColEndOrg, OverallDupCountOrg, row, (col - Levels - 1), OverallDupCount);
        } catch (Exception HyperE) {
            log.info("Exception: ColumnNMinus2Levels");
            System.out.println("Exception: ColumnNMinus2Levels");
            System.out.println(HyperE.getStackTrace());
            System.out.println(HyperE.getMessage());
            HyperE.printStackTrace();
        }
        log.info("Completed ColumnNMinus2Levels ");
    }

    public static void ColumnN(int planeNum, long row, long col, long Levels) {
        log.info("Processing ColumnN ");
        try {
            long NumROWSDOE = ROWSDOE;
            int NumCOLMDOE = (int) Length;
            long LevelSpread = NumROWSDOE / Levels;
            for (;
                    ((row < NumROWSDOE)
                    && (col < NumCOLMDOE));
                    row++) {

                log.error("ColumnN col=" + col + " row=" + row);
                ColumnOneMain(planeNum, row, col,
                        LevelCntrlArrayGet(planeNum, col,
                                LevelMaxIndex));
            }
        } catch (Exception HyperE) {
            log.info("Exception: ColumnN");
            System.out.println("Exception: ColumnN");
            System.out.println(HyperE.getStackTrace());
            System.out.println(HyperE.getMessage());
            HyperE.printStackTrace();
        }
        log.info("Completed ColumnN ");
    }

    public static void ColumnFullFactorial(int planeNum, long row, long col, long Levels) {
        log.error("Processing ColumnFullFactorial ");
        try {
            long NumROWSDOE = ROWSDOE;
            long NumCOLMDOE = Length;
            long LevelSpread = 0;
            if (Levels > 0) {
                LevelSpread = NumROWSDOE / Levels;
            }
            long FullFactorialROWS = 1;
            for (int j = 0; (j < NumCOLMDOE); j++) {
                if (UsageDOEDPFine.TestCase < 10) {
                    FailingTestHarnessDiff("ColumnFullFactorial", UsageDOEDPFine.TestCase);
                    UsageDOEDPFine.TestCase++;
                    if (UsageDOEDPFine.TestCase == 10) {
                        UsageDOEDPFine.TestCase = 0;
                    }
                }
                for (int i = (int) 0; (i < NumROWSDOE); i++) {
                    FileInputStream fpinput = null;
                    String msg = "";
                    if ((fpinput = new FileInputStream(Path + GPConfigFileName)) == null) {
                        msg = "Input: can't open " + Path + GPConfigFileName;
                        System.out.println(msg);
                        log.error(msg);
                    }
                    //log.error("Opened File Name: Infile=" + Path + ConfigFileName);
                    //System.out.println("Opened File Name: Infile=" + Path + ConfigFileName);
                    NoiseDOEDefineDP.Myfscanf(fpinput);
                    //NoiseDOEDefineGP.Myprintf();

                    if (j < (NumCOLMDOE - 1)) {
                        ColumnFullFactorialRecursiveMain(planeNum, i, j,
                                LevelCntrlArrayGet(planeNum, j,
                                        LevelMaxIndex), NumCOLMDOE, LevelSpread);
                    } 
                    //Dup Prevention Strategy  AND 
                    // Noise Reduction to Zero on RIGHT SIDE of Hadamard OA
                    // Noise moves from Left to Right Column
                    // i.e No information Set Column 
                    // i.e No Variability && Fully Controlled.
                    else if (j == (NumCOLMDOE - 1)) {
                        ColumnZero(planeNum, 0, j, Levels);
                    }
                }
            }
        } catch (Exception HyperE) {
            log.info("Exception: ColumnFullFactorial UsageDOEDPFine.TestCase=" + UsageDOEDPFine.TestCase + " UsageDOE.TestCase=" + UsageDOE.TestCase);
            System.out.println("Exception: ColumnFullFactorial UsageDOEDPFine.TestCase=" + UsageDOEDPFine.TestCase + " UsageDOE.TestCase=" + UsageDOE.TestCase);
            System.out.println(HyperE.getStackTrace());
            System.out.println(HyperE.getMessage());
            HyperE.printStackTrace();
        }
        log.error("Completed ColumnFullFactorial ");
    }

    public static void ColumnZero(int planeNum, long row, long col, long Levels) {

        log.error("Processing ColumnZero ");
        try {
            long NumROWSDOE = ROWSDOE;
            long NumCOLMDOE = Length;
            long LevelSpread = 0;
            if (Levels > 0) {
                LevelSpread = NumROWSDOE / Levels;
            }

            for (;
                    (((getArrayDataArray().limit() != 0))
                    && (row < NumROWSDOE));
                    row++) {
                //Coarse Control
                //Block Level Dups Reduced
                //ColZeroMain(planeNum, row, col, Levels, LevelSpread);
                //Fine Control
                //LSB Level Dups Reduced
                ColumnOneMain(planeNum, row, col,  LevelCntrlArrayGet(planeNum, col,  LevelMaxIndex));
            }
        } catch (Exception HyperE) {
            log.info("Exception: ColumnZero");
            System.out.println("Exception: ColumnZero");
            System.out.println(HyperE.getStackTrace());
            System.out.println(HyperE.getMessage());
            HyperE.printStackTrace();
        }
        log.error("Completed ColumnZero ");
    }

    public static void ColZeroMain(int planeNum, long row, long col, long Levels, long LevelSpread) {
        int levelCntrl = LevelCntrlArrayGet(planeNum, col, LevelCtrlIndex);
        DataArrayPut(planeNum, row, col, (byte) LevelCntrlArrayGet(planeNum, col, LevelCtrlIndex));
        LevelCntrlArrayPut(planeNum, col, LevelCtrlIndex, levelCntrl);
        LevelCntrlArrayPut(planeNum, col, LastIssuedLevel, levelCntrl);
        if ((LevelSpread > 0) && ((row > 0) && ((row + 1) % LevelSpread) == 0)) {
            LevelCntrlArrayPut(planeNum, col, LastIssuedLevel, LevelCntrlArrayGet(planeNum, col, LevelMinIndex));
            if (levelCntrl == LevelCntrlArrayGet(planeNum, col, LevelMaxIndex)) {
                levelCntrl = LevelCntrlArrayGet(planeNum, col, LevelMinIndex);
            } else {
                levelCntrl++;
            }
        }
        LevelCntrlArrayPut(planeNum, col, LevelCtrlIndex, levelCntrl);
    }

    public static void ClassicTaguchiRowLevel3Levels(int planeNum) {
        log.error("Processing ClassicTaguchiRowLevel3Levels ");
        long NumROWSDOE = ROWSDOE;
        long NumCOLMDOE = Length;
        long Levels = LEVELS;
        long row = 0;
        if (NumROWSDOE > (10 * Levels)) {
            row = 10 * Levels;//(10 * Levels);
        } else if ((NumROWSDOE < (10 * Levels)) && (NumROWSDOE > Levels)) {
            row = Levels;
        } else {
            row = Levels;
        }

        int col = 3;
        long Value = 0;
        long LastZ = NumCOLMDOE - 1;
        long DupCheckColStartOrg = DupCheckColStart;
        long DupCheckColEndOrg = DupCheckColEnd;
        for (; row < NumROWSDOE; row++) {
            for (col = 2; col < NumCOLMDOE; col++) {
                Levels = LevelCntrlArrayGet(planeNum, col, LevelMaxIndex);
                // Caution:Positional "If then Else" Blocks
                // Results will be different if position is interchanged
                if ((((row - Levels + 1) % (Levels + 1)) != 0) && ((col - 3 + 1) % (Levels + 1)) != 0) {
                    Value = ((row - Levels + 1) % (Levels + 1)) + ((col - 3 + 1) % (Levels + 1));
                } else if (((row - Levels + 1) % (Levels + 1)) != 0) {
                    Value = ((col - 3 + 1) % (Levels + 1));
                } else if (((col - 3 + 1) % (Levels + 1)) != 0) {
                    Value = ((row - Levels) % (Levels + 1));
                } else {
                    Value = levelCntrl;
                }
                if (Value >= Levels) {
                    Value = Value % (Levels + 1);
                }
//                SimpleModuloLevelXn=Value;
//                Value = SimpleModuloLevel(planeNum, "ClassicTaguchiRowLevel3Levels", row, col,
//                    SimpleModuloLevelXn,
//                    Levels, LevelCntrlArrayGet(planeNum, col,  LevelMinIndex));
//                SimpleModuloLevelXn = Value;    
                if (Value == 0) {
                    Value = 1;
                }
                DataArrayPut(planeNum, row, col, (byte) Value);
                if (row < Length) {
                    dupCombinationCol(planeNum, "ClassicTaguchiRowLevel3Levels", row, 0, col, 20, false);
                }
            }
            if (levelCntrl >= Levels) {
                levelCntrl = 1;
            } else {
                levelCntrl++;
            }
            if (levelCntrlBack >= Levels) {
                levelCntrlBack = 2;
            } else {
                levelCntrl++;
            }
            log.info("Completed ClassicTaguchiRowLevel3Levels ");
        }
    }

    public static void ComputeFactorial(int planeNum, String AlgoName,
            long col,
            /*LEVELS*/ long N,
            long R,
            long RowNum,
            long ColNum,
            int FuncFactIndex,
            int SpreadIndex,
            int FuncRowFactIndex) {
        //log.info("ComputeFactorial:" + AlgoName + " RowNum=" + RowNum + " ColNum=" + ColNum);
        long Factorial = Fact(N, 1);
        long RowFactorial = nPr(R, ColNum, 1);
        for (long i = col; i <= (col + R); i++) {
            //if (AlgoName.equalsIgnoreCase("RowLevel3Levels")) 
            //log.info("ComputeFactorial:" + AlgoName + " col=" + i + " Factorial=" + Factorial);
            LevelCntrlArrayPut(planeNum, i, FuncFactIndex, (byte) Factorial);
            long SpreadLevel = 1;
            if (ColNum > 0) {
                SpreadLevel = Factorial / ColNum;
            }
            //if (AlgoName.equalsIgnoreCase("RowLevel3Levels")) 
            //log.info("ComputeFactorial:" + AlgoName + " col=" + i + " SpreadLevel=" + SpreadLevel );
            LevelCntrlArrayPut(planeNum, i, SpreadIndex, (byte) SpreadLevel);
            //if (AlgoName.equalsIgnoreCase("RowLevel3Levels")) 
            //log.info("ComputeFactorial:" + AlgoName + " col=" + i + " RowFactorial=" + RowFactorial);
            LevelCntrlArrayPut(planeNum, i, FuncRowFactIndex, (byte) RowFactorial);
        }
    }

    public static boolean dupCombinationCol(int planeNum, String AlgoName, long row, long colDupCheckColStart, long colDupCheckColEnd, long LastZ, boolean Matrix) {
        boolean RetValue = false;
        String HistRow = "";
        String CurrRow = "";
        int Duptimes = 0;
        log.info("Entered dupCombinationCol row=" + row + " colDupCheckColStart=" + colDupCheckColStart + " colDupCheckColEnd=" + colDupCheckColEnd + " LastZ=" + LastZ);
        long i = 0;
        long j = 0;
        try {
            long upperlimit = row;
            if (Matrix == true) {
                upperlimit = ROWSDOE;
            }
            CurrRow = getRowAsStringDataArray(planeNum, row, colDupCheckColStart, colDupCheckColEnd, false);
            log.info("dupCombinationCol row=" + row + " CurrRow =" + CurrRow);
            for (j = 0; (j < upperlimit) && (Duptimes < 1); j++) {
                if ((j == row) && (Matrix == true)) {
                    continue;
                }
                RetValue = false;
                log.info("dupCombinationCol CurrRow =" + CurrRow);
                HistRow = getRowAsStringDataArray(planeNum, j, colDupCheckColStart, colDupCheckColEnd, false);
                if ((CurrRow.length() == HistRow.length()) && (CurrRow.equalsIgnoreCase(HistRow))) {
                    RetValue = true;
                    log.info("dupCombinationCol Got Duplicates in AlgoName=" + AlgoName + ": HistRow=" + j + " Curr row=" + row);
                    //log.error("dupCombinationRow HistRow =" + HistRow.toString());
                    log.info("dupCombinationCol HistRow(" + j + " =" + HistRow);
                    log.info("dupCombinationCol CurrRow (" + row + " =" + CurrRow);
                    //FixerIncremFast(planeNum, row, colDupCheckColStart, LastZ, FixDepth);
                    log.info("dupCombinationCol FixedRow=" + getRowAsStringDataArray(planeNum, row, colDupCheckColStart, colDupCheckColEnd, true));
                    // Restart combination as the fixed Row may clash 
                    // with previously unique values
                    break;
                }
            }

            if (RetValue == true) {
                log.info("Found Dups in AlgoName=" + AlgoName + " for row(Current Row)=" + row + " i(Historical Row)=" + j + " OverallDupCount=" + OverallDupCount + " DupCount=" + DupCount + " ZeroCount=" + ZeroCount);
                log.info("dupCombinationCol HistRow(" + j + ") =" + getRowAsStringDataArray(planeNum, j, colDupCheckColStart, colDupCheckColEnd, true));
                log.info("dupCombinationCol CurrRow(" + row + ") =" + CurrRow);
            } else if (RetValue == false) {
                log.info("dupCombinationCol:No duplicates for row=" + row);
                RetValue = false;
            }
        } catch (Exception HyperE) {
            log.info("Exception: dupCombinationCol");
            System.out.println("Exception: dupCombinationCol");
            System.out.println(HyperE.getStackTrace());
            System.out.println(HyperE.getMessage());
            HyperE.printStackTrace();
        }
        log.info("Completed dupCombinationCol ");
        return RetValue;
    }

    public static long Fact(long n, long Result) {
        long TempResult = 1;
        if (n > 1) {
            if (Result > SYSTEM_BIGNUMBER) {
                log.info("Fact:Result=" + Result);
                return (long) SYSTEM_BIGNUMBER;
            } else {
                TempResult = Result * n;
                log.info("Fact:Result=" + Result);
                if ((TempResult > SYSTEM_BIGNUMBER) && (Result < SYSTEM_BIGNUMBER)) {
                    return (long) Result;
                } else {
                    return Fact(n - 1, TempResult);
                }
            }
        } else {
            return Result;
        }
    }

    public static long nPr(long n, long r, long Result) {
        long TempResult = 1;
        for (int i = 1; (i <= r) && (n > 0) && (TempResult < SYSTEM_BIGNUMBERnPr) && (TempResult > 0); i++, n--) {
            TempResult = Result * n;
            if (TempResult > 0) {
                Result = TempResult;
            }
            log.info("nPr:Result=" + Result + " n=" + n + " r=" + r + " i=" + i);
        }
        return Result;
    }

    public static long nCr(long n, long r, long Result) {
        long TempResult = 1;
        int i = 1;
        TempResult = Result * nPr(n, r, Result) / Fact(r, 1);
        if (TempResult > 0) {
            Result = TempResult;
        }
        log.info("nCr:Result=" + Result + " n=" + n + " r=" + r + " i=" + i);
        return Result;
    }

    //Normal Input Code
    // Checker Function to Validate class Normal
    public static boolean Checker(int planeNum, boolean UseFlag, boolean HasFlag, Integer HasColumns) {
        if ((HasFlag == true) && (HasColumns > 0)) {
            //if (HyperCubeArray[planeNum].SignalObj.ColNormalArray!=null) {
            {
                if (DataArray != null) {
                    {
                        if (LevelCntrlArray != null) {
                            return true;
                        } else {
                            log.error("NormalChecker:Failed Making Object Null:LevelCntrlArray");
                        }
                    }
                } else {
                    log.error("NormalChecker:Failed Making Object Null:LevelCntrlArray");
                }
            }
            //}
            //else log.error("NormalChecker:Failed Making Object Null:ColNormalArray");
        } else {
            log.error("NormalChecker:Failed Making Object Null:HasNormal:HasColumns");
        }
        //HyperCubeArray[planeNum].SignalObj.ColNormalArray=null;
        return false;
    }

    public static void setArrayLevelCntrlArray(int planeNum) {
        long MyStartROWSDOE = Length;
        long MyStartCOLMDOE = InitLevels[0].length - 1;
        LevelCntrlArray = ByteBuffer.allocateDirect((int) (MyStartROWSDOE * MyStartCOLMDOE * SIZEOFCELL)).order(ByteOrder.nativeOrder());
        if ((LevelCntrlArray == null)) {
            log.error("setArrayLevelCntrlArray:Null LevelCntrlArray for planeNum=" + planeNum + " MyStartROWSDOE=" + MyStartROWSDOE + " MyStartCOLMDOE=" + MyStartCOLMDOE);
        }
        LevelValueStore = new double[(int) Length][LEVELS + 1];
        LevelValueInit = new int[(int) Length];
        Name = new String[(int) Length];
        for (int j = 0; j < Length; j++) {
            LevelValueStore[j] = new double[(int) LEVELS + 1];
            LevelValueInit[j] = -1;
            Name[j] = "";
        }
        for (long j = 0, i = 0; (j < MyStartROWSDOE); j++) {
            LevelCntrlArrayPut(planeNum, j, LevelMinIndex, 1);
            LevelCntrlArrayPut(planeNum, j, LevelMaxIndex, LEVELS);
            LevelCntrlArrayPut(planeNum, j, LevelCtrlIndex, 1);
            LevelCntrlArrayPut(planeNum, j, LastIssuedLevel, 1);
            LevelCntrlArrayPut(planeNum, j, LastIssuedValue, 1);

            LevelCntrlArrayPut(planeNum, j, FactorialRow1Lev, 1);
            LevelCntrlArrayPut(planeNum, j, FactSpreadRow1Lev, 1);
            LevelCntrlArrayPut(planeNum, j, FactRowRow1Lev, 1);

            LevelCntrlArrayPut(planeNum, j, FactorialRow3Lev, 1);
            LevelCntrlArrayPut(planeNum, j, FactSpreadRow3Lev, 1);
            LevelCntrlArrayPut(planeNum, j, FactRowRow3Lev, 1);

            LevelCntrlArrayPut(planeNum, j, FactorialNMinusLev, 1);
            LevelCntrlArrayPut(planeNum, j, FactSpreadNMinusLev, 1);
            LevelCntrlArrayPut(planeNum, j, FactRowNMinusLev, 1);

            log.info("setArrayLevelCntrlArray:Putting Default Values at j=" + j
                    + " Retrieved LevelMinValue=" + LevelCntrlArrayGet(planeNum, j, LevelMinIndex)
                    + " Retrieved LevelMaxValue=" + LevelCntrlArrayGet(planeNum, j, LevelMaxIndex)
                    + " Retrieved LevelCtrlIndex=" + LevelCntrlArrayGet(planeNum, j, LevelCtrlIndex)
                    + " Retrieved LastIssuedLevel=" + LevelCntrlArrayGet(planeNum, j, LastIssuedLevel)
                    + " Retrieved LastIssuedValue=" + LevelCntrlArrayGet(planeNum, j, LastIssuedValue)
                    + " Retrieved FactorialRow1Lev=" + LevelCntrlArrayGet(planeNum, j, FactorialRow1Lev)
                    + " Retrieved FactSpreadRow1Lev=" + LevelCntrlArrayGet(planeNum, j, FactSpreadRow1Lev)
                    + " Retrieved FactRowRow1Lev=" + LevelCntrlArrayGet(planeNum, j, FactRowRow1Lev)
                    + " Retrieved FactorialRow3Lev=" + LevelCntrlArrayGet(planeNum, j, FactorialRow3Lev)
                    + " Retrieved FactSpreadRow3Lev=" + LevelCntrlArrayGet(planeNum, j, FactSpreadRow3Lev)
                    + " Retrieved FactRowRow3Lev=" + LevelCntrlArrayGet(planeNum, j, FactRowRow3Lev)
                    + " Retrieved FactorialNMinusLev=" + LevelCntrlArrayGet(planeNum, j, FactorialNMinusLev)
                    + " Retrieved FactSpreadNMinusLev=" + LevelCntrlArrayGet(planeNum, j, FactSpreadNMinusLev)
                    + " Retrieved FactRowNMinusLev=" + LevelCntrlArrayGet(planeNum, j, FactRowNMinusLev)
            );
            log.error("LevelCntrlArray.size=" + LevelCntrlArray.limit() + " MyStartROWSDOE=" + MyStartROWSDOE + " MyStartCOLMDOE=" + MyStartCOLMDOE + " for planeNum=" + planeNum);
        }
    }

    public static int LevelCntrlArrayGet(int planeNum, long row, long LevelIndex) {
        long MaxRow = Length;
        long MaxCol = InitLevels[0].length - 1;
        if ((LevelCntrlArray != null)
                && ((MaxRow * MaxCol) < LevelCntrlArray.limit())
                && (row < MaxRow)
                && ((row * LevelIndex) < LevelCntrlArray.limit())) {
            long n = ((row) * MaxCol) + LevelIndex;
            if (n <= LevelCntrlArray.limit()) {
                return (int) LevelCntrlArray.get((int) n);
            }
        }
        return -1;
    }

    public static void LevelCntrlArrayPut(int planeNum, long row, long LevelIndex, long Value) {
        long MaxRow = Length;
        long MaxCol = InitLevels[0].length - 1;
        if (LevelCntrlArray == null) {
            log.error("LevelCntrlArrayPut:LevelCntrlArray=null for planeNum=" + planeNum + " MaxRow=" + MaxRow + " MaxCol=" + MaxCol);
            return;
        }
        if ((LevelCntrlArray != null)
                && ((MaxRow * MaxCol) < LevelCntrlArray.limit())
                && (row < MaxRow)
                && ((row * LevelIndex) < LevelCntrlArray.limit())) {
            long n = ((row) * MaxCol) + LevelIndex;
            if (n <= LevelCntrlArray.limit()) {
                if ((Value <= Byte.MAX_VALUE) && (Value >= Byte.MIN_VALUE)) {
                    LevelCntrlArray.put((int) n, (byte) Value);
                } else {
                    LevelCntrlArray.put((int) n, (byte) 1);
                }
            }
        }
    }
    // DataArray Functions

    public static ByteBuffer getArrayDataArray() {
        return DataArray;
    }

    public static void setArrayDataArray(int planeNum) {
        long MyStartROWSDOE = ROWSDOE;
        long MyStartCOLMDOE = Length;
        DataArray = ByteBuffer.allocateDirect((int) (MyStartROWSDOE * MyStartCOLMDOE * SIZEOFCELL)).order(ByteOrder.nativeOrder());
        if ((DataArray == null)) {
            log.error("setArrayDataArray:Null DataArray for planeNum=" + planeNum);
        }

        for (int i = 0; ((i < MyStartROWSDOE)); i++) {
            for (int j = 0; ((j < MyStartCOLMDOE)); j++) {
                {
                    DataArrayPut(planeNum, i, j, (byte) 0);
                    log.info("setArrayDataArray:Putting Got Initial Value DOE Matrix=" + 0 + " at i=" + i + " j=" + j + " Retrieved Value=" + DataArrayGet(planeNum, i, j));
                }
            }
        }
        log.error("setArrayDataArray.size=" + DataArray.limit() + " MyStartROWSDOE=" + MyStartROWSDOE + " MyStartCOLMDOE=" + MyStartCOLMDOE + " for planeNum=" + planeNum);

    }

    public static byte DataArrayGet(int planeNum, long row, long col) {
        long MaxRow = ROWSDOE;
        long MaxCol = Length;
        if ((DataArray != null)
                && ((MaxRow * MaxCol) < DataArray.limit())
                && (row < MaxRow)
                && (col <= MaxCol)
                && ((row * col) < DataArray.limit())) {
            long n = ((row) * MaxCol) + col;
            if (n <= DataArray.limit()) {
                return DataArray.get((int) n);
            }
        }
        log.fatal("DataArrayGet: Size Exceeded:row=" + row + " MaxRow=" + MaxRow + " col=" + col + " MaxCol=" + MaxCol + " this.DataArray.limit()=" + DataArray.limit());
        return 0;
    }

    public static void DataArrayPut(int planeNum, long row, long col, Byte Value) {
        long MaxRow = ROWSDOE;
        long MaxCol = Length;
        if (DataArray == null) {
            log.info("DataArrayPut:DataArray=null for planeNum=" + planeNum);
            return;
        }
        if ((DataArray != null)
                && ((MaxRow * MaxCol) < DataArray.limit())
                && (row < MaxRow)
                && (col <= MaxCol)
                && ((row * col) < DataArray.limit())) {
            long n = ((row) * MaxCol) + col;
            if (n <= DataArray.limit()) {
//                if (LEVELS == 2) {
//                    if(Value==1)
//                        DataArray.put((int) n, (byte)-1);
//                    else if(Value==2)
//                        DataArray.put((int) n, (byte)1);
//                }
//                else
                    DataArray.put((int) n, Value);
            } else {
                log.error("DataArrayPut: Size Exceeded:row=" + row + " col=" + col);
            }
        }
    }

    /* ********************                                  ************ */
 /* ********************                                  ************ */
 /* ************************Normal Core Cell Generating Functions Start Here* */
 /* ********************                                  ************ */
 /* ********************                                  ************ */
 /* ********************                                  ************ */
    public static void ColumnOneMain(int planeNum, long row, long col, long Levels) {
        int levelCntrl = 1;
        levelCntrl = LevelCntrlArrayGet(planeNum, col, LevelCtrlIndex);
        DataArrayPut(planeNum, row, col, (byte) LevelCntrlArrayGet(planeNum, col, LevelCtrlIndex));
        LevelCntrlArrayPut(planeNum, col, LevelCtrlIndex, levelCntrl);
        LevelCntrlArrayPut(planeNum, col, LastIssuedLevel, levelCntrl);
        if (levelCntrl == LevelCntrlArrayGet(planeNum, col, LevelMaxIndex)) {
            levelCntrl = LevelCntrlArrayGet(planeNum, col, LevelMinIndex);
        } else if ((levelCntrl < LevelCntrlArrayGet(planeNum, col, LevelMaxIndex))) {
            levelCntrl++;
        }
        LevelCntrlArrayPut(planeNum, col, LevelCtrlIndex, levelCntrl);
    }

    public static void RowLevelupto1LevelsRecursiveMain(int planeNum, long row, long col) {
        int levelCntrl = 1;
        if (LevelCntrlArrayGet(planeNum, col, LastIssuedLevel) == 0) {
            levelCntrl = LevelCntrlArrayGet(planeNum, col, LevelMinIndex);
        } else if (((LevelCntrlArrayGet(planeNum, col, LastIssuedLevel)) >= (LevelCntrlArrayGet(planeNum, col, LevelMinIndex)))
                && ((LevelCntrlArrayGet(planeNum, col, LastIssuedLevel)) <= (LevelCntrlArrayGet(planeNum, col, LevelMaxIndex)))) {
            levelCntrl = LevelCntrlArrayGet(planeNum, col, LastIssuedLevel);
        }
        LevelCntrlArrayPut(planeNum, col, LevelCtrlIndex, levelCntrl);

        //HyperCubeArray[planeNum].getDataArray().get((int) row)[(int) col].setCell((int) row, (int) col, HyperCube[planeNum].Matrix.LevelCntrlArrayGet(planeNum, col, LevelCtrlIndex));
        DataArrayPut(planeNum, row, col, (byte) LevelCntrlArrayGet(planeNum, col, LevelCtrlIndex));
        LevelCntrlArrayPut(planeNum, col, LevelCtrlIndex, levelCntrl);
        if ((levelCntrl >= LevelCntrlArrayGet(planeNum, col, LevelMaxIndex))
                || (levelCntrl <= LevelCntrlArrayGet(planeNum, col, LevelMinIndex))) {
            levelCntrl = LevelCntrlArrayGet(planeNum, col, LevelMinIndex);
        }
        LevelCntrlArrayPut(planeNum, col, LastIssuedLevel, levelCntrl + 1);

    }

    public static long ComplexCommonRCMain(int planeNum, String AlgoName,
            long row,
            long col,
            long Levels,
            long Value,
            long RowSub,
            long ColSub,
            long AlgoRowFactIndex,
            long AlgoRowSpreadIndex,
            long AlgoFactRowFactIndex) {
        long Temp = 1;
        int levelCntrl = LCG_levelCntrlInit;
        long FactValue = LCG_FactValueInit;
        long NPRFactValue = LCG_NPRFactValueInit;
        long SpreadValue = 1;

        long NPRSpreadValue = 1;
        if (AlgoName.equalsIgnoreCase("RowLevel1Levels")) //            log.info("ComplexCommonRCMain " 
        //                + " AlgoRowFactIndex=" + AlgoRowFactIndex 
        //                + " AlgoRowSpreadIndex="+ AlgoRowSpreadIndex
        //                + " AlgoFactRowFactIndex="+ AlgoFactRowFactIndex);
        {
            FactValue = LevelCntrlArrayGet(planeNum, col, AlgoRowFactIndex);
        }
        SpreadValue = LevelCntrlArrayGet(planeNum, col, AlgoRowSpreadIndex);
        NPRFactValue = LevelCntrlArrayGet(planeNum, col, AlgoFactRowFactIndex);
        if (NPRFactValue > 0) {
            NPRSpreadValue = ROWSDOE / NPRFactValue;
        } else {
            NPRSpreadValue = 1;
        }
        LCG_Value = LCG_Value_Random(planeNum, AlgoName, EqnLCG_Value, 1, SpreadValue, 1, 1, Levels, LCG_ValueOrder);
        LCG_levelCntrl = (int) LCG_Value_Random(planeNum, AlgoName, EqnLCG_levelCntrl, 1, SpreadValue, 1, LCG_levelCntrlInit, Levels, LCG_levelCntrlOrder);
        LCG_TempValue = LCG_TempValue(planeNum, AlgoName, row, col, RowSub, ColSub, Value, Levels);
        if (LCG_Value == 1) {
            LCG_Value = LCG_Value_Random(planeNum, AlgoName, EqnLCG_Value, 1, SpreadValue, 1, LCG_Value, Levels, LCG_ValueOrder);
        } else {
            LCG_Value = LCG_Value_Random(planeNum, AlgoName, EqnLCG_Value, 1, SpreadValue, 1, LCG_Value, Levels, LCG_ValueOrder);
        }
        if (LCG_FactValue == 1) {
            LCG_FactValue = LCG_Factorial_Random(planeNum, AlgoName, EqnLCG_FactValue, FactValue, SpreadValue, 1, 1, Levels, LCG_FactValueOrder);
        } else {
            LCG_FactValue = LCG_Factorial_Random(planeNum, AlgoName, EqnLCG_FactValue, LCG_FactValue, SpreadValue, 1, 1, Levels, LCG_FactValueOrder);
        }
        if ((LCG_NPRFactValue == 1) && (NPRFactValue > 1) && (NPRSpreadValue > 1)) {
            LCG_NPRFactValue = LCG_Factorial_Random(planeNum, AlgoName, EqnLCG_NPRFactValue, NPRFactValue, NPRSpreadValue, 1, 1, Levels, LCG_NPRFactValueOrder);
        } else {
            LCG_NPRFactValue = LCG_Factorial_Random(planeNum, AlgoName, EqnLCG_NPRFactValue, LCG_NPRFactValue, NPRSpreadValue, 1, 1, Levels, LCG_NPRFactValueOrder);
        }

        if ((SpreadValue > 0)) {
            Temp = (LCG_Value + LCG_levelCntrl + LCG_TempValue + LCG_FactValue + LCG_NPRFactValue) % (Levels + 1);
        } else if ((Levels + 1) > 0) {
            Temp = (Value + levelCntrl + LCG_TempValue) % (Levels + 1);
        }
        //log.info("Func:" + AlgoName + ": row=" + row + " col=" + col + " levelCntrl=" + levelCntrl + " Value=" + Value + " LCG_TempValue=" + LCG_TempValue + " LCG_NPRFactValue=" + LCG_NPRFactValue + " Temp=" + Temp);
        LevelCntrlArrayPut(planeNum, col, LevelCtrlIndex, levelCntrl);

        if ((Temp >= LevelCntrlArrayGet(planeNum, col, LevelMinIndex)) && (Temp <= LevelCntrlArrayGet(planeNum, col, LevelMaxIndex))) {
            DataArrayPut(planeNum, row, col, (byte) Temp);
        } else {
            Temp = LevelCntrlArrayGet(planeNum, col, LevelMinIndex);
            DataArrayPut(planeNum, row, col, (byte) Temp);
        }
        LevelCntrlArrayPut(planeNum, col, LastIssuedLevel, (int) Temp);
        if ((Value >= LevelCntrlArrayGet(planeNum, col, LevelMaxIndex))
                || (Value < LevelCntrlArrayGet(planeNum, col, LevelMinIndex))) {
            Value = LevelCntrlArrayGet(planeNum, col, LastIssuedValue);
        }
        if ((levelCntrl >= LevelCntrlArrayGet(planeNum, col, LevelMaxIndex))
                || (levelCntrl < LevelCntrlArrayGet(planeNum, col, LevelMinIndex))) {
            levelCntrl = LevelCntrlArrayGet(planeNum, col, LastIssuedLevel);
        }
        LevelCntrlArrayPut(planeNum, col, LevelCtrlIndex, levelCntrl);
        LevelCntrlArrayPut(planeNum, col, LastIssuedLevel, (int) Temp);
        LevelCntrlArrayPut(planeNum, col, AlgoRowFactIndex, (FactValue - 1));
        return Temp;
    }

    public static void ColumnFullFactorialRecursiveMain(int planeNum, long row, long col, long Levels, long NumCOLMDOE, long LevelSpread) {
        long Temp = 0;
        long FullFactorialROWS = (long) Math.pow((1.0 * Levels), (1.0 * col));
        if ((FullFactorialROWS <= LevelSpread)) {
            //Generate Random Level Value within LevelMinIndex-LevelMaxIndex(Modulus Parameter)
            Temp = ComplexCommonRCMain(planeNum, "ColumnFullFactorialRecursiveMain", row, col,
                    LevelCntrlArrayGet(planeNum, col, LevelMaxIndex),
                    LevelCntrlArrayGet(planeNum, col, LastIssuedValue),
                    4, LevelCntrlArrayGet(planeNum, col, LevelMaxIndex),
                    FactorialRow3Lev, FactSpreadRow3Lev, FactRowRow3Lev);//Need to test last parameter
            if ((DataArrayGet(planeNum, row, (col)) == Temp)) {
                Temp = (1 * (Temp) + 1) % (Levels + 1);
            }
            if (Temp <= LevelCntrlArrayGet(planeNum, col, LevelMinIndex)) {
                Temp = LevelCntrlArrayGet(planeNum, col, LevelMinIndex);
            }
            log.info("ColumnFullFactorialRecursiveMain:FullFactorialROWS=" + FullFactorialROWS + " LevelSpread=" + LevelSpread + " row=" + row + " col=" + col + " Temp=" + Temp + " Proceeding with Random Value");

        } else {
            Temp = SimpleModuloLevel(planeNum, "ColumnFullFactorialRecursiveMain", row, col,
                    SimpleModuloLevelXn,
                    Levels, 1);
            SimpleModuloLevelXn = Temp;
            log.info("ColumnFullFactorialRecursiveMain:FullFactorialROWS=" + FullFactorialROWS + " LevelSpread=" + LevelSpread + " row=" + row + " col=" + col + " Temp=" + Temp + " Proceeding with SimpleModuloLevel");

        }
        LevelCntrlArrayPut(planeNum, col, LastIssuedValue, Temp);
        DataArrayPut(planeNum, row, col, (byte) Temp);
        log.info("ColumnFullFactorialRecursiveMain col=" + col + " row=" + row + " Value=" + DataArrayGet(planeNum, row, col));
        LevelCntrlArrayPut(planeNum, col, LevelCtrlIndex, Temp);
    }

    // LCG Functions    
    public static long LCG_TempValue(int planeNum, String AlgoName, long row, long col, long RowSub, long ColSub, long Xn, long Modulus) {
        long TempValue = 1;
        // Caution:Positional "If then Else" Blocks
        // Results will be different if position is interchanged
        if (((Modulus + 1) > 0) && ((Math.abs(row - RowSub + 1) % (Modulus + 1)) > 0) && ((Math.abs(col - ColSub + 1) % (Modulus + 1)) > 0) && (((Xn + 1) % (Modulus + 1)) > 0)) {
            TempValue = ((row - RowSub + 1) % (Modulus + 1)) + ((col - ColSub + 1) % (Modulus + 1));
        } else if (((Modulus + 1) > 0) && (Math.abs(col - ColSub + 1) % (Modulus + 1)) > 0) {
            TempValue = (Math.abs(row - RowSub) % (Modulus + 1));
        } else if (((Modulus + 1) > 0) && (Math.abs(row - RowSub + 1) % (Modulus + 1)) > 0) {
            TempValue = (Math.abs(col - ColSub + 1) % (Modulus + 1));
        } else if (((Modulus + 1) > 0) && ((Xn + 1) % (Modulus + 1)) > 0) {
            TempValue = (Math.abs(row - RowSub) % (Modulus + 1));
        }
        return TempValue;
    }

    public long LCG_Value(int planeNum, String AlgoName,
            long a,
            long c,
            long Xn,
            long Modulus) {
        long Value = 1;
        Value = (a * Xn + c) % (Modulus + 1);
        return Value;
    }

    public long LCG_Factorial(int planeNum, String AlgoName, long Factorial, long FactorialSpread, long a, long c, long Modulus) {
        long Value = 1;
        //log.info("LCG_Factorial:" + AlgoName + ": Factorial=" + Factorial + " FactorialSpread=" + FactorialSpread);
        Value = (a * Factorial + c) % (FactorialSpread + 1);
        //Value = (a * Value + c) % (Modulus + 1);
        //log.info("LCG_Factorial:" + AlgoName + ": Value=" + Value);
        return Value;
    }

    public static long SimpleModuloLevel(int planeNum, String AlgoName, long row, long col, long Xn, long Modulus, long ModulusLow) {
        long levelCntrl = 1;
        levelCntrl = (1 * Xn + 1) % (Modulus + 1);
        if ((DataArrayGet(planeNum, row, (col - 1)) == levelCntrl) && (DataArrayGet(planeNum, row, (col - 1)) != Xn)) {
            levelCntrl = (1 * DataArrayGet(planeNum, row, (col - 1)) + 1) % (Modulus + 1);
        }
        if (levelCntrl < ModulusLow) {
            levelCntrl = ModulusLow;
        }
        //if (((levelCntrl >= HyperCubeArray[planeNum].MatrixArray.LevelCntrlArrayGet(planeNum, col, LevelMaxIndex))||(levelCntrl <= HyperCubeArray[planeNum].MatrixArray.LevelCntrlArrayGet(planeNum, col, LevelMinIndex))))
        //    levelCntrl=HyperCubeArray[planeNum].MatrixArray.LevelCntrlArrayGet(planeNum, col, LevelMinIndex);
        return levelCntrl;
    }

    public static long LCG_Value_Random(int planeNum, String AlgoName, String Exprn, long Xn, long FactorialSpread, long a, long c, long Modulus, int Order) {
        //Value = (a * Xn + c) % (Modulus + 1);

        Map<String, Double> variables = new HashMap<>();
        Map<String, String> variablesDiff = new HashMap<>();
        MyFuncExpress();
        MyFuncDiff();
        MyFuncSimple();

        String DiffWithRespTo = "Xn";
        variablesDiff.put(DiffWithRespTo, DiffWithRespTo);
        //String Exprn = FormEquation(Xn, a, c, (long) 1.0, DiffWithRespTo, (int) Modulus);
        MathsContxtLAv0_1_Prod.SimpleExpression expSimple;
        MathsContxtLAv0_1_Prod.Expression expValue;
        String StrexpSimpleExpress = "";
        String StrexpDiffExpress = "";
        int i = 1;
        long Xnplus1 = Xn;
        long Temp = 0;

        for (; (i < Order) && (Xn == Xnplus1); Xn++, i++) {
            variables.put(DiffWithRespTo, ((double) Xn));
            expValue = parse(Exprn, variables, DiffWithRespTo);
            Temp = (long) expValue.eval();
            log.info("Expr_LCG_Value:Temp(Xn)=" + Temp + " Xn=" + Xn + " Xnplus1=" + Xnplus1);
            if ((Temp == Xn) || (i > 1)) {
                expSimple = parseSimple(Exprn, variables, variablesDiff, "Xn");
                StrexpSimpleExpress = "";
                StrexpSimpleExpress = expSimple.SimpleExpr();
                log.info("Expr_LCG_Value: Xn=" + Xn + " Basic Simple Exprn=" + StrexpSimpleExpress);

                MathsContxtLAv0_1_Prod.DiffExpr exp = parseDiff(StrexpSimpleExpress, variables, variablesDiff, DiffWithRespTo);
                StrexpDiffExpress = "";
                StrexpDiffExpress = exp.DiffExpr();
                log.info("Expr_LCG_Value:Result(Diff Equation)(" + i + " th order)=> " + StrexpDiffExpress);

                expSimple = parseSimple(StrexpDiffExpress, variables, variablesDiff, "Xn");
                StrexpSimpleExpress = expSimple.SimpleExpr();
                log.info("Expr_LCG_Value: Xn=" + Xn + " (Simplified Diff Equation) Exprn=" + StrexpSimpleExpress);

                expValue = parse(StrexpSimpleExpress, variables, DiffWithRespTo);
                log.info("Expr_LCG_Value:Result(Value)(Xn)=" + expValue.eval());
                Exprn = StrexpSimpleExpress;
                StrexpSimpleExpress = "";
                StrexpDiffExpress = "";
                Xnplus1 = (long) ((expValue.eval()) % (Modulus + 1));
            } else {
                Xnplus1 = Temp;
            }
        }
        return ((long) Xnplus1);
    }

    public static long LCG_Factorial_Random(int planeNum, String AlgoName, String Exprn, long Xn, long FactorialSpread, long a, long c, long Modulus, int Order) {
        //Value = (a * Factorial + c) % (FactorialSpread + 1);
        Map<String, Double> variables = new HashMap<>();
        Map<String, String> variablesDiff = new HashMap<>();
        MyFuncExpress();
        MyFuncDiff();
        MyFuncSimple();
        String DiffWithRespTo = "Xn";
        variablesDiff.put(DiffWithRespTo, DiffWithRespTo);
        //String Exprn = FormEquation(Xn, a, c, (long) 1.0, DiffWithRespTo, (int) Modulus);

        MathsContxtLAv0_1_Prod.SimpleExpression expSimple;
        MathsContxtLAv0_1_Prod.Expression expValue;
        MathsContxtLAv0_1_Prod.Expression expValueInte;
        MathsContxtLAv0_1_Prod.Expression expValueInteTemp;
        String StrexpSimpleExpress = "";
        String StrexpDiffExpress = "";
        int i = 1;
        long Xnplus1 = Xn;
        long Temp = 0;
        String ExprnInte = Exprn;
        for (; (i < Order) && (Xn == Xnplus1); Xn++, i++) {
            variables.put("Xn", ((double) Xn));
            if ((UseIntegration == true) && (i > 1)) {
                expValue = parse(ExprnInte, variables, DiffWithRespTo);
                Temp = (long) expValue.eval();
            } else {
                expValue = parse(Exprn, variables, DiffWithRespTo);
                Temp = (long) expValue.eval();
            }

            log.info("Expr_LCG_Value:Temp(Xn)=" + Temp + " Xn=" + Xn + " Xnplus1=" + Xnplus1);
            if ((Temp == Xn) || (i > 1)) {
                expSimple = parseSimple(Exprn, variables, variablesDiff, DiffWithRespTo);
                StrexpSimpleExpress = expSimple.SimpleExpr();
                log.info("Expr_LCG_Factorial: Xn=" + Xn + " Basic Simple Exprn=" + StrexpSimpleExpress);

                MathsContxtLAv0_1_Prod.DiffExpr exp = parseDiff(StrexpSimpleExpress, variables, variablesDiff, DiffWithRespTo);
                StrexpDiffExpress = exp.DiffExpr();
                log.info("Expr_LCG_Factorial:Result(Diff Equation)(" + i + " th order)=> " + StrexpDiffExpress);

                expSimple = parseSimple(StrexpDiffExpress, variables, variablesDiff, "Xn");
                StrexpSimpleExpress = expSimple.SimpleExpr();
                log.info("Expr_LCG_Value: Xn=" + Xn + " (Simplified Diff Equation) Exprn=" + StrexpSimpleExpress);

                expValue = parse(StrexpSimpleExpress, variables, DiffWithRespTo);
                log.info("Expr_LCG_Factorial:Result(Value)(Xn)=" + expValue.eval());
                Exprn = StrexpSimpleExpress;
                StrexpSimpleExpress = "";
                StrexpDiffExpress = "";
                if (UseIntegration == true) {
                    expSimple = parseSimple(ExprnInte, variables, variablesDiff, DiffWithRespTo);
                    StrexpSimpleExpress = expSimple.SimpleExpr();
                    log.info("Expr_LCG_Factorial: Xn=" + Xn + " Basic Simple Exprn=" + StrexpSimpleExpress);

                    MathsContxtLAv0_1_Prod.IntegrExpr expInte = parseIntegr(StrexpSimpleExpress, variables, variablesDiff, DiffWithRespTo);
                    StrexpDiffExpress = expInte.IntegrExpr();
                    log.info("Expr_LCG_Factorial:Result(Diff Equation)(" + i + " th order)=> " + StrexpDiffExpress);

                    expSimple = parseSimple(StrexpDiffExpress, variables, variablesDiff, "Xn");
                    StrexpSimpleExpress = expSimple.SimpleExpr();
                    log.info("Expr_LCG_Value: Xn=" + Xn + " (Simplified Diff Equation) Exprn=" + StrexpSimpleExpress);

                    expValueInte = parse(StrexpSimpleExpress, variables, DiffWithRespTo);
                    log.info("Expr_LCG_Factorial:Result(Value)(Xn)=" + expValue.eval());
                    ExprnInte = StrexpSimpleExpress;
                    StrexpSimpleExpress = "";
                    StrexpDiffExpress = "";
                    if (((UseSoftwareNoise == true) && (expValueInte.eval() < 0) && (Temp > 0))
                            && (AvoidStackOverflow == false)) {
                        Xnplus1 = (long) ((-1.0 * expValueInte.eval()) % (Modulus + 1));
                    } else if ((UseSoftwareNoise == true) && (AvoidStackOverflow == true) && ((expValueInte.eval() < 0) && (Temp > 0))) {
                        expValueInteTemp = parse(StrexpSimpleExpress, variables, DiffWithRespTo);
                        while (expValueInteTemp.eval() < 0.0) {
                            expValueInteTemp = parse(StrexpSimpleExpress, variables, DiffWithRespTo);
                            if (expValueInteTemp.eval() < 0.0) {
                                //Design Issue:Xn is long, variables takes Double 
                                // but StrexpSimpleExpress End Result may vary the rounding off drastically
                                //:Coarse Control Fine Control Problem
                                Xn = Xn / 2;
                            }
                        }
                    } else {
                        Xnplus1 = (long) ((expValueInte.eval()) % (Modulus + 1));
                    }
                } else {
                    if (((UseSoftwareNoise == true) && (expValue.eval() < 0) && (Temp > 0))
                            && (AvoidStackOverflow == false)) {
                        Xnplus1 = (long) ((-1.0 * expValue.eval()) % (Modulus + 1));
                    } else if ((UseSoftwareNoise == true) && (AvoidStackOverflow == true)
                            && ((expValue.eval() < 0) && (Temp > 0))) {
                        expValue = parse(StrexpSimpleExpress, variables, DiffWithRespTo);
                        while (expValue.eval() < 0.0) {
                            expValue = parse(StrexpSimpleExpress, variables, DiffWithRespTo);
                            if (expValue.eval() < 0.0) {
                                Xn = Xn / 2;
                            }
                        }
                    } else {
                        Xnplus1 = (long) ((expValue.eval()) % (Modulus + 1));
                    }
                }
            } else {
                Xnplus1 = Temp;
            }
        }
        if (UseHardwareNoise == true) {
            Xnplus1 = (long) ((Xnplus1 + Seed) % (Modulus + 1));
        }
        return ((long) Xnplus1);
    }

    public static boolean DupCheckMatrixMode(int planeNum) {
        int i = 0;
        int k = 0;
        int m = 0;
        boolean flag = false;
        long a = 0;
        int j = 0;
        for (i = 0; i < ROWSDOE; i++) {
            a = getRowAsInteger(planeNum, i, Length);
            for (m = 0; m <= j; m++) {
                if ((a == ModeValue[m])) {
                    Mode[m] = Mode[m] + 1;
                    log.fatal("DupCheckMatrixMode:Duplicate row=(i)=" + i + " m=" + m + " j=" + j + " Integer(a)=" + a + " Freq=" + Mode[m]);
                    OverallDupCount++;
                    DupsList.add(i + "@" + m);
                    break;
                }
            }
            if (m >= j) {
                ModeValue[j] = a;
                Mode[j]++;
                ModeIndex[j] = i;
                //log.fatal("DupCheckMatrixMode:Inserting row=(i)=" + i + " ModeValue["+ j +"]=" + ModeValue[j] + " Integer(a)=" + a + " Freq="+ Mode[j]);
                j++;
            }
        }
        log.fatal("DupCheckMatrixMode:ROWSDOE=" + ROWSDOE + " Mode.length(j)=" + j);
        for (k = 0; k < j; k++) {
            if ((Mode[k] > 1)) {
                log.fatal("DupCheckMatrix:Array Incomplete and with Duplicates duplicate Row k=" + k + " MeanOutputModeTracker=" + MeanInputModeTracker[k] + "ModeValue[k]=" + ModeValue[k] + " Mode[k]=" + Mode[k] + " i=" + i);
                flag = true;
            }
        }
        OverallUniqueCount = j;
        if (flag == false) {
            log.fatal("DupCheckMatrix:No duplicates for Whole Array ");
            System.out.println("DupCheckMatrix:No duplicates for Whole Array ");
        }
        return flag;
    }

    public static long getRowAsInteger(int planeNum, long row, long Col) {
        long Temp = 0;
        long col = Col;
        //long col = 6;
        for (long i = 0; i < col; i++) {
            Temp = 10 * Temp + DataArrayGet(planeNum, row, i);
        }
        return Temp;
    }

    public static long getColAsInteger(int planeNum, long col /*, long colStart, long colEnd*/) {
        long Temp = 0;
        // Below Length is ROWS of DataArray and NOT String Length
        //long row = LocalLength;
        long row = 6;
        for (long i = 0; i < row; i++) {
            Temp = 10 * Temp + DataArrayGet(planeNum, col, i);
        }
        return Temp;
    }

    public static String getRowAsStringDataArray(int planeNum, long row, long colDupCheckColStart, long colDupCheckColEnd, boolean DisplayAsString) {
        String Temp = "";
        long col = Length;
        for (long i = colDupCheckColStart; i < colDupCheckColEnd; i++) {
            if (DisplayAsString == false) {
                Temp = Temp + DataArrayGet(planeNum, row, i);
            } else {
                Temp = Temp + " " + DataArrayGet(planeNum, row, i);
            }
        }
        return Temp;
    }

    public static double getLevelValuePair(int col, double Level) {
        return LevelValueStore[col][(int) Level];
    }

    public static void PutLevelValuePair(int col, double Level, Double Value) {
        LevelValueStore[col][(int) Level] = Value;
    }

    public static void pmain(String... args) {
        log.info("HyperDOE: main Entered");
        String Path = "data\\";
        String msg = "";
        try {
            FileInputStream fpinput = null;
            if ((fpinput = new FileInputStream(Path + ConfigFileName)) == null) {
                msg = "Input: can't open " + Path + ConfigFileName;
                System.out.println(msg);
                log.error(msg);
            }
            log.error("Opened File Name: Infile=" + Path + ConfigFileName);
            System.out.println("Opened File Name: Infile=" + Path + ConfigFileName);
            NoiseDOEDefine.Myfscanf(fpinput);
            NoiseDOEDefine.Myprintf();

            fpinput = null;
            msg = "";
            if ((fpinput = new FileInputStream(Path + GPConfigFileName)) == null) {
                msg = "Input: can't open " + Path + GPConfigFileName;
                System.out.println(msg);
                log.error(msg);
            }
            NoiseDOEDefineDP.Myfscanf(fpinput);
            System.out.println("Base Values ON WHICH DOE PROGRAMMING IS DONE Infile=" + Path + ConfigFileName);
            NoiseDOEDefineDP.Myprintf();

            MeanColModeTracker = new String[(int) Length];
            ColMode = new int[(int) Length];// Needs to be improved aqs this is a wastage of space
            ColModeValue = new int[(int) Length];// Needs to be improved aqs this is a wastage of space
            //Intimate Compiler of Equation Memory
            EqnDetails = new ColConfigration[(int) Length];
            EqnParsed = new int[(int) Length];
            for (int i = 0; i < Length; i++) {
                MeanColModeTracker[i] = new String("");
                ColMode[i] = 0;
                ColModeValue[i] = 0;
                //Set Equation Memory
                EqnDetails[i] = new ColConfigration(i);
                EqnParsed[i] = 0;
            }
            MeanInputModeTracker = new String[(int) ROWSDOE];
            Mode = new long[(int) ROWSDOE];// Needs to be improved as this is a wastage of space. Can be reduced to ROWSDOE/LEVELS
            ModeValue = new long[(int) ROWSDOE];// Needs to be improved as this is a wastage of space. Can be reduced to ROWSDOE/LEVELS
            ModeIndex = new long[(int) ROWSDOE];// Needs to be improved as this is a wastage of space. Can be reduced to ROWSDOE/LEVELS
            for (int i = 0; i < ROWSDOE; i++) {
                MeanInputModeTracker[i] = new String("");
                Mode[i] = 0;
                ModeValue[i] = 0;
                ModeIndex[i] = 0;
            }
            DupsList = new LinkedList<String>();
            DataCountSum = new int[(int)Length];
                    
            GenerateDOEHammard(0, ROWSDOE, Length);
            System.out.print(System.lineSeparator());
            System.out.print(System.lineSeparator());
            System.out.println("Final Completed Hadamard Noise Matrix:");
            System.out.print(System.lineSeparator());
            printDataOutput(0);
            System.out.print(System.lineSeparator());
            StrengthLevel(0,LEVELS);
            DataCountAnalysis( 0);
            System.out.print(System.lineSeparator());
            ComputeROWSDOETaguchi();
            System.out.println("main:ROWSDOEComputed=" + ROWSDOEComputed + " ROWSDOE=" + ROWSDOE + " ROWSDOERecommended=" + ROWSDOERecommended);
            ComputeLengthHadamard();
            System.out.print(System.lineSeparator());
            MultiplicationMatrixX2((int) ROWSDOE, (int) Length);
            //double[][] Matrix = null;
            //System.out.println("main:Determinant Computed=" + Determinant(Matrix, (int) 3, (int) 3, (int) 3));

        } catch (Exception HyperDOEse) {
            log.error("Exception: main");
            System.out.println(HyperDOEse.getStackTrace());
            System.out.println(HyperDOEse.getMessage());
            HyperDOEse.printStackTrace();
        }
    }

    public static void printDataOutput(int planeNum) {
        try {
            int NumROWSDOE = (int) ROWSDOE;//matrix.limit();
            int NumCOLMDOE = (int) Length;
            int c = 0;
            int i = -1, j = 0, k = 0;
            String DupString = "";
            String[] DataTemp;
            int[] BlockDups=new int[(int)LEVELS+1];
            System.out.print("DataArray:Header:");
            for (int r = 0; r < NumCOLMDOE; r++) {
                System.out.print(r + " ");
            }
            System.out.print(System.lineSeparator());
            if (DupsList.size() > 0) {
                DupString = DupsList.getFirst();
                DataTemp = DupString.split("@");
                if (DataTemp.length == 2) {
                    i = Integer.parseInt(DataTemp[0]);
                    k = Integer.parseInt(DataTemp[1]);
                }
            }
            for (int r = 0; r < NumROWSDOE; r++) {
                System.out.print("Row=" + r + " ");
                // Rajesh Pai:Not sure why c should start from 1;
                for (c = 0; c < NumCOLMDOE; c++) {
                    System.out.print(DataArrayGet(planeNum, r, c) + " ");
                }
                if (r == i) {
                    System.out.print(ConsoleColors.RED + " duplicate with Row=" + ModeIndex[k] + ConsoleColors.RESET);
                    for(int p=1; p <= LEVELS; p++){
                        if (DataArrayGet(planeNum, r, (Length-1))==p) { //Belongs to pth Block
                            BlockDups[p]++;
                            break;
                        }
                    }
                    j++;
                    if (j < DupsList.size()) {
                        DupString = DupsList.get(j);
                        DataTemp = DupString.split("@");
                        if (DataTemp.length == 2) {
                            i = Integer.parseInt(DataTemp[0]);
                            k = Integer.parseInt(DataTemp[1]);
                        }
                    }
                } else {
                    System.out.print(ConsoleColors.GREEN + " Unique Row " + ConsoleColors.RESET);
                }
                System.out.print(System.lineSeparator());
            }
            System.out.print(System.lineSeparator());
            System.out.println("Overall DupsList.size()=" + DupsList.size() + " OverallDupCount=" + OverallDupCount + " OverallUniqueCount=" + OverallUniqueCount);
            System.out.print(System.lineSeparator());
            System.out.println("Duplicate Summary(based on Last Column="+ (Length-1) + "):");
            for(int p=0; p <= LEVELS; p++){
                    System.out.println(" Block Level=" + p + " Dups=" + BlockDups[p] + " ");
            }
            System.out.print(System.lineSeparator());
            System.out.println("Full Factorial Sum Of Levels Summary:");
            System.out.print(System.lineSeparator());
            
            
            
        } catch (Exception HyperE) {
            log.info("Exception: printDataOutput");
            System.out.println("Exception: printDataOutput");
            System.out.println(HyperE.getStackTrace());
            System.out.println(HyperE.getMessage());
            HyperE.printStackTrace();
        }
    }

    //RowsTaguchi=(1 + Sigma((i=1 to NV) (Li-1)) ) 
    public static void ComputeROWSDOETaguchi() {
        int i = 0;
        int Li = 0;
        ROWSDOEComputed = 1;
        for (i = 0; i < Length; i++) {
            Li = LevelCntrlArrayGet(0, i, LevelMaxIndex);
            ROWSDOEComputed = ROWSDOEComputed + (Li - 1);
        }
        ROWSDOERecommended = ROWSDOEComputed;
        for (i = 1; i < Length; i++) {
            Li = LevelCntrlArrayGet(0, i, LevelMaxIndex);

            if ((Li > 0) && (ROWSDOERecommended % Li == 0))
                ; else if (Li > 0) {
                ROWSDOERecommended++;
                i = 0;
            }
        }
        //System.out.println("ComputeROWSDOETaguchi:ROWSDOEComputed=" + ROWSDOEComputed + " ROWSDOE=" + ROWSDOE + " ROWSDOERecommended=" + ROWSDOERecommended);
    }
    public static void ComputeLengthHadamard() {
        
        LengthRecommended = (long)Math.pow(LEVELS,ROWSDOE);
        
        System.out.println("ComputeLengthHadamard:LengthRecommended=" + LengthRecommended + " Length=" + Length + " LEVELS=" + LEVELS  + " k=" + ROWSDOE);
    }
    public static void StrengthLevel(int planeNum,int Levels){
        int c = 0, DataCount = 0;
        int NumCOLMDOE=(int)Length;
        int NumROWSDOE=(int)ROWSDOE;
        System.out.println("Strength of Levels Summary:");
            System.out.print("Column=");
            for (int r = 0; r < NumCOLMDOE; r++) {
                System.out.print(r + " ");
            }
            System.out.print(System.lineSeparator());
            StrengthMode = new int[(int) Length][Levels + 10];
            for (c = 0, DataCount = 0; ((c < NumCOLMDOE)); c++, DataCount = 0) {
                int Li = LevelCntrlArrayGet(0, c, LevelMaxIndex);
                StrengthMode[c] = new int[Levels + 10];
                for (int r = 0; (r < NumROWSDOE); r++) {
                    int CellVal = DataArrayGet(planeNum, r, c);
                    if ((CellVal >= 0) && (CellVal < StrengthMode[c].length)) {
                        StrengthMode[c][CellVal]++;
                    }
                }
            }
            //Printing StrengthMode
            int r1 = 1;
            for (r1 = 1; r1 < (Levels + 1); r1++) {
                System.out.print("Strength of Level=" + r1);
                for (c = 0; c < NumCOLMDOE; c++) {
                    System.out.print(" " + StrengthMode[c][r1]);
                    DataCountSum[c] += StrengthMode[c][r1];
                }
                System.out.print(System.lineSeparator());
            }
    }
    
    public static void DataCountAnalysis(int planeNum){
        int c = 0, DataCount = 0;
        int NumCOLMDOE=(int)Length;
        int NumROWSDOE=(int)ROWSDOE;
        int FullFactorialCnt = 0;
        int FullFactUptoCol = 0;
        int FullFactIndivCol = 0;
         boolean FullFactFlag = false;
        System.out.print("DataCountSum=");
            for (c = 0; c < NumCOLMDOE; c++) {
                System.out.print(" " + DataCountSum[c]);
            }
            System.out.print(System.lineSeparator());
            System.out.print("DataCount(Sum of Cells of Noise Matrix)=");
            int r = 0;
            for (c = 0, DataCount = 0; c < NumCOLMDOE; c++, DataCount = 0) {
                for (r = 0; r < NumROWSDOE; r++) {
                    DataCount += DataArrayGet(planeNum, r, c);
                }
                System.out.print(DataCount + " ");
                if (c == 0) {
                    FullFactorialCnt = DataCount;
                    FullFactFlag = true;
                    FullFactUptoCol = c;
                } else if ((DataCount != FullFactorialCnt) && (FullFactFlag == true)) {
                    FullFactFlag = false;
                } else if (FullFactFlag == true) {
                    FullFactUptoCol = c;
                    FullFactFlag = true;
                }
            }
            System.out.print(System.lineSeparator());
            System.out.println("Matrix is Full Factorial upto FullFactUptoCol=" + FullFactUptoCol + ".");
            System.out.println(ConsoleColors.RED + "Columns " + (FullFactUptoCol + 1) + " upto " + (c - 1)
                    + " Should be Full Factored Manually." + ConsoleColors.RESET);
            System.out.print("Full Factorial % =");
            for (c = 0, DataCount = 0, FullFactIndivCol = 0; c < NumCOLMDOE; c++, DataCount = 0) {
                for (r = 0; r < NumROWSDOE; r++) {
                    DataCount += DataArrayGet(planeNum, r, c);
                }
                if (FullFactorialCnt == DataCount) {
                    FullFactIndivCol++;
                }
                if (FullFactorialCnt > 0) {
                    System.out.printf("%3.2f ", ((double) DataCount / (double) FullFactorialCnt * 100.00));
                }
            }
            System.out.print(System.lineSeparator());
            if (c > 0) {
                System.out.printf("Matrix High Level Full Factorial=%3.2f%%. ( %d Columns out of %d Columns)", (double) (FullFactUptoCol + 1) / (double) Length * 100.00, (FullFactUptoCol + 1), Length);
            } else {
                System.out.printf("Matrix High Level Full Factorial=%3.2f%%.", 0.00);
            }
            System.out.print(System.lineSeparator());
            System.out.print("Matrix High Level Full Factorial :Individual Column Count=" + FullFactIndivCol + " out of " + Length);
            if (Length > 0) {
                System.out.printf("(%3.2f%%) ", ((double) FullFactIndivCol / (double) Length * 100.00));
            } else {
                System.out.printf("(%3.2f%%) ", 0.00);
            }
    }
    
    public static double Determinant(double[][] Matrix, int row, int column, int order) {
        double Value = 0.0;
        double Scalar = 0.0;
        double[][] Smaller = null;
        Smaller = new double[order - 1][order - 1];
        boolean EndFlag = false;
        boolean MiddleFlag = false;
        if (order > 3) {

        } else if (order == 3) {
            System.out.println("Order 3: row=" + row);
            System.out.println("Order 3: column=" + column);
            Smaller = CoFactor(row, column, order - 1, EndFlag, MiddleFlag);
            Scalar = Determinant(Smaller, row, column, order - 1);
            System.out.println("Scalar=" + Scalar);
            //(Sgn*a*CoFactor of 2X2
            Value += Math.pow(-1.0, (row + column)) * (DataArrayGet(0, row - row, column - column)) * Scalar;
            System.out.println("Order 3:Value=" + Value);
            MiddleFlag = true;
            Smaller = CoFactor(row, column - column + 1, order - 1, EndFlag, MiddleFlag);
            Scalar = Determinant(Smaller, row - 1, column + 1, order - 1);
            System.out.println("Scalar=" + Scalar);
            //(Sgn*b*CoFactor of 2X2
            Value += Math.pow(-1.0, (row + column)) * (DataArrayGet(0, row - row, column - column + 1)) * Scalar;
            System.out.println("Order 3:Value=" + Value);
            MiddleFlag = false;
            EndFlag = true;
            Smaller = CoFactor(row - row, column - column + 2, order - 1, EndFlag, MiddleFlag);
            Scalar = Determinant(Smaller, row - 1, column, order - 1);
            System.out.println("Scalar=" + Scalar);
            //(Sgn*c*CoFactor of 2X2
            Value += Math.pow(-1.0, (row + column)) * (DataArrayGet(0, row - row, column - column + 2)) * Scalar;
            System.out.println("Order 3:Value=" + Value);
        } else if (order == 2) {
            System.out.println("Order 2: order=" + order);
            System.out.println("Order 2: row=" + row);
            System.out.println("Order 2: column=" + column);
            if ((row == order) && (column < order)) {
                System.out.println("Order 2: a=" + DataArrayGet(0, row - row + 1, column - column));
                System.out.println("Order 2: d=" + DataArrayGet(0, row - row + 2, column - column + 1));
                System.out.println("Order 2: b=" + DataArrayGet(0, row - row + 1, column - column + 1));
                System.out.println("Order 2: c=" + DataArrayGet(0, row - row + 2, column - column));
                Value = (DataArrayGet(0, row - row + 1, column - column)) * (DataArrayGet(0, row - row + 2, column - column + 1)) //ad
                        - ((DataArrayGet(0, row - row + 1, column - column + 1)) * (DataArrayGet(0, row - row + 2, column - column))); //-bc
                System.out.println("Order 2:Value=" + Value);
            } else {
                if ((column == 4)) {
                    System.out.println("Order 2:Middle: a=" + DataArrayGet(0, row - row + 1, column - column + 1));
                    System.out.println("Order 2:Middle: d=" + DataArrayGet(0, row - row + 3, column - column + 2));
                    System.out.println("Order 2:Middle: b=" + DataArrayGet(0, row - row + 1, column - column + 2));
                    System.out.println("Order 2:Middle: c=" + DataArrayGet(0, row - row + 3, column - column + 1));
                    Value = (DataArrayGet(0, row - row, column - column + 1)) * (DataArrayGet(0, row - row + 2, column - column + 2)) //ad
                            - ((DataArrayGet(0, row - row, column - column + 2)) * (DataArrayGet(0, row - row + 2, column - column))); //-bc
                    System.out.println("Order 2:Middle:Value=" + Value);
                } else {
                    System.out.println("Order 2:End: a=" + DataArrayGet(0, row - row + 1, column - column));
                    System.out.println("Order 2:End: d=" + DataArrayGet(0, row - row + 2, column - column + 1));
                    System.out.println("Order 2:End: b=" + DataArrayGet(0, row - row + 1, column - column + 1));
                    System.out.println("Order 2:End: c=" + DataArrayGet(0, row - row + 2, column - column));
                    Value = (DataArrayGet(0, row - row + 1, column - column)) * (DataArrayGet(0, row - row + 2, column - column + 1)) //ad
                            - ((DataArrayGet(0, row - row + 1, column - column + 1)) * (DataArrayGet(0, row - row + 2, column - column))); //-bc
                    System.out.println("Order 2:End:Value=" + Value);
                }
            }
        } else {
            return 0.0;
        }
        return Value;
    }

    public static double[][] CoFactor(int row, int column, int order, boolean EndFlag, boolean MiddleFlag) {
        double[][] Smaller = null;
        boolean ColFlag = false;
        Smaller = new double[order][order];
        int k = 0;
        if ((EndFlag == false) && (MiddleFlag == false)) {
            System.out.println("CoFactor:Initial:row=" + row);
            System.out.println("CoFactor:Initial:column=" + column);
            for (int i = 0; ((i < row - 1) && (i < Length)); i++) {
                if (row == i) {
                    continue;
                }
                for (int j = 0; ((j < column - 1) && (j < Length)); j++) {
                    for (k = 0; ((k < column - 1) && (k < Length));) {
                        if (column == j) {
                            ColFlag = true;
                            break;//Do Nothing 
                        } else {
                            Smaller[i][k] = DataArrayGet(0, i + 1, k + 1);
                            k++;
                        }
                    }
                    if (ColFlag == true) {
                        continue;
                    }
                }
            }
        } else if (MiddleFlag == true) {
            System.out.println("CoFactor:Middle:order=" + order);
            System.out.println("CoFactor:Middle:row=" + row);
            System.out.println("CoFactor:Middle:column=" + column);
            for (int i = 0; ((i < row - 1) && (i < Length)); i++) {
                if (row == i) {
                    continue;
                }
                for (int j = 0; ((j < column) && (j < Length)); j++) {
                    for (k = 0; ((k < column) && (k < Length));) {
                        if (column == j) {
                            ColFlag = true;
                            break;//Do Nothing 
                        } else {
                            Smaller[i][k] = DataArrayGet(0, i + 1, k);
                            k++;
                        }
                    }
                    if (ColFlag == true) {
                        continue;
                    }
                }

            }
            if (order == 2) {
                Smaller[0][order - 1] = DataArrayGet(0, row - 2, column + 1);
                Smaller[1][order - 1] = DataArrayGet(0, row - 2 + 1, column + 1);
            }
        } else {
            System.out.println("CoFactor:End:row=" + row);
            System.out.println("CoFactor:End:column=" + column);
            for (int i = 0; (i < 2) && (i < Length); i++) {
                for (k = 0; ((k < 2) && (k < column) && (k < Length));) {
                    Smaller[i][k] = DataArrayGet(0, row + 1, k);
                    k++;
                }
                row++;
            }
        }
        System.out.print("CoFactor=");
        System.out.print(System.lineSeparator());
        for (int r = 0; r < Smaller.length; r++) {
            System.out.print("Row=" + r + " ");
            for (int c = 0; c < Smaller[0].length; c++) {
                System.out.print(Smaller[r][c] + " ");
            }
            System.out.print(System.lineSeparator());
        }
        return Smaller;
    }

    public static void MultiplicationMatrix(int row, int column) {
        int[][] Product = null;
        String[] Names = new String[column];
        Product = new int[row][column];
        int[] X = new int[row];
        int[] Y = new int[row];

        if ((row > 0) && (column > 0)) {
            Names = new String[column];
            Product = new int[row][column];
            X = new int[row];
            Y = new int[row];
            for (int i = 0; i < Length - 1; i++) {
                //Form Name of 2 way interaction
                Names[i] = " X" + i + ".Y" + (i + 1);
                //Form X of X.Y dot product
                for (int j = 0; j < ROWSDOE; j++) {
                    X[i] = DataArrayGet(0, j, i);
                }
                //Form Y of X.Y dot product
                for (int k = 0; k < ROWSDOE; k++) {
                    Y[k] = DataArrayGet(0, k, i + 1);
                }
                for (int p = 0; p < ROWSDOE; p++) {
                    for (int m = 0; m < ROWSDOE; m++) {
                        Product[m][p] = X[p] * Y[m];
                    }
                }
            }
            //Last Column Computation of 2 way interaction
            int n = (int) Length - 1;
            //Form Name of 2 way interaction
            Names[n] = " X" + n + ".Y" + (0);
            //Form X of X.Y dot product
            for (int j = 0; j < ROWSDOE; j++) {
                X[n] = DataArrayGet(0, j, n);
            }
            //Form Y of X.Y dot product
            for (int k = 0; k < ROWSDOE; k++) {
                Y[k] = DataArrayGet(0, k, 0);
            }
            for (int i = 0; i < ROWSDOE; i++) {
                for (int m = 0; m < ROWSDOE; m++) {
                    Product[m][i] = X[i] * Y[m];
                }
            }
            System.out.println("Hadamard Product(2-way Interaction)=");
            System.out.print("Column Interaction Names=");
            for (int i = 0; i < Length; i++) {
                System.out.print(Names[i]);
            }
            System.out.print(System.lineSeparator());
            for (int r = 0; r < Product.length; r++) {
                System.out.print("Row=" + r + " ");
                for (int c = 0; c < Product[0].length; c++) {
                    System.out.print(Product[r][c] + " ");
                }
                System.out.print(System.lineSeparator());
            }
        }
    }

    public static void MultiplicationMatrixX2(int row, int column) {
        int[][] Product = null;
        String[] Names = new String[column];
        Product = new int[row][column];
        int[] X = new int[row];
        int[] Y = new int[row];
        if ((row > 0) && (column > 0)) {
            Names = new String[column];
            Product = new int[row][column];
            X = new int[row];
            Y = new int[row];
        for (int i = 0; i < Length - 1; i++) {
            //Form Name of 2 way interaction
            Names[i] = " X" + i + ".Y" + (i + 1);
            for (int p = 0; p < ROWSDOE; p++) {
                Product[p][i] = DataArrayGet(0, p, i) * DataArrayGet(0, p, (i + 1));
            }
        }
        //Last Column Computation of 2 way interaction
        int n = (int) Length - 1;
        //Form Name of 2 way interaction
        Names[n] = " X" + n + ".Y" + (0);
        for (int p = 0; p < ROWSDOE; p++) {
            Product[p][n] = DataArrayGet(0, p, n) * DataArrayGet(0, p, 0);
        }
        System.out.println("Hadamard Product(2-way Interaction-X^2(Arithmetic XY i.e X[i].Y[i]))=");
        System.out.print("Column Interaction Names=");
        for (int i = 0; i < Length; i++) {
            System.out.print(Names[i]);
        }
        System.out.print(System.lineSeparator());
        for (int r = 0; r < Product.length; r++) {
            System.out.print("Row=" + r + " ");
            for (int c = 0; c < Product[0].length; c++) {
                System.out.print(Product[r][c] + " ");
            }
            System.out.print(System.lineSeparator());
        }
        }
    }

    public static void main(String... args) {
        //Uncomment below Line for Normal Usage of DOEMatrixGen
        System.out.println(ConsoleColors.BLUE + " Hammard Noise Matrix Generation" + ConsoleColors.RESET);
        //pmain(args);
        //System.out.println(ConsoleColors.BLUE + " Hammard Noise Matrix Generation Completed" + ConsoleColors.RESET);

        //Starting Test Case :21 to 34, 39,40 , 101 to 104, 201 to 209
        String StartMyTestCase = "-1";//Test Case Number : Optiona :All or a Single Number
        String EndMyTestCase = "29";//Test Case Number : Optiona :All or a Single Number
        String TestCaseType = "LimitedRegression";//Test Suite Type:Individual OR LimitedRegression OR Regression
        //TestHarnessDOEDPFine MyTestData = new TestHarnessDOEDPFine(StartMyTestCase, EndMyTestCase, TestCaseType);
        //Uncomment below Line for DOEMatrixGen Self Regression Test
        //mainRegresssionDOEDP( MyTestData);

        TestHarnessDOE MyTestData = new TestHarnessDOE(StartMyTestCase, EndMyTestCase, TestCaseType);
        mainRegresssionDOE(MyTestData);
    }
}
