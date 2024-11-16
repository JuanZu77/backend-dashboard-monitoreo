package com.juan_zubiri.monitoreo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "register_user")
public class RegisterUser implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_user")
	private Long id;
	
	@Column(name = "register_date", nullable = false)
	private LocalDateTime registerDate;
	
	 @Column(name = "ip_register", length = 45)
	private String ipRegister;
	 
	 @OneToOne
	 @JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
	 private User user;
	

}
