/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author student
 */
public class PollServlet extends HttpServlet {

    private PrintWriter out;
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try{
            getPrintWriter(response);
            if(checkCookies(request.getCookies())){
                ResultHelper.writeResults((HashMap) request.getParameterMap());
                printResult(request);
                
                Cookie mycookie = new Cookie("mycookie", "not_important");
                mycookie.setMaxAge(2 * 60 * 60);
                response.addCookie(mycookie);
                
            }else{
                out.println("You already voted! Don't try to cheat the system !");
            }
            ResultHelper.printAllResults(ResultHelper.readResults(), out);
            out.println("<br> <a href='excel'>Generate Excel file </a>");
            out.println("<br> <a href='chart'>Generate chart </a>");
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    private boolean checkCookies(Cookie[] cookies){
        if(cookies != null){
            for(int i = 0; i < cookies.length; i++){
                Cookie c = cookies[i];
                if(c.getName().equals("mycookie")){
                    return false;
                }
            }
        }
        return true;
    }
    
    private void getPrintWriter(HttpServletResponse response) throws IOException{
        out = response.getWriter();
    }
    
    private void printResult(HttpServletRequest request){
        out.println("<h1>Following languages were chosen :</h1><br>");
        getAllParametersMap(request, out);
    }
    
    private static void getAllParametersMap(HttpServletRequest request , PrintWriter out){
        HashMap mapOfChosenLanguages = (HashMap) request.getParameterMap();
        Set setOfChosenLanguages = mapOfChosenLanguages.entrySet();
        Iterator languageIterator = setOfChosenLanguages.iterator();
        while(languageIterator.hasNext()){
            Map.Entry languageEntry = (Map.Entry) languageIterator.next();
            out.println(languageEntry.getKey() + "<br/>");
        }             
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
