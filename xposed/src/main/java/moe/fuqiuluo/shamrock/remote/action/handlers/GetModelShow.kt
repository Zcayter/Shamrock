package moe.fuqiuluo.shamrock.remote.action.handlers

import moe.fuqiuluo.qqinterface.servlet.CardSvc
import moe.fuqiuluo.shamrock.remote.action.ActionSession
import moe.fuqiuluo.shamrock.remote.action.IActionHandler

internal object GetModelShow: IActionHandler() {
    override suspend fun internalHandle(session: ActionSession): String {
        val uin = session.getLongOrNull("user_id")
        return if (uin == null) {
            invoke(session.echo)
        } else {
            invoke(uin, session.echo)
        }
    }

    suspend operator fun invoke(echo: String = ""): String {
        return ok(CardSvc.getModelShow(), echo)
    }

    suspend operator fun invoke(uin: Long, echo: String = ""): String {
        if (uin == 0L) {
            return invoke(echo)
        }
        return ok(CardSvc.getModelShow(uin), echo)
    }

    override fun path(): String = "get_model_show"
}