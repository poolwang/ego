package cn.wl.base.vo;

public class SearchItem {

	private Long id;
	private String title;
	private String sellPoint;
	private Long price;
	private String image;
	private String categoryName;
    private String[] images;
	
	public String[] getImages() {
		if(null!=this.image){
			images = image.split(",");
		}
		return images;
	}
	public SearchItem() {
		super();
	}
	public SearchItem(Long id, String title, String sellPoint, Long price, String image, String categoryName) {
		super();
		this.id = id;
		this.title = title;
		this.sellPoint = sellPoint;
		this.price = price;
		this.image = image;
		this.categoryName = categoryName;
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
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
