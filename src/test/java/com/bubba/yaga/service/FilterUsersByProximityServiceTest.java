package com.bubba.yaga.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FilterUsersByProximityServiceTest {

    private FilterUsersByProximityService underTest = new FilterUsersByProximityService();

    private static final double LUTON_LAT = 51.8787;
    private static final double LUTON_LONG = -0.4200;

    private static final double LEEDS_LAT = 53.8008;
    private static final double LEEDS_LONG = -1.5491;

    @Nested
    public class getDistanceTests {

        @Test
        public void shouldReturnADouble() {
            double actual = underTest.getDistanceToLondon(LUTON_LAT, LUTON_LONG);

            assertThat(actual).isInstanceOf(Double.class);
        }

        @ParameterizedTest
        @MethodSource("com.bubba.yaga.service.FilterUsersByProximityServiceTest#latLong")
        public void shouldReturnADoubleWithWholeNumber(double lat, double lng) {
            double actual = underTest.getDistanceToLondon(lat, lng);

            assertThat(Double.toString(actual).split("\\.")[1]).hasSize(1);
        }

        @ParameterizedTest
        @MethodSource("com.bubba.yaga.service.FilterUsersByProximityServiceTest#latLongKnownDistanceBetween")
        public void shouldReturnDistanceBetweenCoordinates(double lat, double lng, double distance) {
            double actual = underTest.getDistanceToLondon(lat, lng);

            assertThat(actual).isEqualTo(distance);
        }
    }

    @Nested
    public class isWithin50MilesTests {

        @ParameterizedTest
        @MethodSource("com.bubba.yaga.service.FilterUsersByProximityServiceTest#latLong")
        public void shouldReturnBoolean() {
            boolean actual = underTest.isWithin50MilesOfLondon(LUTON_LAT, LUTON_LONG);

            assertThat(actual).isInstanceOf(Boolean.class);
        }

        @ParameterizedTest
        @MethodSource("com.bubba.yaga.service.FilterUsersByProximityServiceTest#latLongWithin50Miles")
        public void shouldReturnTrueForCoordinatesWithin50Miles(double lat, double lng, boolean bool) {

            boolean actual = underTest.isWithin50MilesOfLondon(lat, lng);

            assertThat(actual).isEqualTo(bool);
        }
    }

    private static Stream<Arguments> latLong() {
        return Stream.of(
                arguments(51.8787, -0.4200),
                arguments(51.3762, -0.0982),
                arguments(53.8008, -1.5491),
                arguments(40.7128, -74.0060),
                arguments(-6.2088, 106.8456));
    }

    private static Stream<Arguments> latLongKnownDistanceBetween() {
        return Stream.of(
                arguments(51.8787, -0.4200, 28.6),
                arguments(51.3762, -0.0982, 9.1),
                arguments(53.8008, -1.5491, 169.3),
                arguments(40.7128, -74.0060, 3461.2),
                arguments(-6.2088, 106.8456, 7281.3));
    }

    private static Stream<Arguments> latLongWithin50Miles() {
        return Stream.of(
                arguments(51.8787, -0.4200, true),
                arguments(51.3762, -0.0982, true),
                arguments(53.8008, -1.5491, false),
                arguments(40.7128, -74.0060, false),
                arguments(-6.2088, 106.8456, false));
    }
}
