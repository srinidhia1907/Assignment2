package com.goldman.projects.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MysqlConfig {

	@Bean(destroyMethod = "close")
	@Qualifier("dataSource")
	public DataSource dataSource() throws SQLException {

		HikariConfig config = new HikariConfig();
		config.setPassword("Chotu@123");
		config.setUsername("root");
		config.setJdbcUrl("jdbc:mysql://localhost:7000/database1?useSSL=false&serverTimezone=UTC");
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setMaximumPoolSize(10);
		Properties props = new Properties();
		props.setProperty("cachePrepStmts", "true");
		props.setProperty("allowPublicKeyRetrieval", "true");
		props.setProperty("prepStmtCacheSize", "250");
		props.setProperty("prepStmtCacheSqlLimit", "2048");
		config.setDataSourceProperties(props);
		return new HikariDataSource(config);
	}

	@Bean
	@Qualifier("JdbcTemplate")
	JdbcTemplate myprojectJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	@Qualifier("NamedParameterJdbcTemplate")
	NamedParameterJdbcTemplate myprojectNamedParameterJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
