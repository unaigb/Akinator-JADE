package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utilities.Pregunta;
import utilities.Utils;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

@SuppressWarnings("serial")
public class BehaviourQuienEsQuien extends CyclicBehaviour {

	@Override
	public void action() {
		// Declaración de variables
		String answer = "";
		String content = "";
		String initialMSG = "\n================= QuienEsQuien =================\n\n";
		String questionText = "PREGUNTA: ";
		ACLMessage msg = null;
		Pregunta question = null;
		ConverterUtils.DataSource source = null;
		Instances dataEntrenamiento = null;
		
		// Funciones para el correcto funcionamiento de las preguntas (Obtención de datos, control de errores, etc.)

		try {
			source = new ConverterUtils.DataSource("famosos.csv");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.print("Error en DataSource: ");
			e1.printStackTrace();
			System.out.println();
		}

		try {
			dataEntrenamiento = source.getDataSet();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.print("Error en getDataSet: ");
			e1.printStackTrace();
			System.out.println();
		}

		// Indicar el atributo con la categorï¿½a a clasificar
		if (dataEntrenamiento.classIndex() == -1) {
			dataEntrenamiento.setClassIndex(0);
		}
		J48 j48 = new J48();
		try {
			j48.setOptions(new String[] { "-C", "0.25", "-M", "1" });
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.print("Error en setOptions: ");
			e1.printStackTrace();
			System.out.println();
		}

		j48.setUnpruned(true);
		try {
			j48.buildClassifier(dataEntrenamiento);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.print("Error en buildClassifier: ");
			e1.printStackTrace();
			System.out.println();
		}

		try {
			j48.classifyInstance(dataEntrenamiento.lastInstance());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.print("Error en classifyInstance: ");
			e1.printStackTrace();
			System.out.println();
		}

		try {
			question = new Pregunta(j48);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.print("Error en new Pregunta(j48): ");
			e1.printStackTrace();
			System.out.println();
		}
		
		// Conexión establecida

		// El inicio de conexión entre el Cliente y el QuienEsQuien se realiza al principio y tan solo una vez
		
		// Se recibe el mensaje de Hola Guesser de comprobación de conexión
		msg = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

		content = Utils.readMsg(msg);
		System.out.println("Respuesta de " + msg.getSender().getLocalName() + " con conversationID "
				+ msg.getConversationId() + " : " + content);
		
		
		// Fase de preguntas
		
		// Aquí se van enviando una detrás de otra las preguntas hasta adivinar el famoso.
		// Primero se envía la pregunta inicial, se espera a la repuesta y luego se envía la siguiente
		
		content = initialMSG;
		content += questionText;
		content += question.obtenerPreguntaNodo();
		Utils.sendMsg(this.myAgent, content, msg.getSender());
		msg = this.myAgent.blockingReceive(MessageTemplate.MatchConversationId(msg.getConversationId()));
		answer = Utils.readMsg(msg);
		question.navegarNodoRespuesta(answer);

		// Ejecución continua hasta que no se llegue al nodo final del arbol y por tanto al famoso
		while (!question.esNodoFinal()) {
			content = questionText;
			content += question.obtenerPreguntaNodo();
			Utils.sendMsg(this.myAgent, content, msg.getSender());
			msg = this.myAgent.blockingReceive(MessageTemplate.MatchConversationId(msg.getConversationId()));
			answer = Utils.readMsg(msg);
			question.navegarNodoRespuesta(answer);
		}
		// Manda respuesta final
		Utils.sendMsg(this.myAgent, "Es " + question.obtenerPreguntaNodo(), msg.getSender());
	}
}
