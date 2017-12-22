package eic

import ammonite.sshd._
import org.apache.sshd.server.auth.password.PasswordAuthenticator
import org.apache.sshd.server.session.ServerSession

object RunAll extends App {
    object passwordChecker extends PasswordAuthenticator {
      def authenticate(username: String, password: String, session: ServerSession): Boolean = {
        username == "scala" && password == "pizza"
      }
    }

    println("--- em outro terminal use: ssh scala@localhost -p 22222 e informe a senha pizza  ---")
    // SshServer.getPasswordAuthenticator()
    val replServer = new SshdRepl(
      SshServerConfig(
        address = "localhost", // or "0.0.0.0" for public-facing shells
        port = 22222, // Any available port
        passwordAuthenticator = Some(passwordChecker) // or publicKeyAuthenticator
      )
    )
    replServer.start()
    Thread.sleep(90000000) // 25 horas
}

