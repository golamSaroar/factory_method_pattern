import creator.FurnitureStore;
import creator.ChairMaker;
import creator.TableMaker;

public class Main {

    public static void main(String[] args) {

        FurnitureStore chairStore = new ChairMaker();
        chairStore.orderFurniture("wood");

        FurnitureStore tableStore = new TableMaker();
        tableStore.orderFurniture("plastic");
    }
}
