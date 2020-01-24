package ci.gouv.dgbf.sib.taskmanager.tools.log;

import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

public class MyLog {

    private Logger logger;
    private Boolean inlogFile;

    static String format = "%d{HH:mm:ss} %-5p [%c{2.}] (%t) %s%e%n";



    public MyLog(String className, Boolean inlogFile) {
        this.logger = Logger.getLogger(className);
        this.inlogFile = inlogFile;
        if(this.inlogFile==true){
            try {
                Handler fh = new FileHandler(ParametersConfig.path_log_file+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+".log");
                this.logger.addHandler(fh);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void info(Object object){
        try {
            this.logger.info(object.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void error(String message){
        try {
            this.logger.setLevel(Level.SEVERE);
            this.logger.info(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
