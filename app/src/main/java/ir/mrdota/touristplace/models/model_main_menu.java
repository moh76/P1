package ir.mrdota.touristplace.models;

public class model_main_menu {
    String  title;
    String src_image;
    String text;
    String src_ic_img ;
    String category ;
    int fav ;
    int _id ;

    public model_main_menu (int _id , String category, String title, String text , String src_image , String src_ic_img, int fav){
        this._id = _id ;
        this.title = title ;
        this.src_image = src_image;
        this.src_ic_img=src_ic_img;
        this.text = text ;
        this.category =category ;
        this.fav=fav;


    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }
    public String getSrc_ic_img() {
        return src_ic_img;
    }

    public void setSrc_ic_img(String src_ic_img) {
        this.src_ic_img = src_ic_img;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc_image() {
        return src_image;
    }

    public void setSrc_image(String src_image) {
        this.src_image = src_image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
