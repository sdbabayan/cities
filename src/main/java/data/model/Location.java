package data.model;

public class Location {
    private final double latitude;
    private final double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location(double latitude, double longitude) {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new LocationException("Широта должна быть от -90 до 90 градусов");
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new LocationException("Долгота должна быть от -180 до 180 градусов");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getDistance(Location destinationPoint) {
        final double R = 6371.0;

        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(destinationPoint.getLatitude());
        double lon2 = Math.toRadians(destinationPoint.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;

        return distance;
    }

    @Override
    public String toString() {
        return ("широта: " + String.valueOf(this.latitude) + ", долгота: " + String.valueOf(this.longitude));
    }
}
