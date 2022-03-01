package com.example.demo;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	@Enumerated(EnumType.STRING)
	private type typ;
	
	private LocalDate createdAt;
	
	
	 public Video() {
		}
	
	
	 public Video(Long id, String title, type typ, LocalDate createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.typ = typ;
		this.createdAt = createdAt;
	}
	 

	 public enum type {
		HORROR,ROMANCE,ACTION,ADVENTRURE,THRILLER,WESTERN,DOCUMENTARY,COMEDY
	 }

	 
	 
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public type getTyp() {
		return typ;
	}



	public void setTyp(type typ) {
		this.typ = typ;
	}



	public LocalDate getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}




}