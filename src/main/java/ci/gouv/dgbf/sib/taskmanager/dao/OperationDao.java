package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Operation;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OperationDao  extends AbstractDao  implements PanacheRepositoryBase<Operation, String> {

    public Operation findByIdCustom(String id){
        return find("id = ?1 AND status = ?2",id, ParametersConfig.status_enable).firstResult();
    }
}
