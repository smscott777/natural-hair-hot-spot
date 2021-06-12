package com.nhhsgroup.naturalhairhotspot.Entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="review")
public class Review {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String title;
	
	private String body;
	
	// json value "product:" "product/{prodNum}"
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_prod_num", nullable = false)
	private Product product;		
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
}
