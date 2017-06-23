package experiment.diary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/*
* @ClassName: Diary
* @Description: manage the data of a diary and provide a template structure
*     Core data structure for databases and activities
* @author Relati
* @date 2017-06-23 17:52:21
*
* IMPORTANT
*/
public class Diary {

    /*
    * @Fields month : the month of the diary when it is created.
    * @Fields day : the day of the diary when it is created.
    * @Fields title : the title of the diary when it is created.
    * @Fields content : the content of the diary when it is created.
    * @Fields picture : the picture of the diary when it is created.
    */
    private int month;
    private int day;
    private String title;
    private String content;
    private byte[] picture;

    /*
    * Title: Class Diary constructor
    * Description: the constructor of Class Diary
    * IMPORTANT
    */
    Diary(int month, int day, String title, String content, byte[] picture) {
        this.month   = month;
        this.day     = day;
        this.title   = title;
        this.content = content;
        this.picture = picture;
    }

    /*
    * @return picture @type byte[]
    */
    public byte[] getPicture() {
        return picture;
    }
    /*
    * @return day @type int
    */
    public int getDay() {
        return day;
    }
    /*
    * @return month @type int
    */
    public int getMonth() {
        return month;
    }
    /*
    * @return content @type String
    */
    public String getContent() {
        return content;
    }
    /*
    * @return title @type String
    */
    public String getTitle() {
        return title;
    }


    /*
    * @param pic @type byte[]: the picture of this diary
    */
    public void setPicture(byte[] pic) {
        picture = pic;
    }
    /*
    * @param d @type int: the day of this diary
    */
    public void setDay(int d) {
        day = d;
    }
    /*
    * @param m @type int: the month of this diary
    */
    public void setMonth(int m) {
        month = m;
    }
    /*
    * @param s @type String: the title of this diary
    */
    public void setTitle(String s) {
        title = s;
    }
    /*
    * @param s @type String: the content of this diary
    */
    public void setContent(String s) {
        content = s;
    }

    /*
    * @Title: bitmapToBytes
    * @Description: change the picture with Bitmap type into
    *     the one with byte[] type
    * @param bm @type Bitmap: the Bitmap picture which is to
    *     be changed into byte[]
    * @return byte[]
    * @throws globle, or to say no exception handler
    */
    // TODO: the method of picture changing
    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /*
    * @Title: bytesToBimap
    * @Description: change the picture with byte[] type into
    *     the one with Bitmap type
    * @param b @type byte[]: the byte[] picture which is to
    *     be changed into Bitmap
    * @return Bitmap
    * @throws globle, or to say no exception handler
    */
    public static Bitmap bytesToBimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /*
    * @Title: drawableToBitmap
    * @Description: change the picture with Drawable type into
    *               the one with Bitmap type
    * @param drawable @type Drawable: the Drawable picture
    *     which is to be changed into Bitmap
    * @return Bitmap
    * @throws globle, or to say no exception handler
    */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /*
    * @Title: bitmapToDrawable
    * @Description: change the picture with Bitmap type into
    *     the one with Drawable type
    * @param bm @type Bitmap: the Bitmap picture which is to
    *     be changed into Drawable
    * @return Drawable
    * @throws globle, or to say no exception handler
    */
    public static Drawable bitmapToDrawable(Bitmap bm) {
        return new BitmapDrawable(bm);
    }
}
