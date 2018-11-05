package creator;

import product.Furniture;
import product.PlasticChair;
import product.WoodenChair;

public class ChairMaker extends FurnitureStore {

    public Furniture createFurniture(String type){
        if(type.equalsIgnoreCase("wood")){
            return new WoodenChair();
        } else if(type.equalsIgnoreCase("plastic")) {
            return new PlasticChair();
        } else {
            return null;
        }
    }
}
