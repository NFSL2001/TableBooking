package wia2007.project.tablebooking;

import org.junit.Test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(System.currentTimeMillis());
        System.out.println(format);
    }
}