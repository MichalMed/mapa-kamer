package cz.mapakamer.entity;


public class Camera {

	private static final int STATUS_NEW = 0;
	private static final int STATUS_ACCEPTED = 1;
	private static final int STATUS_REJECTED = -1;
	
	private int id;
	private double latitude;
	private double longitude;
    private String address;
    private String description;    
    private String author;
    private int status;
	
	
	public Camera() {
		this.status = STATUS_NEW;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
    public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
    
}
