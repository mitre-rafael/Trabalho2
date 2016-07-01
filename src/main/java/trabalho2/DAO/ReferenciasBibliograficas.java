package trabalho2.DAO;

import trabalho2.DTOs.ReferenciaBibliografica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import trabalho2.DTOs.RespostaCompletaDTO;
import trabalho2.utils.*;

public class ReferenciasBibliograficas extends BaseDAO{

 

    //----------------------------------------------------------------------------------
    //MÉTODOS DE INSERÇÃO!
    public boolean incluirReferencia (ReferenciaBibliografica dto){
        int novoPatrimonio;
        
        //incluir na tabela normal
        novoPatrimonio = (new ReferenciasBibliograficas()).incluirPalavraDadosCatalogo(dto);
        
        if (novoPatrimonio == 0) {
        System.out.println("ERRO AO ADICIONAR NA TABELA dadoscatalogo");
        return false;        
        }
        
        //incluir na tabela palavras chave
/*        if (!(new ReferenciasBibliograficas()).incluirPalavraChave(dto.getPalChave(), novoPatrimonio)){
        System.out.println("ERRO AO ADICIONAR NA TABELA palavras_chave");
        return false;
        }*/
        
        //incluir na tabela autoria
        if (!(new ReferenciasBibliograficas()).incluirPalavraTabelaAutoria(dto.getAutoria(), novoPatrimonio)){
        System.out.println("ERRO AO ADICIONAR NA TABELA palavrasautorianormal");
        return false;
        }
        
        //incluir na tabela titulos
        if (!(new ReferenciasBibliograficas()).incluirPalavraTabelaTitulos(dto.getTitulo(), novoPatrimonio)){
        System.out.println("ERRO AO ADICIONAR NA TABELA palavrastitulonormal");
        return false;
        }     
       
        //incluir na tabela veiculo
        if (!(new ReferenciasBibliograficas()).incluirPalavraTabelaVeiculo(dto.getVeiculo(), novoPatrimonio)){
        System.out.println("ERRO AO ADICIONAR NA TABELA palavrasveiculonormal");
        return false;
        }        
        return true;
    }
    
    private int incluirPalavraDadosCatalogo(ReferenciaBibliografica dto){
        
        ResultSet rst = null;
        ReferenciaBibliografica ref = new ReferenciaBibliografica();  
        int patrimonio;
        
        try(Connection con = getConnection()){
            
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(
               "INSERT INTO dadoscatalogo (titulo,autoria,veiculo) VALUES(?,?,?)");
            pstmt.setString(1, dto.getTitulo());
            pstmt.setString(2, dto.getAutoria());
            pstmt.setString(3, dto.getVeiculo());
            //pstmt.setDate(4, dto.getDataPublicacao());
            /*pstmt.setString(5, dto.getDataPublicacao());
            pstmt.setString(6, dto.getDataPublicacao());*/
            
            pstmt.executeUpdate();
                                  
            pstmt = con.prepareStatement ("SELECT patrimonio FROM dadoscatalogo WHERE titulo=(?) AND autoria=(?) AND veiculo=(?) ");
            pstmt.setString(1,dto.getTitulo());
            pstmt.setString(2,dto.getAutoria());
            pstmt.setString(3,dto.getVeiculo());
            rst = pstmt.executeQuery();
            rst.next();
            patrimonio = rst.getInt("patrimonio");
            
            con.commit();
            con.close();
        } catch (Exception e) 
            {
            e.printStackTrace();
            return 0;
            }
        return patrimonio;
    }
     
    private boolean incluirPalavraTabelaAutoria(String autoria, int patrimonio){
        ResultSet rst = null;
        ReferenciaBibliografica ref = new ReferenciaBibliografica();  
        String [] autoriaSeparada = autoria.split(" ");
        
        
        try(Connection con = getConnection()){
        
            con.setAutoCommit(false);
            for (int j = 0; j< autoriaSeparada.length; j++){
            
            PreparedStatement pstmt = con.prepareStatement(
               "INSERT INTO palavrasautorianormal (palavra_autoria_normal,patrimonio) VALUES(?,?)");
            
            pstmt.setString(1, (new Utils()).removeDiacriticals(autoriaSeparada[j]));
            pstmt.setInt(2, patrimonio);
                         
            pstmt.executeUpdate();
            }                          
            con.commit();
            con.close();
        } catch (Exception e) 
            {
            e.printStackTrace();
            return false;
            }
        return true;
    }
   
    private boolean incluirPalavraTabelaTitulos(String titulo, int patrimonio){
        ResultSet rst = null;
        ReferenciaBibliografica ref = new ReferenciaBibliografica();  
        String [] tituloSeparado = titulo.split(" ");
        
        
        try(Connection con = getConnection()){
        
            con.setAutoCommit(false);
            for (int j = 0; j< tituloSeparado.length; j++){
            
            PreparedStatement pstmt = con.prepareStatement(
               "INSERT INTO palavrastitulonormal (palavra_titulo_normal,patrimonio) VALUES(?,?)");
            
            pstmt.setString(1, (new Utils()).removeDiacriticals(tituloSeparado[j]));
            pstmt.setInt(2, patrimonio);
                         
            pstmt.executeUpdate();
            }                          
            con.commit();
            con.close();
        } catch (Exception e) 
            {
            e.printStackTrace();
            return false;
            }
        return true;
    }
    
    private boolean incluirPalavraTabelaVeiculo(String veiculo, int patrimonio){
        ResultSet rst = null;
        ReferenciaBibliografica ref = new ReferenciaBibliografica();  
        String [] veiculoSeparado = veiculo.split(" ");
        
        
        try(Connection con = getConnection()){
        
            con.setAutoCommit(false);
            for (int j = 0; j< veiculoSeparado.length; j++){
            
            PreparedStatement pstmt = con.prepareStatement(
               "INSERT INTO palavrasveiculonormal (palavra_veiculo_normal,patrimonio) VALUES(?,?)");
            
            pstmt.setString(1, (new Utils()).removeDiacriticals(veiculoSeparado[j]));
            pstmt.setInt(2, patrimonio);
                         
            pstmt.executeUpdate();
            }                          
            con.commit();
            con.close();
        } catch (Exception e) 
            {
            e.printStackTrace();
            return false;
            }
        return true;
    }
//------------------------------------------------------------------------------
    public ArrayList<ReferenciaBibliografica>apresentarTudo () 
        {
        ArrayList<ReferenciaBibliografica> lista = new ArrayList<>();
        ResultSet rst = null;
        ReferenciaBibliografica ref = null;
        

        try(Connection con = getConnection())
            {
            PreparedStatement comandoSQL = con.prepareStatement(
            "(SELECT patrimonio, titulo, autoria, veiculo FROM dadoscatalogo)");
            
            rst = comandoSQL.executeQuery();
            
            while (rst.next())
                    {
                    
                    ref = new ReferenciaBibliografica();
                    ref.setPatrimonio(rst.getLong("patrimonio"));
                    ref.setTitulo(rst.getString("titulo"));
                    ref.setAutoria(rst.getString("autoria"));
                    ref.setVeiculo(rst.getString("veiculo"));
                    //ref.setDataPublicacao(rst.getString("data_publicacao"));
                    //ref.setPalChave(rst.getString("palchave"));
                    
                    lista.add(ref);
                    }
            
            con.close();
            }
         catch (Exception e)
            {
            e.printStackTrace();
            }   
        return lista;
        
        }
//------------------------------------------------------------------------------
//MÉTODOS DE BUSCA
    public ArrayList<ReferenciaBibliografica> buscarListaPorPalavraDoTitulo(ReferenciaBibliografica dados){
        ArrayList<ReferenciaBibliografica> lista = new ArrayList<>();
        ArrayList<Long> patrimonios = new ArrayList<>();
        ReferenciaBibliografica ref;
        boolean vazio = true;
        
        
        String[] palavrasDaBusca = extrairPalavrasDaBusca(dados);
        String preparedStatement = prepararComandoSQL(palavrasDaBusca);
        
        String[] palavrasDaBuscaAutoria = extrairPalavrasDaBuscaAutoria(dados);
        String preparedStatement2 = prepararComandoSQLAutoria(palavrasDaBuscaAutoria);
        
        String[] palavrasDaBuscaVeiculo = extrairPalavrasDaBuscaVeiculo(dados);
        String preparedStatement3 = prepararComandoSQLVeiculo(palavrasDaBuscaVeiculo);
        
        try(Connection conexao = getConnection()){
            PreparedStatement comandoSQL = conexao.prepareStatement(preparedStatement);
            
            for(int i=0;i<palavrasDaBusca.length;i++){
                comandoSQL.setString(i+1, palavrasDaBusca[i]);
            }
            
            PreparedStatement comandoSQL2 = conexao.prepareStatement(preparedStatement2);
            
            for(int i=0;i<palavrasDaBuscaAutoria.length;i++){
                comandoSQL2.setString(i+1, palavrasDaBuscaAutoria[i]);
            }
            
            PreparedStatement comandoSQL3 = conexao.prepareStatement(preparedStatement3);
           
            for(int i=0;i<palavrasDaBuscaVeiculo.length;i++){
                comandoSQL3.setString(i+1, palavrasDaBuscaVeiculo[i]);
            }
            
            
        
            ResultSet rs = comandoSQL.executeQuery();
            ResultSet rs2 = comandoSQL2.executeQuery();
            ResultSet rs3 = comandoSQL3.executeQuery();
            
            while(rs.next()){
                ref = new ReferenciaBibliografica();
                if(!patrimonios.contains(rs.getLong("patrimonio"))){
                ref.setPatrimonio(rs.getLong("patrimonio"));
                patrimonios.add(ref.getPatrimonio());
                ref.setTitulo(rs.getString("titulo"));
                ref.setAutoria(rs.getString("autoria"));
                ref.setVeiculo(rs.getString("veiculo"));
                lista.add(ref);}
                
                vazio = false;
            }
            while(rs2.next()){
                ref = new ReferenciaBibliografica();
                if(!patrimonios.contains(rs2.getLong("patrimonio"))){
                ref.setPatrimonio(rs2.getLong("patrimonio"));
                patrimonios.add(ref.getPatrimonio());
                ref.setTitulo(rs2.getString("titulo"));
                ref.setAutoria(rs2.getString("autoria"));
                ref.setVeiculo(rs2.getString("veiculo"));
                lista.add(ref);}
                
                vazio = false;
            }
            while(rs3.next()){
                ref = new ReferenciaBibliografica();
                if(!patrimonios.contains(rs3.getLong("patrimonio"))){
                ref.setPatrimonio(rs3.getLong("patrimonio"));
                patrimonios.add(ref.getPatrimonio());
                ref.setTitulo(rs3.getString("titulo"));
                ref.setAutoria(rs3.getString("autoria"));
                ref.setVeiculo(rs3.getString("veiculo"));
                lista.add(ref);}
                
                vazio = false;
            }
        conexao.close();   
        }catch(Exception e){
            e.printStackTrace();
        }
        if (vazio) lista = null;
        return lista;
    }
    
    private String[] extrairPalavrasDaBusca(ReferenciaBibliografica dados){
        String busca = dados.getTitulo();
        busca = Utils.removeDiacriticals(busca);
        String[] temp = busca.split(" ");
        for(int i=0;i<temp.length;i++){
            temp[i] = temp[i].trim();
        }
        return temp;    
    }
    
    private String prepararComandoSQL(String[] palavrasDaBusca){
/*
Exemplo:
        
SELECT T1.patrimonio, T1.titulo, T1.autoria, (count(*)) AS nrohits
FROM dadoscatalogo T1
INNER JOIN palavrastitulonormal T2 ON(T1.patrimonio = T2.patrimonio) WHERE

T2.palavra_titulo_normal LIKE 'MAVEN'        
OR        
T2.palavra_titulo_normal LIKE 'STYLES'
        
GROUP BY T1.patrimonio, T1.titulo, T1.autoria ORDER BY nrohits DESC, titulo ASC;        
*/
        String inicioSelectExterno = 
        "SELECT T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, (count(*)) AS nrohits \n" +
        "FROM dadoscatalogo T1 \n" +
        "INNER JOIN palavrastitulonormal T2 ON(T1.patrimonio = T2.patrimonio) WHERE \n";
        
        String finalSelectExterno = 
        "GROUP BY T1.patrimonio, T1.titulo, T1.autoria,T1.veiculo ORDER BY nrohits DESC, titulo ASC;";
        
        String baseComando = "T2.palavra_titulo_normal LIKE ? \n";
        
        String comando = "";
        for(int i=0;i<palavrasDaBusca.length;i++){
            comando = comando + baseComando;
            if(i<(palavrasDaBusca.length-1)){
                comando = comando + "OR \n";
            }
        }
        comando = inicioSelectExterno + comando + finalSelectExterno;
        return comando;
    }
    
    public ReferenciaBibliografica buscarPorPatrimonio (long patrimonioBuscado){
        ReferenciaBibliografica resultado = new ReferenciaBibliografica();// array pra armazenar resultado da busca
        
        try (Connection con = getConnection())
        {
                
            PreparedStatement comandoSQL = con.prepareStatement(
            "SELECT patrimonio, titulo, autoria, veiculo FROM dadoscatalogo "
                    + "WHERE patrimonio = (?)");
                    
            comandoSQL.setLong(1, patrimonioBuscado); 
            ResultSet rs = comandoSQL.executeQuery();
            
                while (rs.next())
                    {
                    resultado.setPatrimonio(patrimonioBuscado);
                    resultado.setTitulo(rs.getString("titulo"));
                    resultado.setAutoria(rs.getString("autoria"));
                    resultado.setVeiculo(rs.getString("veiculo"));
                    }
         
         con.close();   
        }
            
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return resultado;
    
    }
   
    private String[] extrairPalavrasDaBuscaAutoria(ReferenciaBibliografica dados){
        String busca = dados.getAutoria();
        busca = Utils.removeDiacriticals(busca);
        String[] temp = busca.split(" ");
        for(int i=0;i<temp.length;i++){
            temp[i] = temp[i].trim();
        }
        return temp;    
    }
    
    private String prepararComandoSQLAutoria(String[] palavrasDaBusca){

        String inicioSelectExterno = 
        "SELECT T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, (count(*)) AS nrohits \n" +
        "FROM dadoscatalogo T1 \n" +
        "INNER JOIN palavrasautorianormal T2 ON(T1.patrimonio = T2.patrimonio) WHERE \n";
        
        String finalSelectExterno = 
        "GROUP BY T1.patrimonio, T1.titulo, T1.autoria,T1.veiculo ORDER BY nrohits DESC, titulo ASC;";
        
        String baseComando = "T2.palavra_autoria_normal LIKE ? \n";
        
        String comando = "";
        for(int i=0;i<palavrasDaBusca.length;i++){
            comando = comando + baseComando;
            if(i<(palavrasDaBusca.length-1)){
                comando = comando + "OR \n";
            }
        }
        comando = inicioSelectExterno + comando + finalSelectExterno;
        return comando;
    }
    
    private String[] extrairPalavrasDaBuscaVeiculo(ReferenciaBibliografica dados){
        String busca = dados.getVeiculo();
        busca = Utils.removeDiacriticals(busca);
        String[] temp = busca.split(" ");
        for(int i=0;i<temp.length;i++){
            temp[i] = temp[i].trim();
        }
        return temp;    
    }
    
    private String prepararComandoSQLVeiculo(String[] palavrasDaBusca){

        String inicioSelectExterno = 
        "SELECT T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, (count(*)) AS nrohits \n" +
        "FROM dadoscatalogo T1 \n" +
        "INNER JOIN palavrasveiculonormal T2 ON(T1.patrimonio = T2.patrimonio) WHERE \n";
        
        String finalSelectExterno = 
        "GROUP BY T1.patrimonio, T1.titulo, T1.autoria,T1.veiculo ORDER BY nrohits DESC, titulo ASC;";
        
        String baseComando = "T2.palavra_veiculo_normal LIKE ? \n";
        
        String comando = "";
        for(int i=0;i<palavrasDaBusca.length;i++){
            comando = comando + baseComando;
            if(i<(palavrasDaBusca.length-1)){
                comando = comando + "OR \n";
            }
        }
        comando = inicioSelectExterno + comando + finalSelectExterno;
        return comando;
    }
//----------------------------------------------------------------------------------------
//MÉTODO PARA EXCLUSÃO:
    public boolean excluirReferencia(long patrimonio){
        ResultSet rst = null;
        ReferenciaBibliografica ref = new ReferenciaBibliografica();  
        
        try(Connection con = getConnection()){
            
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(
               "DELETE FROM dadoscatalogo WHERE patrimonio=(?)");
            pstmt.setLong(1, patrimonio);
            pstmt.executeUpdate();
            
            con.commit();
            con.close();
        } catch (Exception e) 
            {
            e.printStackTrace();
            return false;
            }
        return true;
    }
//------------------------------------------------------------------------------
//MÉTODO PARA EDIÇÃO:
    public boolean editarReferencia(ReferenciaBibliografica dto){
      
        if (!(new ReferenciasBibliograficas()).editarTabelaDadosCatalogo(dto)){
        System.out.println("ERRO AO EDITAR  TABELA dadoscatalogo");
        return false;
        }
        
        if (!(new ReferenciasBibliograficas()).editarTabelaAutoria(dto)){
        System.out.println("ERRO AO EDITAR NA TABELA palavrasautorianormal");
        return false;
        }
        
        //incluir na tabela titulos
        if (!(new ReferenciasBibliograficas()).editarTabelaTitulo(dto)){
        System.out.println("ERRO AO EDITAR NA TABELA palavrastitulonormal");
        return false;
        }     
       
        //incluir na tabela veiculo
        if (!(new ReferenciasBibliograficas()).editarTabelaVeiculo(dto)){
        System.out.println("ERRO AO EDITAR NA TABELA palavrasveiculonormal");
        return false;
        }  
        
        return true;
    }
    
    private boolean editarTabelaDadosCatalogo(ReferenciaBibliografica dto){
        try(Connection con = getConnection()){
            
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(
               "UPDATE dadoscatalogo SET titulo=(?),autoria=(?),veiculo=(?) WHERE patrimonio=(?)");
            pstmt.setString(1, dto.getTitulo());
            pstmt.setString(2, dto.getAutoria());
            pstmt.setString(3, dto.getVeiculo());
            pstmt.setLong(4,dto.getPatrimonio());
            
            pstmt.executeUpdate();

            con.commit();
            con.close();
        } catch (Exception e) 
            {
            e.printStackTrace();
            return false;
            }
        return true;
    }
    
    private boolean editarTabelaAutoria(ReferenciaBibliografica dto){ResultSet rst = null;
        
      
        String [] autoriaSeparada = dto.getAutoria().split(" ");
        
        
        try(Connection con = getConnection()){
        
            con.setAutoCommit(false);
            for (int j = 0; j< autoriaSeparada.length; j++){
            
            PreparedStatement pstmt = con.prepareStatement(
               "UPDATE palavrasautorianormal SET palavra_autoria_normal=(?) WHERE patrimonio=(?)");
            
            pstmt.setString(1, (new Utils()).removeDiacriticals(autoriaSeparada[j]));
            pstmt.setLong(2, dto.getPatrimonio());
                         
            pstmt.executeUpdate();
            }                          
            con.commit();
            con.close();
        } catch (Exception e) 
            {
            e.printStackTrace();
            return false;
            }
        return true;
    }
    
    private boolean editarTabelaTitulo(ReferenciaBibliografica dto){
     
        String [] tituloSeparado = dto.getTitulo().split(" ");
        
        
        try(Connection con = getConnection()){
        
            con.setAutoCommit(false);
            for (int j = 0; j< tituloSeparado.length; j++){
            
            PreparedStatement pstmt = con.prepareStatement(
               "UPDATE palavrastitulonormal SET palavra_titulo_normal=(?) WHERE patrimonio=(?)");
            
            pstmt.setString(1, (new Utils()).removeDiacriticals(tituloSeparado[j]));
            pstmt.setLong(2, dto.getPatrimonio());
                         
            pstmt.executeUpdate();
            }                          
            con.commit();
            con.close();
        } catch (Exception e) 
            {
            e.printStackTrace();
            return false;
            }
        return true;
    }
    
    private boolean editarTabelaVeiculo(ReferenciaBibliografica dto){
        String [] veiculoSeparado = dto.getVeiculo().split(" ");
        
        try(Connection con = getConnection()){
        
            con.setAutoCommit(false);
            for (int j = 0; j< veiculoSeparado.length; j++){
            
            PreparedStatement pstmt = con.prepareStatement(
               "UPDATE palavrasveiculonormal SET palavra_veiculo_normal=(?) WHERE patrimonio=(?)");
            
            pstmt.setString(1, (new Utils()).removeDiacriticals(veiculoSeparado[j]));
            pstmt.setLong(2, dto.getPatrimonio());
                         
            pstmt.executeUpdate();
            }                          
            con.commit();
            con.close();
        } catch (Exception e) 
            {
            e.printStackTrace();
            return false;
            }
        return true;
    }
}
                

        