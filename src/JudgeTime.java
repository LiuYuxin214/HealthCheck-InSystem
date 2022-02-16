//Signature: middleCharacters: String -> int
//Purpose: Judge whether the check-in time is legal
//Examples:
// 			JudgeTime.judgeTime(Thu Nov 25 10:47:43 CST 2021, Thu Nov 23 10:47:43 CST 2021) -> 2
public class JudgeTime {
    public static int judgeTime(String startTime, String endTime) {
        if (startTime.substring(8, 10).equals(endTime.substring(8, 10))) return 0;//OK
        else if (Integer.parseInt(endTime.substring(8, 10)) - Integer.parseInt(startTime.substring(8, 10)) == 1) return 1;//Early
        else return 2;//Late
    }

}
