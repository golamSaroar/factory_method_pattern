package creator;

import product.Furniture;

public abstract class FurnitureStore {

    public void orderFurniture(String type){
        Furniture furniture = createFurniture(type);
        if(furniture != null) {
            furniture.pack();
            furniture.deliver();
        }
    }

    abstract Furniture createFurniture(String type);
}
