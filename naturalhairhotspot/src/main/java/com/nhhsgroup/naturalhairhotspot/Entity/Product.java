package com.nhhsgroup.naturalhairhotspot.Entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Table(name="product")
@Entity
public class Product {

	@Id
	@Column(name="prod_num")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int prodNum;
	
	private String name;
	
	@Column(name="image_url")
	private String imageUrl;
	
	private String ingredients;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="category_id", nullable=false)
	private Category category;	
/*
	@ManyToOne
	@JoinColumn(name="email", nullable=false)
	private User user;
*/	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="product")
	private Set<Review> reviews;	

}
