package ui;

import java.io.File;

public abstract class MemoFile extends MemoMap {
    protected abstract File getResultFile();
    protected abstract Object readResult(File file);
    protected abstract void writeResult(File file, Object result);
    
    
    protected Object getResultWithCache() {
        Object result = null;
        if (isInCache()) {
            result = super.getResult();
        } else {
           File file = getResultFile();
           if (file.exists()) { 
               result = readResult(file);
               put(result);
           } else {
               result = super.getResult();
               writeResult(file, result);
           }
        }
        return result;
    }
    
    protected Object getResult() {
        long start = System.nanoTime();
        Object result = null;
        File file = getResultFile();
        if (file.exists()) {
           // System.out.println("in MemoFile.getResult get result from file: " + file);
           result = readResult(file);
           put(result);
           // System.out.println("in MemoFile.getResult duration: " + ((System.nanoTime() - start) / 1000000) + " ms");
        } else {
           System.out.println("in MemoFile.getResult compute result and store in : " + file);
           result = executeOperation();
           writeResult(file, result);
           System.out.println("in MemoFile.getResult duration: " + ((System.nanoTime() - start) / 1000000) + " ms");
        }
        return result;
    }
}
