/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.imageio.stream.ImageOutputStream;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author wiki
 */
public class GenerateChart extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/jpeg");
        try {
            HashMap<String, Integer> pollResults = ResultHelper.readResults();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            for(String key : pollResults.keySet()){
                dataset.addValue(pollResults.get(key), key, key);
            }
            
            JFreeChart chart = ChartFactory.createBarChart(
                "Wyniki ankiety", // tytul wykresu
                "Języki", // etykieta osi X
                "Głosy", // etykieta osi Y
                dataset, // dane
                PlotOrientation.VERTICAL, // orientacja
                true, // dolaczenie legendy?
                false, // tooltips?
                false // URL?
            );
            
            BufferedImage img = chart.createBufferedImage(400,400,BufferedImage.TYPE_INT_RGB,null);
            OutputStream outputImage = response.getOutputStream();
            ImageIO.write(img, "jpg", outputImage);
            outputImage.close();
        }catch (Exception e){}
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
