
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.jasper.tagplugins.jstl.ForEach;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author student
 */
public class ResultHelper {

    public static void writeResults(HashMap chosenLanguages) {
        try{
            HashMap<String, Integer> pollResults = (HashMap) ResultHelper.readResults();
            FileWriter pollResultsWriter = new FileWriter("/home/wiki/poll_results.txt");
            for(Iterator i = chosenLanguages.keySet().iterator(); i.hasNext(); ){
                String key = (String) i.next();
                int votes = 0;
                if(pollResults.containsKey(key)){
                    votes = (int) pollResults.get(key) + 1;
                    pollResults.replace(key, votes);
                }else{
                    votes = 1;
                    pollResults.put(key, votes);
                }
               
                //pollResultsWriter.write(key + ":" + Integer.toString(votes) + "\n");
            }
            
            for(String key : pollResults.keySet()){
                pollResultsWriter.write(key + ":" + pollResults.get(key) + "\n");
            }
            
            pollResultsWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static HashMap readResults() {
        String line = "";
        HashMap pollResults = new HashMap();
        FileInputStream pollResultStream = null;
        BufferedReader pollResultReader = null;
        try{
           File resultsFile = new File("/home/wiki/poll_results.txt");
           pollResultStream = new FileInputStream(resultsFile);
           pollResultReader = new BufferedReader(new InputStreamReader(pollResultStream));
           while((line = pollResultReader.readLine()) != null){
               String[] elem = line.split(":");
               pollResults.put(elem[0], Integer.parseInt(elem[1]));
           }
           pollResultReader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return pollResults;
    }
    
    public static void printAllResults(HashMap pollResults, PrintWriter out){
        Set setOfChosenLanguages = pollResults.entrySet();
        Iterator languageIterator = setOfChosenLanguages.iterator();
        out.println("<br> <h2> Poll results : </h2> <br> ");
        while(languageIterator.hasNext()){
            Map.Entry languageEntry = (Map.Entry) languageIterator.next();
            out.println(languageEntry.getKey() + " : " + languageEntry.getValue() + "<br/>");
        }           
    }
    
    public static void printExcelFile(HashMap<String, Integer> pollResults, PrintWriter out){
        for(String key : pollResults.keySet()){
            out.println(key + "\t" + pollResults.get(key));
        }
    }
    
}
