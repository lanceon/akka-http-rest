package me.archdev.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._

import me.archdev.restapi.http.SecurityDirectives
import me.archdev.restapi.models.UserEntity
import me.archdev.restapi.services.AuthService

import io.circe.generic.auto._
import io.circe.syntax._

trait AuthServiceRoute extends AuthService with BaseServiceRoute with SecurityDirectives {

  import StatusCodes._

  case class LoginPassword(login: String, password: String)

  val authRoute = pathPrefix("auth") {
    path("signIn") {
      pathEndOrSingleSlash {
        post {
          entity(as[LoginPassword]) { loginPassword =>
            complete(signIn(loginPassword.login, loginPassword.password).map(_.asJson))
          }
        }
      }
    } ~
      path("signUp") {
        pathEndOrSingleSlash {
          post {
            entity(as[UserEntity]) { userEntity =>
              complete(Created -> signUp(userEntity).map(_.asJson))
            }
          }
        }
      }
  }

}
