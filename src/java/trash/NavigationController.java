package trash;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author pc
 */
@ManagedBean(name="navigationController", eager=true)
@RequestScoped
public class NavigationController implements Serializable{
    
    @ManagedProperty(value="#{param.pageId}")
    private String pageId;
}
