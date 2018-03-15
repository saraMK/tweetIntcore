package kayanteam.tweetintcore.Utiles;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mosta on 1/4/2018.
 */

 public  class ResizeViewsUtiles {


    private static ViewGroup.LayoutParams params;
    private static int height,width;
    private static Context context;
    private static View view_;
    public static void resize_views(Context context , View view_ , double height_D , double width_D ,
                                    Boolean heightEqualWidth , boolean height_bool ){

        ResizeViewsUtiles.context=context;
        ResizeViewsUtiles.view_=view_;
        init();
        if (! heightEqualWidth)
        {
        params.height = (int) (height /height_D);
        params.width = (int) (width / width_D);

        }else {
            if (height_bool)
            {
                params.height = (int) (height /height_D);
                params.width = params.height;
            }
            else {

                params.width = (int) (width /width_D);
                params.height = params.width;
            }
        }
       view_.setLayoutParams(params);
    }

    public static void resize_views_noHeigt(Context context , View view_ , double height_D, double width_D ){

        ResizeViewsUtiles.context=context;
        ResizeViewsUtiles.view_=view_;
        init();
        if (height_D == 0.0)
            height_D = params.WRAP_CONTENT;
        else if (height_D == 0.1)
            height_D = params.MATCH_PARENT;
        params.height = (int) height_D;
        params.width = (int) (width / width_D);
        view_.setLayoutParams(params);
    }

    public static void resize_views_noWidth(Context context , View view_ , double height_D, double width_D ){

        ResizeViewsUtiles.context=context;
        ResizeViewsUtiles.view_=view_;
        init();

        if (width_D == 0.0)
            width_D = params.WRAP_CONTENT;
        else if (width_D == 0.01)
            width_D = params.MATCH_PARENT;

        params.height = (int) (height / height_D);
        params.width = (int) width_D;
        view_.setLayoutParams(params);
    }
    public static void resize_views_noWidth_noHeight(Context context , View view_ , double height_D, double width_D ){

        ResizeViewsUtiles.context=context;
        ResizeViewsUtiles.view_=view_;
        init();

        params.height = (int)  height_D;
        params.width = (int) width_D;
        view_.setLayoutParams(params);
    }

    private static void init(){

        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);  // the values are saved in the screenSize object
        width = screenSize.x;
        height = screenSize.y;
        params = view_.getLayoutParams();
    }
}
