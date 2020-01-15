package com.example.mykotlin.basic

fun main(args: Array<String>) {
    AgreegateDemo().filterListDemo()
    AgreegateDemo().copyDemo()
    AgreegateDemo().typeChange()
    AgreegateDemo().referanceDemo()
    AgreegateDemo().mapDemo()
    AgreegateDemo().createMapDemo()
    AgreegateDemo().listIteratorDemo()
    AgreegateDemo().mutableListIteratorDemo()
    AgreegateDemo().zipDemo()
    AgreegateDemo().zipDemo2()
    AgreegateDemo().unzipDemo()
    AgreegateDemo().associateWith()
    AgreegateDemo().listInnerSetDemo()
    AgreegateDemo().stringBufferBuilderDemo()


}

class AgreegateDemo {
    fun List<String>.getShortWordsTo(shortWords: MutableList<String>, maxLength: Int) {
        // 过滤列表会创建与过滤器匹配的新元素列表
        this.filterTo(shortWords) { it.length <= maxLength }
        // throwing away the articles
        val articles = setOf("a", "A", "an", "An", "the", "The", "in")
        shortWords -= articles
    }

    fun filterListDemo() {
        val words = "A long time ago in a galaxy far far away".split(" ")
        val shortWords = mutableListOf<String>()
        words.getShortWordsTo(shortWords, 3)
        println(shortWords)
    }

    //在特定时刻通过集合复制函数，例如toList()、toMutableList()、toSet() 等等。创建了集合的快照。
    // 结果是创建了一个具有相同元素的新集合 如果在源集合中添加或删除元素，则不会影响副本。
    // 副本也可以独立于源集合进行更改
    fun copyDemo() {
        val sourceList = mutableListOf(1, 2, 3)
        val copyList = sourceList.toMutableList()
        val readOnlyCopyList = sourceList.toList()
        sourceList.add(4)
        println("sourceList size: ${sourceList.size}")

        println("Copy size: ${copyList.size}")

        //readOnlyCopyList.add(4)             // 编译异常
        println("Read-only copy size: ${readOnlyCopyList.size}")
    }

    //这些函数还可用于将集合转换为其他类型，例如根据 List 构建 Set，反之亦然。
    fun typeChange() {
        val sourceList = mutableListOf(1, 2, 3)
        val copySet = sourceList.toMutableSet()
        copySet.add(3)
        copySet.add(4)
        println(copySet)
    }

    //可以创建对同一集合实例的新引用。使用现有集合初始化集合变量时，将创建新引用。
    //因此，当通过引用更改集合实例时，更改将反映在其所有引用中。
    fun referanceDemo() {
        val sourceList = mutableListOf(1, 2, 3)
        val referenceList = sourceList
        referenceList.add(4)
        println("Source size: ${sourceList.size}")
    }

    //集合的初始化可用于限制其可变性
    fun referanceLimit() {
        val sourceList = mutableListOf(1, 2, 3)
        val referenceList: List<Int> = sourceList
        //referenceList.add(4)            // 编译错误
        sourceList.add(4)
        println(referenceList) // 显示 sourceList 当前状态
    }

    fun mapDemo() {
        val numbers = setOf(1, 2, 3)
        println(numbers.map { it * 3 })
        println(numbers.mapIndexed { idx, value -> value * idx })
    }

    fun createMapDemo() {
        val numbers = listOf("one", "two", "three", "four")
        println(numbers.associateWith { it.length })
    }

    // 具有双向迭代的能力意味着 ListIterator 在到达最后一个元素后仍可以使用。
    // ListIterator 它支持列表双向迭代：正向与反向。 反向迭代由 hasPrevious() 和 previous() 函数实现。
    fun listIteratorDemo() {
        val numbers = listOf("one", "two", "three", "four")
        val listIterator = numbers.listIterator()
        while (listIterator.hasNext()) listIterator.next()
        println("Iterating backwards:")
        while (listIterator.hasPrevious()) {
            print("Index: ${listIterator.previousIndex()}")
            println(", value: ${listIterator.previous()}")
        }
    }

    //除了删除元素， MutableListIterator 还可以在迭代列表时插入和替换元素。
    fun mutableListIteratorDemo() {
        val numbers = mutableListOf("one", "four", "four")
        val mutableListIterator = numbers.listIterator()

        mutableListIterator.next()
        mutableListIterator.add("two")
        mutableListIterator.next()
        //覆盖
        mutableListIterator.set("three")
        println(numbers)
    }

    // 双路合并
    fun zipDemo() {
        val colors = listOf("red", "brown", "grey")
        val animals = listOf("fox", "bear", "wolf")
        println(colors zip animals)

        val twoAnimals = listOf("fox", "bear")
        println(colors.zip(twoAnimals))

    }

    fun zipDemo2() {
        val colors = listOf("red", "brown", "grey")
        val animals = listOf("fox", "bear", "wolf")

        println(colors.zip(animals) { color, animal -> "The ${animal.capitalize()} is $color" })
    }

    // 双路拆分
    fun unzipDemo() {
        val numberPairs = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4)
        println(numberPairs.unzip())
        //([one, two, three, four], [1, 2, 3, 4])
    }

    //关联
    fun associateWith() {
        val numbers = listOf("one", "two", "three", "four")
        println(numbers.associateWith { it.length })
        //{one=3, two=3, three=5, four=4}
    }

    //
    fun assocaiteBy() {
        val numbers = listOf("one", "two", "three", "four")

        println(numbers.associateBy { it.first().toUpperCase() })
        println(
            numbers.associateBy(
                keySelector = { it.first().toUpperCase() },
                valueTransform = { it.length })
        )
        //{O=one, T=three, F=four}
        //{O=3, T=5, F=4}
    }

    fun assocaiteDemo() {
//        val names = listOf("Alice Adams", "Brian Brown", "Clara Campbell")
//        println(names.associate { name -> parseFullName(name).let { it.lastName to it.firstName } })
    }

    // 打平 list位置放set
    fun listInnerSetDemo() {
        val numberSets = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
        println(numberSets.flatten())
    }

    // StringBuffer StringBuilder
    fun stringBufferBuilderDemo() {
        val numbers = listOf("one", "two", "three", "four")

        println(numbers)
        println(numbers.joinToString())

        val listString = StringBuffer("The list of numbers: ")
        numbers.joinTo(listString)
        listString.append(" YES")
        println(listString)

        val listSB = StringBuilder("gari :")
        numbers.joinTo(listSB)
        print(listSB)

        println(numbers.joinToString(separator = " | ", prefix = "start: ", postfix = ": end"))

        val numberss = (1..100).toList()
        println(numberss.joinToString(limit = 10, truncated = "<...>"))

        val numbersss = listOf(null, 1, "two", 3.0, "four")
        println("All String elements in upper case:")
        numbersss.filterIsInstance<String>().forEach {
            println(it.toUpperCase())
        }

        //划分
        val numberssss = listOf("one", "two", "three", "four")
        val (match, rest) = numberssss.partition { it.length > 3 }

        println(match)
        println(rest)


        val numbersssss = listOf(5, 2, 10, 4)

        val sum = numbersssss.reduce { sum, element -> sum + element }
        println(sum)
        val sumDoubled = numbersssss.fold(0) { sum, element -> sum + element * 2 }
        println(sumDoubled)
    }


}

