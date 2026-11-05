package agents;

import behaviours.BehaviourCliente;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class AgentCliente extends Agent{

	public void setup() {
		
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setName("Client");
		sd.setType("client");
		sd.addOntologies("ontologia");
		sd.addLanguages(new SLCodec().getName());

		dfad.addServices(sd);

		try {
			DFService.register(this, dfad);
		} catch (FIPAException e) {
			System.err.println("Error : Client.setup : Agent " + getLocalName() + ": " + e.getMessage());
		}
		
		addBehaviour(new BehaviourCliente());
	}
}
