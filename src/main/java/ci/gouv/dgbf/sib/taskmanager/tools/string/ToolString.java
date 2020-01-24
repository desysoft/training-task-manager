package ci.gouv.dgbf.sib.taskmanager.tools.string;

public class ToolString {

    public static String getStringByArrayStackTraceElement(Exception e){
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString()).append("\n");
        for(StackTraceElement ste : e.getStackTrace()){
            sb.append(ste.toString()).append("\n");
        }
        return sb.toString();
    }


}
