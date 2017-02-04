package core

import akka.actor.{ActorRef, Actor, ActorLogging}
import core.AuthService.Authenticate
import proto.Packet.PacketMSG

/**
 * Created by uc on 2017/2/4.
 */
class AuthService extends Actor with ActorLogging{

    val storageService = context.actorSelection("akka://server/user/storage")
    val taskService = context.actorSelection("akka://server/user/task")
    val gameService = context.actorSelection("akka://server/user/game")


    override def preStart={
      log.info("Starting auth service ")
    }

    override def receive ={
      case
    }

    def handleAuth(task: Authenticate) = {

         storageService ! StorageService.GetPlayerByName(task.session, task.msg)
    }

}

object AuthService{

   case class Authenticate(session: ActorRef, msg: PacketMSG)
   case class AuthenticatedFailed(session: ActorRef, msg: PacketMSG)
}