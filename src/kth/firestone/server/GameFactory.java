package kth.firestone.server;

public class GameFactory {
	
	public static void main(String[] args) {
		GameFactory gf = new GameFactory();
		ServerFactory sf = new ServerFactory(gf);
		sf.createServer().start();
	}
	
}
