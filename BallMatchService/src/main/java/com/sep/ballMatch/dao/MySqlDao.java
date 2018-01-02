package com.sep.ballMatch.dao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * 
 * @author xiaotao
 *
 */
public class MySqlDao{
	
	private JdbcTemplate jdbcTemplate;
	
	
	public MySqlDao(){
		ApplicationContext ct = new ClassPathXmlApplicationContext("spring/ApplicationContext.xml");
		jdbcTemplate = (JdbcTemplate) ct.getBean("jdbcTemplate");
	}
	
	public int executeQueryForInt(String sql) throws ClassNotFoundException, SQLException{
		try{
			return jdbcTemplate.queryForInt(sql);
		}catch( EmptyResultDataAccessException e ){
			return 0;
		}
	}

	public int executeQueryForInt(String sql, int[] types, Object[] values)throws ClassNotFoundException, SQLException {
		try{
			return jdbcTemplate.queryForInt(sql, values, types);
		}catch( EmptyResultDataAccessException e ){
			return 0;
		}
	}

	public List<Map<String, Object>> executeQueryForList(String sql)throws ClassNotFoundException, SQLException {
		try{
			return jdbcTemplate.queryForList(sql);
		}catch( EmptyResultDataAccessException e ){
			return new ArrayList<Map<String, Object>>();
		}catch( CannotGetJdbcConnectionException e ){
			return new ArrayList<Map<String, Object>>();
		}
	}
	

	public List<Map<String, Object>> executeQueryForList(String sql,int[] types, Object[] values) throws ClassNotFoundException,SQLException {
		try{
			return  jdbcTemplate.queryForList(sql, values, types);
		}catch( EmptyResultDataAccessException e ){
			return new ArrayList<Map<String, Object>>();
		}
	}

	public Map<String, Object> executeQueryForMap(String sql)throws ClassNotFoundException, SQLException {
		try{
			return jdbcTemplate.queryForMap(sql);
		}catch( EmptyResultDataAccessException e ){
			return null;
		}
	}

	public Map<String, Object> executeQueryForMap(String sql, int[] types,Object[] values) throws ClassNotFoundException, SQLException {
		try{
			return jdbcTemplate.queryForMap(sql, values, types);
		}catch( EmptyResultDataAccessException e ){
			return null;
		}
	}

	public int executeUpdate(String sql) throws ClassNotFoundException,SQLException {
		return jdbcTemplate.update(sql);
	}

	public int executeUpdate(String sql, int[] types, Object[] values)throws ClassNotFoundException, SQLException, FileNotFoundException,IOException {
		return jdbcTemplate.update(sql, values, types);
	}

	public long executeUpdateGetKey(final String sql , final int[] types,final Object[] values) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				
                PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                
                for (int i = 0; i < values.length; i++) {
                	switch (types[i]) {
					case Types.VARCHAR:
						 ps.setString((i+1), (String)values[i]);
						break;
					case Types.INTEGER:
						 ps.setInt((i+1), (Integer)values[i]);
						break;
					case Types.TIMESTAMP:
						 ps.setTimestamp((i+1), (Timestamp)values[i]);
						break;
					case Types.DATE:
						 ps.setDate((i+1), (Date)values[i]);
						break;
					case Types.DOUBLE:
						 ps.setDouble((i+1), (Double)values[i]);
						break;
					default:
						ps.setString((i+1), (String)values[i]);
						break;
					}
				}
                return ps;
			}
		}, keyHolder);
		
		return keyHolder.getKey().longValue(); 
	}
	
	public List<?> executeQueryForList(String sql, Class<?> cla)throws ClassNotFoundException, SQLException {
		try{
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper( cla ) );
		}catch( EmptyResultDataAccessException e ){
			return new ArrayList<Map<String, Object>>();
		}
	}
	public List<?> executeQueryForList(String sql, int[] types,Object[] values, Class<?> cla) throws ClassNotFoundException,SQLException {
		try{
			return jdbcTemplate.query(sql, values, types, new BeanPropertyRowMapper( cla ) );
		}catch( EmptyResultDataAccessException e ){
			return new ArrayList<Map<String, Object>>();
		}
	}
	public Object executeQueryForObject(String sql, Class<?> cla)throws ClassNotFoundException, SQLException {
		try{
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper( cla ) );
		}catch( EmptyResultDataAccessException e ){
			return null;
		}
	}

	public Object executeQueryForObject(String sql, int[] types,Object[] values, Class<?> cla) throws ClassNotFoundException,SQLException {
		try{
			return jdbcTemplate.queryForObject(sql, values, types, new BeanPropertyRowMapper( cla ) );
		}catch( EmptyResultDataAccessException e ){
			return null;
		}
	}
	
}