package com.nhhsgroup.naturalhairhotspot.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn(name="category_id", nullable=false)
	private Category category;


}
