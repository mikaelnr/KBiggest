
public class Sekvensiell {
	public static void main(String[] args){
		Sorter sorter = new Sorter(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		sorter.testIfWorking();
	}
}
