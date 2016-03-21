
import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Demo {

    public Demo(){}

    public static void main(String args[]) {
        System.out.println("Escribe la solucion inicial que quieras usar(1, 2), la funcion heuristica (1, 2, 3) y "+
                "la funcion creadora de sucesores (1, 2, 3). Cualquier valor erroneo usara la primera funcion:");
        Scanner s = new Scanner(System.in);
        int iSol = s.nextInt();
        int heu = s.nextInt();
        int sGen = s.nextInt();
        Board board = new Board(50,5,50,5,1234,1);

        switch (iSol) {
            case 1 : board.solIni1();
                break;
            case 2 : board.solIni2();
                break;
            default: board.solIni1();
                break;
        }

        SuccessorFunction sucGen;
        switch (sGen) {
            case 1 : sucGen = new SuccessorFunctionHC1();
                break;
            case 2 : sucGen = new SuccessorFunctionHC2();
                break;
            case 3 : sucGen = new SuccessorFunctionHC3();
                break;
            default : sucGen = new SuccessorFunctionHC1();
                break;
        }

        HeuristicFunction hf;
        switch (heu){
            case 1 : hf = new HeuristicFunction1A();
                break;
            case 2 : hf = new HeuristicFunction2A();
                break;
            case 3 : hf = new HeuristicFunction3A();
                break;
            default : hf = new HeuristicFunction1A();
                break;
        }

        try {
            Problem problem = new Problem(board, sucGen, new GoalFalseTest(), hf);
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }

}