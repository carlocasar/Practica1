import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/*
 * Receives parameters of a LocalSearch, executes it, returns instrumentation.
 */

public class Controller {

    public static Results Hill_Climbing(int seed, int nUsers, int nRequests, int nServers, int minReplications,
                                     int solIni, String operatorS, char heuristic, int criterion)
    {
        Results results = new Results();
        Board initState = new Board(nUsers,nRequests,nServers,minReplications,seed,criterion);
        long iniTime = System.currentTimeMillis();
        initialSolution(initState,solIni);
        long finIniTime = System.currentTimeMillis();

        results.setSolIni(iniTime, finIniTime, initState.getTotalTransmissionTime());
        
        SuccessorFunction successorGen = operatorSet(operatorS,"Hill Climbing");
        HeuristicFunction heuristicF = heuristic(criterion,heuristic);
        try {
            long startTime = System.currentTimeMillis();
            Problem problem = new Problem(initState, successorGen, new GoalFalseTest(), heuristicF);
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);       // La excepcion solo la tira esto. weird...
            long endTime = System.currentTimeMillis();

            results.setSearchTime(startTime, endTime);
            results.setFinalTransmission(((Board) search.getGoalState()).getTotalTransmissionTime());
            results.setTotalTime(iniTime,endTime);

            //System.out.println();                                   // lo guay seria pasarle el search agent
            //printActions(agent.getActions());                       // al main y que soltar la salida desde ahi.
            //printInstrumentation(agent.getInstrumentation());
            /*System.out.println("Final state: ");
            System.out.println(search.getGoalState());
            System.out.println("Execution time: " + (endTime - startTime) + " ms");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static Results Simmulated_Annealing(int seed, int nUsers, int nRequests, int nServers, int minReplications,
                                            int solIni, String operatorS, char heuristic, int criterion,
                                            int steps, int stiter, int k, double lamb)
    {
        Results results = new Results();
        Board initState = new Board(nUsers,nRequests,nServers,minReplications,seed,criterion);
        long iniTime = System.currentTimeMillis();
        initialSolution(initState,solIni);
        long finIniTime = System.currentTimeMillis();

        results.setSolIni(iniTime, finIniTime, initState.getTotalTransmissionTime());

        SuccessorFunction successorGen = operatorSet(operatorS,"Simulated Annealing");
        HeuristicFunction heuristicF = heuristic(criterion,heuristic);

        try {
            long startTime = System.currentTimeMillis();
            Problem problem = new Problem(initState, successorGen, new GoalFalseTest(), heuristicF);
            SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(steps,stiter,k,lamb);
            //? search.traceOn();
            SearchAgent agent = new SearchAgent(problem, search);
            long endTime = System.currentTimeMillis();

            results.setSearchTime(startTime, endTime);
            results.setFinalTransmission(((Board) search.getGoalState()).getTotalTransmissionTime());
            results.setTotalTime(iniTime,endTime);

            /*System.out.println();
            //printActions(agent.getActions());
            //printInstrumentation(agent.getInstrumentation());
            System.out.println("Final state: ");
            System.out.println(search.getGoalState());
            System.out.println("Execution time: " + (endTime - startTime) + " ms");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private static void initialSolution(Board state, int initialSolution)
    {
        switch (initialSolution) {
            case 1 : state.solIni1();
                break;
            case 2 : state.solIni2();
                break;
            case 3 :
            default: state.solIni3();
                break;
        }
    }

    private static SuccessorFunction operatorSet(String operatorS, String searchMethod)
    {
        SuccessorFunction sg;
        if (operatorS.equals("Move")) sg = new SuccessorFunction1(searchMethod);
        else if (operatorS.equals("Swap")) sg = new SuccessorFunction2(searchMethod);
        else sg = new SuccessorFunction3(searchMethod);
        /*switch (operatorS) {
            case "Move": sg = new SuccessorFunction1(searchMethod);
                break;
            case "Swap": sg = new SuccessorFunction2(searchMethod);
                break;
            case "Move+Swap":
            default: sg = new SuccessorFunction3(searchMethod);
                break;
        }*/
        return sg;
    }

    private static HeuristicFunction heuristic(int criterion, char heuristic)
    {
        HeuristicFunction hf;
        if (criterion == 1) {
            switch (heuristic) {
                case 'A': hf = new HeuristicFunction1A();
                    break;
                case 'B':
                default : hf = new HeuristicFunction1B();
            }
        }
        else {  // criterion == 2
            switch (heuristic) {
                case 'A': hf = new HeuristicFunction2A();
                    break;
                case 'B': hf = new HeuristicFunction2B();
                    break;
                default : hf = new HeuristicFunction2A();
            }
        }
        return hf;
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
            String action = actions.get(i).toString();
            System.out.println(action);
        }
    }
}
