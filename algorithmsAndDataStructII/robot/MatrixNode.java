public class MatrixNode {
    private int row, col, cost;

    public MatrixNode(int row, int col, int cost){
        this.row = row;
        this.col = col;
        this.cost = cost;
    }
        
    public int      getRow()    {return this.row;}
    public int      getCol()    {return this.col;}
    public int      getCost()   {return this.cost;}
}