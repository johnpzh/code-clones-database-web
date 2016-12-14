package edu.wm.as.cs.codeclones.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.wm.as.cs.codeclones.entities.CodeClone;

public class CloneDao {
	private static CloneDao instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/code_clones";
	
	public static CloneDao getInstance() throws Exception {
		if (instance == null) {
			instance = new CloneDao();
		}
		return instance;
	}
	
	private CloneDao() throws Exception {
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	private Connection getConnection() throws Exception {
		Connection conn = dataSource.getConnection();
		return conn;
	}
	
	private void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public List<CodeClone> getClones() throws Exception {
		List<CodeClone> clones = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from CodeClone";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int cloneID = rs.getInt("cloneID");
//				int projectID1 = rs.getInt("projectID1");
//				int revisionID1 = rs.getInt("revisionID1");
				String project1Name = rs.getString("project1Name");
				String revision1Name = rs.getString("revision1Name");
				String fileName1 = rs.getString("fileName1");
				int startLine1 = rs.getInt("startLine1");
				int endLine1 = rs.getInt("endLine1");
//				int projectID2 = rs.getInt("projectID2");
//				int revisionID2 = rs.getInt("revisionID2");
				String project2Name = rs.getString("project2Name");
				String revision2Name = rs.getString("revision2Name");
				String fileName2 = rs.getString("fileName2");
				int startLine2 = rs.getInt("startLine2");
				int endLine2 = rs.getInt("endLine2");
//				int detectorID = rs.getInt("detectorID");
				String detectorName = rs.getString("detectorName");
				String configuration = rs.getString("configuration");
				
				
				CodeClone tempClone = new CodeClone(cloneID,
													project1Name,
													revision1Name,
													fileName1,
													startLine1,
													endLine1,
													project2Name,
													revision2Name,
													fileName2,
													startLine2,
													endLine2,
													detectorName,
													configuration);
				clones.add(tempClone);
			}
			return clones;
		}
		finally {
			close(conn, stmt, rs);
		}
	}
	
//	public List<CodeClone> getClones(String project1Name,
//										String project2Name,
//										String revision1Name,
//										String revision2Name) throws Exception {
//		String project1Sql;
//		String project2Sql;
//		String revision1Sql;
//		String revision2Sql;
//	}
	
	public CodeClone getCloneByCloneID(int cloneID) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select * from CodeClone where cloneID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cloneID);
			rs = stmt.executeQuery();
			CodeClone theClone = null;
			
			if(rs.next()) {
				String project1Name = rs.getString("project1Name");
				String revision1Name = rs.getString("revision1Name");
				String fileName1 = rs.getString("fileName1");
				int startLine1 = rs.getInt("startLine1");
				int endLine1 = rs.getInt("endLine1");
				String project2Name = rs.getString("project2Name");
				String revision2Name = rs.getString("revision2Name");
				String fileName2 = rs.getString("fileName2");
				int startLine2 = rs.getInt("startLine2");
				int endLine2 = rs.getInt("endLine2");
//				int detectorID = rs.getInt("detectorID");
				String detectorName = rs.getString("detectorName");
				String configuration = rs.getString("configuration"); 
				
				theClone = new CodeClone(cloneID,
											project1Name,
											revision1Name,
											fileName1,
											startLine1,
											endLine1,
											project2Name,
											revision2Name,
											fileName2,
											startLine2,
											endLine2,
											detectorName,
											configuration);
			} else {
				throw new Exception("Could not find code clone id: " + cloneID);
			}
			return theClone;
		} finally {
			close(conn, stmt, rs);
		}
	}
	
	public void deleteCloneByCloneID(int cloneID) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			String sql = "delete from CodeClone where cloneID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cloneID);
			
			stmt.execute();			
		}
		finally {
			close (conn, stmt);
		}
	}
	
	public void addCloneByClone(CodeClone clone) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			String sql = "insert into CodeClone "
						+ "(project1Name, revision1Name, fileName1, startLine1, "
						+ "endLine1, project2Name, revision2Name, fileName2, "
						+ "startLine2, endLine2, detectorName, configuration) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, clone.getProject1Name());
			stmt.setString(2, clone.getRevision1Name());
			stmt.setString(3, clone.getFileName1());
			stmt.setInt(4, clone.getStartLine1());
			stmt.setInt(5, clone.getEndLine1());
			stmt.setString(6, clone.getProject2Name());
			stmt.setString(7, clone.getRevision2Name());
			stmt.setString(8, clone.getFileName2());
			stmt.setInt(9, clone.getStartLine2());
			stmt.setInt(10, clone.getEndLine2());
			stmt.setString(11, clone.getDetectorName());
			stmt.setString(12, clone.getConfiguration());
			stmt.execute();			
		}
		finally {
			close (conn, stmt);
		}
	}
	
	public List<String> getProject1Names() throws Exception {
		List<String> project1Names = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select distinct project1Name from CodeClone";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String project1Name = rs.getString("project1Name");
				project1Names.add(project1Name);
			}
			return project1Names;
		}
		finally {
			close(conn, stmt, rs);
		}
	}
	
	public List<String> getProject2Names() throws Exception {
		List<String> project2Names = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select distinct project2Name from CodeClone";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String project2Name = rs.getString("project2Name");
				project2Names.add(project2Name);
			}
			return project2Names;
		}
		finally {
			close(conn, stmt, rs);
		}
	}
	
	public List<String> getRevision1Names() throws Exception {
		List<String> revision1Names = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select distinct revision1Name from CodeClone";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String revision1Name = rs.getString("revision1Name");
				revision1Names.add(revision1Name);
			}
			return revision1Names;
		}
		finally {
			close(conn, stmt, rs);
		}
	}
	
	public List<String> getRevision2Names() throws Exception {
		List<String> revision2Names = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select distinct revision2Name from CodeClone";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String revision2Name = rs.getString("revision2Name");
				revision2Names.add(revision2Name);
			}
			return revision2Names;
		}
		finally {
			close(conn, stmt, rs);
		}
	}
	
//	public List<CodeClone> getClonesBy2ProjectName()
}
