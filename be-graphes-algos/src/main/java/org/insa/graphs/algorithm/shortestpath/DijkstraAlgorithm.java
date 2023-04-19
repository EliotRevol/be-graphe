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

		for (Node nodes : data.getGraph().getNodes()) {
			label_list.add(null);
		}

		for (Node nodes : data.getGraph().getNodes()) {
			Label label = null;
			if (nodes.getId() == data.getOrigin().getId()) {
				label = new Label(nodes.getId(), true, 0, null);
			} else {
				label = new Label(nodes.getId(), false, Double.POSITIVE_INFINITY, null);
			}

			label_bin_heap.insert(label);
			label_list.add(nodes.getId(), label);
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
			Node node_dest = null;
			Label label = null;
			Label current_label = label_entree;// label_list.get(node.getId());
			double cout = 0;
			for (Arc arc : node.getSuccessors()) {
				node_dest = arc.getDestination();
				label = label_list.get(node_dest.getId());
				cout = current_label.getCoutRealise() + data.getCost(arc);
				if (label.getCoutRealise() == -1 || label.getCoutRealise() > cout) {
					try {
						label_bin_heap.remove(label);
						label_bin_heap.insert(label);
						label.setCoutRealise(cout);
						label.setPere(arc);
					} catch (ElementNotFoundException e) {
					}

				}
			}
		}

		Label label_entree = label_bin_heap.deleteMin();
		List<Arc> chemin_final = new ArrayList<>();
		chemin.add(label_entree.getPere());
		Arc arc = label_entree.getPere();
		while (arc != null) {
			System.out.println(arc);
			chemin_final.add(0, arc);
			arc = label_list.get(arc.getOrigin().getId()).getPere();
		}

		return new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), chemin_final));

	}

	public Path solution() {
		return this.doRun().getPath();
	}
}
