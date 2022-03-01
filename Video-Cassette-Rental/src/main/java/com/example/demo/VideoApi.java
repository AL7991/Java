package com.example.demo;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/video")
public class VideoApi {
	
	private VideoManager videoManager;
	
	@Autowired
	public VideoApi(VideoManager videoManager) {
		this.videoManager = videoManager;
	}

	@GetMapping("/all")
	public Iterable<Video> getAll() {
		return videoManager.findByAll();
	}
	
	@GetMapping
	public  Optional<Video> getById(@RequestParam Long index) {
		return videoManager.findById(index);
		
	}
	
	@PostMapping()
	public Video addVideo(@RequestBody Video video) {
		return videoManager.save(video);
	}
	
	@PutMapping
	public Video changeVideo(@RequestBody Video video) {
		return videoManager.save(video);
	}
	
	@DeleteMapping
	public void removeVideo(@RequestParam Long index) {
		 videoManager.remove(index);
	}
	
}
