import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class Dataset {
    private ArrayList<Game> data;
    private String sortedByAttribute;

    public Dataset(ArrayList<Game> data) {
        this.data = data;
        this.sortedByAttribute = "none";
    }

    public void getSortedByAttribute() {
        System.out.println("sorted by attribute: "+sortedByAttribute);
    }
    //ordenar por precio
    public void sortByPrice() {
        data.sort(Comparator.comparingInt(Game->Game.price));
        sortedByAttribute = "price";
    }

    //ordenar por calidad
    public void sortByQuality() {
        data.sort(Comparator.comparingInt(Game->Game.quality));
        sortedByAttribute = "quality";
    }

    //imprimir
    public void PrintAll() {
        for (Game g : data) {
            g.getGame();
        }
    }

    //busqueda por precio
    public ArrayList<Game> getGamesByPrice(int x) {
            if(sortedByAttribute.equals("price")) {
               return  binarySearchByPrice(x);
            }else {
                return linearSearchByPrice(x);
            }
    }

    //busqueda por calidad
    public ArrayList<Game> getGamesByQuality(int x) {
        if(sortedByAttribute.equals("quality")) {
            return  binarySearchByQuality(x);
        }else {
            return linearSearchByQuality(x);
        }
    }

    //busqueda de precio por rango
    public ArrayList<Game> getGamesByPriceRange(int lowPrice, int highPrice) {
        if (sortedByAttribute.equals("price")) {
            return  binarySearchByPriceRange(lowPrice, highPrice);
        }else{
            return linearSearchByPriceRange(lowPrice, highPrice);
        }
    }

    //busqueda de categoria
    public ArrayList<Game> getGamesByCategory(String category) {
        if(sortedByAttribute.equals("category")) {
            return binarySearchByCategory(category);
        }else{
            return linearSearchByCategory(category);
        }
    }

    //busqueda binaria por precio
    private ArrayList<Game> binarySearchByPrice(int x){
        int left=0, rigth=data.size()-1;
        ArrayList<Game> results = new ArrayList<>();

        while(left<=rigth){

            int mid=(left+rigth)/2;
            int priceGame= data.get(mid).price;

            if(priceGame==x){
                int i = mid;
                while (i >= 0 && data.get(i).price == x) {
                    results.add(data.get(i));
                    i--;
                }
                i = mid + 1;
                while (i < data.size() && data.get(i).price == x) {
                    results.add(data.get(i));
                    i++;
                }
                return results;
            }else if(priceGame>x){
                rigth=mid-1;
            }else if(priceGame<x){
                left=mid+1;
            }
        }
        return results;
    }

    //busqueda binaria por calidad
    private ArrayList<Game> binarySearchByQuality(int x){
        int left=0, rigth=data.size()-1;
        ArrayList<Game> results = new ArrayList<>();
        while(left<=rigth){
            int mid=(left+rigth)/2;
            int qualityGame= data.get(mid).quality;
            if(qualityGame==x){
                int i = mid;
                while (i >= 0 && data.get(i).quality == x) {
                    results.add(data.get(i));
                    i--;
                }
                i = mid + 1;
                while (i < data.size() && data.get(i).quality == x) {
                    results.add(data.get(i));
                    i++;
                }
                return results;
            }else if(qualityGame>x){
                rigth=mid-1;
            }else if(qualityGame<x){
                left=mid+1;
            }
        }
        return results;
    }

    //busqueda binaria de precio por rango
    private ArrayList<Game> binarySearchByPriceRange(int low , int high) {
        int left=0, rigth=data.size()-1;
        int start=-1;
        ArrayList<Game> results = new ArrayList<>();

        while(left<=rigth){
            int mid=(left+rigth)/2;
            int priceGame= data.get(mid).price;

            if(priceGame>=low){
                start=mid;
                rigth=mid-1;
            }else{
                left=mid+1;
            }
        }

        if(start ==-1) return null;

        for(int i=start; i< data.size(); i++){
            if(data.get(i).price<=high){
                results.add(data.get(i));
            }else{
                break;
            }
        }
        return results;
    }

    //busqueda binaria por categoria
    private ArrayList<Game> binarySearchByCategory(String category){
        int left=0, rigth=data.size()-1;
        ArrayList<Game> results = new ArrayList<>();
        while(left<=rigth){
            int mid=(left+rigth)/2;
            String categoryGame= data.get(mid).category;
            int compare = categoryGame.compareTo(category);

            if (compare==0){
                int i = mid;
                while (i >= 0 && data.get(i).category.equals(category)) {
                    results.add(data.get(i));
                    i--;
                }
                i = mid + 1;
                while (i < data.size() && data.get(i).category.equals(category)) {
                    results.add(data.get(i));
                    i++;
                }
                return results;
            }else if(compare>0){
                rigth=mid-1;
            }else if(compare<0){
                left=mid+1;
            }
        }
        return results;
    }

    //busqueda lineal por precio
    private ArrayList<Game> linearSearchByPrice(int x) {
        ArrayList<Game> results = new ArrayList<>();
        for(int i=0; i<data.size(); i++) {
            if (data.get(i).price == x) {
                results.add(data.get(i));
            }
        }
        return results;
    }

    //busqueda lineal por calidad
    private ArrayList<Game> linearSearchByQuality (int x) {
        ArrayList<Game> results = new ArrayList<>();
        for(int i=0; i<data.size(); i++) {
            if (data.get(i).quality == x) {
                results.add(data.get(i));
            }
        }
        return results;
    }

    //busqueda lineal por precio por rangos

    private ArrayList<Game> linearSearchByPriceRange(int low , int high) {
        ArrayList<Game> results = new ArrayList<>();
        for(Game g : data) {
            if (g.price >= low && g.price <= high) {
                results.add(g);
            }
        }
        return results;
    }

    //busqueda lineal por categoria
    private ArrayList<Game> linearSearchByCategory(String category) {
        ArrayList<Game> results = new ArrayList<>();
        for(Game g : data) {
            if (g.category.equals(category)) {
                results.add(g);
            }
        }
        return results;
    }
    
    public void sortByAlgorithm(String algorithm, String attribute) {
        Comparator<Game> comparator;

        // Definir el atributo por el que se ordenará
        if(attribute.equals("quality")){
            comparator = Comparator.comparingInt(Game->Game.quality);
            
        }else if(attribute.equals("category")){
            comparator = Comparator.comparing(Game->Game.category);
            
        }else if(attribute.equals("price")) {
            comparator = Comparator.comparingInt(Game->Game.price);
        }else {
            comparator = Comparator.comparingInt(Game->Game.price);
            attribute="price";
        }

        if(algorithm.equals("bubbleSort")){
            bubbleSort(comparator);
        }else if(algorithm.equals("insertionSort")){
            insertionSort(comparator);
        }else if(algorithm.equals("selectionSort")){
            selectionSort(comparator);
        }else if(algorithm.equals("mergeSort")){
            data = mergeSort(data,comparator);
        }else if(algorithm.equals("quickSort")){
            quickSort(0,data.size()-1,comparator);
        }else{
            data.sort(comparator);
        }

        sortedByAttribute = attribute;

    }

    private void bubbleSort(Comparator<Game> comp) {
        for (int i = 0; i < data.size() - 1; i++) {
            for (int j = 0; j < data.size() - i - 1; j++) {
                if (comp.compare(data.get(j), data.get(j + 1)) > 0) {
                    Collections.swap(data, j, j + 1);
                }
            }
        }
    }

    private void insertionSort(Comparator<Game> comp) {
        for (int i = 1; i < data.size(); i++) {
            Game key = data.get(i);
            int j = i - 1;
            while (j >= 0 && comp.compare(data.get(j), key) > 0) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, key);
        }
    }

    private void selectionSort(Comparator<Game> comp) {
        for (int i = 0; i < data.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < data.size(); j++) {
                if (comp.compare(data.get(j), data.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            Collections.swap(data, i, minIndex);
        }
    }

    private ArrayList<Game> mergeSort(ArrayList<Game> list, Comparator<Game> comp) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;
        ArrayList<Game> left = mergeSort(new ArrayList<>(list.subList(0, mid)), comp);
        ArrayList<Game> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())), comp);

        return merge(left, right, comp);
    }

    private ArrayList<Game> merge(ArrayList<Game> left, ArrayList<Game> right, Comparator<Game> comp) {
        ArrayList<Game> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (comp.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));
        return result;
    }

    private void quickSort(int low, int high, Comparator<Game> comp) {
        int[] stack = new int[high - low + 1];
        int top = -1;

        stack[++top] = low;
        stack[++top] = high;

        while (top >= 0) {
            high = stack[top--];
            low = stack[top--];

            int pi = partition(low, high, comp);

            if (pi - 1 > low) {
                stack[++top] = low;
                stack[++top] = pi - 1;
            }

            if (pi + 1 < high) {
                stack[++top] = pi + 1;
                stack[++top] = high;
            }

        }
    }

    private int partition(int low, int high, Comparator<Game> comp) {
        Game pivot = data.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comp.compare(data.get(j), pivot) <= 0) {
                i++;
                Collections.swap(data, i, j);
            }
        }
        Collections.swap(data, i + 1, high);
        return i + 1;
    }
}