import java.util.Random;

public class ParalellClass {
	int length;
	int biggest;

	//
	public ParalellClass(int n, int k) {
		length = n;
		biggest = k;
	}

	// Denne metoden fyller et array med tilfeldige tall. De som skal
	// sammenlignes bruker samme x, saa de faar samme tall.
	public void makeRandomArray(int[] a, int x) {
		Random r = new Random(x);
		for (int i = 0; i < length; i++) {
			a[i] = r.nextInt(length);
		}
	}

	// Dette er metoden som oppretter traadene, og sorterer deres resultat.
	public void processList(int[] a) {
		int antKjerner = Runtime.getRuntime().availableProcessors();
		Thread[] traad = new Thread[antKjerner];
		for (int j = 0; j < antKjerner - 1; j++) {
			traad[j] = new Thread(
					new Worker(a, (((length) / antKjerner) * (j)), (((length) / antKjerner) * (j + 1)), biggest));
		}
		traad[antKjerner - 1] = new Thread(
				new Worker(a, (((length) / antKjerner) * (antKjerner - 1)), length, biggest));
		for (int j = 0; j < antKjerner; j++) {
			traad[j].start();
		}
		for (int j = 0; j < antKjerner; j++) {
			try {
				traad[j].join();
			} catch (Exception e) {
				return;
			}
			;
		}
		int tmp;
		// Traadene legger sine resultater i de k foerste plassene i sine deler
		// av arrayene, saa denne forloopen leger alle foerst i arrayet.
		for (int j = 1; j < antKjerner; j++) {
			for (int k = 0; k < biggest; k++) {
				tmp = a[k + (biggest * j)];
				a[k + (biggest * j)] = a[(((length) / antKjerner) * (j)) + k];
				a[(((length) / antKjerner) * (j)) + k] = tmp;
			}
		}
		// Denne koden sorterer det som er lagt foerst i arrayet.
		int i, t;
		for (int k = 0; k < biggest * antKjerner; k++) {
			t = a[k + 1];
			i = k;
			while (i >= 0 && a[i] < t) {
				a[i + 1] = a[i];
				i--;
			}
			a[i + 1] = t;
		}
	}

	// Dette er metoden som tar for seg den sekvensielle implementasjonen av
	// algoritmen A2, forklart i oppgaveteksten.
	void insertSort(int[] a) {
		int i, t;
		for (int k = 0; k < biggest; k++) {
			t = a[k + 1];
			i = k;
			while (i >= 0 && a[i] < t) {
				a[i + 1] = a[i];
				i--;
			}
			a[i + 1] = t;
		}
		i = a[biggest - 1];
		for (int j = biggest; j < length; j++) {
			if (a[j] > i) {
				a[biggest - 1] = a[j];
				a[j] = i;
				for (int s = biggest - 1; s > 0; s--) {
					if (a[s] >= a[s - 1]) {
						t = a[s - 1];
						a[s - 1] = a[s];
						a[s] = t;
					} else {
						break;
					}
				}
				i = a[biggest - 1];
			}
		}
	}

	// Dette er den indre klassen for traadene.
	class Worker implements Runnable {
		int[] a;
		int start;
		int slutt;
		int kk;
		int lowest;

		public Worker(int[] list, int aa, int ab, int ac) {
			a = list;
			start = aa;
			slutt = ab;
			kk = ac;
			lowest = kk + start;
		}

		// Traadene faar en del av arrayet, og utfoerer sorteringsalgoritme
		// A2(se i oppgaveteksten) paa dem.
		public void run() {
			int i, t;
			for (int k = start; k < lowest; k++) {
				t = a[k + 1];
				i = k;
				while (i >= 0 && a[i] < t) {
					a[i + 1] = a[i];
					i--;
				}
				a[i + 1] = t;
			}
			for (int j = lowest; j < slutt; j++) {
				if (a[j] > a[lowest - 1]) {
					i = a[lowest - 1];
					a[lowest - 1] = a[j];
					a[j] = i;
					for (int s = lowest - 1; s > start; s--) {
						if (a[s] > a[s - 1]) {
							t = a[s - 1];
							a[s - 1] = a[s];
							a[s] = t;
						} else {
							s = 0;
						}
					}
				}
			}
		}
	}
}
// Runtime.getRuntime().availableProcessors()