package moe.fuqiuluo.shamrock.remote.action.handlers

import moe.fuqiuluo.qqinterface.servlet.TicketSvc
import moe.fuqiuluo.shamrock.remote.action.ActionSession
import moe.fuqiuluo.shamrock.remote.action.IActionHandler
import moe.fuqiuluo.shamrock.remote.service.data.Credentials

internal object GetCredentials: IActionHandler() {
    override suspend fun internalHandle(session: ActionSession): String {
        val domain = session.getStringOrNull("domain")
            ?: return invoke(session.echo)
        return invoke(domain, session.echo)
    }

    operator fun invoke(echo: String = ""): String {
        val uin = TicketSvc.getUin()
        val skey = TicketSvc.getSKey(uin)
        val pskey = TicketSvc.getPSKey(uin)
        return ok(
            Credentials(
            bkn = TicketSvc.getCSRF(pskey),
            cookie = "o_cookie=$uin; ied_qq=o$uin; pac_uid=1_$uin; uin=o$uin; skey=$skey; p_uin=o$uin; p_skey=$pskey;"
        ), echo)
    }

    suspend operator fun invoke(domain: String, echo: String = ""): String {
        val uin = TicketSvc.getUin()
        val skey = TicketSvc.getSKey(uin)
        val pskey = TicketSvc.getPSKey(uin, domain) ?: ""
        val pt4token = TicketSvc.getPt4Token(uin, domain) ?: ""
        return ok(Credentials(
            bkn = TicketSvc.getCSRF(pskey),
            cookie = "o_cookie=$uin; ied_qq=o$uin; pac_uid=1_$uin; uin=o$uin; skey=$skey; p_uin=o$uin; p_skey=$pskey; pt4_token=$pt4token;"
        ), echo)
    }

    override fun path(): String = "get_credentials"
}