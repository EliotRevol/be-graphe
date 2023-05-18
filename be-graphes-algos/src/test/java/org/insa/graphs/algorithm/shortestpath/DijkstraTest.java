package org.insa.graphs.algorithm.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.insa.graphs.algorithm.ArcInspectorFactory;

public class DijkstraTest {

	private static ShortestPathSolution bellman1;
	private static ShortestPathSolution dijkstra1;
	private static ShortestPathSolution bellman2;
	private static ShortestPathSolution dijkstra2;
	private static ShortestPathSolution dijkstra3;

	@BeforeClass
	public static void initAll() throws Exception {

		// Test sur carré dense
		String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
		GraphReader mapReader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		Graph graph = mapReader.read();

		Node origine = graph.getNodes().get(141193);
		Node destination = graph.getNodes().get(253346);

		BellmanFordAlgorithm bellman_algo1 = new BellmanFordAlgorithm(
				new ShortestPathData(graph, origine, destination,
						ArcInspectorFactory.getAllFilters().get(0)));
		bellman1 = bellman_algo1.doRun();

		DijkstraAlgorithm dijkstra_algo1 = new DijkstraAlgorithm(
				new ShortestPathData(graph, origine, destination,
						ArcInspectorFactory.getAllFilters().get(0)));
		dijkstra1 = dijkstra_algo1.doRun();

		// Test sur Toulouse
		mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
		mapReader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph = mapReader.read();

		origine = graph.getNodes().get(10);
		destination = graph.getNodes().get(100);

		BellmanFordAlgorithm bellman_algo2 = new BellmanFordAlgorithm(
				new ShortestPathData(graph, origine, destination,
						ArcInspectorFactory.getAllFilters().get(0)));
		bellman2 = bellman_algo2.doRun();

		final DijkstraAlgorithm dijkstra_algo2 = new DijkstraAlgorithm(
				new ShortestPathData(graph, origine, destination, ArcInspectorFactory.getAllFilters().get(0)));
		dijkstra2 = dijkstra_algo2.doRun();

		// Test sur la Bretagne (chemin impossible)
		mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
		mapReader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph = mapReader.read();

		origine = graph.getNodes().get(371889);
		destination = graph.getNodes().get(130029);

		DijkstraAlgorithm dijkstra_algo3 = new DijkstraAlgorithm(
				new ShortestPathData(graph, origine, destination, ArcInspectorFactory.getAllFilters().get(0)));
		dijkstra3 = dijkstra_algo3.doRun();

	}

	@Test
	public void testShortPath() {
		assertEquals(null, bellman1.getPath().getLength(), dijkstra1.getPath().getLength(), 0);
		assertEquals(null, bellman2.getPath().getLength(), dijkstra2.getPath().getLength(), 0);

	}

	@Test
	public void testImpossiblePath() {
		assertFalse(dijkstra3.isFeasible());
	}

	/*
	 * @Test
	 * public void testOneNodePath() {
	 * assertEquals(null, dijkPath_unnoeud.getPath().getLength(), 0, 0);
	 * }
	 */

}
