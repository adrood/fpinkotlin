package com.fpinkotlin.lists.exercise01

// out A (if not used, the function of doesn't compile
sealed class List<out A> {

    abstract fun isEmpty(): Boolean

    // Not needed, we construct instances of List with the idiomatic 'of' method
    // Type parameter A occurs in the in-position
//    fun cons(a: A): List<A> = //TODO("cons")
//            Cons(a, this)

    // Why private?
    private object Nil : List<Nothing>() {

        override fun isEmpty() = true

        override fun toString(): String = "[NIL]"
    }

    // data class, why private?
    private class Cons<A>(internal val head: A, internal val tail: List<A>) : List<A>() {

        override fun isEmpty() = false

        override fun toString(): String = "[${toString("", this)}NIL]"

        private tailrec fun toString(acc: String, list: List<A>): String = when (list) {
            Nil -> acc
            is Cons -> toString("$acc${list.head}, ", list.tail)
        }
    }

    companion object {
        //        For data types, it is a common idiom to have a variadic of method in the
//        companion object to conveniently construct instances of the data type.
        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], List.of(*tail))
        }

        //
        operator fun <A> invoke(vararg az: A): List<A> =
                az.foldRight(Nil as List<A>) { a: A, list: List<A> -> Cons(a, list) }
    }
}
