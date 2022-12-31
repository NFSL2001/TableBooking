package wia2007.project.tablebooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuHolder extends RecyclerView.ViewHolder {

    private final TextView menuName;
    private final TextView menuPrice;
    private final TextView menuDescription;
    private final ImageView menuImage;

    public MenuHolder(@NonNull View itemView) {
        super(itemView);
        menuName = itemView.findViewById(R.id.TVMenuName);
        menuPrice = itemView.findViewById(R.id.TVMenuPrice);
        menuDescription = itemView.findViewById(R.id.TVDescriptionMenuAdmin);
        menuImage = itemView.findViewById(R.id.MenuImage);
    }

    public void bind(String name, double price, String description){
        menuName.setText(name);
        menuPrice.setText("RM "+Double.toString(price));
        menuDescription.setText(description);
//        menuImage.setImageResource();
    }

    static MenuHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_menu_row, parent, false);
        return new MenuHolder(view);
    }

//    protected String readMenuImage() {
//        String FileContent = "";
//        FileInputStream FIS = null;
//        try {
//            FIS = getApplicationContext().openFileInput(USER_FILE_NAME);
//            Bitmap thumbnail1 = getApplicationContext().getContentResolver().loadThumbnail(ImageList.get(0).uri, new Size(1280, 960), null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        InputStreamReader inputStreamReader = new InputStreamReader(FIS, StandardCharsets.UTF_8);
//        StringBuilder stringBuilder = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
//            String line = reader.readLine();
//            while (line != null) {
//                stringBuilder.append(line).append('\n');
//                line = reader.readLine();
//            }
//        } catch (IOException e) {
//            System.out.println(e);
//        } finally {
//            FileContent = stringBuilder.toString();
//        }
//        return FileContent;
//    }
}
