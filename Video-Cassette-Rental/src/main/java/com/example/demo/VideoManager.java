package com.example.demo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.demo.Video.type;

@Service
public class VideoManager {

	private VideoRepo videoRapo;
	
	@Autowired
	public VideoManager(VideoRepo videoRapo) {
		this.videoRapo = videoRapo;
	}
	
	
	
	public Optional<Video> findById(Long id) {
		return videoRapo.findById(id);
	}
	
	public Iterable<Video> findByAll() {
		return videoRapo.findAll();
	}
	
	public Video save(Video video) {
		return videoRapo.save(video);
	}
	
	public void remove(Long id) {
		 videoRapo.deleteById(id);
	}
	
	@EventListener(classes = ApplicationReadyEvent.class)
	public void fillDb() {
		
		save(new Video(1L, "Bond", type.ACTION, LocalDate.of(1995, 12, 7)));
		save(new Video(2L, "Mad Max: Fury Road", type.ADVENTRURE, LocalDate.of(2015, 12, 7)));
		save(new Video(3L, "Black Panther", type.ADVENTRURE, LocalDate.of(2018, 8, 12)));
		save(new Video(4L, "Get Out", type.HORROR, LocalDate.of(2017, 3, 22)));
		save(new Video(5L, "Tytanic", type.ROMANCE, LocalDate.of(2005, 2, 12)));
		save(new Video(6L, "Booksmart", type.COMEDY, LocalDate.of(2019, 3, 7)));
		save(new Video(7L, "Avengers: Endgame", type.ACTION, LocalDate.of(2019, 11, 1)));
		save(new Video(8L, "Man on Wire", type.DOCUMENTARY, LocalDate.of(2008, 5, 2)));
		save(new Video(9L, "Red Dragon", type.THRILLER, LocalDate.of(2002, 10, 9)));
		save(new Video(10L, "The Ballad of Buster Scruggs", type.WESTERN, LocalDate.of(2018, 8, 21)));
		
		
		
	}
}
