package com.example.ziadbekhiet.myandroidproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents an album in our project. An instance of an album contains a list of all the
 * photos in that album, and stores properties about that album.
 * 
 * @author Mike Allen
 * @author Ziad Bekhiet
 *
 */
public class AlbumObject implements Serializable {

	/**
	 * The name of the album.
	 */
	private String albumName;
	
	/**
	 * A list of all the photos in the album.
	 */
	private ArrayList<Photo> photos;
	
	/**
	 * Creates a new instance of an album.
	 */
	public AlbumObject(String albumname) {
		
		this.photos = new ArrayList<Photo>();
		this.albumName = albumname;
	}
	
	/**
	 * Returns the name of the album
	 * 
	 * @return the name of the album
	 */
	public String getAlbumName() {
		
		return this.albumName;
	}
	
	/**
	 * Returns the list of photos in the album
	 * 
	 * @return the list of photos in the album
	 */
	public ArrayList<Photo> getPhotos() {
		
		return this.photos;
	}
	
	/**
	 * Sets the name of album to a new album name.
	 * 
	 * @param albumName the new name of the album
	 */
	public void setAlbumName(String albumName) {
		
		this.albumName = albumName;
	}

	/**
	 * Returns the size of the album
	 * 
	 * @return the size of the album
	 */
	public int getAlbumSize() {

		return photos.size();
	}

	/**
	 * Renames the album
	 * 
	 * @param x the new name of the album
	 */
	public void renameAlbum(String x) {

		this.albumName = x;

	}
	
	/**
	 * Sets the entire photos list to a new list
	 * 
	 * @param photos the list of photos in the album
	 */
	public void setPhotos(ArrayList<Photo> photos) {
		
		this.photos = photos;
	}
	
	/**
	 * Adds a photo to the list of photos in the album
	 * 
	 * @param photo the new photo to be added
	 */
	public void addPhoto(Photo photo) {
		
		if(photos == null) {
			
			photos = new ArrayList<Photo>();
		}
		
		// Scan through the photos in the album
		for(int i = 0; i < photos.size(); i++) {
			
			// Check to see if there are any other photos with the same name
			if(photos.get(i).getPhotoName().equalsIgnoreCase(photo.getPhotoName())) {
				
				// TODO: Add error checks to the GUI.
				System.out.println("ERROR: There is already a photo with this name.");
				
				return;
			}
		}
		
		// If there are no photos of the same name, add the photo to the album
		photos.add(photo);
	}
	
	/**
	 * Removes a photo with a given photoName
	 * 
	 * @param photoName the name of the photo to be removed
	 */
	public void removePhoto(String photoName) {
		
		if(photos.size() == 0) {
			
			// TODO: Add error checks to GUI
			System.out.println("ERROR: No photos in album.");
		}
		
		for(int i = 0; i < photos.size(); i++) {
			
			// if the photo name matches the photo that they want to remove
			// TODO: Photos can be of the same type, but have the same name. Add check for that.
			if(photos.get(i).getPhotoName().equalsIgnoreCase(photoName))
			{
				// remove the photo
				photos.remove(i);
				
				return;
			}
		}
		
		// TODO: Add error checks to GUI
		System.out.println("ERROR: Photo not found in album + " + this.albumName);
	}

	/**
	 * Sorts the albums by their date
	 */
	public void sortAlbumByDate() {
		if (this.getAlbumSize() == 0 || this.getAlbumSize() == 1) {
			return;
		}
		Collections.sort(photos, new Comparator<Photo>() {
			@Override
			public int compare(Photo p1, Photo p2) {
				return p1.getDate().compareTo(p2.getDate());
			}
		});
	}
}
