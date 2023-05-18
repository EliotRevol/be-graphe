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

public class AStarTest {

	private static ShortestPathSolution bellman1;
	private static ShortestPathSolution astar1;
	private static ShortestPathSolution bellman2;
	private static ShortestPathSolution astar2;
	private static ShortestPathSolution astar3;
	private static ShortestPathSolution astar4;

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

		AStarAlgorithm astar_algo1 = new AStarAlgorithm(
				new ShortestPathData(graph, origine, destination,
						ArcInspectorFactory.getAllFilters().get(0)));
		astar1 = astar_algo1.doRun();

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

		final AStarAlgorithm astar_algo2 = new AStarAlgorithm(
				new ShortestPathData(graph, origine, destination, ArcInspectorFactory.getAllFilters().get(0)));
		astar2 = astar_algo2.doRun();

		// Test sur la Bretagne (chemin impossible)
		mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
		mapReader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph = mapReader.read();

		origine = graph.getNodes().get(371889);
		destination = graph.getNodes().get(130029);

		AStarAlgorithm astar_algo3 = new AStarAlgorithm(
				new ShortestPathData(graph, origine, destination, ArcInspectorFactory.getAllFilters().get(0)));
		astar3 = astar_algo3.doRun();

		// Test sur Toulouse un noeud
		mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
		mapReader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph = mapReader.read();

		origine = graph.getNodes().get(10);
		// destination = graph.getNodes().get(100);

		final AStarAlgorithm astar_algo4 = new AStarAlgorithm(
				new ShortestPathData(graph, origine, origine, ArcInspectorFactory.getAllFilters().get(0)));
		astar4 = astar_algo4.doRun();

	}

	@Test
	public void testShortPath() {
		assertEquals(null, bellman1.getPath().getLength(), astar1.getPath().getLength(), 0);
		assertEquals(null, bellman2.getPath().getLength(), astar2.getPath().getLength(), 0);

	}

	@Test
	public void testImpossiblePath() {
		assertFalse(astar3.isFeasible());
	}

	@Test
	public void testOneNodePath() {
		assertEquals(null, astar4.getPath().getLength(), 0, 0);
	}

}