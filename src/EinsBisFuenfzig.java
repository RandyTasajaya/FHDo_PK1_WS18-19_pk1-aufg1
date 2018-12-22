public class EinsBisFuenfzig
{
    public static void main(String[] args) {

        if(args.length == 1) {
            int zahl = Integer.parseInt(args[0]);
            for(int i = 1; i <= zahl; i++) {
                System.out.print(i + " ");
            }
        }
        else
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

// Eine Aenderung im Editor von Gitlab