package subway.domain;

import subway.common.ErrorCustomException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StationRepository {
    private static final List<Station> stations = new ArrayList<>();
    private static final String NON_EXIST_STATION = "존재하는 역이 없습니다.";

    public static List<Station> stations() {
        return Collections.unmodifiableList(stations);
    }

    public static void addStation(Station station) {
        stations.add(station);
    }

    public static boolean deleteStation(String name) {
        return stations.removeIf(station -> Objects.equals(station.getName(), name));
    }

    public static void deleteAll() {
        stations.clear();
    }

    public static boolean containsStation(String stationName) {
        return stations.stream()
            .anyMatch(station -> Objects.equals(station.getName(), stationName));
    }

    public static Station getStationByName(String stationName) {
        return stations.stream().filter(station -> Objects.equals(station.getName(), stationName))
            .findFirst().orElseThrow(() -> new ErrorCustomException(NON_EXIST_STATION));
    }

}
