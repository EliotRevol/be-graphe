package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
	}

	@Override
	protected ShortestPathSolution doRun() {
		final ShortestPathData data = getInputData();
		Node node = data.getOrigin();
		List<Label> label_list = new ArrayList<>();
		BinaryHeap<Label> label_bin_heap = new BinaryHeap<>();
		List<Arc> chemin = new ArrayList<>();

		//Si origine = destination, on a qu'un noeud => Aucun traitement
		if (data.getOrigin().getId() == data.getDestination().getId()) {
			List<Arc> chemin_final = new ArrayList<>();
			return new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), chemin_final));
		}

		//Parcours de tous les noeuds du graphe et ajout dans une liste de label qui permet de faire le lien entre l'ID 
		//d'un noeud et son label associé, et génération de tous les labels de chaque noeuds
		for (Node nodes : data.getGraph().getNodes()) {
			Label label = null;
			if (nodes.getId() == data.getOrigin().getId()) {
				label = new Label(nodes.getId(), true, 0, null);
				label_bin_heap.insert(label);
				notifyOriginProcessed(data.getOrigin());
			} else {
				label = new Label(nodes.getId(), false, Double.POSITIVE_INFINITY, null);
			}

			label_list.add(label);
		}

		//Itérations de l'algorithme de Dijkstra : s'arrête quand tous les noeuds sont visitées ou qu'on a trouvé le plus court chemin
		while ((!label_bin_heap.isEmpty())
				&& (data.getGraph().get(label_bin_heap.findMin().getSommetCourant()).getId() != data.getDestination()
						.getId())) {
			Label label_entree = label_bin_heap.deleteMin();
			if (label_entree.getCoutRealise() != 0) {
				chemin.add(label_entree.getPere());
			}
			node = data.getGraph().get(label_entree.getSommetCourant());
			label_entree.setMarque(true); //On marque le noeud avec le chemin minimal enregistré dans le tas
			notifyNodeMarked(node); //Permet de visualiser les noeuds visités
			Node node_dest = null;
			Label label = null;
			Label current_label = label_entree;// label_list.get(node.getId());
			double cout = 0;

			//On regarde tous les arcs reliés au noeud pour visité les voisins du noeud enregistré
			for (Arc arc : node.getSuccessors()) {
				node_dest = arc.getDestination();
				notifyNodeReached(node_dest);
				label = label_list.get(node_dest.getId());
				cout = current_label.getCoutRealise() + data.getCost(arc);

				//Si le noeud actuellement visité n'a pas encore de cout (correspond à infini dans l'algo de Dijkstra)
				// ou que le cout en passant par l'arc actuel est plus petit, on met à jour la valeur du cout dans le label associé.
				if (label.getCoutRealise() == -1 || label.getCoutRealise() > cout) {
					try {
						// label_bin_heap.remove(label);
						label_bin_heap.insert(label);
						label.setCoutRealise(cout);
						label.setPere(arc);
					} catch (ElementNotFoundException e) {
					}

				}
			}
		}
		
		//Si le tas binaire est vide, c'est qu'on a pas atteint la destination -> aucune solution
		if (label_bin_heap.isEmpty()) {
			return new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {
			//Sinon, on a atteint la destination , il ne reste plus qu'à reconstruire le chemin en parcourant le père de chaque 
			//label en partant du label de destination
			Label label_entree = label_bin_heap.deleteMin();
			List<Arc> chemin_final = new ArrayList<>();
			chemin.add(label_entree.getPere());
			Arc arc = label_entree.getPere();
			notifyDestinationReached(arc.getDestination());
			while (arc != null) {
				chemin_final.add(0, arc);
				arc = label_list.get(arc.getOrigin().getId()).getPere();
			}

			return new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), chemin_final));
		}

	}

	public Path solution() {
		return this.doRun().getPath();
	}
}
