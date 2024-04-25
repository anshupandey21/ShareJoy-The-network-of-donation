package comman1;

public class ScreenItem {
    int ScreenImg;
    String Title,Description;
    public ScreenItem(String title,String description,int screenImg)
    {
        ScreenImg=screenImg;
        Title=title;
        Description=description;
    }
    public void setTitle(String title) {
        Title=title;
    }
    public void setDescription(String description) {
        Description=description;
    }
    public void setScreenImg(int screenImg) {
        ScreenImg=screenImg;
    }
    public int getScreenImg(){
        return ScreenImg;
    }


    public String getTitle(){
        return Title;
    }


    public String getDescription(){
        return Description;
    }


}
