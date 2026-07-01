public class ConnectFour {
    private char[][] grid = new char[7][6];
    private char currentSymbol;

    public ConnectFour(){

        for(int i=0; i<7; i++){

            for(int j=0; j<6; j++){

                this.grid[i][j] = ' ';
            }
        }

        this.currentSymbol = 'x';
    }

    public Boolean makeMove(int z){//e

        if(z >= 7 || z < 0) return false;

        for(int i=0; i<6; i++){

            if(grid[z][i] != ' ') continue;

            grid[z][i] = currentSymbol;
            if(currentSymbol == 'x') currentSymbol = 'o';
            else currentSymbol = 'x';

            return true;
        }

        return false;
    }

    public char isGameOver(){
        boolean empty = false;

        for(int i=0; i < 7; i++){
            for(int j=0; j < 6; j++) {

                char symbol = grid[i][j];

                //si esta vacio, todavia no hay empate y se salta a la sig linea
                if (symbol == ' ') {
                    empty = true;
                    break;
                }

                // busca de forma vertical
                if (j + 3 < 6 && grid[i][j + 1] == symbol && grid[i][j + 2] == symbol && grid[i][j + 3] == symbol) {
                    return symbol;
                }
                // busca de forma horizontal
                if (i + 3 < 7 && grid[i + 1][j] == symbol && grid[i + 2][j] == symbol && grid[i + 3][j] == symbol) {
                    return symbol;
                }
                // busca diagonal hacia arriba
                if (i + 3 < 7 && j + 3 < 6 && grid[i + 1][j + 1] == symbol && grid[i + 2][j + 2] == symbol && grid[i + 3][j + 3] == symbol){
                    return symbol;
                }
                //busca diagonal hacia abajo
                if(i + 3 < 7 && j - 3 >= 0 && grid[i + 1][j - 1] == symbol && grid[i + 2][j - 2] == symbol && grid[i + 3][j - 3] == symbol) {
                    return symbol;
                }
            }
        }

        if(empty) return 'c'; //continue
        return 'd'; //draw
    }

    public void printBoard() {
        for (int j = 5; j >= 0; j--) {
            for (int i = 0; i < 7; i++) {
                System.out.print("|" + grid[i][j]);
            }
            System.out.println("|");
        }
        System.out.println(" 1 2 3 4 5 6 7");
    }



    public char getCurrentSymbol() {
        return currentSymbol;
    }

    public void setCurrentSymbol(char symbol) {
        this.currentSymbol = symbol;
    }

    public void clearBoard() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                grid[i][j] = ' ';
            }
        }
        currentSymbol = 'x';
    }

}
