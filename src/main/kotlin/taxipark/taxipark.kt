package taxipark

import java.util.*

class Passenger (
    val id: Int = 123456789,
    val name: String = "Passenger",
    val trips: Collection<Trip> = mutableListOf()
)

class Driver (
    val id: Int = 123456789,
    val name: String = "Driver",
    val trips: Collection<Trip> = mutableListOf()
)

class Trip (
    val origin: String,
    val target: String,
    val duration: Double,
    val distance: Double,
    val price: Double,
    val discount: Double,
    val Date: Date,
    val driver: Driver,
    val passengers: Collection<Passenger>,
)

class TaxiPark (
    private val drivers: Collection<Driver>,
    private val passengers: Collection<Passenger>,
    private val trips: Collection<Trip>
) {
    fun findFakeDrivers(): Collection<Driver> {
        return this.drivers.filter {it.trips.isEmpty()}
    }

    fun findFaithfulPassengers(minTrips: Int): List<Passenger> {
        return this.passengers.filter { it.trips.size >= minTrips}
    }

    fun findFrequentPassengers(driver: Driver): List<Passenger> {
        val driverPassengers = driver.trips.flatMap { it.passengers }
        val uniquePassengers = mutableSetOf<Passenger>()
        val freqPassengers = mutableListOf<Passenger>()
        for (p in driverPassengers) {
            if (uniquePassengers.contains(p)) {
                freqPassengers.add(p)
            } else {
                uniquePassengers.add(p)
            }
        }
        return freqPassengers
    }

    fun findSmartPassengers(): Collection<Passenger> {
        return this.trips.filter {it.discount > 0.0}.flatMap {it.passengers}
    }

    fun findTheMostFrequentTripDuration(): IntRange? {
        if (this.trips.isEmpty()) return null
        var mostFreqRange = 0 to 0 // (rangeStart, frequency)
        val mem = hashMapOf<Int, Int>()

        for (t in this.trips) {
            val bottomRange = (t.duration / 10).toInt()
            mem[bottomRange] = mem.getOrDefault(bottomRange, 0) + 1
            val currFreq = mem.getOrDefault(bottomRange, 0)
            if (currFreq > mostFreqRange.second) {
                mostFreqRange = bottomRange to currFreq
            }
        }

        return IntRange(mostFreqRange.first * 10, mostFreqRange.first * 10 + 9)
    }

    fun checkParetoPrinciple(): Boolean {
        if (this.trips.isEmpty()) return false

        val driversSortedByTrips = this.drivers.sortedBy { it.trips.size }
        val topTwentyPercentDrivers = driversSortedByTrips.subList(0, (driversSortedByTrips.size * 0.2).toInt())

        var topDriversTrips = 0
        for (d in topTwentyPercentDrivers) {
            topDriversTrips += d.trips.size
        }

        return topDriversTrips >= this.trips.size * 0.8
    }
}