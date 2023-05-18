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

	/* --- --- --- Variables pour tests JUnit --- --- --- */
	// trajet court n°1
	private static ShortestPathSolution bellPath_crt1;
	private static ShortestPathSolution aStarPath_crt1;
	// private static ShortestPathSolution dijkPath_crt1; // pour le test de
	// SolvingTime
	// trajet court n°2
	private static ShortestPathSolution bellPath_crt2;
	private static ShortestPathSolution aStarPath_crt2;
	// private static ShortestPathSolution dijkPath_crt2; // pour le test de
	// SolvingTime

	// trajet long n°1 -- longueur --> on va comparer leurs temps et longueurs
	// respectifs pour vérifier que les algorithmes sont cohérents
	// private static ShortestPathSolution aStarPath_lglg1;
	// trajet long n°1 -- temps
	// private static ShortestPathSolution aStarPath_lgtps1;
	// trajet long n°2 -- longueur
	private static ShortestPathSolution aStarPath_lglg2;
	// private static ShortestPathSolution dijkPath_lglg2; // pour le test de
	// SolvingTime
	// trajet long n°2 -- temps
	private static ShortestPathSolution aStarPath_lgtps2;
	// private static ShortestPathSolution dijkPath_lgtps2; // pour le test de
	// SolvingTime

	// impossible de rejoindre la destination
	private static ShortestPathSolution aStarPath_infaisable;

	// destination == origine
	private static ShortestPathSolution aStarPath_unnoeud;

	@BeforeClass
	public static void initAll() throws Exception {
		// trajets courts avec chemins trouvés par Bellman-Ford
		// Carte Carré ("Square") -- On prend deux points à l'opposé l'un de l'autre
		final String mapName_crt1 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre.mapgr";
		final GraphReader reader_crt1 = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName_crt1))));
		final Graph graph_crt1 = reader_crt1.read();

		Node orig_crt1 = graph_crt1.getNodes().get(9);
		Node dest_crt1 = graph_crt1.getNodes().get(10);

		final BellmanFordAlgorithm bellAlgo_crt1 = new BellmanFordAlgorithm(
				new ShortestPathData(graph_crt1, orig_crt1, dest_crt1, ArcInspectorFactory.getAllFilters().get(0)));
		bellPath_crt1 = bellAlgo_crt1.doRun();
		final AStarAlgorithm aStarAlgo_crt1 = new AStarAlgorithm(
				new ShortestPathData(graph_crt1, orig_crt1, dest_crt1, ArcInspectorFactory.getAllFilters().get(0)));
		aStarPath_crt1 = aStarAlgo_crt1.doRun();
		// final DijkstraAlgorithm dijkAlgo_crt1 = new DijkstraAlgorithm(new
		// ShortestPathData(graph_crt1, orig_crt1, dest_crt1,
		// ArcInspectorFactory.getAllFilters().get(0)));
		// dijkPath_crt1 = dijkAlgo_crt1.doRun();

		// Carte de l'Insa -- On prend un point à l'extrimité nord de la carte et un
		// autre à l'extrémité sud
		final String mapName_crt2 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		final GraphReader reader_crt2 = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName_crt2))));
		final Graph graph_crt2 = reader_crt2.read();
		Node orig_crt2 = graph_crt2.getNodes().get(241);
		Node dest_crt2 = graph_crt2.getNodes().get(332);
		final BellmanFordAlgorithm bellAlgo_crt2 = new BellmanFordAlgorithm(
				new ShortestPathData(graph_crt2, orig_crt2, dest_crt2, ArcInspectorFactory.getAllFilters().get(0)));
		bellPath_crt2 = bellAlgo_crt2.doRun();
		final AStarAlgorithm aStarAlgo_crt2 = new AStarAlgorithm(
				new ShortestPathData(graph_crt2, orig_crt2, dest_crt2, ArcInspectorFactory.getAllFilters().get(0)));
		aStarPath_crt2 = aStarAlgo_crt2.doRun();
		// final DijkstraAlgorithm dijkAlgo_crt2 = new DijkstraAlgorithm(new
		// ShortestPathData(graph_crt2, orig_crt2, dest_crt2,
		// ArcInspectorFactory.getAllFilters().get(0)));
		// dijkPath_crt2 = dijkAlgo_crt2.doRun();

		// trajets longs
		// Carte de la France --
		/*
		 * final String mapName_lg1 =
		 * "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/france.mapgr";
		 * final GraphReader reader_lg1 = new BinaryGraphReader(new DataInputStream(new
		 * BufferedInputStream(new FileInputStream(mapName_lg1))));
		 * final Graph graph_lg1 = reader_lg1.read();
		 * Node orig_lg1 = graph_lg1.getNodes().get(241); Node dest_lg1 =
		 * graph_lg1.getNodes().get(332); //!\\ Pas les bons points
		 * // Parcours en temps (uniquement voiture)
		 * final aStarstraAlgorithm aStarAlgo_lgtps1 = new aStarstraAlgorithm(new
		 * ShortestPathData(graph_lg1, orig_lg1, dest_lg1,
		 * ArcInspectorFactory.getAllFilters().get(2)));
		 * aStarPath_lgtps1 = aStarAlgo_lgtps1.doRun();
		 * // Parcours en longueur (uniquement voiture)
		 * final aStarstraAlgorithm aStarAlgo_lglg1 = new aStarstraAlgorithm(new
		 * ShortestPathData(graph_lg1, orig_lg1, dest_lg1,
		 * ArcInspectorFactory.getAllFilters().get(1)));
		 * aStarPath_lglg1 = aStarAlgo_lglg1.doRun();
		 */
		// Carte de la Belgique -- d'est en ouest
		final String mapName_lg2 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/belgium.mapgr";
		final GraphReader reader_lg2 = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName_lg2))));
		final Graph graph_lg2 = reader_lg2.read();
		Node orig_lg2 = graph_lg2.getNodes().get(597909);
		Node dest_lg2 = graph_lg2.getNodes().get(89011);
		// Parcours en temps (uniquement voiture)
		final AStarAlgorithm aStarAlgo_lgtps2 = new AStarAlgorithm(
				new ShortestPathData(graph_lg2, orig_lg2, dest_lg2, ArcInspectorFactory.getAllFilters().get(2)));
		aStarPath_lgtps2 = aStarAlgo_lgtps2.doRun();
		// final DijkstraAlgorithm dijkAlgo_lgtps2 = new DijkstraAlgorithm(new
		// ShortestPathData(graph_lg2, orig_lg2, dest_lg2,
		// ArcInspectorFactory.getAllFilters().get(2)));
		// dijkPath_lgtps2 = dijkAlgo_lgtps2.doRun();
		// Parcours en longueur (uniquement voiture)
		final AStarAlgorithm aStarAlgo_lglg2 = new AStarAlgorithm(
				new ShortestPathData(graph_lg2, orig_lg2, dest_lg2, ArcInspectorFactory.getAllFilters().get(1)));
		aStarPath_lglg2 = aStarAlgo_lglg2.doRun();
		// final DijkstraAlgorithm dijkAlgo_lglg2 = new DijkstraAlgorithm(new
		// ShortestPathData(graph_lg2, orig_lg2, dest_lg2,
		// ArcInspectorFactory.getAllFilters().get(1)));
		// dijkPath_lglg2 = dijkAlgo_lglg2.doRun();

		// destination == origine
		final AStarAlgorithm aStarAlgo_unnoeud = new AStarAlgorithm(
				new ShortestPathData(graph_crt1, orig_crt1, orig_crt1, ArcInspectorFactory.getAllFilters().get(0)));
		aStarPath_unnoeud = aStarAlgo_unnoeud.doRun();

		// impossible de relier destination et origine
		Node orig_impo = graph_crt2.getNodes().get(1282);
		Node dest_impo = graph_crt2.getNodes().get(864);
		final AStarAlgorithm aStarAlgo_impo = new AStarAlgorithm(
				new ShortestPathData(graph_crt2, orig_impo, dest_impo, ArcInspectorFactory.getAllFilters().get(0)));
		aStarPath_infaisable = aStarAlgo_impo.doRun();
	}
	/* --- --- FIN INITALL --- --- --- */

	/* --- --- --- ___Tests JUnit___ --- --- --- */
	@Test
	public void testShortPath() {
		assertEquals(null, bellPath_crt1.getPath().getLength(), aStarPath_crt1.getPath().getLength(), 0);
		assertEquals(null, bellPath_crt2.getPath().getLength(), aStarPath_crt2.getPath().getLength(), 0);
	}

	@Test
	public void testLongPath() { // On teste la cohérence des résultats entre eux, pas s'il s'agit effectivement
																// des trajets les plus courts / rapides existants
		// assertTrue(aStarPath_lglg1.getPath().getLength() <=
		// aStarPath_lgtps1.getPath().getLength());
		assertTrue(aStarPath_lglg2.getPath().getLength() <= aStarPath_lgtps2.getPath().getLength());
		// assertTrue(aStarPath_lglg1.getPath().getMinimumTravelTime() >=
		// aStarPath_lgtps1.getPath().getMinimumTravelTime());
		assertTrue(aStarPath_lglg2.getPath().getMinimumTravelTime() >= aStarPath_lgtps2.getPath().getMinimumTravelTime());
	}

	@Test
	public void testOneNodePath() {
		assertEquals(null, aStarPath_unnoeud.getPath().getLength(), 0, 0);
	}

	@Test
	public void testInfeasiblePath() {
		assertFalse(aStarPath_infaisable.isFeasible());
	}

	/*
	 * @Test
	 * public void testSolvingTime (){
	 * System.out.println(bellPath_crt1.getSolvingTime());
	 * assertTrue(aStarPath_crt1.getSolvingTime().compareTo(dijkPath_crt1.
	 * getSolvingTime()) == 0);
	 * assertTrue(aStarPath_crt2.getSolvingTime().compareTo(dijkPath_crt2.
	 * getSolvingTime()) == 0);
	 * assertTrue(aStarPath_lglg2.getSolvingTime().compareTo(dijkPath_lglg2.
	 * getSolvingTime()) == 0);
	 * assertTrue(aStarPath_lgtps2.getSolvingTime().compareTo(dijkPath_lgtps2.
	 * getSolvingTime()) == 0);
	 * }
	 */
	/* --- --- --- --- --- --- --- --- --- --- */
}
