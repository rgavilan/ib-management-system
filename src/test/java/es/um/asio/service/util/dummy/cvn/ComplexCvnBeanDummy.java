package es.um.asio.service.util.dummy.cvn;

import es.um.asio.domain.cvn.CvnBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplexCvnBeanDummy extends CvnBean {

	public String bar;

	public SimpleCvnBeanDummy simpleCvnBean;
}
