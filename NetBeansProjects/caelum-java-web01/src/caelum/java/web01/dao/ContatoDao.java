/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caelum.java.web01.dao;

import caelum.java.web01.ConnectionFactory;
import caelum.java.web01.model.Contato;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author proto
 */
public class ContatoDao {
    
    private Connection connection;
    
    public ContatoDao() {
    
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Contato contato) {
        
        String sql = "INSERT INTO contatos " + 
                "(nome, email, endereco, dataNascimento)" +
                " VALUES (?, ?, ?, ?)";
        try {
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getEndereco());
            stmt.setDate(4, new Date(
                contato.getDataNascimento().getTimeInMillis()));
            stmt.execute();
            stmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Contato> getLista() {
        
        try {
        
            List<Contato> contatos = new ArrayList<Contato>();
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM contatos");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getLong("id"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setEndereco(rs.getString("endereco"));
                Calendar data = Calendar.getInstance();
                data.setTime(rs.getDate("dataNascimento"));
                contato.setDataNascimento(data);
                contatos.add(contato);
            }
            rs.close();
            stmt.close();
            return contatos;
            
        } catch (SQLException e) {
            throw new RuntimeException (e);
        }
    }
}
