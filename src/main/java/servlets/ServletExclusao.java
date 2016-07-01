/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trabalho2.DAO.ReferenciasBibliograficas;
import trabalho2.DTOs.ReferenciaBibliografica;

/**
 *
 * @author rafael
 */
public class ServletExclusao extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean resultadoExclusao;
        long patrimonioExcluir;
        JsonObject jsonResposta;
        BufferedReader br = new BufferedReader(
                                  new  InputStreamReader(
                                           request.getInputStream(),"UTF8"));
        String textoDoJson = br.readLine();
        
        JsonObject jsonObjectDeJava = null;
        // Ler e fazer o parsing do String para o "objeto json" java
        try (   //Converte o string em "objeto json" java
                // Criar um JsonReader.
                JsonReader readerDoTextoDoJson = 
                        Json.createReader(new StringReader(textoDoJson))) {
                // Ler e fazer o parsing do String para o "objeto json" java
                jsonObjectDeJava = readerDoTextoDoJson.readObject();
                // Acabou, então fechar o reader.
        }catch(Exception e){
            e.printStackTrace();
        }
        patrimonioExcluir=(Long.parseLong(jsonObjectDeJava.getString("patrimonio")));
        resultadoExclusao= (new ReferenciasBibliograficas()).excluirReferencia(patrimonioExcluir); 

        if (resultadoExclusao){
        jsonResposta = Json.createObjectBuilder()
                    .add("resultado","Dado excluido com sucesso")
                    .build();
        }
        else{
        jsonResposta = Json.createObjectBuilder()
                    .add("resultado","Alguma coisa deu errada!! :-(")
                    .build();
        }
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();    
        out.print(jsonResposta.toString());
        out.flush();
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
