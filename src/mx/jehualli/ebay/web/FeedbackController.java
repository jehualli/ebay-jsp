package mx.jehualli.ebay.web;

import mx.jehualli.ebay.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A member's feedback profile, and the form to add to it. Viewing is open to
 * anyone; leaving feedback needs a signed-in member, which this controller
 * checks itself because the filter is mapped by URL, not by HTTP method.
 */
public class FeedbackController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String target = param(req, "user");
        User member = house().getUser(target);
        if (member == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No such member");
            return;
        }

        if ("POST".equals(req.getMethod())) {
            String me = nick(req);
            if (me == null) {
                req.getSession(true).setAttribute("afterSignIn", "/feedback?user=" + target);
                redirect(req, res, "/login?why=required");
                return;
            }
            if (me.equalsIgnoreCase(member.getNick())) {
                req.setAttribute("error", "You cannot leave feedback for yourself.");
            } else {
                String comment = param(req, "comment");
                if (comment.length() == 0) {
                    req.setAttribute("error", "Please write a comment.");
                } else {
                    if (comment.length() > 80) comment = comment.substring(0, 80);
                    house().leaveFeedback(me, member.getNick(), 0L,
                                          intParam(req, "rating", 1), comment);
                    // Redirect after post: F5 must not leave a second comment.
                    redirect(req, res, "/feedback?user=" + member.getNick() + "&left=1");
                    return;
                }
            }
        }

        req.setAttribute("member", member);
        req.setAttribute("comments", house().feedbackFor(member.getNick()));
        forward(req, res, "feedback.jsp");
    }
}
