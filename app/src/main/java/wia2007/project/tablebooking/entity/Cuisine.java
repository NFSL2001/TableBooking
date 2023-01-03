package wia2007.project.tablebooking.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wia2007.project.tablebooking.R;

public class Cuisine {
    public static final Integer MALAYSIAN = 1;
    public static final Integer ICON_MALAYSIAN = R.drawable.cuisine_type_1;
    public static final Integer DINNER_BUFFET = 2;
    public static final Integer ICON_DINNER_BUFFET = R.drawable.cuisine_type_2;
    public static final Integer JAPANESE = 3;
    public static final Integer ICON_JAPANESE = R.drawable.cuisine_type_3;
    public static final Integer INDIAN = 4;
    public static final Integer ICON_INDIAN = R.drawable.cuisine_type_4;
    public static final Integer HIGH_TEA_BUFFET = 5;
    public static final Integer ICON_HIGH_TEA_BUFFET = R.drawable.cuisine_type_5;
    public static final Integer CHINESE = 6;
    public static final Integer ICON_CHINESE = R.drawable.cuisine_type_6;
    public static final Integer WESTERN = 7;
    public static final Integer ICON_WESTERN = R.drawable.cuisine_type_7;
    public static final Integer THAI = 8;
    public static final Integer ICON_THAI = R.drawable.cuisine_type_8;

    public static List<Integer> getCuisineIDList(){
        return new ArrayList<Integer>(Arrays.asList(
                MALAYSIAN,
                DINNER_BUFFET,
                JAPANESE,
                INDIAN,
                HIGH_TEA_BUFFET,
                CHINESE,
                WESTERN,
                THAI)
        );
    }

    public static List<String> getCuisineNameList(){
        return new ArrayList<String>(Arrays.asList(
                "Malaysian",
                "Dinner Buffet",
                "Japanese",
                "Indian",
                "High Tea Buffet",
                "Chinese",
                "Western",
                "Thai")
        );
    }

    public static List<Integer> getCuisineIconList(){
        return new ArrayList<Integer>(Arrays.asList(
                ICON_MALAYSIAN,
                ICON_DINNER_BUFFET,
                ICON_JAPANESE,
                ICON_INDIAN,
                ICON_HIGH_TEA_BUFFET,
                ICON_CHINESE,
                ICON_WESTERN,
                ICON_THAI
        ));
    }

    public static CuisineItem getCuisineItem(int position){
        return new CuisineItem(getCuisineIDList().get(position),
                getCuisineNameList().get(position),
                getCuisineIconList().get(position));
    }

    public static int getSize(){
        return 8;
    }

    public static class CuisineItem {
        public static Integer id;
        public static String name;
        public static Integer iconResource;

        public CuisineItem(Integer id, String name, Integer resource){
            this.id = id;
            this.name = name;
            this.iconResource = resource;
        }
    }
}
