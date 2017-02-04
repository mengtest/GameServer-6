package core

import akka.actor.{ActorRef, ActorLogging, Actor}
import core.StorageService.{SomePlayer, GetPlayerByName}
import entity.{Player, Point}
import proto.Packet.{Login, PacketMSG}


/**
 * Created by uc on 2017/2/4.
 */
class StorageService extends Actor with ActorLogging{

    val players = List(
          new Player(1L, "Tester1", "test", Point(0, 0)),
          new Player(2L, "Tester2", "test", Point(0, 0)),
          new Player(3L, "Tester3", "test", Point(0, 0)),
          new Player(4L, "Tester4", "test", Point(0, 0))
    )

    override  def preStart(): Unit ={
       log.info("Starting storage service")
    }

    override def postStop() {
       log.info("Stopping storage service")
    }

    override  def receive ={
        case GetPlayerByName => handleAuth(GetPlayerByName)
        case _ => log.info("unknown message")
    }

    def handleAuth(task: GetPlayerByName) ={
      val cmd: Login = Login.parseFrom(task.msg.getData)

      val player = players.filter(p => p.name.equals(cmd.getName) && p.pass.equals(cmd.getPass) ).head

      player match {

          case Player(_,_,_) => sender ! SomePlayer(task.session, task.msg, player)
          case _ => sender !
      }
    }
}

object StorageService{

    case class GetPlayerByName(session: ActorRef, msg: PacketMSG)
    case class SomePlayer(session: ActorRef, msg: PacketMSG, player: Player)
}