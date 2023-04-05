package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
	}

	@Override
	protected ShortestPathSolution doRun() {
		final ShortestPathData data = getInputData();
		Node node = data.getOrigin();
		// List<Label> label_list = new ArrayList<>();
		BinaryHeap<Label> label_bin_heap = new BinaryHeap<>();

		/*
		 * for (Node nodes : data.getGraph().getNodes()) {
		 * label_list.add(null);
		 * }
		 */

		for (Node nodes : data.getGraph().getNodes()) {
			Label label = null;
			if (nodes.getId() == data.getOrigin().getId()) {
				label = new Label(nodes.getId(), true, 0, null);
			} else {
				label = new Label(nodes.getId(), false, -1, null);
			}

			label_bin_heap.insert(label);
			// label_list.add(nodes.getId(), label);
		}

		while (1) {
			Label label_entree = label_bin_heap.deleteMin();
			node = data.getGraph().get(label_entree.getSommetCourant());
			Node node_dest = null;
			Label label = null;
			Label current_label = label_list.get(node.getId());
			double cout = 0;
			for (Arc arc : node.getSuccessors()) {
				node_dest = arc.getDestination();
				label = label_list.get(node_dest.getId());
				cout = current_label.getCoutRealise() + data.getCost(arc);
				if (label.getCoutRealise() == -1 || label.getCoutRealise() > cout) {
					label.setCoutRealise(cout);
					label.setPere(arc);
				}
			}

			double min = -1;
			Label label_marque = null;
			for (Label label0 : label_list) {
				if (min == -1 || (label0.getMarque() == false && label0.getCoutRealise() < min)) {
					min = label0.getCoutRealise();
					label_marque = label0;
				}
			}
			label_marque.setMarque(true);
		}

		// node_list.removeAll();
	}

	ShortestPathSolution solution = null;

	return solution;
}

}
