package yaruliy.lgaming.entity;
import yaruliy.lgaming.Config;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Status extends AbstractRequestBody{
    @XmlAttribute
    private int id;

    @XmlAttribute
    private int state;

    @XmlAttribute
    private int substate;

    @XmlAttribute
    private int code;

    @XmlAttribute(name = "final")
    private int final0;

    @XmlAttribute
    private int trans;

    @XmlAttribute
    private int service;

    @XmlAttribute
    private int sum;

    @XmlAttribute
    private int commission;

    @XmlAttribute
    private int sum_prov;

    @XmlAttribute
    private String server_time;

    public Status(int id, int state, int substate, int code, int final0, int trans,
                  int sum, int commission, int sum_prov) {
        super();
        this.service = Config.getServiceNumber();
        this.id = id;
        this.state = state;
        this.substate = substate;
        this.code = code;
        this.final0 = final0;
        this.trans = trans;
        this.sum = sum;
        this.commission = commission;
        this.sum_prov = sum_prov;
        this.server_time = "time";
    }

    public Status(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setSubstate(int substate) {
        this.substate = substate;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setFinal0(int final0) {
        this.final0 = final0;
    }

    public void setTrans(int trans) {
        this.trans = trans;
    }

    public void setService(int service) {
        this.service = service;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public void setSum_prov(int sum_prov) {
        this.sum_prov = sum_prov;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}