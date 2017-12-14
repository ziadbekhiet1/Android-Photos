package com.example.ziadbekhiet.myandroidproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a photo, which contains numerous properties
 * to store and identify an individual photo.
 *
 * @author Mike Allen
 * @author Ziad Bekhiet
 */
public class Photo implements Serializable {

	/**
	 * The actual photo stored in a file
	 */
	private transient Bitmap photo;

	private String location;

	/**
	 * The name of the photo.
	 */
	private String photoName;

	private String caption;

	/**
	 * A list of tags associated with the photo.
	 */
	private ArrayList<Tag> tags;

	/**
	 * Creates a new instance of a photo.
	 */
	public Photo() {

		photo = null;
		caption = null;
		this.tags = new ArrayList<Tag>();
	}

	/**
	 * Returns the image
	 *
	 * @return the image
	 */
	public Bitmap getPhoto() {

		return this.photo;
	}

	/**
	 * Returns the name of the photo
	 *
	 * @return the name of the photo
	 */
	public String getPhotoName() {

		return this.photoName;
	}

	public String getCaption() {

		return this.caption;
	}

	/**
	 * Returns a list of tags the photo is associated with
	 *
	 * @return a list of tags the photo is associated with
	 */
	public ArrayList<Tag> getTags() {

		return this.tags;
	}

	/**
	 * Sets the photo
	 *
	 * @param image the image to be associated with this photo
	 */
	public void setPhoto(Bitmap image) {

		this.photo = image;
		this.location = image.toString();
	}

	/**
	 * Sets the name of the photo
	 *
	 * @param photoName the new name of the photo
	 */
	public void setPhotoName(String photoName) {

		this.photoName = photoName;
	}

	/**
	 * Sets the caption of the photo
	 *
	 * @param caption the new caption of the photo
	 */
	public void setCaption(String caption) {

		this.caption = caption;
	}

	/**
	 * Adds a tag to the tags list
	 */
	public void addTag(String name, String value) {
		if (name==null || value == null) {
			return;
		}
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getTagName().equalsIgnoreCase(name.toLowerCase()) && tags.get(i).getTagValue().equalsIgnoreCase(value.toLowerCase())) {
				return;
			}
		}
		tags.add(new Tag(name, value));
		return;
	}

	/**
	 * Removes a tag from the tags list
	 */
	public void removeTag(Tag tag) {
		if (tag.getTagName()==null || tag.getTagValue() == null) {
			return;
		}
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getTagName().equalsIgnoreCase(tag.getTagName().toLowerCase()) && tags.get(i).getTagValue().equalsIgnoreCase(tag.getTagValue().toLowerCase())) {
				tags.remove(tag);
				return;

			}
		}
	}
	public String getLocation() {
		return this.location;
	}
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		int b;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		while((b = ois.read()) != -1)
			byteStream.write(b);
		byte bitmapBytes[] = byteStream.toByteArray();
		photo = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		if(photo != null){
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
			byte bitmapBytes[] = byteStream.toByteArray();
			oos.write(bitmapBytes, 0, bitmapBytes.length);
		}
	}
}
