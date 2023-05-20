import org.jpl7._
object Main {
  def main(args: Array[String]): Unit = {
    println("EEE")

//    val q1 = new Query("consult('D:/Second Year Computer/Term 2/Paradigms/Project/EightQueensProlog.pl')")
//    println("consult" + (if(q1.hasSolution) "succeeded" else "failed"))
//    val q = new Query("consult('D:/Second Year Computer/Term 2/Paradigms/Project/FamTree.pl')")

    val q = new Query("consult('D:/Second Year Computer/Term 2/Paradigms/Project/Sudoko.pl')")
    if(q.hasSolution){
      val q2 = new Query("Array = [[0,0,6,0,0,0,0,0,9],\n            [4,0,8,5,0,0,2,6,0],\n            [3,0,5,8,0,6,1,7,0],\n            [1,0,0,2,0,7,9,0,0],\n            [8,0,0,0,5,0,0,3,7],\n            [5,7,0,3,4,0,6,0,0],\n            [2,8,0,0,0,0,0,0,6],\n            [0,0,0,0,0,0,0,0,2],\n            [9,0,7,6,0,2,3,0,5]],\n  solve(Array, ConvertedArray).")
      if(q2.hasSolution){
        val qsValue = q2.oneSolution().get("ConvertedArray").toString
        println("qsValue = " +qsValue)
      }
      println("succeeded")

    }else{
      println("failed")
    }

  }

}
