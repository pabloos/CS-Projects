import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

class Grafo {
	private final Map<String, List<Vertice>> vertices;
	
	public Grafo() {
		this.vertices = new HashMap<String, List<Vertice>>();
	}
	
	public void anadirVertice(String String, List<Vertice> vertice) {
		this.vertices.put(String, vertice);
	}
	
	public List<String> caminoMasCorto(String start, String finish, boolean withTrace) {
		final Map<String, Integer> distances 	= new HashMap<String, Integer>();
		final Map<String, Vertice> previous 	= new HashMap<String, Vertice>();
		
		PriorityQueue<Vertice> nodes 			= new PriorityQueue<Vertice>();			//cola de prioridad
		
		Dijkstra.traza += withTrace ? "Inicializando todos los vertices... " : "";		//mensajes de la traza que vamos acumulando

		for(String vertice : vertices.keySet()) {										//Inicializamos todos los nodos en infinito
			if (vertice.equals(start)) {
				distances.put(vertice, 0);
				nodes.add(new Vertice(vertice, 0));
			} else {
				distances.put(vertice, Integer.MAX_VALUE);
				nodes.add(new Vertice(vertice, Integer.MAX_VALUE));
			}
			previous.put(vertice, null);
		}

		Dijkstra.traza += withTrace ? "Hecho\n\n" : "";

		while (!nodes.isEmpty()) {
			Vertice smallest = nodes.poll();

			Dijkstra.traza += withTrace ? "\nAnalizando el nodo mas pequenio: " + smallest.getId() + "\n" : "";

			if (smallest.getId().equals(finish)) {
				Dijkstra.traza += withTrace ? "\n\nAlcanzado el nodo final... \n\n" : "";

				final List<String> path = new ArrayList<String>();

				while (previous.get(smallest.getId()) != null) {
					path.add(smallest.getId());
					smallest = previous.get(smallest.getId());
				} return path;
			}

			if (distances.get(smallest.getId()) == Integer.MAX_VALUE) break;
						
			for (Vertice neighbor : vertices.get(smallest.getId())) {
				Integer alt = distances.get(smallest.getId()) + neighbor.getDistance();
				
				Dijkstra.traza += withTrace ? "Comparando con el valor del vertice: " + distances.get(neighbor.getId()) + "\n"  : "";

				if (alt < distances.get(neighbor.getId())) {
					distances.put(neighbor.getId(), alt);
					previous.put(neighbor.getId(), smallest);
										
					Dijkstra.traza += withTrace ? "Estado del vector previous: " + previous + "\n" : "";
					Dijkstra.traza += withTrace ? "Estado del vector distances: " + distances + "\n" : "";
										
					forloop:
					for(Vertice n : nodes) {
						if (n.getId().equals(neighbor.getId())){
							nodes.remove(n);
							n.setDistance(alt);
							nodes.add(n);
							break forloop;
						}
					}
				}
			}			
		} return new ArrayList<String>(distances.keySet());
	}
}