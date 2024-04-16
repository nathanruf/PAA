package motoqueiro;

public class Main {

	public static void main(String[] args) {
		long tempo = System.currentTimeMillis();
		Entrega.calcular();
		tempo = System.currentTimeMillis() - tempo;
		System.out.println("Tempo (ms): " + tempo);
	}
	
}
