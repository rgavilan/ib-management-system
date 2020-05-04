package es.um.asio.service.util.dummy.cvn;

import java.util.List;

import es.um.asio.domain.cvn.CvnBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CvnBeanWithCvnBeanCollectionDummy extends CvnBean {
    
    private List<SimpleCvnBeanDummy> simpleCvnBeans;
}
