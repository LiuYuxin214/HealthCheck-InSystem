import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

//Purpose: Record the user's health status,
//         judge the user's epidemic risk and
//         control the spread of the epidemic.
//users array structure: Name Password Identity Original answer Risk level  Last check-in time Separator (0)
public class Main {
    public static void main(String[] args) throws Exception {
        //1. The necessary array variable declarations initialize and create objects.
        Scanner input = new Scanner(System.in);
        Scanner pause = new Scanner(System.in);
        java.io.File peopleFile = new java.io.File("People.txt");
        java.io.File userFile = new java.io.File("Users.txt");
        java.io.File announcementFile = new java.io.File("Announcement.txt");

        String[] users = new String[2100];

        String announcement;
        String enter;
        String user;
        String password = "";
        String status;
        String riskLevel = "";
        String lastTime1 = "";
        int position = 0;
        int people;
        Date date = new Date();

        //2. File preprocessing
        Scanner pfile = new Scanner(peopleFile);
        people = pfile.nextInt();
        Scanner ufile = new Scanner(userFile);
        for (int i = 0; i < people * 7; i++) users[i] = ufile.nextLine();
        Scanner afile = new Scanner(announcementFile);
        announcement = afile.nextLine();

        //3. Login system
        while (true) {
            boolean login = false;
            user = "";
            status = "";
            while (login == false) {
                UI.version();
                for (int i = 5; i < people * 7; i += 7) {
                    if (JudgeTime.judgeTime(users[i], date.toString()) == 2) {
                        users[i - 1] = "High";
                    }
                }
                System.out.println("Announcement: " + announcement);
                System.out.println("Enter Your User Name to log in");
                System.out.println("Or Type \"EXIT\" to exit the system.");
                System.out.print("User Name: ");
                enter = input.nextLine();
                if (enter.equals("EXIT")) {//Write the contents of the array to the file and close the program.
                    PrintWriter uoutput = new PrintWriter("Users.txt");
                    PrintWriter poutput = new PrintWriter("People.txt");
                    PrintWriter aoutput = new PrintWriter("Announcement.txt");
                    for (int j = 0; j < people * 7; j++) {
                        uoutput.println(users[j]);
                    }
                    uoutput.close();
                    poutput.print(people);
                    poutput.close();
                    aoutput.print(announcement);
                    aoutput.close();
                    System.exit(0);
                }
                for (int i = 0; i < people * 7; i += 7) {
                    if (enter.equals(users[i])) {
                        System.out.print("Password: ");
                        enter = input.nextLine();
                        if (enter.equals(users[i + 1])) {
                            user = users[i];
                            password = users[i + 1];
                            status = users[i + 2];
                            riskLevel = users[i + 4];
                            lastTime1 = users[i + 5];
                            position = i;
                            login = true;
                            UI.loading();
                            break;
                        } else {
                            UI.loading();
                            System.out.println("Wrong password, please try again.");
                            System.out.println("Press ENTER to continue");
                            pause.nextLine();
                            break;
                        }
                    }
                }
            }

            //4. Main parts of the system
            int function;
            while (login == true) {
                if (status.equals("Admin")) {//Administrator menu
                    UI.version();
                    for (int i = 5; i < people * 7; i += 7) {
                        if (JudgeTime.judgeTime(users[i], date.toString()) == 2) {
                            users[i - 1] = "High";
                        }
                    }
                    System.out.println("Welcome, Administrator " + user);
                    System.out.println("Login Time: " + date);
                    System.out.println("Last check-in time: " + lastTime1);
                    System.out.println("Risk Level: " + riskLevel);
                    System.out.println("Announcement: " + announcement);
                    System.out.println("==========================Main Menu==========================");
                    System.out.println("1.Health Check-in");
                    System.out.println("2.Log Out");
                    System.out.println("3.View the Health Check-in Data");
                    System.out.println("4.View Or Modify the Risk Level Data");
                    System.out.println("5.User Management");
                    System.out.println("6.Change Password");
                    System.out.println("7.Developer List");
                    System.out.println("8.Edit Announcement");
                    System.out.println("Enter the number before the desired function and press ENTER.");
                    System.out.print("Function number: ");
                    function = input.nextInt();
                    switch (function) {
                        case 1: { //Health Check-in
                            UI.loading();
                            if (JudgeTime.judgeTime(users[position + 5], date.toString()) == 0) {
                                users[position + 5] = date.toString();
                                System.out.println("Please answer the following questions carefully. Enter the answers (y = yes / N = no) in sequence and press enter");
                                System.out.println("Is the body temperature greater than 37.2 ℃ today? (Y/N)");
                                System.out.println("Is your Wenzhou Health Code Green? (Y/N)");
                                System.out.println("Are you in WKU? (Y/N)");
                                System.out.print("Your answer: ");
                                users[position + 3] = input.next();
                                if (!users[position + 3].equals("NYY")) users[position + 4] = "High";
                                else users[position + 4] = "Low";
                                System.out.println("Health Check-in successfully!");
                                System.out.println("Press ENTER to continue");
                                pause.nextLine();
                                break;
                            } else if (JudgeTime.judgeTime(users[position + 5], date.toString()) == 1) {
                                System.out.println("It is less than 24 hours since the last check-in. You can't check-in");
                                System.out.println("Press ENTER to continue");
                                pause.nextLine();
                                break;
                            } else if (JudgeTime.judgeTime(users[position + 5], date.toString()) == 2) {
                                users[position + 4] = "High";
                                System.out.println("You have missed your signature and need to apply for green code.");
                                System.out.println("Press ENTER to continue");
                                pause.nextLine();
                                break;
                            }

                        }
                        case 2: { //Log Out
                            UI.loading();
                            login = false;
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            System.out.println("Log out successfully!");
                            System.out.println();
                            System.out.println();
                            break;
                        }
                        case 3: { //View the Health Check-in Data
                            UI.loading();
                            System.out.println("++++++++++++++++++++++Health Check-in Data+++++++++++++++++++++++");
                            System.out.println("|No.|      User|Health Check-in Data|Risk Level|            Time|");
                            String u;
                            String h;
                            String r;
                            String t;
                            int num = 0;
                            for (int i = 0; i < people * 7; i += 7) {
                                num++;
                                u = users[i];
                                h = users[i + 3];
                                r = users[i + 4];
                                t = users[i + 5];
                                System.out.printf("|%3d|%10s|%20s|%10s|%16s|\n", num, u, h, r, t);
                            }
                            System.out.println("Press ENTER to continue");
                            pause.nextLine();
                            break;
                        }
                        case 4: { //View Or Modify the Risk Level Data
                            UI.loading();
                            System.out.println("++++++++++++++++++++++Risk Level Data+++++++++++++++++++++++");
                            System.out.println("|No.|      User|Risk Level|                            Time|");
                            String u;
                            String r;
                            String t;
                            int num = 0;
                            for (int i = 0; i < people * 7; i += 7) {
                                num++;
                                u = users[i];
                                r = users[i + 4];
                                t = users[i + 5];
                                System.out.printf("|%3d|%10s|%10s|%32s|\n", num, u, r, t);
                            }
                            boolean exit = false;
                            while (exit == false) {
                                exit = false;
                                System.out.println("Functions: 1.Edit 2.Exit");
                                System.out.print("Function Number: ");
                                function = input.nextInt();
                                switch (function) {
                                    case 1: {
                                        System.out.print("(Edit)User Name: ");
                                        enter = input.next();
                                        for (int i = 0; i < people * 7; i += 7) {
                                            if (users[i].equals(enter)) {
                                                System.out.print("(Edit)New Risk Level(1=High,2=Low): ");
                                                function = input.nextInt();
                                                if (function == 1) users[i + 4] = "High";
                                                else if (function == 2) {
                                                    users[i + 4] = "Low";
                                                    users[i + 5] = date.toString();
                                                }
                                            }
                                        }

                                        System.out.print("Succeed to Edit A Risk Level!");
                                        break;
                                    }
                                    case 2: {
                                        exit = true;
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case 5: { //User Management
                            UI.loading();
                            System.out.println("++++++++++++++++User Management+++++++++++++++++");
                            System.out.println("|No.|          User|            Password|  Status|");
                            String u;
                            String p;
                            String s;
                            int num = 0;
                            for (int i = 0; i < people * 7; i += 7) {
                                num++;
                                u = users[i];
                                p = users[i + 1];
                                s = users[i + 2];
                                System.out.printf("|%3d|%14s|%20s|%8s|\n", num, u, p, s);
                            }
                            boolean exit = false;
                            while (exit == false) {
                                exit = false;
                                System.out.println("Functions: 1.Add 2.Edit 3.Delete 4.Exit");
                                System.out.print("Function Number: ");
                                function = input.nextInt();
                                switch (function) {
                                    case 1: { //Add
                                        System.out.print("(Add)User Name: ");
                                        users[people * 7] = input.next();
                                        System.out.print("(Add)User Password: ");
                                        users[people * 7 + 1] = input.next();
                                        System.out.print("(Add)User Status(1=Admin,2=User): ");
                                        function = input.nextInt();
                                        switch (function) {
                                            case 1:
                                                users[people * 7 + 2] = "Admin";
                                                break;
                                            case 2:
                                                users[people * 7 + 2] = "User";
                                                break;
                                        }
                                        users[people * 7 + 3] = "NYY";
                                        users[people * 7 + 4] = "Low";
                                        users[people * 7 + 5] = date.toString();
                                        users[people * 7 + 6] = "0";
                                        people++;
                                        System.out.println("Succeed to Add A User!");
                                        exit = true;
                                        break;
                                    }
                                    case 2: { //Edit
                                        System.out.print("(Edit)User Name: ");
                                        enter = input.next();
                                        for (int i = 0; i < people * 7; i += 7) {
                                            if (users[i].equals(enter)) {
                                                System.out.print("(Edit)New User Name: ");
                                                users[i] = input.next();
                                                System.out.print("(Edit)New User Password: ");
                                                users[i + 1] = input.next();
                                                System.out.print("(Add)User Status(1=Admin,2=User): ");
                                                function = input.nextInt();
                                                switch (function) {
                                                    case 1:
                                                        users[people * 7 + 2] = "Admin";
                                                        break;
                                                    case 2:
                                                        users[people * 7 + 2] = "User";
                                                        break;
                                                }
                                            }
                                        }
                                        System.out.println("Succeed to Edit A User!");
                                        exit = true;
                                        break;
                                    }
                                    case 3: { //Delete
                                        System.out.print("(Delete)User Name(Type \"BACK\" to back): ");
                                        enter = input.next();
                                        if (enter.equals("BACK")) break;
                                        people--;
                                        for (int i = 0; i < people * 7; i += 7) {
                                            if (users[i].equals(enter)) {
                                                for (int j = i; j <= i + 5; j++) users[j] = "";
                                                users[i + 6] = "0";
                                            }
                                        }
                                        System.out.println("Succeed to Delete A User!");
                                        exit = true;
                                        break;
                                    }
                                    case 4: { //Exit
                                        exit = true;
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case 6: { //Change Password
                            UI.loading();
                            System.out.print("Please enter your old password: ");
                            enter = input.nextLine();
                            enter = input.nextLine();
                            if (enter.equals(password)) {
                                System.out.print("Please enter your new password: ");
                                users[position + 1] = input.nextLine();
                                login = false;
                                System.out.print("Password changed successfully, please login again.");
                                break;
                            } else {
                                System.out.print("Wrong password, please try again.");
                                break;
                            }

                        }
                        case 7: { //Developer List
                            UI.version();
                            System.out.println("The Developer is");
                            System.out.println("Liu Yuxin, Liu Chang from WKU");
                            System.out.println("Press ENTER to continue");
                            pause.nextLine();
                            break;
                        }
                        case 8: { //Edit Announcement
                            System.out.println("Now Announcement: " + announcement);
                            System.out.println("Functions: 1.Edit 2.Exit");
                            System.out.print("Function Number: ");
                            function = input.nextInt();
                            switch (function) {
                                case 1: {
                                    System.out.print("New Announcement: ");
                                    announcement = input.nextLine();
                                    announcement = input.nextLine();
                                    break;
                                }
                                case 2: {
                                    break;
                                }
                            }
                        }
                        default: {
                            System.out.println();
                            System.out.println();
                            System.out.println("Wrong Function Number, Please try again");
                            break;
                        }
                    }
                } else if (status.equals("User")) { //User menu
                    UI.version();
                    for (int i = 5; i < people * 7; i += 7) {
                        if (JudgeTime.judgeTime(users[i], date.toString()) == 2) {
                            users[i - 1] = "High";
                        }
                    }
                    System.out.println("Welcome, User " + user);
                    System.out.println("Login Time: " + date);
                    System.out.println("Last check-in time: " + lastTime1);
                    System.out.println("Risk Level: " + riskLevel);
                    System.out.println("Announcement: " + announcement);
                    System.out.println("==========================Main Menu==========================");
                    System.out.println("1.Health Check-in");
                    System.out.println("2.Log Out");
                    System.out.println("3.Change Password");
                    System.out.println("4.Developer List");
                    System.out.println("Enter the number before the desired function and press ENTER.");
                    System.out.print("Function number: ");
                    function = input.nextInt();
                    switch (function) {
                        case 1: { //Health Check-in
                            UI.loading();
                            if (JudgeTime.judgeTime(users[position + 5], date.toString()) == 0) {
                                users[position + 5] = date.toString();
                                System.out.println("Please answer the following questions carefully. Enter the answers (y = yes / N = no) in sequence and press enter");
                                System.out.println("Is the body temperature greater than 37.2 ℃ today? (Y/N)");
                                System.out.println("Is your Wenzhou Health Code Green? (Y/N)");
                                System.out.println("Are you in WKU? (Y/N)");
                                System.out.print("Your answer: ");
                                users[position + 3] = input.next();
                                if (!users[position + 3].equals("NYY")) users[position + 4] = "High";
                                else users[position + 4] = "Low";
                                System.out.println("Health Check-in successfully!");
                                System.out.println("Press ENTER to continue");
                                pause.nextLine();
                                break;
                            } else if (JudgeTime.judgeTime(users[position + 5], date.toString()) == 1) {
                                System.out.println("It is less than 24 hours since the last check-in. You can't sign in");
                                System.out.println("Press ENTER to continue");
                                pause.nextLine();
                                break;
                            } else if (JudgeTime.judgeTime(users[position + 5], date.toString()) == 2) {
                                users[position + 4] = "High";
                                System.out.println("You have missed your signature and need to apply for green code.");
                                System.out.println("Press ENTER to continue");
                                pause.nextLine();
                                break;
                            }

                        }
                        case 2: { //Log Out
                            UI.loading();
                            login = false;
                            System.out.println();
                            System.out.println();
                            System.out.println("Log out successfully!");
                            System.out.println();
                            System.out.println();
                            break;
                        }
                        case 3: { //Change Password
                            UI.loading();
                            System.out.print("Please enter your old password: ");
                            enter = input.nextLine();
                            enter = input.nextLine();
                            if (enter.equals(password)) {
                                System.out.print("Please enter your new password: ");
                                users[position + 1] = input.nextLine();
                                login = false;
                                System.out.print("Password changed successfully, please login again.");
                                break;
                            } else {
                                System.out.print("Wrong password, please try again.");
                                break;
                            }

                        }
                        case 4: { //Developer List
                            UI.version();
                            System.out.println("The Developer is");
                            System.out.println("Liu Yuxin, Liu Chang from WKU");
                            System.out.println("Press ENTER to continue");
                            pause.nextLine();
                            break;
                        }
                        default: {
                            System.out.println("Wrong Function Number, Please try again");
                            System.out.println("Press ENTER to continue");
                            pause.nextLine();
                            break;
                        }
                    }
                } else { //Error handling
                    UI.loading();
                    System.out.println("There is no such user. Please try again");
                    System.out.println("Press ENTER to continue");
                    pause.nextLine();
                    break;
                }
            }
        }
    }
}