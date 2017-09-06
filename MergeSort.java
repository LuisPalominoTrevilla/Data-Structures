import java.util.Random;
import java.util.Scanner;

public class MergeSort {
	
	//Llena la lista con números aleatorios
	public static void fillRandom(double[] listaRandom){
		for(int i=0;i<listaRandom.length;i++){
			listaRandom[i]=(Math.random()*1000);
			listaRandom[i]=Math.round(listaRandom[i]);
			listaRandom[i]=(listaRandom[i]/100);
		}
	}
	
	public static double[] mergeSort(double[] lista){
		if(lista.length<=1){
			System.out.println("entraste");
			return lista;
		}
		int mitad=lista.length/2;
		
		double[] izq=new double[mitad];
		double[] der;
		if(lista.length%2==0){
			der=new double[mitad];
		}
		else{
			der=new double[mitad+1];
		}
		
		double[] resultado=new double[lista.length];
		
		for(int i=0;i<mitad;i++){
			izq[i]=lista[i];
		}
		
		for(int i=mitad;i<lista.length;i++){
			der[i-mitad]=lista[i];
		}
		
		izq=mergeSort(izq);
		der=mergeSort(der);
		resultado=ordenar(izq,der);
		System.out.println("nnnnnn");
		for(int i=0;i<resultado.length;i++){
			System.out.print(resultado[i]+" ");
		}
		return resultado;
	}
	

	public static double[] ordenar(double[] izq, double[] der){
		double guardado;
		int suma=izq.length+der.length;
		double[] union=new double[suma];
		int nIzq=0;
		int nDer=0;
		int nUnion=0;
		
		while(nIzq<izq.length||nDer<der.length){
			if(nIzq<izq.length&&nDer<der.length){
				if(izq[nIzq]<=der[nDer]){
					union[nUnion]=izq[nIzq];
					nIzq++;
					nUnion++;
				}
				else{
					union[nUnion]=der[nDer];
					nDer++;
					nUnion++;	
				}		
			}
			else if(nIzq<izq.length){
				union[nUnion]=izq[nIzq];
				nIzq++;
				nUnion++;
			}
			else if(nDer<der.length){
				union[nUnion]=der[nDer];
				nDer++;
				nUnion++;
			}
		}
		return union;
	}
	
	
	public static boolean isInt(String sn){		
		try{
			Integer.parseInt(sn);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	public static void main(String[] args){
		Scanner scanner=new Scanner(System.in);
		int n=0;
		boolean condicion=true;
		
		System.out.println("¿Cuántos números desea ordenar?");
		while(condicion){
			String sn=scanner.next();
			if(isInt(sn)){
				n=Integer.parseInt(sn);
				condicion=false;
			}
			else{
				System.out.println("Digite un número entero");
			}
		}

		double[] lista=new double[n];
		fillRandom(lista);
		
		System.out.println("Lista desordenada:");
		for(int i=0;i<lista.length;i++){
			System.out.print(lista[i]+" ");
		}
		System.out.println();
		
		lista=mergeSort(lista);
		System.out.println("Lista ordenada:");
		for(int i=0;i<lista.length;i++){
			System.out.print(lista[i]+" ");
		}
		scanner.close();	
	}
}
