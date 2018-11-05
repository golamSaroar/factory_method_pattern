package creator;

import product.*;

public class TableMaker extends FurnitureStore {

    public Furniture createFurniture(String type){
        if(type.equalsIgnoreCase("wood")){
            return new WoodenTable();
        } else if(type.equalsIgnoreCase("plastic")){
            return new PlasticTable();
        } else {
            return null;
        }
    }
}
