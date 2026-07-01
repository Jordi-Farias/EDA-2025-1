public class Game {
    String name;
    String category;
    int price;
    int quality;

    public Game (String name, String category, int price, int quality) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quality = quality;
    }


    public void getGame(){
        System.out.println("Caracteristicas del juego: ");
        System.out.println("nombre: "+name);
        System.out.println("categoria: "+category);
        System.out.println("price: "+price);
        System.out.println("quantity: "+quality);
    }
}