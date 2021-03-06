/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import modelo.Cliente;
import modelo.Promocao;

/**
 *
 * @author Tiago
 */
public class ClienteCRUD {

    Conexao conexao = new Conexao();

    public void cadastrar(Object o) throws SQLException
    {
        if(o instanceof Cliente)
        {
            Cliente c = (Cliente) o;
            Promocao p= new Promocao();

            PreparedStatement stmt = conexao.con.prepareStatement("SELECT CADASTRA_PROMOCAO(?, ?)");

            stmt.setString(1, p.getDescricao());
            stmt.setDouble(2, p.getCoeficienteDesconto());

            stmt.execute();
            stmt.close();
        } else {
            throw new SQLException("Tipo de objeto inesperado. Tipo esperado: \n " + Cliente.class);
        }
    }


    public void editar(Object o) throws SQLException
    {
        if(o instanceof Cliente)
        {
            Cliente cl = (Cliente) o;
            Promocao p= new Promocao();

            PreparedStatement stmt = conexao.con.prepareStatement("UPDATE promocoes SET descricao,=? coeficiente_desconto=?");

            stmt.setString(1, p.getDescricao());
            stmt.setDouble(2, p.getCoeficienteDesconto());

            stmt.execute();
            stmt.close();
        } else {
            throw new SQLException("Tipo de objeto inesperado. Tipo esperado: \n " + Cliente.class);
        }
    }


    public void excluir(int idPromocao) throws SQLException
    {

        PreparedStatement stmt = conexao.con.prepareStatement("UPDATE promocoes SET ativo='N' WHERE id_promocao = ?");

        stmt.setInt(1, idPromocao);

        stmt.execute();
        stmt.close();
    }


    public List buscar(String q) throws SQLException
    {

        List<Promocao> lista = new ArrayList<Promocao>();
        PreparedStatement stmt = conexao.con.prepareStatement("SELECT * FROM relacao_promocoes WHERE descricao LIKE '%%" + q + "%%' ORDER BY id_promocao DESC ");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Cliente cl = new Cliente();
               Promocao p= new Promocao();

            p.setIdPromocao(rs.getInt("id_promocao"));
            p.setDescricao(rs.getString("descricao"));
            p.setCoeficienteDesconto(rs.getDouble("coeficiente_desconto"));
            p.setDataCadastro(rs.getDate("data_cadastro"));

            lista.add(p);
        }

        stmt.close();

        return lista;
    }



    public List lista() throws SQLException
    {

        List<Promocao> lista = new ArrayList<Promocao>();
        PreparedStatement stmt = conexao.con.prepareStatement(
                "SELECT id_promocao, descricao, coeficiente_desconto, data_cadastro FROM promocoes WHERE ativo='S' ORDER BY id_promocao DESC ");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Cliente cl = new Cliente();
            Promocao p= new Promocao();

//            DecimalFormat df  = new DecimalFormat();
//            df.applyPattern("00.00;(00.00)");
              //JOptionPane.showMessageDialog(null, df.format(rs.getDouble("coeficiente_desconto")), "Locaki ~ A Sua Locadora!", 1);

            p.setIdPromocao(rs.getInt("id_promocao"));
            p.setDescricao(rs.getString("descricao"));
            p.setCoeficienteDesconto(rs.getDouble("coeficiente_desconto"));
            p.setDataCadastro(rs.getDate("data_cadastro"));


            lista.add(p);
        }

        stmt.close();

        return lista;
    }

    public List pesq(String retorno) throws SQLException {

        List<String> lista = new ArrayList<String>();

        PreparedStatement stmt = conexao.con.prepareStatement(
                "SELECT nome FROM clientes " +
                "WHERE nome like '" +retorno+ "%'");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Cliente c = new Cliente();
            String re = rs.getString("nome");
           
            lista.add(re);

        }

        stmt.close();

        return lista;

    }

}
