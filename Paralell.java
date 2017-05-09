import java.util.Arrays;
import java.util.Random;

public class Paralell {

	public static void main(String[] args) {
		// Foerst setter vi parameterne inn i variabler, for saa aa opprette et
		// objekt av klassen ParalellClass, som er hvor den faktiske sorteringen
		// skjer.
		int length = Integer.parseInt(args[0]);
		int biggest = Integer.parseInt(args[1]);
		ParalellClass sort = new ParalellClass(length, biggest);
		// Saa opprettes det 3 arrayer, en for traadsortering, en for den
		// sekvensielle sortering, og sist en for Arrays.sort. Disse fylles med
		// en rekke tilfeldige tall, men alle faar de samme.
		Random r = new Random();
		int randomSource = r.nextInt(length);
		int[] a = new int[length];
		int[] b = new int[length];
		int[] c = new int[length];
		sort.makeRandomArray(a, randomSource);
		sort.makeRandomArray(b, randomSource);
		sort.makeRandomArray(c, randomSource);
		long startTime;
		long endTime;
		// Her kjoerer vi sorteringen av listene, og vi tar ogsaa tiden paa hvor
		// lang tid de bruker.
		System.out.println("n: " + length + " k: " + biggest);
		System.out.println("Starter Sorting naa.");
		startTime = System.nanoTime();
		sort.processList(a);
		endTime = System.nanoTime();
		double time = (double) (endTime - startTime) / 1000000.0;
		System.out.println("Threads brukte: " + time);
		startTime = System.nanoTime();
		sort.insertSort(c);
		endTime = System.nanoTime();
		time = (double) (endTime - startTime) / 1000000.0;
		System.out.println("Sekvensielt brukte: " + time);
		startTime = System.nanoTime();
		Arrays.sort(b);
		endTime = System.nanoTime();
		time = (double) (endTime - startTime) / 1000000.0;
		System.out.println("Arrays.sort brukte: " + time);
		// her sjekkes det at de to selvlagde sorteringsmetodene faar samme
		// resultat som Arrays.sort
		System.out.println("Traad, Sekvensiellt, Arrays.sort");
		for (int i = 0; i < biggest; i++) {
			System.out.println(a[i] + " " + c[i] + " " + b[length - 1 - i]);
		}
	}
}
