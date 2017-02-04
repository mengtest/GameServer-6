package entity

/**
 * Created by uc on 2017/2/4.
 */
case class Point(var x:Int, var y:Int)

case class Player(id:Long, name:String, pass:String, point:Point)