package ci.gouv.dgbf.sib.taskmanager.tools;

public class ParametersConfig {

    public ParametersConfig() {
    }

    public static final String status_enable ="enable";
    public static final String status_is_Waiting ="is_waiting";
    public static final String status_delete ="delete";
    public static final String status_complete ="complete";
    public static final String status_block ="blocked";
    public static String PROCESS_SUCCES = "1";
    public static String PROCESS_FAILED = "0";
    public static String SUCCES_CREATE = "Création effectuée avec succès";
    public static String SUCCES_UPDATE = "Modification effectuée avec succès";
    public static String SUCCES_DELETE = "Suppression effectuée avec succès";
    public static String SUCCES_LINKED = "Liaison effectuée avec succès";
    public static String SUCCES_BLOCKED = "Blocage effectué avec succès";

    public static String FAILED_CREATE = "Echec de la création";
    public static String FAILED_UPDATE = "Echec de la modification";
    public static String FAILED_DELETE = "Echec de la suppression";

    public static String FAILED_LINKED = "Echec lors de la liaison";
    public static String FAILED_BLOCKED = "Echec lors dU blocage";

    public static String projectNotFoundMessage = "Projet introuvable dans la base de données";
    public static String personNotFoundMessage = "Personne introuvable dans la base de données";
    public static String genericParameterNullMessage = "Paramètre null";
    public static String genericNotFoundMessage = "Element introuvable";
    public static String codeAlreadyExist = "Ce code est déja existant";

    public static String taskTableNameInApplication = "Tache";
    public static String projectTableNameInApplication = "Projet";
    public static String personTableNameInApplication = "Personne";
    public static String userTableNameInApplication = "Utitilisateur";
    public static String activityTableNameInApplication = "Activité";

    public static String GENERIC_MESSAGE_PROCESS_FAILED = "Echec lors de l'opération. Veuillez svp vérifier les paramètres";
    public static String PROJECT_PERSON_ALREADY_EXIST = "Cette personne est déja liée à ce projet";
    public static String PROJECT_PERSON_NOT_EXIST = "Cette personne n'est pas affectée à ce projet";
    public static String FAILED_LINKED_TASK_TO_PERSON = "Echec dans l'attribution de la tache à la personne";
    public static String FAILED_TASK_ALREADY_LINKED_TO_PERSON = "Cette tâche est déja attribuée à cette personne";
    public static String FAILED_TASK_ALREADY_LINKED_TO_PROJECT = "Cette tâche est déja attribuée à un projet";


    public static String id_assignateTaskOperation = "1";
    public static String id_updateOperation = "2";
    public static String id_choseProjectLeadOperation = "3";
    public static String id_assignateActivityToTaskOperation = "4";
    public static String id_assignateTaskToProject = "5";

    public static String message_parameter_login_null = "Veuillez renseigner le login SVP";
    public static String message_parameter_pwd_null = "Veuillez renseigner le mot de passe SVP";



}

