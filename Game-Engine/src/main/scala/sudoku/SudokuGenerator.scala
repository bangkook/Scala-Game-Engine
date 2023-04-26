package sudoku

import scala.collection.immutable.Set

object SudokuGenerator {
  val size = 9
  var board: Array[Array[Int]] = Array.ofDim[Int](size, size)
  private val possible: Set[Int] = Set(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
  private val rand = new scala.util.Random(System.currentTimeMillis)
  val puzzle = new Array[Int](81)

  lazy val presented: Array[Int] = {
    val r = new Array[Int](81)

    def randomSet(max: Int, count: Int, set: Set[Int]): Set[Int] = {
      set.size match {
        case `count` => set
        case _ => randomSet(max, count, set + rand.nextInt(max))
      }
    }

    randomSet(81, 35, Set()).foreach(index => r(index) = puzzle(index))
    r
  }

  private def rowValues(cell: Int): Set[Int] = {
    val index: Int = cell / 9 * 9
    puzzle.slice(index, index + 9).toSet
  }

  private def columnValues(cell: Int): Set[Int] =
    ((cell % 9) to 80 by 9).map(index => puzzle(index)).toSet

  // What values have been used in the same block as the cell?
  private def blockValues(cell: Int): Set[Int] = {
    val startColumn = ((cell % 9) / 3) * 3
    val startRow = ((cell / 9) / 3) * 3
    val blockArray = (0 to 2) flatMap {
      (row: Int) =>
        val currentLocation = ((startRow + row) * 9) + startColumn
        puzzle.slice(currentLocation, currentLocation + 3)
    }
    blockArray.toSet
  }

  private def possibleNumbers(cell: Int): List[Int] = {
    val items = possible -- (rowValues(cell) ++ columnValues(cell) ++ blockValues(cell))
    scala.util.Random.shuffle(items.toList)
  }

  def puzzleToString(p: Array[Int]): String = {
    (0 to 8).map {
      row => (0 to 8).map(column => p(row * 9 + column).toString).mkString(" ")
    }.mkString("\n")
  }

  override def toString = puzzleToString(puzzle)

  def generate(cell: Int, possibilities: List[Int]): Boolean = {
    (cell, possibilities) match {
      case (81, _) => true // If we get to cell 81, we're done!
      case (_, Nil) => false // If we have no possibilities, time to backtrack
      case _ => {
        // Try filling in the cell with each of the possibilities until either all
        // possibilities are exhausted or an acceptable possibility is found
        val x = possibilities dropWhile {
          value =>
            puzzle(cell) = value
            val nextCell = cell + 1
            if (generate(nextCell, possibleNumbers(nextCell)) == false) {
              puzzle(cell) = 0
              true
            }
            else false
        }
        x != Nil
      }
    }
  }

  def removeKDigits() = {
    var count = 50;
    while (count != 0) {
      var cellId: Int = rand.between(0, 80)
      if (puzzle(cellId) != 0) {
        count = count - 1
        puzzle(cellId) = 0
      }
    }
  }

  def getSudoku(board: Array[Array[Int]]){
    generate(0, possibleNumbers(0))
    removeKDigits()
    var c = 0
    for (x <- 0 until board.size) {
      for (y <- 0 until board.size) {
        board(x)(y) = puzzle(c)
        c = c + 1
        //                print(board(x)(y) + "  ")
      }
      //            println("")
    }
  }

}
