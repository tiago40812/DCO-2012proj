package ui;

import java.util.HashMap;
import java.util.Map;

public abstract class MemoMap {
    private static Map<Object,Object> memory = new HashMap<Object,Object>();
    protected abstract Object executeOperation();
    
    protected Object getResult() {
        Object result = null;
        if (memory.containsKey(this)) 
            result = memory.get(this);
        else {
            result = executeOperation();
            memory.put(this, result);
        }
        return result;
    }
    
    protected boolean isInCache() {
        return memory.containsKey(this);
    }
    
    protected void put(Object result) {
        memory.put(this, result);
    }
}
