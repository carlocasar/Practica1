// Hill Climbing - Ambos operadores

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SuccessorFunctionHC3 implements SuccessorFunction {

    public List getSuccessors (Object estat)
    {
        ArrayList llistaSuccessors = new ArrayList();

        Board board = (Board) estat;
        Servers servers = board.getServers();
        Requests requests = board.getRequests();
        ArrayList<Integer> assigs = board.getServReq();

        int nreq = requests.size();
        for (int req = 0; req < nreq; ++req) {
            Set<Integer> replications = servers.fileLocations(requests.getRequest(req)[1]);
            Iterator<Integer> repl = replications.iterator();
            while (repl.hasNext()) {
                Board successor = new Board(board);
                llistaSuccessors.add(successor.move(req,repl.next()));
            }
        }

        int nreq = requests.size();
        for (int req1 = 0; req1 < nreq; ++req1) {
            Set<Integer> replications1 = servers.fileLocations(requests.getRequest(req1)[1]);
            for (int req2 = req1+1; req2 < nreq; ++req2) {
                Set<Integer> replications2 = servers.fileLocations(requests.getRequest(req2)[1]);
                if (replications1.contains(assigs.get(req2)) && replications2.contains(assigs.get(req1)) ) {
                    Board successor = new Board(board);
                    llistaSuccessors.add(successor.swap(req1, req2));
                }
            }
        }

        return llistaSuccessors;
    }
}