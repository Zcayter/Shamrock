package moe.fuqiuluo.shamrock.remote.action.handlers

import moe.fuqiuluo.qqinterface.servlet.GroupSvc
import moe.fuqiuluo.shamrock.remote.action.ActionSession
import moe.fuqiuluo.shamrock.remote.action.IActionHandler

internal object LeaveTroop: IActionHandler() {
    override suspend fun internalHandle(session: ActionSession): String {
        val groupId = session.getString("group_id")
        return invoke(groupId, session.echo)
    }

    operator fun invoke(groupId: String, echo: String = ""): String {
        if (GroupSvc.isOwner(groupId)) {
            return error("you are the owner of this group", echo)
        }
        GroupSvc.resignTroop(groupId.toLong())
        return ok("成功", echo)
    }

    override val requiredParams: Array<String> = arrayOf("group_id")

    override val alias: Array<String> = arrayOf("set_group_leave")

    override fun path(): String = "leave_group"
}