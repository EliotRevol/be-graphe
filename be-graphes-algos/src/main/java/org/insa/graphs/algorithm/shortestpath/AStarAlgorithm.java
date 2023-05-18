package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

	public AStarAlgorithm(ShortestPathData data) {
		super(data);
	}

	@Override
	protected ShortestPathSolution doRun() {
		final ShortestPathData data = getInputData();
		Node node = data.getOrigin();
		List<LabelStar> label_list = new ArrayList<>();
		BinaryHeap<Label> label_bin_heap = new BinaryHeap<>();
		List<Arc> chemin = new ArrayList<>();

		// for (Node nodes : data.getGraph().getNodes()) {
		// label_list.add(null);
		// }

		for (Node nodes : data.getGraph().getNodes()) {
			LabelStar label = null;
			if (nodes.getId() == data.getOrigin().getId()) {
				label = new LabelStar(nodes.getId(), true, 0, null, 0);
				label_bin_heap.insert(label);
				notifyOriginProcessed(data.getOrigin());
			} else {
				label = new LabelStar(nodes.getId(), false, Double.POSITIVE_INFINITY, null,
						Point.distance(data.getDestination().getPoint(), nodes.getPoint()));
			}

			label_list.add(label);
		}

		while (!label_bin_heap.isEmpty()
				&& data.getGraph().get(label_bin_heap.findMin().getSommetCourant()).getId() != data.getDestination().getId()) {
			Label label_entree = label_bin_heap.deleteMin();
			if (label_entree.getCoutRealise() != 0) {
				System.out.println(label_entree.getCoutRealise());
				chemin.add(label_entree.getPere());
			}
			node = data.getGraph().get(label_entree.getSommetCourant());
			label_entree.setMarque(true);
			notifyNodeMarked(node);
			Node node_dest = null;
			LabelStar label = null;
			Label current_label = label_entree;// label_list.get(node.getId());
			double cout = 0;
			for (Arc arc : node.getSuccessors()) {
				node_dest = arc.getDestination();
				notifyNodeReached(node_dest);
				label = label_list.get(node_dest.getId());
				cout = current_label.getCoutRealise() + data.getCost(arc);
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

		if (label_bin_heap.isEmpty()) {
			return new ShortestPathSolution(data, Status.INFEASIBLE);
		}
		Label label_entree = label_bin_heap.deleteMin();
		List<Arc> chemin_final = new ArrayList<>();
		chemin.add(label_entree.getPere());
		Arc arc = label_entree.getPere();
		notifyDestinationReached(arc.getDestination());
		while (arc != null) {
			System.out.println(arc);
			chemin_final.add(0, arc);
			arc = label_list.get(arc.getOrigin().getId()).getPere();
		}

		return new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), chemin_final));

	}

}
