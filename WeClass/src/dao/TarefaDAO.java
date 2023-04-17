/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Tarefa;

public class TarefaDAO {

    private ConnectionFactory factory;
    private Connection conn;

    
    
    public TarefaDAO() {
        factory = new ConnectionFactory();
        conn = factory.getConnection();
    }

    public void inserirTarefa(Tarefa tarefa) throws SQLException {
        String sql = "INSERT INTO `weclass`.`tarefa` (`nomeTarefa`, `desc`, `nota`, `data_inicio`, `data_fim`, `Turma_idTurma`) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tarefa.getNomeTarefa());
            statement.setString(2, tarefa.getDescricao());
            statement.setFloat(3, tarefa.getNota());
            statement.setDate(4, (Date) tarefa.getDataInicio());
            statement.setDate(5, (Date) tarefa.getDataFim());
            statement.setInt(6, tarefa.getIdTurma());
            statement.execute();
            statement.close();           
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao adicionar tarefa "+ e.getMessage());
        }
        

    }

    public void atualizarTarefa(Tarefa tarefa) throws SQLException {
        String sql = "UPDATE tarefa SET nomeTarefa = ?, desc = ?, nota = ?, "
                     + "data_inicio = ?, data_fim = ?, Turma_idTurma = ? WHERE idTarefa = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tarefa.getNomeTarefa());
            statement.setString(2, tarefa.getDescricao());
            statement.setInt(3, tarefa.getNota());
            statement.setDate(4, (Date) tarefa.getDataInicio());
            statement.setDate(5, (Date) tarefa.getDataFim());
            statement.setInt(6, tarefa.getIdTurma());
            statement.setInt(7, tarefa.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao Atualizar Tarefa "+ e.getMessage());
        }
        
    }

    public void excluirTarefa(Tarefa tarefa) throws SQLException {
        String sql = "DELETE FROM tarefa WHERE idTarefa = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, tarefa.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao deletar tarefa"+ e);
        }
        
    }

    public Tarefa buscarTarefa(int id) throws SQLException {
        Tarefa tarefa = null;
        String sql = "SELECT * FROM tarefa WHERE idTarefa = ?";


        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String nomeTarefa = resultSet.getString("nomeTarefa");
            String descricao = resultSet.getString("desc");
            int nota = resultSet.getInt("nota");
            Date dataInicio = resultSet.getDate("data_inicio");
            Date dataFim = resultSet.getDate("data_fim");
            int idTurma = resultSet.getInt  ("Turma_idTurma");

        tarefa = new Tarefa(id, nomeTarefa, descricao, nota, dataInicio, dataFim, idTurma);
    }

    resultSet.close();
    statement.close();

    return tarefa;
}

public List<Tarefa> listarTarefas() throws SQLException {
    List<Tarefa> listaTarefas = new ArrayList<>();

    String sql = "SELECT * FROM tarefa";


    Statement statement = conn.createStatement();
    ResultSet resultSet = statement.executeQuery(sql);

    while (resultSet.next()) {
        int id = resultSet.getInt("idTarefa");
        String nomeTarefa = resultSet.getString("nomeTarefa");
        String descricao = resultSet.getString("desc");
        int nota = resultSet.getInt("nota");
        Date dataInicio = resultSet.getDate("data_inicio");
        Date dataFim = resultSet.getDate("data_fim");
        int idTurma = resultSet.getInt("Turma_idTurma");

        Tarefa tarefa = new Tarefa(id, nomeTarefa, descricao, nota, dataInicio, dataFim, idTurma);
        listaTarefas.add(tarefa);
    }

    resultSet.close();
    statement.close();


    return listaTarefas;
}
}