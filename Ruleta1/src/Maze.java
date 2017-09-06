import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JPanel;

public class Maze extends JPanel{
    
    private Cuadro[][] cuadros;
    private QueueArray<Camino> queue;
    private StackLinkedList<Camino> stack;
    private CoordinateConverter converter;
    private int state;
    private Camino fin;
    private Camino inicio;
    private int velocidad;
    private int wallAglomeration;
    private ControlPanel cp;
    private int pixelSize;
    
    public Maze(ControlPanel cp){
        super();
        this.cp = cp;
        this.pixelSize = 40;
        this.setPreferredSize(new Dimension(this.pixelSize*20, this.pixelSize*20-10));
        cuadros = new Cuadro[20][20];
        this.converter = new CoordinateConverter();
        this.state = 1;
        this.velocidad = 75;
        this.wallAglomeration = 4;
        
        this.reset();
        
        this.addMouseListener(new MouseListener() {
            
            public void mouseReleased(MouseEvent arg0) {
                
            }
            
            public void mousePressed(MouseEvent arg0) {
                
            }
            
            public void mouseExited(MouseEvent arg0) {
                
            }
            
            public void mouseEntered(MouseEvent arg0) {
                
            }
            
            public void mouseClicked(MouseEvent e) {
                try{
                if(Maze.this.cuadros[e.getY()/pixelSize][e.getX()/pixelSize] instanceof Pared){
                    // Si es una pared no hacer nada
                    return;
                }
                if(Maze.this.state == 1){
                    // Si esta en el estado uno, agregar el cuadrante como el inicio
                    Maze.this.inicio = (Camino)Maze.this.cuadros[e.getY()/pixelSize][e.getX()/pixelSize];
                    Maze.this.inicio.changeColor(Cuadro.colorStart);
                    Maze.this.repaint();
                    Maze.this.state = 2;
                    Maze.this.cp.setNotesText("Selecciona el punto final");
                }else if(Maze.this.state == 2){
                    // Si esta en el estado dos, agregarlo como el final
                    if(Maze.this.cuadros[e.getY()/pixelSize][e.getX()/pixelSize] != Maze.this.inicio){
                        Maze.this.fin = (Camino)Maze.this.cuadros[e.getY()/pixelSize][e.getX()/pixelSize];
                        Maze.this.fin.changeColor(Cuadro.colorEnd);
                        Maze.this.repaint();
                        Maze.this.state = 4;
                        Maze.this.cp.setNotesText("");
                        Maze.this.startSimulation();
                    }
                }
                }catch(ArrayIndexOutOfBoundsException ex){
                    // No hacer nada
                }
            }
        });
    }
    
    public void startSimulation(){
        
        Thread hilo = new Thread(new Runnable() {
            
            public void run() {
                boolean found = false;
                Maze.this.queue.enQueue(Maze.this.inicio);
                Camino current = null;
                while(!Maze.this.queue.isEmpty()){
                    try {
                        current = Maze.this.queue.deQueue();
                        
                        Maze.this.stack.push(current);
                        if(current != Maze.this.inicio){
                            current.changeColor(Cuadro.colorSearch);
                        }
                        if(current == Maze.this.fin){
                            current.changeColor(Cuadro.colorEnd);
                            Maze.this.repaint();
                            current.addPath(new int[]{Maze.this.converter.matrixToLinear(current.getY()/pixelSize, current.getX()/pixelSize, Maze.this.cuadros.length)});
                            found = true;
                            
                            break;
                        }else{
                            // Sacar el path de current
                            QueueArray<Integer> previousPath = current.getPath();
                            int[] path = new int[previousPath.size() + 1];
                            int i = 0;  // Contador
                            while(!previousPath.isEmpty()){
                                path[i] = previousPath.deQueue();
                                i++;
                            }
                            // Aniadir la coordenada del nodo actual
                            path[i] = Maze.this.converter.matrixToLinear(current.getY()/pixelSize, current.getX()/pixelSize, Maze.this.cuadros.length);
                            
                            
                            // Agregar vecinos
                            if(current.getX()/pixelSize + 1 < Maze.this.cuadros.length){
                                // Vecino de la derecha
                                if(Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize + 1] instanceof Camino){    // Asegurar que el vecino no es una pared
                                    if(!Maze.this.stack.contains((Camino)Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize + 1]) && !Maze.this.queue.contains((Camino)Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize + 1])){
                                    
                                        ((Camino) Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize + 1]).addPath(path);
                                        Maze.this.queue.enQueue((Camino)Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize + 1]);
                                    }
                                }
                            }
                            if(current.getX()/pixelSize - 1 >= 0){
                                // Vecino de la izquierda
                                if(Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize - 1] instanceof Camino){    // Asegurar que el vecino no es una pared
                                    if(!Maze.this.stack.contains((Camino)Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize - 1]) && !Maze.this.queue.contains((Camino)Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize - 1])){
                                       
                                        ((Camino) Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize - 1]).addPath(path);
                                        Maze.this.queue.enQueue((Camino)Maze.this.cuadros[current.getY()/pixelSize][current.getX()/pixelSize - 1]);
                                    }
                                }
                            }
                            if(current.getY()/pixelSize - 1 >= 0){
                                // Vecino de arriba
                                if(Maze.this.cuadros[current.getY()/pixelSize -1][current.getX()/pixelSize] instanceof Camino){     // Asegurar que el vecino no es una pared
                                    if(!Maze.this.stack.contains((Camino)Maze.this.cuadros[current.getY()/pixelSize - 1][current.getX()/pixelSize]) && !Maze.this.queue.contains((Camino)Maze.this.cuadros[current.getY()/pixelSize - 1][current.getX()/pixelSize])){
                                    
                                        ((Camino) Maze.this.cuadros[current.getY()/pixelSize -1][current.getX()/pixelSize]).addPath(path);
                                        Maze.this.queue.enQueue((Camino)Maze.this.cuadros[current.getY()/pixelSize -1][current.getX()/pixelSize]);
                                    }
                                }
                            }
                            if(current.getY()/pixelSize + 1 < Maze.this.cuadros.length){
                                // Vecino de abajo
                                if(Maze.this.cuadros[current.getY()/pixelSize + 1][current.getX()/pixelSize] instanceof Camino){    // Asegurar que el vecino no es una pared
                                    if(!Maze.this.stack.contains((Camino)Maze.this.cuadros[current.getY()/pixelSize + 1][current.getX()/pixelSize]) && !Maze.this.queue.contains((Camino)Maze.this.cuadros[current.getY()/pixelSize + 1][current.getX()/pixelSize])){
                                    
                                        ((Camino) Maze.this.cuadros[current.getY()/pixelSize + 1][current.getX()/pixelSize]).addPath(path);
                                        Maze.this.queue.enQueue((Camino)Maze.this.cuadros[current.getY()/pixelSize + 1][current.getX()/pixelSize]);
                                    }
                                }
                            }
                        }
                        Maze.this.repaint();
                        Thread.sleep(Maze.this.velocidad);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(found){
                    Maze.this.showPath(current.getPath());
                }else{
                    Maze.this.cp.setNotesText("No se encontro una solucion al laberinto");
                    Maze.this.state = 5;
                }
            }
        });
        hilo.start();
    }
    
    public void reset(){
        this.queue = new QueueArray<Camino>();
        this.stack = new StackLinkedList<Camino>();
        this.state = 1;
        this.cp.setNotesText("Selecciona el punto de inicio");
        Random ran = new Random();
        // Llenar la matriz
        for(int i = 0; i < cuadros.length; i++){
            for(int j = 0; j < cuadros[i].length; j++){
                // Generar las paredes aleatorias
                if(ran.nextInt(this.wallAglomeration) == 0){
                    cuadros[i][j] = new Pared(j*pixelSize, i*pixelSize, this.pixelSize);
                }else{
                    cuadros[i][j] = new Camino(j*pixelSize, i*pixelSize, this.pixelSize);
                }
            }
        }
        
        this.repaint();
    }
    
    public void reset(String filename){
        // Resetea el laberinto con laberinto prefabricado Debe ser 20 x 20
        this.queue = new QueueArray<Camino>();
        this.stack = new StackLinkedList<Camino>();
        this.state = 1;
        this.cp.setNotesText("Selecciona el punto de inicio");
        try{
            BufferedReader bf = new BufferedReader(new FileReader(filename));
            // Llenar la matriz
            for(int i = 0; i < cuadros.length; i++){
                String line = bf.readLine();
                StringTokenizer tk = new StringTokenizer(line, " ");
                for(int j = 0; j < cuadros[i].length; j++){
                    if(tk.nextToken().equals("1")){
                        cuadros[i][j] = new Pared(j*pixelSize, i*pixelSize, this.pixelSize);
                    }else{
                        cuadros[i][j] = new Camino(j*pixelSize, i*pixelSize, this.pixelSize);
                    }
                }
            }
            bf.close();
        }catch(Exception ex){
            // En caso de excepcion resetearlo de manera aleatoria
            this.reset();
        }
        this.repaint();
    }
    
    public void showPath(QueueArray<Integer> path){
        Thread hilo = new Thread(new Runnable() {
            
            public void run() {
                try {
                    while(!path.isEmpty()){
                        int num = path.deQueue();       // Siguiente cordenada del camino
                        Thread.sleep(Maze.this.velocidad + 100);        // El thread se va a esperar dependiendo de la velocidad deseada
                        // Cambiar el color de cada cuadro
                        ((Camino) Maze.this.cuadros[Maze.this.converter.linearToMatrix(num, Maze.this.cuadros.length)[0]][Maze.this.converter.linearToMatrix(num, Maze.this.cuadros.length)[1]]).changeColor(Cuadro.colorPath);
                        Maze.this.repaint();
                    }
                    Maze.this.state = 5;
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        hilo.start();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i < cuadros.length; i++){
            for(int j = 0; j < cuadros[i].length; j++){
                // Pintar los cuadros con su color
                g.setColor(cuadros[i][j].getColor());
                g.fillRect(cuadros[i][j].getX(), cuadros[i][j].getY(), cuadros[i][j].getSide(), cuadros[i][j].getSide());
                // Pintar contornos para la cuadricula
                g.setColor(Color.decode(Cuadro.colorLine));
                g.drawRect(cuadros[i][j].getX(), cuadros[i][j].getY(), cuadros[i][j].getSide(), cuadros[i][j].getSide());
            }
        }
    }
    
    public void setVelocidad(int velocidad){
        if(velocidad >= 5 && velocidad <= 1000){
            this.velocidad = velocidad;
        }
    }
    
    public int getState(){
        return this.state;
    }
    
    public void setWallAglomeration(int num){
        this.wallAglomeration = num;
    }
    
}
