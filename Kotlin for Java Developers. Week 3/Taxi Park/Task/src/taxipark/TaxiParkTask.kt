package taxipark


/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver>
{
    val allDrivers = this.allDrivers
    var goodDrivers = mutableSetOf<Driver>()
    this.trips.forEach(){trip -> if (allDrivers.contains(trip.driver)) goodDrivers.add(trip.driver)}

    return allDrivers.minus(goodDrivers)
}



/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger>
{
    val allPassengers = this.allPassengers
    if (minTrips == 0) return allPassengers

    var passengerTripCount = mutableMapOf<Passenger, Int>()
    //this.trips.forEach(){trip -> trip.passengers.forEach(){ passenger -> passenger.name.groupingBy{passenger}.eachCountTo(passengerTripCount)}}

    this.trips.forEach(){trip -> trip.passengers.forEach()
    {
        passenger -> if (passengerTripCount.containsKey(passenger))
        {
            passengerTripCount[passenger] = passengerTripCount.get(passenger)!! + 1
        }
        else
        {
            passengerTripCount[passenger] = 1
        }
    }}
    val faithfulPassengers = passengerTripCount.filterKeys { passenger -> passengerTripCount.get(passenger)!! >= minTrips }.keys.toSet()
    //println("$passengerTripCount")

    return faithfulPassengers
}


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> = this.allPassengers.filter {
    passenger -> this.trips.filter { trip -> passenger in trip.passengers && trip.driver == driver
}.count() > 1 }.toSet()


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> = allPassengers.filter { hasDiscount(it) }.toSet()

private fun TaxiPark.hasDiscount(it: Passenger) = passengerWithDiscount(it) > passengerWithoutDiscount(it)

private fun TaxiPark.passengerWithDiscount(passenger: Passenger) = trips
        .filter { passengerExistInTrip(passenger, it) }
        .filter (::isReceivingADiscount )
        .count()

private fun TaxiPark.passengerWithoutDiscount(passenger: Passenger) = trips
        .filter { passengerExistInTrip(passenger, it) }
        .filter (::isNotReceivingADiscount )
        .count()

private fun passengerExistInTrip(passenger: Passenger, it: Trip) = passenger in it.passengers

private fun isReceivingADiscount(trip: Trip) = trip.discount != null && trip.discount > 0.0

private fun isNotReceivingADiscount(trip: Trip) = trip.discount == null || trip.discount == 0.0



/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    var range: IntRange = 0..59
    var rangeCount = mutableMapOf<Int, Int>()

    this.trips.forEach{trip -> trip.passengers.forEach()
    {
        println(trip.duration)
    }}

/*
    this.trips.forEach{trip -> trip.passengers.forEach()
    {
        passenger -> if (rangeCount.containsKey(trip.duration.div(10)))
        {
            rangeCount[trip.duration.div(10)] = rangeCount.get(trip.duration.div(10))!! + 1
        }
        else
        {
            rangeCount[trip.duration.div(10)] = 1
        }
    }}

    println(rangeCount)
    val maxRange = rangeCount.maxBy { it.value }?.toPair()?.first
    println("R $maxRange")
*/

    return range
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    TODO()
}