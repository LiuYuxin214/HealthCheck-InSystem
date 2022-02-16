//Signature: middleCharacters: void -> String
//Purpose: Display character drawing interface
//Examples:
// 			UI.version() -> WKU Health Check-In System v1.0
//          UI.loading() -> Loading...
public class UI {

    public static void version() {
            System.out.println();
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("+                                              +");
            System.out.println("+        WKU Health Check-In System v1.0       +");
            System.out.println("+                                              +");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println();
    }

    public static void loading() {
        System.out.println();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+                                              +");
        System.out.println("+                  Loading...                  +");
        System.out.println("+                                              +");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    }

}
