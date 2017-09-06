
public class CoordinateConverter {
    
    public int matrixToLinear(int i, int j, int len){
        // Cambia coordenadas i j en linear
        return i*(len) + j;
    }
    
    public int[] linearToMatrix(int linear, int len){
        int i = 0;
        int j = 0;
        while(linear-len >= 0){
            i++;
            linear -= len;
        }
        j = linear % len;
        return new int[]{i, j};
    }
}
