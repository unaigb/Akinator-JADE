package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utilities.Utils;

import java.util.Scanner;

@SuppressWarnings("serial")
public class BehaviourCliente extends CyclicBehaviour {

	@Override
	public void action() {
		// Declaración de Variables
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		@SuppressWarnings("unused")
		String text = "", answer = "", trash = "";
		
		
		// Notificación de conexión. Usado para saber que se han conectado correctamente al inicio
		
		Utils.enviarMensaje(this.myAgent, "quienesquien", "Hola Guesser", ACLMessage.REQUEST);

		
		// Fase de preguntas  respuestas
		// Se comprueba que la pregunta que llega no sea directamente la adivinación
		while (!text.startsWith("Es ")) {
			// Se recibe la pregunta
			ACLMessage msg = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			text = Utils.readMsg(msg);
			// Imprime la pregunta
			System.out.println(text);
			// Bucle que comprueba si el mensaje recibido es la adivinación, si no es así, continua
			if (!text.startsWith("Es ")) {
				// Se responde a la pregunta
				while (true) {
					System.out.print("Introduzca respuesta (s/n): ");
					// Respuestas de s/n
					answer = scanner.nextLine().toLowerCase();
					if (answer.equals("s")) {
						break;
					} else if (answer.equals("n")) {
						break;
					}
				}
				// Envia finalmente la respuesta
				Utils.enviarMensaje(this.myAgent, "quienesquien", answer, ACLMessage.INFORM);
			} else {
				System.out.println("\nPulse enter/intro para continuar\n");
				trash = scanner.nextLine();
				break;
			}
		}
	}
}
