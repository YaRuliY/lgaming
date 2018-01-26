package yaruliy.lgaming.entity;
import yaruliy.lgaming.Config;

public abstract class AbstractRequestBody {
    protected int point;
    public AbstractRequestBody(){ this.point = Config.getPointNumber(); }
}