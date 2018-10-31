public class EinsBisFuenfzig
{
	public static void main(String[] args) {
		
		for(int i = 1; i <= 50; i++) {
			if(i % 3 == 0 && i % 5 == 0) {
				System.out.print("PingPong" + " ");
			} else {
				if(i % 3 == 0) {
					System.out.print("Ping" + " ");
				} else {
					if(i % 5 == 0) {
						System.out.print("Pong" + " ");
					} else {
						System.out.print(i + " ");
					}
				}
			}
		}

	}
}