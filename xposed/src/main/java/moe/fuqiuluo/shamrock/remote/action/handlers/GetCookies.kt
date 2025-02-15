package moe.fuqiuluo.shamrock.remote.action.handlers

import moe.fuqiuluo.qqinterface.servlet.TicketSvc
import moe.fuqiuluo.shamrock.remote.action.ActionSession
import moe.fuqiuluo.shamrock.remote.action.IActionHandler
import moe.fuqiuluo.shamrock.remote.service.data.Credentials

internal object GetCookies: IActionHandler() {
    override suspend fun internalHandle(session: ActionSession): String {
        val domain = session.getStringOrNull("domain")
            ?: return invoke(session.echo)
        return invoke(domain, session.echo)
    }

    operator fun invoke(echo: String = ""): String {
        return ok(Credentials(cookie = TicketSvc.getCookie()), echo)
    }

    suspend operator fun invoke(domain: String, echo: String = ""): String {
        return ok(Credentials(cookie = TicketSvc.getCookie(domain)), echo)
    }

    override fun path(): String = "get_cookies"
}