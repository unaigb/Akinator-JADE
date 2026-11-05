package agents;

import behaviours.BehaviourQuienEsQuien;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.core.behaviours.ThreadedBehaviourFactory;

@SuppressWarnings("serial")
public class AgentQuienEsQuien extends Agent {

	public void setup() {
    	
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setName("AgentQuienEsQuien");
        sd.setType("quienesquien");
        sd.addOntologies("ontologia");
        sd.addLanguages(new SLCodec().getName());

        dfad.addServices(sd);

        try {
                DFService.register(this, dfad);
        } catch (FIPAException e) {
                System.err.println("Error : AgentQuienEsQuien.setup : Agent " + getLocalName() + ": " + e.getMessage());
        }
        
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        ThreadedBehaviourFactory threadedBehaviourFactory = new ThreadedBehaviourFactory();
        BehaviourQuienEsQuien a1 = new BehaviourQuienEsQuien();
        BehaviourQuienEsQuien a2 = new BehaviourQuienEsQuien();
        BehaviourQuienEsQuien a3 = new BehaviourQuienEsQuien();
        BehaviourQuienEsQuien a4 = new BehaviourQuienEsQuien();
        BehaviourQuienEsQuien a5 = new BehaviourQuienEsQuien();
		parallelBehaviour.addSubBehaviour(threadedBehaviourFactory.wrap(a1));
        parallelBehaviour.addSubBehaviour(threadedBehaviourFactory.wrap(a2));
        parallelBehaviour.addSubBehaviour(threadedBehaviourFactory.wrap(a3));
        parallelBehaviour.addSubBehaviour(threadedBehaviourFactory.wrap(a4));
        parallelBehaviour.addSubBehaviour(threadedBehaviourFactory.wrap(a5));
		addBehaviour(parallelBehaviour);
		
	}
}
