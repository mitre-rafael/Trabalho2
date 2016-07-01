package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trabalho2.DAO.ReferenciasBibliograficas;
import trabalho2.DTOs.ReferenciaBibliografica;
import trabalho2.DTOs.RespostaCompletaDTO;

public class ServletApresentarTudo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ArrayList<ReferenciaBibliografica> resultados = new ArrayList<>();
        // Não é um conjunto de pares nome-valor,
        // então tem que ler como se fosse um upload de arquivo...
        String stringResposta="[";
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
        
        resultados = (new ReferenciasBibliograficas()).apresentarTudo();    
        if ((resultados == null)){ //se for vazio, enviar array de objetos json com tamanho 0
                    stringResposta = "[]";
            }
            else{
                for (int i = 0; i < resultados.size(); i++){
                    stringResposta = stringResposta + "{\"patrimonio\":\""+Integer.toString((int)resultados.get(i).getPatrimonio())+"\","
                            + " \"titulo\":\""+resultados.get(i).getTitulo()+"\","
                            + "\"autoria\":\""+resultados.get(i).getAutoria()+"\","
                            + "\"veiculo\":\""+resultados.get(i).getVeiculo()+"\"},";
                }
                stringResposta = stringResposta.substring(0,stringResposta.length()-1);
                stringResposta = stringResposta + "]";
            }
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(stringResposta);
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


