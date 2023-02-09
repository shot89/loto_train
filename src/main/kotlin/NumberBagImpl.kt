class NumberBagImpl:NumberBag {
    override fun fillTheBag(): MutableList<Int> {
        var bag = mutableListOf<Int>()
        for (i in 1..90){
            bag.add(i)
        }
        bag.shuffle()
        return bag
    }
}