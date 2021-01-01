package subway.domain;

import java.util.List;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

public class PathRepository {
    private static final WeightedMultigraph<Station, DefaultWeightedEdge> graphByDistance
        = new WeightedMultigraph<>(DefaultWeightedEdge.class);
    private static final WeightedMultigraph<Station, DefaultWeightedEdge> graphByTime
        = new WeightedMultigraph<>(DefaultWeightedEdge.class);
    private static final DijkstraShortestPath<Station, Station> dijkstraShortestDistance
        = new DijkstraShortestPath(graphByDistance);
    private static final DijkstraShortestPath<Station, Station> dijkstraShortestTime
        = new DijkstraShortestPath(graphByTime);

    private PathRepository() {
    }

    public static void addGraphVertex(String vertex) {
        graphByDistance.addVertex(StationRepository.getStationByName(vertex));
        graphByTime.addVertex(StationRepository.getStationByName(vertex));
    }

    public static void addGraphEdgeAndWeight(String sourceVertex, String targetVertex, int distance,
        int time) {
        addWeightByDistance(sourceVertex, targetVertex, distance);
        addWeightByTime(sourceVertex, targetVertex, time);
    }

    private static void addWeightByDistance(String sourceVertex, String targetVertex, int weight) {
        graphByDistance.setEdgeWeight(graphByDistance
            .addEdge(StationRepository.getStationByName(sourceVertex),
                StationRepository.getStationByName(targetVertex)), weight);
    }

    private static void addWeightByTime(String sourceVertex, String targetVertex, int weight) {
        graphByTime.setEdgeWeight(graphByTime
            .addEdge(StationRepository.getStationByName(sourceVertex),
                StationRepository.getStationByName(targetVertex)), weight);
    }

    public static List<Station> getListByShortestDistance(String source, String sink) {
        return dijkstraShortestDistance.getPath(StationRepository.getStationByName(source),
            StationRepository.getStationByName(sink)).getVertexList();
    }

    public static List<Station> getListByShortestTime(String source, String sink) {
        return dijkstraShortestTime.getPath(StationRepository.getStationByName(source),
            StationRepository.getStationByName(sink)).getVertexList();
    }

    public static int getDistanceByList(List<Station> path) {
        double totalTime = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalTime += graphByDistance
                .getEdgeWeight(graphByDistance.getEdge(path.get(i), path.get(i + 1)));
        }
        return (int) totalTime;
    }

    public static int getTimeByList(List<Station> path) {
        double totalTime = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalTime += graphByTime
                .getEdgeWeight(graphByTime.getEdge(path.get(i), path.get(i + 1)));
        }
        return (int) totalTime;
    }

}
