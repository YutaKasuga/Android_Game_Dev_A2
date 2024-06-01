package com.mygdx.game;

public class Craft {
    /*
        The class to creating Potion or Weapon.
        To craft items, player need some materials to get it.
     */
    private static Assignment2 game;
    private static Weapon weapon;
    private static Potion potion;


    public static Weapon createWeapon(String material1, String material2){
        /*
         Create new weapon.
         parameter:
            material1 -> the dropped material from the enemy.
            material2 -> the dropped material from the enemy.
         */

        // NOTE: need to decide and set the item names

        if (material1.equals("TempItem") && material2.equals("TempItemWood")){
            // section for making WoodSword
            weapon = new WoodSword(game);
        } else if (material1.equals("TempItem") && material2.equals("TempItemStone")){
            // section for making BoneSword
            weapon = new BoneSword(game);
        } else if (material1.equals("TempItem2") && material2.equals("TempItemStone")){
            // section for making BoneBow
            weapon = new BoneBow(game);
        } else {
            // section for making WoodBow
            weapon = new WoodBow(game);
        }

        return weapon;
    }

    public static Potion createPotion(String material1, String material2){
        /*
         Create new Potion.
         parameter:
            material1 -> the dropped material from the enemy.
            material2 -> the dropped material from the enemy.
         */

        // NOTE: need to decide and set the item names

        if (material1.equals("TempItem") && material2.equals("TempItem2")){
            // section for making Elixir
            potion = new Elixir(game);
        } else if (material1.equals("TempItem3") && material2.equals("TempItem4")){
            // section for making SpeedPotion
            potion = new SpeedPotion(game);
        } else {
            // section for making HighPotion
            potion = new HighPotion(game);
        }

        return potion;
    }
}
