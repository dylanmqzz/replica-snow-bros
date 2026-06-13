package Utils.BehaviorTree;

public abstract class Nodo {
    public enum Status {
        SUCCES, FAILURE, RUNNING
    }
    
    public abstract Status tick();
    
}
